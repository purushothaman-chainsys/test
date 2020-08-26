package com.ascap.apm.common.utils.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Saravanan_Sengottaiyan
 *
 *         Constants that will be used acress distribution modules
 */

public class DistributionConstants {

	private DistributionConstants() {

	}

	// Constants for filters
	public static final String FILTER_ALL = "all";
	public static final String FILTER_ACTIVE = "active";

	// Prep Config params
	public static final String TNDD = "TNDD";
	public static final String TNII = "TNII";
	public static final String TNDD_CONST = "4";
	public static final String DEFAULT_TOTAL_DISTRIBUTIONS = "4";

	protected static final Map<Object, Object> status = new HashMap<>();

	static {
		status.put("LOAD", 0);
		status.put("LTBA", 1);
		status.put("BTAD", 2);
		status.put("ATFD", 3);
		status.put("RFPD", 4);
		status.put("RFPC", 5);
		status.put("TVPC", 6);
		status.put("ARIP", 7);
		status.put("ADEE", 8);

	}

	public static final String MUSIC_USER_SPECIFIC_WEIGHT_TYP_HOOKUP = "HKUP";

	public static final String RFP_TVP_MULTIPLIER_TYPE_TV = "TV";

	public static final String SURVEY_TYPE_SAMPLE = "S";
	public static final String SURVEY_TYPE_CENSUS = "C";
	public static final String SURVEY_TYPE_LIBRARY = "L";

	public static final String SAMPLE_TYPE_TAPE = "T";
	public static final String SAMPLE_TYPE_NOLOG = "N";
	public static final String SAMPLE_TYPE_LOG = "L";

	public static final String LOAD = "LOAD";
	public static final String INIT = "INIT";
	public static final String LTBA = "LTBA";
	public static final String ADJB = "BTAD";
	public static final String FDC = "ATFD";
	public static final String RFPC = "RFPC";
	public static final String RFPD = "RFPD";
	public static final String TVP = "TVPC";
	public static final String ARIP = "ARIP";
	public static final String ADEE = "ADEE";

	public static final String PIQ = "PIQ";

	public static final String ABORTED = "ABRT";
	public static final String COMPLETE = "COMP";
	public static final String IN_PROGRESS = "INPR";

	public static final String DEL_FL_NO = "N";
	public static final String DEL_FL_YES = "Y";

	public static final String MOD_FL_NO = "N";
	public static final String MOD_FL_YES = "Y";

	public static final String DIS_INIT = "Initial";
	public static final String DIS_IN_PROGRESS = "In Progress";

	// Rollback Run controll Types Start
	public static final String TO_PREVIOUS = "REVR";
	public static final String TO_BEGIN = "RBRF";

	public static final String TO_BACK_ARIP_PREVIOUS = "REAP";
	public static final String TO_BACK_ARIP_BEGIN = "REAB";
	public static final String TO_BACK_ADEE_PREVIOUS = "REEP";
	public static final String TO_BACK_ADEE_BEGIN = "REEB";

	public static final String TO_BACK_ADEE_WRITER = "REVA";
	public static final String TO_BACK_ADEE_PUBLISHER = "REEP";
	public static final String TO_BACK_ADEE_FOREIGN_OUTGOING = "REEF";
	// Rollback Run controll Types End

	// Rollback Statuses
	public static final String TO_PREVIOUS_IN_PROGRESS = "RVIP";
	public static final String TO_BEGIN_IN_PROGRESS = "RBIP";
	public static final String TO_PREVIOUS_COMPLETED = "REVR";
	public static final String TO_BEGIN_COMPLETED = "REVB";

	public static final String DOMS = "DOMS";
	public static final String II = "II";
	public static final String PS = "PS";
	public static final String POS = "POS";

	// FOR INTERNET USE CASES - MUSIC USER TYPE CODES
	public static final String AUDIO_VISUAL = "GN-AV";
	public static final String STREAMING = "GN-IN";
	public static final String SELF_REPR = "GN-IS";
	public static final String NONSELF_REPR = "GN-NS";
	public static final String RINGTONES = "GN-RI";

	public static final String LIVE_POP_CONCERT = "GN-LI";

	public static final String FILTER_STD_QWI_TYPE = "QI";

	public static final String WRITER = "ENTW";
	public static final String PUBLISHER = "ENTP";
	public static final String FOREIGN_OUTGOING = "ENTF";

	public static final String SPCF_WGT_SAMPLE = "SAMP";
	public static final String SPCF_WGT_LIBRARY = "LIBR";
	public static final String SPCF_WGT_CENSUS = "CENS";
	public static final String SPCF_WGT_NOLOG = "NLOG";
	public static final String SPCF_WGT_LOG = "LOG";
	public static final String SPCF_WGT_TAPE = "TAPE";
	public static final String SPCF_WGT_MUWEIGHT = "MUSW";
	public static final String SPCF_WGT_PROGRAM = "PGMW";
	public static final String SPCF_WGT_HOOKUP = "HKUP";

	protected static final String[] MUSICUSER_GROUP_GENERAL = { "TV-NE", "TV-LO", "TV-UN", "TV-LC", "TV-PB", "TV-CA",
			"TV-CI", "TV-PA", "TV-SA", "TV-SE", "GN-BA", "GN-AI", "GN-BP", "GN-IC", "GN-RA", "GN-CI", "GN-TH", "GN-RI",
			"GN-LI", "GN-AV", "GN-IN", "GN-IS", "GN-NS" };
	protected static final String[] MUSICUSER_GROUP_RADIO = { "RD-AM", "RD-FM", "RD-PP", "RD-CO", "RD-CL", "RD-ET",
			"RD-JA", "RD-RE", "RD-SP", "RD-UR", "RD-PO", "RD-NC", "RD-NP", "RD-SA" };
	protected static final String[] MUSICUSER_GROUP_OTHER = { "SRE" };

	public static final String FILE_STATUS_PROCESSED = "COMP";
	public static final String FILE_STATUS_PENDING = "PEND";
	public static final String FILE_STATUS_LOADING = "LOAD";
	public static final String FILE_STATUS_NEW = "NEW";

	public static final String REJECT_FILE_SUFFIX = "_Rejected.txt";
	public static final String ERRORS_FILE_SUFFIX = "_ErrCde.txt";

	public static final String WEIGHT_PROCESS = "Reject";

	public static final String BASE_CREDITS = "BASE";
	public static final String ADJUSTED_BASE_CREDITS = "ADJB";
	public static final String FINAL_DISTRIBUTABLE_CREDITS = "FNLD";
	public static final String RADIOFEATURE_PREMIUM_CREDITS = "RFPR";
	public static final String TV_PREMIUM_CREDITS = "TVPR";

	public static final String PERSET_CODE_PRIMETIME = "P";
	public static final String PERSET_CODE_MORNING = "M";
	public static final String PERSET_CODE_AFTERNOON = "A";
	public static final String PERSET_CODE_NIGHT = "N";

	public static final String EO_SETTING_TYPE_GOAL_CREDITS = "EO_SETTING_TYPE_GOAL_CREDITS";
	public static final String EO_SETTING_TYPE_ADJUSTED_BASE_FACTORS = "EO_SETTING_TYPE_ADJUSTED_BASE_FACTORS";
	public static final String EO_SETTING_TYPE_MONEY_CLASS_GROUPS = "EO_SETTING_TYPE_MONEY_CLASS_GROUPS";
	public static final String EO_SETTING_TYPE_USE_WEIGHT_RULES = "EO_SETTING_TYPE_USE_WEIGHT_RULES";
	public static final String EO_SETTING_TYPE_TIME_OF_DAY_WEIGHTS = "EO_SETTING_TYPE_TIME_OF_DAY_WEIGHTS";
	public static final String EO_SETTING_TYPE_RFP_RULES = "EO_SETTING_TYPE_RFP_RULES";
	public static final String EO_SETTING_TYPE_RFP_WEIGHTS = "EO_SETTING_TYPE_RFP_WEIGHTS";
	public static final String EO_SETTING_TYPE_TVP_WEIGHTS = "EO_SETTING_TYPE_TVP_WEIGHTS";
	public static final String EO_SETTING_TYPE_TV_HOOKUP_WEIGHTS = "EO_SETTING_TYPE_TV_HOOKUP_WEIGHTS";
	public static final String EO_SETTING_TYPE_LIVE_POP_CONCERT_DENSITY_WEIGHTS = "EO_SETTING_TYPE_LIVE_POP_CONCERT_DENSITY_WEIGHTS";
	public static final String EO_SETTING_TYPE_INTERACTIVE_INTERNET = "EO_SETTING_TYPE_INTERACTIVE_INTERNET";
	public static final String EO_SETTING_TYPE_STREAMING_INTERNET = "EO_SETTING_TYPE_STREAMING_INTERNET";

	public static final String EO_SETTING_OPREATION_ADD = "EO_SETTING_OPREATION_ADD";
	public static final String EO_SETTING_OPREATION_DELETE = "EO_SETTING_OPREATION_DELETE";
	public static final String EO_SETTING_OPREATION_UPDATE = "EO_SETTING_OPREATION_UPDATE";

	// The Following Constants BASE_CREDIT_CALCULATION_METHOD_* are used by Usage
	// for Validation Purposes.
	public static final String BASE_CREDIT_CALCULATION_METHOD_FM1_MUZAK = "FM1-MUZAK";
	public static final String BASE_CREDIT_CALCULATION_METHOD_MUSIC_CHOICE = "MUS-CH";

	public static final String MEMBER_DISSUM_LINE_TYP_CURRENT_PERFORMANCE = "CPERF";
	public static final String MEMBER_DISSUM_LINE_TYP_CURRENT_ADJUSTMENT = "CADJ";
	public static final String MEMBER_DISSUM_LINE_TYP_BALANCE_ADJUSTMENT = "BADJ";
	public static final String MEMBER_DISSUM_LINE_TYP_HISTORICAL_ADJUSTMENT = "HADJ";
	public static final String MEMBER_DISSUM_LINE_TYP_AWARD = "AWD";

	public static final String BLOCK_ADJ_TRIG_TYP_USEWGTRL = "Y";
	public static final String BLOCK_ADJ_TRIG_TYP_GOALCREDIT = "Y";
	public static final String BLOCK_ADJ_TRIG_TYP_MUWGT = "Y";
	public static final String BLOCK_ADJ_TRIG_TYP_PROGWGT = "Y";
	public static final String BLOCK_ADJ_TRIG_TYP_TDAYWGTCHNG = "Y";
	public static final String BLOCK_ADJ_TRIG_TYP_TVHKPWGTCHNG = "Y";
	public static final String BLOCK_ADJ_TRIG_TYP_ADJRLCHNG = "Y";
	public static final String BLOCK_ADJ_TRIG_TYP_INTRINETFEECHNG = "Y";
	public static final String BLOCK_ADJ_TRIG_TYP_STREAMINETFEECHNGE = "Y";
	public static final String BLOCK_ADJ_TRIG_TYP_LIVEPOPRULECHNG = "Y";
	public static final String BLOCK_ADJ_TRIG_TYP_RFPREC = "Y";
}
