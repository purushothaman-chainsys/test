package com.ascap.apm.common.utils.constants;

/**
 * @author Vinayak_Panse
 *
 * @author Pratap Sanikommu
 * @version $Revision:   1.37  $ $Date:   Nov 17 2010 13:06:06  $
 *
 * To store static JNDI Names used in Controller to call Service
 */
public class JNDINames {

	public static final String ORACLE_DATASOURCE = "jdbc/oracleprepds";
	public static final String ORACLE_ADJUSTMENTS_DATASOURCE = "jdbc/PrepAdjustmentsDS";
	public static final String ORACLE_MODELING_DATASOURCE = "jdbc/PrepModelingDS";
	public static final String ORACLE_LRS_DATASOURCE = "jdbc/PrepLrsDS";
	public static final String ORACLE_DEFAULT_DATASOURCE = "jdbc/ascapDS";
	public static final String SQL_SERVER_EO_DATASOURCE = "jdbc/eoDS";
	

	public static final String ORACLE_MDB_DATASOURCE = "jdbc/mdbDS";

	//  EJB JNDI Constants

	public static final String COPYRIGHTS_SERVICE = "ejb/com/ascap/prep/service/copyrights/CopyrightsServiceHome";
	public static final String COPYRIGHTS_CONTROLLER = "ejb/com/ascap/prep/controller/copyrights/CopyrightsControllerHome";
	public static final String COPYRIGHTS_CONTROLLER_WEB = "java:comp/env/ejb/CopyrightsController";

	public static final String AGREEMENTS_SERVICE = "ejb/com/ascap/prep/service/copyrights/agreements/AgreementsServiceHome";
	public static final String AGREEMENTS_CONTROLLER = "ejb/com/ascap/prep/controller/copyrights/agreements/AgreementsControllerHome";
	public static final String AGREEMENTS_CONTROLLER_WEB = "java:comp/env/ejb/AgreementsController";

    public static final String MANDATE_SERVICE = "ejb/com/ascap/prep/service/copyrights/mandates/MandatesServiceHome";
	public static final String MANDATES_CONTROLLER = "ejb/com/ascap/prep/controller/copyrights/mandates/MandatesControllerHome";
	public static final String MANDATES_CONTROLLER_WEB = "java:comp/env/ejb/MandatesController";

	public static final String WORKS_SERVICE = "ejb/com/ascap/prep/service/copyrights/works/WorksServiceHome";
	public static final String WORKS_CONTROLLER = "ejb/com/ascap/prep/controller/copyrights/works/WorksControllerHome";
	public static final String WORKS_CONTROLLER_WEB = "java:comp/env/ejb/WorksController";

	public static final String WORKS_POSSIBLE_MATCHES_CONTROLLER = "ejb/com/ascap/prep/controller/copyrights/works/PossibleMatchesControllerHome";
	public static final String WORKS_POSSIBLE_MATCHES_CONTROLLER_WEB = "java:comp/env/ejb/PossibleMatchesController";


	public static final String WORKS_RECORDING_CONTROLLER = "ejb/com/ascap/prep/controller/copyrights/works/RecordingControllerHome";
	public static final String WORKS_RECORDING_CONTROLLER_WEB = "java:comp/env/ejb/RecordingController";

	public static final String WORKS_PERFORMER_CONTROLLER = "ejb/com/ascap/prep/controller/copyrights/works/PerformerControllerHome";
	public static final String WORKS_PERFORMER_CONTROLLER_WEB = "java:comp/env/ejb/PerformerController";

	public static final String DISTRIBUTION_SERVICE = "ejb/com/ascap/prep/service/distribution/DistributionServiceHome";
	public static final String DISTRIBUTION_CONTROLLER = "ejb/com/ascap/prep/controller/distribution/DistributionControllerHome";
	public static final String DISTRIBUTION_CONTROLLER_WEB = "java:comp/env/ejb/DistributionController";

	public static final String DISTRIBUTION_MAINTENANCE_SERVICE = "ejb/com/ascap/prep/service/distribution/DistributionMaintenanceServiceHome";
	public static final String DISTRIBUTION_MAINTENANCE_CONTROLLER = "ejb/com/ascap/prep/controller/distribution/DistributionMaintenanceControllerHome";
	public static final String DISTRIBUTION_MAINTENANCE_CONTROLLER_WEB = "java:comp/env/ejb/DistributionMaintenanceController";

	public static final String USAGE_SERVICE = "ejb/com/ascap/prep/service/usage/UsageServiceHome";
	public static final String USAGE_CONTROLLER = "ejb/com/ascap/prep/controller/usage/UsageControllerHome";
	public static final String USAGE_CONTROLLER_WEB = "java:comp/env/ejb/UsageController";

	public static final String LOGON_SERVICE = "ejb/com/ascap/prep/service/logon/LogonServiceHome";
	public static final String LOGON_CONTROLLER = "ejb/com/ascap/prep/controller/logon/LogonControllerHome";
	public static final String LOGON_CONTROLLER_WEB = "java:comp/env/ejb/LogonController";


	public static final String ROYALTY_SERVICE = "ejb/com/ascap/prep/service/royalty/RoyaltyServiceHome";
	public static final String ROYALTY_CONTROLLER = "ejb/com/ascap/prep/controller/royalty/RoyaltyControllerHome";
	public static final String ROYALTY_CONTROLLER_WEB = "java:comp/env/ejb/RoyaltyController";


	public static final String MEMBERSHIP_SERVICE = "ejb/com/ascap/prep/service/membership/MembershipServiceHome";
	public static final String MEMBERSHIP_CONTROLLER = "ejb/com/ascap/prep/controller/membership/MembershipControllerHome";
	public static final String MEMBERSHIP_CONTROLLER_WEB = "java:comp/env/ejb/MembershipController";


	public static final String INQUIRY_SERVICE = "ejb/com/ascap/prep/service/inquiry/InquiryServiceHome";
	public static final String INQUIRY_CONTROLLER = "ejb/com/ascap/prep/controller/inquiry/InquiryControllerHome";
	public static final String INQUIRY_CONTROLLER_WEB = "java:comp/env/ejb/InquiryController";

	public static final String INQUIRY_WFSERVICE = "ejb/com/ascap/prep/service/inquiry/InquiryWorkflowServiceHome";

	public static final String ADMIN_SERVICE = "ejb/com/ascap/prep/service/admin/AdminServiceHome";
	public static final String ADMIN_CONTROLLER = "ejb/com/ascap/prep/controller/admin/AdminControllerHome";
	public static final String ADMIN_CONTROLLER_WEB = "java:comp/env/ejb/AdminController";

	public static final String ADJUSTMENTS_SERVICE = "ejb/com/ascap/prep/service/adjustments/AdjustmentsServiceHome";
	public static final String ADJUSTMENTS_CONTROLLER = "ejb/com/ascap/prep/controller/adjustments/AdjustmentsControllerHome";
	public static final String ADJUSTMENTS_CONTROLLER_WEB = "java:comp/env/ejb/AdjustmentsController";

	public static final String REMARKS_CONTROLLER_WEB = "ejb/com/ascap/prep/controller/copyrights/works/RemarksControllerHome";

	//************************************JNDI Names for Report Services*****************************************
	public static final String REPORTS_SCHEDULEINFO_CONTROLLER_WEB = "java:comp/env/ejb/ReportScheduleInfoController";
	public static final String REPORTS_SCHEDULEINFO_SERVICE = "ejb/com/ascap/prep/service/reports/ReportScheduleInfoServiceHome";
	public static final String REPORTS_CONTROLLER_WEB = "java:comp/env/ejb/ReportController";
	public static final String REPORTS_SERVICE = "ejb/com/ascap/prep/service/reports/ReportServiceHome";

	
	public static final String LRS_CONTROLLER_WEB = "java:comp/env/ejb/LrsController";
    public static final String LRS_SERVICE = "ejb/com/ascap/prep/service/lrs/LrsServiceHome";
	public static final String LRS_CONTROLLER = "ejb/com/ascap/prep/controller/lrs/LrsControllerHome";
	
	public static final String DOCUMENTS_CONTROLLER_WEB = "java:comp/env/ejb/DocumentsController";
    public static final String DOCUMENTS_SERVICE = "ejb/com/ascap/prep/service/documents/DocumentsServiceHome";
	public static final String DOCUMENTS_CONTROLLER = "ejb/com/ascap/prep/controller/documents/DocumentsControllerHome";
	
												  
	public static final String CUESHEET_SERVICE = "ejb/com/ascap/prep/service/cuesheets/CueSheetServiceHome";
	public static final String EWA_CONTROLLER = "ejb/com/ascap/ewa/controller/EWAControllerHome";
	public static final String EWA_CONTROLLER_WEB = "java:comp/env/ejb/EWAController";
	public static final String EWA_SERVICE = "ejb/com/ascap/ewa/service/EWAServiceHome";

	// Preferences Constants //
	public static final String PREFERENCES_SERVICE = "ejb/com/ascap/prep/service/preferences/PreferencesServiceHome";
	public static final String PREFERENCES_CONTROLLER = "ejb/com/ascap/prep/controller/preferences/PreferencesControllerHome";
	public static final String PREFERENCES_CONTROLLER_WEB = "java:comp/env/ejb/PreferencesController";

	public static final String COMMON_SERVICE = "ejb/com/ascap/prep/service/common/CommonServiceHome";
	public static final String COMMON_CONTROLLER = "ejb/com/ascap/prep/controller/common/CommonControllerHome";
	public static final String COMMON_CONTROLLER_WEB = "java:comp/env/ejb/CommonController";


}
