package com.example.aplicatiemanagementfilme.util;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private static final String FORMAT_DATE = "yyyy-mm-dd";
    private final SimpleDateFormat formatter;

    public DateConverter() {
        formatter = new SimpleDateFormat(FORMAT_DATE, Locale.US);
    }

    @TypeConverter
    public Date toDate(String value) {
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    @TypeConverter
    public String toString(Date value) {
        if (value == null) {
            return null;
        }
        return formatter.format(value);
    }
}
