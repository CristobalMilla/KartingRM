package milla.special_day_fee_service.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "holiday")
@NoArgsConstructor
@AllArgsConstructor
public class HolidayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int holiday_id;
    private String name;
    private LocalDate date;
    BigDecimal discount;
}
