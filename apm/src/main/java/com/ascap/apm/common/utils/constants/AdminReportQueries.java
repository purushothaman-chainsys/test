package com.ascap.apm.common.utils.constants;

/**
 * @author vzayyadevara
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface AdminReportQueries {
	public static final String DELETE_FLAG_YES = "Y";
	public static final String DELETE_FLAG_NO = "N";
	public static final String ADMIN_REPORT_UPDATE_SCHEDULE_INFO="update rpt_sch_inf SET DEL_FL='Y',UPD_ID=?,UPD_DT=SYSDATE where RPT_NA=?";	//3
	public static final String ADMIN_REPORT_SELECT_SCHEDULE_INFO="select RPT_NA from rpt_sch_inf where DEL_FL='N'";
	//public static final String REPORT_INSERT_SCHEDULE_INFO="insert into rpt_sch_inf(RPT_SCH_ID,RPT_NA,SCH_LATER,SCH_TM,CRE_ID,CRE_DT,UPD_ID,UPD_DT,DEL_FL) VALUES (RPT_SCH_ID_SEQ.nextval,		?,		 ?,		 	?,	  	?,	 	SYSDATE,     ?,		 SYSDATE,     'N')";
	//public static final String ADMIN_REPORT_GET_LAST_SCH_ID="select MAX(RPT_SCH_ID) from rpt_sch_inf";
	
	public static final String ADMIN_REPORT_SELECT_All_VALIDATE_INFO="select RPT_NA from rpt_vldtn_inf where DEL_FL='N'";
	public static final String ADMIN_REPORT_UPDATE_VALIDATE_INFO="update rpt_vldtn_inf SET DEL_FL='Y',UPD_ID=?,UPD_DT=SYSDATE where RPT_NA=?";	
	
	
	
	public static final String ADMIN_SELECT_SCHEDULE_INFO=
	"SELECT sch_latr, sch_tm, usr_inst_cnt, arch_inst_cnt, inst_max_days_cnt, " +
	"       rpt_max_inst_fl, rpt_inst_max_days_fl, rpt_run_fl, rpt_export_fl,db_typ_cde " +
	"  FROM rpt_sch_inf " +
	" WHERE rpt_na = ? ";


	
	public static final String ADMIN_SELECT_VALIDATE_INFO="select STR_PROC_NA, DEL_FL from rpt_vldtn_inf where RPT_NA=?";
	
	
	
	public static final String ADMIN_UPDATE_SCHEDUE_INFO="update rpt_sch_inf set SCH_LATR=?, SCH_TM=? ,UPD_ID=?,USR_INST_CNT=?,ARCH_INST_CNT=?,INST_MAX_DAYS_CNT=?,RPT_MAX_INST_FL=?,RPT_INST_MAX_DAYS_FL=?,UPD_DT=SYSDATE,DEL_FL='N', RPT_RUN_FL=? ,RPT_EXPORT_FL=? ,DB_TYP_CDE=? where RPT_NA=?";
	public static final String ADMIN_UPDATE_VALIDATE_REPORT="update rpt_vldtn_inf set UPD_ID=?, UPD_DT=SYSDATE, DEL_FL=? where RPT_NA=?";
	
    
         																								        //  RPT_VLDTN_ID	              RPT_NA	 PARAM_ORDER  STR_PROC_NA CRE_ID   CRE_DT  	UPD_ID   UPD_DT	  DEL_FL
    public static final String ADMIN_INSERT_VALIDATE_INFO="insert into rpt_vldtn_inf(RPT_VLDTN_ID,RPT_NA,STR_PROC_NA,CRE_ID,CRE_DT,UPD_ID,UPD_DT,DEL_FL) VALUES (RPT_VLDTN_ID_SEQ.NEXTVAL,		      ?,		 	 ?,	  	    ?,	   SYSDATE,   ?,	?,  'N')"; 

   	
    public static final String ADMIN_INSERT_SCHEDULE_INFO="insert into rpt_sch_inf(RPT_SCH_ID,RPT_NA,SCH_LATR,SCH_TM,USR_INST_CNT,ARCH_INST_CNT,INST_MAX_DAYS_CNT,RPT_MAX_INST_FL,RPT_INST_MAX_DAYS_FL,CRE_ID,CRE_DT,UPD_ID,UPD_DT,DEL_FL, RPT_RUN_FL, RPT_EXPORT_FL, DB_TYP_CDE ) VALUES (RPT_SCH_ID_SEQ.NEXTVAL,		?,		 ?,		 	?,?,?,?,?,?,	  	?,	 	SYSDATE,     ?,		 ?,     'N', ?, ?, ?)";
        		

}
