package com.neoflex.vacationcalculator.controller;

import com.neoflex.vacationcalculator.service.CalculationService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Validated
public class VacationController {

    private final CalculationService calculationService = new CalculationService();

    @GetMapping("/calculate")
    public double calculate(
            @RequestParam("averageSalary")
            @NotNull(message = "Средняя заработная плата обязательна")
            @DecimalMin(value = "0.0", inclusive = false, message = "Средняя заработная плата должна быть больше нуля")
            Double averageSalary,

            //if just quantity of days passed
            @RequestParam(value = "vacationDays", required = false)
            @Min(value = 1, message = "Количество дней отпуска должно быть 1 и более")
            Integer vacationDays,

            //if a period passed
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {
        double result = 0;
        if(vacationDays != null) {
            return calculationService.calculateVacationPay(averageSalary, vacationDays);
        } else if(startDate != null && endDate != null) {
            result = calculationService.calculateVacationPay(averageSalary, startDate, endDate);
        }
        return result;
    }
}
