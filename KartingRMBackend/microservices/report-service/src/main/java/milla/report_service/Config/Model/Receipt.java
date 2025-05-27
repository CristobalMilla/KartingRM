package milla.report_service.Config.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    private int receipt_id;
    private int rent_id;
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
}
