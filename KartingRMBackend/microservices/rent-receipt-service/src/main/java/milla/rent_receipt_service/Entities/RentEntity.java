package milla.rent_receipt_service.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@Table(name = "rent")
@NoArgsConstructor
@AllArgsConstructor
public class RentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rent_id;
    private String rent_code;
    private LocalDate rent_date;
    private LocalTime rent_time;
    private int fee_type_id;
    private int people_number;
    private String main_client;
    private BigDecimal total_price;

    public RentEntity(int rent_id, String rent_code, LocalDate rent_date, LocalTime rent_time, int people_number, int fee_type_id, String main_client) {
        this.rent_id = rent_id;
        this.rent_code = rent_code;
        this.rent_date = rent_date;
        this.rent_time = rent_time;
        this.people_number = people_number;
        this.fee_type_id = fee_type_id;
        this.main_client = main_client;
    }
}
