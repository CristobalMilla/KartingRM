package milla.calendar_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEvent {
    private String title;        // Event title
    private Date start;        // Start timestamp (e.g., "2025-05-23T10:30:00")
    private Date end;          // End timestamp (e.g., "2025-05-23T12:30:00")
    private String client_name;
}
