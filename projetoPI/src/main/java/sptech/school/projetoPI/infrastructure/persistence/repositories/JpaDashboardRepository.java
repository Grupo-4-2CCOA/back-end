package sptech.school.projetoPI.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sptech.school.projetoPI.core.domains.ServiceRanking;
import sptech.school.projetoPI.infrastructure.persistence.entity.ScheduleJpaEntity;

import java.util.List;

@Repository
public interface JpaDashboardRepository extends JpaRepository<ScheduleJpaEntity, Integer> {

    // CORREÇÃO: Utilizando DAYOFMONTH para calcular a semana do mês (1 a 5)
    @Query(value = """
        SELECT 
            (DAYOFMONTH(s.appointment_datetime) - 1) DIV 7 + 1 AS semana,
            COALESCE(SUM(si.final_price - si.discount), 0) AS rendimento
        FROM schedule_item si
        JOIN schedule s ON s.id = si.fk_schedule
        WHERE MONTH(s.appointment_datetime) = :mes
          AND YEAR(s.appointment_datetime) = :ano
          AND s.status = 'COMPLETED'
        GROUP BY semana
        ORDER BY semana
    """, nativeQuery = true)
    List<Object[]> findRendimentoPorSemana(@Param("mes") int mes, @Param("ano") int ano);

    // CORREÇÃO: Utilizando DAYOFMONTH para calcular a semana do mês (1 a 5)
    @Query(value = """
        SELECT 
            (DAYOFMONTH(s.appointment_datetime) - 1) DIV 7 + 1 AS semana,
            ROUND(
                (SUM(CASE WHEN s.status = 'CANCELED' THEN 1 ELSE 0 END) / COUNT(*)) * 100, 
                2
            ) AS taxa_cancelamento
        FROM schedule s
        WHERE MONTH(s.appointment_datetime) = :mes
          AND YEAR(s.appointment_datetime) = :ano
        GROUP BY semana
        ORDER BY semana
    """, nativeQuery = true)
    List<Object[]> findTaxaCancelamentoPorSemana(@Param("mes") int mes, @Param("ano") int ano);

    @Query(value = "SELECT COUNT(*) FROM schedule s WHERE MONTH(s.appointment_datetime) = :mes AND YEAR(s.appointment_datetime) = :ano AND s.status = 'COMPLETED'", nativeQuery = true)
    int findTotalAtendimentos(@Param("mes") int mes, @Param("ano") int ano);

    @Query(value = """
        SELECT sv.name AS nomeServico, COUNT(*) AS quantidade
        FROM schedule_item si
        JOIN service sv ON sv.id = si.fk_service
        JOIN schedule s ON s.id = si.fk_schedule
        WHERE MONTH(s.appointment_datetime) = :mes
          AND YEAR(s.appointment_datetime) = :ano
          AND s.status = 'COMPLETED'
        GROUP BY sv.name
        ORDER BY quantidade DESC
        """, nativeQuery = true)
    List<Object[]> findRankingServicosMaisVendidos(@Param("mes") int mes, @Param("ano") int ano);

    default List<ServiceRanking> findRankingServicos(int mes, int ano) {
        return findRankingServicosMaisVendidos(mes, ano).stream()
                .map(r -> new ServiceRanking((String) r[0], ((Number) r[1]).longValue()))
                .toList();
    }
}