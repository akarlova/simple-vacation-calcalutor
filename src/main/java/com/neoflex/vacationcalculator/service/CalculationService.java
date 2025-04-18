package com.neoflex.vacationcalculator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationService {

    private final static double AVERAGE_DAYS_IN_MONTH = 29.3;

    public double calculateVacationPay(double averageSalary, int vacationDays) {
        //getting daily salary:
        double dailySalary = averageSalary / AVERAGE_DAYS_IN_MONTH;
        double result = dailySalary * vacationDays;
        BigDecimal bd = BigDecimal.valueOf(result);
        return bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
