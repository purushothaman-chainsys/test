package com.ascap.apm.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import com.ascap.apm.common.helpers.LogHelper;

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

	protected static String[] ignoredPlusCategories = { "GENRL", "SEULR" };

	public static final String DATEFORMAT = "MM/dd/yyyy";

	protected static LogHelper log = new LogHelper(ValidationUtils.class.getName());

	private ValidationUtils() {

	}

	/**
	 * Method isEmptyOrNull. Returns true if the test String is null or trimmed
	 * String equals "".
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
	 * Method hasCollectionAnyElements. Returns true if array contains atleast one
	 * element else false.
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
	 * Method hasCollectionAnyElements. Returns true if array contains atleast one
	 * element else false.
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
	 * Method isValidInteger. Returns True only when a non null/non empty, long
	 * value is given.
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
	 * Method isValidLong. Returns True only when a non null/non empty, long value
	 * is given.
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
	 * Method isValidDouble. Returns True only when a non null/non empty, double
	 * value is given.
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
	 * Method areBothEqual. NOTE: Treats Nulls in a different way than Oracle where
	 * null is UNKNONWN and Unknown can never be equal to another unknown. BE
	 * CAREFULL WHEN USING THIS FUNCTION When both the Strings are null then they
	 * are treated as equal. if any one string is null then a false is returned and
	 * when both are not null a CASE INSENSITIVE CHECK IS MADE
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
	 * Method areBothEqual. NOTE: Treats Nulls in a different way than Oracle where
	 * null is UNKNONWN and Unknown can never be equal to another unknown. BE
	 * CAREFULL WHEN USING THIS FUNCTION When both the Strings are null then they
	 * are treated as equal. if any one string is null then a false is returned and
	 * when both are not null a CASE SENSITIVE CHECK IS MADE implemented for issue
	 * 1607 - Shyam
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

}
