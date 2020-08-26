package com.ascap.apm.controller.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import com.ascap.apm.common.exception.cache.InvalidCacheException;
import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.common.utils.CharacterSetUtils;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.common.utils.cache.PrepKeyValueObject;
import com.ascap.apm.common.utils.cache.lookup.LookupTables;

/**
 * @author Srinivas_Dintakurti To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class Utils {

    public static final int SSN_TAXID_CHECK_ONE_IS_REQUIRED = 0;

    public static final int SSN_TAXID_CHECK_BOTH_SHOULD_NOT_EXIST = 1;

    public static final int SSN_TAXID_CHECK_SSN_FORMAT_IF_PRESENT = 2;

    public static final int SSN_TAXID_CHECK_TAXID_FORMAT_IF_PRESENT = 3;

    public static final int SSN_TAXID_CHECK_TAXID_REQUIRED = 4;

    public static final String MM_DD_YYYY = "MM/dd/yyyy";

    protected static LogHelper log = new LogHelper(Utils.class.getName());

    /**
     * Constructor for Utils.
     */
    private Utils() {
        super();
    }

    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = null;
        simpleDateFormat = new SimpleDateFormat(MM_DD_YYYY);
        simpleDateFormat.setLenient(false);
        String systemDate = null;
        try {
            systemDate = simpleDateFormat.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return systemDate;
    }

    public static int compareTwoDates(String startDate, String endDate) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(MM_DD_YYYY);

            Date tempStartDate = sdf.parse(startDate);
            Date tempEndDate = sdf.parse(endDate);

            if (tempStartDate.compareTo(tempEndDate) < 0) {
                return -1;
            } else if (tempStartDate.compareTo(tempEndDate) > 0) {
                return 1;
            } else {
                return 0;
            }

        } catch (Exception fe) {
            return -1;
        }

    }

    /**
     * Method isEmptyOrNull.
     * 
     * @param testString
     * @return boolean
     */
    public static boolean isEmptyOrNull(String testString) {
        if (testString == null) {
            return true;
        }
        return ("".equals(testString.trim()));
    }

    /**
     * Method appendEscapeCharToQuoteForJS.
     * 
     * @param name
     * @return String
     */
    public static String appendEscapeCharToQuoteForJS(String name) {

        if (isEmptyOrNull(name)) {
            return "";
        }

        int s = 0;
        int e = 0;
        StringBuilder result = new StringBuilder();

        while ((e = name.indexOf('\'', s)) >= 0) {
            result.append(name.substring(s, e));
            result.append('\\');
            s = e + 1;
        }

        result.append(name.substring(s));
        StringBuilder result1 = new StringBuilder();
        s = 0;
        while ((e = result.toString().indexOf('\'', s)) >= 0) {
            result1.append(result.toString().substring(s, e));
            result1.append("&quot;");
            s = e + 1;
        }
        result1.append(result.toString().substring(s));
        return result1.toString();
    }

    /**
     * Method isValidDateFormat.
     * 
     * @param date
     * @return boolean
     */
    public static boolean isValidDateFormat(String date) {
        SimpleDateFormat simpleDateFormat = null;
        simpleDateFormat = new SimpleDateFormat(MM_DD_YYYY);
        simpleDateFormat.setLenient(false);

        if (isEmptyOrNull(date)) {
            try {
                simpleDateFormat.parse(date);
            } catch (ParseException pse) {
                return false;
            }
        }

        StringTokenizer st = new StringTokenizer(date, "/");
        int noOfTokens = st.countTokens();

        if (noOfTokens != 3) {
            return false;
        }

        int tokenCount = 1;
        String currentToken = "";
        int tokenSize = 0;
        int currentCheckingPiece = 0;
        try {
            while (st.hasMoreTokens()) {
                currentToken = st.nextToken();
                currentCheckingPiece = Integer.parseInt(currentToken);
                tokenSize = currentToken.length();

                if ((tokenCount == 1 || tokenCount == 2) && (currentCheckingPiece < 0 || currentCheckingPiece > 99)) {
                    return false;
                }

                if ((tokenCount == 3) && (tokenSize != 4)) {
                    return false;
                }

                tokenCount++;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Method isValidYearFormat. Checks the year is of format YYYY and is between 1900 and 9999
     * 
     * @param year
     * @return boolean
     */
    public static boolean isValidYearFormat(String year) {
        int tempYear = 0;
        try {
            tempYear = Integer.parseInt(year);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return (tempYear >= 1900 && tempYear <= 9999);
    }

    /**
     * Method isValidTax.
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
            if ((tokenCount == 1) && (tokenSize != 2)) {
                return false;
            }

            if ((tokenCount == 2) && (tokenSize != 7)) {
                return false;
            }
        }
        return tokenCount != 2;

    }

    /**
     * Method isValidSsn.
     * 
     * @param ssn
     * @return boolean
     */
    public static boolean isValidSsn(String ssn) {

        if (ssn.equalsIgnoreCase("000-00-0000")) {
            return false;
        }

        StringTokenizer st = new StringTokenizer(ssn, "-");
        int tokenCount = 1;
        int tempNumber = 0;
        String currentToken = "";
        int tokenSize = 0;

        // check number of tokens
        int noOfTokens = st.countTokens();
        if (noOfTokens > 3 || noOfTokens < 3) {
            return false;
        }
        while (st.hasMoreTokens()) {
            currentToken = st.nextToken();
            try {
                tempNumber = Integer.parseInt(currentToken);
            } catch (NumberFormatException nfe) {
                return false;
            }

            tokenSize = currentToken.length();

            if (tokenCount == 1) {
                if (tokenSize != 3) {
                    return false;
                }
                if ((tempNumber < 0) || (tempNumber >= 1000)) {
                    return false;
                }
            }
            if (tokenCount == 2) {
                if (tokenSize != 2) {
                    return false;
                }
                if ((tempNumber < 0) || (tempNumber >= 100)) {
                    return false;
                }
            }
            if (tokenCount == 3) {
                if (tokenSize != 4) {
                    return false;
                }
                if ((tempNumber < 0) || (tempNumber >= 10000)) {
                    return false;
                }
            }
            tokenCount++;
        }

        return true;
    }

    public static boolean fileExtensionCSV(String fileName) {
        if (fileName.lastIndexOf('.') == -1) {
            return false;
        } else {
            int index = fileName.lastIndexOf('.');
            String ext = fileName.substring(index + 1);
            if (!(ext.equals("csv") || ext.equals("CSV"))) {
                return false;
            }
        }
        return true;
    }

    public static boolean fileExtensionValid(String fileName) {
        if (fileName.lastIndexOf('.') == -1) {
            return false;
        } else {
            int index = fileName.lastIndexOf('.');
            String ext = fileName.substring(index + 1);
            if (!(ext.equals("txt") || ext.equals("TXT"))) {
                return false;
            }
        }
        return true;
    }

    public static boolean fileNameStartingCharValid(String fileName) {

        int index = fileName.lastIndexOf('.');
        String fileNameWithNoExt = fileName.substring(0, index);
        char[] fileNameChars = fileNameWithNoExt.toCharArray();
        String validStartingChars = "_abcdefghijklmnopqrstuvwxyzABCEDFGHIJKLMNOPQRSTUVWXYZ";

        char fileStartingChar = fileNameChars[0];

        return (validStartingChars.indexOf(fileStartingChar) == -1);
    }

    public static boolean fileNameValid(String fileName) {

        int index = fileName.lastIndexOf('.');

        String fileNameWithNoExt = fileName.substring(0, index);

        char[] fileNameChars = fileNameWithNoExt.toCharArray();

        String validChars = "_abcdefghijklmnopqrstuvwxyzABCEDFGHIJKLMNOPQRSTUVWXYZ1234567890";

        for (int i = 0; i < fileNameChars.length; i++) {
            char character = fileNameChars[i];
            if (validChars.indexOf(character) == -1) {
                return false;
            }
        }

        return true;
    }

    public static boolean containsSpecialChars(String charString) {
        for (int i = 33; i <= 47; i++) {
            if (charString.indexOf((char) i) >= 0) {

                return true;
            }
        }
        for (int i = 58; i <= 64; i++) {
            if (charString.indexOf((char) i) >= 0) {

                return true;
            }
        }
        for (int i = 91; i <= 96; i++) {
            if (charString.indexOf((char) i) >= 0) {

                return true;
            }
        }
        for (int i = 123; i <= 126; i++) {
            if (charString.indexOf((char) i) >= 0) {

                return true;
            }
        }
        for (int i = 128; i <= 254; i++) {
            if (charString.indexOf((char) i) >= 0) {

                return true;
            }
        }
        return false;

    }

    public static boolean containsIntegers(String charString) {
        for (int i = 48; i <= 57; i++) {
            if (charString.indexOf((char) i) >= 0) {
                return true;
            }
        }
        return false;

    }

    public static boolean containsChars(String charString) {
        for (int i = 97; i <= 122; i++) {
            if (charString.indexOf((char) i) >= 0) {
                return true;
            }
        }
        for (int i = 65; i <= 90; i++) {
            if (charString.indexOf((char) i) >= 0) {
                return true;
            }
        }
        return false;

    }

    /**
     * Method isValidPostalCode.
     * 
     * @param postalCode
     * @return boolean
     */
    public static boolean isValidPostalCode(String postalCode) {

        int postalSize = 0;
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
        return (postalSize <= 10);

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

    /**
     * Method getDollarDisplayFormat.
     * 
     * @param dollarAmount
     * @return String
     */
    public static String getDollarDisplayFormat(String dollarAmount) {

        if (isEmptyOrNull(dollarAmount)) {
            return "";
        }

        double amount = 0;

        try {
            amount = Double.parseDouble(dollarAmount);
        } catch (Exception e) {
            return "";
        }

        DecimalFormat decimalFormat = new DecimalFormat("$#,###,###,##0.00");
        dollarAmount = decimalFormat.format(amount);

        return dollarAmount;
    }

    public static String getISOCorrectedString(String namePartToFix) throws InvalidCacheException {
        if (!ValidationUtils.isEmptyOrNull(namePartToFix)) {
            namePartToFix = namePartToFix.trim();
            // Code for Character Standradization
            String isoName = CharacterSetUtils.standardizeCharacters(namePartToFix, CharacterSetUtils.PARTY_NAME_TYPE);
            if (!namePartToFix.trim().equals(isoName.trim())) {
                namePartToFix = isoName;
            }
        }

        return namePartToFix;
    }

    /**
     * @param disYearStr - Start Distribution Year in the format '2009'
     * @param disNumberStr - Start Distribution Number in the format '1'
     * @param numberOfRecords - Number of values required in dropdown.
     * @return Formatted String in the format '2009Q3/2010D1'
     * @throws Exception
     */
    public static List<PrepKeyValueObject> getLatestDistributions(String yearQtrStr, int numberOfRecords) {
        List<PrepKeyValueObject> list = new ArrayList<>();
        int disYear = 0;
        int disNumber = 0;

        try {

            disYear = Integer.parseInt(yearQtrStr.substring(0, 4));
            disNumber = Integer.parseInt(yearQtrStr.substring(4));

            PrepKeyValueObject prepKeyValueObject = null;

            Calendar calendar = new GregorianCalendar(disYear, disNumber - 1, 01);
            calendar.add(Calendar.YEAR, 1);
            calendar.add(Calendar.MONTH, 3);
            for (int i = 0; i < numberOfRecords; i++) {
                calendar.add(Calendar.MONTH, -3);
                prepKeyValueObject = new PrepKeyValueObject();
                String s = calendar.get(Calendar.YEAR) + "Q" + ((calendar.get(Calendar.MONTH) / 3) + 1);
                prepKeyValueObject.setKey(s);
                prepKeyValueObject.setValue(s);
                list.add(prepKeyValueObject);
            }

        } catch (Exception e) {
            log.error(e.getMessage());

        }
        return list;
    }

    public static List<PrepKeyValueObject> getLatestDistributionsSpl(String yearQtrStr, int numberOfRecords) {
        List<PrepKeyValueObject> list = new ArrayList<>();
        int disYear = 0;
        int disNumber = 0;
        try {

            disYear = Integer.parseInt(yearQtrStr.substring(0, 4));
            disNumber = Integer.parseInt(yearQtrStr.substring(4));

            PrepKeyValueObject prepKeyValueObject = null;

            Calendar calendar = new GregorianCalendar(disYear, disNumber - 1, 01);
            calendar.add(Calendar.YEAR, 1);
            calendar.add(Calendar.MONTH, 3);
            for (int i = 0; i < numberOfRecords; i++) {
                calendar.add(Calendar.MONTH, -3);
                prepKeyValueObject = new PrepKeyValueObject();
                String s = calendar.get(Calendar.YEAR) + "D" + ((calendar.get(Calendar.MONTH) / 3) + 1);
                prepKeyValueObject.setKey(s);
                prepKeyValueObject.setValue(s);
                list.add(prepKeyValueObject);
            }

        } catch (Exception e) {
            log.error(e.getMessage());

        }
        return list;
    }

    /**
     * @param disYearStr - Start Distribution Year in the format '2009'
     * @param disNumberStr - Start Distribution Number in the format '1'
     * @param numberOfRecords - Number of values required in dropdown.
     * @return Formatted String in the format '2009Q3/2010D1'
     * @throws Exception
     */
    public static List<PrepKeyValueObject> getLatestDistributions(String disYearStr, String disNumberStr,
        int numberOfRecords) {
        List<PrepKeyValueObject> list = new ArrayList<>();
        int disYear = 0;
        int disNumber = 0;
        int numRecordsRequired = numberOfRecords; // to calculate number of years required
        try {
            disYear = Integer.parseInt(disYearStr);
            disNumber = Integer.parseInt(disNumberStr);
            if (disNumber > 4) {
                disNumber = 4;
            }
            PrepKeyValueObject prepKeyValueObject = null;
            for (int tempYear = disYear; tempYear > (disYear - ((numRecordsRequired / 4) + 1)); tempYear--) {
                for (int tempQuarter = 4; tempQuarter > 0; tempQuarter--) {
                    if (!(tempYear == disYear && tempQuarter > disNumber) && numberOfRecords > 0) {
                        prepKeyValueObject = new PrepKeyValueObject();
                        String str = ((tempQuarter > 2) ? (tempQuarter - 2) + "Q" + tempYear
                            : (tempQuarter + 2) + "Q" + (tempYear - 1)) + "/" + tempQuarter + "D" + tempYear;
                        prepKeyValueObject.setKey(tempYear + "0" + tempQuarter);
                        prepKeyValueObject.setValue(str);
                        list.add(prepKeyValueObject);
                        numberOfRecords--;
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return list;
    }

    public static List<PrepKeyValueObject> getNextSurveyYearQuarters(int numberOfRecords) {
        List<PrepKeyValueObject> list = new ArrayList<>();
        int disYear = 0;
        int disNumber = 0;

        Calendar now = Calendar.getInstance();

        try {
            disYear = now.get(Calendar.YEAR);
            disNumber = now.get(Calendar.MONTH);
            PrepKeyValueObject prepKeyValueObject = null;

            for (int i = 0; i < numberOfRecords; i++) {

                now.add(Calendar.MONTH, 1);

                if (disNumber < 12) {
                    disNumber++;
                } else {
                    disNumber = 1;
                    disYear++;
                }

                prepKeyValueObject = new PrepKeyValueObject();
                if (disNumber < 10) {
                    prepKeyValueObject.setKey(disYear + "0" + disNumber);
                    prepKeyValueObject.setValue(disYear + "0" + disNumber);
                } else {
                    prepKeyValueObject.setKey(disYear + "" + disNumber);
                    prepKeyValueObject.setValue(disYear + "" + disNumber);

                }
                list.add(prepKeyValueObject);
            }
        } catch (Exception e) {
            log.error(e.getMessage());

        }
        return list;
    }

    public static List<PrepKeyValueObject> getLatestDistributions(Collection<?> col, int numberOfRecords) {
        List<PrepKeyValueObject> list = new ArrayList<>();
        if (col == null || col.isEmpty()) {
            return list;
        }
        int disYear = 0;
        int disNumber = 0;
        try {

            Iterator<?> itr = col.iterator();
            String distribution = null;

            PrepKeyValueObject prepKeyValueObject = null;
            while (itr.hasNext()) {
                distribution = (String) itr.next();
                if ((distribution != null) && (numberOfRecords > 0)) {
                    disYear = Integer.parseInt(distribution.substring(0, 4));
                    disNumber = Integer.parseInt(distribution.substring(5));
                    prepKeyValueObject = new PrepKeyValueObject();
                    String str =
                        ((disNumber > 2) ? (disNumber - 2) + "Q" + disYear : (disNumber + 2) + "Q" + (disYear - 1))
                            + "/" + disNumber + "D" + disYear;
                    prepKeyValueObject.setKey(disYear + "D" + disNumber);
                    prepKeyValueObject.setValue(str);
                    list.add(prepKeyValueObject);
                    numberOfRecords--;

                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());

        }
        return list;
    }

    public static String getSingleOutputValue(String valueType) {
        String outputString = null;
        try {
            outputString = LookupTables.getSingleOutputValue(valueType);
        } catch (Exception e) {
            outputString = "";
        }
        return outputString;

    }

}
