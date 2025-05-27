package milla.report_service.Config.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class People_Discount {
    private int people_discount_id;
    private int min_people;
    private int max_people;
    private BigDecimal discount;
}
