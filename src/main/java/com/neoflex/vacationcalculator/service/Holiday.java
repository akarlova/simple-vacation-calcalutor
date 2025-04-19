package com.neoflex.vacationcalculator.service;

import java.time.LocalDate;

public interface Holiday {

    /**
     * @return true, если дата - государственный праздник
     */
    public boolean isHoliday(LocalDate date);
}
