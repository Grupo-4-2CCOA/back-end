package sptech.school.projetoPI.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sptech.school.projetoPI.core.domains.ServiceRanking;
import sptech.school.projetoPI.infrastructure.persistence.entity.ScheduleJpaEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public interface JpaDashboardRepository extends JpaRepository<ScheduleJpaEntity, Integer> {

  @Query(value = """
        SELECT 
            YEAR(s.appointment_datetime) AS ano,
            MONTH(s.appointment_datetime) AS mes,
            COALESCE(SUM(si.final_price - si.discount), 0) AS rendimento
        FROM schedule_item si
        JOIN schedule s ON s.id = si.fk_schedule
        WHERE DATE(s.appointment_datetime) BETWEEN :startDate AND :endDate
          AND s.status = 'COMPLETED'
        GROUP BY ano, mes
        ORDER BY ano, mes
    """, nativeQuery = true)
  List<Object[]> findRendimentoPorMes(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  @Query(value = """
        SELECT 
            YEAR(s.appointment_datetime) AS ano,
            MONTH(s.appointment_datetime) AS mes,
            ROUND(
                (SUM(CASE WHEN s.status = 'CANCELED' THEN 1 ELSE 0 END) / COUNT(*)) * 100, 
                2
            ) AS taxa_cancelamento
        FROM schedule s
        WHERE DATE(s.appointment_datetime) BETWEEN :startDate AND :endDate
        GROUP BY ano, mes
        ORDER BY ano, mes
    """, nativeQuery = true)
  List<Object[]> findTaxaCancelamentoPorMes(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  @Query(value = "SELECT COUNT(*) FROM schedule s WHERE DATE(s.appointment_datetime) BETWEEN :startDate AND :endDate AND s.status = 'COMPLETED'", nativeQuery = true)
  int findTotalAtendimentos(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  @Query(value = """
      SELECT
          ROW_NUMBER() OVER (ORDER BY COUNT(*) DESC) AS linha,
          sv.name AS nomeServico,
          COUNT(*) AS quantidade,
          SUM(si.final_price) AS valorTotal
      FROM schedule_item si
      JOIN service sv ON sv.id = si.fk_service
      JOIN schedule s ON s.id = si.fk_schedule
      WHERE DATE(s.appointment_datetime) BETWEEN :startDate AND :endDate
        AND s.status = 'COMPLETED'
      GROUP BY sv.name
      ORDER BY quantidade DESC;
    """, nativeQuery = true)
  List<Object[]> findRankingServicosMaisVendidos(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  default List<ServiceRanking> findRankingServicos(LocalDate startDate, LocalDate endDate) {
    return findRankingServicosMaisVendidos(startDate, endDate).stream()
      .map(r -> new ServiceRanking(((Number) r[0]).intValue(), (String) r[1], ((Number) r[2]).longValue(), ((Number) r[3]).doubleValue()))
      .toList();
  }

  // Agrupa leads por ano e mês, retorna: [ano, mes, quantidade]
  @Query(value = """
    SELECT
        YEAR(u.created_at) AS ano,
        MONTH(u.created_at) AS mes,
        COUNT(*) AS usuarios_sem_agendamentos
    FROM user u
    LEFT JOIN schedule s
        ON s.fk_client = u.id
    WHERE DATE(u.created_at) BETWEEN :startDate AND :endDate
      AND s.id IS NULL
    GROUP BY ano, mes
    ORDER BY ano, mes
    """, nativeQuery = true)
  List<Object[]> findLeadsPorMes(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  @Query(value = """
    SELECT
        YEAR(s.appointment_datetime) AS ano,
        MONTH(s.appointment_datetime) AS mes,
        COUNT(*) AS usuarios_com_1_agendamento
    FROM user u
    JOIN schedule s ON s.fk_client = u.id
    WHERE 
        (SELECT COUNT(*) 
         FROM schedule sc 
         WHERE sc.fk_client = u.id) = 1
        AND DATE(s.appointment_datetime) BETWEEN :startDate AND :endDate
    GROUP BY ano, mes
    ORDER BY ano, mes
    """, nativeQuery = true)
  List<Object[]> findPrimeirosAgendamentosPorMes(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  @Query(value = """
    SELECT 
        YEAR(segundo.appointment_datetime) AS ano,
        MONTH(segundo.appointment_datetime) AS mes,
        COUNT(*) AS usuarios_2_ou_mais_agendamentos
    FROM (
        SELECT 
            s.fk_client AS user_id,
            (
                SELECT s2.appointment_datetime
                FROM schedule s2
                WHERE s2.fk_client = s.fk_client
                ORDER BY s2.appointment_datetime
                LIMIT 1 OFFSET 1
            ) AS segundo_agendamento
        FROM schedule s
        GROUP BY s.fk_client
        HAVING COUNT(*) >= 2
    ) AS x
    JOIN schedule segundo ON segundo.fk_client = x.user_id
        AND segundo.appointment_datetime = x.segundo_agendamento
    WHERE DATE(segundo.appointment_datetime) BETWEEN :startDate AND :endDate
    GROUP BY ano, mes
    ORDER BY ano, mes
    """, nativeQuery = true)
  List<Object[]> findTaxaRetornoPorMes(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  @Query(value = """
    SELECT 
        ano,
        mes,
        ROUND(
            CASE 
                WHEN total_leads = 0 THEN 0
                ELSE (total_clientes / total_leads) * 100 
            END, 
            2
        ) AS taxa_conversao
    FROM (
        SELECT 
            YEAR(u.created_at) AS ano,
            MONTH(u.created_at) AS mes,
            COUNT(*) AS total_leads,
            SUM(CASE WHEN EXISTS (
                SELECT 1 FROM schedule s WHERE s.fk_client = u.id
            ) THEN 1 ELSE 0 END) AS total_clientes
        FROM user u
        WHERE DATE(u.created_at) BETWEEN :startDate AND :endDate
        GROUP BY ano, mes
    ) AS stats
    ORDER BY ano, mes
    """, nativeQuery = true)
  List<Object[]> findTaxaConversaoPorMes(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
