package com.ascap.apm.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import com.ascap.apm.common.exception.cache.InvalidCacheException;
import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.common.utils.constants.UsageConstants;

/**
 * The purpose of the Utilitis is for the Validation Needs accross USAGE module. Please do not change the
 * implementations of these methods unless you have checked how these are used in USAGE Module. Do note that some these
 * methods are also available ValidationUtils Thos are more appropriate for other modules.
 * 
 * @author Jaya Shyam Narayana Lingamchetty
 * @version $Revision: 1.5 $ $Date: Jul 02 2009 03:05:52 $
 */
public class UsageCommonValidations {

    private UsageCommonValidations() {
    }

    protected static LogHelper log = new LogHelper(UsageCommonValidations.class.getName());

    /**
     * Method isEmptyOrNull. Returns true if the test String is null or trimmed String equals "".
     * 
     * @param testString
     * @return boolean
     */
    public static boolean isEmptyOrNull(String testString) {
        if (testString == null) {
            return true;
        }
        return "".equals(testString.trim());
    }

    /**
     * Method hasCollectionAnyElements. Returns true if array DOES NOT contain atleast one element or Null ELSE false.
     * 
     * @param testString
     * @return boolean
     */
    public static boolean isCollectionEmptyOrNoElements(String[] testStringArray) {
        return isCollectionEmptyOrNoElements(testStringArray, false);
    }

    public static boolean isCollectionEmptyOrNoElements(String[] testStringArray, boolean removeEmptyElemnts) {
        if (testStringArray == null) {
            return true;
        } else {
            if (removeEmptyElemnts) {
                testStringArray = UsageCommonValidations.removeEmptyElements(testStringArray);
            }

            if (testStringArray == null || testStringArray.length <= 0) {
                return true;
            }
        }
        return false;
    }

    public static String[] removeEmptyElements(String[] testStringArray) {
        if (testStringArray == null) {
            return testStringArray;
        } else {
            String[] outArray = null;
            ArrayList<String> outArrayList = new ArrayList<>();
            String tempString;
            if (testStringArray.length > 0) {
                for (int strIndex = 0; strIndex < testStringArray.length; strIndex++) {
                    tempString = testStringArray[strIndex];
                    if (!UsageCommonValidations.isEmptyOrNull(tempString)) {
                        outArrayList.add(tempString);
                    }
                }
                outArray = new String[outArrayList.size()];
                outArray = outArrayList.toArray(outArray);
            }
            return outArray;
        }
    }

    /**
     * Method hasCollectionAnyElements. Returns true if array contains atleast one element else false.
     * 
     * @param testString
     * @return boolean
     */
    public static boolean hasCollectionAnyElements(Object[] testStringArray) {
        if (testStringArray == null) {
            return false;
        } else {
            if (testStringArray.length > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidLongCollection(String[] testStringArray) {
        if (!hasCollectionAnyElements(testStringArray)) {
            return false;
        }
        int arrayLength = 0;
        arrayLength = testStringArray.length;
        for (int curIndex = 0; curIndex < arrayLength; curIndex++) {
            if (!isValidLong(testStringArray[curIndex])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method isValidInteger. Returns True only when a non null/non empty, long value is given.
     * 
     * @param testString
     * @return boolean
     */
    public static boolean isValidInteger(String testString) {
        if (isEmptyOrNull(testString)) {
            return (false);
        } else {
            try {
                Integer.parseInt(testString);
                return (true);
            } catch (NumberFormatException ne) {
                return false;
            }
        }
    }

    /**
     * Method isValidLong. Returns True only when a non null/non empty, long value is given.
     * 
     * @param testString
     * @return boolean
     */
    public static boolean isValidLong(String testString) {
        if (isEmptyOrNull(testString)) {
            return (false);
        } else {
            try {
                Long.parseLong(testString);
                return (true);
            } catch (NumberFormatException ne) {
                return false;
            }
        }
    }

    /**
     * Method isValidDouble. Returns True only when a non null/non empty, double value is given.
     * 
     * @param testString
     * @return boolean
     */
    public static boolean isValidDouble(String testString) {
        if (isEmptyOrNull(testString)) {
            return (false);
        } else {
            try {
                Double.parseDouble(testString);
                return (true);
            } catch (NumberFormatException ne) {
                return false;
            }
        }
    }

    /**
     * Method getCurrentDate.
     * 
     * @return String
     */
    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = null;
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        simpleDateFormat.setLenient(false);
        String systemDate = null;
        try {
            systemDate = simpleDateFormat.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return systemDate;
    }

    /**
     * Method isAfterCurrentDate.
     * 
     * @return String dateVal should be with a valid format.
     */
    public static boolean isAfterCurrentDate(String dateVal) {

        Calendar currentDateCal = getCalendar(getCurrentDate());
        Calendar dateValCal = getCalendar(dateVal);
        return dateValCal != null && dateValCal.after(currentDateCal);
    }

    /**
     * Method getCalendar.
     * 
     * @param date
     * @return Calendar
     */
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
            calendarDate.set(year, month - 1, day);
        } catch (Exception e) {
            calendarDate = null;
        }
        return calendarDate;
    }

    /**
     * Method areBothEqual. NOTE: Treats Nulls in a different way than Oracle where null is UNKNONWN and Unknown can
     * never be equal to another unknown. BE CAREFULL WHEN USING THIS FUNCTION When both the Strings are null then they
     * are treated as equal. if any one string is null then a false is returned and when both are not null a CASE
     * INSENSITIVE CHECK IS MADE
     * 
     * @param str1
     * @param str2
     * @return boolean
     */
    public static boolean areBothEqualIgnoreCase(String str1, String str2) {
        if (isEmptyOrNull(str1) && isEmptyOrNull(str2)) {
            return true;
        }

        if (!isEmptyOrNull(str1) && !isEmptyOrNull(str2)) {
            return (str1.equalsIgnoreCase(str2));
        }
        return false;
    }

    public static String replace(String source, String pattern, String replace) {
        if (source != null) {
            final int len = pattern.length();
            StringBuilder sb = new StringBuilder();
            int found = -1;
            int start = 0;

            while ((found = source.indexOf(pattern, start)) != -1) {
                sb.append(source.substring(start, found));
                sb.append(replace);
                start = found + len;
            }

            sb.append(source.substring(start));

            return sb.toString();
        } else {
            return "";
        }
    }

    public static final boolean isStringWithinMaxLength(String stringToTest, int maxLength) {
        if (ValidationUtils.isEmptyOrNull(stringToTest)) {
            return true;
        }
        return stringToTest.length() <= maxLength;

    }

    /**
     * This method is used from Distribution. Date Strings passed must be valid date format MM/DD/YYYY
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isStartDateAfterEndDate(String date1, String date2) {
        SimpleDateFormat simpleDateFormat = null;
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        simpleDateFormat.setLenient(false);
        boolean isStartDateAfterEndDateVal = false;
        try {
            if (simpleDateFormat.parse(date1).after(simpleDateFormat.parse(date2))) {
                isStartDateAfterEndDateVal = true;
            }
        } catch (ParseException pse) {
            // do nothing
        }
        return isStartDateAfterEndDateVal;
    }

    /**
     * Performs ISO Standardization of Charecters by delegating to the CharacterSetUtils.standardizeCharacters. this
     * should be used only for Usage Module. The type of strings are Usage Module Specific.
     * 
     * @param inputStr
     * @param usageISOFieldType -- UsageConstants.ISO_FIELD_PH_xxx or UsageConstants.ISO_FIELD_WP_xxx...
     * @return
     * @throws InvalidCacheException
     */
    public static String standardizeCharacters(String inputStr, String usageISOFieldType) throws InvalidCacheException {
        if (UsageCommonValidations.isEmptyOrNull(inputStr)) {
            return inputStr;
        }
        String methodToUse;
        if (UsageConstants.ISO_FIELDS_MAPPING_HASH.containsKey(usageISOFieldType)) {
            methodToUse = (String) UsageConstants.ISO_FIELDS_MAPPING_HASH.get(usageISOFieldType);
            return CharacterSetUtils.standardizeCharacters(inputStr, methodToUse);
        } else {
            return inputStr.toUpperCase().trim();
        }

    }
}
