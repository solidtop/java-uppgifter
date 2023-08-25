package org.example.labb1;

public class Util {
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static String formatHours(int hourFrom, int hourTo) {
        String hourFromStr = hourFrom < 10 ? "0" + hourFrom : Integer.toString(hourFrom);
        String hourToStr = hourTo < 10 ? "0" + hourTo : Integer.toString(hourTo);
        return hourFromStr + "-" + hourToStr;
    }
}
