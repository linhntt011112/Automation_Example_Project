package Utility;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtility {

    /**
     * Select date from DatePicker
     * @param month, year, day: month, year, day selected
     * @param select_year, select_month: WebElement of select Month, Year
     * @param columnsDay: columns of Day table
     */
    public static void selectDate(String month, String year, String day, WebElement select_year, WebElement select_month, List<WebElement> columnsDay) {
        Select selectYear = new Select(select_year);
        selectYear.selectByVisibleText(year);
        Select selectMonth = new Select(select_month);
        selectMonth.selectByVisibleText(month);
        for (WebElement cell : columnsDay) {
            if (cell.getText().equals(day)){
                cell.findElement(By.linkText(day)).click();
                break;
            }
        }
    }

    /**
     * Check date format MMM/DD/YYYY || MMM.DD.YYYY || MMM-DD-YYYY || MM/DD/YYYY || MM-DD-YYYY || MM.DD.YYYY
     * @return
     */
    public static boolean checkDateFormatMDY(WebElement inputDay) {
        Pattern pattern = Pattern.compile("^(?:(?:(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec))(\\/|-|\\.)31)\\1|(?:(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))(\\/|-|\\.)(?:29|30)\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:(?:0?2|(?:Feb))(\\/|-|\\.)(?:29)\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))(\\/|-|\\.)(?:0?[1-9]|1\\d|2[0-8])\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputDay.getText());
        return matcher.find();
    }

    /**
     * Check date format DD/MM/YY
     * @return
     */
    public static boolean checkDateFormatDDMMYY(WebElement inputDay) {
        Pattern pattern = Pattern.compile("^[0-3][0-9]/[0-3][0-9]/(?:[0-9][0-9])?[0-9][0-9]$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputDay.getText());
        return matcher.find();
    }

    /**
     * Get current date
     * @param desiredDateFormat: date format String
     * @return
     */
    public static String getCurrentDate(String desiredDateFormat) {
        String dateString = null;
        try {
            LocalDate date = LocalDate.now();
            DateTimeFormatter format = DateTimeFormat.forPattern(desiredDateFormat);
            dateString = format.print(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    /**
     * Get current timestamp
     * @param desiredDateTimeFormat: datetime format string
     * @return
     */
    public static String getCurrentTimestamp(String desiredDateTimeFormat){
        String timeStamp = null;
        try {
            timeStamp = new SimpleDateFormat(desiredDateTimeFormat, Locale.JAPAN).format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    /**
     * Get the difference between two timeStamps in format: 8 Min. 15 Sec.
     * @param startDateTime
     * @param endDateTime
     * @param format_DateTime
     * @return
     * @throws ParseException
     */
    public static String getDateTimeDifference(String startDateTime, String endDateTime, String format_DateTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(format_DateTime);

        Date d1 = null;
        Date d2 = null;

        int min = 0;
        int sec = 0;

        d1 = format.parse(startDateTime);
        d2 = format.parse(endDateTime);

        DateTime dt1 = new DateTime(d1);
        DateTime dt2 = new DateTime(d2);

        min = Minutes.minutesBetween(dt1, dt2).getMinutes()%60;
        sec = Seconds.secondsBetween(dt1, dt2).getSeconds()%60;
        return min + " Min." + " " + sec + " Sec.";
    }
}