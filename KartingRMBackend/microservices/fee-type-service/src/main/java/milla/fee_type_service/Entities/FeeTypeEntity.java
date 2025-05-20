package milla.fee_type_service.Entities;

<<<<<<< HEAD

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

=======
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


>>>>>>> 62bdd90 (FeeType sin eureka)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeeTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
    private long fee_type_id;
=======
    private int fee_type_id;
>>>>>>> 62bdd90 (FeeType sin eureka)
    private int lap_number;
    private int max_time;
    private BigDecimal price;
    private int duration;
<<<<<<< HEAD

=======
>>>>>>> 62bdd90 (FeeType sin eureka)
}
