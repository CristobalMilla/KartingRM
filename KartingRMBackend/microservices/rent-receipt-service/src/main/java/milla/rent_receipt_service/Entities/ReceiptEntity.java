package milla.rent_receipt_service.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "receipt")
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int receipt_id;
    @Column(name = "rent_id")
    private int rentId;
    private String sub_client;
    //Tarifa base aplicada según el número de vueltas/tiempo máximo, o día especial (fines de semana o feriados).
    private BigDecimal base_tariff;
    //Descuento por el tamaño del grupo, si corresponde.
    private BigDecimal size_discount;
    //Descuento por ser cliente frecuente o promociones especiales (como cumpleaños).
    private BigDecimal special_discount;
    //Monto final calculado después de aplicar tarifas, descuentos y promociones.
    private BigDecimal aggregated_price;
    //Valor del IVA.
    private BigDecimal iva_price;
    //Monto Total incluyendo IVA
    private BigDecimal final_price;

    public ReceiptEntity(int receipt_id, int rent_id, String sub_client) {
        this.receipt_id = receipt_id;
        this.rentId = rent_id;
        this.sub_client = sub_client;
    }
}
