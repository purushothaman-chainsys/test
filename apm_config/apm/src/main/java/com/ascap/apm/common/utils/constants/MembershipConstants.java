package com.ascap.apm.common.utils.constants;

/**
 * @author shyam_narayana
 */
public class MembershipConstants {

    private MembershipConstants() {

    }

    public static final String STOCKHOLDER_OWNERSHIP_TYPE = "Stockholder";

    public static final String PARTNER_OWNERSHIP_TYPE = "Partner";

    public static final String OFFICER_OWNERSHIP_TYPE = "Officer";

    public static final String SOLEPROPRIETOR_OWNERSHIP_TYPE = "Proprietor";

    public static final String DESIGNATED_REPRESENTATIVE_OWNERSHIP_TYPE = "DesgRepresentative";

    public static final String BUS_TYPE_PARTERNESHIP = "PRT";

    public static final String BUS_TYPE_CORPORATION = "CRP";

    public static final String BUS_TYPE_SOLEPROPRIETOR = "IO";

    public static final String BUS_TYPE_JOINT_VENTURE = "JVN";

    public static final String BUS_TYPE_LLC = "LLC";

    public static final String RELATIONSHIP_DIRECTION_IS_A_TYPE = "isA";

    public static final String RELATIONSHIP_DIRECTION_HAS_A_TYPE = "hasA";

    public static final String DELETE_FLAG_YES = "Y";

    public static final String DELETE_FLAG_NO = "N";

    public static final String GENRE_TYPE_CATEGORY_MEMBERSHIP = "Membership";

    public static final String GENRE_TYPE_CATEGORY_MARKETING = "Marketing";

    public static final String GENRE_TYPE_CATEGORY_MUSIC_USER = "MusicUser";

    public static final String DEFAULT_ENTITLED_EARNER_ALLOCATION_PERCENTAGE = "100";

    public static final String CONTACT_DETAIL_ADDRESS_TYPE = "ADR";

    public static final String CONTACT_DETAIL_EMAIL_TYPE = "EML";

    public static final String CONTACT_DETAIL_PHONE_TYPE = "PHN";

    public static final String CONTACT_DETAIL_WEB_ADR_TYPE = "WEB";

    public static final String EMAIL_TYPE_PRIMARY = "PRI";

    public static final String EMAIL_TYPE_ALTERNATE = "ALT";

    public static final String WEB_ADR_TYPE_HTTP = "HTP";

    public static final String WEB_ADR_TYPE_FTP = "FTP";

    public static final String PHONE_TYPE_MOBILE = "MOBI";

    public static final String PHONE_TYPE_REPRESENTATIVE_PHONE = "REP";

    public static final String PHONE_TYPE_HOME_PHONE = "HOME";

    public static final String PHONE_TYPE_BUSINESS_PHONE = "BUSI";

    public static final String PHONE_TYPE_FAX = "FAX";

    public static final String ADDRESS_TYPE_REPRESENTATIVE = "REP";

    public static final String ADDRESS_TYPE_HOME_ADDRESS = "HOME";

    public static final String ADDRESS_TYPE_OTHER_ADDRESS = "OTHER";

    public static final String ADDRESS_TYPE_OFFICE_ADDRESS = "OFFIC";

    public static final String CORRESPONDENCE_TYPE_PLAYBACK = "PLYBK";

    public static final String CORRESPONDENCE_TYPE_GENERAL_CORSP = "CORSP";

    public static final String CORRESPONDENCE_TYPE_NEW_SERVICE = "NESER";

    public static final String CORRESPONDENCE_TYPE_VOTING = "VOTE";

    public static final String CORRESPONDENCE_TYPE_REPRESENTATION = "REP";

    public static final String CORRESPONDENCE_TYPE_STATEMENT = "STMT";

    public static final String CORRESPONDENCE_TYPE_PMNT_INSTR_TYP = "PMNT";

    public static final String CORRESPONDENCE_TYPE_ROYALTY = "RYLT";

    public static final String CORRESPONDENCE_TYPE_ACKNOWLEDGEMENT_PREFERENCE = "ACKN";

    public static final String CORRESPONDENCE_TYPE_ACE = "ACE";

    public static final String CORRESPONDENCE_TYPE_OTHER = "OTHER";

    public static final String TERRITORY_INCLUDE_FLAG = "I";

    public static final String TERRITORY_EXCLUDE_FLAG = "E";

    public static final String TIS_CODE_US = "840";

    public static final String TIS_CODE_WORLD = "2136";

    public static final String COUNTRY_US = "US";

    public static final String COUNTRY_CANADA = "CA";

    public static final String SOCIETY_ASCAP_CODE = "10";

    public static final String SOCIETY_BMI_CODE = "21";

    public static final String SOCIETY_SESAC_CODE = "71";

    public static final String MUSIC_USER_TV = "TV";

    public static final String MUSIC_USER_RADIO = "Radio";

    public static final String MUSIC_USER_GENERAL = "General";

    public static final String MUSIC_USER_RADIO_GROUP = "RD";

    public static final String MUSIC_USER_RADIO_AM = "AM";

    public static final String MUSIC_USER_RADIO_FM = "FM";

    public static final String MEDIA_TYPE_PAPER = "PPR";

    public static final String MEDIA_TYPE_ONLINE = "ELC";

    public static final String AWARD_TYPE_CODE = "D";

    public static final String AWARD_TYPE_CODE_INTERNATIONAL = "I";

    public static final String AWARD_GRANTED_YES = "Y";

    public static final String AWARD_LIST_TYPE_BY_CATEGORY = "CATEGORY";

    public static final String AWARD_LIST_TYPE_BY_ALPHABETICAL = "ALPHABETICAL";

    public static final String RYLT_LINE_STA_CDE_GRANTED = "RA";

    public static final String RYLT_LINE_STA_CDE_DISTRIBUTED = "EA";

    public static final String AWARD_STATUS_GRANTED = "Granted";

    public static final String AWARD_STATUS_NOTGRANTED = "Not Granted";

    public static final String AWARD_STATUS_DISTRIBUTED = "Distributed";

    public static final String AWARD_STATUS_CODE_NOT_REVIEWED = "PNDG"; // Not Reviewed

    public static final String AWARD_STATUS_CODE_GRANTED = "GRNT"; // Granted

    public static final String AWARD_STATUS_CODE_DECLINED = "DECL"; // Declined

    public static final String AWARD_STATUS_CODE_WITHDRAWN = "SAVD";// "WTHD"; //Withdrawn

    public static final String AWARD_STATUS_CODE_SAVED = "SAVD"; // Saved

    public static final String AWARD_PANEL_DIVISION_CONCERT = "CNCRT";

    public static final String PREP_CONFIG_PARAM_MA_PLUS_AWD_APP_ENABLE_START = "MAAS";

    public static final String PREP_CONFIG_PARAM_MA_PLUS_AWD_APP_ENABLE_END = "MAAE";

    public static final String PREP_CONFIG_PARAM_PREP_PLUS_AWD_REVIEW_ENABLE_START = "PAPS";

    public static final String PREP_CONFIG_PARAM_PREP_PLUS_AWD_REVIEW_ENABLE_END = "PAPE";

    public static final String ONSTAGE_APPLICATION_STATUS_CODE_GRANTED = "GRNT"; // Granted

    public static final String ONSTAGE_APPLICATION_STATUS_CODE_NOT_REVIEWED = "PNDG"; // Not Reviewed

    public static final String ONSTAGE_APPLICATION_STATUS_CODE_DECLINED = "DECL"; // Declined

    public static final String ONSTAGE_APPLICATION_STATUS_CODE_WITHDRAWN = "WTHD"; // Withdrawn

    // for Membership level constants
    /*
     * Level 1 - Soprano Level 2 - Alto Level 3 - Tenor Level 4 - Bass
     */
    // LEVEL 1 BEING THE VERY HIGHEST ANS LEVEL 4 BEING THE VERY LOWEST
    // need to be two character long
    public static final String MEMBERSHIP_LEVEL1 = "DM";

    public static final String MEMBERSHIP_LEVEL2 = "SP";

    public static final String MEMBERSHIP_LEVEL3 = "AL";

    public static final String MEMBERSHIP_LEVEL4 = "TE";

    public static final String MEMBERSHIP_LEVEL5 = "BA";

    public static final String MEMBERSHIP_LEVEL_DEFAULT = MEMBERSHIP_LEVEL5;

    // Active 'A' flag is for Active Successors and Inactive 'I' is for Named
    // Successors

    public static final String RELATIONSHIP_ACTIVE_SUCCESSOR = "A";

    public static final String RELATIONSHIP_NAMED_SUCCESSOR = "I";

    public static final String ACTIVE_SUCCESSOR_FLAG = "Y";

    public static final String NAMED_SUCCESSOR_FLAG = "N";

    public static final String RELATIONSHIP_WRITER_OWNERSHIP = "ASWRO";

    public static final String RELATIONSHIP_WRITER_PART_OWNERSHIP = "ASWPO";

    public static final String RELATIONSHIP_PUBLISHER_OWNERSHIP = "ASPUB";

    public static final String RELATIONSHIP_PUBLISHER_PART_OWNERSHIP = "ASPPO";

    public static final String RELATIONSHIP_NONMEMBER_OWNERSHIP = "NONMB";

    public static final String RELATIONSHIP_SUCCESSOR = "SUCCR";

    public static final String RELATIONSHIP_LEGAL_EARNER = "LEGAL";

    public static final String RELATIONSHIP_THIRD_PARTY_PAYEE = "TPPYE";

    public static final String RELATIONSHIP_ADMINISTRATION = "ADMIN";

    public static final String RELATIONSHIP_ACQUIRED = "ACQUD";

    public static final String RELATIONSHIP_SUPER_PARENT = "PSUPP";

    public static final String ASCAP_LICENSES_PAYS_ALL_WORKS = "WRT";

    public static final String ASCAP_LICENSES_PAYS_SOME_WORKS = "PRT";

    public static final String ASCAP_DOES_NOT_LICENSE_PAYS_NO_WORKS = "WOR";

    public static final String ASCAP_NO_INFO = "UNK";

    // Party Merge - Resign Code
    public static final String PARTY_MERGE_RESIGN_REASON_CODE = "XREF";

    public static final String PARTY_ROLE_TYPE_WRITER = "W";

    public static final String PARTY_ROLE_TYPE_PUBLISHER = "P";

    public static final String PARTY_TYPE_WRITER = "WRTMB";

    public static final String PARTY_TYPE_PUBLISHER = "PBRMB";

    public static final String PARTY_TYPE_SUPER_PARENT = "SUPPB";

    public static final String PARTY_TYPE_LEGAL_EARNER = "LEGAL";

    public static final String PARTY_TYPE_SUCCESSOR = "SUCCR";

    public static final String PARTY_TYPE_SOCIETY = "SOC";

    public static final String PARTY_TYPE_NON_AFF_MEMEBER = "NFMBR";

    public static final String PARTY_TYPE_AFF_MEMBER = "AFMBR";

    public static final String PARTY_TYPE_NON_MEMBER = "NONMB";

    public static final String STMT_PREF_SORT_ORDER_WORK_TITLE = "T";

    public static final String STMT_PREF_SORT_ORDER_PROGRAM = "P";

    public static final String PAYMENT_PLAN_CPP = "CPP";

    public static final String PAYMENT_PLAN_APP = "APP";

    public static final String OFFLINE_CHECK = "OFCK";

    public static final String OFFLINE_CREDIT_CARD = "OFCC";

    public static final String RECOUP_FROM_ROYALTIES = "RCUP";

    public static final String ALLOC_TYPE_ALL = "ALL";

    public static final String ALLOC_TYPE_WORK_IN_PRODUCTION = "WRKPR";

    public static final String ALLOC_TYPE_WORK = "WRKID";

    public static final String ALLOC_TYPE_SOCIETY = "SOCID";

    public static final int MEMBERSHIP_EE_ALLOC_PERCENTAGE_PRECISION = 14;

    public static final int MEMBERSHIP_EE_ALLOC_PERCENTAGE_SCALE = 11;

    public static final String MERGETYPE_SELF_REFERENCE = "1";

    public static final String MERGETYPE_PURCHASE = "2";

    public static final String MERGETYPE_XREF_DEL_MERGE = "3";

    public static final String MERGETYPE_PHYSICAL_DELETE = "4";

    /** <!-- Start ascapdevel changes --> **/

    /** Statement Preference Constants > START - Narsa **/

    public static final String ROYALTY_PMT_THRESHOLD_DOMESTIC = "RPTD";

    public static final String ELGIBLE_FOR_DTL_STMT_YES = "Y";

    public static final String ADMINISTERED_WORKS_RPT_DTL_YES = "Y";

    public static final String LNKD_ENTLTD_ERNRS_RPT_DTL_YES = "Y";

    public static final String SUB_PBLSHD_RPT_DTL_YES = "Y";

    public static final String DEFAULT_NUM_OF_COPIES = "1";

    /** Statement Preference Constants > END - Narsa **/

    public static final String US_CNTRY_CDE1 = "001";

    public static final String US_CNTRY_CDE2 = "1";

    public static final String SEQ_TYP_MU = "MUS";

    public static final String SEQ_TYP_MA = "MAP";

    public static final String SEQ_TYP_NMU = "NMU";

    public static final String PARTY_NAME_SUFFIX_TYPE_OTHER = "Other";

    public static final String REP_CARD_STATUS_SUCCESS = "SUCS";

    public static final String REP_CARD_STATUS_ERRROR_PTY_ID = "EPID";

    public static final String REP_CARD_STATUS_ERRROR_PST_CDE = "EPCDE";

    public static final int FULL_NAME_LENGTH = 90;

    public static final int LAST_NAME_LENGTH = 90;

    public static final int FIRST_NAME_LENGTH = 45;

    public static final int MIDDLE_NAME_LENGTH = 45;

    public static final String CHARGEABLE_SERVICE_ADD = "add";

    public static final String CHARGEABLE_SERVICE_UPDATE = "update";

    public static final String CHARGEABLE_SERVICE_DELETE = "delete";

    public static final String CHARGEABLE_SERVICE_ADDNEW = "addNew";

    public static final String ADDRESS_VALIDATE_PREP_YES = "Y";

    public static final String ADDRESS_VALIDATE_MA_YES = "Y";

    public static final String AFF_SOC_FLAG_YES = "Y";

    public static final String AFF_SOC_FLAG_NO = "N";

    public static final String PAY_DIRECT_FLAG_YES = "Y";

    public static final String PAY_DIRECT_FLAG_NO = "N";

    public static final String VALIDITY_INDICATOR_YES = "Y";

    public static final String VALIDITY_INDICATOR_NO = "N";

    public static final String EFFECTIVE_RESIGNATION_QTR_ONE = "Q1";

    public static final String EFFECTIVE_RESIGNATION_QTR_TWO = "Q2";

    public static final String EFFECTIVE_RESIGNATION_QTR_THREE = "Q3";

    public static final String EFFECTIVE_RESIGNATION_QTR_FOUR = "Q4";

    public static final String EFFECT_START_DATE_PART_QTR_ONE = "04/01/";

    public static final String EFFECT_START_DATE_PART_QTR_TWO = "07/01/";

    public static final String EFFECT_START_DATE_PART_QTR_THREE = "10/01/";

    public static final String EFFECT_START_DATE_PART_QTR_FOUR = "01/01/";

    public static final String DECEASED_INDICATOR_YES = "Y";

    public static final String DECEASED_INDICATOR_NO = "N";

    public static final String ADDRESS_VALIDATION_FLAG_YES = "Y";

    public static final String ADDRESS_VALIDATION_FLAG_NO = "N";

    public static final String FIRST_POINT_OF_CONTACT_YES = "Y";

    public static final String FIRST_POINT_OF_CONTACT_NO = "N";

    public static final String PARTY_TYPE_WRITER_FOR_EMAIL = "Writer";

    public static final String PARTY_TYPE_PUBLISHER_FOR_EMAIL = "Publisher";

    public static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";

    public static final String REPEATING_CHARGE_YES = "Y";

    public static final String CONSTANT_YES = "Y";

    public static final String CONSTANT_NO = "N";

    public static final String DEFAULT_RPI_PERCENT_DIST_AMT = "100";

    public static final String DEFAULT_RPI_PMT_METHOD_CHECK = "CH";

    public static final String DEFAULT_RPI_CHECK_MEMO = "Royalty Payment";

    public static final String DEFAULT_RPI_CALC_BASIS_GROSS = "G";

    public static final String MEMBERSHIP_QUAL_TYP_COMMERCIAL = "COME";

    public static final String MEMBERSHIP_QUAL_TYP_SHEET_MUSIC = "QFOL";

    public static final String MEMBERSHIP_QUAL_TYP_PUBLIC_PERF = "PUBP";

    public static final String MEMBERSHIP_QUAL_TYP_ELECTR_MEDIUM = "QWEM";

    public static final String MEMBERSHIP_ALT_ID_TYP_SSN = "SSN";

    public static final String MEMBERSHIP_ALT_ID_TYP_TAXID = "TAXID";

    public static final String AWARD_SEARCH_LIST_TYPE_ALPHA = "Alpha";

    public static final String AWARD_OPER_FLAG_MA_SAVE = "MA_SAVE";

    public static final String AWARD_OPER_FLAG_MA_SUBMIT = "MA_SUBMIT";

    public static final String AWARD_OPER_FLAG_GRANT = "GRANT";

    public static final String AWARD_OPER_FLAG_WITHDRAW_GRANT = "WITHDRAW_GRANT";

    public static final String AWARD_OPER_FLAG_DECLINE = "DECLINE";

    public static final String AWARD_OPER_FLAG_UPDATE_APP_TYP = "UPDATEAPPTYPE";

    public static final String AWARD_OPER_FLAG_UPDATE = "UPDATE";

    public static final String AWARD_OPER_FLAG_UPDATE_COMMENTS = "UPDATE_COMMENTS";

    public static final String AWARD_OPER_FLAG_GRANT_OUT_OF_CONTEXT = "GRANT_OUT_OF_CONTEXT";

    public static final String AWARD_OPER_FLAG_DECLINE_OUT_OF_CONTEXT = "DECLINE_OUT_OF_CONTEXT";

    public static final String AWARD_OPER_FLAG_WITHDRAW_OUT_OF_CONTEXT = "WITHDRAW_GRANT_OUT_OF_CONTEXT";

    public static final String AWARD_OPER_FLAG_UPDATE_APP_TYP_OUT_OF_CONTEXT = "UPDATEAPPTYPE_OUT_OF_CONTEXT";

    public static final String PLUS_AWARD_APPLICATION_TYPE_AUTO_CONSIDERATION = "AC";

    public static final String PLUS_AWARD_APPLICATION_TYPE_APPLIED = "AM";

    public static final String AWARD_QUAL_WORK_OR_COMPOSITION = "WORK";

    public static final String AWARD_QUAL_RELEASE = "RELS";

    public static final String AWARD_QUAL_PERFORMANCE = "PERF";

    public static final String AWARD_QUAL_BAND = "BAND";

    public static final String MEMBERSHIP_SQL_TYPE_NULL = "SQLTypes.NULL";

    public static final String MEMBERSHIP_SEARCH_TYPE_MEMBER = "SEARCH_PARTY";

    public static final String MEMBERSHIP_SEARCH_TYPE_PARTY = "SEARCH_PARTY_NAME";

    public static final String MEMBERSHIP_SEARCH_TYPE_ROYALTY = "SEARCH_PARTY_ROYALTY";

    public static final String MEMBERSHIP_SEARCH_SEL_TYP_ALLIPI = "allIPI";

    public static final String MEMBERSHIP_SEARCH_SEL_TYP_ASCAP = "ASCAPOnly";

    public static final String MEMBERSHIP_SEARCH_SEL_TYP_ASCAP_CURRENT = "ASCAPCurrentOnly";

    public static final String MEMBERSHIP_CONSIDER_PTY_MERGE_FLAG_YES = "Yes";

    public static final String MEMBERSHIP_ALLOCATION_STATE_A = "A";

    public static final String MEMBERSHIP_MERGE_STATUS_ERROR = "Error";

    public static final String MERGE_PARTY_MERGE_TYP_UPD_STAT = "updatestatus";

    public static final String MERGE_PARTY_MERGE_TYP_CANCEL_REQ = "cancelrequest";

    public static final String MERGE_PARTY_SOURCE_TYPE_IPI = "IPI";

    public static final String MERGE_PARTY_SOURCE_TYPE_ASCAP = "ASCAP";

    public static final String MEMBERSHIP_MA_SUP_TYP_CDE = "EWAS";

    public static final String MEMBERSHIP_MA_DES_TYP_CDE = "EWAD";

    public static final String MEMBERSHIP_MA_MSTR_TYP_CDE = "EWAM";

    public static final String MEMBERSHIP_CLOSE_TERRITORIES_FOR_STATUS = "CloseTerritoriesForStatus";

    public static final String MEMBER_ACCESS_ACCOUNT_UPDATE = "update";

    public static final String MEMBER_ACCESS_ACCOUNT_RESET_PASSWORD = "resetPwd";

    public static final String MEMBER_ACCESS_ACCOUNT_LOCK_UPDATE = "updateLock";

    public static final String EMAIL_WELCOME_PACK = "emailWelcomePack";

    public static final String MEMBER_ACCESS_ACCOUNT_ACTIVATE = "activateAccount";

    public static final String MEMBER_ACCESS_GET_ACCOUNT_ACTIVATE = "getActivateAccount";

    public static final String PUBLISHER_NAME_VALID = "VALID";

    public static final String PUBLISHER_NAME_INVALID = "INVALID";

    public static final String PUBLISHER_NAMES_ONE_TO_BE_VALIDATED = "ONE";

    public static final String PUBLISHER_NAMES_TWO_TO_BE_VALIDATED = "TWO";

    public static final String PUBLISHER_NAMES_THREE_TO_BE_VALIDATED = "THREE";

    public static final String PUBLISHER_NAMES_FOUR_TO_BE_VALIDATED = "FOUR";

    public static final String PUBLISHER_NAMES_ALL_TO_BE_VALIDATED = "ALL";

    public static final String EMAIL_LOG_TYPE_REMINDER = "RMDR";

    public static final String EMAIL_LOG_TYPE_CONFIRMATION = "CONF";

    public static final String STRUTS_SEARCH_ACTION_SEND_REMINDER_NOTFICATIONS = "sendReminderNotifications";

    public static final String STRUTS_SEARCH_ACTION_SEND_CONFIRMATION_NOTFICATIONS = "sendConfirmationNotifications";

    public static final String SUBMIT_REMARKS = "submitRemarks";

    /**
     * Default maximum number of records per page during the Bulk email send
     */
    public static final int DEFAULT_MAX_RECS_PER_PAGE = 30;

    public static final String PLUS_AWARDS_DOMESTIC_EARNINGS_THRESHOLD = "25000";

    public static final String PLUS_AWARDS_MA_DISPLAY_FROM_YEAR = "2012";

    public static final String ONSTAGE_APPLICATION_STATUS_SAVED = "SAVD";

    public static final String ONSTAGE_APPLICATION_STATUS_RECEIVED = "RCVD";

    public static final String ONSTAGE_APPLICATION_STATUS_GRANTED = "GRNT";

    public static final String ONSTAGE_APPLICATION_STATUS_DECLINED = "DECL";

    public static final String ONSTAGE_APPLICATION_STATUS_PROCESSING = "INPR";

    public static final String ONSTAGE_APPLICATION_STATUS_DISTRIBUTED = "DIST";

    public static final String ONSTAGE_APPLICATION_OPER_FLAG_VIEW = "VIEW_MODE";

    public static final String ONSTAGE_APPLICATION_OPER_FLAG_SAVE = "SAVE";

    public static final String ONSTAGE_APPLICATION_OPER_FLAG_SUBMIT = "SUBMIT";

    public static final String ONSTAGE_APPLICATION_OPER_FLAG_CREATE = "CREATE";

    public static final String ONSTAGE_APPLICATION_OPER_FLAG_UPDATE = "UPDATE";

    public static final String ONSTAGE_APPLICATION_OPER_FLAG_DELETE = "DELETE";

    public static final String ONSTAGE_APPLICATION_OPER_FLAG_GRANT = "GRANT";

    public static final String ONSTAGE_APPLICATION_OPER_FLAG_DECLINE = "DECLINE";

    public static final String ONSTAGE_APPLICATION_OPER_FLAG_WITHDRAW = "WITHDRAW";

    public static final String ONSTAGE_APPLICATION_OPER_FLAG_UPDATE_COMMENT = "UPDATE_COMMENT";

    public static final String ONSTAGE_APPLICATION_OPER_FLAG_CANCEL = "CANCEL";

}
