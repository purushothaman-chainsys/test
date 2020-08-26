package com.ascap.apm.common.utils.constants;

import java.util.HashMap;
import java.util.Map;

import com.ascap.apm.common.utils.CharacterSetUtils;

/**
 * Defines Constants Used by APM.
 * 
 * @author Manoj Puli
 * @version $Revision: 1.48 $ $Date: May 05 2011 13:55:18 $
 */
public class UsageConstants {

    private UsageConstants() {

    }

    public static final String WRK_MATCH_TYPE_WRK_PERF = "WRK_PERF";

    public static final String WRK_MATCH_TYPE_CATALOG = "CATALOG";

    public static final String YOUTUBE_URL_PREFIX = "https://www.youtube.com/watch?v=";

    public static final String APM_LEARNED_MATCH_TYPE_PERFORMER = "PFR";

    public static final String APM_LEARNED_MATCH_TYPE_WRITER = "WRT";

    public static final String LM_BULK_UPD_REQ_STATUS_PENDING = "PE";

    public static final String LM_BULK_UPD_REQ_STATUS_CANCELLED = "CA";

    public static final String PENDING_BATCH_VALIDATIONS = "PBVLD";

    public static final String BULK_REQUEST_STATUS_PENDING = "PE";

    public static final String BULK_REQUEST_TYPE_UPDATE = "UPD";

    public static final String BULK_REQUEST_TYPE_DELETE = "DEL";

    public static final String BULK_REQUEST_TYPE_UNMATCH = "UNM";

    public static final String BULK_REQUEST_TYPE_UNDELETE = "UND";

    public static final String APM_LEARNED_MATCH_REQ_TYP_ADD = "ADD";

    public static final String APM_LEARNED_MATCH_REQ_TYP_UPDATE = "UPD";

    public static final String APM_LEARNED_MATCH_REQ_TYP_GET = "GET";

    public static final int APM_ERROR_STATUS = 2;

    public static final int APM_WARNING_STATUS = 1;

    public static final String APM_MATCH_TYPE_MANUAL = "MAN";

    public static final String APM_MATCH_TYPE_NO_MATCH = "NMT";

    public static final String APM_MATCH_TYPE_PENDING = "PND";

    public static final String WRK_PERF_DELETE_TYPE = "MOVE";

    public static final String PERF_HDR_ADD = "PERF_HDR_ADD";

    public static final String PERF_HDR_UPDATE = "PERF_HDR_UPDATE";

    public static final String PERF_HDR_DELETE = "PERF_HDR_DELETE";

    public static final String WRK_PERF_ADD = "WRK_PERF_ADD";

    public static final String WRK_PERF_UPDATE = "WRK_PERF_UPDATE";

    public static final String WRK_PERF_DELETE = "WRK_PERF_DELETE";

    public static final String ADD_TO_MEDLEY = "ADD_TO_MEDLEY";

    public static final String DELETE_FROM_MEDLEY = "DELETE_FROM_MEDLEY";

    public static final String PERF_BULK_REQUEST = "PERF_BULK_REQUEST";

    public static final String PROGRAM_PERFORMANCE_TYPE = "ProgramPerformance";

    public static final String WORK_PERFORMANCE_TYPE = "WorkPerformance";

    public static final String WORK_PERF_SHARE_TYPE = "WorkPerformanceShare";

    public static final String SOURCE_SYSTEM_APP = "APP";

    public static final String SOURCE_SYSTEM_PCH = "PCH";

    public static final String SOURCE_SYSTEM_TVPP = "TVPP";

    public static final String SOURCE_SYSTEM_FOREIGN = "FOREIGN";

    // Batch should remove this source system and if it receives anything with 'FRN' should convert it to FOREIGN
    public static final String SOURCE_SYSTEM_INVALID_FOREIGN = "FRN";

    public static final String SOURCE_SYSTEM_PREP = "PREP";

    public static final String SOURCE_SYSTEM_NABT = "NABT";

    public static final String SOURCE_SYSTEM_PROXY = "PROXY";

    public static final String SOURCE_SYSTEM_PERFORMANCE_SPECIAL = "PS";

    public static final String SOURCE_SYSTEM_OVERHEAD_SPECIAL = "POS";

    public static final String SOURCE_SYSTEM_ADD_AND_PROMO = "ADPROMO";

    public static final String SOURCE_SYSTEM_TVMU = "TVMU"; // CMT is changed to TVMU

    public static final String SOURCE_SYSTEM_INTERNET = "Internet";

    public static final String SURVEY_DATE_TYPE_SUB_SAMPLE = "SS";

    public static final String SURVEY_DATE_TYPE_EDUCATIONAL_CONCERT = "E";

    public static final String SURVEY_DATE_TYPE_LIBRARY = "LIB";

    public static final String SURVEY_DATE_DESCRIPTION_TYPE_SUB_SAMPLE = "Sub Sample Survey";

    public static final String SURVEY_DATE_DESCRIPTION_TYPE_EDUCATIONAL_CONCERT = "Educational Concert Sample Survey";

    public static final String SURVEY_DATE_DESCRIPTION_TYPE_LIBRARY = "Library Sample Survey";

    protected static final Map<String, String> SURVEY_DATES_DESCRIPTION = new HashMap<>();
    static {
        SURVEY_DATES_DESCRIPTION.put(SURVEY_DATE_TYPE_SUB_SAMPLE, SURVEY_DATE_DESCRIPTION_TYPE_SUB_SAMPLE);
        SURVEY_DATES_DESCRIPTION.put(SURVEY_DATE_TYPE_EDUCATIONAL_CONCERT,
            SURVEY_DATE_DESCRIPTION_TYPE_EDUCATIONAL_CONCERT);
        SURVEY_DATES_DESCRIPTION.put(SURVEY_DATE_TYPE_LIBRARY, SURVEY_DATE_DESCRIPTION_TYPE_LIBRARY);
    }

    public static final String PLAY_LIST_FLAG_AVERAGE = "A";

    public static final String PLAY_LIST_FLAG_SPECIAL = "S";

    public static final String PERFORMANCE_POSTING_INDICATOR_UNPOST = "U";

    public static final String PERFORMANCE_POSTING_INDICATOR_REMATCH = "M";

    public static final String PERFORMANCE_POSTING_INDICATOR_REFRESH = "R";

    // aDJUSTMENT tRIGGER rELATED
    public static final String USAGE_MODULE_CODE_FOR_ADJ = "U";

    public static final String STAGING_CONTROL_BATCH_STATUS_CODE_LOADING = "01";

    public static final String STAGING_CONTROL_BATCH_STATUS_CODE_READY = "02";

    public static final String STAGING_CONTROL_BATCH_STATUS_CODE_INPROGRESS = "03";

    public static final String STAGING_CONTROL_BATCH_STATUS_CODE_PROCESSED = "04";

    public static final String STAGING_CONTROL_BATCH_STATUS_CODE_ERROR = "05";

    public static final String STAGING_CONTROL_BATCH_STATUS_CODE_DELETE = "06";

    public static final String STAGING_CONTROL_BATCH_STATUS_CODE_ARCHIVED = "07";

    public static final String STAGING_CONTROL_ACTION_CODE_UNPOST = "03";

    public static final String STAGING_CONTROL_PROCESS_TYPE_CODE_IMMEDIATE = "01";

    public static final String STAGING_CONTROL_PROCESS_TYPE_CODE_SCHEDULED = "02";

    public static final String STAGING_UNPOST_BATCH_STATUS_CODE_LOADING = "01";

    public static final String STAGING_UNPOST_BATCH_STATUS_CODE_READY = "02";

    public static final String STAGING_UNPOST_BATCH_STATUS_CODE_INPROGRESS = "01";

    public static final String STAGING_UNPOST_BATCH_STATUS_CODE_PROCESSED = "02";

    public static final String STAGING_UNPOST_BATCH_STATUS_CODE_ERROR = "01";

    public static final String STAGING_UNPOST_BATCH_STATUS_CODE_DELETE = "02";

    public static final String STAGING_UNPOST_BATCH_STATUS_CODE_ARCHIVED = "02";

    public static final String STAGING_UNPOST_ACTION_CODE_UNPOST = "0";

    public static final String STAGING_UNPOST_ACTION_CODE_REMATCH = "1";

    public static final String STAGING_UNPOST_ACTION_CODE_REFRESH = "2";

    public static final String STAGING_UNPOST_PROCESS_TYPE_CODE_IMMEDIATE = "01";

    public static final String STAGING_UNPOST_PROCESS_TYPE_CODE_SCHEDULED = "02";

    // Remember to change UsageHelper.isPartOfRuleGroup() method if you are changing these values - shyam
    public static final String US_RULE_GRP_TV_PER_PROGRAM = "TVR";

    public static final String US_RULE_GRP_ALL = "ALL";

    public static final String US_RULE_GRP_DEFAULT_MUSIC_USER = "DEF";

    public static final String US_RULE_GRP_DEFAULT_TV = "DTV";

    public static final String US_RULE_GRP_TV_NETWORK = "TVN";

    public static final String US_RULE_GRP_TV_SECONDARY_NETWORK = "TVS";

    public static final String US_RULE_GRP_TV_PAY_PER_VIEW = "TVP";

    public static final String US_RULE_GRP_LIVE_POP_CONCERTS = "POP";

    public static final String US_RULE_GRP_INTERNET_STREAMING = "NET";

    public static final String US_RULE_GRP_INTERACTIVE = "ACT";

    public static final String US_RULE_GRP_SRE = "SRE";

    public static final String US_RULE_GRP_GENERAL_BACKGROUND = "BCK";

    public static final String US_RULE_GRP_FOREGROUND = "FOR";

    public static final String US_RULE_GRP_INTERNATIONAL_INCOMING = "INT";

    public static final String US_RULE_GRP_INTERNET_AUDIO_VISUAL = "NAV";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_PRG_DATE = "PUDT";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_PRG_NUMBER_OF_HOOKS = "PUSNH";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_PRG_SURVEY_TYPE = "PUSUD";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_PRG_SAMPLE_TYPE = "PUSAM";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_PRG_AVERAGE_SPECIAL_SET_FLAG = "PUAVG";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_PRG_NO_CONCERTS = "PUNOC";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_PRG_NO_PLAYS = "PUNOP";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_PRG_PROGRAM_REVENUE = "PUREV";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_PRG_PPR_VALUE = "PUPPR";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_PRG_MUSIC_USER_ID = "PUMUS";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_WRK_WORK_ID = "WUWID";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_WRK_DURATION = "WUDUR";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_WRK_USE_TYPE = "WUUST";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_WRK_SRE_POINT = "WUSRE";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_WRK_NO_PLAYS = "WUNOP";

    public static final String ADJ_TRIG_NAME_CODE_ADD_TO_MEDLEY = "WMMED";

    public static final String ADJ_TRIG_NAME_CODE_REMOVE_FROM_MEDLEY = "WRMED";

    public static final String ADJ_TRIG_NAME_CODE_UNPOST_PERFORMANCE = "PUNPO";

    public static final String ADJ_TRIG_NAME_CODE_REMATCH_PERFORMANCE = "PREMT";

    public static final String ADJ_TRIG_NAME_CODE_DELETE_WORK_PERFORMANCE = "UDEWP";

    public static final String ADJ_TRIG_NAME_CODE_ADD_WORK_PERFORMANCE = "UADWP";

    // public static final String ADJUSTMENT_TRIGGER_
    public static final String ADJ_TRIG_NAME_CODE_UPDATE_CUE_WORKID = "CWUIWI";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_CUE_WORKTITLE = "CWUWT";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_CUE_DURATION = "CWUDR";

    public static final String ADJ_TRIG_NAME_CODE_UPDATE_CUE_USETYPE = "CWUUS";

    public static final String ADJ_TRIG_NAME_CODE_ADD_CUE_WORK_PERFORMANCE = "CWA";

    public static final String ADJ_TRIG_NAME_CODE_DELETE_CUE_WORK_PERFORMANCE = "CWD";

    /**
     * Defines the duration in seconds for program segment elapsed time. according to business rules when applied for a
     * program performance would generate a warning. The value is 6 hours.
     */
    public static final long PROGRAM_SEGMENT_WARN_ELAPSED_TIME_SECONDS = 21600L;

    public static final String PROGRAM_OVERLAP_CODE_REGIONAL_STATIONS = "POC004";

    // SRE Duration that is more than 60 Minutes is to be prorated.
    public static final long SRE_PRO_RATE_DURATION_IN_SECONDS = 60L * 60L;

    // This is the Maximum value stored in record in the table column SRE_DUR_RNG.SRE_MAX_DUR. Plese note it the value
    // that is present in the record. (not what is possible)
    public static final long SRE_HIGHEST_DURATION_RANGE_MINUTES = 9999L;

    public static final String SRE_HIGHEST_DURATION_RANGE_MINUTES_STRING = "9999";

    public static final String SRE_CATEGORY_CODE_A = "A";

    public static final String USE_TYPE_FILTER_ALL = "ALL";

    public static final String USE_TYPE_FILTER_FEATURED = "FEATURED";

    public static final String USE_TYPE_FILTER_MEDLEY = "MEDLEY";

    public static final String USE_TYPE_FILTER_SRE = "SRE";

    public static final String USE_TYPE_FILTER_LIBRARY = "LIBRARY";

    // Fallback is used only when PREP_PROPERTIES constants are not defined properly
    public static final long DEFAULT_FALLBACK_USAGE_THRESHOLD_FOR_ONLINE_VALIDATION = 100000L;

    public static final String VLDN_LKUPS_KEY_USE_TYPE_ALL = "UsageValidationsUseTypes_ALL";

    public static final String VLDN_LKUPS_KEY_USE_TYPE_FEATURED = "UsageValidationsUseTypes_IS_FEATURED";

    public static final String VLDN_LKUPS_KEY_USE_TYPE_MEDLEY = "UsageValidationsUseTypes_IS_MEDLEY";

    public static final String VLDN_LKUPS_KEY_USE_TYPE_SRE = "UsageValidationsUseTypes_IS_SRE";

    public static final String VLDN_LKUPS_KEY_USE_TYPE_LIBRARY = "UsageValidationsUseTypes_IS_LIBRARY";

    // Explicitly using UsageConstants.US_RULE_GRP_XXXXXX constant to show that any changes to the rule group code has
    // dependencies in other places (UsageHelper) and Lookup.xml etc.
    public static final String VLDN_KEY_PREFIX_US_RULE_GRP = "UsageValidationsRuleGroupMembers_";

    public static final String VLDN_LKUPS_KEY_US_RULE_GRP_TV_PER_PROGRAM =
        UsageConstants.VLDN_KEY_PREFIX_US_RULE_GRP + UsageConstants.US_RULE_GRP_TV_PER_PROGRAM;

    public static final String VLDN_LKUPS_KEY_US_RULE_GRP_ALL =
        UsageConstants.VLDN_KEY_PREFIX_US_RULE_GRP + UsageConstants.US_RULE_GRP_ALL;

    public static final String VLDN_LKUPS_KEY_US_RULE_GRP_DEFAULT_MUSIC_USER =
        UsageConstants.VLDN_KEY_PREFIX_US_RULE_GRP + UsageConstants.US_RULE_GRP_DEFAULT_MUSIC_USER;

    public static final String VLDN_LKUPS_KEY_US_RULE_GRP_DEFAULT_TV =
        UsageConstants.VLDN_KEY_PREFIX_US_RULE_GRP + UsageConstants.US_RULE_GRP_DEFAULT_TV;

    public static final String VLDN_LKUPS_KEY_US_RULE_GRP_TV_NETWORK =
        UsageConstants.VLDN_KEY_PREFIX_US_RULE_GRP + UsageConstants.US_RULE_GRP_TV_NETWORK;

    public static final String VLDN_LKUPS_KEY_US_RULE_GRP_TV_SECONDARY_NETWORK =
        UsageConstants.VLDN_KEY_PREFIX_US_RULE_GRP + UsageConstants.US_RULE_GRP_TV_SECONDARY_NETWORK;

    public static final String VLDN_LKUPS_KEY_US_RULE_GRP_TV_PAY_PER_VIEW =
        UsageConstants.VLDN_KEY_PREFIX_US_RULE_GRP + UsageConstants.US_RULE_GRP_TV_PAY_PER_VIEW;

    public static final String VLDN_LKUPS_KEY_US_RULE_GRP_LIVE_POP_CONCERTS =
        UsageConstants.VLDN_KEY_PREFIX_US_RULE_GRP + UsageConstants.US_RULE_GRP_LIVE_POP_CONCERTS;

    public static final String VLDN_LKUPS_KEY_US_RULE_GRP_INTERNET_STREAMING =
        UsageConstants.VLDN_KEY_PREFIX_US_RULE_GRP + UsageConstants.US_RULE_GRP_INTERNET_STREAMING;

    public static final String VLDN_LKUPS_KEY_US_RULE_GRP_INTERACTIVE =
        UsageConstants.VLDN_KEY_PREFIX_US_RULE_GRP + UsageConstants.US_RULE_GRP_INTERACTIVE;

    public static final String VLDN_LKUPS_KEY_US_RULE_GRP_SRE =
        UsageConstants.VLDN_KEY_PREFIX_US_RULE_GRP + UsageConstants.US_RULE_GRP_SRE;

    public static final String VLDN_LKUPS_KEY_US_RULE_GRP_GENERAL_BACKGROUND =
        UsageConstants.VLDN_KEY_PREFIX_US_RULE_GRP + UsageConstants.US_RULE_GRP_GENERAL_BACKGROUND;

    public static final String VLDN_LKUPS_KEY_US_RULE_GRP_FOREGROUND =
        UsageConstants.VLDN_KEY_PREFIX_US_RULE_GRP + UsageConstants.US_RULE_GRP_FOREGROUND;

    public static final String VLDN_LKUPS_KEY_US_RULE_GRP_INTERNATIONAL_INCOMING =
        UsageConstants.VLDN_KEY_PREFIX_US_RULE_GRP + UsageConstants.US_RULE_GRP_INTERNATIONAL_INCOMING;

    public static final String VLDN_LKUPS_KEY_US_RULE_GRP_INTERNET_AUDIO_VISUAL =
        UsageConstants.VLDN_KEY_PREFIX_US_RULE_GRP + UsageConstants.US_RULE_GRP_INTERNET_AUDIO_VISUAL;

    /**
     * Defines the Duration in seconds for a work performance with Serious Music Work and Use Type Featured. according
     * to business rules when applied for a work performance would generate a warning. The value is 4 minutes.
     */
    public static final long SERIOUS_MUSIC_WORK_FEATURED_USE_TYPE_WARN_DURATION_SECONDS = 240L;

    /**
     * IMPLEMENTATION of 31201 and 31202 (FM1, Music Choice etc.) validations are now based on DIS_BAS_CR_CALC Table
     * Refer to DistributionConstants.BASE_CREDIT_CALCULATION_METHOD_*
     */

    public static final String REJECT_FILE_SUFFIX = "_Rejected.txt";

    public static final String ERRORS_FILE_SUFFIX = "_ErrCde.txt";

    public static final String LEGACY_MUSIC_USER_TYPE_PREP = "PREP";

    public static final String LEGACY_MUSIC_USER_TYPE_CALL = "CALL";

    public static final Map<String, String> MUSIC_USER_RELATED_ERRORS_TEMP = new HashMap<>();

    static {
        MUSIC_USER_RELATED_ERRORS_TEMP.put(UsageMessageCodes.MSG_ALL_R_51506, UsageMessageCodes.MSG_ALL_R_51506);
        MUSIC_USER_RELATED_ERRORS_TEMP.put(UsageMessageCodes.MSG_ALL_R_51507, UsageMessageCodes.MSG_ALL_R_51507);
        MUSIC_USER_RELATED_ERRORS_TEMP.put(UsageMessageCodes.MSG_ALL_R_51508, UsageMessageCodes.MSG_ALL_R_51508);
        MUSIC_USER_RELATED_ERRORS_TEMP.put(UsageMessageCodes.MSG_ALL_R_51509, UsageMessageCodes.MSG_ALL_R_51509);
        MUSIC_USER_RELATED_ERRORS_TEMP.put(UsageMessageCodes.MSG_ALL_R_51510, UsageMessageCodes.MSG_ALL_R_51510);
        MUSIC_USER_RELATED_ERRORS_TEMP.put(UsageMessageCodes.MSG_ALL_R_51511, UsageMessageCodes.MSG_ALL_R_51511);
        MUSIC_USER_RELATED_ERRORS_TEMP.put(UsageMessageCodes.MSG_ALL_R_51512, UsageMessageCodes.MSG_ALL_R_51512);
    }

    public static final String SDBINF_GET_PROGRAM_PERFORMANCE_CURRENT_VERSION_NUMBER =
        "GET_PROGRAM_PERFORMANCE_CURRENT_VERSION_NUMBER";

    public static final String SDBINF_GET_WORK_PERFORMANCE_CURRENT_VERSION_NUMBER =
        "GET_WORK_PERFORMANCE_CURRENT_VERSION_NUMBER";

    public static final String SDBINF_GET_PROGRAM_PERFORMANCE_HIGHEST_VERSION_NUMBER =
        "GET_PROGRAM_PERFORMANCE_HIGHEST_VERSION_NUMBER";

    public static final String SDBINF_GET_PROGRAM_PERFORMANCE_IS_DISTRIBUTED = "GET_PROGRAM_PERFORMANCE_IS_DISTRIBUTED";

    public static final String SDBINF_GET_WORK_PERFORMANCE_HIGHEST_VERSION_NUMBER =
        "GET_WORK_PERFORMANCE_HIGHEST_VERSION_NUMBER";

    public static final String SDBINF_GET_WORK_PERFORMANCE_HIGHEST_WORKSEQUENCE_NUMBER =
        "GET_WORK_PERFORMANCE_HIGHEST_WORKSEQUENCE_NUMBER";

    public static final String SDBINF_GET_WORK_PERFORMANCE_IS_DISTRIBUTED = "GET_WORK_PERFORMANCE_IS_DISTRIBUTED";

    public static final String SDBINF_GET_WORK_PERFORMANCE_LOWEST_DISTRIBUTION_ID =
        "GET_WORK_PERFORMANCE_LOWEST_DISTRIBUTION_ID";

    public static final String SDBINF_GET_DURATION_SUM_OF_WORK_PERFORMANCES = "GET_DURATION_SUM_OF_WORK_PERFORMANCES";

    public static final String SDBINF_GET_IF_VALID_MUSIC_USER_PARTY = "GET_IF_VALID_MUSIC_USER_PARTY";

    public static final String SDBINF_GET_IF_WORK_EXIST = "GET_IF_WORK_EXIST";

    public static final String SDBINF_GET_IF_VALID_SOC_CODE = "GET_IF_VALID_SOC_CODE";

    public static final String SDBINF_GET_IF_VALID_INTERNATIONAL_REVENUE_CLASS =
        "GET_IF_VALID_INTERNATIONAL_REVENUE_CLASS";

    public static final String SDBINF_GET_IF_PART_OF_BASE_CREDITS_CALCULATION_METHOD_FM1_MUZAK =
        "GET_IF_PART_OF_BASE_CREDITS_CALCULATION_METHOD_FM1_MUZAK";

    public static final String SDBINF_GET_IF_PART_OF_BASE_CREDITS_CALCULATION_METHOD_MUSIC_CHOICE =
        "GET_IF_PART_OF_BASE_CREDITS_CALCULATION_METHOD_MUSIC_CHOICE";

    public static final String SDBINF_GET_NOF_WORK_PERFORMANCES_IN_PERFORMACE_HEADER =
        "SDBINF_GET_NOF_WORK_PERFORMANCES_IN_PERFORMACE_HEADER";

    public static final String SDB_MEDLEYINFO_GET_WORK_PERFORMANCE_SMALLEST_WORKSEQUENCE_NUMBER =
        "GET_WORK_PERFORMANCE_SMALLEST_WORKSEQUENCE_NUMBER";

    public static final String SDB_MEDLEYINFO_GET_WORK_PERFORMANCE_HIGHEST_MEDLEY_SEQUENCE_NUMBER =
        "GET_WORK_PERFORMANCE_HIGHEST_MEDLEY_SEQUENCE_NUMBER";

    public static final String SDB_MEDLEYINFO_GET_IF_PART_OF_MEDLEY = "GET_IF_PART_OF_MEDLEY";

    public static final String SDB_MEDLEYINFO_GET_IF_DUPLICATE_WRK_IN_MEDLEY = "GET_IF_DUPLICATE_WRK_IN_MEDLEY";

    public static final String SDB_MEDLEYINFO_GET_IF_MEDLEY_BUT_SEQUENCE_NUMBER_MISSING =
        "GET_IF_MEDLEY_BUT_SEQUENCE_NUMBER_MISSING";

    public static final String BOOLEAN_STRING_Y = "Y";

    public static final String BOOLEAN_STRING_N = "N";

    public static final String PERFORMANCES_DISTRIBUTION_INDICATOR_DISTRIBUTED = "DP";

    public static final String PERFORMANCES_DISTRIBUTION_INDICATOR_NON_DISTRIBUTED = "NP";

    public static final String SEARCH_RESULTS_COUNT_POLICY_EXACT_COUNT = "EXACT_COUNT";

    public static final String SEARCH_RESULTS_COUNT_POLICY_DONOT_COUNT = "DONOT_COUNT";

    public static final String SEARCH_RESULTS_COUNT_POLICY_LIMIT_TOMAX = "LIMIT_TOMAX";

    public static final String SEARCH_DELETE_FLAG_POLICY_INCLUDE_DELETED_PERFORMANCES = "INCLUDE_DELETED_PERFORMANCES";

    public static final String SEARCH_DELETE_FLAG_POLICY_EXCLUDE_DELETED_PERFORMANCES = "EXCLUDE_DELETED_PERFORMANCES";

    public static final String ISO_FIELD_PH_SERIES_TITLE = "1";

    public static final String ISO_FIELD_PH_PROGRAM_TITLE = "2";

    public static final String ISO_FIELD_PH_SRE_VENUE = "3";

    public static final String ISO_FIELD_PH_SRE_FEATURED_PERFORMER = "4";

    public static final String ISO_FIELD_PH_LIVE_PFR_NA = "7";

    public static final String ISO_FIELD_WP_WORK_TITLE = "5";

    public static final String ISO_FIELD_WP_PERFORMER_NAME = "6";

    public static final Map<String, String> ISO_FIELDS_MAPPING_HASH = new HashMap<>();

    static {
        ISO_FIELDS_MAPPING_HASH.put(UsageConstants.ISO_FIELD_PH_SERIES_TITLE, CharacterSetUtils.WORK_TITLE_TYPE);
        ISO_FIELDS_MAPPING_HASH.put(UsageConstants.ISO_FIELD_PH_PROGRAM_TITLE, CharacterSetUtils.WORK_TITLE_TYPE);
        ISO_FIELDS_MAPPING_HASH.put(UsageConstants.ISO_FIELD_PH_SRE_VENUE, CharacterSetUtils.ADDRESS_TYPE);
        ISO_FIELDS_MAPPING_HASH.put(UsageConstants.ISO_FIELD_PH_SRE_FEATURED_PERFORMER,
            CharacterSetUtils.PARTY_NAME_TYPE);
        ISO_FIELDS_MAPPING_HASH.put(UsageConstants.ISO_FIELD_WP_WORK_TITLE, CharacterSetUtils.WORK_TITLE_TYPE);
        ISO_FIELDS_MAPPING_HASH.put(UsageConstants.ISO_FIELD_WP_PERFORMER_NAME, CharacterSetUtils.PARTY_NAME_TYPE);
    }

    public static final long USAGE_CLONE_THRESHOLD = 10000L;

    public static final String AUDIT_FIELD_OR_ACTION_PERF_HDR_CREATED = "UAP_CREATE";

    public static final String AUDIT_FIELD_OR_ACTION_PERF_HDR_DELETED = "UAP_DELETE";

    public static final String AUDIT_FIELD_OR_ACTION_PERF_HDR_UPDATED = "UAP_UPDATE";

    public static final String AUDIT_FIELD_OR_ACTION_PERF_HDR_REFRESH = "UAP_REFRESH";

    public static final String AUDIT_FIELD_OR_ACTION_PERF_HDR_REMATCH = "UAP_REMATCH";

    public static final String AUDIT_FIELD_OR_ACTION_PERF_HDR_UNPOST = "UAP_UNPOST";

    public static final String AUDIT_FIELD_OR_ACTION_PERF_HDR_MUSIC_USER_ID = "UFP_MUSIC_USER_ID";

    public static final String AUDIT_FIELD_OR_ACTION_PERF_HDR_SAMPLE_TYPE = "UFP_SAMPLE_TYPE";

    public static final String AUDIT_FIELD_OR_ACTION_PERF_HDR_SURVEY_TYPE = "UFP_SURVEY_TYPE";

    public static final String AUDIT_FIELD_OR_ACTION_WRK_PERF_CREATED = "UAW_CREATE";

    public static final String AUDIT_FIELD_OR_ACTION_WRK_PERF_DELETED = "UAW_DELETE";

    public static final String AUDIT_FIELD_OR_ACTION_WRK_PERF_DURATION = "UFW_DURATION";

    public static final String AUDIT_FIELD_OR_ACTION_WRK_PERF_USE_TYPE = "UFW_USE_TYPE";

    public static final String AUDIT_FIELD_OR_ACTION_WRK_PERF_WORK_ID = "UFW_WORK_ID";

    public static final String AUDIT_FIELD_OR_ACTION_WRK_PERF_MAKE_MEDLEY = "UAW_MAKE_MEDLEY";

    public static final String AUDIT_FIELD_OR_ACTION_WRK_PERF_BREAK_MEDLEY = "UAW_BREAK_MEDLEY";

    public static final String AUDIT_FIELD_OR_ACTION_WRK_PERF_SHR_CREATED = "UAS_CREATE";

    public static final String AUDIT_FIELD_OR_ACTION_WRK_PERF_SHR_DELETED = "UAS_DELETE";

    // File Upload
    public static final String FILE_TYPE_DISTRIBUTION = "DIS";

    public static final String FILE_TYPE_RADIO_LOG_REQUEST = "RLR";

    public static final String FILE_TYPE_RADIO_LOG_USAGE = "RLU";

}
