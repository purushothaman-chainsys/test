package com.ascap.apm.common.utils.constants;

import java.util.BitSet;
/*
import com.ascap.apm.common.database.usage.SET;
import com.ascap.apm.common.database.usage.UPDATE;
*/
/**
 * Constants for SQL queries used in APM.
 * @author Manoj Puli
 * @version $Revision:   1.103  
 */
public class UsageQueries {

	
	
	
	public static final String UPDATE_APM_FILES ="UPDATE APM_ARCHIVE SET UPD_DT = SYSDATE, TGTSURVYEARQTR = ?, UPD_ID = ? WHERE APM_ARCHIVE_ID = ?";
	
	
	
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


	public static final String CALL_PERF_HDRS_CLONE="{call pkg_Usage_Online.p_Clone_Performance(?,?,?,?)}";
	
	public static final String DELETE_ALL_PROGRAM_PERFORMANCE_MESSAGES = "DELETE FROM APM_ERROR_LOG WHERE APM_PERF_HDR_ID = ?";
	public static final String DELETE_WORK_PERFORMANCE_FOR_HEADER = "UPDATE APM_WRK_PERF SET DEL_FL = 'Y', DEL_RSN_CDE = 'USER_DEL',  UPD_DT = SYSDATE, UPD_ID = ? WHERE APM_PERF_HDR_ID = ? AND DEL_FL = 'N' ";
	public static final String DELETE_PROGRAM_PERFORMANCE = " UPDATE APM_PERF_HDR A " +
	"   SET A.DEL_FL = 'Y', DEL_RSN_CDE = 'USER_DEL', A.UPD_DT = SYSDATE, A.UPD_ID = ? " +
	" WHERE A.APM_PERF_HDR_ID = ? AND A.DEL_FL = 'N' " +
	"       AND NOT EXISTS " +
	"                  (SELECT 1 " +
	"                     FROM APM_WRK_PERF B " +
	"                    WHERE     B.APM_PERF_HDR_ID = A.APM_PERF_HDR_ID " +
	"                          AND B.LOCK_IND = 'Y' " +
	"                          AND B.DEL_FL = 'N') " ;

	public static final String DELETE_ALL_WORK_PERFORMANCE_MESSAGES = "DELETE FROM APM_ERROR_LOG WHERE  APM_WRK_PERF_ID = ?";
	public static final String DELETE_WORK_PERFORMANCE = "UPDATE APM_WRK_PERF SET DEL_FL = 'Y', DEL_RSN_CDE = 'USER_DEL', UPD_DT = SYSDATE, UPD_ID = ? WHERE APM_WRK_PERF_ID = ? AND DEL_FL = 'N' ";
	
	public static final String UPDATE_WORK_PERFORMANCE_USE_TYP = "UPDATE APM_WRK_PERF SET USE_TYP = ?,  UPD_DT = SYSDATE, UPD_ID = ? WHERE APM_WRK_PERF_ID = ? AND DEL_FL = 'N' ";
	
	
	
	public static final String GET_APM_PERF_HDR_ID_FOR_WRK_PERF = "SELECT APM_PERF_HDR_ID FROM APM_WRK_PERF WHERE APM_WRK_PERF_ID = ?";
	
	public static final String BATCH_APM_PERF_VALIDATE = "{call pkg_Performances_Online.p_validate_performances(?,?,?,?)}";
	public static final String BATCH_APM_PERF_BULK_REQUEST = "{call pkg_Performances_Online.p_Performance_Bulk_Request(?,?)}";
	public static final String BATCH_APM_PERF_FILE_REQUEST = "{call pkg_Performances_Online.p_PerfTargetSurveyQtrUpd (?,?)}";
	public static final String BATCH_APM_WORK_PERF_MOVE = "{call pkg_Performances_Online.p_move_wrkPerfsTo_NewHdr (?,?)}";
		
	
	public static final String BATCH_APM_SAMPLING_L1_REQUEST = "{call PROC_SAMP_L1_UI (?)}";
	public static final String BATCH_APM_SAMPLING_L2_REQUEST = "{call PROC_SAMP_L2_UI (?)}";
	public static final String BATCH_APM_SAMPLING_L3_REQUEST = "{call PROC_SAMP_L3_UI (?)}";
	public static final String BATCH_APM_SAMPLING_L4_REQUEST = "{call PROC_SAMP_L4_UI (?)}"; 
	public static final String GET_SAMP_REQ_ID = "SELECT SAMP_REQ_ID FROM SAMP_REQ WHERE MUS_USER = ? AND TGTSURVYEARQTR = ? AND STEP_CDE = 'L1' AND DEL_FL = 'N'";
	public static final String UPDATE_SAMP_REQ = "UPDATE SAMP_REQ SET STA_CDE = 'IQ'  WHERE SAMP_REQ_ID = ? ";
	
	/*Iteration II start*/
	//Sequences
//	public static final String SEQUENCE_PERF_HDR_ID = "SELECT PERF_HDR_ID.NEXTVAL FROM DUAL";
//	public static final String SEQUENCE_APM_WRK_PERF_ID = "SELECT APM_WRK_PERF_ID.NEXTVAL FROM DUAL";
//	public static final String SEQUENCE_PERF_HDR_WRN_ID = "SELECT PERF_HDR_WRN_ID.NEXTVAL FROM DUAL";
//	public static final String SEQUENCE_WRK_PERF_WRN_ID = "SELECT WRK_PERF_WRN_ID.NEXTVAL FROM DUAL";
//	public static final String SEQUENCE_SUR_DT_ID = "SELECT SUR_DT_ID.NEXTVAL FROM DUAL";


	//public static final String SEQUENCE_CNTRL_ID = "SELECT CNTRL_ID.NEXTVAL FROM DUAL";
	//public static final String SEQUENCE_CNTRL_ID = "SELECT PERF_HDR_ID.NEXTVAL FROM DUAL";
//	public static final String SEQUENCE_CNTRL_ID = "SELECT BAT_ID_SEQ.NEXTVAL FROM DUAL";

	//public static final String SEQUENCE_UNPOST_ID = "SELECT UNPOST_ID.NEXTVAL FROM DUAL";

	public static final String SEQUENCE_PERF_HDR_ID = "SELECT APM_PERF_HDR_ID.NEXTVAL FROM DUAL";
	public static final String SEQUENCE_PERF_HDR_WRN_ID = "SELECT PERF_HDR_WRN_ID.NEXTVAL FROM DUAL";
	public static final String SEQUENCE_PRX_SEL_ID = "SELECT PRX_SEL_ID.NEXTVAL FROM DUAL";
	public static final String SEQUENCE_SAM_SUR_DT_ID = "SELECT SAM_SUR_DT_ID.NEXTVAL FROM DUAL";
	public static final String SEQUENCE_SM_DEDUP_EXEMPTIONS_ID = "SELECT SM_DEDUP_EXEMPTIONS_ID.NEXTVAL FROM DUAL";
	//public static final String SEQUENCE_SUB_SAM_PAR_ID = "SELECT SAM_SUR_DT_ID.NEXTVAL FROM DUAL";
	public static final String SEQUENCE_SUB_SAM_PAR_ID = "SELECT USE_SUB_SAM_PAR_ID.NEXTVAL FROM DUAL";
	public static final String SEQUENCE_SUR_DT_ID = "SELECT SUR_DT_ID.NEXTVAL FROM DUAL";
	public static final String SEQUENCE_USE_BLK_ADJ_TRIG_ID = "SELECT USE_BLK_ADJ_TRIG_ID.NEXTVAL FROM DUAL";
	public static final String SEQUENCE_USE_BLK_PRC_PERFS_ID = "SELECT USE_BLK_PRC_PERFS_ID.NEXTVAL FROM DUAL";
	public static final String SEQUENCE_USE_BLK_PROC_ID = "SELECT USE_BLK_PROC_ID.NEXTVAL FROM DUAL";
	public static final String SEQUENCE_WRK_PERF_ID = "SELECT APM_WRK_PERF_ID.NEXTVAL FROM DUAL";
	public static final String SEQUENCE_WRK_PERF_WRN_ID = "SELECT WRK_PERF_WRN_ID.NEXTVAL FROM DUAL";
	public static final String SEQUENCE_SMC_ASGN_HIST_ID = "SELECT SMC_ASGN_HIST_ID.NEXTVAL FROM DUAL";
//	public static final String SEQUENCE_USG_AUDT_HST_ID = "SELECT USG_AUDT_HST_ID.NEXTVAL FROM DUAL";

	//Dynamic Inserts
	public static final String DYNAMIC_INSERT_PERF_HDR_TBL = "INSERT INTO APM_PERF_HDR ";
	public static final String DYNAMIC_INSERT_WRK_PERF_TBL = "INSERT INTO APM_WRK_PERF ";

	public static final String DYNAMIC_INSERT_PRX_SEL_TBL = "INSERT INTO PRX_SEL ";


	//Dynamic Updates
	public static final String DYNAMIC_UPDATE_PERF_HDR_TBL = "UPDATE APM_PERF_HDR ";
	public static final String DYNAMIC_UPDATE_WRK_PERF_TBL = "UPDATE APM_WRK_PERF ";

	public static final String DYNAMIC_SEARCH_CRITERIA_MERGED_WRK_IDS =
		" /* merged Work Id subquery start */  " +
		"(  " +
		" (select xwrkidx from ( " +
		"   (SELECT ? xwrkidx  " +
		"     FROM dual wwl1 " +
		"   ) " +
		/*"   UNION " +
		"   (SELECT wal2.wrk_id xwrkidx  " +
		"     FROM alt_wrk_id wal2  " +
		"    WHERE wal2.alt_wrk_id_typ_cde = 'TRFID' AND wal2.alt_id = ? AND wal2.del_fl = 'N'   " +
		"   ) " +
		"   UNION  " +
		"   (SELECT TO_NUMBER (wal3.alt_id) xwrkidx  " +
		"     FROM alt_wrk_id wal3  " +
		"    WHERE wal3.alt_wrk_id_typ_cde = 'TRFID' AND wal3.del_fl = 'N'  " +
		"		  AND wal3.wrk_id IN " +
		"						  (" +
		"   						(SELECT wrk_id xwrkidx  " +
		"    						 FROM wrk wwl1 " +
		"   						 WHERE wwl1.wrk_id = ? AND wwl1.del_fl = 'N' " +
		"  							 ) " +
		"  							 UNION " +		
		"							 (SELECT wal4.wrk_id xwrkidx  " +
		"		  	  			 	  FROM alt_wrk_id wal4, wrk wwl5  " +
		"							 WHERE wwl5.wrk_id = wal4.wrk_id AND wwl5.acv_wrk_ind = 'Y' AND wal4.alt_wrk_id_typ_cde = 'TRFID' AND wal4.alt_id = ? AND wal4.del_fl = 'N' and wwl5.del_fl = 'N'  " +
		"							 )" +
		"							) " +
		"   ) " +*/
		" ) where xwrkidx is not null) " +
		")  " +
		" /* merged Work Id subquery end */ "; //:wrk_id_under_test
	
	public static final String DYNAMIC_SEARCH_CRITERIA_MERGED_PARTY_IDS =
		" /* merged Party Id subquery start */ " +
		"( " +
		"(SELECT pty.txfr_pty_id FROM pty WHERE txfr_pty_id is not null " +
		"										and pty_id IN (	(SELECT m_pty FROM tgt_pty_list WHERE tgt_pty = ? and tgt_pty is not null and m_pty is not null ) " +
		"							 	             	       UNION " +
		"													  	(SELECT tgt_pty FROM tgt_pty_list WHERE m_pty = ? and tgt_pty is not null and m_pty is not null ) " +
		"												 	  ) " +
		" ) " +
		" UNION " +
		" (SELECT m_pty FROM tgt_pty_list WHERE tgt_pty = ? and tgt_pty is not null and m_pty is not null ) " +
		" UNION " + 
		" (SELECT tgt_pty FROM tgt_pty_list WHERE m_pty = ? and tgt_pty is not null and m_pty is not null ) " +
		" UNION " +
		" (SELECT pty_id from pty where pty_id = ? ) " +
		") " +
		" /* merged Party Id subquery start */ " ;
		
	public static final String DYNAMIC_SEARCH_CRITERIA_DISTRIBUTION_IDS_PART1 =
		" /* Distribution Ids subquery start */ " +
		"( " +
		" SELECT dis_id " +
		"  FROM dis n " +
		" WHERE n.del_fl = 'N' " ;
	//	"       AND n.dis_typ_cde IN ('DOMS', 'II') AND n.dis_dt BETWEEN TO_DATE ('01/01/2000', 'MM/DD/YYYY') AND TO_DATE ('01/01/2010', 'MM/DD/YYYY') " +
	public static final String DYNAMIC_SEARCH_CRITERIA_DISTRIBUTION_IDS_PART2 =		
		") " +
		"/* Distribution Ids subquery end */";
		
//------------------------- dependent mask based joins Start----------------------------------------------	
	
//--------------------------Dependency JOIN MASKS start
	public static final int BIT_MASK_SIZE = 16;
	
	public static final int BIT_POSITION_A_PERF_HDR = 0;
	public static final int BIT_POSITION_B_WRK_PERF = 1;
	public static final int BIT_POSITION_C_WRK_PERF_SHR = 2;
	public static final int BIT_POSITION_D_PTY_FOR_MUSICUSER = 3;
	public static final int BIT_POSITION_E_PTY_FOR_SHAREOWNERPARTY = 4;
	public static final int BIT_POSITION_F_PTY_NA_FOR_CALLLETTER = 5;
	public static final int BIT_POSITION_G_PTY_NA_FOR_SHAREOWNERPARTY = 6;
	//public static final int BIT_POSITION_H_PERF_HDR_WRN = 7;
	//public static final int BIT_POSITION_I_WRK_PERF_WRN = 8;
	public static final int BIT_POSITION_J_MUS_USER_TYP_SEL = 9;
	public static final int BIT_POSITION_K_MUS_USER_LIC = 10;
	public static final int BIT_POSITION_L_WRK = 11;
	//public static final int BIT_POSITION_M_WRK_PERF_SHR_WRN = 12;
	public static final int BIT_POSITION_ALL_ERRORS_PERF_HDR = 13;
	public static final int BIT_POSITION_ALL_ERRORS_WRK_PERF = 14;
	
	public static final BitSet JOIN_DEP_MASK_PERF_HDR = new BitSet(UsageQueries.BIT_MASK_SIZE);
	static{
		UsageQueries.JOIN_DEP_MASK_PERF_HDR.set(UsageQueries.BIT_POSITION_A_PERF_HDR);
	}

	public static final BitSet JOIN_DEP_MASK_WRK_PERF = new BitSet(UsageQueries.BIT_MASK_SIZE);
	static{
		UsageQueries.JOIN_DEP_MASK_WRK_PERF.set(UsageQueries.BIT_POSITION_A_PERF_HDR);
		UsageQueries.JOIN_DEP_MASK_WRK_PERF.set(UsageQueries.BIT_POSITION_B_WRK_PERF);
	}
	

	public static final BitSet JOIN_DEP_MASK_WRK_PERF_SHR = new BitSet(UsageQueries.BIT_MASK_SIZE);
	static{
		UsageQueries.JOIN_DEP_MASK_WRK_PERF_SHR.set(UsageQueries.BIT_POSITION_A_PERF_HDR);
		UsageQueries.JOIN_DEP_MASK_WRK_PERF_SHR.set(UsageQueries.BIT_POSITION_B_WRK_PERF);
		UsageQueries.JOIN_DEP_MASK_WRK_PERF_SHR.set(UsageQueries.BIT_POSITION_C_WRK_PERF_SHR);
	}
	
	public static final BitSet JOIN_DEP_MASK_SHR_OWNER_PTY = new BitSet(UsageQueries.BIT_MASK_SIZE);
	static{
		UsageQueries.JOIN_DEP_MASK_SHR_OWNER_PTY.set(UsageQueries.BIT_POSITION_A_PERF_HDR);
		UsageQueries.JOIN_DEP_MASK_SHR_OWNER_PTY.set(UsageQueries.BIT_POSITION_B_WRK_PERF);
		UsageQueries.JOIN_DEP_MASK_SHR_OWNER_PTY.set(UsageQueries.BIT_POSITION_C_WRK_PERF_SHR);
		UsageQueries.JOIN_DEP_MASK_SHR_OWNER_PTY.set(UsageQueries.BIT_POSITION_G_PTY_NA_FOR_SHAREOWNERPARTY);
		UsageQueries.JOIN_DEP_MASK_SHR_OWNER_PTY.set(UsageQueries.BIT_POSITION_E_PTY_FOR_SHAREOWNERPARTY);
	}
	
	

	public static final BitSet JOIN_DEP_MASK_CALL_LETTER = new BitSet(UsageQueries.BIT_MASK_SIZE);
	static{
		UsageQueries.JOIN_DEP_MASK_CALL_LETTER.set(UsageQueries.BIT_POSITION_A_PERF_HDR);
		UsageQueries.JOIN_DEP_MASK_CALL_LETTER.set(UsageQueries.BIT_POSITION_D_PTY_FOR_MUSICUSER);
		UsageQueries.JOIN_DEP_MASK_CALL_LETTER.set(UsageQueries.BIT_POSITION_F_PTY_NA_FOR_CALLLETTER);
	}
	
	public static final BitSet JOIN_DEP_MASK_MUS_USER_TYP = new BitSet(UsageQueries.BIT_MASK_SIZE);
	static{
		UsageQueries.JOIN_DEP_MASK_MUS_USER_TYP.set(UsageQueries.BIT_POSITION_A_PERF_HDR);
		UsageQueries.JOIN_DEP_MASK_MUS_USER_TYP.set(UsageQueries.BIT_POSITION_D_PTY_FOR_MUSICUSER);
		UsageQueries.JOIN_DEP_MASK_MUS_USER_TYP.set(UsageQueries.BIT_POSITION_J_MUS_USER_TYP_SEL);
	}
	public static final BitSet JOIN_DEP_MASK_MUS_USER_LIC = new BitSet(UsageQueries.BIT_MASK_SIZE);
	static{
		UsageQueries.JOIN_DEP_MASK_MUS_USER_LIC.set(UsageQueries.BIT_POSITION_A_PERF_HDR);
		UsageQueries.JOIN_DEP_MASK_MUS_USER_LIC.set(UsageQueries.BIT_POSITION_D_PTY_FOR_MUSICUSER);
		UsageQueries.JOIN_DEP_MASK_MUS_USER_LIC.set(UsageQueries.BIT_POSITION_K_MUS_USER_LIC);
	}
	
	public static final BitSet JOIN_DEP_MASK_USAGE_ERROR_FOR_PERF_HDR = new BitSet(UsageQueries.BIT_MASK_SIZE);
	static{
		UsageQueries.JOIN_DEP_MASK_USAGE_ERROR_FOR_PERF_HDR.set(UsageQueries.BIT_POSITION_A_PERF_HDR);
		UsageQueries.JOIN_DEP_MASK_USAGE_ERROR_FOR_PERF_HDR.set(UsageQueries.BIT_POSITION_ALL_ERRORS_PERF_HDR);
	}
	
	public static final BitSet JOIN_DEP_MASK_USAGE_ERROR_FOR_WRK_PERF = new BitSet(UsageQueries.BIT_MASK_SIZE);
	static{
		UsageQueries.JOIN_DEP_MASK_USAGE_ERROR_FOR_WRK_PERF.set(UsageQueries.BIT_POSITION_A_PERF_HDR);
		UsageQueries.JOIN_DEP_MASK_USAGE_ERROR_FOR_WRK_PERF.set(UsageQueries.BIT_POSITION_B_WRK_PERF);
		UsageQueries.JOIN_DEP_MASK_USAGE_ERROR_FOR_WRK_PERF.set(UsageQueries.BIT_POSITION_ALL_ERRORS_WRK_PERF);
	}
	
//--------------------------Dependency Join Masks end	
	
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_PERF_HDR_A = " APM_PERF_HDR a ";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_WRK_PERF_B = " INNER JOIN APM_WRK_PERF b ON (a.apm_perf_hdr_id = b.apm_perf_hdr_id and b.del_fl = 'N') ";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_WRK_PERF_B_EVEN_DELETED = " INNER JOIN APM_WRK_PERF b ON (a.apm_perf_hdr_id = b.apm_perf_hdr_id ) ";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_WRK_PERF_SHR_C = " INNER JOIN wrk_perf_shr c ON (b.APM_APM_WRK_PERF_ID = c.APM_APM_WRK_PERF_ID AND c.del_fl = 'N') ";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_MUSIC_USER_PTY_D = " ";//" INNER JOIN pty d ON (d.pty_id = a.pty_id AND d.pty_typ_cde = 'MUSUS' AND d.del_fl = 'N') ";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_CALL_LETTERS_F = " INNER JOIN mvw_pty_na f ON (a.pgm_stt_dt BETWEEN to_char(f.stt_dt,'YYYY/MM/DD') AND TO_CHAR(f.end_dt,'YYYY/MM/DD') AND f.pty_id = a.pty_id ) ";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_MUSIC_USER_TYPE_J = " INNER JOIN mvw_mus_user_typ_sel j ON ( /* a.pty_id = j.pty_id AND */ to_date(a.pgm_stt_dt,'YYYY/MM/DD') BETWEEN j.stt_dt AND j.end_dt AND j.pty_id = A.pty_id ) ";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_MUSIC_USER_LICENSE_K = " INNER JOIN mvw_mus_user_lic k ON ( /* a.pty_id = k.pty_id AND */ to_date(a.pgm_stt_dt,'YYYY/MM/DD') BETWEEN k.eff_dt AND k.end_dt AND k.pty_id = A.pty_id ) ";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_SHAREOWNER_PARTYID_E = "  ";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_SHAREOWNER_PARTY_NA_ID_G = " INNER JOIN (pty_na g INNER JOIN pty e ON (e.pty_id = g.pty_id /* AND e.del_fl = 'N' */ )) ON (g.pty_na_id = c.shr_own_pty_na_id AND c.shr_own_pty_na_id IS NOT NULL /* AND g.del_fl='N' */ ) ";
	//SHAREOWNER DOES NOT JOIN TO THE PARTY VARIATION
	//public static final String DYNAMIC_USAGE_SEARCH_JOIN_SHAREOWNER_PARTYID_G = " INNER JOIN pty_na g ON (g.pty_na_id = c.shr_own_pty_na_id /* AND g.del_fl='N' */ ) ";
	
// Errors correctly join to the perf_hdr and APM_WRK_PERF and Wrk_perf_shr	
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_COMMON =
		"INNER JOIN " +
		"( " +
		"SELECT h.err_cde use_ewi_cde, h.APM_PERF_HDR_ID PERF_hdr_ID, null wrk_perf_id " +
		"  FROM APM_ERROR_LOG h" +
		" WHERE  " +
		"       (h.DEL_FL <> 'Y' OR h.DEL_FL IS NULL) AND h.APM_PERF_HDR_ID IS NOT NULL " +
		") all_errors on ";
		
	
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_WORK_PERF =
		"INNER JOIN " +
		"( " +
		"SELECT h.err_cde use_ewi_cde, h.APM_WRK_PERF_ID APM_WRK_PERF_ID, null perf_HDR_id " +
		"  FROM APM_ERROR_LOG h" +
		" WHERE  " +
		"       (h.DEL_FL <> 'Y' OR h.DEL_FL IS NULL) AND h.APM_WRK_PERF_ID IS NOT NULL " +
		") all_errors on ";
		
		
		
//		"SELECT h.use_ewi_cde, h.perf_hdr_id, NULL APM_APM_WRK_PERF_ID /* , NULL wrk_perf_shr_id */ FROM perf_hdr_wrn h where use_ewi_cde is not null and perf_hdr_id is not null and h.del_fl='N' /* INNER JOIN perf_hdr a ON (h.perf_hdr_id = a.perf_hdr_id AND h.prg_perf_ver_num = a.ver_num AND h.del_fl = 'N' AND a.del_fl = 'N' ) */ " +
//		"UNION " +
//		"SELECT i.use_ewi_cde, i.perf_hdr_id, i.APM_APM_WRK_PERF_ID /* , NULL wrk_perf_shr_id */ FROM wrk_perf_wrn i where use_ewi_cde is not null and perf_hdr_id is not null and APM_APM_WRK_PERF_ID is not null and  i.del_fl='N' /* (wrk_perf_wrn i INNER JOIN APM_WRK_PERF b ON (i.APM_APM_WRK_PERF_ID = b.APM_APM_WRK_PERF_ID AND i.wrk_perf_ver_num = b.ver_num AND i.del_fl = 'N' AND b.del_fl = 'N' )) INNER JOIN perf_hdr a ON (b.perf_hdr_id = a.perf_hdr_id AND a.del_fl = 'N' ) */ " +
//		"UNION " +
//		"SELECT m.USE_EWI_CDE, m.perf_hdr_id, m.APM_APM_WRK_PERF_ID /* , m.wrk_perf_shr_id */ FROM wrk_perf_shr_wrn m where use_ewi_cde is not null and perf_hdr_id is not null and APM_APM_WRK_PERF_ID is not null and  m.del_fl='N' /* ((wrk_perf_shr_wrn m INNER JOIN wrk_perf_shr c ON (m.wrk_perf_shr_id = c.wrk_perf_shr_id AND m.WRK_PERF_SHR_VER_NUM = c.ver_num and M.del_fl = 'N' and c.del_fl='N') inner join APM_WRK_PERF b on (c.APM_APM_WRK_PERF_ID = b.APM_APM_WRK_PERF_ID and b.del_fl='N' ))) inner join perf_hdr a on (b.perf_hdr_id = a.perf_hdr_id and a.del_fl='N' )*/ " +
//		") all_errors on ";

//Errors dont join with perf and wrk_perfs	
//	public static final String DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_COMMON =
//		"INNER JOIN " +
//		"( " +
//		"SELECT h.use_ewi_cde, h.perf_hdr_id, NULL APM_WRK_PERF_ID, NULL wrk_perf_shr_id FROM perf_hdr_wrn h where h.del_fl = 'N' " +
//		"UNION " +
//		"SELECT i.use_ewi_cde, i.perf_hdr_id, i.APM_WRK_PERF_ID, NULL wrk_perf_shr_id FROM wrk_perf_wrn i where i.del_fl = 'N' " +
//		"UNION " +
//		"SELECT m.USE_EWI_CDE, m.perf_hdr_id, m.APM_WRK_PERF_ID, m.wrk_perf_shr_id FROM wrk_perf_shr_wrn m where m.del_fl = 'N' " +
//		") all_errors on ";
	
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_PERF_HDR_ALL_ERRORS = 
		UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_COMMON +
		" (a.APM_PERF_HDR_ID = all_errors.perf_hdr_id) ";
	
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_WRK_PERF_ALL_ERRORS =
		UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_WORK_PERF +
		" (b.APM_WRK_PERF_ID = all_errors.APM_WRK_perf_id and nvl(all_errors.APM_WRK_PERF_ID, b.APM_WRK_PERF_ID) = b.APM_WRK_PERF_ID) ";
//------------------------- dependent mask based joins End----------------------------------------------		

//------------------------- Independent joins Start----------------------------------------------
/*  
    public static final String DYNAMIC_USAGE_SEARCH_JOIN_PERF_HDR_A = " perf_hdr a ";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_WRK_PERF_B = " INNER JOIN wrk_perf b ON (a.perf_hdr_id = b.perf_hdr_id AND b.curr_ver_flag = 'Y' AND b.del_fl = 'N') ";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_WRK_PERF_SHR_C = " INNER JOIN (wrk_perf_shr c INNER JOIN wrk_perf wrkperfC ON (wrkperfC.APM_WRK_PERF_ID = c.APM_WRK_PERF_ID AND c.del_fl = 'N' AND wrkperfC.del_fl = 'N' AND wrkperfC.curr_ver_flag = 'Y')) ON (wrkperfC.perf_hdr_id = a.perf_hdr_id) ";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_MUSIC_USER_PTY_D = "";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_CALL_LETTERS_F = 
		"       INNER JOIN  " +
		"	       (pty_na f INNER JOIN pty ptyf ON (f.pty_na_typ_cde='CL' AND f.pty_id = ptyf.pty_id AND ptyf.pty_typ_cde = 'MUSUS' AND f.del_fl = 'N' AND ptyf.del_fl = 'N')) " +
		"       ON (a.pty_id = f.pty_id AND a.pgm_stt_dt BETWEEN f.stt_dt AND f.end_dt) ";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_MUSIC_USER_TYPE_J = 
		"       INNER JOIN  " +
		"	       (mus_user_typ_sel j INNER JOIN pty ptyj ON (j.pty_id = ptyj.pty_id AND ptyj.pty_typ_cde = 'MUSUS' AND j.del_fl = 'N' AND ptyj.del_fl = 'N')) " +
		"       ON (a.pty_id = j.pty_id AND a.pgm_stt_dt BETWEEN j.stt_dt AND j.end_dt) ";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_MUSIC_USER_LICENSE_K = 
		"       INNER JOIN " +
		"           (mus_user_lic k INNER JOIN pty ptyk ON (k.pty_id = ptyk.pty_id AND ptyk.pty_typ_cde = 'MUSUS' AND k.del_fl = 'N' AND ptyk.del_fl = 'N')) " +
		"       ON (a.pty_id = k.pty_id AND a.pgm_stt_dt BETWEEN k.eff_dt AND k.end_dt) ";
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_SHAREOWNER_PARTYID_G = " INNER JOIN (pty_na g INNER JOIN pty e ON (e.pty_id = g.pty_id AND e.del_fl = 'N')) ON (g.pty_na_id = c.shr_own_pty_na_id AND g.del_fl='N') ";
	
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_COMMON =
		"INNER JOIN " +
		"( " +
		"SELECT h.use_ewi_cde, h.perf_hdr_id, NULL APM_WRK_PERF_ID, NULL wrk_perf_shr_id FROM perf_hdr_wrn h INNER JOIN perf_hdr a ON (h.perf_hdr_id = a.perf_hdr_id AND h.prg_perf_ver_num = a.ver_num AND h.del_fl = 'N' AND a.del_fl = 'N' AND a.curr_ver_flag = 'Y') " +
		"UNION " +
		"SELECT i.use_ewi_cde, i.perf_hdr_id, i.APM_WRK_PERF_ID, NULL wrk_perf_shr_id FROM (wrk_perf_wrn i INNER JOIN wrk_perf b ON (i.APM_WRK_PERF_ID = b.APM_WRK_PERF_ID AND i.wrk_perf_ver_num = b.ver_num AND i.del_fl = 'N' AND b.del_fl = 'N' AND b.curr_ver_flag = 'Y')) INNER JOIN perf_hdr a ON (b.perf_hdr_id = a.perf_hdr_id AND a.del_fl = 'N' AND a.curr_ver_flag = 'Y') " +
		"UNION " +
		"SELECT m.USE_EWI_CDE, m.perf_hdr_id, m.APM_WRK_PERF_ID, m.wrk_perf_shr_id FROM ((wrk_perf_shr_wrn m INNER JOIN wrk_perf_shr c ON (m.wrk_perf_shr_id = c.wrk_perf_shr_id AND m.WRK_PERF_SHR_VER_NUM = c.ver_num and M.del_fl = 'N' and c.del_fl='N') inner join wrk_perf b on (c.APM_WRK_PERF_ID = b.APM_WRK_PERF_ID and b.del_fl='N' and b.curr_ver_flag = 'Y'))) inner join perf_hdr a on (b.perf_hdr_id = a.perf_hdr_id and a.del_fl='N' and a.curr_ver_flag='Y') " +
		") all_errors on ";
	
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_PERF_HDR_ALL_ERRORS = 
		UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_COMMON +
		" (a.PERF_HDR_ID = all_errors.perf_hdr_id) ";
	
	public static final String DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_WRK_PERF_ALL_ERRORS =
		UsageQueries.DYNAMIC_USAGE_SEARCH_JOIN_USAGE_ERROR_COMMON +
		" (a.PERF_HDR_ID = all_errors.perf_hdr_id and nvl(all_errors.APM_WRK_PERF_ID,b.APM_WRK_PERF_ID) = b.APM_WRK_PERF_ID) ";
*/		
//------------------------- Independent joins End----------------------------------------------	

	public static final String DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_COUNT_PART1 =

	
	
	" SELECT COUNT(APM_PERF_HDR_ID) TOTALCOUNT "
	+ " FROM ( ";		
	public static final String DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_COUNT_PART1_END = "	  SELECT DISTINCT A.APM_PERF_HDR_ID FROM ";



////		+  "   PERF_HDR A, 		WRK_PERF B, 		WRK_PERF_SHR C, 		PTY_NA F, 		PTY_NA G, 		(SELECT * FROM PERF_HDR_WRN WHERE (UPPER(DEL_FL) <> 'Y' OR DEL_FL IS NULL)) H, 		"
////		+  "   (SELECT * FROM WRK_PERF_WRN WHERE (UPPER(DEL_FL) <> 'Y' OR DEL_FL IS NULL))  I, 		"
//		+  "   PERF_HDR A, 		WRK_PERF B, 		WRK_PERF_SHR C, 		PTY_NA F, 		PTY_NA G, PERF_HDR_WRN  H, 		"
//		+  "   WRK_PERF_WRN I, 		"
//		+  "   MUS_USER_TYP_SEL J, 		"
//		+  "   MUS_USER_LIC K 		" +
//		   "   WHERE 			" +
//		   "   A.PERF_HDR_ID = B.PERF_HDR_ID(+) AND "
//		+  "   B.APM_WRK_PERF_ID = C.APM_WRK_PERF_ID(+) AND"
//		+  "   A.PTY_ID = F.PTY_ID(+) AND"
//		+  "   A.PTY_ID = J.PTY_ID(+) AND"
//		+  "   A.PTY_ID = K.PTY_ID(+) AND"
//		+  "   C.SHR_OWN_PTY_NA_ID = G.PTY_NA_ID(+) AND "
//		+  "   A.PERF_HDR_ID = H.PERF_HDR_ID(+) AND"
//		+  "   B.APM_WRK_PERF_ID = I.APM_WRK_PERF_ID(+) AND"
//		+  "   A.CURR_VER_FLAG = 'Y' "
//		+  "   AND a.del_fl = 'N' "
//		+  "   AND b.del_fl(+) = 'N' "
//		+  "   AND c.del_fl(+) = 'N' "
//		+  "   AND f.del_fl(+) = 'N' "
//		+  "   AND g.del_fl(+) = 'N' "
//		+  "   AND h.del_fl(+) = 'N' "
//		+  "   AND i.del_fl(+) = 'N' "
//		+  "   AND j.del_fl(+) = 'N' "
//		+  "   AND k.del_fl(+) = 'N' "
//		+  "   AND a.pgm_stt_dt between j.stt_dt(+) and j.end_dt(+) "
//		+  "   AND a.pgm_stt_dt between k.eff_dt(+) and k.end_dt(+) ";

	
	public static final String DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_COUNT_PART2 = 
	") ";

	public static final String DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_COUNT_LIMT_PART2 = 
	") where rownum <= ? ";

	public static final String DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_PART1 =
		
	/*###################### OLD_MANOJ #####################*/	
	" SELECT  * "
+  " FROM "
+  "   (SELECT  "
+ " LAG(APM_PERF_HDR_ID, 1, 0) OVER (ORDER BY APM_PERF_HDR_ID) AS PREV_ID,  LEAD(APM_PERF_HDR_ID, 1, 0) OVER (ORDER BY APM_PERF_HDR_ID) AS NEXT_ID, "
+ "  ROW_NUMBER () OVER ( ORDER BY PERFHDR.APM_PERF_HDR_ID) CURR_ROW_NUM,"
+  "   PERFHDR.APM_PERF_HDR_ID,"
+  "   PERFHDR.SRC_SYS,"
+  "   NVL(PERFHDR.LGY_MUS_USER_ID,  (PERFHDR.NA || PERFHDR.CAL_LTR_SUF)) LGY_MUS_USER_ID, "
//+  "   PERFHDR.LGY_MUS_USER_ID," xxxxxx
+  "   PERFHDR.LGY_MUS_USER_TYP,"
+  "   PERFHDR.PTY_ID,"
+  "   PGM_STT_DT,"
+  "   PGM_STT_TM,"
+  "   PGM_END_DT,"
+  "   PGM_END_TM,"
+  "   PERFHDR.PGM_OVRLP_CDE,"
+  "   PERFHDR.PGM_DUR,"
+  "   PERFHDR.SUR_TYP_ID,"
+  "   PERFHDR.SAM_TYP_ID,"
+  "   PERFHDR.SEG_NUM,"
+  "   PERFHDR.PGM_TTL,"
+  "   PERFHDR.PGM_NUM,"
+  "   PERFHDR.SET_LIST_TYP,"
+  "   PERFHDR.HDLNR_OPNR_IND,"
+  "   PERFHDR.TOUR_CNC_CNT,"
+  "   PERFHDR.ART_REV,"
+  "   PERFHDR.ERR_STATUS,"
+  "   PERFHDR.PROC_STATUS,"
+  "   PERFHDR.WRK_PERF_CNT,"
+  "   PERFHDR.TOTAL_NUM_PLAYS,"
+  "   PERFHDR.ASG_USR_ID,"
+  "   TO_CHAR(PERFHDR.STATUS_DATE,'MM/DD/YYYY HH24:MI:SS') STATUS_DATE,"
+  "   PERFHDR.SUPP_CODE,"
+  "   DECODE (PERFHDR.PROC_STATUS, 'RDY','N','PSTD','N','Y') EDITABLE,"
+  "   MUSUSERTYPEFORPERF.MUS_USER_TYP_CDE,"
+  "   PTYNAFORCALL.NA || PTYNAFORCALL.CAL_LTR_SUF CALL_LETTER_N_SUFF,"
+  "   PTYNAFORCALL.NA CALL_LETTER_ONLY, PTYNAFORCALL.CAL_LTR_SUF CALL_LETTER_SUFF_ONLY,"
+  "   MUSUSERLICFORPERF.LIC_TYP_CDE , "
+  "   MUSUSERTYPECODES.MUS_USER_TYP_DES MUSIC_USER_TYPE_DES "
+  "  FROM  APM_PERF_HDR PERFHDR,"
+  "   MVW_PTY_NA PTYNAFORCALL,"
+  "   MVW_MUS_USER_TYP_SEL MUSUSERTYPEFORPERF,"
+  "   MVW_MUS_USER_LIC MUSUSERLICFORPERF," 
+  "   MVW_MUS_USER_TYP MUSUSERTYPECODES,"
+  "   MVW_SUR_TYP SURVEYTYPECODES,"
+  "   MVW_SAM_TYP SAMPLETYPECODES "
+  " WHERE  PERFHDR.DEL_FL = 'N' " 
+  "   AND PERFHDR.PTY_ID = MUSUSERTYPEFORPERF.PTY_ID(+) "
+  "   AND PERFHDR.PTY_ID = MUSUSERLICFORPERF.PTY_ID(+) "
+  "   AND PERFHDR.PTY_ID = PTYNAFORCALL.PTY_ID(+) "
+  "   AND MUSUSERTYPEFORPERF.MUS_USER_TYP_CDE = MUSUSERTYPECODES.MUS_USER_TYP_CDE(+)  "
+  "   AND PERFHDR.SUR_TYP_ID = SURVEYTYPECODES.SUR_TYP_CDE(+) "
+  "   AND PERFHDR.SAM_TYP_ID = SAMPLETYPECODES.SAM_TYP_CDE(+) "
+  "   AND PERFHDR.PGM_STT_DT BETWEEN TO_CHAR(PTYNAFORCALL.STT_DT (+) ,'YYYY/MM/DD') AND TO_CHAR(PTYNAFORCALL.END_DT (+),'YYYY/MM/DD') "
+  "   AND PERFHDR.PGM_STT_DT BETWEEN TO_CHAR(MUSUSERLICFORPERF.EFF_DT(+),'YYYY/MM/DD') AND TO_CHAR(MUSUSERLICFORPERF.END_DT(+),'YYYY/MM/DD') "
+  "   AND PERFHDR.PGM_STT_DT BETWEEN TO_CHAR(MUSUSERTYPEFORPERF.STT_DT(+),'YYYY/MM/DD') and TO_CHAR(MUSUSERTYPEFORPERF.END_DT(+),'YYYY/MM/DD') "
+  "   AND PERFHDR.apm_PERF_HDR_ID IN 	( "
+ "/* FINAL REQUIRED PAGE END */		    " 
+ "select APM_perf_hdr_id  " 
+ "  from  " 
+ "  ( " 
+ "/* LIMINTING TO THE MAXC ROWS START */   " 
+ "  select ORIG_QUERY.*, ROWNUM rnum from  " 
+ "	  ( " 
+ "	  /* THE MAIN QUERY WITH ORDER BY CLAUSE START */ " ;
	public static final String DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_PART1_END = "	  SELECT DISTINCT a.APM_perf_hdr_id FROM ";

	public static final String DYNAMIC_SEARCH_PROGRAM_PERFORMANCES_PART2 =// ") ) A1 WHERE RN BETWEEN ? AND ? ";
//	"							  order by a.pgm_stt_tm, a.perf_hdr_id " 
	"							  order by a.apm_perf_hdr_id " 
//	"							   " 
+	"	  /* THE MAIN QUERY WITH ORDER BY CLAUSE START */ " 
+	" ) orig_query where ROWNUM <= ? /* MAX_ROWNUMBER*/ " 
+	" /* LIMINTING TO THE MAXC ROWS END */ " 
+	" ) limited_to_last_row_query " 
+	" where rnum  >= ? /* MIN_ROW_TO_FETCH */ " 
+	" ) /* FINAL REQUIRED PAGE END */ " 
+	" )  order by apm_perf_hdr_id ";	

	

	public static final String GET_MAIN_SEARCH_CONTEXT_START = " WITH CTX AS "  +
		"( " +
		"SELECT /*+ materialize */ * FROM ( " ;
	
	public static final String GET_MAIN_SEARCH_CONTEXT_END = " )) ";
	public static final String GET_MAIN_SEARCH_CONTEXT_JOIN = " , CTX ";
	public static final String GET_MAIN_SEARCH_CONTEXT_WHERE_CLAUSE = " AND A.APM_WRK_PERF_ID = CTX.APM_WRK_PERF_ID ";
	
	public static final String GET_MAIN_SEARCH_PFR_CONTAINS_SEARCH = " SELECT CT1.APM_WRK_PERF_ID FROM PAPM.APM_WRK_PERF CT1 WHERE CATSEARCH (CT1.PFR_NA, ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'NAME', 'CNTS') FROM DUAL), '') > 0 ";
	public static final String GET_MAIN_SEARCH_TTL_CONTAINS_SEARCH = " SELECT  CT.APM_WRK_PERF_ID FROM PAPM.APM_WRK_PERF CT WHERE CATSEARCH (CT.WRK_TTL, ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'TITLE', 'CNTS') FROM DUAL), '') > 0 ";
	
	
	
	public static final String DYNAMIC_SEARCH_WORK_PERFORMANCES_COUNT_PART1=

		" SELECT COUNT(APM_WRK_PERF_ID) TOTALCOUNT "
		+ " FROM ( ";		
	public static final String DYNAMIC_SEARCH_WORK_PERFORMANCES_COUNT_PART1_END=  "	  SELECT DISTINCT b.APM_WRK_PERF_ID FROM ";


	//public static final String DYNAMIC_SEARCH_WORK_PERFORMANCES_COUNT_PART2 = ")";
	public static final String DYNAMIC_SEARCH_WORK_PERFORMANCES_COUNT_PART2 = 
		") ";

	public static final String DYNAMIC_SEARCH_WORK_PERFORMANCES_COUNT_LIMT_PART2 = 
	") where rownum <= ? ";

	public static final String DYNAMIC_SEARCH_WORK_PERFORMANCES_PART1 = " SELECT "
+  " *"
+  " FROM "
+  "   (SELECT " 
//+  "   ROWNUM RN,"
+  "   WRKPERF.APM_PERF_HDR_ID,"
+  "   WRKPERF.APM_WRK_PERF_ID,"
+  "   WRKPERF.APM_ARCHIVE_ID,"
+  "   WRKPERF.WRK_SEQ_NR,"
+  "   WRKPERF.MEDL_SEQ,"
+  "   WRKPERF.WRK_TTL,"
+  "   WRKPERF.WRK_ID,"
+  "   WRKPERF.USE_TYP,"
+  "   WRKPERF.WRK_PERF_DUR,"
+  "   WRKPERF.PFR_NA,"
+  "   WRKPERF.PLAY_CNT,"
+  "   WRKPERF.ERR_STATUS,"
+  "   WRKPERF.DEL_FL,"

+  "   WRKPERF.DETECTION_TM,"
+  "   WRKPERF.CONFIDENCE_LVL,"
+  "   WRKPERF.WRITER,"
+  "   WRKPERF.LIBRARY_DISC,"
+  "   WRKPERF.LIBRARY_DISC_ID,"



+  "   PERFHDR.SRC_SYS," 
+  "   PERFHDR.LGY_MUS_USER_ID,"
+  "   PERFHDR.LGY_MUS_USER_TYP,"
+  "   PERFHDR.PTY_ID,"
+  "   PERFHDR.PGM_STT_DT,"
+  "   PERFHDR.PGM_STT_TM,"
+  "   PERFHDR.PGM_END_DT,"
+  "   PERFHDR.PGM_END_TM,"
+  "   PERFHDR.SUR_TYP_ID,"
+  "   PERFHDR.SAM_TYP_ID,"
+  "   PERFHDR.PGM_TTL,"
+  "   WRKPERF.SUPP_CODE,"
+  "   PERFHDR.PGM_NUM,"
+  "   DECODE (PERFHDR.PROC_STATUS, 'RDY','N','PSTD','N','Y') HDR_EDITABLE,"
+  "   DECODE (PERFHDR.PROC_STATUS, 'RDY','N','PSTD','N', DECODE(WRKPERF.LOCK_IND,'Y','N','Y')) WORK_PERF_EDITABLE,"
+  "   trim(PTYNAFORMUS.NA ||  PTYNAFORMUS.CAL_LTR_SUF)  PTYNAFORMUS_FULL_NAME, "
+  "   MUSUSERLICFORPERF.LIC_TYP_CDE, "
+  "   MUSUSERTYPEFORPERF.MUS_USER_TYP_CDE,"
+  "   MUSUSERTYPECODES.MUS_USER_TYP_DES MUSIC_USER_TYPE_DES,"
+  "   SURVEYTYPECODES.DES SURVEY_TYPE_DES,"
+  "   SAMPLETYPECODES.DES SAMPLE_TYPE_DES, "
+  "   USETYPECODES.DES USE_TYPE_DES "
+  "  FROM  APM_WRK_PERF WRKPERF,"
+  "   APM_PERF_HDR PERFHDR,"
+  "   MVW_PTY_NA PTYNAFORMUS,"
+  "   MVW_MUS_USER_LIC MUSUSERLICFORPERF,"
+  "   MVW_MUS_USER_TYP_SEL MUSUSERTYPEFORPERF,"
+  "   MVW_MUS_USER_TYP MUSUSERTYPECODES,"
+  "   MVW_SUR_TYP SURVEYTYPECODES,"
+  "   MVW_SAM_TYP SAMPLETYPECODES,"
+  "   MVW_USE_TYP USETYPECODES "
+  " WHERE  " 
+  "   WRKPERF.APM_PERF_HDR_ID = PERFHDR.APM_PERF_HDR_ID AND"
+  "   PERFHDR.PTY_ID = PTYNAFORMUS.PTY_ID(+) AND"
+  "   PERFHDR.PTY_ID = MUSUSERTYPEFORPERF.PTY_ID(+) AND"
+  "   PERFHDR.PTY_ID = MUSUSERLICFORPERF.PTY_ID(+) AND "
+  "   MUSUSERTYPEFORPERF.MUS_USER_TYP_CDE = MUSUSERTYPECODES.MUS_USER_TYP_CDE(+) AND"
+  "   PERFHDR.SUR_TYP_ID = SURVEYTYPECODES.SUR_TYP_CDE(+) AND"
+  "   PERFHDR.SAM_TYP_ID = SAMPLETYPECODES.SAM_TYP_CDE(+) AND"
+  "   WRKPERF.USE_TYP = USETYPECODES.USE_TYP_CDE(+) AND"
+  "   perfhdr.pgm_stt_dt between TO_CHAR(mususertypeforperf.stt_dt(+),'YYYY/MM/DD') and TO_CHAR(mususertypeforperf.end_dt(+),'YYYY/MM/DD') AND "
+  "   perfhdr.pgm_stt_dt between TO_CHAR(PTYNAFORMUS.stt_dt(+),'YYYY/MM/DD') and TO_CHAR(PTYNAFORMUS.end_dt(+),'YYYY/MM/DD')  AND "   
+  "   perfhdr.pgm_stt_dt between to_char(mususerlicforperf.eff_dt(+),'YYYY/MM/DD') and TO_CHAR(mususerlicforperf.end_dt(+),'YYYY/MM/DD') "
+ " AND WRKPERF.APM_WRK_PERF_ID IN 	(" 
+ "/* FINAL REQUIRED PAGE END */		    " 
+ "select APM_WRK_PERF_ID  " 
+ "  from  " 
+ "  ( " 
+ "/* LIMINTING TO THE MAXC ROWS START */   " 
+ "  select ORIG_QUERY.*, ROWNUM rnum from  " 
+ "	  ( " 
+ "	  /* THE MAIN QUERY WITH ORDER BY CLAUSE START */ ";
	
	public static final String DYNAMIC_SEARCH_WORK_PERFORMANCES_PART1_END = "	  SELECT DISTINCT b.WRK_SEQ_NR, b.WRK_TTL, b.APM_WRK_PERF_ID FROM ";

	public static final String DYNAMIC_SEARCH_WORK_PERFORMANCES_PART2 =		
		"							  order by NVL2(B.WRK_SEQ_NR, TO_NUMBER(B.WRK_SEQ_NR),0) , b.APM_WRK_PERF_ID "
	+	"	  /* THE MAIN QUERY WITH ORDER BY CLAUSE START */ " 
	+	" ) orig_query where ROWNUM <= ? /* MAX_ROWNUMBER*/ " 
	+	" /* LIMINTING TO THE MAXC ROWS END */ " 
	+	" ) limited_to_last_row_query " 
	+	" where rnum  >= ? /* MIN_ROW_TO_FETCH */ " 
	+	" ) /* FINAL REQUIRED PAGE END */ " 
	+	" )  order by NVL2(WRK_SEQ_NR, TO_NUMBER(WRK_SEQ_NR),0), APM_WRK_PERF_ID";

	public static final String GET_PROGRAM_PERFORMANCE =
"   SELECT "
+  "   PERFHDR.APM_PERF_HDR_ID,"
+  "   PERFHDR.SRC_SYS,"
+  "   NVL(PERFHDR.LGY_MUS_USER_ID,  (PERFHDR.NA || PERFHDR.CAL_LTR_SUF)) LGY_MUS_USER_ID, "
+  "   PERFHDR.LGY_MUS_USER_TYP,"
+  "   PERFHDR.PTY_ID,"
+  "   PGM_STT_DT,"
+  "   PGM_STT_TM,"
+  "   PGM_END_DT,"
+  "   PGM_END_TM,"
+  "   PERFHDR.PGM_OVRLP_CDE,"
+  "   PERFHDR.PGM_DUR,"
+  "   PERFHDR.SUR_TYP_ID,"
+  "   PERFHDR.SAM_TYP_ID,"
+  "   PERFHDR.SEG_NUM,"
+  "   PERFHDR.PGM_TTL,"
+  "   PERFHDR.PGM_NUM,"
+  "   PERFHDR.SET_LIST_TYP,"
+  "   PERFHDR.HDLNR_OPNR_IND,"
+  "   PERFHDR.TOUR_CNC_CNT,"
+  "   PERFHDR.ART_REV,"
+  "   PERFHDR.TOTAL_NUM_PLAYS,"
+  "   PERFHDR.ERR_STATUS,"
+  "   PERFHDR.SUPP_CODE,"
+  "   PERFHDR.PROC_STATUS,"
+  "   PERFHDR.TGTSURVYEARQTR,"
+  "   PERFHDR.WRK_PERF_CNT,"
+  "   PERFHDR.APM_ARCHIVE_ID,"
+  "   PERFHDR.PFR_NA,"
+  "   PERFHDR.CLASSICAL_IND,"
+  "   PERFHDR.ASG_USR_ID,"
+  "   DECODE (PERFHDR.PROC_STATUS, 'RDY','N','PSTD','N','Y') EDITABLE,"
+  "   PTYNAFORMUS.NA,"
+  "   PTYNAFORMUS.CAL_LTR_SUF,"
+  "   trim(  PTYNAFORMUS.NA || ' ' || PTYNAFORMUS.CAL_LTR_SUF) PTYNAFORMUS_FULL_NAME, "
+  "   MUSUSERTYPEFORPERF.MUS_USER_TYP_CDE,"
+  "   MUSUSERLICFORPERF.LIC_TYP_CDE,"
+  "   MUSUSERTYPECODES.MUS_USER_TYP_DES MUSIC_USER_TYPE_DES,"
+  "   LICENSETYPECODES.NA LICENSE_TYPE_DES,"
+  "   SURVEYTYPECODES.DES SURVEY_TYPE_DES,"
+  "   SAMPLETYPECODES.DES SAMPLE_TYPE_DES, "
+  "   SUPPFORMAT.CHANNEL_IND  "
+  "  FROM  APM_PERF_HDR PERFHDR,"
+  "   MVW_PTY_NA PTYNAFORMUS,"
+  "   MVW_MUS_USER_TYP_SEL MUSUSERTYPEFORPERF,"
+  "   MVW_MUS_USER_LIC MUSUSERLICFORPERF,"
+  "   MVW_MUS_USER_TYP MUSUSERTYPECODES,"
+  "   MVW_LIC_TYP LICENSETYPECODES,"
+  "   MVW_SUR_TYP SURVEYTYPECODES,"
+  "   MVW_SAM_TYP SAMPLETYPECODES, "
+  "    SUPP_FORMAT SUPPFORMAT "
+  " WHERE PERFHDR.DEL_FL = 'N' AND "
+  "   (PERFHDR.PTY_ID = PTYNAFORMUS.PTY_ID(+) ) AND "
+  "   PERFHDR.PTY_ID = MUSUSERTYPEFORPERF.PTY_ID(+) AND"
+  "   PERFHDR.PTY_ID = MUSUSERLICFORPERF.PTY_ID(+) AND"
+  "   MUSUSERTYPEFORPERF.MUS_USER_TYP_CDE = MUSUSERTYPECODES.MUS_USER_TYP_CDE(+) AND"
+  "   MUSUSERLICFORPERF.LIC_TYP_CDE = LICENSETYPECODES.LIC_TYP_CDE(+) AND"
+  "   PERFHDR.SUR_TYP_ID = SURVEYTYPECODES.SUR_TYP_CDE(+) AND"
+  "   PERFHDR.SAM_TYP_ID = SAMPLETYPECODES.SAM_TYP_CDE(+) "
+  "   AND PERFHDR.PGM_STT_DT  BETWEEN TO_CHAR(MUSUSERTYPEFORPERF.STT_DT(+),'YYYY/MM/DD') AND TO_CHAR(MUSUSERTYPEFORPERF.END_DT(+),'YYYY/MM/DD')  " 
+  "   AND PERFHDR.PGM_STT_DT  BETWEEN TO_CHAR( MUSUSERLICFORPERF.EFF_DT(+),'YYYY/MM/DD') AND TO_CHAR(MUSUSERLICFORPERF.END_DT(+),'YYYY/MM/DD')   " 
+  "   AND PERFHDR.PGM_STT_DT  BETWEEN TO_CHAR(PTYNAFORMUS.STT_DT(+),'YYYY/MM/DD') AND TO_CHAR(PTYNAFORMUS.END_DT(+),'YYYY/MM/DD') "
+  "   AND PERFHDR.SUPP_CODE = SUPPFORMAT.SUPP_CODE "
+  "   AND PERFHDR.APM_PERF_HDR_ID = ?  ";

	public static final String GET_WORK_PERFORMANCE =
"   SELECT "
+  "   WRKPERF.APM_PERF_HDR_ID,"
+  "   WRKPERF.APM_WRK_PERF_ID,"
+  "   WRKPERF.APM_ARCHIVE_ID,"
+  "   WRKPERF.WRK_SEQ_NR,"
+  "   WRKPERF.MEDL_SEQ,"
+  "   WRKPERF.WRK_TTL,"
+  "   WRKPERF.WRK_ID,"
+  "   WRKPERF.USE_TYP,"
+  "   WRKPERF.WRK_PERF_DUR,"
+  "   WRKPERF.PFR_NA,"
+  "   WRKPERF.PLAY_CNT,"
+  "   WRKPERF.ERR_STATUS,"
+  "   WRKPERF.PROVIDER_ID,"
+  "   WRKPERF.APM_MATCH_TYP,"
+  "   WRKPERF.SUPP_CODE,"
+  "   TO_CHAR(WRKPERF.STATUS_DATE,'YYYY/MM/DD HH24:MI:SS') STATUS_DATE,"
+  "   WRKPERF.PROC_STATUS PROC_STATUS,"
+  "   WRKPERF.DETECTION_TM DETECTION_TM,"
+  "   WRKPERF.LIBRARY_DISC LIBRARY_DISC,"
+  "   WRKPERF.LIBRARY_DISC_ID LIBRARY_DISC_ID,"
+  "   WRKPERF.WRITER WRITER,"
+  "   WRKPERF.CONFIDENCE_LVL CONFIDENCE_LVL,"
+  "   PERFHDR.SRC_SYS,"
+  "   PERFHDR.LGY_MUS_USER_ID,"
+  "   PERFHDR.LGY_MUS_USER_TYP,"
+  "   PERFHDR.PTY_ID,"
+  "   PGM_STT_DT,"
+  "   PGM_STT_TM,"
+  "   PGM_END_DT,"
+  "   PGM_END_TM,"
+  "   PERFHDR.SUPP_CODE,"
+  "   PERFHDR.SUR_TYP_ID,"
+  "   PERFHDR.SAM_TYP_ID,"
+  "   PERFHDR.PGM_TTL,"
+  "   PERFHDR.PGM_NUM,"
+  "   PERFHDR.TGTSURVYEARQTR TGTSURVYEARQTR,"
+  "   DECODE (PERFHDR.PROC_STATUS, 'RDY','N','PSTD','N','Y') HDR_EDITABLE,"
+  "   DECODE (PERFHDR.PROC_STATUS, 'RDY','N','PSTD','N', DECODE(WRKPERF.LOCK_IND,'Y','N','Y')) WORK_PERF_EDITABLE,"
+  "   PTYNAFORMUS.NA,"
+  "   trim( PTYNAFORMUS.NA || PTYNAFORMUS.CAL_LTR_SUF) PTYNAFORMUS_FULL_NAME, "
+  "   MUSUSERTYPEFORPERF.MUS_USER_TYP_CDE,"
+  "   MUSUSERLICFORPERF.LIC_TYP_CDE,"
+  "   MUSUSERTYPECODES.MUS_USER_TYP_DES MUSIC_USER_TYPE_DES,"
+  "   LICENSETYPECODES.NA LICENSE_TYPE_DES,"
+  "   SURVEYTYPECODES.DES SURVEY_TYPE_DES,"
+  "   SAMPLETYPECODES.DES SAMPLE_TYPE_DES,"
+  "   MATCHTYP.DESCRIPTION MATCH_TYPE_DES,"
+  "   USETYPECODES.DES USE_TYPE_DES"
+  "  FROM  APM_WRK_PERF WRKPERF,"
+  "   APM_PERF_HDR PERFHDR,"
+  "   MVW_PTY_NA PTYNAFORMUS,"
+  "   MVW_MUS_USER_TYP_SEL MUSUSERTYPEFORPERF,"
+  "   MVW_MUS_USER_LIC MUSUSERLICFORPERF,"
+  "   MVW_MUS_USER_TYP MUSUSERTYPECODES,"
+  "   MVW_LIC_TYP LICENSETYPECODES,"
+  "   MVW_SUR_TYP SURVEYTYPECODES,"
+  "   MVW_SAM_TYP SAMPLETYPECODES,"
+  "   MVW_USE_TYP USETYPECODES, "
+  "   MATCH_TYP MATCHTYP "
+  " WHERE WRKPERF.DEL_FL = 'N' AND PERFHDR.DEL_FL = 'N' AND "
+  "   WRKPERF.APM_PERF_HDR_ID = PERFHDR.APM_PERF_HDR_ID AND"
+  "   (PERFHDR.PTY_ID = PTYNAFORMUS.PTY_ID(+) ) AND"
+  "   PERFHDR.PTY_ID = MUSUSERTYPEFORPERF.PTY_ID(+) AND"
+  "   PERFHDR.PTY_ID = MUSUSERLICFORPERF.PTY_ID(+) AND"
+  "   MUSUSERTYPEFORPERF.MUS_USER_TYP_CDE = MUSUSERTYPECODES.MUS_USER_TYP_CDE(+) AND"
+  "   MUSUSERLICFORPERF.LIC_TYP_CDE = LICENSETYPECODES.LIC_TYP_CDE(+) AND"
+  "   PERFHDR.SUR_TYP_ID = SURVEYTYPECODES.SUR_TYP_CDE(+) AND"
+  "   PERFHDR.SAM_TYP_ID = SAMPLETYPECODES.SAM_TYP_CDE(+) AND"
+  "   WRKPERF.USE_TYP = USETYPECODES.USE_TYP_CDE(+) AND "
+  "   WRKPERF.APM_MATCH_TYP = MATCHTYP.MATCH_TYP_CDE(+) "
+  "   AND PERFHDR.PGM_STT_DT  BETWEEN TO_CHAR(MUSUSERTYPEFORPERF.STT_DT(+),'YYYY/MM/DD') AND TO_CHAR(MUSUSERTYPEFORPERF.END_DT(+),'YYYY/MM/DD')  " 
+  "   AND PERFHDR.PGM_STT_DT  BETWEEN TO_CHAR( MUSUSERLICFORPERF.EFF_DT(+),'YYYY/MM/DD') AND TO_CHAR(MUSUSERLICFORPERF.END_DT(+),'YYYY/MM/DD')   " 
+  "   AND PERFHDR.PGM_STT_DT  BETWEEN TO_CHAR(PTYNAFORMUS.STT_DT(+),'YYYY/MM/DD') AND TO_CHAR(PTYNAFORMUS.END_DT(+),'YYYY/MM/DD') "
+ "    AND WRKPERF.APM_WRK_PERF_ID = ? "; 

	
	public static final String GET_PROGRAM_PERFORMANCE_MESSAGES = 
		"SELECT A.APM_PERF_HDR_ID PERF_ID, " +
		"         A.ERR_CDE, " +
		"         A.ERR_CAT, " +
		"         B.ERR_DES " +
		"         || DECODE (B.VLD_TYP_CDE, " +
		"                    'FLD', '  - ' " +
		"                           || (SELECT DES " +
		"                                 FROM FLD_NA " +
		"                                WHERE FLD_NA = A.FIELDNAME), " +
		"                    '') " +
		"            MSG_DES " +
		"    FROM APM_ERROR_LOG A, APM_ERR_CDE B " +
		"   WHERE     A.APM_PERF_HDR_ID = ? " +
		"         AND A.APM_WRK_PERF_ID IS NULL " +
		"         AND A.ERR_CDE = B.ERR_CDE(+) " +
		"         AND A.DEL_FL = 'N' "; 




	public static final String GET_WORK_PERFORMANCE_MESSAGES =
		"SELECT A.APM_WRK_PERF_ID PERF_ID, " +
		"       A.ERR_CDE, " +
		"       A.ERR_CAT, " +
		"       B.ERR_DES " +
		"       || DECODE (B.VLD_TYP_CDE, " +
		"                  'FLD', '  - ' " +
		"                         || (SELECT DES " +
		"                               FROM FLD_NA " +
		"                              WHERE FLD_NA = A.FIELDNAME), " +
		"                  '') " +
		"          MSG_DES " +
		"  FROM APM_ERROR_LOG A, APM_ERR_CDE B " +
		" WHERE     A.APM_WRK_PERF_ID = ? " +
		"       AND A.ERR_CDE = B.ERR_CDE(+) " +
		"       AND A.DEL_FL = 'N' ";




	public static final String GET_PROGRAM_PERFORMANCE_MESSAGE_BY_CODE = "SELECT "
+ "	A.PERF_HDR_ID PERF_ID,"
+ "	A.PRG_PERF_VER_NUM PERF_VER,"
+ "	A.USE_EWI_CDE,"
+ "	B.EWI_CAT_CDE,"
+ "	B.DES MSG_DES,"
+ "	C.EWI_DES MSG_CAT_DES"
+ " FROM PERF_HDR_WRN A,USE_ERR_WRN_INFO B,EWI_CAT C WHERE "
+ "	A.USE_EWI_CDE = B.USE_EWI_CDE(+) AND "
+ "	B.EWI_CAT_CDE = C.EWI_CAT_CDE(+) AND "
+ " (B.DEL_FL <> 'Y' OR B.DEL_FL IS NULL) AND "
+ " (C.DEL_FL <> 'Y' OR C.DEL_FL IS NULL) AND "
+ "	A.PERF_HDR_ID = ? "
+ " AND "
+ "	A.PRG_PERF_VER_NUM = ? AND A.USE_EWI_CDE = ? ";

	public static final String GET_WORK_PERFORMANCE_MESSAGE_BY_CODE ="SELECT "
+ "	A.APM_WRK_PERF_ID PERF_ID,"
+ "	A.WRK_PERF_VER_NUM PERF_VER,"
+ "	A.USE_EWI_CDE,"
+ "	B.EWI_CAT_CDE,"
+ "	B.DES MSG_DES,"
+ "	C.EWI_DES MSG_CAT_DES"
+ "	FROM WRK_PERF_WRN A,USE_ERR_WRN_INFO B,EWI_CAT C WHERE "
+ "	A.USE_EWI_CDE = B.USE_EWI_CDE(+) AND "
+ "	B.EWI_CAT_CDE = C.EWI_CAT_CDE(+) AND "
+ " (B.DEL_FL <> 'Y' OR B.DEL_FL IS NULL) AND "
+ " (C.DEL_FL <> 'Y' OR C.DEL_FL IS NULL) AND "
+ "	A.APM_WRK_PERF_ID = ? "
+ " AND "
+ "	A.WRK_PERF_VER_NUM = ? AND A.USE_EWI_CDE = ? ";

	

	//public static final String GET_PROGRAM_PERFORMANCE_CURRENT_VERSION_NUMBER = "SELECT NVL(MAX(VER_NUM),0) CURR_VER FROM PERF_HDR WHERE PERF_HDR_ID = ? AND CURR_VER_FLAG='Y'";

	public static final String GET_WORK_PERFORMANCE_CURRENT_VERSION_NUMBER = "SELECT NVL(MAX(VER_NUM),0) CURR_VER FROM APM_WRK_PERF WHERE APM_WRK_PERF_ID = ? AND CURR_VER_FLAG='Y'";


	//public static final String GET_PROGRAM_PERFORMANCE_HIGHEST_VERSION_NUMBER = "SELECT NVL(MAX(VER_NUM),0) HIGH_VER FROM PERF_HDR WHERE PERF_HDR_ID = ?";

	public static final String GET_WORK_PERFORMANCE_HIGHEST_VERSION_NUMBER = "SELECT NVL(MAX(VER_NUM),0) HIGH_VER FROM APM_WRK_PERF WHERE APM_WRK_PERF_ID = ?";

	public static final String GET_WORK_PERFORMANCE_HIGHEST_WORKSEQUENCE_NUMBER = "SELECT NVL(MAX(   to_number(WRK_SEQ_NR)   ),0) HIGH_WRK_SEQ_NR FROM APM_WRK_PERF WHERE APM_PERF_HDR_ID = ? ";

	public static final String GET_WORK_PERFORMANCE_SMALLEST_WORKSEQUENCE_NUMBER = "SELECT NVL(MIN(TO_NUMBER(WRK_SEQ_NR)),0) FROM APM_WRK_PERF WHERE 1=1 AND APM_WRK_PERF_ID IN ";

	public static final String GET_WORK_PERFORMANCE_HIGHEST_MEDLEY_SEQUENCE_NUMBER = "SELECT NVL(MAX(TO_NUMBER(MEDL_SEQ)),0) FROM APM_WRK_PERF WHERE WRK_SEQ_NR = ? AND apm_perf_hdr_id in (select apm_perf_hdr_id from APM_WRK_PERF where APM_WRK_PERF_ID IN ";

	public static final String GET_PROGRAM_PERFORMANCE_IS_DISTRIBUTED = "SELECT 'Y' FROM DUAL WHERE EXISTS (SELECT 'X' FROM APM_WRK_PERF WHERE PERF_HDR_ID = ? AND DIS_ID IS NOT NULL)";
	
	public static final String GET_IF_PART_OF_BASE_CREDITS_CALCULATION_METHOD_FM1_MUZAK = "SELECT 'Y' FROM DUAL WHERE EXISTS (SELECT 'X' FROM DIS_BAS_CR_CALC WHERE CALC_TYPE = '"+ DistributionConstants.BASE_CREDIT_CALCULATION_METHOD_FM1_MUZAK +"' AND MUS_USER = ?) ";
	
	public static final String GET_IF_PART_OF_BASE_CREDITS_CALCULATION_METHOD_MUSIC_CHOICE = "SELECT 'Y' FROM DUAL WHERE EXISTS (SELECT 'X' FROM DIS_BAS_CR_CALC WHERE CALC_TYPE = '"+ DistributionConstants.BASE_CREDIT_CALCULATION_METHOD_MUSIC_CHOICE +"' AND MUS_USER = ?) ";

	public static final String GET_NOF_WORK_PERFORMANCES_IN_PERFORMACE_HEADER = "SELECT COUNT(APM_WRK_PERF_ID) FROM APM_WRK_PERF WHERE PERF_HDR_ID = ? AND (DEL_FL <> 'Y' OR DEL_FL IS NULL) ";
	
	//Please check with business
	//two things possible
	//"SELECT 'Y' FROM DUAL WHERE EXISTS (SELECT 'X' FROM WRK_PERF WHERE PERF_HDR_ID = (SELECT perf_hdr_id FROM APM_WRK_PERF WHERE APM_WRK_PERF_ID = ? ) AND DIS_ID IS NOT NULL)";
	public static final String GET_WORK_PERFORMANCE_IS_DISTRIBUTED ="SELECT 'Y' FROM DUAL WHERE EXISTS (SELECT 'X' FROM APM_WRK_PERF WHERE APM_WRK_PERF_ID = ? AND DIS_ID IS NOT NULL)";

	//Just Theoretically the following is correct but logically and businesswise the query being used is fine
//	"SELECT XA.DIS_ID FROM DIS XA WHERE  to_number( (trim(to_char(XA.DIS_YR,'0999')) || trim(to_char(XA.CRT_DIS_NR,'099'))) ,'9999999') =  " +
//	"(  " +
//	"SELECT min( to_number( (trim(to_char(A.DIS_YR,'0999')) || trim(to_char(A.CRT_DIS_NR,'099'))) ,'9999999') )  FROM DIS A WHERE A.DIS_ID IN (SELECT B.DIS_ID FROM APM_WRK_PERF B WHERE B.PERF_HDR_ID = ? ) " +
//	") " +
//	"AND XA.DIS_ID  " +
//	"IN (SELECT XC.DIS_ID FROM APM_WRK_PERF XC WHERE XC.PERF_HDR_ID = ? ) ";
	//public static final String GET_WORK_PERFORMANCE_LOWEST_DISTRIBUTION_ID ="SELECT XA.DIS_ID FROM DIS XA WHERE XA.DIS_DT = ( SELECT MIN(A.DIS_DT) FROM DIS A WHERE A.DIS_ID IN (SELECT B.DIS_ID FROM APM_WRK_PERF B WHERE B.PERF_HDR_ID = ?) ) AND (XA.DIS_ID,XA.DIS_DT) IN (SELECT XC.DIS_ID,XD.DIS_DT FROM APM_WRK_PERF XC, DIS XD WHERE XC.PERF_HDR_ID = ? AND XC.DIS_ID = XD.DIS_ID)";

	public static final String GET_IF_WORK_EXIST = "SELECT 'Y' FROM MVW_WRK WHERE WRK_ID = ?";

	public static final String GET_IF_PART_OF_MEDLEY = "SELECT 'Y' FROM DUAL WHERE ((SELECT COUNT(*) FROM APM_WRK_PERF WHERE TO_NUMBER(WRK_SEQ_NR) = TO_NUMBER(?) AND APM_PERF_HDR_ID = ?) > 1) OR EXISTS (SELECT 'Y' FROM APM_WRK_PERF WHERE APM_WRK_PERF_ID = ? AND NVL(MEDL_SEQ,0) != 0)";
	
	public static final String GET_IF_MEDLEY_USE_TYPE_NOT_PART_OF_MEDLEY = "SELECT 'Y' FROM DUAL WHERE ((SELECT COUNT(*) FROM APM_WRK_PERF WHERE TO_NUMBER(WRK_SEQ_NR) = TO_NUMBER(?) AND APM_PERF_HDR_ID = ?) > 1) AND EXISTS (SELECT 'Y' FROM APM_WRK_PERF WHERE APM_WRK_PERF_ID = ? AND NVL(MEDL_SEQ,0) != 0)";

	public static final String GET_IF_MEDLEY_BUT_SEQUENCE_NUMBER_MISSING = 
  " SELECT 'Y' FROM DUAL WHERE EXISTS ( "
+ " SELECT A.APM_WRK_PERF_ID "
+ "FROM APM_WRK_PERF A, "
+ "  (SELECT  APM_PERF_HDR_ID,WRK_SEQ_NR "
+ "	 FROM 	APM_WRK_PERF "
+ "	 WHERE 	APM_PERF_HDR_ID = ? "
+ "	 GROUP BY APM_PERF_HDR_ID, WRK_SEQ_NR "
+ "	 HAVING 	 COUNT(WRK_SEQ_NR)>1) B "
+ "WHERE A.APM_PERF_HDR_ID = B.APM_PERF_HDR_ID "
+ "AND   A.WRK_SEQ_NR  = B.WRK_SEQ_NR "
+ "AND  (A.MEDL_SEQ = 0 OR A.MEDL_SEQ IS NULL) "
+ "AND	 A.APM_WRK_PERF_ID = ? ";

	public static final String GET_IF_RULE_GROUP_MEMBER = "SELECT 'Y' FROM DUAL WHERE EXISTS ( SELECT * FROM RULE_GRP_MBR WHERE (RULE_GRP_CDE = ? AND MUS_USER_TYP_CDE = ?) AND (DEL_FL <> 'Y' OR DEL_FL IS NULL))";
	
	public static final String GET_IF_MEDLEY_DISTRIBUTED = "SELECT DIS_ID FROM APM_WRK_PERF WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND UPPER(CURR_VER_FLAG) ='Y' AND WRK_SEQ_NR = ? AND APM_WRK_PERF_ID IN ";

	public static final String GET_DURATION_SUM_OF_WORK_PERFORMANCES = "SELECT SUM(DUR) FROM APM_WRK_PERF WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND CURR_VER_FLAG = 'Y' AND PERF_HDR_ID = NVL(?,'999999999999999999999999999999')  ";

	public static final String GET_WORK_PERFORMANCE_IDS_OF_MEDLEY = "SELECT APM_WRK_PERF_ID FROM APM_WRK_PERF WHERE DEL_FL='N' AND APM_PERF_HDR_ID = ? AND WRK_SEQ_NR = ? ";

	public static final String GET_IF_LIBRARY_SURVEY_OVERRIDE = "SELECT 'Y' FROM DUAL WHERE EXISTS ( SELECT * FROM LIB_SUR_OVRD WHERE (MUS_USER_TYP_CDE = ?) AND (DEL_FL <> 'Y' OR DEL_FL IS NULL))";

	public static final String GET_WORK_TYPE_CODE = "SELECT WRK_TYP_CDE FROM WRK WHERE WRK_ID = ?  AND (DEL_FL <> 'Y' OR DEL_FL IS NULL)";

	public static final String GET_IF_DUPLICATE_WRK_IN_MEDLEY = //"SELECT 'Y' FROM DUAL WHERE (SELECT COUNT(*) FROM APM_WRK_PERF WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND UPPER(CURR_VER_FLAG) ='Y' AND wrk_seq_nr IS NOT NULL AND (SELECT COUNT(*) FROM APM_WRK_PERF WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND UPPER(CURR_VER_FLAG) ='Y' AND WRK_SEQ_NR = ? AND PERF_HDR_ID = ?) > 1 AND WRK_SEQ_NR = ? AND PERF_HDR_ID = ?  AND wrk_id = (SELECT wrk_id FROM APM_WRK_PERF WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND UPPER(CURR_VER_FLAG) ='Y' AND WRK_SEQ_NR = ? AND PERF_HDR_ID = ? AND APM_WRK_PERF_ID = ?)  GROUP BY perf_hdr_id,wrk_seq_Nr,wrk_id) > 1 ";
	" SELECT 'Y'"   +
"   FROM DUAL"   +
"  WHERE ("   +
" SELECT SUM(NUM) DUP FROM ("   +
" SELECT   COUNT (*) NUM"   +
"  FROM APM_WRK_PERF"   +
" WHERE "   +
"   "   +
"   wrk_seq_nr IS NOT NULL"   +
"   AND (SELECT COUNT (*)"   +
" FROM APM_WRK_PERF"   +
"   WHERE wrk_seq_nr = ?"   +	//1 seq_nr
"  AND APM_perf_hdr_id = ?) > 1"   + //2 perf_hdr_id
"   AND wrk_seq_nr = ?"   + // 3 seq_nr
"   AND APM_perf_hdr_id = ?"   + // 4 perf_hdr_id
"   AND (   wrk_id IN "   +
"  (SELECT b.wrk_id"   +
"  FROM APM_WRK_PERF a, alt_wrk_id b"   +
"    WHERE (a.del_fl <> 'Y' OR a.del_fl IS NULL)"   +
"   AND b.del_fl = 'N'"   +
"   AND a.wrk_id = b.alt_id"   +
"   AND b.alt_wrk_id_typ_cde = 'TRFID'"   +
"   AND UPPER (curr_ver_flag) = 'Y'"   +
"   AND wrk_seq_nr = ?"   + //5 seq_nr
"   AND perf_hdr_id = ?"   + //6 perf_hdr_id
"   AND APM_WRK_PERF_ID = ?)"   + // 7 APM_WRK_PERF_ID
"  OR wrk_id IN "   +
"  (SELECT wrk_id"   +
"  FROM APM_WRK_PERF"   +
"    WHERE (del_fl <> 'Y' OR del_fl IS NULL)"   +
"   AND UPPER (curr_ver_flag) = 'Y'"   +
"   AND wrk_seq_nr = ?"   + //8 seq_nr
"   AND perf_hdr_id = ?"   + //9 perf_hdr_id
"   AND APM_WRK_PERF_ID = ?)"   + //10 APM_WRK_PERF_ID
" )"   +
" GROUP BY perf_hdr_id, wrk_seq_nr, wrk_id	)	) > 1"  ;


	public static final String GET_IF_DUPLICATE_MERGE_WRK_IN_MEDLEY = //"SELECT 'Y' FROM DUAL WHERE (SELECT COUNT(*) FROM APM_WRK_PERF WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND UPPER(CURR_VER_FLAG) ='Y' AND wrk_seq_nr IS NOT NULL AND (SELECT COUNT(*) FROM APM_WRK_PERF WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND UPPER(CURR_VER_FLAG) ='Y' AND WRK_SEQ_NR = ? AND PERF_HDR_ID = ?) > 1 AND WRK_SEQ_NR = ? AND PERF_HDR_ID = ?  AND wrk_id = (SELECT wrk_id FROM APM_WRK_PERF WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND UPPER(CURR_VER_FLAG) ='Y' AND WRK_SEQ_NR = ? AND PERF_HDR_ID = ? AND APM_WRK_PERF_ID = ?)  GROUP BY perf_hdr_id,wrk_seq_Nr,wrk_id) > 1 ";
	" SELECT 'Y'"   +
"   FROM DUAL"   +
"  WHERE ("   +
" SELECT SUM(NUM) DUP FROM ("   +
" SELECT   COUNT (*) NUM"   +
"  FROM APM_WRK_PERF"   +
" WHERE (del_fl <> 'Y' OR del_fl IS NULL)"   +
"   AND UPPER (curr_ver_flag) = 'Y'"   +
"   AND wrk_seq_nr IS NOT NULL"   +
"   AND (SELECT COUNT (*)"   +
" FROM APM_WRK_PERF"   +
"   WHERE (del_fl <> 'Y' OR del_fl IS NULL)"   +
"  AND UPPER (curr_ver_flag) = 'Y'"   +
"  AND wrk_seq_nr = ?"   +	//1 seq_nr
"  AND perf_hdr_id = ?) > 1"   + //2 perf_hdr_id
"   AND wrk_seq_nr = ?"   + // 3 seq_nr
"   AND perf_hdr_id = ?"   + // 4 perf_hdr_id
"   AND (   wrk_id IN "   +
"  (SELECT b.alt_id"   +
"  FROM APM_WRK_PERF a, alt_wrk_id b"   +
"    WHERE (a.del_fl <> 'Y' OR a.del_fl IS NULL)"   +
"   AND b.del_fl = 'N'"   +
"   AND a.wrk_id = b.wrk_id"   +
"   AND b.alt_wrk_id_typ_cde = 'TRFID'"   +
"   AND UPPER (curr_ver_flag) = 'Y'"   +
"   AND wrk_seq_nr = ?"   + //5 seq_nr
"   AND perf_hdr_id = ?"   + //6 perf_hdr_id
"   AND APM_WRK_PERF_ID = ?)"   + // 7 APM_WRK_PERF_ID
"  OR wrk_id IN "   +
"  (SELECT wrk_id"   +
"  FROM APM_WRK_PERF"   +
"    WHERE (del_fl <> 'Y' OR del_fl IS NULL)"   +
"   AND UPPER (curr_ver_flag) = 'Y'"   +
"   AND wrk_seq_nr = ?"   + //8 seq_nr
"   AND perf_hdr_id = ?"   + //9 perf_hdr_id
"   AND APM_WRK_PERF_ID = ?)"   + //10 APM_WRK_PERF_ID
" )"   +
" GROUP BY perf_hdr_id, wrk_seq_nr, wrk_id	)	) > 1"  ;



//----------
//SELECT perf_hdr_id,wrk_seq_Nr,wrk_id,COUNT(*) FROM wrk_perf WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND UPPER(CURR_VER_FLAG) ='Y'
//AND wrk_seq_nr IS NOT NULL AND (SELECT COUNT(*) FROM wrk_perf WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND UPPER(CURR_VER_FLAG) ='Y' AND WRK_SEQ_NR = 1 AND PERF_HDR_ID = 241) > 1
//AND WRK_SEQ_NR = 1 AND PERF_HDR_ID = 241 AND wrk_id = (SELECT wrk_id FROM wrk_perf WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND UPPER(CURR_VER_FLAG) ='Y' AND WRK_SEQ_NR = 1 AND PERF_HDR_ID = 241 AND APM_WRK_PERF_ID = 1118)
//GROUP BY perf_hdr_id,wrk_seq_Nr,wrk_id
//
//SELECT 'Y' FROM DUAL WHERE (SELECT COUNT(*) FROM wrk_perf WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND UPPER(CURR_VER_FLAG) ='Y'
//AND wrk_seq_nr IS NOT NULL AND (SELECT COUNT(*) FROM wrk_perf WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND UPPER(CURR_VER_FLAG) ='Y' AND WRK_SEQ_NR = 1 AND PERF_HDR_ID = 401) > 1
//AND WRK_SEQ_NR = 1 AND PERF_HDR_ID = 401
//GROUP BY perf_hdr_id,wrk_seq_Nr,wrk_id) > 1

//--------

//SELECT 'Y' FROM DUAL WHERE EXISTS (
//SELECT * FROM MUS_USER_TYP_WGT WHERE MUS_USER_TYP_WGT_TYP_CDE = 'T'
//AND PER_SET_CDE = 'P' AND MUS_USER_TYP_CDE = 'GN_LI' AND
//((TO_DATE('1/1/1972','MM/DD/YYYY') >= STT_DT AND
//TO_DATE('1/1/1972','MM/DD/YYYY') >= END_DT AND
//) OR
//(TO_DATE('1/1/1972','MM/DD/YYYY') >= STT_DT AND
//TO_DATE('1/1/1972','MM/DD/YYYY') >= END_DT AND
//)) AND
//(
//	(
//	TO_DATE(NVL('10:0:0','0:0:0'), 'HH24:MI:SS') >=
//	TO_DATE(NVL(TO_CHAR(STT_TM,'HH24:MI:SS'),'0:0:0'), 'HH24:MI:SS') AND
//	TO_DATE(NVL('10:0:0','0:0:0'), 'HH24:MI:SS') <=
//	TO_DATE(NVL(TO_CHAR(END_TM,'HH24:MI:SS'),'0:0:0'), 'HH24:MI:SS')
//	)
//OR
//	(
//	TO_DATE(NVL('10:10:0','0:0:0'), 'HH24:MI:SS') >=
//	TO_DATE(NVL(TO_CHAR(STT_TM,'HH24:MI:SS'),'0:0:0'), 'HH24:MI:SS') AND
//	TO_DATE(NVL('10:10:0','0:0:0'), 'HH24:MI:SS') <=
//	TO_DATE(NVL(TO_CHAR(END_TM,'HH24:MI:SS'),'0:0:0'), 'HH24:MI:SS')
//	)
//)
//)

	//MUS_USER_TYP_WGT_TYP_CDE is 'T' in Distribution



// SELECT 'Y' FROM DUAL WHERE EXISTS (
//select * from mus_user_typ_wgt where  MUS_USER_TYP_WGT_TYP_CDE = 'T'
// AND PER_SET_CDE = 'P' AND MUS_USER_TYP_CDE = 'TV-NE' and
// (
// (
//   (
// (
//  TO_DATE('01/01/1999','MM/DD/YYYY') >= TO_DATE(TO_CHAR(STT_DT,'MM/DD/YYYY'),'MM/DD/YYYY')
//   OR
//  TO_DATE('01/01/1999','MM/DD/YYYY') <= TO_DATE(TO_CHAR(END_DT,'MM/DD/YYYY'),'MM/DD/YYYY')
//  )
//  and
//  (
// TO_DATE('01/01/1999','MM/DD/YYYY') <= TO_DATE(TO_CHAR(END_DT,'MM/DD/YYYY'),'MM/DD/YYYY')
//  )
//   )
// AND
//  (
// (
// TO_DATE('01/01/1999','MM/DD/YYYY') >= TO_DATE(TO_CHAR(STT_DT,'MM/DD/YYYY'),'MM/DD/YYYY')
//   OR
//	 TO_DATE('01/01/1999','MM/DD/YYYY') <= TO_DATE(TO_CHAR(END_DT,'MM/DD/YYYY'),'MM/DD/YYYY')
// )
//   AND
//  (
// TO_DATE('01/01/1999','MM/DD/YYYY') >= TO_DATE(TO_CHAR(STT_DT,'MM/DD/YYYY'),'MM/DD/YYYY')
// )
//  )
// )
//)
//and
// (
// (
//   (
// (
//  TO_DATE(TO_CHAR(STT_TM,'MM/DD/YYYY ') || '00:00:00','MM/DD/YYYY HH24:MI:SS') >= TO_DATE(TO_CHAR(STT_TM,'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')
//   OR
//  TO_DATE(TO_CHAR(STT_TM,'MM/DD/YYYY ') || '00:00:00','MM/DD/YYYY HH24:MI:SS')  <= TO_DATE(TO_CHAR(STT_TM,'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')
//  )
//  and
//  (
// TO_DATE(TO_CHAR(END_TM,'MM/DD/YYYY ') || '00:00:00','MM/DD/YYYY HH24:MI:SS')  <= TO_DATE(TO_CHAR(END_TM,'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')
//  )
//   )
// AND
//  (
// (
// TO_DATE(TO_CHAR(STT_TM,'MM/DD/YYYY ') || '23:10:00','MM/DD/YYYY HH24:MI:SS')  >= TO_DATE(TO_CHAR(STT_TM,'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')
//   OR
//	 TO_DATE(TO_CHAR(END_TM,'MM/DD/YYYY ') || '23:10:00','MM/DD/YYYY HH24:MI:SS')  <= TO_DATE(TO_CHAR(END_TM,'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')
// )
//   AND
//  (
// TO_DATE(TO_CHAR(STT_TM,'MM/DD/YYYY ') || '23:10:00','MM/DD/YYYY HH24:MI:SS')  >= TO_DATE(TO_CHAR(STT_TM,'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')
// )
//  )
// )
//)
//)


public static final String GET_IF_PERFORMED_IN_PRIME_TIME =
  " SELECT 'Y' FROM DUAL WHERE EXISTS ( "
+ "	SELECT 'Y' "
+ "	  FROM mus_user_typ_wgt a "
+ "	 WHERE del_fl = 'N' "
+ "	   AND mus_user_typ_wgt_typ_cde = 'T' "
+ "	   AND per_set_cde = '" + DistributionConstants.PERSET_CODE_PRIMETIME + "' "
+ "	   AND mus_user_typ_cde = ? "
+ "	   AND TO_DATE (?, 'MM/DD/YYYY') BETWEEN stt_dt AND NVL (end_dt, TO_DATE ('" + MAXIMUM_POSSIBLE_DATE_IN_DB + "', 'MM/DD/YYYY')) "
+ "	   AND TO_DATE (?, 'mm/dd/yyyy hh24:mi:ss') BETWEEN TO_DATE (   TO_CHAR (TO_DATE (? , 'mm/dd/yyyy') + (TO_DATE (TO_CHAR (stt_tm, 'mm/dd/yyyy'), 'mm/dd/yyyy') - TO_DATE ('1/1/1900', 'mm/dd/yyyy')), 'mm/dd/yyyy ') "
+ "	       || TO_CHAR (stt_tm, 'hh24:mi:ss'), "
+ "	       'mm/dd/yyyy hh24:mi:ss' "
+ "	      ) "
+ "	      AND TO_DATE (   TO_CHAR (TO_DATE (?, 'mm/dd/yyyy') + (TO_DATE (TO_CHAR (end_tm, 'mm/dd/yyyy'), 'mm/dd/yyyy') - TO_DATE ('1/1/1900', 'mm/dd/yyyy')), 'mm/dd/yyyy ') "
+ "	       || TO_CHAR (end_tm, 'hh24:mi:ss'), "
+ "	       'mm/dd/yyyy hh24:mi:ss' "
+ "	      ) "
+ " ) ";




	public static final String GET_IF_FEATURED_WORK_PERFORMANCES_OF_WORK_IN_PERF_HDR = " SELECT 'Y' FROM DUAL "
+  " WHERE "
+  "   (SELECT COUNT(*) FROM WRK_PERF WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND"
+  "   UPPER(CURR_VER_FLAG) ='Y' AND"
+  "   PERF_HDR_ID = ? AND WRK_ID = ? AND APM_WRK_PERF_ID <> ? and "
+  "   USE_TYP_CDE = '"+  UseTypeConstants.FEATURE + "') >= 1 ";

	public static final String GET_IF_VALID_SOC_CODE =	
	"SELECT 'Y' FROM DUAL " +
	"WHERE  " +
	"( " +
	" SELECT count(*) FROM SOC WHERE TO_NUMBER(SOC_CDE) = TO_NUMBER(?) AND DEL_FL = 'N' " +
	") = 1 ";
	
	public static final String GET_IF_VALID_INTERNATIONAL_REVENUE_CLASS =	
	"SELECT 'Y' FROM DUAL WHERE ( " +
	" SELECT COUNT(*) FROM INTL_REV_CL WHERE TRIM(UPPER(ITNL_REV_CL_CDE)) = 2 AND DEL_FL = 'N' " +
	") = 1 ";
	
	
	
//This is Similar to the Above one but the Clarity sake making them as two methods
//Just Getting Only One Work Perf Id thats enough since Adjustments Module will load all the other ones left
	public static final String GET_WRK_PERFIDS_OF_SAME_USE_TYPE_OF_WORK_IN_PERF_HDR = " SELECT WRKPERF.PERF_HDR_ID,"
+  "   WRKPERF.APM_WRK_PERF_ID,"
+  "   WRKPERF.VER_NUM,"
+  "   WRKPERF.CURR_VER_FLAG,"
+  "   WRKPERF.LGY_APM_WRK_PERF_ID,"
+  "   WRKPERF.DIS_ID,"
+  "   WRKPERF.WRK_SEQ_NR,"
+  "   WRKPERF.CUE_SHT_SEQ_NUM,"
+  "   WRKPERF.MEDL_SEQ,"
+  "   WRKPERF.WRK_TTL,"
+  "   WRKPERF.WRK_ID,"
+  "   WRKPERF.USE_TYP_CDE,"
+  "   USETYPE.DES,"
+  "   WRKPERF.DUR "
+   " FROM WRK_PERF WRKPERF, USE_TYP USETYPE WHERE WRKPERF.USE_TYP_CDE = USETYPE.USE_TYP_CDE AND WRKPERF.DEL_FL = 'N' AND"
+  "   UPPER(CURR_VER_FLAG) ='Y' AND"
+  "   PERF_HDR_ID = ? AND WRK_ID = ? AND APM_WRK_PERF_ID <> ? and "
+  "   WRKPERF.USE_TYP_CDE = ? AND ROWNUM < 2";


/*
	public static final String GET_MATCHING_PERFORMANCES_WITH_NULL_PGM_OVRLP_CDE =
" SELECT "
+  "   COUNT(*) TOTALRECORDCOUNT"
+  " FROM "
+  "   PERF_HDR"
+  " WHERE "
+  "   PTY_ID = ? AND"
//+  "   TO_DATE(NVL(TO_CHAR(PGM_STT_DT,'MM/DD/YYYY'),'1/1/1800') || ' ' || NVL(TO_CHAR(PGM_STT_TM,'HH24:MI:SS'),'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') = TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') AND"
//+  "   TO_DATE(NVL(TO_CHAR(PGM_END_DT,'MM/DD/YYYY'),'1/1/1800') || ' ' || NVL(TO_CHAR(PGM_END_TM,'HH24:MI:SS'),'00:00:00'), 'MM/DD/YYYY HH24:MI:SS')  = TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') AND"
+  "   PGM_STT_TM = TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') AND"
//+  "   PGM_END_TM = TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') AND"
+  "   PGM_STT_DT = TO_DATE(?,'MM/DD/YYYY') AND"
+  "   (PERF_HDR.DEL_FL <> 'Y' OR PERF_HDR.DEL_FL IS NULL) AND"
+  "   CURR_VER_FLAG = 'Y' AND"
+  "   PERF_HDR_ID <> NVL(?, '999999999999999999999999999999') "
+  "  AND PGM_OVRLP_CDE IS NULL ";


	public static final String GET_MATCHING_PERFORMANCES_WITH_NOT_NULL_PGM_OVRLP_CDE =
" SELECT "
+  "   COUNT(*) TOTALRECORDCOUNT"
+  " FROM "
+  "   PERF_HDR"
+  " WHERE "
+  "   PTY_ID = ? AND"
+  "   PGM_STT_TM = TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') AND"
//+  "   PGM_END_TM = TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') AND"
+  "   PGM_STT_DT = TO_DATE(?,'MM/DD/YYYY') AND"
+  "   (PERF_HDR.DEL_FL <> 'Y' OR PERF_HDR.DEL_FL IS NULL) AND"
+  "   CURR_VER_FLAG = 'Y' AND"
+  "   PERF_HDR_ID <> NVL(?, '999999999999999999999999999999') "
+  "  AND PGM_OVRLP_CDE IS NOT NULL ";
*/


//	public static final String GET_MATCHING_SURVEY_DATES = "SELECT COUNT(*) TOTALRECORDCOUNT  FROM SUR_DT WHERE "
//+ " (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND "
//+ " SUR_DT_TYP_CDE = ? AND"
//+ " TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') >= "
//+ " TO_DATE(TO_CHAR(STT_DT,'MM/DD/YYYY') || ' ' || NVL(TO_CHAR(STT_DT, 'HH24:MI:SS'), '00:00:00'), 'MM/DD/YYYY HH24:MI:SS') "
//+ " AND "
//+ " TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') <= "
//+ "  TO_DATE(TO_CHAR(END_DT,'MM/DD/YYYY') || ' ' || NVL(TO_CHAR(END_DT, 'HH24:MI:SS'), '23:59:59'), 'MM/DD/YYYY HH24:MI:SS') AND SUR_DT_ID <> NVL(?,'999999999999999999999999999999') ";


//	public static final String GET_MATCHING_SURVEY_DATES = "SELECT COUNT(*) TOTALRECORDCOUNT  FROM SUR_DT WHERE "
//+ " (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND "
//+ " SUR_DT_TYP_CDE = ? AND"
//+ " TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') >= "
//+ " STT_DT "
//+ " AND "
//+ " TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') <= "
//+ " END_DT ";

	public static final String GET_MATCHING_SURVEY_DATES = "SELECT COUNT(*) TOTALRECORDCOUNT  FROM SUR_DT WHERE "
+ " (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND "
+ " SUR_DT_TYP_CDE = ? AND"
+ " TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') BETWEEN STT_DT and END_DT ";


	public static final String GET_DUPLICATE_SURVEY_DATES = "SELECT COUNT(*) TOTALRECORDCOUNT  FROM SUR_DT WHERE "
+ " (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND "
+ " SUR_DT_TYP_CDE = ? "
+ "	AND	  (STT_DT BETWEEN TO_DATE(?,'MM/DD/YYYY  HH24:MI:SS') AND TO_DATE(?,'MM/DD/YYYY  HH24:MI:SS')  " 
+ "			OR 	  END_DT BETWEEN TO_DATE(?,'MM/DD/YYYY  HH24:MI:SS') AND TO_DATE(?,'MM/DD/YYYY  HH24:MI:SS')  " 
+ "		   	OR 	  TO_DATE(?,'MM/DD/YYYY  HH24:MI:SS') BETWEEN STT_DT AND END_DT  " 
+ "		   	OR 	  TO_DATE(?,'MM/DD/YYYY  HH24:MI:SS') BETWEEN STT_DT AND END_DT)  " 
+ " AND SUR_DT_ID <> NVL(?,'999999999999999999999999999999') ";

	public static final String GET_MATCHING_SAMPLE_DATES = "SELECT COUNT(*) TOTALRECORDCOUNT  FROM SAM_SUR_DT WHERE "
+ " (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND "
+ " MUS_USR_ID = ? AND"
+ " TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') BETWEEN STT_DT AND END_DT ";
//+ " TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') >= "
//+ " STT_DT "
//+ " AND "
//+ " TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') <= "
//+ " END_DT ";

	public static final String GET_DUPLICATE_SAMPLE_DATES = "SELECT COUNT(*) TOTALRECORDCOUNT  FROM SAM_SUR_DT WHERE "
+ " (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND "
+ " MUS_USR_ID = ? AND"
+ " TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') >= "
+ " STT_DT "
+ " AND "
+ " TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') <= "
+ " END_DT  AND SAM_SUR_DT_ID <> NVL(?,'999999999999999999999999999999') ";

public static final String GET_IS_SUB_SAMPLED = "SELECT COUNT(*) TOTALRECORDCOUNT  FROM USE_SUB_SAM_PAR WHERE "
+ "  (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND "
+ "  MUS_USER_TYP_CDE = ? AND "
+ "  USE_TYP_CDE = ? AND "
+ "  TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') BETWEEN STT_DT AND END_DT ";
//+ "  TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') >= "
//+ "  STT_DT "
//+ "  AND "
//+ "  TO_DATE(? || ' ' || NVL(?,'00:00:00'), 'MM/DD/YYYY HH24:MI:SS') <= "
//+ "  END_DT ";

public static final String GET_IF_VALID_MUSIC_USER_PARTY =
	
	"SELECT 'Y'  FROM DUAL " +
	" WHERE (SELECT COUNT (*) " +
	"          FROM MVW_PTY_NA " +
	"         WHERE PTY_ID = ? AND ROWNUM < 2) = 1 ";
/*
	"SELECT 'Y' FROM DUAL  " +
" WHERE   " +
" (  " +
" SELECT COUNT(*) FROM PTY WHERE PTY_ID = ? AND PTY_TYP_CDE = '"+ PartyTypeConstants.MUSIC_USER +"' AND DEL_FL = 'N'  " +
" ) = 1  ";
*/

public static final String GET_MUSIC_USER_TYPE = 
	/*"SELECT A.PTY_ID, A.MUS_USER_TYP_SEL_ID, A.MUS_USER_TYP_CDE, B.MUS_USER_TYP_DES"
	+ " FROM MUS_USER_TYP_SEL A, MUS_USER_TYP B WHERE"
	+ " A.MUS_USER_TYP_CDE = B.MUS_USER_TYP_CDE(+) AND"
	+ " (UPPER(A.DEL_FL) <> 'Y' OR A.DEL_FL IS NULL) AND"
	+ " (UPPER(B.DEL_FL) <> 'Y' OR B.DEL_FL IS NULL) AND"
	+ " A.PTY_ID = ? AND"
	+ " TO_DATE(? , 'MM/DD/YYYY') BETWEEN A.STT_DT AND A.END_DT";*/

 " SELECT A.PTY_ID, A.MUS_USER_TYP_CDE, B.MUS_USER_TYP_DES "
+ "  FROM MVW_MUS_USER_TYP_SEL A, MVW_MUS_USER_TYP B "
+	 " WHERE     A.MUS_USER_TYP_CDE = B.MUS_USER_TYP_CDE(+) "
+	"       AND A.PTY_ID = ? "
+	"       AND TO_DATE (?, 'YYYY/MM/DD') BETWEEN A.STT_DT AND A.END_DT ";



	public static final String GET_MUSIC_USER_LICENSE = /*"SELECT A.PTY_ID, A.MUS_USER_LIC_ID, A.LIC_TYP_CDE, B.NA FROM MUS_USER_LIC A, LIC_TYP B WHERE"
	+ " A.LIC_TYP_CDE = B.LIC_TYP_CDE(+) AND"
	+ " (UPPER(A.DEL_FL) <> 'Y' OR A.DEL_FL IS NULL) AND"
	+ " (UPPER(B.DEL_FL) <> 'Y' OR B.DEL_FL IS NULL) AND"
	+ " A.PTY_ID = ? AND"
	+ " TO_DATE(? , 'MM/DD/YYYY') BETWEEN A.EFF_DT AND A.END_DT";*/		
//	+ " TO_DATE(? , 'MM/DD/YYYY') >= A.EFF_DT AND"
//	+ " TO_DATE(? , 'MM/DD/YYYY') <= A.END_DT ";
		
		"SELECT A.PTY_ID, A.LIC_TYP_CDE, B.NA " +
		"  FROM MVW_MUS_USER_LIC A, MVW_LIC_TYP B " +
		" WHERE     A.LIC_TYP_CDE = B.LIC_TYP_CDE(+) " +
		"       AND A.PTY_ID = ? " +
		"       AND TO_DATE (?, 'YYYY/MM/DD') BETWEEN A.EFF_DT AND A.END_DT "; 

	
	
	public static final String GET_MUSIC_USER_CALL_LETTER =/* " SELECT (na || CAL_LTR_SUF ) AS CALL_LETTER_N_SUFF, NA CALL_LETTER_ONLY, CAL_LTR_SUF CALL_LETTER_SUFF_ONLY " +	
"   FROM pty_na"   +
"  WHERE pty_id = ?"   +
" AND pty_na_typ_cde = '" + PartyNameTypeConstants.CALL_LETTERS + "'"   +
" AND TO_DATE(? , 'MM/DD/YYYY') BETWEEN STT_DT AND END_DT " +
//" AND TO_DATE (?, 'MM/DD/YYYY') >= stt_dt"   +
//" AND TO_DATE (?, 'MM/DD/YYYY') <= end_dt"   +
" AND del_fl = 'N'"  ;*/
		
		"SELECT (na || CAL_LTR_SUF) AS CALL_LETTER_N_SUFF, " +
		"       NA CALL_LETTER_ONLY, " +
		"       CAL_LTR_SUF CALL_LETTER_SUFF_ONLY " +
		"  FROM MVW_pty_na " +
		" WHERE pty_id = ? " +
		"       AND TO_DATE (? , 'YYYY/MM/DD') BETWEEN STT_DT AND END_DT "; 


	//Based on Defect 1029 @PREP_AT
	//public static final String GET_MUSIC_USER_FULL_NAME	= "select pty_na.na, pty_na.fst_na, trim(pty_na.SAL || ' ' || pty_na.NA || DECODE(pty_na.FST_NA, NULL, '', ', ' || pty_na.FST_NA ) || DECODE(pty_na.MID_INIT, NULL, '', ' ' || pty_na.MID_INIT ) || ' ' || nvl(pty_na.SUF_OTH,pty_na.SUF)) FULL_DISPLAY_NAME from pty_na where pty_na.pty_id = ?  and pty_na.PTY_NA_TYP_CDE = '" + PartyNameTypeConstants.PATRONYM + "' and pty_na.del_fl = 'N' ";
	  public static final String GET_MUSIC_USER_FULL_NAME	= /*"select pty_na.na, pty_na.fst_na, trim(                     pty_na.NA || DECODE(pty_na.FST_NA, NULL, '', ', ' || pty_na.FST_NA ) || DECODE(pty_na.MID_INIT, NULL, '', ' ' || pty_na.MID_INIT ) || ' ' || nvl(pty_na.SUF_OTH,pty_na.SUF)) FULL_DISPLAY_NAME from pty_na where pty_na.pty_id = ?  and pty_na.PTY_NA_TYP_CDE = '" + PartyNameTypeConstants.PATRONYM + "' and pty_na.del_fl = 'N' "*/
		  "SELECT MVW_PTY_NA.NA, TRIM (NA || CAL_LTR_SUF) FULL_DISPLAY_NAME " +
		  "  FROM MVW_PTY_NA " +
		  " WHERE MVW_PTY_NA.PTY_ID = ? ";	
		
		
//------------------------------------------
//SELECT
//COUNT(*)
//A.MUS_USER_SPCF_WGT_TYP_CDE,B.MLT
//FROM
//MUS_USER_WGT_RULE A
//,
//MUS_USER_SPCF_WGT B
//WHERE
// (A.DEL_FL <> 'Y' OR A.DEL_FL IS NULL) AND
// A.MUS_USER_TYP_CDE = 'RD-CO' AND
// A.SUR_TYP_CDE = 'C' AND
// NVL(A.SAM_TYP_CDE,'XXXXXXXXXXXXXXXXXXXX') = NVL(NULL,'XXXXXXXXXXXXXXXXXXXX') AND
// TO_DATE('1/1/2001' , 'MM/DD/YYYY') >=
// A.STT_DT
// AND
// TO_DATE('1/1/2001' , 'MM/DD/YYYY') <=
// A.END_DT
// AND
// A.MUS_USER_SPCF_WGT_TYP_CDE = B.MUS_USER_SPCF_WGT_TYP_CDE AND
// TO_DATE('1/1/2001' , 'MM/DD/YYYY') >=
// B.STT_DT
// AND
// TO_DATE('1/1/2001' , 'MM/DD/YYYY') <=
// B.END_DT
// AND B.PTY_ID = 366
// AND B.MLT IS NOT NULL

public static final String GET_MATCHING_MUSIC_USER_WEIGHTS_COUNT_COMMON_PART = 
	"SELECT COUNT(1) TOTALRECORDS " +
	"FROM MUS_USER_SPCF_WGT A, MUS_USER_WGT_RULE B " +
	"WHERE A.MUS_USER_SPCF_WGT_TYP_CDE = B.MUS_USER_SPCF_WGT_TYP_CDE AND A.MLT IS NOT NULL " +
	"    AND B.MUS_USER_TYP_CDE = ? " +
	"    AND B.SUR_TYP_CDE = ? " +
	"    AND NVL(B.SAM_TYP_CDE,'XXXXXXXXXXXXXXXXXXXX') = NVL(?,'XXXXXXXXXXXXXXXXXXXX') " +
	"    AND TO_DATE(? , 'MM/DD/YYYY')  BETWEEN TRUNC(A.STT_DT) AND TRUNC(A.END_DT) " +
	"    AND TO_DATE(? , 'MM/DD/YYYY')  BETWEEN TRUNC(B.STT_DT) AND TRUNC(B.END_DT) " +
	"    AND A.PTY_ID = ? " +
	"    AND A.DEL_FL = 'N' " +
	"    AND B.DEL_FL = 'N' " ;
public static final String GET_MATCHING_MUSIC_USER_WEIGHTS_COUNT_NON_HOOKUP =	
	"    AND A.MUS_USER_SPCF_WGT_TYP_CDE NOT IN ('"+ DistributionConstants.SPCF_WGT_PROGRAM + "','"+ DistributionConstants.SPCF_WGT_HOOKUP + "') ";
public static final String GET_MATCHING_MUSIC_USER_WEIGHTS_COUNT_HOOKUP =
	"    AND A.MUS_USER_SPCF_WGT_TYP_CDE = '"+ DistributionConstants.SPCF_WGT_HOOKUP + "' " +
	"    AND ? BETWEEN LOW_VAL AND HIGH_VAL ";


public static final String GET_HAS_NULL_SAMPLE_MUSIC_USER_WEIGHT_RULE = " SELECT"
 +   " COUNT(*) TOTALRECORDS  "
 +   " FROM "
 +   " MUS_USER_WGT_RULE A"
 +   " WHERE"
 +   "  A.DEL_FL = 'N' AND"
 +   "  A.MUS_USER_TYP_CDE = ? AND"
 +   "  A.SUR_TYP_CDE = ? AND"
 +   "  A.SAM_TYP_CDE IS NULL AND"
 +   "  TO_DATE(? , 'MM/DD/YYYY') BETWEEN A.STT_DT AND A.END_DT " ;
	public static final String GET_SURVEY_DATES_LIST_COUNT = "SELECT COUNT(*) TOTALCOUNT FROM SUR_DT WHERE DEL_FL ='" + DELETE_FLAG_NO + "' AND SUR_DT_TYP_CDE = ? ";
	
	public static final String GET_SURVEY_DATES_LIST_PART1 = "SELECT SUR_DT_ID, SUR_DT_TYP_CDE, SUR_STT_DT_PART, SUR_STT_TM_PART, SUR_END_DT_PART, SUR_END_TM_PART FROM "
	+ " ("
	+ " SELECT DENSE_RANK() OVER (ORDER BY STT_DT DESC, SUR_DT_ID) RN, SUR_DT_ID, SUR_DT_TYP_CDE, TO_CHAR(STT_DT,'MM/DD/YYYY') SUR_STT_DT_PART,TO_CHAR(STT_DT,'HH24:MI:SS') SUR_STT_TM_PART, TO_CHAR(END_DT,'MM/DD/YYYY') SUR_END_DT_PART,TO_CHAR(END_DT,'HH24:MI:SS') SUR_END_TM_PART FROM SUR_DT WHERE DEL_FL = 'N' AND SUR_DT_TYP_CDE = ? ";
	
	public static final String GET_SURVEY_DATES_LIST_PART2 = " ) WHERE RN BETWEEN ? AND ? ";

	public static final String ADD_SURVEY_DATE = "INSERT INTO SUR_DT(SUR_DT_ID, SUR_DT_TYP_CDE, STT_DT, END_DT, DEL_FL, CRE_ID, CRE_DT) VALUES( ?, ?, TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS'), TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS'),'" + DELETE_FLAG_NO + "', ?, SYSDATE)";
	public static final String STATIC_DELETE_SURVEY_DATES = "UPDATE SUR_DT SET DEL_FL = '" + DELETE_FLAG_YES + "', UPD_DT = SYSDATE, UPD_ID = ? WHERE SUR_DT_ID IN ? ";
	public static final String GET_SURVEY_DATE = "SELECT SUR_DT_ID, SUR_DT_TYP_CDE, TO_CHAR(STT_DT,'MM/DD/YYYY') SUR_STT_DT_PART,TO_CHAR(STT_DT,'HH24:MI:SS') SUR_STT_TM_PART, TO_CHAR(END_DT,'MM/DD/YYYY') SUR_END_DT_PART,TO_CHAR(END_DT,'HH24:MI:SS') SUR_END_TM_PART FROM SUR_DT WHERE (DEL_FL IS NULL OR DEL_FL <> '" + DELETE_FLAG_YES + "') AND SUR_DT_ID = ? ";

	public static final String GET_SRE_SMC_POINT_ALLOCATION_HISTORY_COUNT = "SELECT COUNT(*) TOTALRECCOUNT FROM (SELECT DISTINCT a.wrk_id, a.smc_cat_cde, a.smc_subcat_cde, MAX (a.dur) max_dur, a.point_value " +
	"                    FROM smc_asgn_hist a " +
	"                   WHERE a.wrk_id = ? AND del_fl = 'N' and DFLT_IND = 'N' and a.point_value is not null " +
	"                GROUP BY a.wrk_id, a.smc_cat_cde, a.smc_subcat_cde, a.point_value " + ") ";
	public static final String GET_SRE_SMC_POINT_ALLOCATION_HISTORY = 
	"SELECT wrk_id, smc_cat_cde, smc_subcat_cde, max_dur, point_value, (smc_cat_cde || smc_subcat_cde || '-' || point_value) disp_point " +
	"  FROM (SELECT ROWNUM rn, wrk_id, smc_cat_cde, smc_subcat_cde, max_dur, point_value " +
	"          FROM (SELECT DISTINCT a.wrk_id, a.smc_cat_cde, a.smc_subcat_cde, MAX (a.dur) max_dur, a.point_value " +
	"                    FROM smc_asgn_hist a " +
	"                   WHERE a.wrk_id = ? AND del_fl = 'N' and DFLT_IND = 'N' and a.point_value is not null " +
	"                GROUP BY a.wrk_id, a.smc_cat_cde, a.smc_subcat_cde, a.point_value " +
	"                ORDER BY a.wrk_id, a.smc_cat_cde, a.smc_subcat_cde NULLS FIRST, a.point_value, MAX (a.dur))) " +
	" WHERE rn BETWEEN ? AND ? ";

	public static final String GET_SRE_SMC_POINT_ALLOCATION_STORED_COUNT = "SELECT COUNT(*) TOTALRECCOUNT FROM SMC_ASGN_HIST WHERE del_fl ='N' and WRK_ID = ? AND UPPER(SMC_CAT_CDE) = UPPER(?) AND UPPER(nvl(SMC_SUBCAT_CDE,-999)) = UPPER(nvl(?,-999)) AND POINT_VALUE = ? and DFLT_IND = 'N' "; //AND APM_WRK_PERF_ID IS NOT NULL ";

	//public static final String ADD_SRE_SMC_POINT_ALLOCATION_HISTORY = "INSERT INTO SMC_ASGN_HIST (SMC_ASGN_HIST_ID, PERF_HDR_ID, APM_WRK_PERF_ID, WRK_ID, PTY_ID, ASGN_DT, SMC_CAT_CDE, SMC_SUBCAT_CDE, DUR, SMC_DUR_CDE, POINT_VALUE, NUM_EXCERPTS_ACTS, NUM_PLAYS, INSTN, STT_DT, END_DT) VALUES( ? , ? , ? , ? , ? , sysdate , ? , ? , ? , ? , ? , ? , ? ,? , TO_DATE(? , 'MM/DD/YYYY HH24:MI:SS') , TO_DATE(? , 'MM/DD/YYYY HH24:MI:SS'))";
	public static final String ADD_SRE_SMC_POINT_ALLOCATION_HISTORY = "INSERT INTO SMC_ASGN_HIST (SMC_ASGN_HIST_ID, WRK_ID, SMC_CAT_CDE, SMC_SUBCAT_CDE, DUR, SMC_DUR_CDE, POINT_VALUE, DFLT_IND, DEL_FL, CRE_ID, CRE_DT) " +
			                                                                             "  SELECT ? , ? , ? , ? , ? , ? , ? , ? , 'N' , ? , sysdate FROM DUAL WHERE NOT EXISTS " +
			                                                                             "( " +
																						 " SELECT 1 " +
																						 "  FROM smc_asgn_hist " +
																						 " WHERE del_fl = 'N' AND wrk_id = ? AND UPPER (smc_cat_cde) = UPPER (?) AND UPPER (NVL (smc_subcat_cde, -999)) = UPPER (NVL (?, -999)) AND point_value = ? and DFLT_IND = ? " +
																						 ") ";
	
	public static final String GET_SRE_SMC_REFERENCE_POINT_VALUE_WITH_DURATION = "SELECT SRE_CAT_CDE, SRE_SUBCAT_CDE, SRE_DUR_CDE, PT_VAL FROM SRE_PT_REF WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND UPPER(SRE_CAT_CDE) = UPPER(?) AND UPPER(nvl(SRE_SUBCAT_CDE,-999)) = UPPER(nvl(?,-999)) AND SRE_DUR_CDE = ( SELECT SRE_DUR_CDE FROM SRE_DUR_RNG WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND ? >=  SRE_MIN_DUR AND ? <= SRE_MAX_DUR)";
	//(DEL_FL <> 'Y' OR DEL_FL IS NULL) AND
	public static final String GET_SRE_SMC_DEFAULT_POINT_VALUE = "SELECT SMC_ASGN_HIST_ID, WRK_ID, SMC_CAT_CDE, SMC_SUBCAT_CDE, DUR, SMC_DUR_CDE, POINT_VALUE FROM SMC_ASGN_HIST WHERE WRK_ID = ? and DFLT_IND = 'Y' and del_fl ='N' ";

//	public static final String ADD_LEGACY_SOURCE_BATCH = "INSERT INTO CNTRL(BAT_ID, GRP_0_SUM, SRC_SYS, TGT_SYS, PRC_TYP_CDE, BAT_STAT_CDE, ACTN_CDE, CRE_ID, CRE_DT) VALUES(?, ?, ?, ?, ?, ?, ?, ?,SYSDATE)";
	public static final String ADD_LEGACY_UNPOST_DETAIL = "INSERT INTO UNPOST(BAT_ID, LGY_PERF_HDR_ID, PREP_PERF_HDR_ID, ACTN_CD, USER_ID, UNPOST_DT, CRE_ID, CRE_DT, DEL_FL) VALUES( ?, ?, ?, ?, ?, SYSDATE, ?, SYSDATE, 'N')";

//	public static final String UPDATE_LEGACY_SOURCE_BATCH_STATUS = "UPDATE CNTRL SET BAT_STAT_CDE = ?, UPD_ID =?, UPD_DT = SYSDATE WHERE BAT_ID = ? ";

	public static final String ADD_PROGRAM_PERFORMANCE_MESSAGE = "INSERT INTO PERF_HDR_WRN (PERF_HDR_ID, PRG_PERF_VER_NUM, USE_EWI_CDE, CRE_ID, CRE_DT, DEL_FL) VALUES(?, ?, ?, ?,SYSDATE,'N')";
	public static final String ADD_WORK_PERFORMANCE_MESSAGE = "INSERT INTO WRK_PERF_WRN (APM_WRK_PERF_ID, WRK_PERF_VER_NUM, USE_EWI_CDE, CRE_ID, CRE_DT, DEL_FL, PERF_HDR_ID) VALUES(?, ?, ?, ?,SYSDATE,'N',?)";

	public static final String UPDATE_UNDELETE_PROGRAM_PERFORMANCE_MESSAGE = "UPDATE PERF_HDR_WRN SET DEL_FL = 'N', UPD_ID = ?, PRG_PERF_VER_NUM=?, UPD_DT = SYSDATE WHERE PERF_HDR_ID = ? AND PRG_PERF_VER_NUM = ? AND USE_EWI_CDE = ?";
	//public static final String UPDATE_UNDELETE_WORK_PERFORMANCE_MESSAGE = "UPDATE WRK_PERF_WRN SET DEL_FL = 'N', UPD_ID = ?, WRK_PERF_VER_NUM=?, UPD_DT = sysdate, PERF_HDR_ID=? WHERE APM_WRK_PERF_ID =? AND WRK_PERF_VER_NUM = ?  AND USE_EWI_CDE = ?";
	public static final String UPDATE_UNDELETE_WORK_PERFORMANCE_MESSAGE = "UPDATE WRK_PERF_WRN SET DEL_FL = 'N', UPD_ID = ?, WRK_PERF_VER_NUM=?, UPD_DT = sysdate WHERE APM_WRK_PERF_ID =? AND WRK_PERF_VER_NUM = ?  AND USE_EWI_CDE = ?";


//	public static final String DELETE_ALL_PROGRAM_PERFORMANCE_MESSAGES = "UPDATE PERF_HDR_WRN SET DEL_FL = 'Y', UPD_ID = ?, UPD_DT = SYSDATE WHERE PERF_HDR_ID = ? AND PRG_PERF_VER_NUM = ?";
//	public static final String DELETE_ALL_WORK_PERFORMANCE_MESSAGES = "UPDATE WRK_PERF_WRN SET DEL_FL = 'Y', UPD_ID = ?, UPD_DT = sysdate WHERE APM_WRK_PERF_ID =? AND WRK_PERF_VER_NUM = ?";
	
	
	public static final String DELETE_ALL_WORK_PERFORMANCE_MESSAGES_FOR_PERF_HDR = "DELETE FROM APM_PERF_HDR WHERE APM_PERF_HDR_ID =?";
	public static final String DELETE_ALL_WORK_PERFORMANCE_SHARE_MESSAGES_FOR_WRK_PERF = "DELETE FROM WRK_PERF_SHR_WRN WHERE APM_WRK_PERF_ID =?";
	public static final String DELETE_ALL_WORK_PERFORMANCE_SHARE_MESSAGES_FOR_PERF_HDR = "DELETE FROM WRK_PERF_SHR_WRN WHERE PERF_HDR_ID =?";

	public static final String STATIC_DELETE_CURRENT_PROGRAM_PERFORMANCE = "DELETE FROM APM_PERF_HDR WHERE APM_PERF_HDR_ID = ?";
	
	


	
	//public static final String STATIC_DELETE_CURRENT_WORK_PERFORMANCE_ASSIGN_HIST = "UPDATE SMC_ASGN_HIST SET DEL_FL = 'Y', UPD_ID = ? , UPD_DT = SYSDATE WHERE APM_WRK_PERF_ID = ?";

	public static final String STATIC_DELETE_CURRENT_WORK_PERFORMANCES_FOR_PERF_HDR = "UPDATE APM_WRK_PERF SET DEL_FL = '" + DELETE_FLAG_YES + "', UPD_ID = ? , UPD_DT = SYSDATE WHERE APM_PERF_HDR_ID = ?";

	public static final String STATIC_DELETE_WORK_PERFORMANCE_SHARES_FOR_WORK_PERF = "UPDATE WRK_PERF_SHR SET DEL_FL = '" + DELETE_FLAG_YES + "', UPD_ID = ? , UPD_DT = SYSDATE WHERE APM_WRK_PERF_ID = ? AND CURR_VER_FLAG = 'Y'";
	public static final String STATIC_DELETE_WORK_PERFORMANCE_SHARES_FOR_PERF_HDR = "UPDATE WRK_PERF_SHR SET DEL_FL = '" + DELETE_FLAG_YES + "', UPD_ID = ? , UPD_DT = SYSDATE WHERE PERF_HDR_ID = ? AND CURR_VER_FLAG = 'Y'";

	
	//public static final String ADD_PROXY_SELECTION_CRITERIA_BASIC = "INSERT INTO PRX_SEL (PRX_SEL_ID, PRX_SRC_PERF_STT_DT, PRX_SRC_PERF_END_DT, PRX_SRC_PERF_STT_TM, PRX_SRC_PERF_END_TM, PRX_SRC_LIC_TYP_CDE, PRX_TGT_PERF_STT_DT, PRX_TGT_PERF_END_DT, PRX_TGT_PERF_STT_TM, PRX_TGT_PERF_END_TM, PRX_TGT_MUS_USR_ID, PRX_RESTRU_IND, PRX_SRC_DIS_STT_DT, PRX_SRC_DIS_END_DT, STA, CRE_ID, CRE_DT, DEL_FL) VALUES (?, TO_DATE(?,'MM/DD/YYYY HH24:MI:SS'), TO_DATE(?,'MM/DD/YYYY HH24:MI:SS'), TO_DATE(?,'MM/DD/YYYY HH24:MI:SS'), TO_DATE(?,'MM/DD/YYYY HH24:MI:SS'), ?, TO_DATE(?,'MM/DD/YYYY HH24:MI:SS'), TO_DATE(?,'MM/DD/YYYY HH24:MI:SS'), TO_DATE(?,'MM/DD/YYYY HH24:MI:SS'), TO_DATE(?,'MM/DD/YYYY HH24:MI:SS'), ?, ?, TO_DATE(?,'MM/DD/YYYY'), TO_DATE(?,'MM/DD/YYYY'), ?, ?, SYSDATE, 'N') ";

	public static final String ADD_PROXY_SELECTION_CRITERIA_MUSIC_USER = "INSERT INTO PRX_SEL_MUS_USR_ID (PRX_SEL_ID, PRX_SRC_MUS_USR_ID, CRE_ID, CRE_DT, DEL_FL) VALUES (?, ?, ?, SYSDATE, 'N')" ;
	public static final String ADD_PROXY_SELECTION_CRITERIA_MUSIC_USER_TYPE = "INSERT INTO PRX_SEL_MUS_USR_TYP (PRX_SEL_ID, PRX_SRC_MUS_USR_TYP, CRE_ID, CRE_DT, DEL_FL) VALUES(?, ?, ?, SYSDATE, 'N')" ;
	public static final String ADD_PROXY_SELECTION_CRITERIA_MEMBER_PARTY_ID = "INSERT INTO PRX_SEL_MEM_PTY_ID (PRX_SEL_ID, PRX_SRC_MEM_PTY_ID, CRE_ID, CRE_DT, DEL_FL) VALUES(?, ?, ?, SYSDATE, 'N')" ;
	public static final String ADD_PROXY_SELECTION_CRITERIA_USE_TYPE = "INSERT INTO PRX_SEL_USE_TYP_CDE (PRX_SEL_ID, PRX_SRC_USE_TYP_CDE, CRE_ID, CRE_DT, DEL_FL) VALUES(?, ?, ?, SYSDATE, 'N')" ;

	
	public static final String GET_PROXY_SELECTION_CRITERIA_BASIC = 
		"SELECT prx_sel_id, to_char(prx_src_perf_stt_dt,'MM/DD/YYYY') prx_src_perf_stt_dt, to_char(prx_src_perf_end_dt,'MM/DD/YYYY') prx_src_perf_end_dt, to_char(prx_src_perf_stt_tm,'HH24:MI:SS') prx_src_perf_stt_tm, to_char(prx_src_perf_end_tm,'HH24:MI:SS') prx_src_perf_end_tm, prx_src_lic_typ_cde, to_char(prx_tgt_perf_stt_dt,'MM/DD/YYYY') prx_tgt_perf_stt_dt, to_char(prx_tgt_perf_end_dt,'MM/DD/YYYY') prx_tgt_perf_end_dt, to_char(prx_tgt_perf_stt_tm,'HH24:MI:SS') prx_tgt_perf_stt_tm, to_char(prx_tgt_perf_end_tm,'HH24:MI:SS') prx_tgt_perf_end_tm, prx_tgt_mus_usr_id, prx_restru_ind, to_char(prx_src_dis_stt_dt,'MM/DD/YYYY') prx_src_dis_stt_dt, to_char(prx_src_dis_end_dt,'MM/DD/YYYY') prx_src_dis_end_dt, sta, calc_run_ctl_id, cre_id, to_char(cre_dt,'MM/DD/YYYY HH24:MI:SS') cre_dt, upd_id, to_char(upd_dt,'MM/DD/YYYY HH24:MI:SS') upd_dt " +
		"  FROM prx_sel " +
		" WHERE del_fl = 'N' AND prx_sel_id = ? ";
	public static final String GET_PROXY_SELECTION_CRITERIA_MUSIC_USER = 
		"SELECT prx_src_mus_usr_id, ptynaforcall.na cal_ltr, ptynaforcall.cal_ltr_suf, ptynaforpa.na, " +
		"	   pkg_rpt_utils.rpt_fmt_pty_name (ptynaforcall.pty_na_typ_cde, ptynaforcall.sal, ptynaforcall.na, ptynaforcall.fst_na, ptynaforcall.mid_init, ptynaforcall.suf, ptynaforcall.suf_oth, ptynaforcall.cal_ltr_suf) AS formatted_party_callletter, " +
		"	   pkg_rpt_utils.rpt_fmt_pty_name (ptynaforpa.pty_na_typ_cde, ptynaforpa.sal, ptynaforpa.na, ptynaforpa.fst_na, ptynaforpa.mid_init, ptynaforpa.suf, ptynaforpa.suf_oth, ptynaforpa.cal_ltr_suf) AS formatted_party_fullname " +
		"  FROM prx_sel_mus_usr_id a  " +
		"  	   LEFT OUTER JOIN pty_na ptynaforcall ON (a.prx_src_mus_usr_id = ptynaforcall.pty_id AND ptynaforcall.pty_na_typ_cde = 'CL' AND ptynaforcall.del_fl = 'N' AND TRUNC (SYSDATE) BETWEEN ptynaforcall.stt_dt AND ptynaforcall.end_dt) " +
		"      LEFT OUTER JOIN pty_na ptynaforpa ON (a.prx_src_mus_usr_id = ptynaforpa.pty_id AND ptynaforpa.pty_na_typ_cde = 'PA' AND ptynaforpa.del_fl = 'N') " +
		" WHERE a.del_fl = 'N' AND a.prx_sel_id = ? ";
	public static final String GET_PROXY_SELECTION_CRITERIA_MUSIC_USER_TYPE = "SELECT prx_src_mus_usr_typ FROM prx_sel_mus_usr_typ WHERE del_fl = 'N' AND prx_sel_id = ? ";
	public static final String GET_PROXY_SELECTION_CRITERIA_MEMBER_PARTY_ID =
		"SELECT prx_src_mem_pty_id,  " +
		"	   pkg_rpt_utils.rpt_fmt_pty_name (ptynaforpa.pty_na_typ_cde, ptynaforpa.sal, ptynaforpa.na, ptynaforpa.fst_na, ptynaforpa.mid_init, ptynaforpa.suf, ptynaforpa.suf_oth, ptynaforpa.cal_ltr_suf) AS formatted_party_fullname " +
		"  FROM prx_sel_mem_pty_id LEFT OUTER JOIN pty_na ptynaforpa ON (prx_src_mem_pty_id = ptynaforpa.pty_id AND ptynaforpa.pty_na_typ_cde = 'PA' AND ptynaforpa.del_fl = 'N') " +
		" WHERE prx_sel_mem_pty_id.del_fl = 'N' AND prx_sel_mem_pty_id.prx_sel_id = ? ";
	public static final String GET_PROXY_SELECTION_CRITERIA_USE_TYPE = "SELECT prx_src_use_typ_cde FROM prx_sel_use_typ_cde WHERE del_fl = 'N' AND prx_sel_id = ? ";


	/***
	 * 
	 * 
	 * APM FILE 
	 */

	public static final String GET_APM_FILE_LIST_COUNT_DIST = "SELECT COUNT(*) TOTALRECORDCOUNT FROM APM_ARCHIVE A ";
	
	public static final String GET_APM_FILE_LIST_COUNT_RADIO = "SELECT COUNT(*) TOTALRECORDCOUNT FROM IMPORT_FILES B  ";
	
	/*public static final String GET_APM_FILE_LIST = 
		"SELECT APM_ARCHIVE_ID, " +
		"       APM_FILENAME, " +
		"       ORIG_FILENAME, " +
		"       PROCESS_DT, " +
		"       DEL_FL, " +
		"       SUPP_ID, " +
		"       HDR_CNT, " +
		"       DTL_CNT, " +
		"       CRE_DT, " +
		"       CRE_ID, " +
		"       UPD_DT, " +
		"       UPD_ID, " +
		"       STATUS, " +
		"       SUPP_CODE, " +
		"       TGTSURVYEARQTR " +
		"  FROM (SELECT A.APM_ARCHIVE_ID, " +
		"               A.APM_FILENAME, " +
		"               A.ORIG_FILENAME, " +
		"               TO_CHAR (A.PROCESS_DT, 'MM/DD/YYYY HH24:MI:SS') PROCESS_DT, " +
		"               A.DEL_FL, " +
		"               A.SUPP_ID, " +
		"               A.HDR_CNT, " +
		"               A.DTL_CNT, " +
		"               TO_CHAR (A.CRE_DT, 'MM/DD/YYYY') CRE_DT, " +
		"               A.CRE_ID, " +
		"               TO_CHAR (A.UPD_DT, 'MM/DD/YYYY') UPD_DT, " +
		"               A.UPD_ID, " +
		"               A.STATUS, " +
		"               A.SUPP_CODE, " +
		"               A.TGTSURVYEARQTR, " +
		"               ROW_NUMBER () OVER (ORDER BY APM_ARCHIVE_ID DESC) RN " +
		"          FROM APM_ARCHIVE A  WHERE A.DEL_FL = 'N') " +
		" WHERE rn BETWEEN ? AND ? "; 

*/	
	public static final String GET_APM_FILE_LIST_COMMON_COUNT_PART = 	"	select sum(TOTALRECORDCOUNT) from ( ";
	public static final String GET_APM_FILE_LIST_COMMON_SELECT_PART = 	
			"SELECT * FROM ( SELECT FILE_ID, " + 
			"       FILE_NAME, " + 
			"       FILE_TYPE, " + 
			"       PROCESS_DT, " +
			"       CNT, " +
			"       CRE_DT, " +
			"       CRE_ID, " +
			"       STATUS, " +
			"       SUPP_CODE, " +
			"       TGTSURVYEARQTR, " +
			"       ROW_NUMBER () OVER (ORDER BY PROCESS_DT) RN, " +
			"       COUNT (*) OVER () TOTALROWS " +
			"  FROM (" ;
	
	public static final String GET_APM_FILE_LIST_DIST = 
            "        SELECT A.APM_ARCHIVE_ID FILE_ID, " +
			"               A.APM_FILENAME FILE_NAME, " +
			"               'Distribution' FILE_TYPE, " +
			"               TO_CHAR (A.PROCESS_DT, 'MM/DD/YYYY HH24:MI:SS') PROCESS_DT, " +
			"               A.DTL_CNT CNT, " +
			"               TO_CHAR (A.CRE_DT, 'MM/DD/YYYY') CRE_DT, " +
			"               A.CRE_ID, " +
			"               S.DESCRIPTION STATUS, " +
			"               A.SUPP_CODE, " +
			"               A.TGTSURVYEARQTR " +
			"          FROM APM_ARCHIVE A  INNER JOIN STATUS S "+
			"            ON A.STATUS = S.STATUS ";
			
			
			
			public static final String GET_APM_FILE_LIST_RADIO = 	
			 "SELECT B.IMPORT_FILE_ID FILE_ID, " +
			"               B.IMPORT_FILE_NAME FILE_NAME, " +
			"               I.DESCRIPTION FILE_TYPE, " +
			"               TO_CHAR (B.IMPORT_FILE_DATE, 'MM/DD/YYYY HH24:MI:SS') PROCESS_DT, " +
			"               B.FILE_ROW_COUNT CNT, " +
			"               TO_CHAR (B.CRE_DT, 'MM/DD/YYYY') CRE_DT, " +
			"               B.CRE_ID, " +
			"               S.DESCRIPTION STATUS, " +
			"               B.SUPP_CODE, " +
			"               B.TGTSURVYEARQTR " +
			"          FROM IMPORT_FILES B "+
			"          INNER JOIN STATUS S ON B.IMPORT_FILE_STATUS=S.STATUS "+
			"          INNER JOIN IMPORT_FILE_TYPE I ON B.IMPORT_FILE_TYPE = I.IMPORT_FILE_TYPE  ";
		
		
	public static final String GET_APM_FILE_LIST_COMMON_ROW_NBR_PART = 	" )) WHERE RN BETWEEN ? AND ? ORDER BY 4 DESC"; 

	public static final String GET_MUS_USER_PTY_COUNT_START = "SELECT COUNT (*) " +
	"  FROM MVW_PTY_NA A, MVW_MUS_USER_TYP_SEL B, MVW_MUS_USER_LIC C " +
	" WHERE  ? BETWEEN TO_CHAR(A.STT_DT,'YYYY/MM/DD') AND TO_CHAR(A.END_DT,'YYYY/MM/DD') " +
	"       AND A.PTY_ID = B.PTY_ID  AND ? BETWEEN TO_CHAR(B.STT_DT,'YYYY/MM/DD') AND TO_CHAR(B.END_DT,'YYYY/MM/DD')  " +
	"       AND A.PTY_ID = C.PTY_ID  AND ? BETWEEN TO_CHAR(C.EFF_DT,'YYYY/MM/DD') AND TO_CHAR(C.END_DT,'YYYY/MM/DD')  " ;
	
	//public static final String LOAD_USAGE_FILE = "INSERT INTO IMPORT_FILES (IMPORT_FILE_ID, IMPORT_FILE_NAME, IMPORT_FILE_TYPE, IMPORT_FILE_STATUS, TGTSURVYEARQTR, DEL_FL, CRE_ID, CRE_DT) VALUES(?, ?, ?,  ?, ?,'N', ?, SYSDATE)";
	public static final String LOAD_USAGE_FILE = "INSERT INTO IMPORT_FILES ( IMPORT_FILE_ID, IMPORT_FILE_NAME, IMPORT_FILE_TYPE, IMPORT_FILE_STATUS, TGTSURVYEARQTR, DEL_FL, CRE_ID, CRE_DT, FILE_LOC) VALUES(?, ?, ?, ?, ?,'N', ?, SYSDATE,?)";
	
	public static final String SEQUENCE_IMPORT_FILE_ID = "SELECT PAPM.IMPORT_FILE_ID.NEXTVAL FROM DUAL";
	
	public static final String GET_MUS_USER_PTY_COUNT_END = " " ;
	
	
	public static final String GET_MUS_USER_PTY_DATA_START = "SELECT  PTY_ID, NA, CAL_LTR_SUF, MUS_USER_TYP_CDE, LIC_TYP_CDE FROM  " +
	"(  " +
	" SELECT A.PTY_ID, A.NA, A.CAL_LTR_SUF, B.MUS_USER_TYP_CDE, C.LIC_TYP_CDE, " +
	" ROW_NUMBER() OVER (ORDER BY A.PTY_ID ) RN  " +
	"  FROM MVW_PTY_NA A, MVW_MUS_USER_TYP_SEL B, MVW_MUS_USER_LIC C  " +
	" WHERE     ? BETWEEN TO_CHAR(A.STT_DT,'YYYY/MM/DD') AND TO_CHAR(A.END_DT,'YYYY/MM/DD')   " +
	"       AND A.PTY_ID = B.PTY_ID  AND ? BETWEEN TO_CHAR(B.STT_DT,'YYYY/MM/DD') AND TO_CHAR(B.END_DT,'YYYY/MM/DD') " +
	"       AND A.PTY_ID = C.PTY_ID  AND ? BETWEEN TO_CHAR(C.EFF_DT,'YYYY/MM/DD') AND TO_CHAR(C.END_DT,'YYYY/MM/DD') ";	

	public static final String GET_MUS_USER_PTY_DATA_END = "       )  " +
	"       WHERE RN BETWEEN ? AND ? " ;
	
//	public static final String GET_BULK_REQUEST_DATA_COUNT_SPLR_START = "SELECT COUNT(*) TOTALRECORDCOUNT FROM  " +
//	"(SELECT  " +
//	"  a.supp_code supp_code, pfr_na, wrk_ttl, " +
//	"         COUNT (1) wrk_perf_cnt, " +
//	"         SUM (DECODE( TRIM(TRANSLATE(play_cnt,'0123456789',' ')), NULL, DECODE (TO_NUMBER(play_cnt), NULL, 1, 0, 1, TO_NUMBER(play_cnt)), 1)) play_cnt " +
//	"    FROM apm_wrk_perf a " +
//	"   WHERE ((a.wrk_id IS  NULL and a.del_fl = 'N') OR  (TRUNC(a.man_match_dt) = TRUNC(sysdate) AND a.apm_match_typ = 'MAN'  ) ) "+
//	"   AND nvl(a.SEND_MAN_MATCH,'Y') <> 'N'" +
//	"	AND a.proc_status IN ('BUSVLD') AND a.lock_ind = 'N' "+
//	"	AND EXISTS (SELECT PERF.APM_PERF_HDR_ID    FROM PAPM.APM_PERF_HDR PERF  WHERE     PERF.PROC_STATUS IN ('BUSVLD')        AND PERF.APM_PERF_HDR_ID = A.APM_PERF_HDR_ID  AND PERF.DEL_FL = 'N'  AND perf.tgtsurvyearqtr =  (SELECT tgtsurvyearqtr  FROM apm_surv_date WHERE active_fl = 'Y')) ";
//	
//	public static final String GET_BULK_REQUEST_DATA_COUNT_NSPLR_START = "SELECT COUNT(*) TOTALRECORDCOUNT FROM  " +
//	"(SELECT  " +
//	"  null supp_code, pfr_na, wrk_ttl, " +
//	"         COUNT (1) wrk_perf_cnt, " +
//	"         SUM (DECODE( TRIM(TRANSLATE(play_cnt,'0123456789',' ')), NULL, DECODE (TO_NUMBER(play_cnt), NULL, 1, 0, 1, TO_NUMBER(play_cnt)), 1)) play_cnt " +
//	"    FROM apm_wrk_perf a " +
//	"   WHERE ((a.wrk_id IS  NULL and a.del_fl = 'N') OR  (TRUNC(a.man_match_dt) = TRUNC(sysdate) AND a.apm_match_typ = 'MAN'  ) ) "+
//	"   AND nvl(a.SEND_MAN_MATCH,'Y') <> 'N'" +
//	"	AND a.proc_status IN ('BUSVLD') AND a.lock_ind = 'N' " +
//	"	AND EXISTS (SELECT PERF.APM_PERF_HDR_ID    FROM PAPM.APM_PERF_HDR PERF  WHERE     PERF.PROC_STATUS IN ('BUSVLD')        AND PERF.APM_PERF_HDR_ID = A.APM_PERF_HDR_ID  AND PERF.DEL_FL = 'N'  AND perf.tgtsurvyearqtr =  (SELECT tgtsurvyearqtr  FROM apm_surv_date WHERE active_fl = 'Y')) ";
	
//	public static final String GET_BULK_REQUEST_DATA_COUNT_SPLR_END = 
//		"GROUP BY supp_code, a.pfr_na, a.wrk_ttl, a.wrk_id, a.man_match_usr_id,  a.del_fl) ";
//	public static final String GET_BULK_REQUEST_DATA_COUNT_NSPLR_END = 
//		"GROUP BY a.pfr_na, a.wrk_ttl, a.wrk_id, a.man_match_usr_id,   a.del_fl ) ";
//	
	

	public static final String GET_WRK_MATCH_INTERSECT = " INTERSECT ";
	public static final String GET_WRK_MATCH_SELECT = "SELECT ";
	public static final String GET_WRK_MATCH_SELECT_INITIAL_SPLR_COL = "SUPP_CODE, PROVIDER_ID, ";
	public static final String GET_WRK_MATCH_SELECT_INITIAL_NSPLR_COL = "NULL SUPP_CODE, ";
	public static final String GET_WRK_MATCH_SELECT_INITIAL_COMMON_COL = " PFR_NA, " +
	"       WRK_TTL, WRITER," +
	"       WRK_PERF_CNT, " +
	"       WRK_ID, " +
	"       MAN_MATCH_USR_ID, " +
	"       DEL_FL, " +
	"       PLAY_CNT, " +
	"       TRIM (TO_CHAR (EST_DOL_AMT, '$999,999,999,999,990.99')) EST_DOL_AMT, " +
	"       EST_DLR_FOUND, MULT_WRK_ID, ASG_USR_ID, " +
	"       TOTALROWS ";
	public static final String GET_WRK_MATCH_FROM = " FROM ";
	public static final String GET_WRK_MATCH_PAREN_OPEN = " ( ";
	public static final String GET_WRK_MATCH_PAREN_CLOSE = " ) ";
	public static final String GET_WRK_MATCH_SELECT_INNER_SPLR_COL = " SUPP_CODE SUPP_CODE, " +
	"                 (CASE " +
	"                     WHEN SUPP_CODE = 'MEDIAMONITOR' THEN PROVIDER_ID  WHEN SUPP_CODE = 'NIELSEN'  THEN PROVIDER_ID " +
	"                     ELSE NULL " +
	"                  END) " +
	"                    PROVIDER_ID, " ;

	public static final String GET_WRK_MATCH_SELECT_INNER_NSPLR_COL = "  " ;
	public static final String GET_WRK_MATCH_SELECT_INNER_COMMON_COL = "                   PFR_NA, " +
	"                 WRK_TTL, WRITER, " +
	"                 WRK_ID, " +
	"                 MAN_MATCH_USR_ID, " +
	"                 DEL_FL, ASG_USR_ID, " +
	"                 COUNT (1) WRK_PERF_CNT, " +
	"                 SUM (PLAY_CNT_VLD) PLAY_CNT, " +
	"                 SUM (NVL (ESTM_DOLLAR_VAL, 0)) EST_DOL_AMT, " +
	"                 MIN (ESTM_DLRVAL_FOUND) EST_DLR_FOUND,  MULT_WRK_ID, " +
	"                 ROW_NUMBER () " +
	"                 OVER ( " +
	"                    ORDER BY " +
	"                       SUM (NVL (ESTM_DOLLAR_VAL, 0)) DESC, " +
	"                       COUNT (1) DESC, " +
	"                       WRK_TTL,  NVL(PFR_NA, WRITER) ) " +
	"                    RN, " +
	"                 COUNT (*) OVER () TOTALROWS ";
	
	public static final String ORACLE_INDEX_HINT = " /*+  INDEX(A APM_WRK_PERF_IX12) */ ";
	
	public static final String GET_WRK_MATCH_SELECT_INNER_INNER_SPLR_COL = "  A.SUPP_CODE SUPP_CODE, A.PROVIDER_ID, " ;
	public static final String GET_WRK_MATCH_SELECT_INNER_INNER_NSPLR_COL = "  " ;
	public static final String GET_WRK_MATCH_SELECT_INNER_INNER_COMMON_COL = "                         A.PFR_NA, " +
	"                         A.WRK_TTL, A.WRITER, " +
	"                         A.WRK_ID, " +
	"                         A.MAN_MATCH_USR_ID, " +
	"                         A.DEL_FL, " +
	"                         A.PLAY_CNT_VLD, " +
	"                         A.ESTM_DOLLAR_VAL, " +
	"                         A.ESTM_DLRVAL_FOUND, A.MULT_WRK_ID, A.ASG_USR_ID " +
	"                    FROM PAPM.APM_WRK_PERF A ";
	
	
	
	
	public static final String GET_WRK_MATCH_CONTEXT_START = " WITH CTX AS "  +
		"( " +
		"SELECT /*+ materialize */ * FROM ( " ;
	
	public static final String GET_WRK_MATCH_CONTEXT_END = " )) ";
	public static final String GET_WRK_MATCH_CONTEXT_JOIN = " , CTX ";
	public static final String GET_WRK_MATCH_CONTEXT_WHERE_CLAUSE = " AND A.APM_WRK_PERF_ID = CTX.APM_WRK_PERF_ID ";
	
	public static final String GET_WRK_MATCH_WHERE_CLAUSE = " WHERE A.PROC_STATUS = 'BUSVLD' " +
	"                         AND A.SEND_MAN_MATCH = 'Y' " +
	"                         AND A.LOCK_IND = 'N' " +
	" 						  AND A.WRK_TTL IS NOT NULL  " +
	" 						  AND A.TGTSURVYEARQTR =  " ;
	

	public static final String GET_WRK_MATCH_UNION_ALL = " UNION ALL ";
	public static final String GET_WRK_MATCH_GROUP_BY = " GROUP BY ";
	public static final String GET_WRK_MATCH_GROUP_BY_SPLR_COL = " SUPP_CODE, " +
	"                 (CASE " +
	"                     WHEN SUPP_CODE = 'MEDIAMONITOR' THEN PROVIDER_ID  WHEN SUPP_CODE = 'NIELSEN'  THEN PROVIDER_ID" +
	"                     ELSE NULL " +
	"                  END), ";
	public static final String GET_WRK_MATCH_GROUP_BY_COMMON_COL = "  PFR_NA, " +
	"                 WRK_TTL, WRITER, " +
	"                 WRK_ID, " +
	"                 MAN_MATCH_USR_ID, " +
	"                 DEL_FL,  MULT_WRK_ID, ASG_USR_ID ";
	
	public static final String GET_WRK_MATCH_ROWNUM_WHERE_CLAUSE = "  WHERE RN BETWEEN ? AND ? ";
	public static final String GET_WRK_MATCH_ORDER_CLAUSE = "  ORDER BY RN ";
	
	public static final String GET_WRK_MATCH_PFR_BEGINS_SEARCH = " AND A.PFR_NA LIKE ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'NAME', 'BGNS') FROM DUAL) ";
	public static final String GET_WRK_MATCH_TTL_BEGINS_SEARCH = " AND A.WRK_TTL LIKE ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'TITLE', 'BGNS') FROM DUAL) ";

	public static final String GET_WRK_MATCH_PFR_CONTAINS_SEARCH = " SELECT CT1.APM_WRK_PERF_ID FROM PAPM.APM_WRK_PERF CT1 WHERE CATSEARCH (CT1.PFR_NA, ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'NAME', 'CNTS') FROM DUAL), '') > 0 and CT1.tgtsurvyearqtr =  ";
	public static final String GET_WRK_MATCH_TTL_CONTAINS_SEARCH = " SELECT  CT.APM_WRK_PERF_ID FROM PAPM.APM_WRK_PERF CT WHERE CATSEARCH (CT.WRK_TTL, ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'TITLE', 'CNTS') FROM DUAL), '') > 0 and CT.tgtsurvyearqtr =  ";
	
	public static final String GET_WRK_MATCH_MATCH_STATUS_NOT_MATCHED = " AND A.APM_MATCH_TYP = 'NMT' AND A.WRK_ID IS NULL  AND A.DEL_FL = 'N' ";
	public static final String GET_WRK_MATCH_MATCH_STATUS_MATCHED = " AND A.APM_MATCH_TYP = 'MAN' AND A.DEL_FL = 'N' AND A.MAN_MATCH_DT >= TRUNC (SYSDATE) ";
	public static final String GET_WRK_MATCH_MATCH_STATUS_DELETED = " AND A.APM_MATCH_TYP = 'MAN' AND A.DEL_FL = 'Y' AND A.MAN_MATCH_DT >= TRUNC (SYSDATE) AND A.DEL_RSN_CDE = 'MAN_DEL' ";
	public static final String GET_WRK_MATCH_MATCH_STATUS_MATCHED_OR_DELETED = " AND A.APM_MATCH_TYP = 'MAN' AND A.MAN_MATCH_DT >= TRUNC (SYSDATE) AND (A.DEL_FL = 'N' OR (A.DEL_FL = 'Y' AND A.DEL_RSN_CDE = 'MAN_DEL')) ";
	// ==================================================================================================================================
	
	
	
	
	
	public static final String GET_SPLR_CONTEXT_END = " )) ";
	public static final String GET_SPLR_CONTEXT_JOIN = " , CTX ";
	public static final String GET_SPLR_CONTEXT_WHERE_CLAUSE = " AND A.APM_WRK_PERF_ID = CTX.APM_WRK_PERF_ID ";
	/*
	public static final String GET_SPLR_MIDDLE = 	
	"SELECT A.SUPP_CODE SUPP_CODE, " +
	"                         A.PROVIDER_ID, " +
	"                         A.PFR_NA, " +
	"                         A.WRK_TTL, " +
	"                         A.WRK_ID, " +
	"                         A.MAN_MATCH_USR_ID, " +
	"                         A.DEL_FL, " +
	"                         A.PLAY_CNT_VLD, " +
	"                         A.ESTM_DOLLAR_VAL, " +
	"                         A.ESTM_DLRVAL_FOUND " +
	"                    FROM PAPM.APM_WRK_PERF A ";*/
	/*public static final String GET_SPLR_WHERE_CLAUSE = "                   WHERE A.PROC_STATUS = 'BUSVLD' " +
	"                         AND A.SEND_MAN_MATCH = 'Y' " +
	"                         AND A.LOCK_IND = 'N' " +
	" 						  AND A.TGTSURVYEARQTR =  " ;*/
	/*"                         AND EXISTS " +
	"                                (SELECT PERF.APM_PERF_HDR_ID " +
	"                                   FROM PAPM.APM_PERF_HDR PERF " +
	"                                  WHERE PERF.PROC_STATUS IN ('BUSVLD', 'PBVLD') " +
	"                                        AND PERF.TGTSURVYEARQTR =  "; */

	//public static final String GET_SPLR_MIDDLE = " " ;//" AND PERF.APM_PERF_HDR_ID = " +	"                                               A.APM_PERF_HDR_ID " +	"                                        AND PERF.DEL_FL = 'N') " ;
	
	

	/*public static final String GET_SPLR_END = " ) " +
	"        GROUP BY SUPP_CODE, " +
	"                 (CASE " +
	"                     WHEN SUPP_CODE = 'MEDIAMONITOR' THEN PROVIDER_ID " +
	"                     ELSE NULL " +
	"                  END), " +
	"                 PFR_NA, " +
	"                 WRK_TTL, " +
	"                 WRK_ID, " +
	"                 MAN_MATCH_USR_ID, " +
	"                 DEL_FL) " +
	" WHERE RN BETWEEN ? AND ? ORDER BY RN"; */

	/*public static final String GET_SPLR_ALL_UNION_START = " UNION ALL SELECT A.SUPP_CODE SUPP_CODE, " +
	"                         A.PROVIDER_ID, " +
	"                         A.PFR_NA, " +
	"                         A.WRK_TTL, " +
	"                         A.WRK_ID, " +
	"                         A.MAN_MATCH_USR_ID, " +
	"                         A.DEL_FL, " +
	"                         A.PLAY_CNT_VLD, " +
	"                         A.ESTM_DOLLAR_VAL, " +
	"                         A.ESTM_DLRVAL_FOUND " +
	"                    FROM PAPM.APM_WRK_PERF A " +
	"                   WHERE     A.PROC_STATUS = 'BUSVLD' " +
	"                         AND A.SEND_MAN_MATCH = 'Y' " +
	"                         AND A.LOCK_IND = 'N' " +
	" 						  AND A.TGTSURVYEARQTR =  " ;*/
	
	
	/*public static final String GET_NSPLR_START = "SELECT NULL SUPP_CODE, " +
	"       PFR_NA, " +
	"       WRK_TTL, " +
	"       WRK_PERF_CNT, " +
	"       WRK_ID, " +
	"       MAN_MATCH_USR_ID, " +
	"       DEL_FL, " +
	"       PLAY_CNT, " +
	"       TRIM (TO_CHAR (EST_DOL_AMT, '$999,999,999,999,990.99')) EST_DOL_AMT, " +
	"       EST_DLR_FOUND, " +
	"       TOTALROWS " +
	"  FROM (  SELECT " +
	"                 PFR_NA, " +
	"                 WRK_TTL, " +
	"                 WRK_ID, " +
	"                 MAN_MATCH_USR_ID, " +
	"                 DEL_FL, " +
	"                 COUNT (1) WRK_PERF_CNT, " +
	"                 SUM (PLAY_CNT_VLD) PLAY_CNT, " +
	"                 SUM (NVL (ESTM_DOLLAR_VAL, 0)) EST_DOL_AMT, " +
	"                 MIN (ESTM_DLRVAL_FOUND) EST_DLR_FOUND, " +
	"                 ROW_NUMBER () " +
	"                 OVER ( " +
	"                    ORDER BY " +
	"                       SUM (NVL (ESTM_DOLLAR_VAL, 0)) DESC, " +
	"                       COUNT (1) DESC, " +
	"                       WRK_TTL) " +
	"                    RN, " +
	"                 COUNT (*) OVER () TOTALROWS " +
	"            FROM (SELECT " +
	"                         A.PFR_NA, " +
	"                         A.WRK_TTL, " +
	"                         A.WRK_ID, " +
	"                         A.MAN_MATCH_USR_ID, " +
	"                         A.DEL_FL, " +
	"                         A.PLAY_CNT_VLD, " +
	"                         A.ESTM_DOLLAR_VAL, " +
	"                         A.ESTM_DLRVAL_FOUND " +
	"                    FROM PAPM.APM_WRK_PERF A " +
	"                   WHERE     A.PROC_STATUS = 'BUSVLD' " +
	"                         AND A.SEND_MAN_MATCH = 'Y' " +
	"                         AND A.LOCK_IND = 'N' " +
	" 						  AND A.TGTSURVYEARQTR =  " ;*/
	/*"                         AND EXISTS " +
	"                                (SELECT PERF.APM_PERF_HDR_ID " +
	"                                   FROM PAPM.APM_PERF_HDR PERF " +
	"                                  WHERE PERF.PROC_STATUS IN ('BUSVLD', 'PBVLD') " +
	"                                        AND PERF.TGTSURVYEARQTR =  "; */ 

	/*public static final String GET_NSPLR_MIDDLE = " ";*///" AND PERF.APM_PERF_HDR_ID = " +"                                               A.APM_PERF_HDR_ID " +"                                        AND PERF.DEL_FL = 'N') " ;
	
	
/*
	public static final String GET_NSPLR_END = " ) " +
	"        GROUP BY "+
	"                 PFR_NA, " +
	"                 WRK_TTL, " +
	"                 WRK_ID, " +
	"                 MAN_MATCH_USR_ID, " +
	"                 DEL_FL) " +
	" WHERE RN BETWEEN ? AND ? ORDER BY RN "; */

	/*public static final String GET_NSPLR_ALL_UNION_START = " UNION ALL SELECT " +
	"                         A.PFR_NA, " +
	"                         A.WRK_TTL, " +
	"                         A.WRK_ID, " +
	"                         A.MAN_MATCH_USR_ID, " +
	"                         A.DEL_FL, " +
	"                         A.PLAY_CNT_VLD, " +
	"                         A.ESTM_DOLLAR_VAL, " +
	"                         A.ESTM_DLRVAL_FOUND " +
	"                    FROM PAPM.APM_WRK_PERF A " +
	"                   WHERE     A.PROC_STATUS = 'BUSVLD' " +
	"                         AND A.SEND_MAN_MATCH = 'Y' " +
	"                         AND A.LOCK_IND = 'N' " +
	" 						  AND A.TGTSURVYEARQTR =  " ;*/
	/*"                         AND EXISTS " +
	"                                (SELECT PERF.APM_PERF_HDR_ID " +
	"                                   FROM PAPM.APM_PERF_HDR PERF " +
	"                                  WHERE PERF.PROC_STATUS IN ('BUSVLD', 'PBVLD') "  +
	"                                        AND PERF.TGTSURVYEARQTR =  "; */

	
	
	
	/*public static final String GET_BULK_REQUEST_DATA_NSPLR_START = "SELECT SUPP_CODE,  pfr_na, wrk_ttl,  wrk_perf_cnt,  wrk_id,  man_match_usr_id, del_fl, play_cnt, TRIM (TO_CHAR (EST_DOL_AMT, '$999,999,999,999,990.99')) EST_DOL_AMT,  EST_DLR_FOUND, TOTALROWS FROM ( " +
	"SELECT   " +
	"  	null supp_code, pfr_na, wrk_ttl,  a.wrk_id,  a.man_match_usr_id,  a.del_fl, " +
	"         COUNT (1) wrk_perf_cnt, " +
	"         SUM (A.PLAY_CNT_VLD) play_cnt, SUM (NVL (A.ESTM_DOLLAR_VAL, 0)) EST_DOL_AMT, MIN(A.ESTM_DLRVAL_FOUND) EST_DLR_FOUND, ROW_NUMBER() OVER (ORDER BY SUM (NVL (ESTM_DOLLAR_VAL, 0)) DESC, COUNT (1)  DESC, a.wrk_ttl) RN, COUNT(*) OVER () TOTALROWS  " +
	"    FROM apm_wrk_perf a " +
	"   WHERE a.proc_status = 'BUSVLD' "+
	"   AND a.SEND_MAN_MATCH = 'Y' " +
	"	AND a.lock_ind = 'N' " +
	"	AND EXISTS (SELECT PERF.APM_PERF_HDR_ID    FROM PAPM.APM_PERF_HDR PERF  WHERE  perf.tgtsurvyearqtr =  ";
	
	public static final String GET_BULK_REQUEST_DATA_NSPLR_MIDDLE_PART = "  AND PERF.PROC_STATUS IN ('BUSVLD','PBVLD') AND PERF.APM_PERF_HDR_ID = A.APM_PERF_HDR_ID  AND PERF.DEL_FL = 'N'  ) ";
	
	public static final String GET_BULK_REQUEST_DATA_SPLR_START = "SELECT SUPP_CODE,  PROVIDER_ID, pfr_na, wrk_ttl,  wrk_perf_cnt,  wrk_id,  man_match_usr_id, del_fl, play_cnt, TRIM (TO_CHAR (EST_DOL_AMT, '$999,999,999,999,990.99')) EST_DOL_AMT,  EST_DLR_FOUND, TOTALROWS FROM ( " +
	"SELECT   " +
	"  a.supp_code supp_code,  (CASE WHEN SUPP_CODE = 'MEDIAMONITOR' THEN A.PROVIDER_ID ELSE NULL END) PROVIDER_ID, pfr_na, wrk_ttl,  a.wrk_id,  a.man_match_usr_id,  a.del_fl, " +
	"         COUNT (1) wrk_perf_cnt, " +
	"         SUM (A.PLAY_CNT_VLD) play_cnt, SUM (NVL (A.ESTM_DOLLAR_VAL, 0)) EST_DOL_AMT, MIN(A.ESTM_DLRVAL_FOUND) EST_DLR_FOUND, ROW_NUMBER() OVER (ORDER BY SUM (NVL (ESTM_DOLLAR_VAL, 0)) DESC, COUNT (1)  DESC,  a.wrk_ttl) RN, COUNT(*) OVER () TOTALROWS " +
	"    FROM apm_wrk_perf a " +
	"   WHERE a.proc_status = 'BUSVLD' "+  
	"   AND a.SEND_MAN_MATCH = 'Y' " +
	"	AND a.lock_ind = 'N' " +
	"	AND EXISTS (SELECT PERF.APM_PERF_HDR_ID    FROM PAPM.APM_PERF_HDR PERF  WHERE  perf.tgtsurvyearqtr =  ";
	
	public static final String GET_BULK_REQUEST_DATA_SPLR_MIDDLE_PART = "  AND PERF.PROC_STATUS IN ('BUSVLD','PBVLD') AND PERF.APM_PERF_HDR_ID = A.APM_PERF_HDR_ID  AND PERF.DEL_FL = 'N'  ) ";

	
	public static final String GET_BULK_REQUEST_DATA_SPLR_END = 
		" GROUP BY supp_code,   (CASE WHEN SUPP_CODE = 'MEDIAMONITOR' THEN A.PROVIDER_ID ELSE NULL END),a.pfr_na, a.wrk_ttl, a.wrk_id, a.man_match_usr_id, a.del_fl " +
		" ORDER BY  EST_DOL_AMT DESC, wrk_perf_cnt DESC, a.wrk_ttl " +
		") WHERE RN BETWEEN ? AND ? ";
	
	public static final String GET_BULK_REQUEST_DATA_NSPLR_END = 
		" GROUP BY a.pfr_na, a.wrk_ttl, a.wrk_id,  a.man_match_usr_id, a.del_fl " +
		" ORDER BY  EST_DOL_AMT DESC, wrk_perf_cnt DESC, a.wrk_ttl " +
		") WHERE RN BETWEEN ? AND ? ";
*/

	public static final String INSERT_APM_PERF_BLK_REQ = "INSERT INTO APM_PERF_BLK_REQ ( " +
	"APM_PERF_BLK_REQ_ID,                 " +
	"APM_PERF_BLK_REQ_GRP_ID,                 " +
	"SUPP_CODE,                 " +
	"PFR_NA,                 " +
	"WRK_TTL,                 " +
	"WRK_ID,                 " +
	"WRK_PERF_CNT,                 " +
	"PLAY_CNT,                 " +
	"STA_CDE,                 " +
	"CRE_DT,                 " +
	"CRE_ID,                 " +
	"DEL_FL, REQ_TYP_CDE, WRITER, TGTSURVYEARQTR) " +
	"VALUES (?,?,?,?,?,?,?,?,?,SYSDATE,?,'N',?, ?, ?) "; 
	
	
	public static final String UPDATE_APM_PERF_BLK_REQ_CO = "UPDATE APM_PERF_BLK_REQ SET STA_CDE = 'CO', UPD_DT = SYSDATE, UPD_ID = ? WHERE DEL_FL = 'N' AND APM_PERF_BLK_REQ_GRP_ID = ? ";
	
	public static final String INSERT_APM_PERF_BLK_REQ_DUMMY = "INSERT INTO APM_PERF_BLK_REQ ( " +
	"APM_PERF_BLK_REQ_ID,                 " +
	"APM_PERF_BLK_REQ_GRP_ID,                 " +
	"SUPP_CODE,                 " +
	"PFR_NA,                 " +
	"WRK_TTL,                 " +
	"WRK_ID,                 " +
	"WRK_PERF_CNT,                 " +
	"PLAY_CNT,                 " +
	"STA_CDE,                 " +
	"CRE_DT,                 " +
	"CRE_ID,                 " +
	"DEL_FL, REQ_TYP_CDE, UPD_ID, UPD_DT, WRITER) " +
	"VALUES (?,?,?,?,?,?,?,?,?,SYSDATE,?,'N',?,?, SYSDATE, ?) "; 

	
	public static final String GET_SEQUENCE_APM_PERF_BLK_REQ_ID = "SELECT APM_PERF_BLK_REQ_ID.NEXTVAL FROM DUAL";
	public static final String GET_SEQUENCE_APM_PERF_BLK_REQ_GRP_ID = "SELECT APM_PERF_BLK_REQ_GRP_ID.NEXTVAL FROM DUAL";
	

	public static final String GET_SEQUENCE_LM_BULK_UPD_REQ_ID = "SELECT PAPM.LM_BULK_UPD_REQ_ID.NEXTVAL FROM DUAL";
	
	public static final String GET_LEANED_MATCH_COUNT = "SELECT COUNT(1) GROUP_COUNT  FROM APM_LEARNED_MATCH A " +
	"WHERE A.DEL_FL = 'N'  " +
	"AND A.PFR_NA = ? " +
	"AND A.WRK_TTL = ? AND A.WRK_ID = ? "; 
	
	
	public static final String DELETE_LEARNED_MATCH =	
	"UPDATE APM_LEARNED_MATCH SET DEL_FL = 'Y', APM_MATCH_TYP = 'MAN', UPD_DT=SYSDATE, UPD_ID=?  " +
	"WHERE   PFR_NA = ?  AND WRK_TTL = ? AND WRK_ID = ? AND DEL_FL = 'N' AND LRN_DEL = 'N' " ;
	
	public static final String DELETE_LEARNED_MATCH_WTR =	
		"UPDATE APM_LEARNED_MATCH_WTR SET DEL_FL = 'Y', APM_MATCH_TYP = 'MAN', UPD_DT=SYSDATE, UPD_ID=?  " +
		"WHERE   WRITER = ?  AND WRK_TTL = ? AND WRK_ID = ? AND DEL_FL = 'N' AND LRN_DEL = 'N' " ;	
		
	public static final String GET_APM_LRN_MCH_TYPE_GRPBY = 
	"SELECT APM_LRN_MCH_TYP FROM SUPP_FORMAT WHERE SUPP_CODE = ? AND  APM_LRN_MCH_TYP = 'WTR' "; 
	
	public static final String GET_APM_LRN_MCH_TYPE_NOGRPBY = 
	"SELECT APM_LRN_MCH_TYP FROM PAPM.SUPP_FORMAT S WHERE EXISTS"+
	"(SELECT 1 FROM PAPM.APM_WRK_PERF A WHERE A.PROC_STATUS = 'BUSVLD' AND A.SEND_MAN_MATCH = 'Y' AND A.LOCK_IND = 'N' AND A.WRK_TTL IS NOT NULL AND A.TGTSURVYEARQTR = ? "+  
	" /*AND A.APM_MATCH_TYP = 'NMT' AND A.WRK_ID IS NULL */AND A.DEL_FL = 'N' AND A.WRK_TTL = ? AND A.WRITER = ? AND A.SUPP_CODE = S.SUPP_CODE "+
	") AND S.APM_LRN_MCH_TYP = 'WTR'";
	
	public static final String GET_APM_LRN_MCH_TYPE_NOGRPBY_CATALOG = 
			 " SELECT APM_LRN_MCH_TYP FROM PAPM.SUPP_FORMAT S WHERE EXISTS "
			+ "(SELECT 1 FROM PAPM.APM_CATALOG A WHERE A.PROC_STATUS = 'BUSVLD' AND A.SEND_MAN_MATCH = 'Y'  AND A.WRK_TTL IS NOT NULL   "
			+ " AND (A.DEL_FL = 'N' OR (A.DEL_FL = 'Y' AND A.MAN_MATCH_DT > TRUNC(SYSDATE))) AND A.WRK_TTL = ? AND A.WRITER = ? AND A.SUPP_CODE = S.SUPP_CODE  "
			+ ") AND S.APM_LRN_MCH_TYP = 'WTR' ";
	
	public static final String UPDATE_LEARNED_MATCH = 	
	"UPDATE APM_LEARNED_MATCH SET DEL_FL = 'Y', APM_MATCH_TYP = 'MAN', UPD_DT=SYSDATE, UPD_ID=?  " +
	"WHERE   PFR_NA = ?  AND WRK_TTL = ? AND WRK_ID <> ?  AND DEL_FL = 'N'   " ;
	
	public static final String UPDATE_LEARNED_MATCH_WTR = 	
		"UPDATE APM_LEARNED_MATCH_WTR SET DEL_FL = 'Y', APM_MATCH_TYP = 'MAN', UPD_DT=SYSDATE, UPD_ID=?  " +
		"WHERE   WRITER = ?  AND WRK_TTL = ? AND WRK_ID <> ?  AND DEL_FL = 'N'   " ;
		
	public static final String LEARNED_DEL_EXISTS =  "select  case when count(*) > 0 then 'Y' else 'N' end learnedDeleteExists  from APM_LEARNED_MATCH " +
	"where  wrk_ttl = ? and pfr_na = ?  and lrn_del = 'Y' ";
	
	public static final String LEARNED_DEL_EXISTS_WTR =  "select  case when count(*) > 0 then 'Y' else 'N' end learnedDeleteExists  from APM_LEARNED_MATCH_WTR " +
	"where  wrk_ttl = ? and writer = ?  and lrn_del = 'Y' ";
	
	public static final String UNDELETE_LEARNED_MATCH =  "delete from APM_LEARNED_MATCH " +
	"where  wrk_ttl = ? and pfr_na = ?  and lrn_del = 'Y' ";
	 
	public static final String UNDELETE_LEARNED_MATCH_WTR =  "delete from APM_LEARNED_MATCH_WTR " +
	"where  wrk_ttl = ? and writer = ?  and lrn_del = 'Y' ";
	
	public static final String GET_APM_PERF_BLK_REQ_RECORD =  
	" SELECT supp_code, pfr_na, wrk_ttl, wrk_id, req_typ_cde, writer, TGTSURVYEARQTR FROM apm_perf_blk_req WHERE apm_perf_blk_req_grp_id = ?";
	
	public static final String VALIDATE_HDR_FOR_UNDELETE =  
		" 	select case when count(*) > 0 then 'Y' else 'N' end invalidHeaderExists  from APM_PERF_HDR "+
		"   where APM_PERF_HDR_ID in "+
        " ( "+
        "   select APM_PERF_HDR_ID from APM_WRK_PERF   where DEL_FL = 'Y' and MAN_MATCH_DT >= TRUNC(SYSDATE) "+
        " and NVL (SEND_MAN_MATCH, 'Y') <> 'N' and PROC_STATUS = 'BUSVLD' and LOCK_IND = 'N' and DEL_RSN_CDE = 'MAN_DEL' ";
        
	public static final String UPDATE_APM_LEARNED_MATCH_EVEN_DELETED_START =	
	"UPDATE ";
	
	public static final String UPDATE_APM_LEARNED_MATCH_EVEN_DELETED_END =
	" SET DEL_FL = 'N', APM_MATCH_TYP = 'LRN', UPD_DT=SYSDATE, UPD_ID=?, LRN_DEL=?  " +
	"WHERE  WRK_TTL = ? AND WRK_ID =? " ;	
	
	public static final String UPDATE_ORIGINAL_APM_LEARNED_MATCH = 	
		"UPDATE APM_LEARNED_MATCH SET DEL_FL = 'N', APM_MATCH_TYP = 'LRN', UPD_DT=SYSDATE, UPD_ID=?, LRN_DEL=?, WRK_ID = ?  " +
		"WHERE   PFR_NA = ?  AND WRK_TTL = ? AND WRK_ID =? AND DEL_FL = 'N' " ;
	
	public static final String UPDATE_ORIGINAL_APM_LEARNED_MATCH_WTR = 	
		"UPDATE APM_LEARNED_MATCH_WTR SET DEL_FL = 'N', APM_MATCH_TYP = 'LRN', UPD_DT=SYSDATE, UPD_ID=?, LRN_DEL=?, WRK_ID = ?  " +
		"WHERE   WRITER = ?  AND WRK_TTL = ? AND WRK_ID =? AND DEL_FL = 'N' " ;	


	public static final String DELETE_INVALID_APM_LEARNED_MATCH = 	
		"UPDATE APM_LEARNED_MATCH SET DEL_FL = 'Y', UPD_DT=SYSDATE, UPD_ID=?   " +
		"WHERE   PFR_NA = ?  AND WRK_TTL = ? AND WRK_ID <> ? AND DEL_FL = 'N' " ;
	
	public static final String DELETE_INVALID_APM_LEARNED_MATCH_WTR = 	
		"UPDATE APM_LEARNED_MATCH_WTR SET DEL_FL = 'Y', UPD_DT=SYSDATE, UPD_ID=?   " +
		"WHERE   WRITER = ?  AND WRK_TTL = ? AND WRK_ID <> ? AND DEL_FL = 'N' " ;
	
	
	public static final String DELETE_APM_LEARNED_MATCH = 	
		"UPDATE APM_LEARNED_MATCH SET DEL_FL = 'Y', UPD_DT=SYSDATE, UPD_ID=?, MULT_WRK_ID = NULL   " +
		"WHERE   PFR_NA = ?  AND WRK_TTL = ? AND DEL_FL = 'N' " ;
	
	
	public static final String DELETE_APM_LEARNED_MATCH_WTR = 	
		"UPDATE APM_LEARNED_MATCH_WTR SET DEL_FL = 'Y', UPD_DT=SYSDATE, UPD_ID=?, MULT_WRK_ID = NULL   " +
		"WHERE   WRITER = ?  AND WRK_TTL = ? AND DEL_FL = 'N' " ;
	
	public static final String UPDATE_LEARNED_MATCH_VALID = 	
	"UPDATE APM_LEARNED_MATCH SET DEL_FL = 'N', APM_MATCH_TYP = 'MAN', UPD_DT=SYSDATE, UPD_ID=? , LRN_DEL = ?  " +
	"WHERE   PFR_NA = ?  AND WRK_TTL = ?  ";
	public static final String UPDATE_LEARNED_MATCH_VALID_WTR = 	
		"UPDATE APM_LEARNED_MATCH_WTR SET DEL_FL = 'N', APM_MATCH_TYP = 'MAN', UPD_DT=SYSDATE, UPD_ID=? , LRN_DEL = ?  " +
		"WHERE   WRITER = ?  AND WRK_TTL = ?  ";
		
	public static final String INSERT_LEARNED_MATCH = "INSERT INTO APM_LEARNED_MATCH(SUPP_CODE,PFR_NA,WRK_TTL,WRK_ID,DEL_FL,APM_MATCH_TYP,CRE_ID,CRE_DT,LRN_DEL) " +
	" VALUES(?,?,?,?,'N','MAN',?,SYSDATE,?) ";
	
	public static final String INSERT_LEARNED_MATCH_WTR = "INSERT INTO APM_LEARNED_MATCH_WTR (SUPP_CODE,WRITER,WRK_TTL,WRK_ID,DEL_FL,APM_MATCH_TYP,CRE_ID,CRE_DT,LRN_DEL) " +
	" VALUES(?,?,?,?,'N','MAN',?,SYSDATE,?) ";
	
	
	public static final String GET_SAMPLE_DATES_LIST_COUNT = "SELECT COUNT(*) TOTALRECORDCOUNT FROM SAM_SUR_DT A, PTY B, PTY_NA C ";

	public static final String GET_SAMPLE_DATES_LIST = "SELECT SAM_SUR_DT_ID,  MUS_USR_ID,  ACC_NR,  CALLLETTER,  CALLLETTERSUFFIX,  MUS_USER_LOG_WK,  MUS_USER_TEST_TAPE,  DO_FL_LOC "
	+ " ,  MUS_USER_TPR_NA,  REM,  CAT,  STT_DT,  STT_TM,  END_DT,  END_TM "
	+ " FROM ( SELECT DENSE_RANK() OVER (ORDER BY A.STT_DT DESC, A.SAM_SUR_DT_ID) RN, A.SAM_SUR_DT_ID,  A.MUS_USR_ID,  B.ACC_NR,  C.NA AS CALLLETTER,  C.CAL_LTR_SUF AS CALLLETTERSUFFIX, "
	+ "  A.MUS_USER_LOG_WK,  A.MUS_USER_TEST_TAPE,  A.DO_FL_LOC,  A.MUS_USER_TPR_NA,  A.REM,  A.CAT,  TO_CHAR(A.STT_DT,  'MM/DD/YYYY') STT_DT "
	+ " 	,  TO_CHAR(A.STT_TM,  'HH24:MI:SS') STT_TM	,  TO_CHAR(A.END_DT,  'MM/DD/YYYY') END_DT,  TO_CHAR(A.END_TM,  'HH24:MI:SS') END_TM   "
	+ " 	FROM SAM_SUR_DT A, PTY B, PTY_NA C ";



	public static final String ADD_SAMPLE_SURVEY_DATE = "INSERT INTO SAM_SUR_DT (SAM_SUR_DT_ID, MUS_USR_ID, STT_DT, STT_TM, END_DT, END_TM, MUS_USER_LOG_WK, MUS_USER_TEST_TAPE, DO_FL_LOC, MUS_USER_TPR_NA, REM, CAT, CRE_ID, CRE_DT, DEL_FL) VALUES (?, ?, TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS') , TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS') , TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS') , TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS') , ?, ?, ?, ?, ?, ?, ?,SYSDATE,'N')";

	public static final String UPDATE_SAMPLE_SURVEY_DATE = "UPDATE SAM_SUR_DT SET MUS_USR_ID = ?, STT_DT = TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS'), STT_TM = TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS'), END_DT = TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS'), END_TM = TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS'), "
	+ " MUS_USER_LOG_WK = ?, MUS_USER_TEST_TAPE = ?, DO_FL_LOC = ?, MUS_USER_TPR_NA = ?, REM = ?, CAT = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE SAM_SUR_DT_ID = ?";

	
	public static final String DELETE_SAMPLE_SURVEY_DATE = "UPDATE SAM_SUR_DT SET DEL_FL = 'Y', UPD_DT = SYSDATE, UPD_ID = ? WHERE SAM_SUR_DT_ID = ?";

	public static final String GET_SAMPLE_DATE = " SELECT A.SAM_SUR_DT_ID,  A.MUS_USR_ID, D.MUS_USER_TYP_CDE, B.ACC_NR,  C.NA AS CALLLETTER,  C.CAL_LTR_SUF AS CALLLETTERSUFFIX, "
	+ " A.MUS_USER_LOG_WK,  A.MUS_USER_TEST_TAPE,  A.DO_FL_LOC,  A.MUS_USER_TPR_NA,  A.REM,  A.CAT,  TO_CHAR(A.STT_DT,  'MM/DD/YYYY') STT_DT "
	+ "	,  TO_CHAR(A.STT_TM,  'HH24:MI:SS') STT_TM	,  TO_CHAR(A.END_DT,  'MM/DD/YYYY') END_DT,  TO_CHAR(A.END_TM,  'HH24:MI:SS') END_TM "
	+ "	FROM SAM_SUR_DT A, PTY B, PTY_NA C, MUS_USER_TYP_SEL D "
	+ "	WHERE  A.DEL_FL = 'N' AND  A.MUS_USR_ID = B.PTY_ID AND A.MUS_USR_ID = C.PTY_ID(+)  AND C.PTY_NA_TYP_CDE(+) = '" + PartyNameTypeConstants.CALL_LETTERS + "' "
	+ "	AND TO_DATE(TO_CHAR(C.STT_DT(+), 'mm/dd/yyyy'), 'mm/dd/yyyy') <=  TO_DATE(TO_CHAR(A.STT_DT, 'mm/dd/yyyy'), 'mm/dd/yyyy') "
	+ "	AND TO_DATE(TO_CHAR(C.END_DT(+), 'mm/dd/yyyy'), 'mm/dd/yyyy') >=  TO_DATE(TO_CHAR(A.STT_DT, 'mm/dd/yyyy'), 'mm/dd/yyyy') "
	+ "	AND A.MUS_USR_ID = D.PTY_ID(+) "
	+ "	AND TO_DATE(TO_CHAR(D.STT_DT(+), 'mm/dd/yyyy'), 'mm/dd/yyyy') <=  TO_DATE(TO_CHAR(A.STT_DT, 'mm/dd/yyyy'), 'mm/dd/yyyy')  "
	+ "	AND TO_DATE(TO_CHAR(D.END_DT(+), 'mm/dd/yyyy'), 'mm/dd/yyyy') >=  TO_DATE(TO_CHAR(A.STT_DT, 'mm/dd/yyyy'), 'mm/dd/yyyy')  	"
	+ "	AND SAM_SUR_DT_ID = ? ";

	//public static final String ADD_SUB_SAMPLE_ELIGIBILITY = "INSERT INTO SUB_SAM_ELIG (SUB_SAM_ELIG_ID, MUS_USR_TYP_CDE, USE_TYP_CDE, EFF_STT_DT, EFF_STT_TM, EFF_END_DT, EFF_END_TM, CRE_ID, CRE_DT, DEL_FL) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE,'N')";
	//public static final String DELETE_SUB_SAMPLE_ELIGIBILITY = "UPDATE SUB_SAM_ELIG  SET DEL_FL = 'Y', UPD_DT = SYSDATE, UPD_ID = ? WHERE SUB_SAM_ELIG_ID = ?";

	public static final String DYNAMIC_INSERT_USAGE_BULK_PROCESS_BASIC = "INSERT INTO USE_BLK_PROC ";
	public static final String ADD_USAGE_BULK_PROCESS_TRIGGER_DETAILS = "INSERT INTO USE_BLK_ADJ_TRIG ( USE_BLK_ADJ_TRIG_ID, USE_BLK_PROC_ID, TRIG_ID, TRIG_NAME_CDE, ACCT_IND_CDE, ADJ_DEPT_CDE, ADJ_RSN_CDE, ADJ_MOD_CDE, CRE_ID, CRE_DT, DEL_FL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE,'N') ";
	public static final String ADD_USAGE_BULK_PROCESS_PERFORMANCE_ID = "INSERT INTO USE_BLK_PRC_PERFS ( USE_BLK_PRC_PERFS_ID, USE_BLK_PROC_ID, PERF_HDR_ID, APM_WRK_PERF_ID, CRE_ID, CRE_DT, DEL_FL) VALUES ( ?, ?, ?, ?, ?, SYSDATE, 'N')";

	public static final String ADD_SUB_SAMPLE = "INSERT INTO USE_SUB_SAM_PAR ( USE_SUB_SAM_PAR_ID, USE_TYP_CDE, MUS_USER_TYP_CDE, STT_DT, END_DT, MN_YR, CRE_ID, CRE_DT, DEL_FL) VALUES ( ?, ?, ?,  TO_Date( ?, 'MM/DD/YYYY HH24:MI:SS'),  TO_Date( ?, 'MM/DD/YYYY HH24:MI:SS'), ?,?,SYSDATE,'N')";
	public static final String END_BULK_SUB_SAMPLES_BY_SINGLE_ID = "UPDATE USE_SUB_SAM_PAR SET DEL_FL = 'Y', UPD_DT = SYSDATE, UPD_ID = ? WHERE (MUS_USER_TYP_CDE,TO_CHAR(STT_DT,'MM/DD/YYYY HH24:MI:SS'), TO_CHAR(END_DT,'MM/DD/YYYY HH24:MI:SS'))  IN (SELECT MUS_USER_TYP_CDE,TO_CHAR(STT_DT,'MM/DD/YYYY HH24:MI:SS'), TO_CHAR(END_DT,'MM/DD/YYYY HH24:MI:SS') FROM USE_SUB_SAM_PAR  WHERE USE_SUB_SAM_PAR_ID = ? AND (DEL_FL <> 'Y' OR DEL_FL IS NULL)) ";

	//SELECT USE_SUB_SAM_PAR_ID, USE_TYP_CDE, MUS_USER_TYP_CDE, STT_DT, STT_TM, END_DT, MN_YR FROM USE_SUB_SAM_PAR WHERE
	//(MUS_USER_TYP_CDE, TO_CHAR(STT_DT,'MM/DD/YYYY HH24:MI:SS'), TO_CHAR(END_DT,'MM/DD/YYYY HH24:MI:SS')) IN
	//  (SELECT  MUS_USER_TYP_CDE, STT_DT, END_DT FROM
	//	(SELECT ROWNUM RN, MUS_USER_TYP_CDE MUS_USER_TYP_CDE, STT_DT, END_DT FROM
	//		(
	//		  SELECT MUS_USER_TYP_CDE MUS_USER_TYP_CDE,TO_CHAR(STT_DT,'MM/DD/YYYY HH24:MI:SS') STT_DT, TO_CHAR(END_DT,'MM/DD/YYYY HH24:MI:SS') END_DT FROM USE_SUB_SAM_PAR A
	//		  WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL)
	//		  AND MUS_USER_TYP_CDE = 'E'
	//		  AND STT_DT <= SYSDATE AND END_DT >= SYSDATE
	//		  GROUP BY A.MUS_USER_TYP_CDE,TO_CHAR(A.STT_DT,'MM/DD/YYYY HH24:MI:SS'), TO_CHAR(A.END_DT,'MM/DD/YYYY HH24:MI:SS')
	//		  ORDER BY  TO_DATE(STT_DT,'MM/DD/YYYY HH24:MI:SS'), TO_DATE(END_DT, 'MM/DD/YYYY HH24:MI:SS'), MUS_USER_TYP_CDE
	//		)
	// ) X
	// WHERE X.RN BETWEEN 1 AND 2
	//  )
	public static final String DYNAMIC_GET_SUB_SAMPLE_PARAMETERS_LIST_COUNT_PART_1 = " SELECT COUNT(*) TOTAL_REC_COUNT FROM (  "
	+"  SELECT MUS_USER_TYP_CDE MUS_USER_TYP_CDE,TO_CHAR(STT_DT,'MM/DD/YYYY HH24:MI:SS') STT_DT, TO_CHAR(END_DT,'MM/DD/YYYY HH24:MI:SS') END_DT FROM USE_SUB_SAM_PAR A "
	+"  WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) ";
	//+ "  AND MUS_USER_TYP_CDE = 'E'  "
	//+ "  AND STT_DT <= SYSDATE AND END_DT >= SYSDATE  "
	public static final String DYNAMIC_GET_SUB_SAMPLE_PARAMETERS_LIST_COUNT_PART_2 =
	"  GROUP BY A.MUS_USER_TYP_CDE,TO_CHAR(A.STT_DT,'MM/DD/YYYY HH24:MI:SS'), TO_CHAR(A.END_DT,'MM/DD/YYYY HH24:MI:SS')"
	+"  ORDER BY  TO_DATE(STT_DT,'MM/DD/YYYY HH24:MI:SS'), TO_DATE(END_DT, 'MM/DD/YYYY HH24:MI:SS'), MUS_USER_TYP_CDE "
	+" ) ";


	//SELECT OUTA.USE_SUB_SAM_PAR_ID, OUTA.USE_TYP_CDE, OUTA.MUS_USER_TYP_CDE, TO_CHAR(OUTA.STT_DT,'MM/DD/YYYY') STT_DT, TO_CHAR(OUTA.STT_DT,'HH24:MI:SS') STT_TM , TO_CHAR(OUTA.END_DT,'MM/DD/YYYY') END_DT,TO_CHAR(OUTA.END_DT,'HH24:MI:SS') END_TM, OUTA.MN_YR, OUTB.MUS_USER_TYP_DES MUSIC_USER_TYPE_DES, OUTC.DES USE_TYPE_DES
	//FROM USE_SUB_SAM_PAR OUTA, MUS_USER_TYP OUTB, USE_TYP OUTC
	//WHERE
	//OUTA.MUS_USER_TYP_CDE = OUTB.MUS_USER_TYP_CDE AND
	//OUTA.USE_TYP_CDE = OUTC.USE_TYP_CDE AND
	//(OUTA.DEL_FL <> 'Y' OR OUTA.DEL_FL IS NULL) AND
	//(OUTB.DEL_FL <> 'Y' OR OUTB.DEL_FL IS NULL) AND
	//(OUTC.DEL_FL <> 'Y' OR OUTC.DEL_FL IS NULL) AND
	//(OUTA.MUS_USER_TYP_CDE, TO_CHAR(OUTA.STT_DT,'MM/DD/YYYY HH24:MI:SS'), TO_CHAR(OUTA.END_DT,'MM/DD/YYYY HH24:MI:SS')) IN
	//   (SELECT  MUS_USER_TYP_CDE, STT_DT, END_DT FROM
	// 	(SELECT ROWNUM RN, MUS_USER_TYP_CDE MUS_USER_TYP_CDE, STT_DT, END_DT FROM
	// 		(
	// 		  SELECT MUS_USER_TYP_CDE MUS_USER_TYP_CDE,TO_CHAR(STT_DT,'MM/DD/YYYY HH24:MI:SS') STT_DT, TO_CHAR(END_DT,'MM/DD/YYYY HH24:MI:SS') END_DT FROM USE_SUB_SAM_PAR A
	// 		  WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL)
	// 		  AND MUS_USER_TYP_CDE = 'E'
	// 		  AND STT_DT <= SYSDATE AND END_DT >= SYSDATE
	// 		  GROUP BY A.MUS_USER_TYP_CDE,TO_CHAR(A.STT_DT,'MM/DD/YYYY HH24:MI:SS'), TO_CHAR(A.END_DT,'MM/DD/YYYY HH24:MI:SS')
	// 		  ORDER BY  TO_DATE(STT_DT,'MM/DD/YYYY HH24:MI:SS'), TO_DATE(END_DT, 'MM/DD/YYYY HH24:MI:SS'), MUS_USER_TYP_CDE
	// 		)
	//  ) X
	//  WHERE X.RN BETWEEN 1 AND 5
	//   )
	//ORDER BY TO_DATE(TO_CHAR(OUTA.STT_DT,'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS'), TO_DATE(TO_CHAR(OUTA.END_DT,'MM/DD/YYYY HH24:MI:SS'), 'MM/DD/YYYY HH24:MI:SS'), OUTA.MUS_USER_TYP_CDE, OUTC.DES


	public static final String DYNAMIC_GET_SUB_SAMPLE_PARAMETERS_LIST_PART_1 = " SELECT OUTA.USE_SUB_SAM_PAR_ID, OUTA.USE_TYP_CDE, OUTA.MUS_USER_TYP_CDE, TO_CHAR(OUTA.STT_DT,'MM/DD/YYYY') STT_DT, TO_CHAR(OUTA.STT_DT,'HH24:MI:SS') STT_TM , TO_CHAR(OUTA.END_DT,'MM/DD/YYYY') END_DT,TO_CHAR(OUTA.END_DT,'HH24:MI:SS') END_TM, OUTA.MN_YR, OUTB.MUS_USER_TYP_DES MUSIC_USER_TYPE_DES, OUTC.DES USE_TYPE_DES "
+" FROM USE_SUB_SAM_PAR OUTA, MUS_USER_TYP OUTB, USE_TYP OUTC"
+" WHERE"
+" OUTA.MUS_USER_TYP_CDE = OUTB.MUS_USER_TYP_CDE AND"
+" OUTA.USE_TYP_CDE = OUTC.USE_TYP_CDE AND"
+" NVL(OUTA.DEL_FL,'N') = 'N' AND "
+" NVL(OUTB.DEL_FL,'N') = 'N' AND "
+" NVL(OUTC.DEL_FL,'N') = 'N' AND "
+" (OUTA.MUS_USER_TYP_CDE, TO_CHAR(OUTA.STT_DT,'MM/DD/YYYY HH24:MI:SS'), TO_CHAR(OUTA.END_DT,'MM/DD/YYYY HH24:MI:SS')) IN"
+" (SELECT  MUS_USER_TYP_CDE, STT_DT, END_DT FROM"
+"  	(SELECT ROWNUM RN, MUS_USER_TYP_CDE MUS_USER_TYP_CDE, TO_CHAR(STT_DT,'MM/DD/YYYY HH24:MI:SS') STT_DT, TO_CHAR(END_DT,'MM/DD/YYYY HH24:MI:SS') END_DT  FROM "
+"  		( "
+"  		  SELECT MUS_USER_TYP_CDE MUS_USER_TYP_CDE, STT_DT, END_DT FROM USE_SUB_SAM_PAR A "
+"  		  WHERE NVL(DEL_FL,'N') = 'N' ";

//+"  		  AND MUS_USER_TYP_CDE = 'E' "
//+"  		  AND STT_DT <= SYSDATE AND END_DT >= SYSDATE  "

public static final String DYNAMIC_GET_SUB_SAMPLE_PARAMETERS_LIST_PART_2 = "  		  GROUP BY A.MUS_USER_TYP_CDE, A.STT_DT, A.END_DT "
+"  		  ORDER BY  STT_DT, END_DT, MUS_USER_TYP_CDE "
+"  		) "
+"   ) X"
+"   WHERE X.RN BETWEEN ? AND ?"
+" ) "
+" ORDER BY TO_DATE(TO_CHAR(OUTA.STT_DT,'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS'), TO_DATE(TO_CHAR(OUTA.END_DT,'MM/DD/YYYY HH24:MI:SS'), 'MM/DD/YYYY HH24:MI:SS'), OUTA.MUS_USER_TYP_CDE, OUTC.DES"	;




//SELECT OUTA.USE_SUB_SAM_PAR_ID, OUTA.USE_TYP_CDE, OUTA.MUS_USER_TYP_CDE, TO_CHAR(OUTA.STT_DT,'MM/DD/YYYY') STT_DT, TO_CHAR(OUTA.STT_DT,'HH24:MI:SS') STT_TM , TO_CHAR(OUTA.END_DT,'MM/DD/YYYY') END_DT,TO_CHAR(OUTA.END_DT,'HH24:MI:SS') END_TM, OUTA.MN_YR, OUTB.MUS_USER_TYP_DES MUSIC_USER_TYPE_DES, OUTC.DES USE_TYPE_DES
//FROM USE_SUB_SAM_PAR OUTA, MUS_USER_TYP OUTB, USE_TYP OUTC
//WHERE
//OUTA.MUS_USER_TYP_CDE = OUTB.MUS_USER_TYP_CDE AND
//OUTA.USE_TYP_CDE = OUTC.USE_TYP_CDE AND
//(OUTA.DEL_FL <> 'Y' OR OUTA.DEL_FL IS NULL) AND
//(OUTB.DEL_FL <> 'Y' OR OUTB.DEL_FL IS NULL) AND
//(OUTC.DEL_FL <> 'Y' OR OUTC.DEL_FL IS NULL) AND
//(OUTA.MUS_USER_TYP_CDE, TO_CHAR(OUTA.STT_DT,'MM/DD/YYYY HH24:MI:SS'), TO_CHAR(OUTA.END_DT,'MM/DD/YYYY HH24:MI:SS')) IN
//   (SELECT  MUS_USER_TYP_CDE, TO_CHAR(STT_DT,'MM/DD/YYYY HH24:MI:SS'), TO_CHAR(END_DT,'MM/DD/YYYY HH24:MI:SS') FROM USE_SUB_SAM_PAR WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND USE_SUB_SAM_PAR_ID = 1)
//ORDER BY TO_DATE(TO_CHAR(OUTA.STT_DT,'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS'), TO_DATE(TO_CHAR(OUTA.END_DT,'MM/DD/YYYY HH24:MI:SS'), 'MM/DD/YYYY HH24:MI:SS'), OUTA.MUS_USER_TYP_CDE, OUTC.DES

	public static final String STATIC_GET_BULK_SUB_SAMPLE_BY_SINGLE_ID =
" SELECT OUTA.USE_SUB_SAM_PAR_ID, OUTA.USE_TYP_CDE, OUTA.MUS_USER_TYP_CDE, TO_CHAR(OUTA.STT_DT,'MM/DD/YYYY') STT_DT, TO_CHAR(OUTA.STT_DT,'HH24:MI:SS') STT_TM , TO_CHAR(OUTA.END_DT,'MM/DD/YYYY') END_DT,TO_CHAR(OUTA.END_DT,'HH24:MI:SS') END_TM, OUTA.MN_YR, OUTB.MUS_USER_TYP_DES MUSIC_USER_TYPE_DES, OUTC.DES USE_TYPE_DES "
+" FROM USE_SUB_SAM_PAR OUTA, MUS_USER_TYP OUTB, USE_TYP OUTC"
+" WHERE"
+" OUTA.MUS_USER_TYP_CDE = OUTB.MUS_USER_TYP_CDE AND"
+" OUTA.USE_TYP_CDE = OUTC.USE_TYP_CDE AND"
+" (OUTA.DEL_FL <> 'Y' OR OUTA.DEL_FL IS NULL) AND"
+" (OUTB.DEL_FL <> 'Y' OR OUTB.DEL_FL IS NULL) AND"
+" (OUTC.DEL_FL <> 'Y' OR OUTC.DEL_FL IS NULL) AND"
+" (OUTA.MUS_USER_TYP_CDE, TO_CHAR(OUTA.STT_DT,'MM/DD/YYYY HH24:MI:SS'), TO_CHAR(OUTA.END_DT,'MM/DD/YYYY HH24:MI:SS')) IN"
+" (SELECT  MUS_USER_TYP_CDE, TO_CHAR(STT_DT,'MM/DD/YYYY HH24:MI:SS'), TO_CHAR(END_DT,'MM/DD/YYYY HH24:MI:SS') FROM USE_SUB_SAM_PAR WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND USE_SUB_SAM_PAR_ID = ?) "
+" ORDER BY TO_DATE(TO_CHAR(OUTA.STT_DT,'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS'), TO_DATE(TO_CHAR(OUTA.END_DT,'MM/DD/YYYY HH24:MI:SS'), 'MM/DD/YYYY HH24:MI:SS'), OUTA.MUS_USER_TYP_CDE, OUTC.DES"	;

//public static final String STATIC_UPDATE_PERF_HDR_POSTING_INDICATOR = " UPDATE PERF_HDR SET PSTING_IND = ?, upd_id = ?, upd_dt = sysdate  WHERE PERF_HDR_ID = ? ";  
	
public static final String STATIC_SET_PROGRAM_PERFORMANCE_ERROR_FLAG = 
"UPDATE perf_hdr SET err_fl =  " +
"(select case when reject_cnt > 0 then 'R'  " +
"	 	when error_cnt > 0 then 'E' " +
"	   		when warning_cnt > 0 then 'W' " +
"	   		when information_cnt > 0 then 'I' " +
"	   end " +
"from (	   " +
"SELECT COUNT(CASE WHEN ewi_cat_cde IN ('G','R') then 1 end) REJECT_CNT, COUNT(CASE WHEN ewi_cat_cde = 'E' then 1 end) ERROR_CNT, COUNT(CASE WHEN ewi_cat_cde = 'W' then 1 end) WARNING_CNT, COUNT(CASE WHEN ewi_cat_cde = 'I' then 1 end) INFORMATION_CNT " +
"FROM perf_hdr_wrn A,use_err_wrn_info b WHERE A.use_ewi_cde = b.use_ewi_cde AND (A.del_fl ='N')  " +
"AND A.perf_hdr_id = ? AND A.prg_perf_ver_num = ? " +
")  " +
") " +
" WHERE perf_hdr_id = ? AND ver_num = ? ";


public static final String STATIC_SET_WORK_PERFORMANCE_ERROR_FLAG = 
"UPDATE wrk_perf SET err_fl = " +
"(select case when reject_cnt > 0 then 'R'   " +
"	 	when error_cnt > 0 then 'E'  " +
"	   		when warning_cnt > 0 then 'W'  " +
"	   		when information_cnt > 0 then 'I'  " +
"	   end  " +
"from (	 " +
"SELECT COUNT(CASE WHEN ewi_cat_cde IN ('G','R') then 1 end) REJECT_CNT, COUNT(CASE WHEN ewi_cat_cde = 'E' then 1 end) ERROR_CNT, COUNT(CASE WHEN ewi_cat_cde = 'W' then 1 end) WARNING_CNT, COUNT(CASE WHEN ewi_cat_cde = 'I' then 1 end) INFORMATION_CNT  " +
"FROM wrk_perf_wrn A,use_err_wrn_info b WHERE A.use_ewi_cde = b.use_ewi_cde AND (A.del_fl ='N')   " +
"AND A.APM_WRK_PERF_ID = ? AND A.wrk_perf_ver_num = ?  " +
")   " +
")  " +
" WHERE APM_WRK_PERF_ID = ? AND ver_num = ?  ";


 public static final String DYNAMIC_GET_FILTERED_USE_TYPES = "SELECT USE_TYP_CDE FROM USE_TYP WHERE (DEL_FL <>'Y' OR DEL_FL IS NULL) ";

 public static final String GET_PROGRAM_PERFORMANCE_TOTALS = " SELECT COUNT(*) TOT_WRK_PERF_CNT, SUM(PLAY_CNT) TOT_PLAY_COUNT, MAX(PLAY_CNT) MAX_PLAY_COUNT FROM wrk_perf WHERE "
+ " (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND (curr_ver_flag = 'Y' OR CURR_VER_FLAG IS NULL)"
+ " AND perf_hdr_id = ? "	;

 public static final String UPDATE_PROGRAM_PERFORMANCE_TOTALS_INTERNET = " UPDATE perf_hdr SET WRK_PERF_CNT = (SELECT COUNT(*) FROM wrk_perf WHERE "
+ " (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND (curr_ver_flag = 'Y' OR CURR_VER_FLAG IS NULL)"
+ " AND perf_hdr_id = ?), "
+ " CUM_NUM_OF_PLAYS = (SELECT GREATEST(nvl((SELECT SUM(PLAY_CNT) MAX_PLAY_COUNT FROM wrk_perf WHERE "
+ " (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND (curr_ver_flag = 'Y' OR CURR_VER_FLAG IS NULL)"
+ " AND perf_hdr_id = ?),0), nvl((SELECT CUM_NUM_OF_PLAYS FROM perf_hdr WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND (curr_ver_flag = 'Y' OR CURR_VER_FLAG IS NULL)"
+ " AND perf_hdr_id = ?),0)) FROM dual),"
+ " MAX_NO_OF_PLAYS = (SELECT GREATEST(nvl((SELECT MAX(PLAY_CNT) MAX_PLAY_COUNT FROM wrk_perf WHERE "
+ " (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND (curr_ver_flag = 'Y' OR CURR_VER_FLAG IS NULL)"
+ " AND perf_hdr_id = ?),0),nvl((SELECT MAX_NO_OF_PLAYS FROM perf_hdr WHERE (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND (curr_ver_flag = 'Y' OR CURR_VER_FLAG IS NULL)"
+ " AND perf_hdr_id = ?),0)) FROM dual)"
+ " WHERE perf_hdr_id = ? AND (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND (curr_ver_flag = 'Y' OR CURR_VER_FLAG IS NULL) ";

//public static final String UPDATE_PROGRAM_PERFORMANCE_TOTALS = " UPDATE perf_hdr SET WRK_PERF_CNT = (SELECT COUNT(*) FROM wrk_perf WHERE "
//+ " (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND (curr_ver_flag = 'Y' OR CURR_VER_FLAG IS NULL)"
//+ " AND perf_hdr_id = ?), "
//+ " CUM_NUM_OF_PLAYS = (SELECT SUM(PLAY_CNT) TOT_PLAY_COUNT FROM wrk_perf WHERE "
//+ " (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND (curr_ver_flag = 'Y' OR CURR_VER_FLAG IS NULL)"
//+ " AND perf_hdr_id = ?),"
//+ " MAX_NO_OF_PLAYS = (SELECT MAX(PLAY_CNT) TOT_PLAY_COUNT FROM wrk_perf WHERE "
//+ " (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND (curr_ver_flag = 'Y' OR CURR_VER_FLAG IS NULL)"
//+ " AND perf_hdr_id = ?) "
//+ " WHERE perf_hdr_id = ? AND (DEL_FL <> 'Y' OR DEL_FL IS NULL) AND (curr_ver_flag = 'Y' OR CURR_VER_FLAG IS NULL) ";

public static final String UPDATE_PROGRAM_PERFORMANCE_TOTALS_INTERNET_AUDIO_VISUAL =
    "UPDATE perf_hdr " +
    "   SET (wrk_perf_cnt, tot_pt_cnt) = " +
    "          (SELECT COUNT (*), SUM (pt_val) " +
    "             FROM wrk_perf " +
    "            WHERE (del_fl <> 'Y' OR del_fl IS NULL) " +
    "              AND (curr_ver_flag = 'Y' OR curr_ver_flag IS NULL) " +
    "              AND perf_hdr_id = ? ) " +
    " WHERE perf_hdr_id = ? " +
    "   AND (del_fl <> 'Y' OR del_fl IS NULL) " +
    "   AND (curr_ver_flag = 'Y' OR curr_ver_flag IS NULL) ";

public static final String UPDATE_PROGRAM_PERFORMANCE_TOTALS =
    "UPDATE perf_hdr " +
    "   SET (wrk_perf_cnt, cum_num_of_plays, max_no_of_plays, tot_pt_cnt) = " +
    "          (SELECT COUNT (*), SUM (play_cnt), MAX (play_cnt), SUM (pt_val) " +
    "             FROM wrk_perf " +
    "            WHERE (del_fl <> 'Y' OR del_fl IS NULL) " +
    "              AND (curr_ver_flag = 'Y' OR curr_ver_flag IS NULL) " +
    "              AND perf_hdr_id = ? ) " +
    " WHERE perf_hdr_id = ? " +
    "   AND (del_fl <> 'Y' OR del_fl IS NULL) " +
    "   AND (curr_ver_flag = 'Y' OR curr_ver_flag IS NULL) ";

 public static final String UPDATE_PROXY_RESTRUCTURING_INDICATOR = " UPDATE PRX_SEL SET PRX_RESTRU_IND = NVL((SELECT 'Y' FROM DUAL WHERE "
+  " EXISTS ((SELECT * FROM (SELECT mus_user_typ_cde FROM prx_sel_mus_usr_id, mus_user_typ_sel WHERE "
+  " prx_src_mus_usr_id = pty_id"
+  " AND ( prx_sel_mus_usr_id.DEL_FL IS NULL OR prx_sel_mus_usr_id.DEL_FL <> 'Y')"
+  " AND ( mus_user_typ_sel.DEL_FL IS NULL OR mus_user_typ_sel.DEL_FL <> 'Y')"
+  " AND prx_sel_id = ?"
+  " UNION"
+  " SELECT prx_src_mus_usr_typ FROM prx_sel_mus_usr_typ "
+  " WHERE prx_sel_id = ?"
+  " ) SOURCE_MUS WHERE SOURCE_MUS.MUS_USER_TYP_CDE NOT IN  ('"+ MusicUserTypeConstants.GENERAL_RINGTONES +"', '" + MusicUserTypeConstants.GENERAL_INTERNET_STREAMING + "', '" + MusicUserTypeConstants.GENERAL_INTERNET_INTERACTIVE_SELF_REPRESENTING + "', '" + MusicUserTypeConstants.GENERAL_INTERNET_INTERACTIVE_NON_SELF_REPRESENTING + "', '" + MusicUserTypeConstants.GENERAL_LIVE_POP_CONCERTS +"')"
+  " )"
+  " ) AND EXISTS"
+  " ("
+  " SELECT mus_user_typ_cde,prx_tgt_mus_usr_id FROM prx_sel,mus_user_typ_sel "
+  " WHERE"
+  " prx_tgt_mus_usr_id = pty_id AND mus_user_typ_cde IN ('"+ MusicUserTypeConstants.GENERAL_RINGTONES +"', '" + MusicUserTypeConstants.GENERAL_INTERNET_STREAMING + "', '" + MusicUserTypeConstants.GENERAL_INTERNET_INTERACTIVE_SELF_REPRESENTING + "', '" + MusicUserTypeConstants.GENERAL_INTERNET_INTERACTIVE_NON_SELF_REPRESENTING + "', '" + MusicUserTypeConstants.GENERAL_LIVE_POP_CONCERTS + "')"
+  " AND prx_sel_id = ?"
+  " AND ( mus_user_typ_sel.DEL_FL IS NULL OR mus_user_typ_sel.DEL_FL <> 'Y')"
+  " )),'N') WHERE PRX_SEL_ID = ? ";

	/*Iteration II end*/

	//public static final String GET_ALL_WORK_PERFORMANCE_SHARES = "SELECT WRK_PERF_SHR_ID, N1.NA || DECODE(N1.FST_NA,NULL,'',', ') || N1.FST_NA AS SHR_OWN, W.SHR_OWN_PTY_NA_ID SHR_OWN_PTY_NA_ID, N1.PTY_ID SHR_OWN_PTY_ID, ENT_SHR, PAY_IND, NO_PAY_RSN, WRK_PERF_USE_AMT, SUR_MEDIA_LIC_IN_EFF, UNSUR_MEDIA_LIC_IN_EFF, N2.NA || DECODE(N2.FST_NA,NULL,'',', ') || N2.FST_NA AS PAY_OWN, N2.PTY_NA_ID AS PAY_OWN_PTY_NA_ID, N2.PTY_ID AS PAY_OWN_PTY_ID, ROL_TYP_CDE, FINAL_CR, W.SOC_CDE, SOCFORDES.DES SOC_DES, FMPD_IND FROM WRK_PERF_SHR W, PTY_NA N1, PTY_NA N2,SOC SOCFORDES WHERE W.SHR_OWN_PTY_NA_ID = N1.PTY_NA_ID(+) AND W.PAYBL_OWN_PTY_NA_ID = N2.PTY_NA_ID(+) AND W.SOC_CDE = SOCFORDES.SOC_CDE(+) AND NVL(W.DEL_FL,'N') = 'N' AND APM_WRK_PERF_ID = ?  ";
	
	/*public static final String GET_ALL_WORK_PERFORMANCE_SHARES = "SELECT WRK_PERF_SHR_ID, " + 
	//N1.NA || DECODE(N1.FST_NA,NULL,'',', ') || N1.FST_NA
    //	Based on Defect 1029 @PREP_AT
	//" trim(n1.SAL || ' ' || n1.NA || DECODE(n1.FST_NA, NULL, '', ', ' || n1.FST_NA ) || DECODE(n1.MID_INIT, NULL, '', ' ' || n1.MID_INIT ) || ' ' || nvl(n1.SUF_OTH,n1.SUF)) " +
	  " trim(                 n1.NA || DECODE(n1.FST_NA, NULL, '', ', ' || n1.FST_NA ) || DECODE(n1.MID_INIT, NULL, '', ' ' || n1.MID_INIT ) || ' ' || nvl(n1.SUF_OTH,n1.SUF)) " +	
	" AS SHR_OWN, W.SHR_OWN_PTY_NA_ID SHR_OWN_PTY_NA_ID, N1.PTY_ID SHR_OWN_PTY_ID, ENT_SHR, PAY_IND, NO_PAY_RSN, WRK_PERF_USE_AMT, SUR_MEDIA_LIC_IN_EFF, UNSUR_MEDIA_LIC_IN_EFF, " + 
	//N2.NA || DECODE(N2.FST_NA,NULL,'',', ') || N2.FST_NA
   //	Based on Defect 1029 @PREP_AT
	//" trim(n2.SAL || ' ' || n2.NA || DECODE(n2.FST_NA, NULL, '', ', ' || n2.FST_NA ) || DECODE(n2.MID_INIT, NULL, '', ' ' || n2.MID_INIT ) || ' ' || nvl(n2.SUF_OTH,n2.SUF)) " +
	  " trim(                 n2.NA || DECODE(n2.FST_NA, NULL, '', ', ' || n2.FST_NA ) || DECODE(n2.MID_INIT, NULL, '', ' ' || n2.MID_INIT ) || ' ' || nvl(n2.SUF_OTH,n2.SUF)) " +	
	" AS PAY_OWN, N2.PTY_NA_ID AS PAY_OWN_PTY_NA_ID, N2.PTY_ID AS PAY_OWN_PTY_ID, ROL_TYP_CDE, FINAL_CR, W.SOC_CDE, SOCFORDES.DES SOC_DES, FMPD_IND FROM WRK_PERF_SHR W, PTY_NA N1, PTY_NA N2,SOC SOCFORDES WHERE W.SHR_OWN_PTY_NA_ID = N1.PTY_NA_ID(+) AND W.PAYBL_OWN_PTY_NA_ID = N2.PTY_NA_ID(+) AND W.SOC_CDE = SOCFORDES.SOC_CDE(+) AND NVL(W.DEL_FL,'N') = 'N' AND APM_WRK_PERF_ID = ?  ";
	*/
    
	
	
	
	
	// Manoj 
	
	public static final String VALIDATE_MUSIC_USER_ID_PREP = " SELECT COUNT(a.pty_id) FROM pty a, perf_hdr b"   +
" WHERE a.pty_id = b.LGY_MUS_USER_ID"   +
" AND b.LGY_MUS_USER_ID_TYPE = 'PREP' "   +
" AND b.perf_hdr_id = ? "   +
" AND b.VER_NUM = 0 "   +
" AND a.del_fl = 'N' "   +
" AND b.del_fl = 'N' "  ;

	public static final String VALIDATE_MUSIC_USER_ID_CALL = " SELECT A.PTY_ID"   +
" FROM   PTY_NA A,PTY B"   +
" WHERE CONCAT(NA, CAL_LTR_SUF) = ?"   +
" AND   PTY_NA_TYP_CDE='" + PartyNameTypeConstants.CALL_LETTERS + "'"   +
" AND	  A.PTY_ID = B.PTY_ID"   +
" AND   pgmSttDate between STT_DT and end_dt "   +
//" AND   pgmSttDate >= STT_DT"   +
//" AND   pgmSttDate <= END_DT"   +
" AND   A.DEL_FL = 'N'"   +
" AND   B.DEL_FL = 'N';"  ;


	public static final String GET_IF_WORK_UNID_OR_HAS_UNID_SHARES =
		"		SELECT 	 count(WRK_ID) unid_work_count " +
		"		FROM	 WRK " +
		"		WHERE	 WRK_ID = ? " +
		"		AND		 NVL(SUR_WRK_IND,'N')  = 'N' " +
		"		AND		 NVL(FST_PERF_VER,'N') = 'N' " +
		"		AND		 DEL_FL  = 'N' " +
		"		AND		 (UNID_WRK_IND = 'Y' OR  EXISTS (SELECT 1 " +
		"							   	 	 			  FROM  ENTMT " +
		"												  WHERE WRK.WRK_ID = ENTMT.WRK_ID " +
		"												  AND   (ENTMT.PTY_NA_ID IS NULL OR ENTMT.PTY_NA_ID = 0) " +
		"                                                 AND TRUNC (TO_DATE (nvl(?,to_char(ENTMT.stt_dt,'mm/dd/yyyy')),'mm/dd/yyyy')) BETWEEN ENTMT.stt_dt AND  NVL(ENTMT.END_DT,TO_DATE('12/31/9999','MM/DD/YYYY'))  " +
		"										  		  AND	ENTMT.ENTMT_STA_IND = 'Y' " +
		"												  AND   ENTMT.DEL_FL = 'N') " +
		"				) ";

	public static final String VLDN_LKUPS_US_RULE_GRP_MBRS_ALL = 
		"SELECT mus_user_typ_cde, mus_user_typ_cde code2 FROM mvw_rule_grp_mbr WHERE rule_grp_cde = '"+ UsageConstants.US_RULE_GRP_ALL + "'";
	public static final String VLDN_LKUPS_US_RULE_GRP_MBRS_DEFAULT_MUSIC_USER = 
		"SELECT mus_user_typ_cde, mus_user_typ_cde code2 FROM mvw_rule_grp_mbr WHERE rule_grp_cde = '"+ UsageConstants.US_RULE_GRP_DEFAULT_MUSIC_USER +"' ";
	public static final String VLDN_LKUPS_US_RULE_GRP_MBRS_DEFAULT_TV = 
		"SELECT mus_user_typ_cde, mus_user_typ_cde code2 FROM mvw_rule_grp_mbr WHERE rule_grp_cde = '"+ UsageConstants.US_RULE_GRP_DEFAULT_TV +"' ";
	public static final String VLDN_LKUPS_US_RULE_GRP_MBRS_TV_NETWORK =  	
		"SELECT mus_user_typ_cde, mus_user_typ_cde code2 FROM mvw_rule_grp_mbr WHERE rule_grp_cde = '"+ UsageConstants.US_RULE_GRP_TV_NETWORK +"' ";
	public static final String VLDN_LKUPS_US_RULE_GRP_MBRS_TV_SECONDARY_NETWORK =  
		"SELECT mus_user_typ_cde, mus_user_typ_cde code2 FROM mvw_rule_grp_mbr WHERE rule_grp_cde = '"+ UsageConstants.US_RULE_GRP_TV_SECONDARY_NETWORK +"' ";
	public static final String VLDN_LKUPS_US_RULE_GRP_MBRS_TV_PER_PROGRAM =  
		"SELECT mus_user_typ_cde, mus_user_typ_cde code2 FROM mvw_rule_grp_mbr WHERE rule_grp_cde = '"+ UsageConstants.US_RULE_GRP_TV_PER_PROGRAM +"' ";
	public static final String VLDN_LKUPS_US_RULE_GRP_MBRS_TV_PAY_PER_VIEW =   
		"SELECT mus_user_typ_cde, mus_user_typ_cde code2 FROM mvw_rule_grp_mbr WHERE rule_grp_cde = '"+ UsageConstants.US_RULE_GRP_TV_PAY_PER_VIEW +"' ";
	public static final String VLDN_LKUPS_US_RULE_GRP_MBRS_LIVE_POP_CONCERTS =  
		"SELECT mus_user_typ_cde, mus_user_typ_cde code2 FROM mvw_rule_grp_mbr WHERE rule_grp_cde = '"+ UsageConstants.US_RULE_GRP_LIVE_POP_CONCERTS +"' ";
	public static final String VLDN_LKUPS_US_RULE_GRP_MBRS_INTERNET_STREAMING =  
		"SELECT mus_user_typ_cde, mus_user_typ_cde code2 FROM mvw_rule_grp_mbr WHERE rule_grp_cde = '"+ UsageConstants.US_RULE_GRP_INTERNET_STREAMING +"' ";
	public static final String VLDN_LKUPS_US_RULE_GRP_MBRS_INTERACTIVE =  
		"SELECT mus_user_typ_cde, mus_user_typ_cde code2 FROM mvw_rule_grp_mbr WHERE rule_grp_cde = '"+ UsageConstants.US_RULE_GRP_INTERACTIVE +"' ";
	public static final String VLDN_LKUPS_US_RULE_GRP_MBRS_SRE =  
		"SELECT mus_user_typ_cde, mus_user_typ_cde code2 FROM mvw_rule_grp_mbr WHERE rule_grp_cde = '"+ UsageConstants.US_RULE_GRP_SRE +"' ";
	public static final String VLDN_LKUPS_US_RULE_GRP_MBRS_GENERAL_BACKGROUND =  
		"SELECT mus_user_typ_cde, mus_user_typ_cde code2 FROM mvw_rule_grp_mbr WHERE rule_grp_cde = '"+ UsageConstants.US_RULE_GRP_GENERAL_BACKGROUND +"' ";
	public static final String VLDN_LKUPS_US_RULE_GRP_MBRS_FOREGROUND =  
		"SELECT mus_user_typ_cde, mus_user_typ_cde code2 FROM mvw_rule_grp_mbr WHERE rule_grp_cde = '"+ UsageConstants.US_RULE_GRP_FOREGROUND +"' ";
	public static final String VLDN_LKUPS_US_RULE_GRP_MBRS_INTERNATIONAL_INCOMING =  
		"SELECT mus_user_typ_cde, mus_user_typ_cde code2 FROM mvw_rule_grp_mbr WHERE rule_grp_cde = '"+ UsageConstants.US_RULE_GRP_INTERNATIONAL_INCOMING +"' ";

	public static final String VLDN_LKUPS_USE_TYPES_ALL=
		"select use_typ_cde,use_typ_cde code2 from mvw_use_typ";
	public static final String VLDN_LKUPS_USE_TYPES_IS_FEATURED=
		"select use_typ_cde,use_typ_cde code2 from mvw_use_typ where is_feature='Y'";
	public static final String VLDN_LKUPS_USE_TYPES_IS_LIBRARY=
		"select use_typ_cde,use_typ_cde code2 from mvw_use_typ where is_Library='Y'";
	public static final String VLDN_LKUPS_USE_TYPES_IS_MEDLEY=
		"select use_typ_cde,use_typ_cde code2 from mvw_use_typ where is_medley='Y'";
	public static final String VLDN_LKUPS_USE_TYPES_IS_SRE=
		"select use_typ_cde,use_typ_cde code2 from mvw_use_typ where is_SRE='Y'";
	public static final String VLDN_LKUPS_USE_TYPES_IS_TVPP=
		"select use_typ_cde,use_typ_cde code2 from mvw_use_typ where is_tvpp='Y'";
	
	
	
	
	

	public static final String UPDATE_AUDIT = " UPDATE usg_audt SET rem=?, usg_audt_rsn_cde = ?, inq_id = ?, upd_id=?, upd_dt=sysdate WHERE usg_audt_id = ? ";

	public static final String DELETE_PENDING_AUDIT = " DELETE FROM audt_pend WHERE grp_id = ? AND mod_id = "+Constants.USAGE_MODULE+" AND pend_ind = 'Y' AND del_fl = 'N' ";


	public static final String USG_AUDIT_HISTORY_COUNT_START = " SELECT COUNT (*) " +
	"  FROM usg_audt a " + 
	" WHERE a.perf_hdr_id = ? AND a.del_fl = 'N' "; 

	public static final String USG_AUDIT_HISTORY_COUNT_END = " AND a.usg_audt_id NOT IN (SELECT grp_id FROM audt_pend WHERE mod_id = " + Constants.USAGE_MODULE + " AND pend_ind = 'Y' AND del_fl = 'N') ";

	public static final String SELECT_PENDING_USG_AUDIT_COUNT_START = "SELECT COUNT (*) " +
	"  FROM usg_audt a, usg_audt_hst b, audt_pend ap " + 
	" WHERE ap.usr_id = ? " + 
	"   AND ap.mod_id = '" + Constants.USAGE_MODULE +  "' " +
	"   AND ap.pend_ind = 'Y' " + 
	"   AND ap.del_fl = 'N' " + 
	"   AND ap.grp_id = a.usg_audt_id " + 
	"   AND a.usg_audt_id = b.usg_audt_id " + 
	"   AND a.del_fl = 'N' " + 
	"   AND b.del_fl = 'N' ";

		
	public static final String USG_AUDIT_LIST_START = 	
	"    SELECT " +
	"         a.usg_audt_id, a.cre_id, TO_CHAR (a.cre_dt, 'MM/DD/YYYY HH24:MI:SS') cre_dt, " +
	"         b.usg_fld_actn_cde, c.des  usg_fld_actn_desc, b.old_val, b.new_val, " +
	"         d.usg_audt_rsn_cde, d.des rsn_desc, a.REM remarks, a.inq_id, " +
	"         TRIM ((DECODE (e.fname, '', '', (e.fname || ' ')) || DECODE (e.mid_na, '', '', (e.mid_na || ' ')) || DECODE (e.lname, '', '', (e.lname || ' ')) )) user_name, "+
	"         a.perf_hdr_id, a.APM_WRK_PERF_ID, a.wrk_perf_shr_id, a.use_blk_proc_id, a.bat_id, a.prx_sel_id " +
	"    FROM usg_audt a, " + 
	"         usg_audt_hst b, " + 
	"         usg_fld_actn c, " + 
	"         usg_audt_rsn d, " +
	"		  user_pro e, ";

	public static final String USG_AUDIT_LIST_END = 
	"     AND a.del_fl = 'N' " + 
	"     AND a.usg_audt_id = b.usg_audt_id " + 
	"     AND b.usg_fld_actn_cde = c.usg_fld_actn_cde(+) " + 
	"     AND a.usg_audt_rsn_cde = d.usg_audt_rsn_cde(+) " + 
	"     AND b.del_fl = 'N' " + 
	"     AND c.del_fl = 'N' " + 
	"     AND lower(a.cre_id) = lower(e.usr_id(+)) " +
	" ORDER BY a.cre_dt DESC, a.usg_audt_id DESC " ;

	public static final String USG_AUDIT_HISTORY_DYNAMIC_START = 
	" (SELECT a.usg_audt_id, ROW_NUMBER () OVER (ORDER BY a.cre_dt DESC) rn " +
	" FROM usg_audt a " + 
	" WHERE a.perf_hdr_id = ? AND a.del_fl = 'N' ";

	public static final String USG_AUDIT_HISTORY_DYNAMIC_END =	
	" AND a.usg_audt_id not in (SELECT grp_id FROM audt_pend WHERE mod_id = '" + Constants.USAGE_MODULE + "' AND pend_ind = 'Y' AND del_fl = 'N')) t1 " +
	" WHERE (rn >= ? AND rn < ?) " + 
	" AND a.usg_audt_id = t1.usg_audt_id " ;

	public static final String USG_AUDIT_PENDING_DYNAMIC_START = 
	" (SELECT grp_id, ROW_NUMBER () OVER (ORDER BY cre_dt DESC) rn " + 
	" FROM audt_pend " + 
	" WHERE usr_id = ? " + 
	" AND mod_id =  '" + Constants.USAGE_MODULE + "'  " + 
	" AND pend_ind = 'Y' " + 
	" AND del_fl = 'N') t1 " +
	" WHERE (rn >= ? AND rn < ?) " + 
	" AND a.usg_audt_id = t1.grp_id ";
	
//	public static final String STATIC_INSERT_AUDIT_HISTORY = "INSERT INTO USG_AUDT_HST (USG_AUDT_HST_ID, USG_AUDT_ID, USG_FLD_ACTN_CDE, OLD_VAL, NEW_VAL, DEL_FL, CRE_ID, CRE_DT) VALUES(?, ?, ?, ?, ?, 'N', ?, SYSDATE) ";	

	//SOME ORACLE INTERNAL BUG UNABLE TO CREATE PARENT CHILD RECORDS BEYOND 250 giving parent key know found violation
//	public static final String STATIC_INSERT_AUDIT_N_HISTORY_FOR_WRK_PERFS = 
//	"INSERT ALL  " +
//	"INTO usg_audt (usg_audt_id, perf_hdr_id, APM_WRK_PERF_ID, wrk_perf_shr_id, use_blk_proc_id, bat_id, prx_sel_id, inq_id, usg_audt_rsn_cde, rem, del_fl, cre_id, cre_dt) VALUES (usg_audt_id.NEXTVAL, perf_hdr_id, APM_WRK_PERF_ID, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'N', audt_user_id, upd_dt_time)  " +
//	"INTO usg_audt_hst (usg_audt_hst_id, usg_audt_id, usg_fld_actn_cde, old_val, new_val, del_fl, cre_id, cre_dt) VALUES (usg_audt_hst_id.NEXTVAL, usg_audt_id.CURRVAL, usg_fld_actn_cde, oldvalue, NULL, 'N', audt_user_id, upd_dt_time)  " +
//	"SELECT ? audt_user_id, perf_hdr_id, APM_WRK_PERF_ID, APM_WRK_PERF_ID oldvalue, ? usg_fld_actn_cde, to_date(?,'MM/DD/YYYY hh24:mi:ss') upd_dt_time FROM pprep.wrk_perf a WHERE a.del_fl = 'N' AND a.perf_hdr_id = ? " ;

	
	
	public static final String STATIC_INSERT_AUDIT_HISTORY_FOR_ALL_WRK_PERFS =
	"INSERT  " +
	"INTO usg_audt_hst (usg_audt_hst_id, usg_audt_id, usg_fld_actn_cde, del_fl, cre_id, cre_dt)  " +
	"SELECT usg_audt_hst_id.NEXTVAL, b.usg_audt_id baudtid,  ?  usgfldactncde, 'N', ? audtuserid, to_date(? ,'MM/DD/YYYY hh24:mi:ss') upddttime  " +
	"FROM pprep.wrk_perf a inner join pprep.usg_audt b on (A.PERF_HDR_ID = b.perf_hdr_id and a.APM_WRK_PERF_ID = b.APM_WRK_PERF_ID) WHERE a.del_fl = 'N' AND a.perf_hdr_id = ?  " ;



	public static final String UPDATE_WRK_PERF_LIST_SUPP_CODE = "UPDATE APM_WRK_PERF SET SUPP_CODE = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE APM_PERF_HDR_ID = ? AND DEL_FL = 'N'";
	
	public static final String GET_ACTIVE_SURVEY_YEAR_QTR = "SELECT TGTSURVYEARQTR  FROM APM_SURV_DATE WHERE ACTIVE_FL = 'Y' AND DEL_FL = 'N' "; 

	public static final String GET_ACTIVE_LEARNED_MATCH = "SELECT WRK_ID FROM APM_LEARNED_MATCH WHERE SUPP_CODE = ? AND WRK_TTL = ? AND PFR_NA = ? AND  DEL_FL = 'N' AND LRN_DEL = 'N' ";
	
	public static final String GET_ACTIVE_LEARNED_MATCH_WTR = "SELECT WRK_ID FROM APM_LEARNED_MATCH_WTR WHERE SUPP_CODE = ? AND WRK_TTL = ? AND WRITER = ? AND  DEL_FL = 'N' AND LRN_DEL = 'N' ";
	
	public static final String GET_APM_LEARNED_MATCH_LIST_START =	
	/*"SELECT SUPP_CODE, " +
	"       PFR_NA, " +
	"       WRK_TTL, " +
	"       WRK_ID, LRN_DEL, MULT_WRK_ID, " +
	"       TOTALROWS " +
	"  FROM (  SELECT A.SUPP_CODE SUPP_CODE, " +
	"                 PFR_NA, " +
	"                 WRK_TTL, " +
	"                 A.WRK_ID, A.LRN_DEL, A.MULT_WRK_ID, " +
	"                 ROW_NUMBER () OVER (ORDER BY A.WRK_TTL, A.PFR_NA) RN, " +
	"                 COUNT (*) OVER () TOTALROWS " +
	"            FROM APM_LEARNED_MATCH A " +
	"           WHERE A.DEL_FL = 'N' ";*/
		
		"SELECT * " +
		"  FROM (SELECT LM_TYP,  SUPP_CODE, " +
		"               PFR_NA, " +
		"               WRITER, " +
		"               WRK_TTL, " +
		"               WRK_ID, " +
		"               LRN_DEL, " +
		"               MULT_WRK_ID,  CLONE_CNT, " +
		"               ROW_NUMBER () OVER (ORDER BY WRK_TTL) RN, " +
		"               COUNT (*) OVER () TOTALROWS " +
		"          FROM (  SELECT  'PFR' LM_TYP,  A.SUPP_CODE SUPP_CODE, " +
		"                         A.PFR_NA, " +
		"                         NULL WRITER, " +
		"                         A.WRK_TTL, " +
		"                         A.WRK_ID, " +
		"                         A.LRN_DEL, " +
		"                         A.MULT_WRK_ID, NVL(A.CLONE_CNT,1) CLONE_CNT " +
		"                         FROM APM_LEARNED_MATCH A " +
		"                   WHERE A.DEL_FL = 'N' " ;

	public static final String GET_APM_LEARNED_MATCH_WRITER_LIST_START =	
		" UNION ALL " +
		"                  SELECT  'WTR' LM_TYP,  A.SUPP_CODE SUPP_CODE, " +
		"                         NULL PFR_NA, " +
		"                         A.WRITER, " +
		"                         A.WRK_TTL, " +
		"                         A.WRK_ID, " +
		"                         A.LRN_DEL, " +
		"                         A.MULT_WRK_ID, NVL(A.CLONE_CNT,1) CLONE_CNT " +
		"                    FROM APM_LEARNED_MATCH_WTR A " +
		"                   WHERE A.DEL_FL = 'N' " ;
	
	public static final String GET_APM_LEARNED_MATCH_MAIN_END =	
		")) " +
		" WHERE RN BETWEEN ? AND ? " ;


			
		

	
	
	
	
	
	
	
	
	
	public static final String GET_APM_LEARNED_MATCH_LIST_END =
/*	"        GROUP BY SUPP_CODE, " +
	"                 A.PFR_NA, " +
	"                 A.WRK_TTL, " +
	"                 A.WRK_ID, A.LRN_DEL, A.MULT_WRK_ID) " +
	" WHERE RN BETWEEN ? AND ? "; */
		" GROUP BY SUPP_CODE, " +
		"                         A.PFR_NA, " +
		"                         A.WRK_TTL, " +
		"                         A.WRK_ID, " +
		"                         A.LRN_DEL, " +
		"                         A.MULT_WRK_ID, A.CLONE_CNT " ;
	
	public static final String GET_APM_LEARNED_MATCH_WRITER_LIST_END =
		" GROUP BY SUPP_CODE, " +
		"                         A.WRITER, " +
		"                         A.WRK_TTL, " +
		"                         A.WRK_ID, " +
		"                         A.LRN_DEL, " +
		"                         A.MULT_WRK_ID,  A.CLONE_CNT  " 
;

	
	public static final String GET_APM_LEARNED_MATCH_START =	
		"SELECT A.SUPP_CODE, " +
		"       A.PFR_NA, NULL WRITER," +
		"       A.WRK_TTL, " +
		"       A.WRK_ID, A.LRN_DEL, A.APM_MATCH_TYP, A.DEL_FL, A.CLONE_CNT FROM APM_LEARNED_MATCH A WHERE 1=1 ";
	
	public static final String GET_APM_LEARNED_MATCH_WTR_START =	
		"SELECT A.SUPP_CODE, " +
		"       NULL PFR_NA, A.WRITER, " +
		"       A.WRK_TTL, " +
		"       A.WRK_ID, A.LRN_DEL, A.APM_MATCH_TYP, A.DEL_FL, A.CLONE_CNT FROM APM_LEARNED_MATCH_WTR A WHERE 1=1 ";
	
	
	public static final String GET_ORIG_WRK_APM_LEARNED_MATCH_START =	
		"SELECT A.SUPP_CODE, " +
		"       A.PFR_NA, NULL WRITER," +
		"       A.WRK_TTL, " +
		"       A.WRK_ID, A.LRN_DEL, A.APM_MATCH_TYP, A.DEL_FL, NVL(A.CLONE_CNT,1) CLONE_CNT FROM APM_LEARNED_MATCH A WHERE A.DEL_FL = 'N' ";
	
	public static final String GET_ORIG_WRK_APM_LEARNED_MATCH_WTR_START =	
		"SELECT A.SUPP_CODE, " +
		"       NULL PFR_NA, A.WRITER, " +
		"       A.WRK_TTL, " +
		"       A.WRK_ID, A.LRN_DEL, A.APM_MATCH_TYP, A.DEL_FL, NVL(A.CLONE_CNT,1) CLONE_CNT FROM APM_LEARNED_MATCH_WTR A WHERE A.DEL_FL = 'N' ";
	

	
	public static final String GET_APM_PERF_BLK_REQUEST =	
		"SELECT   A.APM_WRK_PERF_ID,  " +
		"         A.SUPP_CODE,  " +
		"         A.PFR_NA,  " +
		"         A.WRK_TTL, " +
		"         A.WRITER,  " +
		"         A.WRK_ID  " +
		"    FROM PAPM.APM_WRK_PERF A  " +
		"   WHERE     A.DEL_FL = 'N'  " +
		"         AND A.APM_MATCH_TYP = 'MAN'  " +
		"         AND A.MAN_MATCH_DT >  TRUNC (SYSDATE) " +
		"		  AND (A.MULT_WRK_ID IS NOT NULL OR A.WRK_ID <> ? )";	
	
	
	public static final String GET_APM_PERF_BLK_REQUEST_CATALOG =	
			"SELECT   A.APM_CATALOG_ID,  " +
			"         A.SUPP_CODE,  " +
			"         A.PFR_NA,  " +
			"         A.WRK_TTL, " +
			"         A.WRITER,  " +
			"         A.WRK_ID  " +
			"    FROM PAPM.APM_CATALOG A  " +
			"   WHERE     A.DEL_FL = 'N'  " +
			"         AND A.APM_MATCH_TYP = 'MAN'  " +
			"         AND A.MAN_MATCH_DT >  TRUNC (SYSDATE) " +
			"		  AND (A.MULT_WRK_ID IS NOT NULL OR A.WRK_ID <> ? )";	


		/*"SELECT A.REQ_TYP_CDE, " +
		"         UPD_DT, " +
		"         A.APM_PERF_BLK_REQ_ID, " +
		"         A.APM_PERF_BLK_REQ_GRP_ID, " +
		"         A.SUPP_CODE, " +
		"         A.PFR_NA, " +
		"         A.WRK_TTL, " +
		"         A.WRK_ID " +
		"    FROM APM_PERF_BLK_REQ A " +
		"   WHERE     DEL_FL = 'N' " +
		"         AND STA_CDE = 'CO' " +
		"         AND TRUNC (UPD_DT) = TRUNC (SYSDATE) ";
*/
	
	
	
	public static final String GET_APM_LEARNED_MATCH_END = " ";
	

	public static final String DYNAMIC_INSERT_APM_LEARNED_MATCH = "INSERT INTO APM_LEARNED_MATCH ";
	public static final String DYNAMIC_INSERT_APM_LEARNED_MATCH_WTR = "INSERT INTO APM_LEARNED_MATCH_WTR ";
	public static final String DYNAMIC_UPDATE_APM_LEARNED_MATCH = "UPDATE APM_LEARNED_MATCH SET DEL_FL = 'N' ";
	
	
	
	public static final String DYNAMIC_INSERT_EO_LEARNED_MATCH_ISRC = "INSERT INTO IDMatching.dbo.LearnedMatchISRC ";
	public static final String DYNAMIC_INSERT_EO_LEARNED_MATCH_ISWC = "INSERT INTO IDMatching.dbo.LearnedMatchISWC ";
	public static final String DYNAMIC_INSERT_EO_LEARNED_MATCH_SUPP_ID = "INSERT INTO IDMatching.dbo.LearnedMatchSuppId ";
	
	
	/*
	public static final String GET_SAMPLING_SUMMARY_LIST = 
		"SELECT SAMP_REQ_ID, " +
		"         STEP_CDE, " +
		" 		  DECODE (STEP_CDE, 'L1', 'Level 1 Grid', 'L2', 'Level 2 Grid', 'L3', 'Apply Sample','L4', 'Bypass', '' ) STEP_DESC,	"+	
		"         TO_CHAR(PRC_END_TM,'MM/DD/YYYY HH24:MI:SS') PRC_END_TM, " +
		"         TO_CHAR(PRC_STT_TM,'MM/DD/YYYY HH24:MI:SS') PRC_STT_TM, " +
		"         CRE_ID, " +
		"         UPD_ID, " +
		"         TO_CHAR(CRE_DT,'MM/DD/YYYY HH24:MI:SS') CRE_DT, " +
		"         TO_CHAR(UPD_DT,'MM/DD/YYYY HH24:MI:SS') UPD_DT, " +
		"         A.DEL_FL, " +
		"         NVL(STA_CDE,'PE') STA_CDE, " +
		"		  DECODE (STA_CDE, 'PR', 'In Progress', 'CO','Completed', 'AB','Aborted' , 'CA', 'Cancelled', 'BP','In Progress','BD','Completed','Pending' ) STA_DESC,	" +	
		"         TGTSURVYEARQTR, " +
		"         A.INIT_NUM_SETS , " +
		"         MSG, " +
		"         A.MUS_USER , " +
		"         C.DESCRIPTION LOAD_STA, " +
		"         B.MAN_MATCH_IND    " +
		"    FROM SAMP_REQ  A    , APM_MUSUSER_CNTRL B , LOAD_STA C " +
		"   WHERE A.MUS_USER = B.MUS_USER AND A.LOAD_STA = C.LOAD_STA(+) AND TGTSURVYEARQTR = ? AND A.DEL_FL = 'N' AND B.DEL_FL = 'N'" + 
		"ORDER BY MUS_USER, DECODE(STEP_CDE, 'L1', 1, 'L2', 2, 99) DESC  " ;
	  */
	  
	public static final String GET_SAMPLING_SUMMARY_LIST =   
	"SELECT * FROM " +
    "( " +
    "SELECT SAMP_REQ_ID,  " +
    " STEP_CDE,   " +
    "   DECODE (STEP_CDE, 'L1', 'Level 1 Grid', 'L2', 'Level 2 Grid', 'L3', 'Apply Sample','L4', 'Bypass', '' ) STEP_DESC,        " + 
    " TO_CHAR(PRC_END_TM,'MM/DD/YYYY HH24:MI:SS') PRC_END_TM,   " +
    " TO_CHAR(PRC_STT_TM,'MM/DD/YYYY HH24:MI:SS') PRC_STT_TM,   " +
    " CRE_ID,   " +
    " UPD_ID,   " +
    " TO_CHAR(CRE_DT,'MM/DD/YYYY HH24:MI:SS') CRE_DT, " +  
    " TO_CHAR(UPD_DT,'MM/DD/YYYY HH24:MI:SS') UPD_DT,   " +
    " A.DEL_FL,   " +
    " NVL(STA_CDE,'PE') STA_CDE, " +  
    "  DECODE (STA_CDE, 'PR', 'In Progress', 'CO','Completed', 'AB','Aborted' , 'CA', 'Cancelled', 'BP','In Progress','BD','Completed','Pending' ) STA_DESC, " +         
    " TGTSURVYEARQTR,   " +
    " A.INIT_NUM_SETS ,   " +
    " MSG,   " +
    " A.MUS_USER , " +  
    " C.DESCRIPTION LOAD_STA, " +  
    " B.MAN_MATCH_IND      " +
    " FROM SAMP_REQ  A    , APM_MUSUSER_CNTRL B , LOAD_STA C " +  
    "  WHERE A.MUS_USER = B.MUS_USER AND A.LOAD_STA = C.LOAD_STA(+) AND TGTSURVYEARQTR = ? AND A.DEL_FL = 'N' AND B.DEL_FL = 'N' " +   
    "  UNION ALL " +
    "  SELECT SAMP_REQ_ID, " +  
    " STEP_CDE,   " +
    "   DECODE (STEP_CDE, 'L1', 'Level 1 Grid', 'L2', 'Level 2 Grid', 'L3', 'Apply Sample','L4', 'Bypass', '' ) STEP_DESC, " +        
    " TO_CHAR(PRC_END_TM,'MM/DD/YYYY HH24:MI:SS') PRC_END_TM,   " +
    " TO_CHAR(PRC_STT_TM,'MM/DD/YYYY HH24:MI:SS') PRC_STT_TM,   " +
    " CRE_ID,   " +
    " UPD_ID,   " +
    " TO_CHAR(CRE_DT,'MM/DD/YYYY HH24:MI:SS') CRE_DT, " +  
    " TO_CHAR(UPD_DT,'MM/DD/YYYY HH24:MI:SS') UPD_DT,   " +
    " A.DEL_FL,   " +
    " NVL(STA_CDE,'PE') STA_CDE, " +  
    "  DECODE (STA_CDE, 'PR', 'In Progress', 'CO','Completed', 'AB','Aborted' , 'CA', 'Cancelled', 'BP','In Progress','BD','Completed','Pending' ) STA_DESC, " +         
    " TGTSURVYEARQTR,   " +
    " A.INIT_NUM_SETS ,   " +
    " MSG,   " +
    "  'NIELSEN-' || A.MUS_USER_TYP_CDE MUS_USER , " +  
    " C.DESCRIPTION LOAD_STA,   " +
    " 'N' MAN_MATCH_IND      " +
    "FROM SAMP_REQ  A    , MVW_MUS_USER_TYP B , LOAD_STA C " +  
    "WHERE A.MUS_USER_TYP_CDE = B.MUS_USER_TYP_CDE AND A.LOAD_STA = C.LOAD_STA(+) AND TGTSURVYEARQTR = ? AND A.DEL_FL = 'N'  AND UPPER(B.MUS_USER_GRP) = 'RADIO' " +  
    ") " +
    "ORDER BY MUS_USER, DECODE(STEP_CDE, 'L1', 1, 'L2', 2, 99) DESC  " ; 
	
	
	
	public static final String GET_CATALOG_SAMPLING_SUMMARY_LIST = 
			"WITH CTE AS "+
			"( "+
			"    SELECT SUPP_CODE, MAX (PERFQTR_PERIOD) PERFQTR_PERIOD FROM "+
			"    ( "+
			"    SELECT A.SUPP_CODE ,  TRIM( REPLACE(PERF_QTR,'Q','')) ||  TRIM(TO_CHAR(PERF_PERIOD,'09')) PERFQTR_PERIOD "+    
			"       FROM APM_ARCHIVE A , SUPP_FORMAT B "+ 
			"            WHERE A.SUPP_FORMAT_ID = B.SUPP_FORMAT_ID "+ 
			"            AND  NVL(a.STATUS,'X') = 'CL' "+
			"            AND A.DEL_FL = 'N' "+ 
			"            AND b.SUPP_TYPE = 'CATALOG' "+
			"            AND a.perf_qtr is not null "+
			"    ) "+
			"    GROUP BY SUPP_CODE "+
			") "+
			"SELECT SAMP_REQ_CAT_ID RequestId, A.SUPP_CODE Supplier, TRIM( SUBSTR(A.PERF_QTR,1,4)) || TRIM(TO_CHAR(A.PERF_PERIOD,'09')) Period,   B.LOAD_STA LoadStatus, C.STA_CDE SamplingStatus "+  
			"FROM PAPM.SAMP_REQ_CAT A LEFT JOIN PAPM.LOAD_STA B ON A.LOAD_STA = B.LOAD_STA LEFT JOIN PAPM.STA_CDE C ON A.STA_CDE = C.STA_CDE  "+
			"LEFT JOIN CTE ON A.SUPP_CODE = CTE.SUPP_CODE "+
			"WHERE   TO_NUMBER( TRIM( REPLACE(PERF_QTR,'Q','')) ||  TRIM(TO_CHAR(PERF_PERIOD,'09')) )  >= NVL(CTE.PERFQTR_PERIOD,0) " +
			"ORDER BY A.SUPP_CODE, TRIM( SUBSTR(A.PERF_QTR,1,4)) || TRIM(TO_CHAR(A.PERF_PERIOD,'09'))   " ;
	
	
	public static final String GET_LOGREQUEST_SUMMARY_LIST_OLD = 
			"  SELECT A.LOG_REQ_HDR_ID logRequestId,A.CALL CallLetter, TO_CHAR(A.PGM_STT_DT,'MM/DD/YYYY') StartDate,  TO_CHAR( A.PGM_END_DT,'MM/DD/YYYY') EndDate, " +
			"		A.LOC Location, A.STATE State, A.ACCT_NUM AccountNumber, A.LOG_MODE LogMode, " +
	        "  A.FORM Format,  A.RECEIVED  DateReceived, A.COMMENTS LogComments, A.NOTES Notes, B.DESCRIPTION Status, A.TGTSURVYEARQTR targetsurvyearqtr  " +
			"  FROM PAPM.LOG_REQ_HDR A  INNER JOIN PAPM.LOG_STATUS B ON A.LOG_STATUS = B.LOG_STATUS WHERE A.DEL_FL = 'N' " ;
	
	public static final String GET_LOGREQUEST_SUMMARY_LIST_ORDER_BY_OLD = 
			"  ORDER BY CallLetter, StartDate" ;
	
	public static final String GET_LOGREQUEST_CAN_DELETE =
			" SELECT CASE WHEN COUNT(*) = 0 THEN 'Y' ELSE 'N' END Count FROM PAPM.LOG_REQ_HDR WHERE TGTSURVYEARQTR = ? "
			+ "AND LOG_STATUS IN ('PND','RR','RD') AND DEL_FL = 'N' ";
	
	public static final String GET_LOGREQUEST_SUMMARY_LIST = 		
			" SELECT logRequestId, CallLetter, StartDate,  EndDate, Location, State, AccountNumber, LogMode, Format, DateReceived, LogComments,Notes, Status, targetsurvyearqtr, totalrows " +
			" FROM ( " +
			" SELECT A.LOG_REQ_HDR_ID logRequestId,A.CALL CallLetter, TO_CHAR(A.PGM_STT_DT,'MM/DD/YYYY') StartDate,  TO_CHAR( A.PGM_END_DT,'MM/DD/YYYY') EndDate, " +         
			" A.LOC Location, A.STATE State, A.ACCT_NUM AccountNumber, A.LOG_MODE LogMode,   A.FORM Format,   A.RECEIVED DateReceived, A.COMMENTS LogComments, " + 
			" A.NOTES Notes, B.DESCRIPTION Status, A.TGTSURVYEARQTR targetsurvyearqtr, ROW_NUMBER () OVER (ORDER BY A.CALL, TO_CHAR(A.PGM_STT_DT,'MM/DD/YYYY')) RN, COUNT (*) OVER () totalrows " +
			" FROM PAPM.LOG_REQ_HDR A  INNER JOIN PAPM.LOG_STATUS B ON A.LOG_STATUS = B.LOG_STATUS WHERE A.DEL_FL = 'N'   " ;
	
	public static final String GET_LOGREQUEST_SUMMARY_LIST_ORDER_BY = 
		  	" ) WHERE RN BETWEEN ? AND ? ORDER BY RN " ;
	
	public static final String GET_LOGREQUEST_STATUS = 
			"  SELECT A.LOG_STATUS Status FROM PAPM.LOG_REQ_HDR A WHERE LOG_REQ_HDR_ID = ? "; 
	
	
	 
	public static final String UPDATE_LOGREQUEST_HDR = 
			"	UPDATE PAPM.LOG_REQ_HDR SET PGM_STT_DT = TO_DATE(?, 'MM/DD/YYYY') ,PGM_END_DT =  TO_DATE(?, 'MM/DD/YYYY') , NOTES = ?, RECEIVED = ?  " +
			"	WHERE LOG_REQ_HDR_ID = ? ";
	
	public static final String DELETE_LOGREQUEST_HDR = 
			"	UPDATE PAPM.LOG_REQ_HDR SET DEL_FL = 'Y' WHERE TGTSURVYEARQTR = ? ";
	
	public static final String UPDATE_LOGREQUESTHDR_FOR_RELEASE = 
			"	UPDATE PAPM.LOG_REQ_HDR SET LOG_STATUS = 'RR' WHERE LOG_REQ_HDR_ID = ? ";

	public static final String UPDATE_LOGREQUESTDTL_FOR_RELEASE =
			"   UPDATE PAPM.LOG_REQ_WRK_PERF SET LOG_STATUS = 'RR' WHERE LOG_REQ_HDR_ID = ? AND LOG_STATUS = 'PND' ";
	
	public static final String UPDATE_LOGREQUESTHDR = 
			"	UPDATE PAPM.LOG_REQ_HDR SET LOG_STATUS = ? WHERE LOG_REQ_HDR_ID = ? ";

	public static final String UPDATE_LOGREQUESTDTL =
			"   UPDATE PAPM.LOG_REQ_WRK_PERF SET LOG_STATUS = ? WHERE LOG_REQ_HDR_ID = ? ";
	
	public static final String GET_LOGUSAGE_STATUS_INFO =
			"   SELECT LOG_STATUS Status, COUNT(*) Count FROM PAPM.LOG_REQ_WRK_PERF WHERE LOG_REQ_HDR_ID = ? AND DEL_FL = 'N' GROUP BY LOG_STATUS ";
			
	public static final String CALL_RELEASE_LOGREQ_PROC = "{call PRC_RELEASE_LOG_REQ }"; 
	
	public static final String GET_LOGUSAGE_SUMMARY_LIST_OLD = 
			"  SELECT LOG_REQ_WRK_PERF_ID wrkperfid, WRK_TTL title, PFR_NA artist, USE_TYP usetype , WRK_PERF_DUR duration, "
			+ "INST_VOCL instvocal, WRK_ID workid ,DESCRIPTION status, PLAY_CNT plays,"
			+ "CASE WHEN  A.LOG_STATUS = 'RD' THEN 1 ELSE 2 END sort,"
			+ "CASE WHEN  A.LOG_STATUS = 'RD' THEN 'Y' ELSE 'N' END readonly "
			+ "FROM PAPM.LOG_REQ_WRK_PERF A  INNER JOIN PAPM.LOG_STATUS B ON A.LOG_STATUS = B.LOG_STATUS "
			+ "WHERE LOG_REQ_HDR_ID = ? AND  A.DEL_FL = 'N'  ORDER BY sort, title" ;
	
	public static final String GET_LOGUSAGE_SUMMARY_LIST =
			" SELECT  wrkperfid, title, artist, usetype , duration, instvocal, workid ,status, plays, sort, readonly, totalrows " +
			" FROM " +
			" ( " +
			" SELECT LOG_REQ_WRK_PERF_ID wrkperfid, WRK_TTL title, PFR_NA artist, USE_TYP usetype , WRK_PERF_DUR duration,  " +
			" INST_VOCL instvocal, WRK_ID workid ,DESCRIPTION status, PLAY_CNT plays, " +
			" CASE WHEN  A.LOG_STATUS = 'RD' THEN 2 ELSE 1 END sort, " +
			" CASE WHEN  A.LOG_STATUS = 'RD' THEN 'Y' ELSE 'N' END readonly,  ROW_NUMBER () OVER ( ORDER BY CASE WHEN  A.LOG_STATUS = 'RD' THEN 2 ELSE 1 END, WRK_TTL) RN,  COUNT (*) OVER () totalrows  " +
			" FROM PAPM.LOG_REQ_WRK_PERF A  INNER JOIN PAPM.LOG_STATUS B ON A.LOG_STATUS = B.LOG_STATUS  " +
			" WHERE LOG_REQ_HDR_ID = ? AND  A.DEL_FL = 'N'  " +
			" ) WHERE RN BETWEEN ? AND ? ORDER BY RN ";
    
	
	public static final String GET_LOGHDR_INFO = 
			"  SELECT  A.CALL CallLetter, TO_CHAR(A.PGM_STT_DT,'MM/DD/YYYY') StartDate,  TO_CHAR( A.PGM_END_DT,'MM/DD/YYYY') EndDate, " +
					"		A.LOC Location, A.STATE State, A.ACCT_NUM AccountNumber, A.LOG_REQ_HDR_FILE_ID Fileid,A.TGTSURVYEARQTR Tgtsurvyearqtr "
					+ " FROM PAPM.LOG_REQ_HDR A WHERE LOG_REQ_HDR_ID = ?  " ;
	
	public static final String INSERT_LOGUSAGE = " INSERT INTO LOG_REQ_WRK_PERF (  LOG_REQ_WRK_PERF_ID,   LOG_REQ_HDR_FILE_ID,   "
			+ "LOG_REQ_HDR_ID, WRK_PERF_DUR,    PLAY_CNT,PFR_NA,WRK_TTL,WRK_ID, USE_TYP,     "
			+ "INST_VOCL,  LOG_STATUS,  TGTSURVYEARQTR,   CRE_DT,   CRE_ID,  DEL_FL) "    
            + "  VALUES (?,?,?,?,?, FNC_APM_STD_CHARS('NAME',?),FNC_APM_STD_CHARS('TITLE',?),?,?,?,?,?,SYSDATE,?,?) " ;
	 
	
	public static final String GET_VALIDATION_ERR =
			"SELECT COUNT(*) ValidationError FROM  PAPM.LOG_REQ_WRK_PERF  "
			+ "WHERE LOG_REQ_HDR_ID = ? AND DEL_FL = 'N'  AND LOG_STATUS = 'E' ";
			
	public static final String UPDATE_LOGUSAGE =
	        "UPDATE PAPM.LOG_REQ_WRK_PERF SET WRK_TTL = ? , PFR_NA = ?, WRK_ID = ?, WRK_PERF_DUR = ?,  "
	        + " PLAY_CNT = ?, USE_TYP = ?,  INST_VOCL = ?,  UPD_ID = ?, UPD_DT = SYSDATE "
			+ "  WHERE  LOG_REQ_WRK_PERF_ID = ? ";
	
	public static final String UPDATE_LOGUSAGE_WITH_STATUS =
	        "UPDATE PAPM.LOG_REQ_WRK_PERF SET WRK_TTL = ? , PFR_NA = ?, WRK_ID = ?, WRK_PERF_DUR = ?,  "
	        + " PLAY_CNT = ?, USE_TYP = ?,  INST_VOCL = ?,  UPD_ID = ?, UPD_DT = SYSDATE, LOG_STATUS = ?  "
			+ "  WHERE  LOG_REQ_WRK_PERF_ID = ? ";
	
	public static final String DELETE_LOGUSAGE =
	        "UPDATE PAPM.LOG_REQ_WRK_PERF SET DEL_FL = 'Y',  UPD_ID = ?, UPD_DT = SYSDATE WHERE LOG_REQ_WRK_PERF_ID = ? ";
	
	public static final String GET_CATALOG_SAMPLING_SUMMARY_LIST_OLD = 
			" SELECT SAMP_REQ_CAT_ID RequestId, SUPP_CODE Supplier,  TO_CHAR(PERF_QTR) || TO_CHAR(TO_DATE(PERF_PERIOD, 'MM'), 'MM')  Period,  " +
	        " B.LOAD_STA LoadStatus, C.STA_CDE SamplingStatus " +
            " FROM PAPM.SAMP_REQ_CAT A, PAPM.LOAD_STA B, PAPM.STA_CDE C  " + 
	        " WHERE A.LOAD_STA = B.LOAD_STA AND A.STA_CDE = C.STA_CDE " +
            " AND TO_NUMBER( TO_CHAR(SUBSTR(PERF_QTR,1,4)) || TO_CHAR(TO_DATE(PERF_PERIOD, 'MM'), 'MM') ) >=  TO_NUMBER(TO_CHAR( ADD_MONTHS(TRUNC(SYSDATE, 'Q'), -9), 'YYYY') || TO_CHAR( ADD_MONTHS(TRUNC(SYSDATE, 'Q'), -9), 'MM')) " +
	        "ORDER BY A.SUPP_CODE,  TO_CHAR(PERF_QTR) || TO_CHAR(TO_DATE(PERF_PERIOD, 'MM'), 'MM')   " ;
	
	public static final String POPULATE_SAMP_REQ_CAT = "{call PRC_ADD_SAMP_LDSTA_CAT ()}"; 
	 
	public static final String PROCESS_CATALOG_SAMPLING = "{call PRC_SAMP_CAT (?,?)}";  
	
	public static final String GET_CHANNEL_LIST = 
		" " +
		"SELECT Id, SuppCode " +
		"      ,Channel " +
		"      ,ClassicalInd " +
		"      ,DelFl " +
		"      ,Description " +
		"      ,NonMusicInd " +
		"      ,NonClassicalInd " +
		"  FROM FileInventory.dbo.ChannelList " +
		"  WHERE DelFl = 'N' ";
	
	
	
	public static final String GET_EO_FILE_SUMMARY_FILE_INV = "SELECT Status, isnull(LearnedMatch,'N') AS LearnedMatch, COUNT(*) AS FileCount, " +
	"COUNT(CASE WHEN  STATUS = 'NA' AND  CreDate BETWEEN CONVERT( DATE, ? ) AND CONVERT(DATE,? ) then 1 end) AS NoProcess_Count " +
	" FROM     FileInventory.dbo.FileInventory   " +
	"GROUP BY Status, isnull(LearnedMatch,'N') " ;

	
	public static final String GET_EO_FILE_SUMMARY_BATCH_CNTRL = 
		/*
		"SELECT STATUS, COUNT(*) FileCount FROM FileInventory.DBO.BatchCntrl "+
		" WHERE Status IN ('LR', 'LD', 'RA', 'RR', 'LA') AND PerfQtr = ? "+
		" GROUP BY Status ";
		*/
		"SELECT Status, COUNT(*) AS FileCount, " +
		"COUNT(CASE WHEN  STATUS = 'LD' AND  TgtSurvYearQtr = ? then 1 end) as SUCCESS_COUNT " +
		" FROM     FileInventory.dbo.BatchCntrl where DelFlag = 'N' " +
		" GROUP BY Status "; 

	public static final String GET_EO_FILE_SUMMARY_BATCH_CNTRL_SUCCESS_COUNT =
	"SELECT COUNT(*) SUCCESS_COUNT "+
	"FROM  "+
	"(     "+
	"SELECT a.SuppCode, "+  
	"CASE WHEN b.SupplierType = 'CATALOG' "+ 
	"THEN ROW_NUMBER () OVER (partition by a.SuppCode ORDER BY PerfQtr+CAST(PerfPeriod as VARCHAR) DESC) "+ 
	"ELSE 1 END AS RN    "+
	"FROM     FileInventory.dbo.BatchCntrl a "+  
	"inner join FileInventory.dbo.SuppFormat b on a.suppcode = b.suppcode "+  
	"where a.Status = 'LD'  AND A.DelFlag = 'N'  and   "+
	"     CASE WHEN b.SupplierType = 'Distribution' AND a.TgtSurvYearQtr = ? THEN 1 "+   
	"          WHEN b.SupplierType = 'Catalog' THEN 1 ELSE 0 END = 1   "+
	") A   "+
	"WHERE RN = 1" ;
	
	public static final String GET_EO_FILE_UA_CHANNEL_COUNT = "" +
		"	select A.SuppCode SuppCode, COUNT(*) UACount "+ 
		"	from FileInventory.dbo.ChannelList A, FileInventory.dbo.SuppFormat B "+ 
		"	WHERE A.SUPPCODE = B.SUPPCODE  "+
		"	AND B.Channel = 'Y'  "+
		"	AND A.ClassicalInd = 'N' "+ 
		"	AND A.NonMusicInd = 'N'  "+
		"	AND A.NonClassicalInd = 'N'  "+
		"	AND A.DelFl = 'N' "+
		"	GROUP BY A.SuppCode";
	/*
	public static final String GET_EO_FILE_LOAD_TO_APM_PEND_OLD =    
			"SELECT AA.SuppCode, AA.PerfQtr, AA.FileId, AA.FileNa, AA.FileRowCount, (ISNULL(AA.FileRejectCount,0)+ISNULL(AA.FileVldRejectCount,0)) AS FileRejectCount,ISNULL(AA.Display,'') AS ErrorCorrStatus,ISNULL(AA.FileRejectCount,0) AS FileRejectCountNew, ISNULL(AA.FileVldRejectCount,0)  AS FileVldRejectCountNew FROM (          " +
			"select T1.SuppCode, T1.PerfQtr, t1.FileId, t1.FileNa, t1.FileRowCount, t1.FileRejectCount, t1.FileVldRejectCount,ISNULL(T2.Display,'Ready for Rollup') Display from FileInventory.dbo.FileInventory T1 LEFT JOIN ReloadDisplayLookup T2 ON ISNULL(T1.ErrorCorrStatus,'') = ISNULL(T2.Status,'') where T1.STATUS = 'LD' and (T1.BATCHID IS NULL OR T1.BATCHID = '' ) " +
			//"AND T1.CreDate BETWEEN CONVERT( DATE, ? ) AND CONVERT(DATE, ? ) " +
			") AA  " +
			" WHERE "+ 
			" AA.SuppCode NOT IN ( " +
			" SELECT BB.SUPPCODE FROM FileInventory.DBO.ChannelList BB WHERE BB.DelFl = 'N' AND BB.ClassicalInd = 'N' AND BB.NonClassicalInd = 'N' AND BB.NonMusicInd = 'N' " +
			" and aa.SuppCode = bb.SuppCode " +
			" GROUP BY BB.SuppCode) " +
			" GROUP BY AA.SuppCode, AA.PerfQtr, AA.FileNa, AA.FileId, AA.FileRowCount, AA.FileRejectCount, AA.FileVldRejectCount,AA.Display " +
			" ORDER BY AA.SuppCode, AA.PerfQtr, AA.FileNa, AA.FileId, AA.FileRowCount, AA.FileRejectCount ,AA.FileVldRejectCount " ;
    */
	public static final String GET_EO_FILE_LOAD_TO_APM_PEND =   
			" WITH cte AS "+
		    " ( "+
			"  SELECT  suppcode,SUM(RowCnt)   ROLLUPCNT FROM "+
			"  ( "+
			"  SELECT RowCnt,suppcode,ROW_NUMBER() OVER (PARTITION BY SuppCode ORDER BY BatchId DESC) RN  "+
			"  FROM FileInventory.dbo.BatchCntrl where  Status = 'LD' AND DelFlag = 'N' "+
			"  GROUP BY SuppCode, BatchID, RowCnt "+
			"  ) A "+
			"  WHERE A.RN = 1 "+
			"  GROUP BY suppcode "+
			" )       "+
			" SELECT AA.SuppCode, AA.PerfQtr, AA.PerfPeriod, AA.FileId, AA.FileNa, AA.FileRowCount,  "+
			" (ISNULL(AA.FileRejectCount,0)+ISNULL(AA.FileVldRejectCount,0)) AS FileRejectCount, "+
			" ISNULL(AA.Display,'') AS ErrorCorrStatus,ISNULL(AA.FileRejectCount,0) AS FileRejectCountNew,  "+
			" ISNULL(AA.FileVldRejectCount,0)  AS FileVldRejectCountNew, "+
			" ISNULL(AA.Batchid,'') Batchid, AA.ROLLUPCNT, AA.SupplierType  "+
			" FROM  "+
			" (        "+    
			" select T1.SuppCode, T1.PerfQtr,T1.PerfPeriod, t1.FileId, t1.FileNa, t1.FileRowCount, t1.FileRejectCount,  "+
			" t1.FileVldRejectCount," +
			" CASE WHEN T1.FILEVLDREJECTCOUNT > 0 AND T1.ERRORCORRSTATUS IS NULL THEN 'Error Outstanding' ELSE ISNULL(T2.Display,'Ready for Rollup') END Display, "+
			" T1.Batchid, cte.ROLLUPCNT  ROLLUPCNT, SuppFormat.SupplierType  "+
			" from FileInventory.dbo.FileInventory T1  "+
			" LEFT JOIN ReloadDisplayLookup T2  "+
			" ON ISNULL(T1.ErrorCorrStatus,'') = ISNULL(T2.Status,'')  "+
			" LEFT JOIN cte ON T1.SuppCode = cte.SuppCode  "+
			" LEFT JOIN SuppFormat ON T1.SuppCode = SuppFormat.SuppCode  "+
			" where T1.STATUS = 'LD' and (T1.BATCHID IS NULL OR T1.BATCHID = '' )  and T1.learnedMatch = 'CO'    "+
			" ) AA     "+
			" WHERE   "+
			" AA.SuppCode NOT IN ( "+   
			"     SELECT BB.SUPPCODE FROM FileInventory.DBO.ChannelList BB WHERE BB.DelFl = 'N' AND BB.ClassicalInd = 'N' AND BB.NonClassicalInd = 'N' AND BB.NonMusicInd = 'N' "+   
			" 	 and aa.SuppCode = bb.SuppCode    "+
			" 	 GROUP BY BB.SuppCode "+
			"             )   "+
			" GROUP BY AA.SuppCode, AA.PerfQtr, AA.PerfPeriod, AA.FileNa, AA.FileId, AA.FileRowCount, AA.FileRejectCount, "+ 
			" AA.FileVldRejectCount,AA.Display, AA.Batchid , AA.ROLLUPCNT , AA.SupplierType    "+
			" ORDER BY AA.SuppCode, AA.PerfQtr, AA.PerfPeriod, AA.FileNa, AA.FileId, AA.FileRowCount, AA.FileRejectCount , "+
			" AA.FileVldRejectCount"  ;
	
	public static final String VALIDATION_ERROR_EXISTS =   
			" select  case when sum(isnull(filevldrejectcount,0)) = 0 then 'N' else 'Y' end ValidationError "+
			" from fileinventory.dbo.fileinventory "+
			" where suppcode = ? and perfqtr = ? "+
			" group by suppcode, perfqtr ";
	
	public static final String VALIDATION_ERROR_EXISTS_WITH_PERFPERIOD =   
			" select  case when sum(isnull(filevldrejectcount,0)) = 0 then 'N' else 'Y' end ValidationError "+  
			" from fileinventory.dbo.fileinventory a inner join fileinventory.dbo.suppformat b on a.suppcode = b.suppcode "+
			" where a.suppcode = ? and perfqtr = ? and isnull(perfperiod,0) = case when suppliertype = 'catalog' then ? else 0 end "+  
			" group by a.suppcode, a.perfqtr, case when suppliertype = 'catalog' then perfperiod else null end ";  
			   
	public static final String GET_SUPP_TYPE = 	"select SupplierType from suppformat where suppcode = ? ";
	
	public static final String GET_SUPP_INFO = 	"select SuppDB,SupplierType,ApmLdJob from FileInventory.dbo.SuppFormat where SuppCode =? ";
	
	public static final String IS_CORR_ERR_PEND =    
		 " select case when count(*) = 0 then 'N' else 'Y' end pendingErrors from fileinventory "+
         "  where   suppcode = ?  and perfqtr = ?  and ErrorCorrStatus in ('ER','PE','IG') ";
	
	public static final String ERR_TBL_EXISTS =    
	 " SELECT TOP 1 CASE WHEN ISNULL(OBJECT_ID( ? , 'U'),0) = 0 THEN 'N' ELSE 'Y' END ID FROM SYS.TABLES "; 
	 
	public static final String DATA_EXISTS_IN_TABLE = 
	 " SELECT CASE WHEN COUNT(*)= 0 THEN 'N' ELSE 'Y' END  dataexists FROM ErrorTable ";  
	 
	 
	 /*
	public static final String GET_ROLLUP_SUM_CNT_PART1_OLD = 
		 " SELECT ISNULL(SUM(RowCnt),0) ROLLUPCNT FROM " +
		    " ( "+
              " SELECT RowCnt,  ROW_NUMBER() OVER (PARTITION BY SuppCode ORDER BY BatchId DESC) RN "+
              " FROM FileInventory.dbo.BatchCntrl where SuppCode IN ( ";
	*/
	public static final String GET_ROLLUP_SUM_CNT_PART1 = 
		" SELECT COALESCE(SUM(X.ROLLUPCNT),0) ROLLUPCNT FROM "+
		" ( "+
		" SELECT SUM(RowCnt) ROLLUPCNT FROM ( "+
		" SELECT RowCnt,  ROW_NUMBER() OVER (PARTITION BY SuppCode ORDER BY BatchId DESC) RN  "+
		" FROM FileInventory.dbo.BatchCntrl where SuppCode IN (  ";
	/*			
	public static final String GET_ROLLUP_SUM_CNT_PART2_OLD = 
		 "   ) and Status = 'LD' AND DelFlag = 'N' "+
             " GROUP BY SuppCode, BatchID, RowCnt "+
          " ) A "+
                  " WHERE A.RN = 1 "; 
	*/
	public static final String GET_ROLLUP_SUM_CNT_PART2 = 
		"   	) and Status = 'LD' AND DelFlag = 'N' "+
		"   		GROUP BY SuppCode, BatchID, RowCnt "+
		"   		) A "+
		"   		WHERE A.RN = 1 "+
		"   		UNION ALL "+
		"   		SELECT SUM(RowCnt) ROLLUPCNT FROM ( "+
		"   		SELECT RowCnt,  ROW_NUMBER() OVER (PARTITION BY SuppCode ORDER BY BatchId DESC) RN "+ 
		"   		FROM FileInventory.dbo.BatchCntrl where suppcode in (select abc.suppcode  "+
		"   		from fileinventory.dbo.BatchCntrl ABC where ABC.status  IN ('MR','RR')  "+
		"   		and abc.DelFlag = 'N' ) AND DelFlag = 'N' AND STATUS = 'LD' "+
		"   		GROUP BY SuppCode, BatchID, RowCnt "+
		"   		) B "+
		"   		WHERE B.RN = 1 "+
		"   	) X ";
	public static final String GET_ROLLUP_THRESHOLD = 
	      " SELECT PARAM_VAL FROM APM_SYS_PARAM WHERE PARAM_NA = 'ROLLUP_THRESHOLD' ";
		
		
	public static final String GET_EO_FILE_LOAD_TO_APM_PROG =
	"SELECT A.SuppCode, A.PerfQtr, A.BatchID, A.Status, B.FileId, B.FileNa, A.PerfPeriod, S.SupplierType FROM FileInventory.dbo.BatchCntrl A " +
	"INNER JOIN FileInventory.dbo.FileInventory B " +
	"ON (A.BatchID = B.BatchID)  INNER JOIN SuppFormat S ON A.SuppCode = S.SuppCode " +
	"where A.Status in ('RR','LR', 'MR') " +
	"AND A.DelFlag = 'N' " +
	"GROUP BY A.SUPPCODE, A.PerfQtr, A.BatchID, A.Status, B.FileId, B.FileNa, A.PerfPeriod, S.SupplierType  " +
	"ORDER BY A.SUPPCODE, A.PerfQtr, A.PerfPeriod, A.BatchID, A.Status, B.FileId, B.FileNa  "; 

	
	public static final String GET_EO_FILE_LOAD_TO_EO_PEND = 
		"SELECT A.FileId " +
		"      ,A.FileNa " +
		"      ,A.OrigFileNa " +
		"      ,A.FileTo " +
		"      ,A.CreDate " +
		"      ,A.Status " +
		//"      ,A.ISDEOLoadFlg " +
		"      ,A.SuppCode " +
		"      ,B.SuppCode as FormatSuppCode FROM ( " +
		"SELECT FileId " +
		"      ,FileNa " +
		"      ,OrigFileNa " +
		"      ,FileTo " +
		"      ,convert(varchar(10), CreDate, 101) as CreDate, creDate as OriginalDate " +
		"      ,Status " +
		//"      ,ISDEOLoadFlg " +
		"      ,SuppCode " +
		"  FROM FileInventory.dbo.FileInventory WHERE Status IN ('CR','LR') or ( Status = 'LD' and LearnedMatch is null )  " +
		"	) A " +
		" LEFT OUTER JOIN (select suppcode from FileInventory.dbo.SuppFormat group by SuppCode) B  " +
		" ON A.SuppCode = B.SuppCode " +
		" order by A.SuppCode, A.OriginalDate desc ";
	
	public static final String GET_EO_FILES_IN_ERROR =   
	"select a.SuppCode, a.FileId, a.BatchId, a.Status, a.FileNa from  " +
	"( " +
	"select SuppCode, fileid as FileId, null as BatchId, status as Status, FileNa AS FileNa from FileInventory.dbo.FileInventory " +
	"where Status in ('CA','LA','SA') " +
	//"AND CreDate BETWEEN CONVERT( DATE, ? ) AND CONVERT(DATE, ? ) " +
	"UNION ALL " +
	"select SuppCode, null as FileId, batchId as BatchId, status as Status, null AS FileNa  from FileInventory.dbo.BatchCntrl " +
	"where Status in ('RA','LA')  " +
	"and DelFlag = 'N' " +
	//"and PerfQtr = ? " +
	") a " +
	"order by a.SuppCode " ;
	
	public static final String GET_EO_FILES_NOT_FOR_PROCESS = 
	"select a.SuppCode, a.FileId, a.FileNa, a.FileTo, convert(varchar(10), a.CreDate, 101) as CreDateFormatted, convert(varchar(10), a.UpdDt, 101) as UpdDateFormatted from FileInventory.dbo.FileInventory a " +
	"where a.Status = 'NA' " +
	"AND a.CreDate BETWEEN CONVERT( DATE, ? ) AND CONVERT(DATE, ?) " +
	"order by a.SuppCode, a.updDt desc "; //a.SuppCode, a.CreDate desc "; 
	
	public static final String GET_EO_FILES_LOADED_SUCCESS_DEFAULT = 
	"SELECT SuppCode, PerfQtr, TgtSurvYearQtr, UpdDt as UpdDtFormatted, RowCnt, PlayCnt, PerfPeriod,RN "+
	"FROM "+
	"( "+   
	"SELECT a.SuppCode, a.PerfQtr,  "+
	"REPLACE(a.TgtSurvYearQtr,'Q','D') TgtSurvYearQtr, convert(varchar(10),a.UpdDt, 101) as UpdDt,  "+
	"a.RowCnt,  "+
	"CASE WHEN b.SupplierType = 'CATALOG' THEN NULL ELSE a.PlayCnt END PlayCnt, a.PerfPeriod, "+
	"CASE WHEN b.SupplierType = 'CATALOG' THEN "+
	"ROW_NUMBER () OVER (partition by a.SuppCode ORDER BY PerfQtr+CAST(PerfPeriod as VARCHAR) DESC) "+
	"ELSE 1 END AS RN   "+
	"FROM     FileInventory.dbo.BatchCntrl a  "+
	"inner join FileInventory.dbo.SuppFormat b on a.suppcode = b.suppcode  "+
	"where a.Status = 'LD'  "+
	"AND A.DelFlag = 'N'  and  "+
	"CASE WHEN b.SupplierType = 'Distribution' AND a.TgtSurvYearQtr = ? THEN 1   "+
	"WHEN b.SupplierType = 'Catalog' THEN 1 ELSE 0 END = 1  "+
	") A  "+
	"WHERE RN = 1 ";
	
	public static final String GET_EO_FILES_LOADED_SUCCESS_PART1 = 
	"SELECT a.SuppCode, a.PerfQtr, a.TgtSurvYearQtr, convert(varchar(10), a.UpdDt, 101) as UpdDtFormatted, a.RowCnt, "+
	"CASE WHEN b.SupplierType = 'CATALOG' THEN NULL ELSE a.PlayCnt END PlayCnt, a.PerfPeriod "+
	"FROM     FileInventory.dbo.BatchCntrl a " +
	"inner join FileInventory.dbo.SuppFormat b on a.suppcode = b.suppcode "+
	"where a.Status = 'LD' AND A.DelFlag = 'N' " ;
	
	
	public static final String GET_EO_FILES_LOADED_SUCCESS_PART2 = " order by a.SuppCode, a.UpdDt desc " ;
	
	public static final String GET_EO_FILES_NEW = 
	"select a.SuppCode, a.FileId, a.FileNa, a.FileTo, convert(varchar(10), a.CreDate, 101) as CreDate, isnull(CAST (PerfPeriod AS varchar),'') as PerfPeriod, "+
	"upper(b.SupplierType) as SupplierType, upper(b.Frequency) as Frequency "+
	"from     FileInventory.dbo.FileInventory a inner join FileInventory.dbo.SuppFormat b on a.suppcode = b.suppcode "+
	"WHERE (a.Status is null or a.Status = '') " ;
	public static final String GET_EO_FILES_NEW_END = " ORDER BY a.SuppCode, ISNULL(a.CreDate, '1900-01-01T00:00:00') desc, a.FileId desc " ;


	
	public static final String GET_EO_FILE_LIST_START = 
		"SELECT rn, totalrows, FileId " +
		"      ,FileNa " +
		"      ,OrigFileNa " +
		"      ,ZipFileNa " +
		"      ,FileFrom " +
		"      ,FileTo " +
		"      ,EmailFrom " +
		"      ,EmailTo " +
		"      ,EmailDate " +
		"      ,FileTransMethod " +
		"      ,CreDate " +
		"      ,Status " +
		"      ,EOLoadFlg " +
		"      ,APMLoadFlg " +
		"      ,PerfQtr " +
		"      ,SuppCode " +
		"      ,LoadStartTime " +
		"      ,LoadEndTime " +
		"      ,FileRejectCount " +
		"      ,FileRowCount " +
		//"      ,ISDEOLoadFlg " +
		"      ,FileVldRejectCount " +
		" FROM (" +
		"SELECT ROW_NUMBER () OVER ( order by A.fileid desc ) rn "+
		" 	   ,COUNT(*) over () totalrows "+
		"	   ,A.FileId " +
		"      ,A.FileNa " +
		"      ,A.OrigFileNa " +
		"      ,A.ZipFileNa " +
		"      ,A.FileFrom " +
		"      ,A.FileTo " +
		"      ,A.EmailFrom " +
		"      ,A.EmailTo " +
		"      ,A.EmailDate " +
		"      ,A.FileTransMethod " +
		"      ,cast(A.CreDate As Date) CreDate " +
		"      ,A.Status " +
		"      ,A.EOLoadFlg " +
		"      ,A.APMLoadFlg " +
		"      ,A.PerfQtr " +
		"      ,A.SuppCode " +
		"      ,A.LoadStartTime " +
		"      ,A.LoadEndTime " +
		"      ,A.FileRejectCount " +
		"      ,A.FileRowCount " +
		//"      ,A.ISDEOLoadFlg " +
		"      ,A.FileVldRejectCount " +
		"  FROM FileInventory.dbo.FileInventory A where 1=1 ";
	public static final String GET_EO_FILE_LIST_END = " ) OUTERTABLE WHERE OUTERTABLE.rn between ? and ?  ";
	
	public static final String GET_CHANNEL_SUPPLIERS = " SELECT SuppCode FROM FileInventory.dbo.SuppFormat WHERE Channel = 'Y' ORDER BY SuppCode ";
	
	public static final String UPDATE_CHANNELS = "UPDATE FILEINVENTORY.DBO.ChannelList " +
	"   SET ClassicalInd = ?, NonClassicalInd = ?, NonMusicInd = ?, UserID = ?, UserDt = GETDATE() " +
	"   where ID = ? "; 
	
	public static final String UPDATE_CHANNELS_ASSIGN_NON_CLASSICAL = "UPDATE FileInventory.dbo.ChannelList "+
   "	SET  NonClassicalInd = ?,  UserID = ?, UserDt = GETDATE() "+
   "	where SuppCode = ? and ClassicalInd = 'N' and NonClassicalInd = 'N' and NonMusicInd = 'N' ";

	
	public static final String GET_CHANNEL_TYP_COUNTS = " SELECT ISNULL(sum(case when ClassicalInd = 'Y' then 1 end),0) as CLASSICAL_COUNT,  " +
	"       ISNULL(sum(case when NonMusicInd = 'Y' then 1 end),0) as NONMUSIC_COUNT,  " +
	"       ISNULL(sum(case when NonClassicalInd = 'Y' then 1 end),0) as NONCLASSICAL_COUNT, " +
	"       ISNULL(sum(case when ClassicalInd <> 'Y' and NonClassicalInd <> 'Y' and NonMusicInd <> 'Y' then 1 end),0) as UNASSIGNED_COUNT " +
	" FROM FileInventory.dbo.ChannelList  " +
	" WHERE 1=1 " ;
	
	public static final String GET_SAMPLING_DETAILS_LIST = 
		"SELECT STG_PLAYCNT_RANGES_ID, " +
		"         INCLUDE_FRNAFL_FLAG, " +
		"         TOT_UNMATCHED_FRNAFL_PLAYCNT, " +
		"         TOT_UNMATCHED_FRNAFL_WP, " +
		"         RND_STAT, " +
		"         PROCESSED, " +
		"         DEL_FL, " +
		"         REF_ID, " +
		"         MERGE_IND, " +
		"         CENSUS_SAMPLE_IND, " +
		"         SELECT_SKIP, " +
		"         SELECT_START, " +
		"         SELECT_COUNT, " +
		"         TO_NUMBER(TO_CHAR(SELECT_PERCENT,999.99)) SELECT_PERCENT, " +
		"         TOT_UNMATCHED_PLAYCNT, " +
		"         TOT_UNMATCHED_WP, " +
		"         TOT_MATCHED_PLAYCNT, " +
		"         TOT_MATCHED_WP, " +
		"         RANGE_END, " +
		"         RANGE_START, " +
		"         TGTSURVYEARQTR, " +
		"         MUS_USER " +
		"    FROM PAPM.STG_PLAYCNT_RANGES " +
		"   WHERE MUS_USER = ? AND TGTSURVYEARQTR = ? AND DEL_FL = 'N' " +
		"ORDER BY NVL (RANGE_START, 0) " 
 ;
		//" AND MUS_USER = ? AND TGTSURVYEARQTR = ? " ;

	public static final String SAMPLING_MUS_USERS_FROM_MUSUSER_CNTRL = "SELECT MUS_USER, INIT_NUM_SETS " +
	"  FROM PAPM.APM_MUSUSER_CNTRL A " +
	" WHERE A.DEL_FL = 'N' " +
	"       AND NOT EXISTS " +
	"              (SELECT 1 " +
	"                 FROM SAMP_REQ B " +
	"                WHERE B.MUS_USER = A.MUS_USER AND B.TGTSURVYEARQTR = ? AND B.DEL_FL = 'N') " ;

	public static final String SAMPLING_MUS_USERS_FROM_MVW_MUS_USER_TYP = "SELECT MUS_USER_TYP_CDE, 20 INIT_NUM_SETS   " +
    "  FROM PAPM.MVW_MUS_USER_TYP A  " +
    "  WHERE  UPPER(MUS_USER_GRP) = 'RADIO' AND NOT EXISTS  " +
    "              (SELECT 1  " +
    "                 FROM SAMP_REQ B  " +
    "                WHERE B.MUS_USER_TYP_CDE = A.MUS_USER_TYP_CDE AND B.TGTSURVYEARQTR = ? AND B.DEL_FL = 'N') " ;
	
	public static final String INSERT_SAMP_REQ = "INSERT INTO SAMP_REQ (SAMP_REQ_ID, MUS_USER, " +
	"                      INIT_NUM_SETS, STEP_CDE, " +
	"                      STA_CDE, TGTSURVYEARQTR, " +
	"                      CRE_DT, " +
	"                      CRE_ID, TOT_NUM_WP) " +
	"     VALUES (?,?,?,?,?,?,SYSDATE,?,?) " ;
	
	public static final String INSERT_SAMP_REQ_MUS_USER_TYP_CDE = "INSERT INTO SAMP_REQ (SAMP_REQ_ID, MUS_USER_TYP_CDE, " +
			"                      INIT_NUM_SETS, STEP_CDE, " +
			"                      STA_CDE, TGTSURVYEARQTR, " +
			"                      CRE_DT, " +
			"                      CRE_ID, TOT_NUM_WP, SUPP_CODE,MUS_USER) " +
			"     VALUES (?,?,?,?,?,?,SYSDATE,?,?,?,?) " ;
	
	public static final String INSERT_SAMP_REQ_MUS_USER_TYP_CDE_GEN = "INSERT INTO SAMP_REQ (SAMP_REQ_ID, MUS_USER, " +
			"                      INIT_NUM_SETS, STEP_CDE, " +
			"                      STA_CDE, TGTSURVYEARQTR, " +
			"                      CRE_DT, " +
			"                      CRE_ID, TOT_NUM_WP,SUPP_CODE , MUS_USER_TYP_CDE) " +
			"     VALUES (?,?,?,?,?,?,SYSDATE,?,?,?,?) " ;

	public static final String GET_SAMPLING_PLAY_CNT_RANGES = "SELECT INIT_NUM_SETS FROM SAMP_REQ WHERE MUS_USER = ? AND STEP_CDE = 'L1' AND STA_CDE = 'CO' AND TGTSURVYEARQTR = ? AND DEL_FL = 'N' AND ROWNUM = 1";
	
	public static final String UPDATE_STG_PLAYCNT_RANGES = "UPDATE STG_PLAYCNT_RANGES SET CENSUS_SAMPLE_IND=?, MERGE_IND=?, INCLUDE_FRNAFL_FLAG=? WHERE STG_PLAYCNT_RANGES_ID=?";
	
	public static final String UPDATE_STG_PLAYCNT_RANGES_L2 = " UPDATE STG_PLAYCNT_RANGES SET SELECT_PERCENT=?, SELECT_COUNT=?, SELECT_START=?, SELECT_SKIP=? WHERE STG_PLAYCNT_RANGES_ID=? ";
	
	public static final String GET_MUSUSER_HAS_FRN_AFFL = "SELECT HAS_FRN_AFFL  FROM PAPM.APM_MUSUSER_CNTRL WHERE MUS_USER = ?" ;
	
	public static final String UPDATE_EO_FILE_INVENTORY_LOAD2EO = "UPDATE FileInventory.dbo.FileInventory SET Status = ?, PerfQtr=?, updId = ?, updDt = GetDate(), PerfPeriod=? WHERE FileId = ? ";
	
	public static final String IS_SUPPLIER_FORMAT_EXISTS = " " +
	"select A.SuppCode from FileInventory.dbo.SuppFormat A  " +
	"INNER JOIN FileInventory.dbo.FileInventory B " +
	"ON (B.SuppCode COLLATE SQL_Latin1_General_CP1_CS_AS = A.SuppCode COLLATE SQL_Latin1_General_CP1_CS_AS) " +
	"WHERE B.FileId = ? ";
	
	public static final String UPDATE_EO_FILE_INVENTORY_LOAD2APM = "UPDATE FileInventory.dbo.FileInventory SET BatchId = IDENT_CURRENT('FileInventory.dbo.BatchCntrl'), UpdId = ?, UpdDt =  GetDate() WHERE Status = 'LD' AND SuppCode = ? and PerfQtr = ? ";
	
	public static final String GET_ACTIVE_SUVERY_YEAR_QTR = " SELECT APM_SURV_DATE_ID, TO_CHAR(STT_DT,'MM/DD/YYYY') STT_DT, TO_CHAR(END_DT,'MM/DD/YYYY') END_DT, TGTSURVYEARQTR, ACTIVE_FL  FROM APM_SURV_DATE WHERE ACTIVE_FL = 'Y' AND DEL_FL = 'N' ";
	
	public static final String GET_HDR_TGTSURVYEARQTR = " SELECT TGTSURVYEARQTR  FROM APM_PERF_HDR WHERE APM_PERF_HDR_ID = ? ";

	public static final String GET_ACTIVE_CENSUS_SUPPLIERS = " " +
	"SELECT A.MUS_USER , NVL2(B.STA_CDE,'Y','N') SAMP_COMP_FL " +
	" FROM PAPM.APM_MUSUSER_CNTRL A, PAPM.SAMP_REQ B " +
	" WHERE A.DEL_FL = 'N'  AND B.DEL_FL(+) = 'N' " +
	" AND A.MUS_USER = B.MUS_USER(+) " +
	" AND B.TGTSURVYEARQTR(+) = ? " +
	" AND B.STA_CDE(+) = 'CO' " +
	" AND B.STEP_CDE(+) = 'L3'  " +
	" GROUP BY  A.MUS_USER , NVL2(B.STA_CDE,'Y','N')  " +
	" ORDER BY A.MUS_USER ";
	
	public static final String GET_EO_CALL_LETTER_CONFIGS = "SELECT SuppCode "+
      " ,CalLtr "+
      " ,CalLtrSuf "+
      " ,CalLtr+'-'+CalLtrSuf as callLtrFull "+
      " FROM FileInventory.dbo.CalLtrConfig "+
      " where DelFl = 'N' "+
      " order by SuppCode, CalLtr ";
	
	public static final String GET_APM_SUPPLIERS = " SELECT SUPP_FORMAT_ID, SUPP_CODE FROM SUPP_FORMAT ORDER BY SUPP_CODE ";
	
	public static final String INSERT_BATCH_CONTROL_SS = 
	" INSERT INTO FileInventory.dbo.BatchCntrl " +
	"           (SuppCode " +
	"           ,Status " +
	"           ,CreID " +
	"           ,CreDt " +
	"           ,DelFlag " +
	"           ,TableNa " +
	"           ,PerfQtr, TgtSurvYearQtr) " +
	"     VALUES (?,?,?,GetDate(),'N',?,?, ?) "; 
	
	
	public static final String GET_BATCH_CONTROL = "SELECT BatchId FROM FileInventory.DBO.BatchCntrl where SuppCode = ? AND PerfQtr = ? and DelFlag = 'N'";
	
	
	
	
	
	
	/*
	 * MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY
	 * MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY
	 * MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY
	 * MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY
	 * MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY MEDLEY MEDLEY MEDELY MEDLEY MEDLEY
	 */
	
	
	public static final String GET_MEDLEY_WORKIDS = "SELECT WRK_ID_VLD FROM APM_WRK_PERF A WHERE A.MULT_WRK_ID = ? AND A.DEL_FL = 'N' GROUP BY WRK_ID_VLD ";
	
	public static final String GET_MEDLEY_WORKIDS_CATALOG = "SELECT WRK_ID_VLD FROM APM_CATALOG A WHERE A.MULT_WRK_ID = ? AND A.DEL_FL = 'N' GROUP BY WRK_ID_VLD ";
	
	public static final String UPDATE_HEADER_COUNTS_FOR_MEDLEY_UNMATCH = "UPDATE APM_PERF_HDR HDR " +
	"   SET UPD_ID = ?, " +
	"       UPD_DT = SYSDATE, " +
	"       WRK_PERF_CNT = " +
	"          (SELECT COUNT (A.APM_WRK_PERF_ID) " +
	"             FROM APM_WRK_PERF A " +
	"            WHERE HDR.APM_PERF_HDR_ID = A.APM_PERF_HDR_ID AND A.DEL_FL = 'N'), " +
	"       TOTAL_NUM_PLAYS = " +
	"          (SELECT SUM (A.PLAY_CNT_VLD) " +
	"             FROM APM_WRK_PERF A " +
	"            WHERE HDR.APM_PERF_HDR_ID = A.APM_PERF_HDR_ID AND A.DEL_FL = 'N') " +
	" WHERE EXISTS " +
	"          (SELECT 1 " +
	"             FROM APM_WRK_PERF X " +
	"            WHERE     HDR.APM_PERF_HDR_ID = X.APM_PERF_HDR_ID " +
	"                  AND X.MULT_WRK_ID = ? " +
	"                  AND X.DEL_FL = 'Y' " +
	"                  AND X.WRK_ID = ?) "; 


	public static final String MEDLEY_GROUP_ORIGINAL_PERFS_START_XXXXXX =	
	"  FROM PAPM.APM_WRK_PERF A, " +
	"       (SELECT * " +
	"          FROM (  SELECT WRK_ID_VLD, ROW_NUMBER () OVER (ORDER BY CRE_DT) RN " +
	"                    FROM APM_WRK_PERF " +
	"                   WHERE     MULT_WRK_ID = ? " +
	"                         AND DEL_FL = 'N' " +
	"                         AND TGTSURVYEARQTR = ? " +
	"                GROUP BY WRK_ID_VLD, CRE_DT) " +
	"         WHERE RN = 1) Y " +
	" WHERE     A.MULT_WRK_ID = ?  AND A.TGTSURVYEARQTR = ? " +
	"       AND A.WRK_ID_VLD = Y.WRK_ID_VLD " +
	"       AND A.DEL_FL = 'N' " +
	"       AND A.PROC_STATUS = 'BUSVLD' " +
	"       AND A.SEND_MAN_MATCH = 'Y' " +
	"       AND A.APM_MATCH_TYP = 'MAN' " +
	"       AND A.LOCK_IND = 'N' " ;
	
	
	public static final String MEDLEY_GROUP_ORIGINAL_PERFS_START =	
		"  FROM PAPM.APM_WRK_PERF A " +
		" WHERE     A.MULT_WRK_ID = ?  AND A.TGTSURVYEARQTR = ? " +
		"		AND A.CRE_ID <> 'MEDLEYADD' "+	
		"       AND A.DEL_FL = 'N' " +
		"       AND A.PROC_STATUS = 'BUSVLD' " +
		"       AND A.SEND_MAN_MATCH = 'Y' " +
		"       AND A.APM_MATCH_TYP = 'MAN' " +
		"       AND A.LOCK_IND = 'N' " ;
	
	public static final String MEDLEY_GROUP_ORIGINAL_PERFS_START_CATALOG =	
			"  FROM PAPM.APM_CATALOG A " +
			" WHERE     A.MULT_WRK_ID = ? " +
			"		AND A.CRE_ID <> 'MEDLEYADD' "+	
			"       AND A.DEL_FL = 'N' " +
			"       AND A.PROC_STATUS = 'BUSVLD' " +
			"       AND A.SEND_MAN_MATCH = 'Y' " +
			"       AND A.APM_MATCH_TYP = 'MAN' " +
			"       AND A.LOCK_IND = 'N' " ;

	
	public static final String MEDLEY_GROUP_PERFS_ORIGINAL_COLUMNS = 
		
		"SELECT  " +
		"A.APM_PERF_HDR_ID " +
		",A.APM_WRK_PERF_ID " +
		",A.APM_ARCHIVE_ID " +
		",A.APM_MATCH_TYP " +
		",A.APM_SOURCE_SONG_KEY " +
		",A.WRK_EFF_STT_DT " +
		",A.WRK_EFF_END_DT " +
		",A.WRK_PERF_DUR " +
		",A.PLAY_CNT " +
		",A.MEDL_SEQ " +
		",A.PFR_NA " +
		",A.WRK_TTL " +
		",A.WRK_ID " +
		",A.WRK_SEQ_NR " +
		",A.USE_TYP " +
		",A.PROC_STATUS " +
		", TO_CHAR(A.STATUS_DATE,'MM/DD/YYYY HH24:MI:SS') STATUS_DATE " +
		",A.BAT_ID " +
		",A.PROVIDER_ID " +
		",A.CRE_DT " +
		",A.CRE_ID " +
		",A.UPD_DT " +
		",A.UPD_ID " +
		",A.ERR_STATUS " +
		",A.LINE_NR " +
		",A.CHANNEL " +
		",A.ALBUM " +
		",A.LABL " +
		",A.ISRC " +
		",A.CATALOG_NBR " +
		",A.CATALOG_YEAR " +
		",A.CLASSICAL " +
		",A.COMPANY " +
		",A.STATION " +
		",A.SUPPLIER_ID " +
		",A.PERF_DATE " +
		",A.PERF_TIME " +
		",A.DEL_FL " +
		",A.LOCK_IND " +
		",A.INST_VOCL " +
		",A.SUPP_CODE " +
		",A.WRK_ID_VLD " +
		",A.PLAY_CNT_VLD " +
		",A.SEND_MAN_MATCH " +
		",A.WRK_PERF_DUR_VLD " +
		",A.MAN_MATCH_DT " +
		",A.MAN_MATCH_USR_ID " +
		",A.ESTM_DOLLAR_VAL " +
		",A.EO_DETAIL_ID " +
		",A.ESTM_DLRVAL_FOUND " +
		",A.TGTSURVYEARQTR " +
		",A.MULT_WRK_ID " +
		",A.DETECTION_TM " +
		",A.LIBRARY_NM " +
		",A.LIBRARY_DISC " +
		",A.LIBRARY_DISC_ID " +
		",A.LIBRARY_TRACK " +
		",A.LIBRARY_TRACK_ID " +
		",A.LIBRARY_DISC_ID " +
		",A.WRITER " +
		",A.CONFIDENCE_LVL " +
		",A.ASG_USR_ID " ;
	public static final String MEDLEY_GROUP_PERFS_START = 
		"  FROM PAPM.APM_WRK_PERF A WHERE DEL_FL = 'N' " ;
	
	public static final String MEDLEY_GROUP_PERFS_START_CATALOG = 
			"  FROM PAPM.APM_CATALOG A WHERE DEL_FL = 'N' " ;
		

	public static final String MEDLEY_GROUP_PERFS_END = 
	"       AND A.TGTSURVYEARQTR = ? " +
	"       AND A.PROC_STATUS = 'BUSVLD' " +
	"       AND A.SEND_MAN_MATCH = 'Y' " +
	"       AND A.APM_MATCH_TYP = 'NMT' " +
	"       AND A.LOCK_IND = 'N' " ;
	
	public static final String MEDLEY_GROUP_PERFS_END_CATALOG = 
			"       AND A.PROC_STATUS = 'BUSVLD' " +
			"       AND A.SEND_MAN_MATCH = 'Y' " +
			"       AND A.APM_MATCH_TYP = 'NMT' " +
			"       AND A.LOCK_IND = 'N' " ;
	
	public static final String ASSIGN_GROUP_PERFS_END = 
		"       AND A.TGTSURVYEARQTR = ? " +
		"       AND A.PROC_STATUS = 'BUSVLD' " +
		"       AND A.SEND_MAN_MATCH = 'Y' " +
		"       AND (A.APM_MATCH_TYP = 'NMT' OR (A.APM_MATCH_TYP = 'MAN' AND A. MAN_MATCH_DT > TRUNC(SYSDATE)) )" +
		"       AND A.LOCK_IND = 'N' " ;
	
	public static final String ASSIGN_GROUP_CATALOG_PERFS_END = 
			"       AND A.PROC_STATUS = 'BUSVLD' " +
			"       AND A.SEND_MAN_MATCH = 'Y' " +
			"       AND (A.APM_MATCH_TYP = 'NMT' OR (A.APM_MATCH_TYP = 'MAN' AND A. MAN_MATCH_DT > TRUNC(SYSDATE)) )" +
			"       AND A.LOCK_IND = 'N' " ;
		
		
	
	public static final String MEDLEY_GROUP_PERFS_ORIGINAL_COLUMNS_CATALOG = 
			
			" SELECT "+ 
			" APM_ARCHIVE_ID, APM_CATALOG_ID, APM_MATCH_DT,  "+ 
			 "  APM_MATCH_TYP, ASG_USR_ID, CRE_DT,  "+
			  " CRE_ID, DEL_FL, DEL_RSN_CDE,  "+
			   " EO_DETAIL_ID, ERR_STATUS, LOCK_IND,  "+ 
			   " MAN_MATCH_DT, MAN_MATCH_USR_ID, MULT_WRK_ID,  "+ 
			   " PERF_PERIOD, PERF_QTR, PFR_NA,   "+
			   " PLAY_CNT, PRIORITY, PROC_STATUS,   "+
			   " PROVIDER_ID, SEND_MAN_MATCH, TO_CHAR(A.STATUS_DATE,'MM/DD/YYYY HH24:MI:SS') STATUS_DATE,  "+ 
			   " SUPP_CODE, UPD_DT, UPD_ID,  "+
			   " WRITER, WRK_ID, WRK_ID_VLD,  "+
			   " WRK_TTL ";
			
			
	
	
	
	
 
	public static final String MEDLEY_GROUP_PERFS_START_XXXXXXXXXXXXX = 
		"SELECT FINAL_OUT.APM_PERF_HDR_ID, " +
		"       FINAL_OUT.APM_WRK_PERF_ID, " +
		"       FINAL_OUT.PLAY_CNT, " +
		"       FINAL_OUT.MEDL_SEQ, " +
		"       FINAL_OUT.PFR_NA, " +
		"       FINAL_OUT.WRK_TTL, " +
		"       FINAL_OUT.WRK_ID, " +
		"       FINAL_OUT.WRK_SEQ_NR, " +
		"       FINAL_OUT.USE_TYP, " +
		"       FINAL_OUT.PROC_STATUS, " +
		"       FINAL_OUT.STATUS_DATE, " +
		"       FINAL_OUT.PROVIDER_ID, " +
		"       FINAL_OUT.ERR_STATUS, " +
		"       FINAL_OUT.LINE_NR, " +
		"       FINAL_OUT.LOCK_IND, " +
		"       FINAL_OUT.INST_VOCL, " +
		"       FINAL_OUT.SUPP_CODE, " +
		"       FINAL_OUT.WRK_ID_VLD, " +
		"       FINAL_OUT.PLAY_CNT_VLD, " +
		"       FINAL_OUT.SEND_MAN_MATCH, " +
		"       FINAL_OUT.WRK_PERF_DUR_VLD, " +
		"       FINAL_OUT.ESTM_DOLLAR_VAL, " +
		"       FINAL_OUT.ESTM_DLRVAL_FOUND, " +
		"       FINAL_OUT.EO_DETAIL_ID, " +
		"       FINAL_OUT.TGTSURVYEARQTR, FINAL_OUT.MULT_WRK_ID " +
		"  FROM PAPM.APM_WRK_PERF FINAL_OUT, " +
		"       (  SELECT A.SUPP_CODE, A.WRK_TTL, A.PFR_NA " +
		"            FROM PAPM.APM_WRK_PERF A " +
		"           WHERE     A.TGTSURVYEARQTR = ? " +
		"                 AND A.DEL_FL = 'N' " ;

	
	
	public static final String MEDLEY_RESET_ORIGINAL_PERFS_START = " UPDATE APM_WRK_PERF SET WRK_ID = NULL, APM_MATCH_TYP = 'NMT', MAN_MATCH_DT = NULL, MAN_MATCH_USR_ID = NULL, UPD_DT = SYSDATE, UPD_ID = ? WHERE DEL_FL = 'N' AND NVL(APM_ARCHIVE_ID,0) > 0 AND NVL(EO_DETAIL_ID,0) > 0 ";
	public static final String MEDLEY_RESET_ORIGINAL_PERFS_END = "";
	
	public static final String MEDLEY_RESET_MEDLEY_PERFS_START = " UPDATE APM_WRK_PERF SET DEL_FL = 'Y', UPD_DT = SYSDATE, UPD_ID = ?  WHERE DEL_FL = 'N' AND NVL(APM_ARCHIVE_ID,0) = 0 AND NVL(EO_DETAIL_ID,0) = 0 ";
	public static final String MEDLEY_RESET_MEDLEY_PERFS_END = "";


	public static final String UNMATCH_MEDELY_LEANRED_MATCH="UPDATE APM_LEARNED_MATCH SET DEL_FL = 'Y', UPD_DT = SYSDATE, UPD_ID= ? WHERE DEL_FL = 'N' AND (WRK_ID, WRK_TTL, PFR_NA)  IN ( " +
	"select WRK_ID_VLD , WRK_TTL, PFR_NA from apm_wrk_perf where mult_wrk_id = ? AND  del_fl = 'Y'  GROUP BY  WRK_ID_VLD, WRK_TTL, PFR_NA " +
	"MINUS " +
	"select WRK_ID_VLD, WRK_TTL, PFR_NA  from apm_wrk_perf where mult_wrk_id = ? AND  del_fl = 'N'  GROUP BY  WRK_ID_VLD, WRK_TTL, PFR_NA " +
	") " ;
	
	public static final String UNMATCH_MEDELY_LEANRED_MATCH_CATALOG="UPDATE APM_LEARNED_MATCH SET DEL_FL = 'Y', UPD_DT = SYSDATE, UPD_ID= ? WHERE DEL_FL = 'N' AND (WRK_ID, WRK_TTL, PFR_NA)  IN ( " +
			"select WRK_ID_VLD , WRK_TTL, PFR_NA from apm_catalog where mult_wrk_id = ? AND  del_fl = 'Y'  GROUP BY  WRK_ID_VLD, WRK_TTL, PFR_NA " +
			"MINUS " +
			"select WRK_ID_VLD, WRK_TTL, PFR_NA  from apm_catalog where mult_wrk_id = ? AND  del_fl = 'N'  GROUP BY  WRK_ID_VLD, WRK_TTL, PFR_NA " +
			") " ;
	
	
	
	public static final String UNMATCH_MEDELY_LEANRED_MATCH_WTR="UPDATE APM_LEARNED_MATCH_WTR SET DEL_FL = 'Y', UPD_DT = SYSDATE, UPD_ID= ? WHERE DEL_FL = 'N' AND (WRK_ID, WRK_TTL, WRITER)  IN ( " +
	"select WRK_ID_VLD , WRK_TTL, WRITER from apm_wrk_perf where mult_wrk_id = ? AND  del_fl = 'Y'  GROUP BY  WRK_ID_VLD, WRK_TTL, WRITER " +
	"MINUS " +
	"select WRK_ID_VLD, WRK_TTL, WRITER  from apm_wrk_perf where mult_wrk_id = ? AND  del_fl = 'N'  GROUP BY  WRK_ID_VLD, WRK_TTL, WRITER " +
	") " ;
	
	public static final String UNMATCH_MEDELY_LEANRED_MATCH_WTR_CATALOG="UPDATE APM_LEARNED_MATCH_WTR SET DEL_FL = 'Y', UPD_DT = SYSDATE, UPD_ID= ? WHERE DEL_FL = 'N' AND (WRK_ID, WRK_TTL, WRITER)  IN ( " +
			"select WRK_ID_VLD , WRK_TTL, WRITER from apm_catalog where mult_wrk_id = ? AND  del_fl = 'Y'  GROUP BY  WRK_ID_VLD, WRK_TTL, WRITER " +
			"MINUS " +
			"select WRK_ID_VLD, WRK_TTL, WRITER  from apm_catalog where mult_wrk_id = ? AND  del_fl = 'N'  GROUP BY  WRK_ID_VLD, WRK_TTL, WRITER " +
			") " ;

	
	
	public static final String INSERT_LM_BULK_UPD_REQ = "INSERT INTO PAPM.LM_BULK_UPD_REQ (LM_BULK_UPD_REQ_ID, " +
	"                                  STATUS, " +
	"                                  SUPP_CODE, " +
	"                                  WRK_TTL, " +
	"                                  PFR_NA, " +
	"                                  WRITER, " +
	"                                  ORIG_MULT_WRK_ID, " +
	"                                  ORIG_WRK_ID, " +
	"                                  CRE_DT, " +
	"                                  CRE_ID) " +
	"     VALUES (?, " +
	"             ?, " +
	"             ?, " +
	"             ?, " +
	"             ?, " +
	"             ?, " +
	"             ?, " +
	"             ?, " +
	"             SYSDATE, " +
	"             ?)";  
	
	public static final String GET_LM_BULK_UPD_REQ = "SELECT LM_BULK_UPD_REQ_ID FROM PAPM.LM_BULK_UPD_REQ WHERE STATUS = ? AND DEL_FL = 'N' AND WRK_TTL = ? ";
	
	public static final String UPDATE_LM_BULK_UPD_REQ = "UPDATE PAPM.LM_BULK_UPD_REQ SET STATUS = ?, UPD_ID = ?, UPD_DT = SYSDATE WHERE LM_BULK_UPD_REQ_ID = ?";

	public static final String GET_EXEMPTION = "SELECT A.SM_DEDUP_EXEMPTIONS_ID, "+
	" CASE " +
	"                  WHEN A.WRK_ID IS NOT NULL THEN 'WRK_ID' " +
	"                  WHEN A.PTY_ID IS NOT NULL THEN 'PTY_ID' " +
	"                  WHEN A.PROVIDER_ID IS NOT NULL THEN 'PROVIDER_ID' " +
	"                  WHEN A.WRK_TTL IS NOT NULL THEN 'WRK_TTL' " +
	"                  WHEN A.LIBRARY_DISC_ID IS NOT NULL THEN 'LIBRARY_DISC_ID' " +
	"                  WHEN A.WRITER IS NOT NULL THEN 'WRITER' " +
	"               END " +
	"                  EXEMPTION_TYPE, A.DISPLAY_VAL FROM SM_DEDUP_EXEMPTIONS A WHERE A.SM_DEDUP_EXEMPTIONS_ID = ? AND A.DEL_FL = 'N' " ;
	
	
	public static final String GET_EXEMPTION_LIST_START = "SELECT * " +
	"  FROM (SELECT A.SM_DEDUP_EXEMPTIONS_ID, " +
	"               A.WRK_ID, " +
	"               A.PTY_ID, " +
	"               A.PROVIDER_ID, " +
	"               A.WRK_TTL, " +
	"               A.LIBRARY_DISC_ID, " +
	"               A.WRITER, " +
	"               A.DISPLAY_VAL, " +
	"               CASE " +
	"                  WHEN WRK_ID IS NOT NULL THEN 'WRK_ID' " +
	"                  WHEN PTY_ID IS NOT NULL THEN 'PTY_ID' " +
	"                  WHEN PROVIDER_ID IS NOT NULL THEN 'PROVIDER_ID' " +
	"                  WHEN WRK_TTL IS NOT NULL THEN 'WRK_TTL' " +
	"                  WHEN LIBRARY_DISC_ID IS NOT NULL THEN 'LIBRARY_DISC_ID' " +
	"                  WHEN WRITER IS NOT NULL THEN 'WRITER' " +
	"               END " +
	"                  EXEMPTION_TYPE, " +
	"               ROW_NUMBER () OVER (ORDER BY ";
	
	public static final String GET_EXEMPTION_LIST_ORDER_BY_PTY_ID = " A.PTY_ID ";
	public static final String GET_EXEMPTION_LIST_ORDER_BY_WRK_ID = " A.WRK_ID ";
	public static final String GET_EXEMPTION_LIST_ORDER_BY_PROVIDER_ID = " A.PROVIDER_ID ";
	public static final String GET_EXEMPTION_LIST_ORDER_BY_DISPLAY_VAL = " A.DISPLAY_VAL ";
	
	
	public static final String GET_EXEMPTION_LIST_MIDDLE = 
	" ) RN, " +
	"               COUNT (*) OVER () TOTAL_ROWS " +
	"          FROM SM_DEDUP_EXEMPTIONS A " +
	"         WHERE A.DEL_FL = 'N' ";
	
	
	public static final String GET_EXEMPTION_LIST_END = 
	" ) " +
	" WHERE RN BETWEEN ? AND ? " ; 

	public static final String DYNAMIC_INSERT_EXEMPTION = "INSERT INTO SM_DEDUP_EXEMPTIONS ";
	
	public static final String DYNAMIC_UPDATE_EXEMPTION = "UPDATE SM_DEDUP_EXEMPTIONS SET UPD_DT = SYSDATE ";
	
	public static final String GET_EXEMPTION_BY_VALUE = " SELECT A.SM_DEDUP_EXEMPTIONS_ID, " +
	"               A.WRK_ID, " +
	"               A.PTY_ID, " +
	"               A.PROVIDER_ID, " +
	"               A.WRK_TTL, " +
	"               A.LIBRARY_DISC_ID, " +
	"               A.WRITER, " +
	"               A.DISPLAY_VAL FROM SM_DEDUP_EXEMPTIONS A WHERE DEL_FL = 'N' ";
	
	
	public static final String GET_EO_LEARNED_MATCH_LIST_START = "SELECT * FROM ( " ;
	
	public static final String GET_EO_LEARNED_MATCH_COLUMN_LIST_ISRC =	"  SELECT ID,  ISRC AS MATCHTYPE, WRKID, NULL AS MULTWRKID, " ;
	public static final String GET_EO_LEARNED_MATCH_COLUMN_LIST_ISWC =	"  SELECT ID,  ISWC AS MATCHTYPE, WRKID, NULL AS MULTWRKID, " ;
	public static final String GET_EO_LEARNED_MATCH_COLUMN_LIST_SUPPLIER_ID =	"  SELECT ID, SUPPCODE, SUPPSONGID AS MATCHTYPE, WRKID, MULT_WRK_ID AS MULTWRKID, " ;
	public static final String GET_EO_LEARNED_MATCH_COLUMN_LIST_URL =	"   SELECT ID,  URL AS MATCHTYPE, WRKID,  MULTWRKID, " ;
	
	public static final String GET_EO_LEARNED_MATCH_LIST_MIDDLE_START =
	"  ROW_NUMBER() OVER (ORDER BY " ; //ISRC) RN, " +
	
	public static final String GET_EO_LEARNED_MATCH_LIST_MIDDLE_COLUMN_ISRC = " ISRC ";
	public static final String GET_EO_LEARNED_MATCH_LIST_MIDDLE_COLUMN_ISWC = " ISWC ";
	public static final String GET_EO_LEARNED_MATCH_LIST_MIDDLE_COLUMN_URL = " URL ";
	public static final String GET_EO_LEARNED_MATCH_LIST_MIDDLE_COLUMN_SUPPLIER_ID = " SUPPSONGID ";
	
	public static final String GET_EO_LEARNED_MATCH_LIST_MIDDLE_END =		
		" ) RN, " +
		"  COUNT (*) OVER () TOTAL_ROWS " +
		"  FROM " ;
		
	public static final String GET_EO_LEARNED_MATCH_ISRC = "SELECT wrkid, isrc from IDMatching.dbo.LearnedMatchISRC where DelFlag = 'N' and isrc = FileInventory.dbo.ValidateIndustryIds('ISRC',?) ";
	public static final String GET_EO_LEARNED_MATCH_ISWC = "SELECT wrkid, iswc from IDMatching.dbo.LearnedMatchISWC where DelFlag = 'N' and iswc = FileInventory.dbo.ValidateIndustryIds('ISWC',?) ";
	public static final String GET_EO_LEARNED_MATCH_URL = "SELECT wrkid, Url from IDMatching.dbo.LearnedMatchUrl where DelFlag = 'N' and Url = ? ";
	public static final String GET_EO_LEARNED_MATCH_SUPPLIER_ID = "SELECT wrkid, suppsongid from IDMatching.dbo.LearnedMatchSuppId where DelFlag = 'N' and suppsongid = ? and suppcode = ? ";
	
	
	public static final String GET_EO_LEARNED_MATCH_TABLE_NAME_ISRC = " IDMatching.dbo.LearnedMatchISRC where DelFlag = 'N' ";
	public static final String GET_EO_LEARNED_MATCH_TABLE_NAME_ISWC = " IDMatching.dbo.LearnedMatchISWC where DelFlag = 'N' ";
	public static final String GET_EO_LEARNED_MATCH_TABLE_NAME_URL = " IDMatching.dbo.LearnedMatchUrl where DelFlag = 'N' ";
	public static final String GET_EO_LEARNED_MATCH_TABLE_NAME_SUPPLIER_ID = " IDMatching.dbo.LearnedMatchSuppId where DelFlag = 'N' ";
	
	public static final String GET_EO_LEARNED_MATCH_LIST_END = "  " +
	"  ) A " +
	"  WHERE A.RN BETWEEN ? AND ? " ;

	
	public static final String UPDATE_EO_FILEINVENTORY = "UPDATE FileInventory.dbo.FileInventory SET ErrorCorrStatus = ?, updId = ?, updDt = GetDate()  where PerfQtr=? and  SuppCode = ? ";
	
	public static final String VALIDATE_APM_WORKS = "SELECT WRK_ID,  WRK_TYP_CDE, nvl(SUR_WRK_IND,'N') SUR_WRK_IND, nvl(POSS_MCH_IND,'N') POSS_MCH_IND, nvl(WRK_GRD_VAL,0) WRK_GRD_VAL, nvl(PUB_DOM_IND,'N') PUB_DOM_IND " +
	"  FROM   mvw_wrk  WHERE  WRK_ID = ? ";
	
	/* CATALOG MATCH QUERIES*/
	
	public static final String GET_CATALOG_MATCH_INTERSECT = " INTERSECT ";
	public static final String GET_CATALOG_MATCH_SELECT = "SELECT ";
	public static final String GET_CATALOG_MATCH_SELECT_INITIAL_SPLR_COL = "SUPP_CODE, PROVIDER_ID, ";
	public static final String GET_CATALOG_MATCH_SELECT_INITIAL_NSPLR_COL = "NULL SUPP_CODE, ";
	public static final String GET_CATALOG_MATCH_SELECT_INITIAL_COMMON_COL = " PFR_NA, " +
	"       WRK_TTL, WRITER," +
	"       WRK_PERF_CNT, " +
	"       WRK_ID, " +
	"       MAN_MATCH_USR_ID, " +
	"       DEL_FL, " +
	"       PLAY_CNT, " +
//	"       TRIM (TO_CHAR (EST_DOL_AMT, '$999,999,999,999,990.99')) EST_DOL_AMT, EST_DLR_FOUND, " +
	"   	PRIORITY, "+
	"       MULT_WRK_ID, ASG_USR_ID, " +
	"       TOTALROWS ";
	public static final String GET_CATALOG_MATCH_FROM = " FROM ";
	public static final String GET_CATALOG_MATCH_PAREN_OPEN = " ( ";
	public static final String GET_CATALOG_MATCH_PAREN_CLOSE = " ) ";
	public static final String GET_CATALOG_MATCH_SELECT_INNER_SPLR_COL = " SUPP_CODE SUPP_CODE, " +
	"                    PROVIDER_ID, " ;

	public static final String GET_CATALOG_MATCH_SELECT_INNER_NSPLR_COL = "  " ;
	public static final String GET_CATALOG_MATCH_SELECT_INNER_COMMON_COL = "                   PFR_NA, " +
	"                 WRK_TTL, WRITER, " +
	"                 WRK_ID, " +
	"                 MAN_MATCH_USR_ID, " +
	"                 DEL_FL, ASG_USR_ID, " +
	"				MIN(NVL(PRIORITY,1)) PRIORITY, "+
	"                 COUNT (1) WRK_PERF_CNT, " +
	"                 SUM (PLAY_CNT) PLAY_CNT, " +
	//"                 SUM (NVL (ESTM_DOLLAR_VAL, 0)) EST_DOL_AMT, " +
	//"                 MIN (ESTM_DLRVAL_FOUND) EST_DLR_FOUND,  MULT_WRK_ID, " +
	"                 MULT_WRK_ID, ROW_NUMBER () " +
	"                 OVER ( " +
	"                    ORDER BY " +
	"                        MIN(NVL(PRIORITY,1)) ASC, " +
	//"                       COUNT (1) DESC, " +
	"                       WRK_TTL,  NVL(PFR_NA, WRITER) ) " +
	"                    RN, " +
	"                 COUNT (*) OVER () TOTALROWS ";
	
	public static final String ORACLE_INDEX_HINT_CATALOG = " /*+  INDEX(A APM_CATALOG_IX18) */ ";
	
	public static final String GET_CATALOG_MATCH_SELECT_INNER_INNER_SPLR_COL = "  A.SUPP_CODE SUPP_CODE, A.PROVIDER_ID, " ;
	public static final String GET_CATALOG_MATCH_SELECT_INNER_INNER_NSPLR_COL = "  " ;
	public static final String GET_CATALOG_MATCH_SELECT_INNER_INNER_COMMON_COL = "                         A.PFR_NA, " +
	"                         A.WRK_TTL, A.WRITER, " +
	"                         A.WRK_ID, " +
	"                         A.MAN_MATCH_USR_ID, " +
	"                         A.DEL_FL, " +
	"                         A.PLAY_CNT, " +
	"                         A.PRIORITY, " +
	//"                         A.ESTM_DOLLAR_VAL, A.ESTM_DLRVAL_FOUND," +
	"                          A.MULT_WRK_ID, A.ASG_USR_ID " +
	"                    FROM APM_CATALOG A ";
	
	
	
	
	public static final String GET_CATALOG_MATCH_CONTEXT_START = " WITH CTX AS "  +
		"( " +
		"SELECT /*+ materialize */ * FROM ( " ;
	
	public static final String GET_CATALOG_MATCH_CONTEXT_END = " )) ";
	public static final String GET_CATALOG_MATCH_CONTEXT_JOIN = " , CTX ";
	public static final String GET_CATALOG_MATCH_CONTEXT_WHERE_CLAUSE = " AND A.APM_CATALOG_ID = CTX.APM_CATALOG_ID ";
	
	public static final String GET_CATALOG_MATCH_WHERE_CLAUSE = " WHERE A.PROC_STATUS = 'BUSVLD' " +
	"                         AND A.SEND_MAN_MATCH = 'Y' " +
	"                         AND A.LOCK_IND = 'N' " +
	" 						  AND A.WRK_TTL IS NOT NULL  " ;
	//" 						  AND A.TGTSURVYEARQTR =  " ;
	

	public static final String GET_CATALOG_MATCH_UNION_ALL = " UNION ALL ";
	public static final String GET_CATALOG_MATCH_GROUP_BY = " GROUP BY ";
	public static final String GET_CATALOG_MATCH_GROUP_BY_SPLR_COL = " SUPP_CODE, PROVIDER_ID, " ;
//	"                 (CASE " +
//	"                     WHEN SUPP_CODE = 'MEDIAMONITOR' THEN PROVIDER_ID  WHEN SUPP_CODE = 'NIELSEN'  THEN PROVIDER_ID" +
//	"                     ELSE NULL " +
//	"                  END), ";
	public static final String GET_CATALOG_MATCH_GROUP_BY_COMMON_COL = "  PFR_NA, " +
	"                 WRK_TTL, WRITER, " +
	"                 WRK_ID, " +
	"                 MAN_MATCH_USR_ID, " +
	"                 DEL_FL,  MULT_WRK_ID, ASG_USR_ID ";
	
	public static final String GET_CATALOG_MATCH_ROWNUM_WHERE_CLAUSE = "  WHERE RN BETWEEN ? AND ? ";
	public static final String GET_CATALOG_MATCH_ORDER_CLAUSE = "  ORDER BY RN ";
	
	public static final String GET_CATALOG_MATCH_PFR_BEGINS_SEARCH = " AND A.PFR_NA LIKE ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'NAME', 'BGNS') FROM DUAL) ";
	public static final String GET_CATALOG_MATCH_TTL_BEGINS_SEARCH = " AND A.WRK_TTL LIKE ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'TITLE', 'BGNS') FROM DUAL) ";

	public static final String GET_CATALOG_MATCH_PFR_CONTAINS_SEARCH = " SELECT CT1.APM_CATALOG_ID FROM PAPM.APM_CATALOG CT1 WHERE CATSEARCH (CT1.PFR_NA, ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'NAME', 'CNTS') FROM DUAL), '') > 0 ";
	public static final String GET_CATALOG_MATCH_TTL_CONTAINS_SEARCH = " SELECT  CT.APM_CATALOG_ID FROM PAPM.APM_CATALOG CT WHERE CATSEARCH (CT.WRK_TTL, ( SELECT  PKG_SEARCH_UTILS.FNC_FORMAT_SEARCH_PARAM(?, 'TITLE', 'CNTS') FROM DUAL), '') > 0 ";
	
	public static final String GET_CATALOG_MATCH_MATCH_STATUS_NOT_MATCHED = " AND A.APM_MATCH_TYP = 'NMT' AND A.WRK_ID IS NULL  AND A.DEL_FL = 'N' ";
	public static final String GET_CATALOG_MATCH_MATCH_STATUS_MATCHED = " AND A.APM_MATCH_TYP = 'MAN' AND A.DEL_FL = 'N' AND A.MAN_MATCH_DT >= TRUNC (SYSDATE) ";
	public static final String GET_CATALOG_MATCH_MATCH_STATUS_DELETED = " AND A.APM_MATCH_TYP = 'MAN' AND A.DEL_FL = 'Y' AND A.MAN_MATCH_DT >= TRUNC (SYSDATE) AND A.DEL_RSN_CDE = 'MAN_DEL' ";
	public static final String GET_CATALOG_MATCH_MATCH_STATUS_MATCHED_OR_DELETED = " AND A.APM_MATCH_TYP = 'MAN' AND A.MAN_MATCH_DT >= TRUNC (SYSDATE) AND (A.DEL_FL = 'N' OR (A.DEL_FL = 'Y' AND A.DEL_RSN_CDE = 'MAN_DEL')) ";
	// ==================================================================================================================================
	
	
	
	
	
	public static final String GET_SPLR_CONTEXT_END_CATALOG = " )) ";
	public static final String GET_SPLR_CONTEXT_JOIN_CATALOG = " , CTX ";
	public static final String GET_SPLR_CONTEXT_WHERE_CLAUSE_CATALOG = " AND A.APM_CATALOG_ID = CTX.APM_CATALOG_ID ";
	
	
	
	
	
}

