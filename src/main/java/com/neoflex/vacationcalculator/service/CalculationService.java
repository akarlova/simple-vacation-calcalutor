package com.neoflex.vacationcalculator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class CalculationService {

    private final static double AVERAGE_DAYS_IN_MONTH = 29.3;
    private HolidayService holidayService;

    public double calculateVacationPay(double averageSalary, int vacationDays) {
        //getting daily salary:
        double dailySalary = averageSalary / AVERAGE_DAYS_IN_MONTH;
        double result = dailySalary * vacationDays;
        BigDecimal bd = BigDecimal.valueOf(result);
        return bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public double calculateVacationPay(double averageSalary, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Начало отпуска не может быть раньше его окончания");
        }
        holidayService = new HolidayService();

        Stream<LocalDate> vacationPeriod = startDate.datesUntil(endDate.plusDays(1));

        long paidDays = vacationPeriod
                .filter(d -> !holidayService.isHoliday(d))
                .count();
        return calculateVacationPay(averageSalary, (int) paidDays);
    }


}
