package com.ascap.apm.common.utils.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * PREP Constants Helper Class. Define all application constants in this class
 *
 * @author Pratap Sanikommu
 * @version $Revision: 1.30 $ $Date: Mar 07 2011 14:55:30 $
 */

public class Constants {

    private Constants() {

    }

    /**
     * Action class mapping forward constants
     */

    /**
     * The session scope attribute under which the User object for the currently logged in user is stored.
     */

    public static final String SINGLE_OUTPUT_VALUE_TYPE_TARGET_DIS = "TARGET_DIS";

    public static final String SINGLE_OUTPUT_QUERY_TARGET_DIS =
        "SELECT TGTSURVYEARQTR FROM APM_SURV_DATE WHERE ACTIVE_FL = 'Y' AND DEL_FL = 'N'";

    public static final String USER_KEY = "user";

    // constant to represent 100%
    public static final String HUNDRED = "100.0";

    public static final String APPLICATION_LOGGED_PREP = "PREP";

    public static final String APPLICATION_LOGGED_MA = "MA";

    public static final String APPLICATION_LOGGED_PREP2MA = "PREP2MA";

    public static final String APPLICATION_LOGGED_MOBILE = "MOBILE";

    // Percentage of tolerance that can be accepted while creating Agreements.

    public static final double TOLERANCE_PERCENTAGE = 0.06;

    // Module names
    public static final byte MEMBERSHIP_MODULE = 1;

    public static final byte ROYALTY_MODULE = 2;

    public static final byte DISTRIBUTION_MODULE = 3;

    public static final byte USAGE_MODULE = 4;

    public static final byte WORKS_MODULE = 5;

    public static final byte AGREEMENTS_MODULE = 6;

    public static final byte MANDATES_MODULE = 7;

    public static final byte INQUIRY_MODULE = 8;

    public static final byte ADJUSTMENTS_MODULE = 9;

    public static final byte LRS_MODULE = 10;

    public static final byte PREFERENCES_MODULE = 11;

    public static final byte ADMINISTRATION_MODULE = 12;

    public static final byte MODELING_MODULE = 13;

    public static final byte DOCUMENTS_MODULE = 14;

    public static final String IS_TRIGGER_CREATRED = "iSTriggerCreated";

    public static final String TRIGGER_CREATRED_VALUE = "true";

    public static final String MINIMUM_POSSIBLE_DATE_IN_DB = "01/01/1800";

    public static final String MAXIMUM_POSSIBLE_DATE_IN_DB = "12/31/9999";

    public static final String MINIMUM_POSSIBLE_TIME = "00:00:00";

    public static final String MAXIMUM_POSSIBLE_TIME = "23:59:59";

    public static final String DELETE_FLAG_YES = "Y";

    public static final String DELETE_FLAG_NO = "N";

    public static final String ADJUSTMENT_INDICATOR_TRUE = "Y";

    public static final String ADJUSTMENT_INDICATOR_FALSE = "N";

    public static final String SORT_ORDER_ASCENDING = " ASC ";

    public static final String SORT_ORDER_DESCENDING = " DESC ";

    public static final String CRYSTAL_ADMINISTRATOR = "Administrator";

    public static final String CRYSTAL_PASSWORD = "AAss12::";

    public static final String CRYSTAL_SERVER_NAME = "610ps";

    public static final String CRYSTAL_AUTHENTICATION_SOURCE = "secEnterprise";

    public static final String MEMBERSHIP_REPORT_FOLDER = "Membership";

    public static final String ROYALTY_REPORT_FOLDER = "Royalty";

    public static final String DISTRIBUTION_REPORT_FOLDER = "Distribution";

    public static final String USAGE_REPORT_FOLDER = "Usage";

    public static final String INQUIRY_REPORT_FOLDER = "Inquiry";

    public static final String ADJUSTMENT_REPORT_FOLDER = "Adjustment";

    public static final String WORK_REPORT_FOLDER = "Works";

    public static final String AGREEMENT_REPORT_FOLDER = "Agreements";

    public static final String MANDATE_REPORT_FOLDER = "Mandates";

    public static final String WORKS_REPORT_FOLDER = "Works";

    public static final String EWA_COPYRIGHT_FOLDER = "EWA Copyright";

    public static final String MODELING_REPORT_FOLDER = "Modeling";

    public static final String LRS_REPORT_FOLDER = "LRS";

    public static final String LRS_REPORT_01 = "LS-RP01";

    public static final String RPT_STA_PEND = "PE";

    public static List<Object> getCustomReportList() {
        List<Object> customReportList = new ArrayList<>();
        customReportList.add(LRS_REPORT_01);
        return customReportList;
    }

    // This is the Higher level Folder containing Module Level Folders in Crystal Enterprise
    // Ex: For UT - This will be "Testing" which will have all "Tst_XXXModule" Sub Folders
    // For Adj - This will be "Adj" which will have all "Adj_XXXModule" Sub Folders
    // For QA - This will be "PREP Reports QA" which will have all "XXXModule" Sub Folders
    public static final String CRYSTAL_ENV_FOLDER = "Testing";

    // For search lookup parameters
    public static final String SRCH_TYP_BEGINS = "BGNS";

    public static final String SRCH_TYP_CONTAINS = "CNTS";

    public static final String SRCH_TYP_EXACT = "EXCT";

    public static final String SRCH_TYP_FUZZY_SSA = "FZZY";

    public static final String SRCH_TYP_COMPRESS_LGCY = "CMPRS";

    public static final String SRCH_NAME = "NAME";

    public static final String SRCH_TITLE = "TITLE";

    public static final String DISPLAY_FORMAT_TYPE_AMOUNT = "amount";

    public static final String DISPLAY_FORMAT_TYPE_CREDITS = "credits";

    public static final String REQUEST_TYP_CAT_CWR = "CTLG_CWR";

    public static final String REQUEST_TYP_BULK_AMEND = "BLKAMD";

    public static final String REQUEST_TYP_AWD_NOTF_LTR = "AWD_NTF";

    public static final String REQUEST_STATUS_PENDING = "PE";

    public static final String REQUEST_MODE_ONLINE = "ONL";

    public static final String REQUEST_MODE_BATCH = "BAT";

    public static final String AGREEMENTS_MANDATES_SEARCH_ACTIVE = "ACT";

    public static final String AGREEMENTS_MANDATES_SEARCH_INACTIVE = "NACT";

    public static final String BACK_TO_SEARCH_CRITERIA = "BACK2SEARCH";

    public static final String PAGE_NAVIGATION_FIRST = "FIRST";

    public static final String PAGE_NAVIGATION_LAST = "LAST";

    public static final String PAGE_NAVIGATION_NEXT = "NEXT";

    public static final String PAGE_NAVIGATION_PREVIOUS = "PREV";

    public static final String PAGE_NAVIGATION_CURRENT = "CURR";

    public static final String APPLICATION_LOGGED_ACE = "ACE";
}
