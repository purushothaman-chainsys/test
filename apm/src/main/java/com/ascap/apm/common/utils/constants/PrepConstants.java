package com.ascap.apm.common.utils.constants;

import java.util.HashMap;

/**
 */
public class PrepConstants {

    private PrepConstants() {

    }

    public static final String REPORT_KIND_WEBI = "Webi";

    public static final String REPORT_KIND_CRYSTALREPORT = "CrystalReport";

    public static final String TYPE_NUMBER = "0";

    public static final String TYPE_CURRENCY = "1";

    public static final String TYPE_BOOLEAN = "2";

    public static final String TYPE_DATE = "3";

    public static final String TYPE_TIME = "4";

    public static final String TYPE_DATETIME = "5";

    public static final String TYPE_STRING = "6";

    public static final String WEBI_PROMPT_TYPE_TEXT = "Text";

    public static final String WEBI_PROMPT_TYPE_NUMERIC = "Numeric";

    public static final String WEBI_PROMPT_TYPE_DATE = "Date";

    public static HashMap<String, String> WEBI_TO_REPORT_MAPPING = new HashMap<>();
    static {
        WEBI_TO_REPORT_MAPPING.put(WEBI_PROMPT_TYPE_TEXT, TYPE_STRING);
        WEBI_TO_REPORT_MAPPING.put(WEBI_PROMPT_TYPE_NUMERIC, TYPE_NUMBER);
        WEBI_TO_REPORT_MAPPING.put(WEBI_PROMPT_TYPE_DATE, TYPE_DATE);
    }

    public static final String WEBI_PROMPT_CORDINALITY_MULTIPLE = "Multiple";

    public static final String WEBI_PROMPT_CORDINALITY_SINGLE = "Single";

    public static final String WEBI_PROMPT_OPTIONAL_TRUE = "true";

    public static final String WEBI_PROMPT_OPTIONAL_FALSE = "false";

    public static final String REPORT_STATUS_SUCCESS = "Success";

    public static final String REPORT_STATUS_FAIL = "Fail";

    public static final String REPORT_STATUS_PENDING = "Pending";

    public static final String REPORT_STATUS_RUNNING = "Running";

    public static final String REPORT_STATUS_UNKNOWN = "Unknown";

    public static final String REPORT_STATUS_PAUSED = "Paused";

    public static final String REPORT_SCHEDULE_NOW = "F";

    public static final String REPORT_SCHEDULE_LATTER = "T";

    public static final String REPORT_SCHEDULE_TIME = "23:00";

    public static final String REPORT_VIEW = "1";

    public static final String REPORT_VIEW_REPORT_INSTANCE = "2";

    public static final String REPORT_EXECUTE = "3";

    public static final String REPORT_SCHEDULE = "4";

    public static final String REPORT_SHOWINPUTS = "5";

    public static final String REPORT_CANCEL_INSTANCE = "6";

    public static final String REPORT_VIEW_REQ_DTLS = "20";

    public static final String ADMIN_UPDATE_REPORT_VALIDATE = "7";

    public static final String ADMIN_REPORT_VALIDATE_INF = "8";

    public static final String ADMIN_UPDATE_VALIDATE_INF = "9";

    public static final String ADMIN_SEARCH_REPORT_INF = "2";

    public static final String ADMIN_SHOW_MODULE_INF = "1";

    // Reports Archives
    public static final String REPORT_VIEW_ARCHIVES = "10";

    public static final String RETURN_REPORT = "11";

    public static final int DEFAULT_USR_INST_CNT = 14;

    public static final int DEFAULT_ARCH_INST_CNT = 28;

    public static final int DEFAULT_INST_MAX_DAYS_CNT = 25;

    public static final String MAX_INST_FLAG_YES = "Y";

    public static final String MAX_INST_FLAG_NO = "N";

    public static final String MAX_DAYS_FLAG_YES = "Y";

    public static final String MAX_DAYS_FLAG_NO = "N";

    public static final String REPORT_USER = "success";

    public static final String REPORT_EXPORT_FORMAT_CSV = "CSV";

    public static final String REPORT_EXPORT_FORMAT_RPT = "rpt";

    public static final String REPORT_EXPORT_FORMAT_PDF = "PDF";

    public static final String REPORT_FALSE = "F";

    public static final String REPORT_TRUE = "T";

    public static final String DELETE_FLAG_YES = "Y";

    public static final String DELETE_FLAG_NO = "N";

    public static final String ADMIN_STATUS_SUCCESS = "report updated successfully in database";

    public static final String PREP_REPORT_MAIL_SUBJECT = "Report Failed ";

    public static final int DEFAULT_MAX_FILESIZE_IN_BYTES_INQ_ATTACHMENT_WIT = 5242880;// 5Mb

    public static final int DEFAULT_MAX_FILESIZE_IN_BYTES_INQ_ATTACHMENT_MA = 10485760;// 10Mb

    public static final int DEFAULT_MAX_FILESIZE_IN_BYTES_INQ_ATTACHMENT_PREP = 31457280;

    public static final int ORACLE_TIME_OUT_ERROR_CODE = 1013;

    public static final String TIME_OUT_GENERIC_MESSAGE_KEY = "errors.prep.common.SQLTimeOut";

    public static final String CALL2COPYRIGHT_STATUS_PENDING = "Pending";

    public static final String CALL2COPYRIGHT_STATUS_INPROGRESS = "In Progress";

    public static final String BATCH_STATUS_CODE_PROCESSING = "PR";

    public static final String BATCH_STATUS_CODE_COMPLETED = "CO";

    public static final String BATCH_STATUS_CODE_ERROR = "ER";

    public static final String BATCH_STATUS_CODE_CANCELLED = "CA";

    public static final String BATCH_STATUS_CODE_DUPLICATE = "DU";

    public static final String BATCH_STATUS_CODE_NEW = "NE";

    public static final String BATCH_STATUS_CODE_PENDING = "PE";

    public static final String BATCH_STATUS_CODE_ABORTED = "AB";

    public static final String THEME_DEFAULT = "TH019";

    public static final String THEME_RANDOM = "RNDM";

    public static final String THEME_BG_FLAG = "Y";

    public static final String DATASOURCE_DW = "DW";

    public static final String DATASOURCE_ORACLE = "ORCL";

    public static final String CONFIG_PATH = System.getProperty("config.path") + "/";

}
