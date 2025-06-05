package milla.frequency_discount_service.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "frequency_discount")
@NoArgsConstructor
@AllArgsConstructor
public class FrequencyDiscountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int frequency_discount_id;
    private String category;
    private int min_frequency;
    private int max_frequency;
    private BigDecimal discount;
}
