package com.neoflex.vacationcalculator.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;


@ExtendWith(CalculationServiceParameterResolver.class)
class CalculationServiceParametrizedTest {

    @ParameterizedTest
    @CsvFileSource(resources = "details.csv")
    @DisplayName("Test for vacation payment if just quantity of vacation dates are given")
    void calculateVacationPayTest(double amount, double averageSalary, int vacationDays, CalculationService service) {
        assertEquals(amount, service.calculateVacationPay(averageSalary, vacationDays));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "testDateCasesWithoutHolidays.csv", numLinesToSkip = 1)
    @DisplayName("Test for period that does not include holidays")
    void calculateVacationPayForInputPeriodWithoutHolidaysTest(double amount, double averageSalary, String startDateOfVacation, String endDateOfVacation, CalculationService service) {
        LocalDate startDate = LocalDate.parse(startDateOfVacation);
        LocalDate endDate = LocalDate.parse(endDateOfVacation);
        assertEquals(amount, service.calculateVacationPay(averageSalary, startDate, endDate));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "testDateCasesWithHolidays.csv", numLinesToSkip = 1)
    @DisplayName("Test for period that includes holidays")
    void calculateVacationPayForInputPeriodWithHolidaysTest(double amount, double averageSalary, String startDateOfVacation, String endDateOfVacation, CalculationService service) {
        LocalDate startDate = LocalDate.parse(startDateOfVacation);
        LocalDate endDate = LocalDate.parse(endDateOfVacation);
        assertEquals(amount, service.calculateVacationPay(averageSalary, startDate, endDate));
    }

    @ParameterizedTest
    @MethodSource("invalidDatesTestCases")
    @DisplayName("Test for input when a start date of vacation is more than an end date")
    void calculateVacationPayWithInvalidDatesTest(double averageSalary, String startDateOfVacation, String endDateOfVacation, CalculationService service) {
        LocalDate startDate = LocalDate.parse(startDateOfVacation);
        LocalDate endDate = LocalDate.parse(endDateOfVacation);
        assertThrows(IllegalArgumentException.class, () -> {service.calculateVacationPay(averageSalary, startDate, endDate);});
    }
     static Stream<Arguments> invalidDatesTestCases() {
        return Stream.of(
                Arguments.of(1000.00,"2025-04-12", "2025-04-01"),
                Arguments.of(1000.00,"2025-03-10", "2025-02-20"),
                Arguments.of(1000.00,"2026-01-01", "2025-12-31")
        );
     }
}