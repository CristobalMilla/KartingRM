package milla.report_service.Config.Services;

import milla.report_service.Config.Model.People_Discount;
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
public class PeopleDiscountReportService {

    @Autowired
    private RestTemplate restTemplate;

    private List<String> getMonthsBetween(String startMonth, String endMonth) {
        YearMonth start = YearMonth.parse(startMonth);
        YearMonth end = YearMonth.parse(endMonth);
        List<String> months = new ArrayList<>();

        while (!start.isAfter(end)) {
            months.add(start.toString());
            start = start.plusMonths(1);
        }
        return months;
    }
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
    public List<Map<String, Object>> generatePeopleDiscountReport(String startMonth, String endMonth) {
        // Fetch all people discounts
        People_Discount[] peopleDiscounts = restTemplate.getForObject("http://discount_service/peopleDiscounts", People_Discount[].class);
        if(peopleDiscounts == null){
            return null;
        }
        // Prepare months list
        List<String> months = getMonthsBetween(startMonth, endMonth);

        // Initialize report
        List<Map<String, Object>> report = new ArrayList<>();

        // Process each people discount
        for (People_Discount discount : peopleDiscounts) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("Numero de personas", discount.getMin_people() + "-" + discount.getMax_people());

            BigDecimal rowTotal = BigDecimal.ZERO;

            // Aggregate data for each month
            for (String month : months) {
                BigDecimal monthTotal = restTemplate.getForObject(
                        String.format("http://rent_service/rents/totalPriceByPeopleDiscount?month=%s&peopleDiscountId=%d",
                                month, discount.getPeople_discount_id()),
                        BigDecimal.class);

                if (monthTotal == null) {
                    monthTotal = BigDecimal.ZERO;
                }
                row.put(month, monthTotal);
                rowTotal = rowTotal.add(monthTotal);
            }

            row.put("Total", rowTotal);
            report.add(row);
        }

        // Add final row for month totals
        Map<String, Object> finalRow = calculateFinalRow(report, months);
        report.add(finalRow);

        return report;
    }
}
