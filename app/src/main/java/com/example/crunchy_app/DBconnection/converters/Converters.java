package com.example.crunchy_app.DBconnection.converters;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.LocalTime;

public class Converters {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate fromTimestamp(String value) {
        return value == null ? null : LocalDate.parse(value);
    }

    @TypeConverter
    public static String dateToTimestamp(LocalDate date) {
        return date == null ? null : date.toString();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalTime fromTimeString(String value) {
        return value == null ? null : LocalTime.parse(value);
    }

    @TypeConverter
    public static String timeToString(LocalTime time) {
        return time == null ? null : time.toString(); // Formato HH:mm:ss
    }
}

