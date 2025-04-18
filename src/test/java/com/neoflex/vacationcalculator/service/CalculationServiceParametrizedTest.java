package com.neoflex.vacationcalculator.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@ExtendWith(CalculationServiceParameterResolver.class)
class CalculationServiceParametrizedTest {

    @ParameterizedTest
    @CsvFileSource(resources = "details.csv")
    void calculateVacationPayTest(double amount, double averageSalary, int vacationDays, CalculationService service) {
        assertEquals(amount, service.calculateVacationPay(averageSalary, vacationDays));
    }
}