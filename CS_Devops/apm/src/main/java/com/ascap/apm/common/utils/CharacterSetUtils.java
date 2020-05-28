package com.ascap.apm.common.utils;

import com.ascap.apm.common.exception.cache.InvalidCacheException;
import com.ascap.apm.common.exception.cache.KeyNotFoundException;
import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.common.utils.cache.lookup.LookupTables;

/**
 * @author Paras_Parekh This class contains utilities to standardize characters. This utility prevents non English
 *         characters to be entered into PREP for Party and titles. Examples of non English characters are �, �, � etc
 */

public class CharacterSetUtils {

    private CharacterSetUtils() {

    }

    protected static LogHelper log = new LogHelper("LookupTablesHelper");

    public static final String WORK_TITLE_TYPE = "Title";

    public static final String PARTY_NAME_TYPE = "Party";

    public static final String ADDRESS_TYPE = "Address";

    public static final String ISOVALID_FOR_ADDRESSES = "ISOValidForAddresses";

    /**
     * Method standardizeCharacters. Returns Standardized representation of the input string.
     * 
     * @param inputStr String Input String to standardize
     * @param typeOfString String Type of String - Whether "WORK_TITLE_TYPE" or "PARTY_NAME_TYPE" or "ADDRESS_TYPE"
     * @return String value of standardized string.
     * @throws InvalidCacheException
     */
    public static String standardizeCharacters(String inputStr, String typeOfString) throws InvalidCacheException {

        if (log.isDebugEnabled()) {
            log.debug("Entering standardizeCharacters Method.");
            log.debug("Input Str : " + inputStr + ", typeOfString :" + typeOfString);
        }
        String outputStr = "";
        StringBuilder buf = new StringBuilder("");
        String validType = "";

        if (WORK_TITLE_TYPE.equalsIgnoreCase(typeOfString)) {
            validType = "ISOValidForTitles";
        } else if (PARTY_NAME_TYPE.equalsIgnoreCase(typeOfString)) {
            validType = "ISOValidForPartyNames";
        } else if (ADDRESS_TYPE.equalsIgnoreCase(typeOfString)) {
            validType = ISOVALID_FOR_ADDRESSES;
        } else {
            log.debug("String not Standardized. validType =  " + validType);
            return outputStr;
        }

        String replaceChr;
        String prevReplaceChr = "";
        String chr;
        char tempChar;
        int ordinalVal;

        int len = inputStr.length();
        for (int i = 0; i < len; i++) {
            chr = inputStr.substring(i, i + 1);
            if (validType.equalsIgnoreCase(ISOVALID_FOR_ADDRESSES)) {
                tempChar = chr.charAt(0);
                ordinalVal = (int) tempChar;
                chr = String.valueOf(ordinalVal);
            }
            try {
                // check if the char in question is valid. if valid, find its replacement.
                if (LookupTables.getLookupTableItemDescription(validType, chr).equals("Y")) {
                    if (validType.equalsIgnoreCase(ISOVALID_FOR_ADDRESSES)) {
                        replaceChr = LookupTables.getLookupTableItemDescription("OrdinalValueReplacement", chr);
                    } else {
                        replaceChr = LookupTables.getLookupTableItemDescription("ISOReplacement", chr);
                    }
                    if (replaceChr.equals(" ") && prevReplaceChr.equals(" ")) {
                        continue;
                    }

                    if (!replaceChr.equals("-1")) {
                        buf.append(replaceChr);
                        prevReplaceChr = replaceChr;
                    }
                }
            } catch (KeyNotFoundException e) {
                if (log.isDebugEnabled()) {
                    log.debug("*** No Key Found for Char **** " + chr);
                }
                // If key is not found, ignore the character.
                buf.append("");
            }
        }

        outputStr = buf.toString();
        if (log.isDebugEnabled()) { // check if debug is enabled
            log.debug("Standardized String : " + outputStr);
        }
        return outputStr.trim();
    }

    /**
     * Method isAlphaNumeric. Returns boolean indicating if input string is alphanumeric or not.
     * 
     * @param str String Input String
     * @return boolean true if string is alphanumeric.
     */
    public static boolean isAlphaNumeric(String str) {
        boolean isAlphaNum = false;

        char[] chrArray = null;
        if (str != null)
            chrArray = str.toCharArray();

        for (int i = 0; i < chrArray.length; i++) {
            if (chrArray[i] >= '0' && chrArray[i] <= '9') {
                isAlphaNum = true;
                break;
            }
        }

        for (int i = 0; i < chrArray.length; i++) {
            if ((chrArray[i] >= 'A' && chrArray[i] <= 'Z') || (chrArray[i] >= 'a' && chrArray[i] <= 'z')) {
                isAlphaNum = true;
                break;
            }
        }
        return (isAlphaNum);
    }

}
