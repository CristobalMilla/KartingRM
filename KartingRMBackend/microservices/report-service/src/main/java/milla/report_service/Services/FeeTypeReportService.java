package milla.report_service.Services;

import milla.report_service.Model.Fee_Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeeTypeReportService {

    @Autowired
    private RestTemplate restTemplate;

    //Primera funcion que obtiene una lista de meses entre 2 meses ingresados
    //Funciona con años
    private List<String> getMonthsBetween(String startMonth, String endMonth) {
        // Example implementation to get all months between two dates
        YearMonth start = YearMonth.parse(startMonth);
        YearMonth end = YearMonth.parse(endMonth);
        List<String> months = new ArrayList<>();

        while (!start.isAfter(end)) {
            months.add(start.toString());
            start = start.plusMonths(1);
        }
        return months;
    }
    //Funcion que calcula la suma por mes y la suma total final
    private Map<String, Object> calculateFinalRow(List<Map<String, Object>> report, List<String> months) {
        Map<String, Object> finalRow = new LinkedHashMap<>();
        finalRow.put("Description", "Total General");

        BigDecimal grandTotal = BigDecimal.ZERO;

        for (String month : months) {
            BigDecimal monthTotal = BigDecimal.ZERO;

            for (Map<String, Object> row : report) {
                monthTotal = monthTotal.add((BigDecimal) row.get(month));
            }

            finalRow.put(month, monthTotal);
            grandTotal = grandTotal.add(monthTotal);
        }

        finalRow.put("Total", grandTotal);
        return finalRow;
    }
    //Funcion que, utilizando las anteriores, genera el la tabla reporte segun los tipos de tarifas
    public List<Map<String, Object>> generateFeeTypeReport(String startMonth, String endMonth) {
        // Fetch all fee types
        Fee_Type[] feeTypes = restTemplate.getForObject("http://fee_type_service/feeTypes", Fee_Type[].class);
        if(feeTypes == null){
            return null;
        }

        // Prepare month list
        List<String> months = getMonthsBetween(startMonth, endMonth);

        // Initialize report
        List<Map<String, Object>> report = new ArrayList<>();

        // Process each fee type
        for (Fee_Type feeType : feeTypes) {
            Map<String, Object> row = new LinkedHashMap<>();
            //Se extrae del fee_type su numero de vueltas y tiempo maximo, colocando en la primera celda de la fila
            row.put("Description", String.format("Numero de vueltas: %d, Tiempo maximo permitido: %s",
                    feeType.getLap_number(), feeType.getMax_time()));

            BigDecimal rowTotal = BigDecimal.ZERO;
            //Para cada mes en la lista de meses, extraer:
            // Aggregate data for each month
            for (String month : months) {
                //Utilizando restTemplate, extraer todos los precios_finales de todas las rentas que se hayan hecho en ese mes, ya sumadas
                BigDecimal monthTotal = restTemplate.getForObject(
                        String.format("http://rent_service/rents/totalPriceByFeeType?month=%s&feeTypeId=%d",
                                month, feeType.getFee_type_id()),
                        BigDecimal.class);

                if (monthTotal == null) {
                    monthTotal = BigDecimal.ZERO;
                }
                row.put(month, monthTotal);
                rowTotal = rowTotal.add(monthTotal);
            }
            //RowTotal siendo el total de cada fee_type
            row.put("Total", rowTotal);
            report.add(row);
        }
        //Finalmente, añadir la fina de totales por mes, y total final
        // Add final row for month totals
        Map<String, Object> finalRow = calculateFinalRow(report, months);
        report.add(finalRow);

        return report;
    }




}
