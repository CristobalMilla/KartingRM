package milla.people_discount_service.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "people_discount")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeopleDiscountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int people_discount_id;
    private int min_people;
    private int max_people;
    private BigDecimal discount;
}
