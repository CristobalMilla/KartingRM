package milla.rent_receipt_service.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Frequency_Discount {
    private int frequency_discount_id;
    private String category;
    private int min_frequency;
    private int max_frequency;
    private BigDecimal discount;
}
