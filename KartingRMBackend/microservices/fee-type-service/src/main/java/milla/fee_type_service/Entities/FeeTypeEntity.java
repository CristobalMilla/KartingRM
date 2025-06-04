package milla.fee_type_service.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "fee_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeeTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fee_type_id;
    private int lap_number;
    private int max_time;
    private BigDecimal price;
    private int duration;
}
