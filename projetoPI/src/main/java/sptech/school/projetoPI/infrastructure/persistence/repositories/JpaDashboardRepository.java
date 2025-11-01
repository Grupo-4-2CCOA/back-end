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

    @Query(value = """
        SELECT COALESCE(SUM(si.final_price - si.discount), 0)
        FROM schedule s
        JOIN schedule_item si ON si.fk_schedule = s.id
        WHERE MONTH(s.appointment_datetime) = :mes
          AND YEAR(s.appointment_datetime) = :ano
          AND s.status = 'COMPLETED'
        """, nativeQuery = true)
    double findRendimentoTotal(@Param("mes") int mes, @Param("ano") int ano);

    @Query(value = """
        SELECT (COUNT(CASE WHEN s.status = 'CANCELED' THEN 1 END) * 100.0 / COUNT(*))
        FROM schedule s
        WHERE MONTH(s.appointment_datetime) = :mes AND YEAR(s.appointment_datetime) = :ano
        """, nativeQuery = true)
    double findTaxaCancelamento(@Param("mes") int mes, @Param("ano") int ano);

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