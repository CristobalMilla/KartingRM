package milla.report_service.Config.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fee_Type_Report {

    Date initial_month;
    Date final_month;
}
