package com.neoflex.vacationcalculator.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class HolidayService implements Holiday {

    private static final Set<MonthDay> HOLIDAYS;

    static {
        Set<MonthDay> dates = new HashSet<>();
        for (int day : List.of(1, 2, 3, 4, 5, 6, 7, 8)) {
            dates.add(MonthDay.of(Month.JANUARY, day));
        }
        dates.add(MonthDay.of(Month.FEBRUARY, 23));
        dates.add(MonthDay.of(Month.MARCH, 8));
        dates.add(MonthDay.of(Month.MAY, 1));
        dates.add(MonthDay.of(Month.MAY, 9));
        dates.add(MonthDay.of(Month.JUNE, 12));
        dates.add(MonthDay.of(Month.NOVEMBER, 4));
        HOLIDAYS = Collections.unmodifiableSet(dates);
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        MonthDay givenDate = MonthDay.from(date);
        return HOLIDAYS.contains(givenDate);
    }
}
