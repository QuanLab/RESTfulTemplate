package com.vccorp.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateUtils {
    /**
     * get current date as String format yyyy-MM-dd
     * @return
     */
    public static String getCurrentDateString() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * validate date in string format
     * @param dateString
     * @return
     */
    public static boolean validateDateString(String dateString){
        if (dateString==null) {
            return false;
        }
        Pattern p = Pattern.compile("([2][0-9]{3})-([0][0-9]|[1][0-2])-([0-2][0-9]|[3][0-1])");
        Matcher matcher = p.matcher(dateString);
        if (matcher.find())
            return true;
        return false;
    }


    /**
     * get list date between two date format "yyyy-MM-dd
     * @param start
     * @param end
     * @return
     */
    public static List<String> getDaysBetweenDates(String start, String end) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dates = new ArrayList<>();
        try{
            java.util.Date  startDate = df.parse(start);
            java.util.Date  endDate = df.parse(end);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(startDate);

            while (calendar.getTime().before(endDate)) {
                String result = df.format(calendar.getTime());
                dates.add(result);
                calendar.add(Calendar.DATE, 1);
            }
            dates.add(end);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dates;
    }


    /**
     * get start week from current date
     * @return date at string format yyyy-MM-dd
     */
    public static String getStartOfWeek() {

        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -6);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * get yesterday of current date
     * @return date at string format yyyy-MM-dd
     */
    public static String getYesterday() {

        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(calendar.getTime());
    }

    /**
     * get next date of date input
     * @param date format with yyy-DD-mm
     * @return
     */
    public static String getNextDate(String date) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(df.parse(date));
        calendar.add(Calendar.DATE, 1);

        return df.format(calendar.getTime());

    }
}
