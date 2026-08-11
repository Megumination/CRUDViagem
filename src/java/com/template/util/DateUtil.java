package com.template.util;

import java.sql.Date;
import java.time.LocalDate;

public class DateUtil {

    public static Date converterParaDate(LocalDate data) {

        if (data == null) {
            return null;
        }

        return Date.valueOf(data);
    }

    public static LocalDate converterParaLocalDate(Date data) {

        if (data == null) {
            return null;
        }

        return data.toLocalDate();
    }
}