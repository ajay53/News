package com.example.news.utility;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String DateTimeFormatter(String s) {
        String dateStr = "";
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date date = dateFormat.parse("2017-04-26T20:55:00.000Z");
            DateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
            dateStr = formatter.format(date);
            Log.d("TAG", "DateTimeFormatter: ");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Published At: " + dateStr;
    }

    public static String getAuthor(String author) {
        return "Published by: " + author;
    }
}
