package milla.calendar_service.Controllers;

import milla.calendar_service.DTO.CalendarEvent;
import milla.calendar_service.Module.Rent;
import milla.calendar_service.Services.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    private CalendarService calendarService;

    //Funcion que recupera las rentas de la semana especificada
    @GetMapping("/singleWeek")
    public ResponseEntity<List<Rent>> getRentsByWeek(@RequestParam LocalDate weekStartDate) {
        List<Rent> rents = calendarService.getRentsForWeek(weekStartDate);
        if (rents == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(rents);
        }
    }
    //Funcion que convierte listas de rentas a listas de eventos, definidos en el DTO
    @GetMapping("/eventListByRentList")
    public ResponseEntity<List<CalendarEvent>> convertToCalendarEvents(@RequestParam List<Rent> rents){
        List<CalendarEvent> events = calendarService.convertToCalendarEvents(rents);
        if (events == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(events);
        }
    }
    //Funcion principal que, utilizando las anteriores, obtiene una lista de eventos, segun una fecha inicial y un numero de semanas a partir de esa fecha
    //Retorna en formato mapeado para las semanas, lo que se envia a frontend
    @GetMapping("/getEventsFromWeek")
    public ResponseEntity<Map<String, List<CalendarEvent>>> getCalendarEventsForWeeks(@RequestParam LocalDate startDate, @RequestParam int numberOfWeeks){
        Map<String, List<CalendarEvent>> weeklyEvents = calendarService.getCalendarEventsForWeeks(startDate, numberOfWeeks);
        if (weeklyEvents == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(weeklyEvents);
        }
    }
}
