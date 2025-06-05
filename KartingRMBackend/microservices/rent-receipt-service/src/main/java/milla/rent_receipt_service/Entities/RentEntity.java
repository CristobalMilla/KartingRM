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
    @Column(name = "rent_date")
    private LocalDate rentDate;
    private LocalTime rent_time;
    private int fee_type_id;
    private int people_number;
    @Column(name = "main_client")
    private String mainClient;
    private BigDecimal total_price;

    public RentEntity(int rent_id, String rent_code, LocalDate rent_date, LocalTime rent_time, int people_number, int fee_type_id, String main_client) {
        this.rent_id = rent_id;
        this.rent_code = rent_code;
        this.rentDate = rent_date;
        this.rent_time = rent_time;
        this.people_number = people_number;
        this.fee_type_id = fee_type_id;
        this.mainClient = main_client;
    }
}
