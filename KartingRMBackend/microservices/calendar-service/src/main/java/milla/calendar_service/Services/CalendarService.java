package milla.calendar_service.Services;

import milla.calendar_service.DTO.CalendarEvent;
import milla.calendar_service.Module.Rent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
public class CalendarService {
    @Autowired
    private RestTemplate restTemplate;

    //Funcion que recupera las rentas de la semana especificada
    public List<Rent> getRentsForWeek(LocalDate weekStartDate) {
        LocalDate weekEndDate = weekStartDate.plusDays(6); // Sunday-Saturday week
        String startDate = weekStartDate.toString();
        String endDate = weekEndDate.toString();

        Rent[] rents = restTemplate.getForObject(
                "http://rent_service/rent/getRentsBetweenDates?startDate=" + startDate + "&endDate=" + endDate,
                Rent[].class
        );
        return rents != null ? Arrays.asList(rents) : new ArrayList<>();
    }
    //Funcion que convierte listas de rentas a listas de eventos, definidos en el DTO
    public List<CalendarEvent> convertToCalendarEvents(List<Rent> rents) {
        List<CalendarEvent> events = new ArrayList<>();

        for (Rent rent : rents) {
            CalendarEvent event = new CalendarEvent();

            // Fetch duration in minutes for the current rent
            Integer duration = restTemplate.getForObject(
                    "http://rent_service/rent/getRentDurationById/" + rent.getRent_id(),
                    Integer.class
            );
            if (duration == null) {
                // Provide a default duration if the service returns null
                duration = 0; // Example: set to 0 minutes, you can adjust as needed
            }

            // Calculate end time
            Date start = rent.getRent_date(); // Assuming rent_date includes time
            Date end = new Date(start.getTime() + duration * 60 * 1000L); // Add duration in milliseconds

            // Set event properties
            event.setTitle("Rent: " + rent.getRent_code());
            event.setStart(start);
            event.setEnd(end);
            event.setClient_name(rent.getMain_client());

            // Add to events list
            events.add(event);
        }
        return events;
    }
    //Funcion que, utilizando las anteriores, obtiene una lista de eventos, segun una fecha inicial y un numero de semanas a partir de esa fecha
    //Retorna en formato mapeado para las semanas, lo que se envia a frontend
    public Map<String, List<CalendarEvent>> getCalendarEventsForWeeks(LocalDate startDate, int numberOfWeeks) {
        Map<String, List<CalendarEvent>> weeklyEventsMap = new LinkedHashMap<>();

        LocalDate currentWeekStart = startDate;
        for (int i = 0; i < numberOfWeeks; i++) {
            // Fetch rents for the current week
            List<Rent> rentsForWeek = getRentsForWeek(currentWeekStart);

            // Convert rents to calendar events
            List<CalendarEvent> eventsForWeek = convertToCalendarEvents(rentsForWeek);

            // Add to the map with a label
            String weekLabel = "Week of " + currentWeekStart.toString();
            weeklyEventsMap.put(weekLabel, eventsForWeek);

            // Move to the next week
            currentWeekStart = currentWeekStart.plusWeeks(1);
        }

        return weeklyEventsMap;
    }



}
