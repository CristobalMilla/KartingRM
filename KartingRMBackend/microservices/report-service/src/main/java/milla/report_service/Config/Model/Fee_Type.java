package milla.report_service.Config.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fee_Type {
    private int fee_type_id;
    private int lap_number;
    private int max_time;
    private BigDecimal price;
    private int duration;
}