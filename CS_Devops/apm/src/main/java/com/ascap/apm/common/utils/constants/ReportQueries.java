package com.ascap.apm.common.utils.constants;

/**
 * @author mzbamidi
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface ReportQueries {

	
	public static final String GET_SCHEDULE_INFO_DETAILS="Select rpt_sch_id,rpt_na,sch_latr,sch_tm from rpt_sch_inf where rpt_na=? And del_fl='N'";
	//public static final String REPORT_GET_REPORT_FOLDER_ID = "Select SI_ID,SI_NAME,SI_DESCRIPTION,SI_PROGID,SI_PARENTID,SI_CHILDREN from CI_INFOOBJECTS Where SI_PROGID='CrystalEnterprise.Folder' And SI_INSTANCE=0 And SI_NAME='";
	//public static final String REPORT_GET_LIST_OF_REPORTS="Select SI_NAME,SI_ID,SI_DESCRIPTION,SI_PARENTID from CI_INFOOBJECTS Where SI_PROGID='CrystalEnterprise.Report' And SI_INSTANCE=0 And SI_PARENTID=";
	//public static final String REPORT_GET_REPORT_OBJECT="Select * from CI_INFOOBJECTS Where SI_PROGID='CrystalEnterprise.Report' And SI_INSTANCE=0 And SI_ID='";
	//public static final String REPORT_GET_REPORT_PARAMETERS="Select * from CI_INFOOBJECTS Where SI_PROGID='CrystalEnterprise.Report' And SI_INSTANCE=0 And SI_ID='";
	//public static final String REPORT_GET_INSTANCE_STATUS="Select * from CI_INFOOBJECTS Where SI_PROGID IN ('CrystalEnterprise.PDF','CrystalEnterprise.Report') AND  SI_INSTANCE=1 And SI_PARENTID='";
	//public static final String REPORT_GET_INSTANCE_STATUS_FILTER= "' ORDER BY SI_CREATION_TIME DESC";
	public static final String REPORT_INSERT_INSTANCE_DETAILS="INSERT INTO rpt_inst_inf (RPT_INST_ID, RPT_NA, USR_ID,REQ_DT, PTY_ID, CRE_ID,CRE_DT,UPD_ID,UPD_DT,DEL_FL) VALUES (?, ?, ?, ?, ?, ?, SYSDATE, ?, SYSDATE, 'N')";
	public static final String REPORT_DELETE_INSTANCE_DETAILS="UPDATE rpt_inst_inf SET DEL_FL = 'Y',UPD_ID=?,UPD_DT=SYSDATE WHERE RPT_INST_ID = ?"; // 	AND RPT_NA=?";
	public static final String UPDATE_REPORT_INSTANCE_DETAILS="UPDATE rpt_inst_inf SET RTN_END_DTT =? WHERE RPT_INST_ID = ?";
	
	public static final String GET_USER_INSTANCE_DETAILS="Select USR_ID from rpt_inst_inf where RPT_NA=? And RPT_INST_ID=?";
	public static final String GET_VALIDATE_INFO_DETAILS="Select STR_PROC_NA from RPT_VLDTN_INF where RPT_NA=? And DEL_FL='N'";
	
	//jason
	//public static final String GET_INSTANCE_DETAILS="Select RPT_INST_ID,RPT_NA,USR_ID,REQ_DT,RTN_END_DTT, RPT_EXPORT_FL from rpt_inst_inf where RPT_INST_ID= ? And DEL_FL='N'";
	/*public static final String GET_INSTANCE_DETAILS="SELECT rii.rpt_inst_id, rii.rpt_na, rii.usr_id, rii.req_dt, rii.rtn_end_dtt, rsi.rpt_export_fl " +
													"  FROM rpt_inst_inf rii, rpt_sch_inf rsi " +
													" WHERE rii.rpt_na = rsi.rpt_na " +
													"   AND rii.rpt_inst_id = ? " +
													"   AND rii.del_fl = 'N' " +
													"   AND rsi.del_fl = 'N' ";*/
	
	public static final String GET_INSTANCE_DETAILS="SELECT rii.rpt_inst_id, rii.rpt_na, rii.usr_id, rii.req_dt, rii.rtn_end_dtt, " +
	"       a.rpt_export_fl " +
	"  FROM rpt_inst_inf rii, " +
	"       rpt_sch_inf rsi, " +
	"       (SELECT rpt_export_fl " +
	"          FROM rpt_sch_inf " +
	"         WHERE rpt_na = ? AND del_fl = 'N') a " +
	" WHERE rsi.rpt_na = ? " +
	"   AND rii.rpt_na(+) = rsi.rpt_na " +
	"   AND rii.rpt_inst_id(+) = ? " +
	"   AND rii.del_fl(+) = 'N' " +
	"   AND rsi.del_fl = 'N' ";


	
	
	public static final String CHECK_STORED_PROC_IN_DATABASE="Select object_name from all_objects where owner = 'PAPM' and object_name = ?";
	
	public static final String GET_USER_REPORT_REQUEST_LIST_COUNT=" SELECT COUNT (1) "+
		"  FROM rpt_req, sta "+
		" WHERE rpt_req.usr_id = ? "+
		"	AND rpt_req.rpt_id = ? " +
		"   AND rpt_req.sta_cde = sta.sta_cde "+
		"   AND sta.sta_cde <> 'CA' " +
		"	AND rpt_req.del_fl ='N' " +
		"	AND sta.del_fl = 'N' ";

	
	public static final String GET_USER_REPORT_REQUEST_LIST=" SELECT a.rpt_req_id, a.usr_id, a.rpt_id, a.sta_cde, a.des, a.rpt_sta_order, " +
		"       a.sta_na AS sta_na, TO_CHAR(a.cre_dt, 'MM/DD/YYYY HH12:MI:SS AM') as cre_dt, a.sta_sta_cde, a.fname, a.lname " +
		"  FROM (SELECT ROW_NUMBER () OVER (ORDER BY " +
		"                rpt_req.cre_dt DESC) rn, "+
		"               rpt_req.rpt_req_id, rpt_req.usr_id, rpt_req.rpt_id, "+
		"               rpt_req.sta_cde, rpt_req.des, sta.rpt_sta_order, "+
		"               sta.na AS sta_na, sta.sta_cde sta_sta_cde,rpt_req.cre_dt, user_pro.fname, user_pro.lname "+
		"          FROM rpt_req, sta, user_pro "+
		"         WHERE rpt_req.usr_id = ? "+
		"           AND user_pro.usr_id = rpt_req.usr_id " +
		"			AND rpt_req.rpt_id = ? " +
		"           AND rpt_req.sta_cde = sta.sta_cde "+
		"           AND sta.sta_cde <> 'CA' " +
		"			AND rpt_req.del_fl ='N' " +
		"			AND sta.del_fl = 'N') a "+
		" WHERE rn BETWEEN ? AND ? ";
	
	public static final String GET_USERS_NAME=" SELECT FNAME, LNAME FROM USER_PRO WHERE USR_ID = ? ";

	public static final String GET_REPORT_REQUEST_DETAIL = "SELECT rq.rpt_req_id, rq.usr_id, rq.rpt_id, rq.sta_cde, rq.des, TO_CHAR(rq.cre_dt, 'MM/DD/YYYY HH12:MI:SS AM') as cre_dt, " +
"       rps.rpt_prm_sel_id, rps.rpt_prm_cde, rps.rpt_prm_dat_typ_cde, " +
"       rpvs.rpt_prm_val_sel_id, rpvs.rpt_prm_val, rp.na AS rep_prm_na, " +
"       rpdt.na AS rep_req_data_typ_na, sta.na AS rep_req_sta_na, " +
"       sta.rpt_sta_order, user_pro.fname, user_pro.lname " +
"  FROM rpt_req rq, " + 
"       rpt_prm_sel rps, " +
"       rpt_prm_val_sel rpvs, " +
"       rpt_prm rp, " +
"       rpt_prm_dat_typ rpdt, " +
"       sta, " +
"       user_pro " +
" WHERE rq.rpt_req_id = rps.rpt_req_id " +
"   AND rps.rpt_prm_sel_id = rpvs.rpt_prm_sel_id " +
"   AND rq.sta_cde = sta.sta_cde " +
"   AND rps.rpt_prm_cde = rp.rpt_prm_cde " +
"   AND rps.rpt_prm_dat_typ_cde = rpdt.rpt_prm_dat_typ_cde " +
"   AND rq.del_fl = 'N' " +
"   AND rps.del_fl = 'N' " +
"   AND rpvs.del_fl = 'N' " +
"   AND rq.rpt_req_id = ? " +
"   AND user_pro.usr_id = rq.usr_id " + 
" order by rps.RPT_PRM_SEL_ID asc ";

	
	public static final String UPDATE_REPORT_REQUEST_STATUS="UPDATE RPT_REQ SET STA_CDE = ?,UPD_DT = SYSDATE, UPD_ID = ? WHERE RPT_REQ_ID = ?";
	public static final String INSERT_REPORT_REQUEST_DETAILS = "INSERT INTO RPT_REQ (RPT_REQ_ID, USR_ID, RPT_ID, STA_CDE, DES, CRE_ID, CRE_DT) VALUES (?, ?, ?, ?, ?, ?, SYSDATE)";
	public static final String INSERT_REPORT_PARAM_DETAILS = "INSERT INTO RPT_PRM_SEL (RPT_PRM_SEL_ID, RPT_REQ_ID, RPT_PRM_CDE, RPT_PRM_DAT_TYP_CDE, CRE_ID, CRE_DT) VALUES (?, ?, ?, ?, ?, SYSDATE)";
	public static final String INSERT_REPORT_PARAM_VALUE_DETAILS = "INSERT INTO RPT_PRM_VAL_SEL (RPT_PRM_VAL_SEL_ID, RPT_PRM_SEL_ID, RPT_PRM_VAL, CRE_ID, CRE_DT) VALUES (?, ?, ?, ?, SYSDATE)";
	public static final String SEQUENCE_RPT_REQ_ID = "SELECT RPT_REQ_ID.NEXTVAL FROM DUAL";
	public static final String SEQUENCE_RPT_PRM_SEL_ID = "SELECT RPT_PRM_SEL_ID.NEXTVAL FROM DUAL";
	public static final String SEQUENCE_RPT_PRM_VAL_SEL_ID = "SELECT RPT_PRM_VAL_SEL_ID.NEXTVAL FROM DUAL";		
	public static final String GET_RPT_REQ_ID = "SELECT MAX(RPT_REQ_ID) RPT_REQ_ID FROM RPT_REQ WHERE CRE_DT = (SELECT MAX(CRE_DT) FROM RPT_REQ WHERE USR_ID = ? AND RPT_ID = ? AND DEL_FL != 'Y')"; //"SELECT MAX(RPT_REQ_ID) RPT_REQ_ID FROM RPT_REQ WHERE USR_ID = ? AND RPT_ID = ? AND DEL_FL != 'Y' ";
	public static final String GET_RPT_PRM_SEL_ID = "SELECT MAX(RPT_PRM_SEL_ID) RPT_PRM_SEL_ID FROM RPT_PRM_SEL WHERE RPT_REQ_ID = ? AND RPT_PRM_CDE = ? AND DEL_FL != 'Y'";
	
	
	public static final String GET_ALL_DB_INSTANCES ="SELECT DISTINCT RPT_INST_ID FROM RPT_INST_INF WHERE RPT_NA =? AND DEL_FL='N'";
	
	public static final String GET_RPT_INST_ID = "SELECT RPT_INST_ID FROM RPT_INST_INF WHERE RPT_INST_ID =? AND DEL_FL='N'";	
	
	public static final String GET_RPT_INSTS_WITH_RETURN_DATE = "SELECT RPT_INST_ID FROM RPT_INST_INF WHERE DEL_FL='N' AND RTN_END_DTT IS NOT NULL";
	
	public static final String GET_REPORT_DATABASE_TYPE="Select DB_TYP_CDE from RPT_SCH_INF where RPT_NA=? And DEL_FL='N'";

}
