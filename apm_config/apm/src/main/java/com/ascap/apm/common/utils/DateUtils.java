package com.ascap.apm.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import com.ascap.apm.common.helpers.LogHelper;

/**
 * Contains small utility functions related to Dates, time and durations.
 */
public class DateUtils {

    /**
     * Constants defining the Quarters of a year
     * 
     * @author Jaya Shyam Narayana Lingamchetty
     */
    public static final int QTR_FIRST = 1;

    public static final int QTR_SECOND = 2;

    public static final int QTR_THIRD = 3;

    public static final int QTR_FOURTH = 4;

    /**
     * Constants defining the Quarters of a year as a String with a left padded zero
     * 
     * @author Saravanan Sengottaiyan
     */
    public static final String QTR_ZERO_ONE = "01";

    public static final String QTR_ZERO_TWO = "02";

    public static final String QTR_ZERO_THREE = "03";

    public static final String QTR_ZERO_FOUR = "04";

    public static final String MM_DD_YYYY = "MM/dd/yyyy";

    /**
     * Constants Defining the Duration
     * 
     * @author Jaya Shyam Narayana Lingamchetty
     */
    public static final int DUR_UNIT_SECONDS = 2;

    /**
     * Constants defining the Months
     * 
     * @author Jaya Shyam Narayana Lingamchetty
     */
    public static final int MONTH_JANUARY = 1;

    public static final int MONTH_FEBRUARY = 2;

    public static final int MONTH_MARCH = 3;

    public static final int MONTH_APRIL = 4;

    public static final int MONTH_MAY = 5;

    public static final int MONTH_JUNE = 6;

    public static final int MONTH_JULY = 7;

    public static final int MONTH_AUGUST = 8;

    public static final int MONTH_SEPTEMBER = 9;

    public static final int MONTH_OCTOBER = 10;

    public static final int MONTH_NOVEMBER = 11;

    public static final int MONTH_DECEMBER = 12;

    /**
     * added for convenience the indexes to the longMonthNames are the MONTH_JANUARY etc.
     * 
     * @author Jaya Shyam Narayana Lingamchetty
     */
    protected static final String[] longMonthNames = new String[] {"", "January", "February", "March", "April", "May",
    "June", "July", "August", "September", "October", "November", "December"};

    protected LogHelper log = new LogHelper(this.getClass().getName());

    /**
     * Method isDateInYear checks if the dateToCheck falls in the year. <b>Please note that nulls are treated as unknown
     * and returns false.</b>
     * 
     * @param dateToCheck
     * @param year
     * @return boolean
     * @author Jaya Shyam Narayana Lingamchetty
     */
    public static final boolean isDateInYear(Calendar dateToCheck, int year) {
        if (dateToCheck == null) {
            return false;
        } else {
            return (year == dateToCheck.get(Calendar.YEAR));
        }
    }

    /**
     * Method getQuarter.
     * 
     * @param dateToCheck
     * @return int
     */
    public static final int getQuarter(Calendar dateToCheck) {
        if (dateToCheck == null) {
            return 0;
        } else {
            int monthOfTheDate;
            // though it is easier to just divide the month and know the quarter this
            // implementation may be safer
            monthOfTheDate = dateToCheck.get(Calendar.MONTH);

            if (Calendar.JANUARY <= monthOfTheDate && Calendar.MARCH >= monthOfTheDate) {
                return DateUtils.QTR_FIRST;
            }

            if (Calendar.APRIL <= monthOfTheDate && Calendar.JUNE >= monthOfTheDate) {
                return DateUtils.QTR_SECOND;
            }

            if (Calendar.JULY <= monthOfTheDate && Calendar.SEPTEMBER >= monthOfTheDate) {
                return DateUtils.QTR_THIRD;
            }

            if (Calendar.OCTOBER <= monthOfTheDate && Calendar.DECEMBER >= monthOfTheDate) {
                return DateUtils.QTR_FOURTH;
            }
        }
        return 0;
    }

    public static final String getPerformanceYearQuarter(String date) {
        String performanceYearQuarter = "";
        if (date == null || date.trim().equals("")) {
            return "";
        }
        try {
            Calendar dateToCheck = getCalendar(date);

            int yearOfTheDate = 0;
            int quarterOfTheDate;
            if (dateToCheck != null)
                yearOfTheDate = dateToCheck.get(Calendar.YEAR);
            quarterOfTheDate = getQuarter(dateToCheck);

            if (quarterOfTheDate != 0) {
                switch (quarterOfTheDate) {
                    case DateUtils.QTR_FIRST:
                        quarterOfTheDate = 1;
                        break;
                    case DateUtils.QTR_SECOND:
                        quarterOfTheDate = 2;
                        break;
                    case DateUtils.QTR_THIRD:
                        quarterOfTheDate = 3;
                        break;
                    case DateUtils.QTR_FOURTH:
                        quarterOfTheDate = 4;
                        break;
                    default:
                }
            }
            performanceYearQuarter = "" + yearOfTheDate + " Q" + quarterOfTheDate;
        } catch (Exception e) {
            return "";
        }
        return performanceYearQuarter;
    }

    public static final String getDistributionYearQuarter(String date) {
        // Just replace Q with D after evaluating Performace Quarter
        String distributionYearQuarter = "";
        distributionYearQuarter = getPerformanceYearQuarter(date);
        if (!distributionYearQuarter.equals("") && distributionYearQuarter.indexOf('Q') != -1) {
            distributionYearQuarter = distributionYearQuarter.replace('Q', 'D');
        }
        return distributionYearQuarter;
    }

    public static final String getPerformanceYearQuarterBacktoTwoQtrs(String date) {
        String performanceYearQuarter = "";
        if (date == null || date.trim().equals("")) {
            return "";
        }
        try {
            Calendar dateToCheck = getCalendar(date);

            int yearOfTheDate = 0;
            int quarterOfTheDate;
            if (dateToCheck != null)
                yearOfTheDate = dateToCheck.get(Calendar.YEAR);
            quarterOfTheDate = getQuarter(dateToCheck);

            if (quarterOfTheDate != 0) {
                switch (quarterOfTheDate) {
                    case DateUtils.QTR_FIRST:
                        quarterOfTheDate = 3;
                        yearOfTheDate = yearOfTheDate - 1;
                        break;
                    case DateUtils.QTR_SECOND:
                        quarterOfTheDate = 4;
                        yearOfTheDate = yearOfTheDate - 1;
                        break;
                    case DateUtils.QTR_THIRD:
                        quarterOfTheDate = 1;
                        break;
                    case DateUtils.QTR_FOURTH:
                        quarterOfTheDate = 2;
                        break;
                    default:
                }
            }
            performanceYearQuarter = "" + yearOfTheDate + " Q" + quarterOfTheDate;
        } catch (Exception e) {
            return "";
        }
        return performanceYearQuarter;
    }

    /**
     * Method isLeapYear checks if the dateToCheck falls in the quarter. <b>Please note that nulls are treated as
     * unknown and returns false.</b>
     * 
     * @param int Year
     * @return boolean Checks for the Year was a Leap Year
     */

    public static boolean isValidTime(String time) {
        boolean isValid = true;
        int hour = 0;
        int min = 0;
        int sec = 0;
        try {
            hour = Integer.parseInt(time.substring(0, time.indexOf(':')));
            min = Integer.parseInt(time.substring(time.indexOf(':') + 1, time.lastIndexOf(':') - 1));
            sec = Integer.parseInt(time.substring(time.lastIndexOf(':') + 1, time.length()));
        } catch (Exception e) {
            return false;
        }

        if ((hour > 12 || hour < 0))
            isValid = false;
        if (min > 60 || min < 0)
            isValid = false;

        if (sec > 60 || sec < 0)
            isValid = false;

        return isValid;
    }

    /**
     * Method getDateStamp converts the String Date and String Time Object into a Calendar Object
     * 
     * @param date - "MM/DD/YYYY"
     * @param time - "HH:MM"
     * @param timePeriod -"AM / PM "
     * @return Calendar if the Parameter Provided are valid , else returns Null if the Incoming parameters are not a
     *         Valid Date
     */

    public static Calendar getDateStamp(String date, String time, String timePeriod) {
        Calendar dateStamp;
        int hour = 0;
        int min = 0;
        int sec = 0;
        int month = 0;
        int day = 0;
        int year = 0;

        try {
            day = Integer.parseInt(date.substring(date.indexOf('/') + 1, date.lastIndexOf('/')));
            month = Integer.parseInt(date.substring(0, date.indexOf('/')));
            year = Integer.parseInt(date.substring(date.lastIndexOf('/') + 1, date.length()));
            hour = Integer.parseInt(time.substring(0, time.indexOf(':')));
            min = Integer.parseInt(time.substring(time.indexOf(':') + 1, time.lastIndexOf(':') - 1));

            sec = Integer.parseInt(time.substring(time.lastIndexOf(':') + 1, time.length()));
            if ("PM".equals(timePeriod)) {
                hour = hour + 12;
            }
            dateStamp = new GregorianCalendar();
            dateStamp.set(year, month, day, hour, min, sec);

        } catch (Exception e) {
            dateStamp = null;
        }
        return dateStamp;
    }

    public static String getASCAPTimeDisplayFormat(Calendar date) {

        String hour = null;
        String min = null;
        String sec = null;

        if ((date.get(Calendar.HOUR)) < 10) {
            hour = "0" + (date.get(Calendar.HOUR));
        } else {
            hour = String.valueOf((date.get(Calendar.HOUR)));
        }
        if ((date.get(Calendar.MINUTE)) < 10) {
            min = "0" + date.get(Calendar.MINUTE);
        } else {
            min = String.valueOf(date.get(Calendar.MINUTE));
        }

        sec = String.valueOf(date.get(Calendar.SECOND));

        return hour + ":" + min + ":" + sec;

    }

    public static String getASCAPDateDisplayFormat(Calendar date) {
        String day = null;
        String month = null;
        String year = null;

        if ((date.get(Calendar.DATE) + 1) < 10) {
            day = "0" + (date.get(Calendar.DATE));
        } else {
            day = String.valueOf((date.get(Calendar.DATE)));
        }

        if ((date.get(Calendar.MONTH)) < 10) {
            month = "0" + (date.get(Calendar.MONTH));
        } else {
            month = String.valueOf(date.get(Calendar.MONTH) + 1);
        }
        year = String.valueOf(date.get(Calendar.YEAR));

        if (day.length() == 1) {
            day = "0" + day;
        }
        if (month.length() == 1) {
            month = "0" + month;
        }
        return month + "/" + day + "/" + year;
    }

    /**
     * takes the duration in long and the duration Unit being Seconds and calculates the hours:minutes:seconds and
     * returns the String.
     * 
     * @author Jaya Shyam Narayana Lingamchetty
     * @param duration duration in durationUnit
     * @param durationUnit durationUnit example DUR_UNIT_SECONDS
     * @return returns the duration String as hh:mm:ss
     * @see DateUtils#DUR_UNIT_SECONDS
     */
    public static String getDurationString(long duration, int durationUnit) {
        long chours = 0;
        long cminutes = 0;
        long cseconds = 0;
        switch (durationUnit) {
            case DateUtils.DUR_UNIT_SECONDS:
                chours = duration / (60L * 60L);
                duration = duration - (chours * 60L * 60L);
                cminutes = duration / 60L;
                duration = duration - (cminutes * 60L);
                cseconds = duration % 60L;
                break;

        }
        return Long.toString(chours) + ":" + Long.toString(cminutes) + ":" + Long.toString(cseconds);
    }

    /**
     * validates the month. Month validation is done against the DateUtils.MONTH_JANUARY to DateUtils.MONTH_DECEMBER.
     * Returns true if monthToValidate is valid, and false if the monthToValidate in invalid.
     * 
     * @author Jaya Shyam Narayana Lingamchetty
     * @param monthToValidate month to be validated
     * @return boolean true is returned on valid month and false on invalid month.
     * @see #MONTH_JANUARY
     */
    public static boolean isValidMonth(int monthToValidate) {
        if (monthToValidate >= DateUtils.MONTH_JANUARY && monthToValidate <= DateUtils.MONTH_DECEMBER) {
            return (true);
        } else {
            return (false);
        }
    }

    /**
     * This method will convert Calender Date to String format format is yyyy-mm-dd
     * 
     * @param Calendar date
     * @return String
     */

    private static String getASCAPDateFormat(Calendar date) {
        String day = null;
        String month = null;
        String year = null;

        day = String.valueOf((date.get(Calendar.DATE)));
        month = String.valueOf(date.get(Calendar.MONTH) + 1);
        year = String.valueOf(date.get(Calendar.YEAR));

        return year + "-" + month + "-" + day;
    }

    /*
     * This method will convert Calender date to SQL date
     * @param Calendar date
     * @return java.sql.Date
     */
    public static java.sql.Date getSQLDate(Calendar date) {
        String dt = getASCAPDateFormat(date);

        return java.sql.Date.valueOf(dt);
    }

    /*
     * This method will convert Calender date to SQL date
     * @return java.sql.Date
     */
    public static java.sql.Date getSystemDate() {

        Calendar sysDate = Calendar.getInstance();
        String dt = getASCAPDateFormat(sysDate);
        return java.sql.Date.valueOf(dt);
    }

    /**
     * this method returns a Date object from a String Object
     */
    public Date stringToDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat(MM_DD_YYYY);
        Date dateOutput = null;
        try {
            dateOutput = df.parse(date);
        } catch (Exception pe) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                dateOutput = df.parse(date);
            } catch (Exception e1) {
                // TO DO handle exception
            }
        }

        return dateOutput;
    }

    /**
     * Left pad a single digit integer quarter of an year with a zero and return it as a String
     * 
     * @author Saravanan Sengottaiyan
     * @param quarter Quarter of the Year
     * @return String the zero padded quarter
     */
    public static String leftpadQuarterOfYear(int quarter) {
        String paddedQuarter = null;
        if (quarter == QTR_FIRST) {
            paddedQuarter = QTR_ZERO_ONE;
        } else if (quarter == QTR_SECOND) {
            paddedQuarter = QTR_ZERO_TWO;
        } else if (quarter == QTR_THIRD) {
            paddedQuarter = QTR_ZERO_THREE;
        } else if (quarter == QTR_FOURTH) {
            paddedQuarter = QTR_ZERO_FOUR;
        }
        return paddedQuarter;
    }

    public static Calendar getCalendar(String date) {

        Calendar calendarDate;
        try {

            if (date == null) {
                return new GregorianCalendar();
            }

            StringTokenizer st = new StringTokenizer(date, "/");
            int month = Integer.parseInt(st.nextToken());
            int day = Integer.parseInt(st.nextToken());
            int year = Integer.parseInt(st.nextToken());

            calendarDate = new GregorianCalendar();
            calendarDate.clear();
            calendarDate.set(year, month - 1, day);
        } catch (Exception e) {
            calendarDate = null;
        }
        return calendarDate;
    }

    public static boolean isEarlier(String strDate, String edDate) {

        boolean isEarlier = false;

        Calendar startDate = getCalendar(strDate);
        Calendar endDate = getCalendar(edDate);

        // if end date is null, considers as later date

        if ((endDate == null) || (startDate != null && startDate.before(endDate))) {
            isEarlier = true;
        } else {
            isEarlier = false;
        }

        return isEarlier;
    }

    public static boolean isEarlierOrEqual(String strDate, String edDate) {

        boolean isEarlier = false;

        Calendar startDate = getCalendar(strDate);
        Calendar endDate = getCalendar(edDate);

        // if end date is null, considers as later date

        if ((endDate == null) || (startDate != null && startDate.before(endDate))
            || (startDate != null && startDate.equals(endDate))) {
            isEarlier = true;
        } else {
            isEarlier = false;
        }

        return isEarlier;
    }

    public static boolean isEarlier(String strDate) {
        boolean isEarlier = false;

        Calendar startDate = getCalendar(strDate);
        Calendar currentDate = getCalendar(getSysDateInASCAPFormat());

        // if end date is null, hold status is checked based on the start date
        if ((startDate != null && startDate.before(currentDate))) {
            isEarlier = true;
        } else {
            isEarlier = false;
        }

        return isEarlier;
    }

    public static String getLeastDate(String date1, String date2) {
        Calendar calDate1 = getCalendar(date1);
        Calendar calDate2 = getCalendar(date2);

        if (calDate1 != null && calDate1.before(calDate2)) {
            return date1;
        } else {
            return date2;
        }
    }

    public static String getSysDateInASCAPFormat() {
        SimpleDateFormat simpleDtFormat;
        Calendar tempCalendar;
        simpleDtFormat = new SimpleDateFormat(MM_DD_YYYY);
        tempCalendar = Calendar.getInstance();
        return simpleDtFormat.format(tempCalendar.getTime());

    }

    /**
     * This method will convert Calender Date to String format format is MM/dd/yyyy
     * 
     * @param Calendar date
     * @return String
     */

    public static String getSysDateAndTimeInASCAPFormat() {
        SimpleDateFormat simpleDtFormat;
        Calendar tempCalendar;
        simpleDtFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        tempCalendar = Calendar.getInstance();
        return simpleDtFormat.format(tempCalendar.getTime());

    }

    public static boolean isDate1BeforeDate2(String date1, String date2) {
        SimpleDateFormat simpleDateFormat = null;
        simpleDateFormat = new SimpleDateFormat(MM_DD_YYYY);
        simpleDateFormat.setLenient(false);
        boolean isEndDateAfterOrEqual = false;
        try {
            if (simpleDateFormat.parse(date1).before(simpleDateFormat.parse(date2))) {
                isEndDateAfterOrEqual = true;
            }
        } catch (ParseException pse) {
            // TO DO handle exception
        }
        return isEndDateAfterOrEqual;
    }

    public static long getDateAsNumber(String stringDate) {

        int indexOfFirstSlash = stringDate.indexOf('/');
        int indexOfNextSlash = stringDate.indexOf('/', indexOfFirstSlash + 1);

        long longDate = -2;
        // conversion process
        String year = stringDate.substring(indexOfNextSlash + 1, stringDate.length());
        String month = stringDate.substring(0, indexOfFirstSlash);
        String date = stringDate.substring(indexOfFirstSlash + 1, indexOfNextSlash);

        try {
            longDate = Long.parseLong(year + month + date);
        } catch (Exception e) {
            // TO DO handle exception
        }

        return longDate;
    }

    public static String getFormattedDateFromNumber(String stringDate) {

        String formattedDate = null;
        if (stringDate != null && stringDate.length() > 0) {
            String year = null;
            String month = null;
            String day = null;

            year = stringDate.substring(0, 4);
            month = stringDate.substring(4, 6);
            day = stringDate.substring(6, 8);

            formattedDate = month + "/" + day + "/" + year;
        }
        return formattedDate;
    }

    public static final int getQuarterNumber(Date date) {
        int quarter = 0;

        if (date == null) {
            return quarter;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        quarter = (calendar.get(Calendar.MONTH) + 3) / 3;

        return quarter;
    }

    public static final int getQuarterNumber(String date) {
        int quarter = 0;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(MM_DD_YYYY);
        try {
            Date processDate = sdf.parse(date);
            quarter = getQuarterNumber(processDate);
        } catch (ParseException pe) {
            quarter = 0;
        }
        return quarter;
    }

    public static final Date getQuarterBegin(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 0 - (cal.get(Calendar.MONTH) % 3));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static final Date getQuarterBegin(int quarter, int year) {
        if (quarter == 0 || year == 0) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.YEAR, year);

        if (quarter == 1) {
            cal.set(Calendar.MONTH, Calendar.JANUARY);
        } else if (quarter == 2) {
            cal.set(Calendar.MONTH, Calendar.APRIL);
        } else if (quarter == 3) {
            cal.set(Calendar.MONTH, Calendar.JULY);
        } else if (quarter == 4) {
            cal.set(Calendar.MONTH, Calendar.OCTOBER);
        }
        return cal.getTime();
    }

    public static final Date getQuarterBegin(String date) {
        if (date == null) {
            return null;
        }
        Date retDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(MM_DD_YYYY);
        try {
            Date processDate = sdf.parse(date);
            retDate = getQuarterBegin(processDate);
        } catch (ParseException pe) {
            // TO DO handle exception
        }
        return retDate;
    }

    public static final Date getQuarterEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 2 - (cal.get(Calendar.MONTH) % 3));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    public static final Date getQuarterEnd(int quarter, int year) {
        if (quarter == 0 || year == 0) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.YEAR, year);

        if (quarter == 1) {
            cal.set(Calendar.MONTH, Calendar.MARCH);
        } else if (quarter == 2) {
            cal.set(Calendar.MONTH, Calendar.JUNE);
        } else if (quarter == 3) {
            cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        } else if (quarter == 4) {
            cal.set(Calendar.MONTH, Calendar.DECEMBER);
        }
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    public static final Date getQuarterEnd(String date) {
        if (date == null) {
            return null;
        }
        Date retDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(MM_DD_YYYY);
        try {
            Date processDate = sdf.parse(date);
            retDate = getQuarterEnd(processDate);
        } catch (ParseException pe) {
            // TO DO handle exception
        }
        return retDate;
    }

    public static final Date getNextQuarterBegin(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 3 - (cal.get(Calendar.MONTH) % 3));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static final Date getNextQuarterBegin(String date) {
        if (date == null) {
            return null;
        }
        Date retDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(MM_DD_YYYY);
        try {
            Date processDate = sdf.parse(date);
            retDate = getNextQuarterBegin(processDate);
        } catch (ParseException pe) {
            // TO DO handle exception
        }
        return retDate;
    }

    public static final Date getNextQuarterEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 5 - (cal.get(Calendar.MONTH) % 3));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    public static final Date getNextQuarterEnd(String date) {
        if (date == null) {
            return null;
        }
        Date retDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(MM_DD_YYYY);
        try {
            Date processDate = sdf.parse(date);
            retDate = getNextQuarterEnd(processDate);
        } catch (ParseException pe) {
            // TO DO handle exception
        }
        return retDate;
    }

    public static final Date getPreviousQuarterBegin(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -3 - (cal.get(Calendar.MONTH) % 3));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static final Date getPreviousQuarterBegin(String date) {
        if (date == null) {
            return null;
        }
        Date retDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(MM_DD_YYYY);
        try {
            Date processDate = sdf.parse(date);
            retDate = getPreviousQuarterBegin(processDate);
        } catch (ParseException pe) {
            // TO DO handle exception
        }
        return retDate;
    }

    public static final Date getPreviousQuarterEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1 - (cal.get(Calendar.MONTH) % 3));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    public static final Date getPreviousQuarterEnd(String date) {
        if (date == null) {
            return null;
        }
        Date retDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(MM_DD_YYYY);
        try {
            Date processDate = sdf.parse(date);
            retDate = getPreviousQuarterEnd(processDate);
        } catch (ParseException pe) {
            // TO DO handle exception
        }
        return retDate;
    }

    public static final Date getPreviousXQuarterBegin(Date date, int quarters) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, (-3 * quarters) - (cal.get(Calendar.MONTH) % 3));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static final Date getPreviousXYearBegin(Date date, int years) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, (-1 * years));
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static final Date getPreviousXQuarterBegin(String date, int quarters) {
        if (date == null) {
            return null;
        }
        Date retDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(MM_DD_YYYY);
        try {
            Date processDate = sdf.parse(date);
            retDate = getPreviousXQuarterBegin(processDate, quarters);
        } catch (ParseException pe) {
            // TO DO handle exception
        }
        return retDate;
    }

    public static final Date getPreviousXYearBegin(String date, int years) {
        if (date == null) {
            return null;
        }
        Date retDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(MM_DD_YYYY);
        try {
            Date processDate = sdf.parse(date);
            retDate = getPreviousXYearBegin(processDate, years);
        } catch (ParseException pe) {
            // TO DO handle exception
        }
        return retDate;
    }

    public static final Date getPreviousXQuarterEnd(Date date, int quarters) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, (-1 * quarters) - (cal.get(Calendar.MONTH) % 3));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    public static final Date getPreviousXYearEnd(Date date, int years) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, (-1 * years));
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    public static final Date getPreviousXQuarterEnd(String date, int quarters) {
        if (date == null) {
            return null;
        }
        Date retDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(MM_DD_YYYY);
        try {
            Date processDate = sdf.parse(date);
            retDate = getPreviousXQuarterEnd(processDate, quarters);
        } catch (ParseException pe) {
            // TO DO handle exception
        }
        return retDate;
    }

    public static final Date getPreviousXYearEnd(String date, int years) {
        if (date == null) {
            return null;
        }
        Date retDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(MM_DD_YYYY);
        try {
            Date processDate = sdf.parse(date);
            retDate = getPreviousXYearEnd(processDate, years);
        } catch (ParseException pe) {
            // TO DO handle exception
        }
        return retDate;
    }

    public static final boolean isDateBetween(Date begin, Date end, Date arg) {
        if (begin == null || end == null || arg == null) {
            return false;
        }

        return begin.compareTo(arg) * arg.compareTo(end) >= 0;
    }

    public static final boolean isDateBetween(String begin, String end, String arg) {
        if (begin == null || end == null || arg == null) {
            return false;
        }
        boolean retValue = false;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(MM_DD_YYYY);
        Date dtBegin = null;
        Date dtEnd = null;
        Date dtArg = null;

        try {
            dtBegin = sdf.parse(begin);
            dtEnd = sdf.parse(end);
            dtArg = sdf.parse(arg);
        } catch (ParseException pe) {
            // TO DO handle exception
        }

        retValue = isDateBetween(dtBegin, dtEnd, dtArg);
        return retValue;
    }

    public static final String convertDateToString(Date date) {

        String strDate = "";
        SimpleDateFormat simpleDtFormat = new SimpleDateFormat(MM_DD_YYYY);
        if (date != null) {
            strDate = simpleDtFormat.format(date.getTime());
        }
        return strDate;
    }

    public static final Date toDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        Date retValue = null;
        sdf.applyPattern(MM_DD_YYYY);
        try {
            retValue = sdf.parse(date);
        } catch (ParseException pe) {
            // TO DO handle exception
        }
        return retValue;
    }

}
