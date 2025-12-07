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

    // DASHBOARD DE SERVIÇOS
  // CORREÇÃO: Utilizando DAYOFMONTH para calcular a semana do mês (1 a 5)
  @Query(value = """
        SELECT 
            (DAYOFMONTH(s.appointment_datetime) - 1) DIV 7 + 1 AS semana,
            COALESCE(SUM(si.final_price - si.discount), 0) AS rendimento
        FROM schedule_item si
        JOIN schedule s ON s.id = si.fk_schedule
        WHERE DATE(s.appointment_datetime) BETWEEN :startDate AND :endDate
          AND s.status = 'COMPLETED'
        GROUP BY semana
        ORDER BY semana
    """, nativeQuery = true)
  List<Object[]> findRendimentoPorSemana(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  // CORREÇÃO: Utilizando DAYOFMONTH para calcular a semana do mês (1 a 5)
  @Query(value = """
        SELECT 
            (DAYOFMONTH(s.appointment_datetime) - 1) DIV 7 + 1 AS semana,
            ROUND(
                (SUM(CASE WHEN s.status = 'CANCELED' THEN 1 ELSE 0 END) / COUNT(*)) * 100, 
                2
            ) AS taxa_cancelamento
        FROM schedule s
        WHERE DATE(s.appointment_datetime) BETWEEN :startDate AND :endDate
        GROUP BY semana
        ORDER BY semana
    """, nativeQuery = true)
  List<Object[]> findTaxaCancelamentoPorSemana(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

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

  // DASHBOARD DE FUNIL DE VENDAS

  @Query(value = """
    SELECT
        (DAYOFMONTH(u.created_at) - 1) DIV 7 + 1 AS semana,
        COUNT(*) AS usuarios_sem_agendamentos
    FROM user u
    LEFT JOIN schedule s
        ON s.fk_client = u.id
    WHERE DATE(u.created_at) BETWEEN :startDate AND :endDate
      AND s.id IS NULL
    GROUP BY semana
    ORDER BY semana;
    """, nativeQuery = true)
  List<Object[]> findLeadsPorSemana(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  @Query(value = """
    SELECT
        (DAYOFMONTH(s.appointment_datetime) - 1) DIV 7 + 1 AS semana,
        COUNT(*) AS usuarios_com_1_agendamento
    FROM user u
    JOIN schedule s ON s.fk_client = u.id
    WHERE\s
        (SELECT COUNT(*)\s
         FROM schedule sc\s
         WHERE sc.fk_client = u.id) = 1
        AND DATE(s.appointment_datetime) BETWEEN :startDate AND :endDate
    GROUP BY semana
    ORDER BY semana;
    """, nativeQuery = true)
  List<Object[]> findPrimeirosAgendamentosPorSemana(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  @Query(value = """
    SELECT\s
        (DAYOFMONTH(segundo.appointment_datetime) - 1) DIV 7 + 1 AS semana,
        COUNT(*) AS usuarios_2_agendamentos
    FROM (
        SELECT\s
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
        HAVING COUNT(*) = 2
    ) AS x
    JOIN schedule segundo ON segundo.fk_client = x.user_id
        AND segundo.appointment_datetime = x.segundo_agendamento
    WHERE DATE(segundo.appointment_datetime) BETWEEN :startDate AND :endDate
    GROUP BY semana
    ORDER BY semana;
    """, nativeQuery = true)
  List<Object[]> findTaxaRetornoPorSemana(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
