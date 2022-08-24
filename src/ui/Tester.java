package ui;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class Tester {
    public static void main(String[] args) {
        Date dateBefore = new Date(2022, Calendar.APRIL, 10);
        Date dateAfter = new Date(2022, Calendar.APRIL, 15);

        long dateBeforeInMs = dateBefore.getTime();
        long dateAfterInMs = dateAfter.getTime();

        long timeDiff = Math.abs(dateAfterInMs - dateBeforeInMs);

        long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
// Alternatevly:
// int daysDiff = (int) (timeDiff / (1000 * 60 * 60 * 24));
        System.out.println(" The number of days between dates: " + daysDiff);
    }
}
