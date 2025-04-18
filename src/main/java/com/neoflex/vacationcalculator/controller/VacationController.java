package com.neoflex.vacationcalculator.controller;

import com.neoflex.vacationcalculator.service.CalculationService;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam("vacationDays")
            @NotNull(message = "Количество дней отпуска обязательно")
            @Min(value =1, message = "Количество дней отпуска должно быть 1 и более")
            Integer vacationDays) {
        return calculationService.calculateVacationPay(averageSalary, vacationDays);
    }

}
