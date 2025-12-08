package sptech.school.projetoPI.core.application.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricaMensalDto {
    private List<String> labels;
    private List<Double> values;
}
