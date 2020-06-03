package com.ascap.apm.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.common.helpers.PrepExtPropertyHelper;
import com.ascap.apm.common.utils.constants.PaymentProcessConstants;
import com.ascap.apm.common.utils.constants.PrepPropertiesConstants;

/**
 * Contains frequently used validation utility functions in PREP.
 * 
 * @author Jaya Shyam Narayana Lingamchetty
 * @version $Revision: 1.31 $ $Date: May 27 2011 12:06:14 $
 */
public class ValidationUtils {

    public static final Pattern regexAscapDate = Pattern.compile("^\\d{2}\\/\\d{2}\\/\\d{4}$");

    public static final Pattern regexAscapTimeOnly = Pattern.compile("^\\d{2}:\\d{2}:\\d{2}$");

    public static final Pattern regexCurrency = Pattern.compile("^([0-9]{1,9})?(\\.([0-9]{1,2})?)?$");

    public static final Pattern regexPercentage = Pattern.compile("^(\\d{1,3})?\\.?(\\d{1,2})?$");

    public static final Pattern regexPhoneNumber = Pattern.compile("^\\d{3}-\\d{3}-\\d{4}$");

    public static final Pattern regexISWC = Pattern.compile("^t\\d{10}$", Pattern.CASE_INSENSITIVE);

    protected static String[] ignoredPlusCategories = {"GENRL", "SEULR"};

    public static final String DATEFORMAT = "MM/dd/yyyy";

    protected static LogHelper log = new LogHelper(ValidationUtils.class.getName());

    private ValidationUtils() {

    }

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
     * Method hasCollectionAnyElements. Returns true if array contains atleast one element else false.
     * 
     * @param testStringArray
     * @return boolean
     */
    public static boolean hasCollectionAnyElements(String[] testStringArray) {
        if (testStringArray == null) {
            return false;
        }
        return testStringArray.length > 0;
    }

    /**
     * Method hasCollectionAnyElements. Returns true if array contains atleast one element else false.
     * 
     * @param testString
     * @return boolean
     */
    public static boolean hasCollectionAnyElements(Collection<?> testCollection) {
        if (testCollection == null) {
            return false;
        }

        return !testCollection.isEmpty();
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
        }

        try {
            Integer.parseInt(testString);
            return (true);
        } catch (NumberFormatException ne) {
            return false;
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
        }

        try {
            Long.parseLong(testString);
            return (true);
        } catch (NumberFormatException ne) {
            return false;
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
        }

        try {
            Double.parseDouble(testString);
            return (true);
        } catch (NumberFormatException ne) {
            return false;
        }
    }

    public static boolean isValidDecimalPrecision(String testValue, int totalLength, int precision) {
        if (isEmptyOrNull(testValue)) {
            return true;
        }

        if (testValue.indexOf('.') != -1) {
            StringTokenizer st = new StringTokenizer(testValue, ".");
            int roundPartLength = 0;
            int decimalPartLength = 0;
            if (st.hasMoreTokens()) {
                roundPartLength = st.nextToken().trim().length();
            }
            if (st.hasMoreTokens()) {
                decimalPartLength = st.nextToken().trim().length();
            }
            if (decimalPartLength > precision || (roundPartLength + decimalPartLength) > totalLength) {
                return false;
            }
        } else {
            if (testValue.trim().length() > (totalLength - precision)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Method getCurrentDate.
     * 
     * @return String
     */
    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = null;
        simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
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

    public static Calendar getCalendarClearTime(String date) {

        Calendar calendarDate;
        try {
            StringTokenizer st = new StringTokenizer(date, "/");
            int month = Integer.parseInt(st.nextToken());
            int day = Integer.parseInt(st.nextToken());
            int year = Integer.parseInt(st.nextToken());

            calendarDate = new GregorianCalendar();
            calendarDate.clear();
            calendarDate.set(year, month - 1, day);

        } catch (Exception e) {
            log.error(e.getMessage());
            calendarDate = null;
        }
        return calendarDate;
    }

    /**
     * Method validateDateFormat.
     * 
     * @param date
     * @return boolean
     */
    public static boolean validateDateFormat(String date) {

        SimpleDateFormat simpleDateFormat = null;
        simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
        simpleDateFormat.setLenient(false);

        if (!isEmptyOrNull(date)) {
            try {
                simpleDateFormat.parse(date);
            } catch (ParseException pse) {
                return false;
            }
        }

        return ValidationUtils.regexAscapDate.matcher(date).matches();
    }

    /**
     * Method isValidPostalCode.
     * 
     * @param postalCode
     * @return boolean
     */
    public static boolean isValidPostalCode(String postalCode) {

        int postalSize;
        String sixthCharacter = "";
        String fiveDigitsStr = "0";
        String fourDigitsStr = "0";
        postalSize = postalCode.length();
        try {
            if (postalSize < 5) {
                return false;
            }
            if (postalSize > 5) {
                sixthCharacter = postalCode.substring(5, 6);
                fiveDigitsStr = postalCode.substring(0, 5);

                if (!(sixthCharacter.equals("-"))) {
                    return false;
                }
                if (postalSize > 6 && postalSize == 10)
                    fourDigitsStr = postalCode.substring(6, 9);
                if (postalSize > 5 && postalSize < 10) {
                    return false;
                }
                Integer.parseInt(fiveDigitsStr);
                Integer.parseInt(fourDigitsStr);
            } else {
                Integer.parseInt(postalCode);
            }
        } catch (NumberFormatException nfe) {
            return false;
        }

        return postalSize <= 10;

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

    /**
     * Method areBothEqual. NOTE: Treats Nulls in a different way than Oracle where null is UNKNONWN and Unknown can
     * never be equal to another unknown. BE CAREFULL WHEN USING THIS FUNCTION When both the Strings are null then they
     * are treated as equal. if any one string is null then a false is returned and when both are not null a CASE
     * SENSITIVE CHECK IS MADE implemented for issue 1607 - Shyam
     */
    public static boolean areBothEqual(String str1, String str2) {
        if (isEmptyOrNull(str1) && isEmptyOrNull(str2)) {
            return true;
        }

        if (!isEmptyOrNull(str1) && !isEmptyOrNull(str2)) {
            return (str1.equals(str2));
        }
        return false;
    }

    /**
     * Moved From WEB Utils Method isValidTax.
     * 
     * @param taxId
     * @return boolean
     */
    public static boolean isValidTax(String taxId) {

        if (taxId.equalsIgnoreCase("00-0000000")) {
            return false;
        }
        StringTokenizer st = new StringTokenizer(taxId, "-");
        int tokenCount = 0;
        String currentToken = "";
        int tokenSize = 0;

        // check number of tokens
        int noOfTokens = st.countTokens();
        if (noOfTokens > 2 || noOfTokens < 2) {
            return false;
        }

        while (st.hasMoreTokens()) {
            tokenCount++;
            currentToken = st.nextToken();
            try {
                Integer.parseInt(currentToken);
            } catch (NumberFormatException nfe) {
                return false;
            }

            tokenSize = currentToken.length();
            if (tokenCount == 1 && tokenSize != 2) {
                return false;
            }
            if (tokenCount == 2 && tokenSize != 7) {
                return false;
            }
        }

        return tokenCount != 2;
    }

    public static String replace(String source, String pattern, String replace) {

        if (source == null) {
            return "";
        }

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
    }

    public static boolean isDigitsOnly(String s) {
        boolean isDigitOnly = true;
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (!Character.isDigit(c)) {
                isDigitOnly = false;
            }
        }
        return isDigitOnly;
    }

    public static boolean isValidWaiverNumber(String waiverNumber) {

        boolean isValidWaiverNumber = false;

        if (isEmptyOrNull(waiverNumber)) {
            return isValidWaiverNumber;
        }

        return true;
    }

    public static boolean isValidCardType(String cardType) {

        boolean isValidCardType = false;

        if (isEmptyOrNull(cardType)) {
            return false;
        }

        cardType = cardType.trim();
        if (PaymentProcessConstants.PAYMENT_GATEWAY_PAYMENT_TECH.equalsIgnoreCase(
            PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.PAYMENT_GATEWAY_TYPE))) {
            if (cardType.equalsIgnoreCase(PaymentProcessConstants.CREDIT_CARD_TYPE_VISA)
                || cardType.equalsIgnoreCase(PaymentProcessConstants.CREDIT_CARD_TYPE_MASTER)
                || cardType.equalsIgnoreCase(PaymentProcessConstants.CREDIT_CARD_TYPE_OTHER)) {

                isValidCardType = true;
            }
        } else if (PaymentProcessConstants.PAYMENT_GATEWAY_PAYPAL.equalsIgnoreCase(
            PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.PAYMENT_GATEWAY_TYPE))
            && (cardType.equalsIgnoreCase(PaymentProcessConstants.CREDIT_CARD_TYPE_VISA)
                || cardType.equalsIgnoreCase(PaymentProcessConstants.CREDIT_CARD_TYPE_MASTER)
                || cardType.equalsIgnoreCase(PaymentProcessConstants.CREDIT_CARD_TYPE_AMERICANEXPRESS)
                || cardType.equalsIgnoreCase(PaymentProcessConstants.CREDIT_CARD_TYPE_DISCOVER)
                || cardType.equalsIgnoreCase(PaymentProcessConstants.CREDIT_CARD_TYPE_OTHER))) {

            isValidCardType = true;
        }

        return isValidCardType;

    }

    public static boolean isValidCardIdentificationNumber(String cardIdentificationNumber) {

        boolean isValidCardIdentification = false;

        if (isEmptyOrNull(cardIdentificationNumber)) {
            return false;
        }

        cardIdentificationNumber = cardIdentificationNumber.trim();

        if (isValidInteger(cardIdentificationNumber)
            && (cardIdentificationNumber.length() == 3 || cardIdentificationNumber.length() == 4)
            && isDigitsOnly(cardIdentificationNumber)) {

            isValidCardIdentification = true;
        }

        return isValidCardIdentification;

    }

    public static boolean isValidExpirationMonth(String monthNumber) {

        boolean isValidExpirationMonth = false;

        if (isEmptyOrNull(monthNumber)) {
            return false;
        }

        monthNumber = monthNumber.trim();

        if (isValidInteger(monthNumber) && Integer.parseInt(monthNumber) > 0 && Integer.parseInt(monthNumber) < 13) {

            isValidExpirationMonth = true;
        }

        return isValidExpirationMonth;

    }

    public static boolean isValidExpirationYear(String yearNumber) {

        boolean isValidExpirationYear = false;

        if (isEmptyOrNull(yearNumber)) {
            return false;
        }

        yearNumber = yearNumber.trim();

        if (isValidInteger(yearNumber)) {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            if (Integer.parseInt(yearNumber) >= currentYear) {
                isValidExpirationYear = true;
            }
        }

        return isValidExpirationYear;

    }

    public static boolean isValidExpirationDate(String monthNumber, String yearNumber) {

        boolean isValidExpirationDate = false;

        if (isEmptyOrNull(monthNumber) || isEmptyOrNull(yearNumber)) {
            return false;
        }

        monthNumber = monthNumber.trim();
        yearNumber = yearNumber.trim();

        if (isValidExpirationMonth(monthNumber) && isValidExpirationYear(yearNumber)) {
            int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);

            if (Integer.parseInt(yearNumber) == currentYear && Integer.parseInt(monthNumber) >= currentMonth) {
                isValidExpirationDate = true;
            }
        }

        return isValidExpirationDate;

    }

    public static String hideCreditCardNumber(String ccNumber) {

        int asteriskLength = 0;
        int ccNumberLength = 0;

        String lastFourCreditNumbers = "";
        StringBuilder asteriskString = new StringBuilder();

        if (ccNumber.length() > 4) {
            ccNumberLength = ccNumber.length();
            asteriskLength = ccNumber.length() - 4;

            lastFourCreditNumbers = ccNumber.substring(ccNumberLength - 4, ccNumberLength);

            for (int i = 0; i < asteriskLength; i++) {

                asteriskString.append("*");

            }

            return (asteriskString.toString() + lastFourCreditNumbers);

        } else {

            return (ccNumber);

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
        simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
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

    public static boolean isValidUserName(String userName) {

        boolean isUserNameValid = false;
        boolean foundInvalidChar = false;

        if (userName != null && userName.length() > 0) {
            for (int i = 0; i < userName.length(); i++) {
                if ((userName.charAt(i) >= 'A' && userName.charAt(i) <= 'Z')
                    || (userName.charAt(i) >= 'a' && userName.charAt(i) <= 'z') || (userName.charAt(i) == '_')
                    || isValidInteger(String.valueOf(userName.charAt(i)))) {
                    //
                } else {
                    foundInvalidChar = true;
                }
            }
        }

        if (foundInvalidChar) {
            isUserNameValid = false;
        } else {
            isUserNameValid = true;
        }
        return isUserNameValid;
    }

    public static final boolean isValidCurrency(String valueToMatch) {
        return ValidationUtils.regexCurrency.matcher(valueToMatch).matches();
    }

    public static final boolean isValidPercentage(String valueToMatch) {
        return ValidationUtils.regexPercentage.matcher(valueToMatch).matches();
    }

    public static final boolean isValidPhoneNumber(String phoneNumber) {
        return ValidationUtils.regexPhoneNumber.matcher(phoneNumber).matches();
    }

    public static final boolean isRegexMatch(Pattern regexPattern, String valueToMatch) {
        return regexPattern.matcher(valueToMatch).matches();
    }

    public static boolean isPositiveInteger(String testString) {
        boolean result = false;
        if (isValidInteger(testString)) {
            try {
                int value = Integer.parseInt(testString);
                if (value > 0) {
                    result = true;
                }
            } catch (NumberFormatException ne) {
                return result;
            }
        }
        return result;
    }

    public static boolean isPositiveDouble(String testString) {
        boolean result = false;
        if (isValidDouble(testString)) {
            try {
                double value = Double.parseDouble(testString);
                if (value > 0) {
                    result = true;
                }
            } catch (NumberFormatException ne) {
                //
            }
        }
        return result;
    }

}
