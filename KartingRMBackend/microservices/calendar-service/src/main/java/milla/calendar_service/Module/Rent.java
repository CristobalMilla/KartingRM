package milla.calendar_service.Module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rent {
    private int rent_id;
    private String rent_code;
    private Date rent_date;
    private LocalTime rent_time;
    private int fee_type_id;
    private int people_number;
    private String main_client;
    private BigDecimal total_price;
}
