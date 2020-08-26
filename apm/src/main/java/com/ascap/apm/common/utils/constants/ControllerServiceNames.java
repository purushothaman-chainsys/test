
package com.ascap.apm.common.utils.constants;


/**
 * @author Manoj_Puli
 
 */
public class ControllerServiceNames {
	
	public static final int GET_MULT_WORK_ID_SEQUENCE = 499;

	public static final int CLONE_CATALOG_PERFS = 500;
	public static final int CLONE_WRK_PERFS = 501;
	public static final int GET_MEDLEY_MULTI_WORKS = 502;
	public static final int GET_LEARNED_MATCH_MEDLEY_MULTI_WORKS = 503;

	public static final int GET_EO_LEARNED_MATCH_MEDLEY_MULTI_WORKS = 505;

	public static final int CANDIDATE_MATCH_SEARCH       = 547;
	public static final int WORKS_COMPARISON       = 548;
	public static final int AUTO_MERGE       = 549;
	public static final int MANUAL_MERGE       = 550;
	public static final int MERGE_WORKS       = 551;
	public static final int REMOVE_POSSIBLE_MATCH = 552;
	public static final int BULK_AMEND = 553;
	public static final int CHECK_AWD_APP_FOR_YEAR = 554;
	public static final int BULK_AMEND_REQUEST_SEARCH = 555;
	public static final int BULK_AMEND_REQUEST_CREATE = 556;
	public static final int BULK_AMEND_REQUEST_RETRIEVE = 557;
	
	

    // This calls the EJB methods for the PerformerController EJB.
    public static final int CREATE_PERFORMER = 570;
	public static final int RETRIVE_PERFORMER = 571;
	public static final int UPDATE_PERFORMER = 572;
	public static final int DELETE_PERFORMER = 573;
	public static final int SEARCH_PERFORMER = 574;

//	This calls the EJB methods for the RecordingController EJB.
	 public static final int CREATE_RECORDING = 575;
	 public static final int RETRIVE_RECORDING = 576;
	 public static final int UPDATE_RECORDING = 577;
	 public static final int DELETE_RECORDING = 578;
	 public static final int SEARCH_RECORDING = 579;

	 public static final int RETRIEVE_ENTITLEMENT_AGREEMENT = 580;
	 public static final int SEARCH_ENTITLEMENT_AGREEMENT = 581;
	 public static final int RETRIEVE_ENTITLEMENT_MANDATE = 582;
	 public static final int SEARCH_ENTITLEMENT_MANDATE = 583;

	public static final int LIST_REMARKS = 584;
	public static final int RETRIEVE_REMARK = 585;
	public static final int ADD_REMARK = 586;
	public static final int DELETE_REMARKS = 587;
	public static final int UPDATE_REMARK = 588;

	public static final int REGENERATE_ACKNOWLEDGEMENT = 589;
	public static final int GET_CWR_FORMAT = 590;
	public static final int GET_WRK_ORIGIN_DTL = 592;


	//This calls the EJB methods on the AgreementController EJB
	public static final int CREATE_AGREEMENT = 601;
	public static final int DELETE_AGREEMENT = 602;
	public static final int RETRIEVE_AGREEMENT = 603;
	public static final int UPDATE_AGREEMENT = 604;
	public static final int SEARCH_AGREEMENT = 605;
	public static final int SEARCH_AGREEMENT_WORKS = 611;
	public static final int SEARCH_AGREEMENT_WORK_SCHEDULES = 612;
	public static final int DELETE_AGREEMENT_WORK_SCHEDULES = 613;
	public static final int ADD_AGREEMENT_WORK_SCHEDULES = 614;
	public static final int AGREEMENT_SEARCH_PARTY = 615;
	public static final int LIST_ACKNOWLEDGEMENTS = 616;
	public static final int GET_DESIGNATED_USER_DETAILS = 617;//copyrights.works related
	public static final int CREATE_AUDIT = 618;//copyrights.works related
	public static final int LIST_AUDITS = 619;//copyrights.works related
	public static final int LIST_AGR_ACKNOWLEDGEMENTS = 620;

	//This calls the create EJB method on the MandateController EJB
	public static final int CREATE_MANDATE = 701;
	public static final int UPDATE_MANDATE = 702;
    public static final int DELETE_MANDATE = 703;
    public static final int RETRIEVE_MANDATE = 704;
    public static final int SEARCH_MANDATE = 705;
    public static final int SEARCH_MANDATE_WORK_SCHEDULES = 706;
    public static final int SEARCH_MANDATE_WORKS = 707;
    public static final int MANDATES_SEARCH_PARTY = 708;
    public static final int MANDATE_ADD_WORK_SCHEDULES = 709;
    public static final int MANDATE_DELETE_WORK_SCHEDULES = 710;
    public static final int MANDATES_PENDING_RESIGNATION = 711;
    public static final int MANDATES_CREATION_PENDING_RESIGNATION = 712;
    public static final int GET_CALL2COPYRIGHT_JOB_STATUS = 713;

	public static final int UPDATE_WORK_LEGAL_STATUS = 714;
	public static final int RETRIEVE_WORK_LEGAL_STATUS = 715;
	public static final int RETRIEVE_LITIGATION = 716;
	public static final int DELETE_LITIGATION = 717;
	public static final int SEARCH_LITIGATION = 718;
    public static final int UPDATE_LITIGATION = 719;    

	public static final int IS_AUDIT_PENDING = 733;
	public static final int CREATE_PENDINGAUDIT = 731;
	public static final int RETRIEVE_PENDINGAUDIT = 732;
	
	public static final int RETRIEVE_LATEST_DISTRIBUTION_DETAILS = 734;
	/**
	 * Usage Related Service Constants range reserved 800 - 899
	 * 
	 * Next Available Number - 882
	 */
	//Program Performance related
    public static final int ADD_PROGRAM_PERFORMANCE=800;
    public static final int UPDATE_PROGRAM_PERFORMANCE=801;
    public static final int DELETE_PROGRAM_PERFORMANCE=802;
    public static final int SEARCH_PROGRAM_PERFORMANCE=803;
    
    public static final int UPDATE_ASSIGNED_USERS = 888;

    public static final int CLONE_PROGRAM_PERFORMANCE=881;

    public static final int GET_PROGRAM_PERFORMANCE =804;
    public static final int GET_PERFORMANCE_MUSIC_USER_INFO =805;

    //Work Performance related
    public static final int ADD_WORK_PERFORMANCE=806;
    public static final int UPDATE_WORK_PERFORMANCE=807;
    public static final int UPDATE_WORK_PERFORMANCE_MULT=1101;
	public static final int DELETE_WORK_PERFORMANCE=808;
	public static final int SEARCH_WORK_PERFORMANCE=809;
    public static final int GET_WORK_PERFORMANCE=810;
    public static final int GET_WORK_PERFORMANCES_FOR_PROGRAM=811;
	//Rematch, Refresh, Unpost Operations
	public static final int REMATCH_PROGRAM_PERFORMANCE=823;
	public static final int REFRESH_PROGRAM_PERFORMANCE=824;
	public static final int UNPOST_PROGRAM_PERFORMANCE=825;

	//Add to medley, remove from Medley Operations
	public static final int ADD_TO_MEDLEY_WORK_PERFORMANCE=826;
	public static final int REMOVE_FROM_MEDLEY_WORK_PERFORMANCE=827;


	//Sample, Sub Sample, Library, Educational Concert
	public static final int US_ADD_SAMPLE = 829;
	public static final int US_EDIT_SAMPLE = 830;
	public static final int US_GET_SAMPLE = 832;

	public static final int US_ADD_SUBSAMPLE = 833;

	public static final int US_ADD_SURVEY_DATE=855;
	
	public static final int US_GET_SURVEY_DATE=1000858;
	public static final int US_GET_SURVEY_DATES_LIST=1000859;

	public static final int USAGE_PARTY_SEARCH=861;

	public static final int DELETE_WORK_PERFORMANCES_LIST = 865;

	//tobe Deleted
	public static final int UPDATE_MULTIPLE_PROGRAM_PERFORMANCE = 870;
	public static final int GET_WORKPERF_FOR_PROGRAM = 871;
	public static final int GET_MUSIC_USER = 872;
	public static final int US_ADD_COMPLETE_WORK_PERFORMANCE = 873;
	public static final int US_ADD_NEXT_WORK_PERFORMANCE = 874;
	public static final int US_EDIT_WORK_PERFORMANCE = 875;
	public static final int US_GET_WORK_PERFORMANCE = 876;
	public static final int US_SEARCH_WORK_PERFORMANCE = 877;

	public static final int US_SEARCH_SAMPLE = 878;
	public static final int US_GET_SUBSAMPLES_MUSUSRTYP = 869;
	
	


	public static final int US_APM_FILES = 899;
	public static final int UPDATE_APM_FILES = 898;
	public static final int US_GET_BULK_REQUEST_LIST=858;
	public static final int APM_CATALOG_MANUAL_MATCH_LIST=857;
	public static final int US_UPDATE_BULK_REQUEST_LIST=859;
	public static final int APM_CATALOG_UPDATE_BULK_REQUEST_LIST=856;
	public static final int US_MUSIC_USER_SEARCH = 828;
	public static final int APM_LEARNED_MATCH_LIST=860;
	public static final int APM_LEARNED_MATCH_GET=5001;
	public static final int APM_LEARNED_MATCH_CREATE=5002;
	public static final int APM_LEARNED_MATCH_UPDATE=5003;
	public static final int APM_LEARNED_MATCH_UPDATE_MULTIPLE=504;
	public static final int APM_EO_LEARNED_MATCH_UPDATE_MULTIPLE=506;
	public static final int APM_LEARNED_MATCH_DELETE=5009;
	public static final int APM_SAMPLING_DETAILS=5004;
	public static final int APM_SAMPLING_SUMMMARY=5005;
	public static final int APM_SAMPLING_DETAILS_UPDATE=5006;
	public static final int APM_SAMPLING_REQUEST=5007;
	public static final int APM_SAMPLING_CANCEL_REQUEST=5008;
	public static final int APM_SAMPLING_DETAILS_BYPASS=5031;

	public static final int APM_CHANNEL_LIST=5010;
	public static final int APM_CHANNEL_UPDATE=5011;
	public static final int EO_FILE_LIST=5012;
	public static final int EO_FILE_LOAD_TO_EO=5013;
	public static final int EO_FILE_LOAD_TO_APM=5014;
	public static final int GET_ACTIVE_SURV_YEAR=5015;
	public static final int GET_APM_SUPPLIERS=5016;
	
	public static final int EXEMPTION_LIST = 5017;
	public static final int EXEMPTION_RETRIEVE = 5018;
	public static final int EXEMPTION_UPDATE = 5019;
	public static final int EXEMPTION_CREATE = 5020;
	public static final int EXEMPTION_DELETE = 5021;
	

	public static final int ASSIGN_USER_TO_WRK_PERF = 5022;
	public static final int EO_FILE_UPDATE_FILEINVENTORY=5023;
	public static final int GET_ROLLUP_THRESHOLD=5024;
	public static final int US_UPDATE_BULK_REQUEST_LIST_UNDELETE=5025;
	
	
	
	public static final int GET_EO_LEARNED_MATCH_LIST = 5026;
	public static final int UPDATE_EO_LEARNED_MATCH_LIST = 5027;
	public static final int DELETE_EO_LEARNED_MATCH_LIST = 5028;
	public static final int ADD_EO_LEARNED_MATCH = 5029;
	public static final int VALIDATE_APM_WORK_IDS = 5030;
	public static final int APM_CATALOGSAMPLING_SUMMMARY=5032;
	public static final int APM_CATALOGSAMPLING_PROCESS=5033;
	public static final int APM_CATALOGSAMPLING_CLOSE=5034;
	public static final int APM_LOGREQUEST_SUMMARY=5035;
	public static final int APM_LOGREQUEST_UPDATE=5036;
	public static final int APM_LOGUSAGE_SUMMARY=5037;
	public static final int APM_LOGUSAGE_INSERT=5038;
	public static final int APM_LOGUSAGE_UPDATE=5039;
	/**
	 * Usage Ends
	 */

	/**
	 * MEMBERSHIP CONTROLLER AND SERVICE ENTRIES START
	 * CONSTANTS USED WILL BE IN THE RANGE 401-499 , 1100 - 1199, 1300 -1399
	 */

	//public static final int SEARCH_PARTY = 401;
	//public static final int SEARCH_CONTACTS = 402;
	//public static final int SEARCH_PARTY_RELATIONSHIPS = 403;
	//public static final int SEARCH_TERRITORIES = 404;
	//public static final int CREATE_ADDR_DETAILS = 405;
	//public static final int UPDATE_EMAIL_DETAILS = 408;
	//public static final int CREATE_EMAIL_DETAILS = 407;
	//public static final int RETRIEVE_EMAIL_DETAILS=409;
	//public static final int CREATE_PHONE_DETAILS = 410;
	//public static final int ADD_CREATE_PARTY = 411;
	//public static final int ADD_MEMBER_RELATIONSHIP = 412;
	//public static final int GET_MEMBER_RELATIONSHIP=413;
	//public static final int UPDATE_MEMBER_RELATIONSHIP=414;
	//public static final int DELETE_MEMBER_RELATIONSHIP=415;
	//public static final int DELETE_TERRITORY = 416;
	//public static final int GET_TERRITORY = 417;
	//public static final int RETRIEVE_WEB_ADR_DETAILS = 424;
	//public static final int CREATE_WEB_ADR_DETAILS = 425;
	//public static final int UPDATE_WEB_ADR_DETAILS = 426;

	//public static final int SEARCH_CONTACT_HISTORY = 476;

	//public static final int VIEW_MA_ACCNTS = 428;
	//public static final int SEARCH_PUB_NAME_HOT_LIST = 429;
	//public static final int DELETE_NAMES_FROM_PUB_NAME_HOT_LIST = 838;
	//public static final int UPDATE_PUB_NAME_HOT_LIST = 839;
	//public static final int ADD_PUB_NAME_HOT_LIST = 840;

	//public static final int SEARCH_PARTYNAMES = 418;
	//public static final int ADD_PARTYNAME = 419;
	//public static final int DELETE_PARTYNAME = 420;
	//public static final int GET_PARTYNAME = 421;
	//public static final int UPDATE_TERRITORY=422;
	//public static final int ADD_TERRITORY=423;

	//public static final int DELETE_CONTACT=427;
	//public static final int UPDATE_ADDRESS_DETAILS=430;
	//public static final int RETRIEVE_ADDRESS_DETAILS=432;
	//public static final int GET_PARTY=433;
	//public static final int UPDATE_BASIC_DETAILS = 434;
	//public static final int RETRIEVE_PHONE_DETAILS = 435;
	//public static final int UPDATE_PHONE_DETAILS = 436;
	//public static final int UPDATE_PARTYNAME_DETAILS = 437;
	//public static final int DECEASED_NOTIFICATION = 438;
	//public static final int SEARCH_SUCCESSORS = 439;
	//public static final int MEMBERSHIP_STATUS_ADD = 440;
	//public static final int SEARCH_CEREMONIAL_AWARDS = 441;
	//public static final int ADD_CEREMONIAL_AWARDS = 442;
	//public static final int DELETE_CEREMONIAL_AWARDS = 443;
	//public static final int GET_CEREMONIAL_AWARDS = 444;
	//public static final int ADD_ASCAP_PLUS_AWARDS = 446;
	//public static final int DELETE_ASCAP_PLUS_AWARDS = 447;
	//public static final int GET_ASCAP_PLUS_AWARDS = 448;
	//public static final int MEMBERSHIP_STATUS_UPDATE = 449;
	//public static final int UPDATE_CEREMONIAL_AWARDS = 450;
	//public static final int UPDATE_ASCAP_PLUS_AWARDS = 451;
	//public static final int MEMBERSHIP_STATUS_RETRIVE = 452;
	//public static final int MEMBERSHIP_SEARCH_WORK = 453;
	//public static final int MEMBERSHIP_RELATIONSHIP_PARTY_SEARCH = 454;
	//public static final int SEARCH_LEGAL_EARNERS = 455;
	//public static final int SEARCH_PARTY_ASCAP_AWARD_OUT_OF_CONTEXT = 456;
	//public static final int ADD_ASCAP_AWARD_OUT_OF_CONTEXT = 457;
	//public static final int SEARCH_ASCAP_AWARD_OUT_OF_CONTEXT_VIEW_LIST = 458;
	//public static final int ASCAP_AWARD_LIST_UPDATE = 459;
//	public static final int PICK_ME_ASCAP_AWARD_OUT_OF_CONTEXT = 460;
//	public static final int SEARCH_ASCAP_AWARD_IN_CONTEXT = 461;
//	public static final int ADD_ASCAP_AWARD_IN_CONTEXT = 462;
//	public static final int RETRIEVE_ASCAP_AWARD_IN_CONTEXT_DETAILS = 463;
//	public static final int UPDATE_ASCAP_AWARD_IN_CONTEXT = 464;

//	public static final int GET_CHARGEABLE_SERVICE_LIST = 465;
//	public static final int GET_CHARGEABLE_SERVICE = 466;
//	public static final int ADD_CHARGEABLE_SERVICE = 467;
//	public static final int UPDATE_CHARGEABLE_SERVICE = 468;
//	public static final int DELETE_CHARGEABLE_SERVICE = 469;


//	public static final int RETRIEVE_COMPANYPRINCIPAL = 470;
//	public static final int SEARCH_COMPANYPRINCIPALS = 471;
//	public static final int ADD_COMPANYPRINCIPAL = 472;
//	public static final int UPDATE_COMPANYPRINCIPAL = 473;
//	public static final int DELETE_COMPANYPRINCIPALS = 474;
//	public static final int SEARCH_PARTYREMARKS = 475;

//	public static final int DELETE_NAMED_SUCCESSOR = 1100;
//	public static final int GET_ALLOCATIONSLIST_FORUPDATE = 480;
//	public static final int GET_ALLOCATIONSLIST_HISTORY = 481;
//	public static final int GET_ENTITLED_EARNER_ALLOCATION_BY_PK = 482;
//	public static final int GET_SPECIFIC_ENTITLED_EARNER_ALLOCATION_BY_PK = 483;

//	public static final int ADD_ENTITLED_EARNER_ALLOCATION = 484;
//	public static final int UPDATE_ENTITLED_EARNER_ALLOCATION = 485;
//	public static final int DELETE_ENTITLED_EARNER_ALLOCATION = 486;

//	public static final int ADD_ENTITLED_EARNER_ALLOCATION_BY_DISTRIBUTION = 487;
//	public static final int UPDATE_ENTITLED_EARNER_ALLOCATION_BY_DISTRIBUTION = 488;
//	public static final int DELETE_ENTITLED_EARNER_ALLOCATION_BY_DISTRIBUTION = 489;
//	public static final int DELETE_EE_ALLOCATIONS_OR_SELECTIONS = 1307;
//
//	public static final int GET_ENTITLED_EARNER_ALLOCATIONS_BY_EE_PARTY_ID = 490;

	public static final int LOAD_ADDRESSFORMAILING_SEARCHCRITERIA = 491;
	public static final int ADD_EMAIL = 492;
	public static final int DELETE_EMAILS = 493;
	public static final int RETRIEVE_ALL_EMAILS = 494;
	public static final int RETRIEVE_ALL_PHONES = 495;
	public static final int RETRIEVE_ALL_ADDRESSES = 496;

	public static final int SEARCH_MUSICUSERTYPES = 1111;
	public static final int ADD_MUSICUSERTYPE = 1112;
	public static final int DELETE_MUSICUSERTYPES = 1113;
	public static final int UPDATE_MUSICUSERTYPE = 1114;
	public static final int RETRIEVE_MUSICUSERTYPE = 1115;

	public static final int SEARCH_LICENSETYPES = 1116;
	public static final int ADD_LICENSETYPE = 1117;
	public static final int DELETE_LICENSETYPES = 1118;
	public static final int UPDATE_LICENSETYPE = 1119;
	public static final int RETRIEVE_LICENSETYPE = 1120;

	public static final int SEARCH_CALLLETTERS = 1121;
	public static final int ADD_CALLLETTER = 1122;
	public static final int DELETE_CALLLETTERS = 1123;
	public static final int UPDATE_CALLLETTER = 1124;
	public static final int RETRIEVE_CALLLETTER = 1125;

	public static final int GET_MEMBERSHIP_STATUS_HISTORY_LIST = 1126;


	public static final int SEARCH_MERGEPARTIES = 1301;
	public static final int RETRIEVE_MERGEPARTY = 1302;
	public static final int MERGEPARTY = 1303;

	public static final int VALIDATE_ADDRESS = 1300;

	public static final int VALIDATE_ADDRESS_FOR_MA = 1304;

	public static final int GET_ALTERNATE_PARTY_ID_LIST = 1305;

	public static final int GET_MEMBERSHIP_STATUS_WITH_HISTORY = 1306;

	public static final int RESET_MA_ACCOUNT_PASSWORD = 1308;
	public static final int UPDATE_MA_ACCOUNT_DETAILS = 1309;
	public static final int RESEND_WELCOME_PACK = 1310;

	public static final int GET_ASCAP_AWARD_PANEL_NOTES = 1311;
	public static final int UPSERT_ASCAP_AWARD_PANEL_NOTES = 1312;

	public static final int VALIDATE_PUBLISHER_NAMES = 1313;

	public static final int RETRIEVE_ASSIGNMENT = 1314;
	public static final int SEARCH_ASSIGNMENTS = 1315;
	public static final int ADD_ASSIGNMENT = 1316;
	public static final int UPDATE_ASSIGNMENT = 1317;
	public static final int DELETE_ASSIGNMENTS = 1318;
	
	public static final int SEARCH_RESIGNATION_REQUESTS = 1319;
	public static final int ACKNOWLEDGE_RESIGN_REQUESTS = 1320;
	public static final int CANCEL_RESIGN_REQUESTS = 1321;
	public static final int RETRIEVE_RESIGN_REQUEST = 1322;
	public static final int UPDATE_RESIGN_REQUEST = 1323;
	public static final int ACKNOWLEDGE_RESIGN_REQUEST = 1324;
	public static final int CANCEL_RESIGN_REQUEST = 1325;
	
	public static final int MEMBERSHIP_SEND_BULK_EMAIL = 1326;
	public static final int GET_RESIGNATION_CONFIRMATIONS = 1327;
	public static final int GET_RESIGNATION_REMINDERS = 1328;
	public static final int PREP_ACTIVATE_MEMBER = 1329;
	public static final int ADD_PARTY_REMARKS = 1330;
	
	public static final int SEARCH_ONSTAGE_APPLICATIONS = 1331;
	public static final int SEARCH_ONSTAGE_VENUES = 1332;
	public static final int SEARCH_SETLISTS = 1333;
	public static final int RETRIEVE_ONSTAGE_APPLICATION = 1334;
	public static final int RETRIEVE_ONSTAGE_VENUE = 1335;
	public static final int RETRIEVE_SETLIST = 1336;
	public static final int GRANT_ONSTAGE_APPLICATIONS = 1337;
	public static final int UPDATE_ONSTAGE_APPLICATION = 1338;
	public static final int UPDATE_ONSTAGE_VENUE = 1339;
	public static final int UPDATE_SETLIST = 1340;
	public static final int DELETE_ONSTAGE_APPLICATION = 1341;
	public static final int DELETE_ONSTAGE_VENUE = 1342;
	public static final int DELETE_SETLIST = 1343;
	public static final int CREATE_ONSTAGE_APPLICATION = 1344;
	public static final int CREATE_ONSTAGE_VENUE = 1345;
	public static final int CREATE_SETLIST = 1346;
	public static final int GET_ONSTAGE_SETLIST_LOOKUP = 1347;
	
	public static final int EWA_SEARCH_ONSTAGE_APPLICATIONS = 1348;
	public static final int EWA_SEARCH_SETLISTS = 1349;
	public static final int EWA_RETRIEVE_ONSTAGE_APPLICATION_DETAILS = 1350;
	public static final int EWA_RETRIEVE_ONSTAGE_SETLIST_DETAILS = 1351;
	public static final int EWA_RETRIEVE_SAVED_SETLISTS = 1352;
	public static final int EWA_UPDATE_ONSTAGE_APPLICATION_DETAILS = 1353;
	public static final int EWA_DELETE_ONSTAGE_APPLICATION_DETAILS = 1354;
	public static final int EWA_CREATE_ONSTAGE_APPLICATION_DETAILS = 1355;
	public static final int EWA_UPDATE_ONSTAGE_SETLIST_DETAILS = 1356;
	public static final int EWA_CREATE_ONSTAGE_SETLIST_DETAILS = 1357;
	public static final int EWA_DELETE_ONSTAGE_SETLIST_DETAILS = 1358;
	
	
	/**
	 * MEMBERSHIP CONTROLLER  ENTRIES END
	 */


	/**
	 * this a simple lookup method to be used by the base action class
	 * in order to use the switch expression
	 */

	/*
	 * Distribution Controller Entries  Constants range reserved 300 - 399 AND 900 - 999
	 */
	public static final int GET_DIS_RUN_LIST = 311;
	public static final int GET_DIS_RUN = 312;
	public static final int ADD_DIS_RUN = 313;
	public static final int UPDATE_DIS_RUN = 314;
	public static final int DELETE_DIS_RUN = 315;
	public static final int GET_DIS_RUN_CONTROL_LIST = 316;
	public static final int GET_DIS_RUN_CONTROL = 317;
	public static final int ADD_DIS_RUN_CONTROL = 318;
	public static final int UPDATE_DIS_RUN_CONTROL = 319;
	public static final int DELETE_DIS_RUN_CONTROL = 320;
	public static final int SCHEDULE_DIS_RUN = 321;
	public static final int GET_DIS_RUN_CONTROL_UNASSIGNED_USAGES = 322;
	//Usage Selection
	public static final int GET_USE_SEL_LIST = 323;
	public static final int GET_USE_SEL = 324;
	public static final int ADD_USE_SEL = 325;
	public static final int UPDATE_USE_SEL = 326;
	public static final int DELETE_USE_SEL = 327;
	public static final int LOAD_USE_SEL_LOOKUP_DATA = 328;

	public static final int GET_DIS_AFFILIATED_SOCIETIES = 329;

	//active and all usage selections
	public static final int ALL_USE_SEL = 329;
	public static final int ACTIVE_USE_SEL = 330;

	// Maintain Money Clas Group
	public static final int GET_DIS_MNY_CLS_GRP_LIST = 331;
	public static final int ADD_DIS_MNY_CLS_GRP = 332;
	public static final int UPDATE_DIS_MNY_CLS_GRP = 333;
	public static final int DELETE_DIS_MNY_CLS_GRP = 334;
	public static final int GET_DIS_MNY_CLS_GRP = 335;

	// Maintain Use Weight Rules
	public static final int GET_USE_WEIGHT_RULE_LIST = 336;
	public static final int ADD_USE_WEIGHT_RULE = 337;
	public static final int UPDATE_USE_WEIGHT_RULE = 338;
	public static final int DELETE_USE_WEIGHT_RULE = 339;
	public static final int GET_USE_WEIGHT_RULE = 340;

	// Maintain Music User Weight Rules
	public static final int GET_MUSIC_USER_WEIGHT_RULE_LIST = 341;
	public static final int ADD_MUSIC_USER_WEIGHT_RULE = 342;
	public static final int UPDATE_MUSIC_USER_WEIGHT_RULE = 343;
	public static final int DELETE_MUSIC_USER_WEIGHT_RULE = 344;
	public static final int GET_MUSIC_USER_WEIGHT_RULE = 345;

	// Maintain Adjusted Base Factor Rules

	public static final int GET_ADJ_BASE_FACTOR_RULE_LIST = 346;
	public static final int ADD_ADJ_BASE_FACTOR_RULE = 347;
	public static final int UPDATE_ADJ_BASE_FACTOR_RULE = 348;
	public static final int DELETE_ADJ_BASE_FACTOR_RULE = 349;
	public static final int GET_ADJ_BASE_FACTOR_RULE = 350;

	//Distribution Party Search
	public static final int DIS_SEARCH_PARTY = 351;

	// Use Weight Rule Validation
	public static final int CORRECT_USE_WEIGHT_RULES = 352;
	public static final int UPDATE_USE_WEIGHT_RULES = 353;

	// Maintain TV Hookup Weight Rules
	public static final int GET_TV_HOOKUP_WEIGHT_RULE_LIST = 354;
	public static final int ADD_TV_HOOKUP_WEIGHT_RULE = 355;
	public static final int UPDATE_TV_HOOKUP_WEIGHT_RULE = 356;
	public static final int DELETE_TV_HOOKUP_WEIGHT_RULE = 357;
	public static final int GET_TV_HOOKUP_WEIGHT_RULE = 358;
	public static final int CORRECT_TV_HOOKUP_WEIGHT_RULES = 359;
	public static final int UPDATE_TV_HOOKUP_WEIGHT_RULES = 360;

	// Get Avg Revenue
	public static final int GET_AVG_REV_IDS = 361;

	// Get Goal Credits
	public static final int GET_GOAL_CREDIT_LIST = 362;
	public static final int LOAD_GOAL_CREDIT_LOOKUP_DATA = 363;
	public static final int ADD_GOAL_CREDIT = 364;
	public static final int UPDATE_GOAL_CREDIT = 365;
	public static final int GET_GOAL_CREDIT = 366;

	public static final int ADD_BULK_USE_WEIGHT_RULES = 367;
	public static final int UPDATE_BULK_USE_WEIGHT_RULES = 368;
	public static final int CORRECT_BULK_USE_WEIGHT_RULES = 369;
	public static final int DELETE_BULK_USE_WEIGHT_RULES = 370;

	public static final int ADD_BULK_TV_HOOKUP_WEIGHT_RULES = 371;
	public static final int UPDATE_BULK_TV_HOOKUP_WEIGHT_RULES = 372;
	public static final int CORRECT_BULK_TV_HOOKUP_WEIGHT_RULES = 373;
	public static final int DELETE_BULK_TV_HOOKUP_WEIGHT_RULES = 374;


	public static final int LOAD_LINK_USE_SELS_LOOKUP_DATA = 375;
	public static final int LINK_USE_SELS = 376;

	public static final int GET_PREFERENCES = 377;
	public static final int SUBMIT_PREFERENCES = 378;



	//Radio Feature Premium Weight Rule
	public static final int GET_RADIO_FEATURE_PREMIUM_WEIGHT_RULE_LIST  = 379 ;
	public static final int ADD_RADIO_FEATURE_PREMIUM_WEIGHT_RULE  = 380 ;
	public static final int UPDATE_RADIO_FEATURE_PREMIUM_WEIGHT_RULE  = 381 ;
	public static final int DELETE_RADIO_FEATURE_PREMIUM_WEIGHT_RULE  = 382 ;
	public static final int GET_RADIO_FEATURE_PREMIUM_WEIGHT_RULE  = 383 ;
	public static final int CORRECT_RADIO_FEATURE_PREMIUM_WEIGHT_RULES  = 384 ;
	public static final int UPDATE_RADIO_FEATURE_PREMIUM_WEIGHT_RULES  = 385 ;
	public static final int ADD_BULK_RADIO_FEATURE_PREMIUM_WEIGHT_RULES  = 386 ;
	public static final int UPDATE_BULK_RADIO_FEATURE_PREMIUM_WEIGHT_RULES  = 387 ;
	public static final int CORRECT_BULK_RADIO_FEATURE_PREMIUM_WEIGHT_RULES  = 388 ;
	public static final int DELETE_BULK_RADIO_FEATURE_PREMIUM_WEIGHT_RULES  = 389 ;

	//Time Of Day Weight Rules
	public static final int GET_TIME_OF_DAY_WEIGHT_RULE_LIST  = 390 ;
	public static final int ADD_TIME_OF_DAY_WEIGHT_RULE  = 391 ;
	public static final int UPDATE_TIME_OF_DAY_WEIGHT_RULE  = 392 ;
	public static final int DELETE_TIME_OF_DAY_WEIGHT_RULE  = 393 ;
	public static final int GET_TIME_OF_DAY_WEIGHT_RULE  = 394 ;
	public static final int CORRECT_TIME_OF_DAY_WEIGHT_RULES  = 395 ;
	public static final int UPDATE_TIME_OF_DAY_WEIGHT_RULES  = 396 ;
	public static final int ADD_BULK_TIME_OF_DAY_WEIGHT_RULES  = 397 ;
	public static final int UPDATE_BULK_TIME_OF_DAY_WEIGHT_RULES  = 398 ;
	public static final int CORRECT_BULK_TIME_OF_DAY_WEIGHT_RULES  = 399 ;
	public static final int DELETE_BULK_TIME_OF_DAY_WEIGHT_RULES  = 900 ;


	// Maintain Radio Feature Premium Multiplier
	public static final int GET_RADIO_FEATURE_PREMIUM_MULTIPLIER_LIST  = 901 ;
	public static final int ADD_RADIO_FEATURE_PREMIUM_MULTIPLIER  = 902 ;
	public static final int UPDATE_RADIO_FEATURE_PREMIUM_MULTIPLIER  = 903 ;
	public static final int DELETE_RADIO_FEATURE_PREMIUM_MULTIPLIER  = 904 ;
	public static final int GET_RADIO_FEATURE_PREMIUM_MULTIPLIER  = 905 ;

	// Maintain LookUps
	public static final int SEARCH_TYPECODES  = 906 ;
	public static final int RETRIEVE_TYPECODE  = 907;
	public static final int ADD_TYPECODE  = 908;
	public static final int UPDATE_TYPECODE  = 909;
	public static final int DELETE_TYPECODES  = 910;


	// Maintain Music User Weights
	public static final int SEARCH_MUSICUSERWEIGHTS = 911;
	public static final int RETRIEVE_MUSICUSERWEIGHTS = 912;
	public static final int ADD_MUSICUSERWEIGHT = 913;
	public static final int UPDATE_MUSICUSERWEIGHTS = 914;
	public static final int DELETE_MUSICUSERWEIGHT = 915;



	/* * * * * * * * * * * * * * * * * * *
	// TVPremium Multiplier
	* * * * * * * * * * * * * * * * * * * */
	public static final int GET_TVP_MULTIPLIER_LIST = 916;
	public static final int ADD_TVP_MULTIPLIER = 917;
	public static final int UPDATE_TVP_MULTIPLIER = 918;
	public static final int GET_TVP_MULTIPLIER = 920;

	/* * * * * * * * * * * * * * * * * * *
	// LIVE POP CONCNERT MUSIC DENSITY WEIGHTS
	* * * * * * * * * * * * * * * * * * * */
	public static final int GET_LIVE_POP_GROUP_LIST  = 921 ;
	public static final int ADD_LIVE_POP_GROUP  = 922 ;
	public static final int UPGRADE_LIVE_POP_GROUP  = 923 ;
	public static final int DELETE_LIVE_POP_GROUP  = 924 ;
	public static final int GET_LIVE_POP_GROUP  = 925 ;
	public static final int CORRECT_LIVE_POP_GROUP  = 926 ;


	/* * * * * * * * * * * * * * * * * * *
	// STREAMING INTERNET SITE WEIGHTS
	* * * * * * * * * * * * * * * * * * * */
	public static final int GET_STREAMING_INET_SITE_WEIGHT_LIST  = 927 ;
	public static final int ADD_STREAMING_INET_SITE_WEIGHT_GROUP  = 928 ;
	public static final int UPDATE_STREAMING_INET_SITE_WEIGHT_GROUP  = 929 ;
	public static final int DELETE_STREAMING_INET_SITE_WEIGHT_GROUP  = 930 ;
	public static final int GET_STREAMING_INET_SITE_WEIGHT_GROUP  = 931 ;

	/* * * * * * * * * * * * * * * * * * *
	// QWI Rule Rel 1.1 Issue 814
	* * * * * * * * * * * * * * * * * * * */

	public static final int GET_QWI_RULE_LIST = 932;
	public static final int GET_QWI_RULE = 933;
	public static final int ADD_QWI_RULE = 934;
	public static final int UPDATE_QWI_RULE = 935;
	public static final int DELETE_QWI_RULE = 936;

	/* *
	// Prep Config Param
	* * * * * * * * * * * * * * * * * * * */
	public static final int GET_PREP_CONFG_PARAM_LIST = 937;
	public static final int GET_PREP_CONFG_PARAM = 938;
	public static final int ADD_PREP_CONFG_PARAM = 939;
	public static final int UPDATE_PREP_CONFG_PARAM = 940;
	public static final int DELETE_PREP_CONFG_PARAM = 941;

	//Distribution Work Search
	public static final int DIS_SEARCH_WORK = 942;

	/* ***
	 * QWI Status Rel 1.1 Issue 814
	 * ***********/
	public static final int GET_QWI_STATUS = 943;
	public static final int UPDATE_QWI_STATUS = 944;

	/* * * * * * * * * * * * * * * * * * *
	// INTERACTIVE INTERNET SITE WEIGHTS
	* * * * * * * * * * * * * * * * * * * */
	public static final int GET_INTERACTIVE_INET_SITE_WEIGHT_LIST  = 945 ;
	public static final int ADD_INTERACTIVE_INET_SITE_WEIGHT_GROUP  = 946 ;
	public static final int UPDATE_INTERACTIVE_INET_SITE_WEIGHT_GROUP  = 947 ;
	public static final int DELETE_INTERACTIVE_INET_SITE_WEIGHT_GROUP  = 948 ;
	public static final int GET_INTERACTIVE_INET_SITE_WEIGHT_GROUP  = 949 ;

	/* * * * * * * * * * * * * * * * * * *
	// Continue Loading Music User Weight
	* * * * * * * * * * * * * * * * * * * */
	public static final int LOAD_MUSICUSERWEIGHT_FILE = 950;
	public static final int SEARCH_MUSICUSERWEIGHTS_FILES = 951;


	public static final int SEARCH_DISTRIBUTION_WORK_PERFORMANCE = 954;
	public static final int SEARCH_DISTRIBUTION_WORK_PERFORMANCE_SHARES = 955;
	public static final int 	GET_DISTRIBUTION_WORK_PERFORMANCE_INFO = 956;

	public static final int LOAD_IDS_FILE = 959;
	public static final int SEARCH_IDS_FILES = 960;


	/* * * * * * * * * * * * * * * * * * *
	// MODELING ENVIRONMENT USE CASES
	* * * * * * * * * * * * * * * * * * * */
	public static final int GET_MODELING_MAINTENANCE_LIST  = 952 ;
	public static final int SUBMIT_MODELING_MAINTENANCE  = 953 ;

	/* * * * * * * * * * * * * * * * * * *
	// MAINTAIN WORK DISTRIBUTION SUMMARY
	* * * * * * * * * * * * * * * * * * * */
	public static final int GET_WORK_DIS_SUMMARY_LIST  = 961 ;
	public static final int SUBMIT_WORK_DIS_SUMMARY_LIST  = 962 ;

	/* * * * * * * * * * * * * * * * * * *
	// QWI RELATED GROUPS
	* * * * * * * * * * * * * * * * * * * */
	//Added QWI Related Groups Constants - Ganesh.B Issue 814 Rel 1.1
	public static final int SEARCH_QWI_GROUPS = 963;
	public static final int RETRIEVE_QWI_GROUP = 964;
	public static final int ADD_WORK_QWI_GROUP = 965;
	public static final int ADD_QWI_GROUP = 966;
	public static final int DELETE_WORK_QWI_GROUP = 967;
	public static final int DELETE_QWI_GROUPS = 968;

	/* * * * * * * * * * * * * * * * * * *
	// Member Distribution Summary
	* * * * * * * * * * * * * * * * * * * */

	public static final int RETRIEVE_MEMBER_DISTRIBUTION_SUMMARY = 969;
	public static final int RETRIEVE_WORK_DISTRIBUTION_SUMMARY = 970;


	public static final int GET_DISTRIBUTION_AVERAGE_REVENUES = 971;
	public static final int UPDATE_DISTRIBUTION_AVERAGE_REVENUES = 972;


	/*
	 * Inquiry  Controller Entries  Constants range reserved 100 - 199
	 */
	 public static final int GET_INQUIRY_DETAILS = 102;
	 public static final int GENERATE_LETTER = 103;
	 public static final int ADD_REMINDER = 104;
	 public static final int UPLOAD_ATTACHMENT = 105;
	 public static final int DOWNLOAD_ATTACHMENT = 106;
	 public static final int GET_ACTION_ITEM_DETAILS = 107;
	 public static final int GET_ATTACHMENT_LIST = 108;
	 public static final int ADD_INQUIRY_TYPE = 109;
	 public static final int DELETE_ATTACHMENT = 110;
	 public static final int UPLOAD_TEMPLATE = 111;
	 public static final int SEARCH_INQUIRY_TYPES = 112;
	 public static final int GET_TEMPLATE_DROPDOWN = 113;
	 public static final int GET_INQUIRY_TYPE_DETAILS = 114;
	 public static final int GET_DEFAULT_ACTION_ITEMS_LIST = 115;
	 public static final int ADD_DEFAULT_ACTION_ITEMS = 116;
	 public static final int UPDATE_DEFAULT_ACTION_ITEMS = 117;
	 public static final int UPDATE_INQUIRY_TYPE = 118;
	 public static final int GET_REMINDER_LIST = 119;
	 public static final int DELETE_REMINDER = 120;
	 public static final int DELETE_DEFAULT_ACTION_ITEMS = 121;
	 public static final int DELETE_INQUIRY_TYPE = 122;
	 public static final int GET_DEFAULT_ACTION_ITEM_DETAILS = 123;
	 public static final int SEARCH_INQUIRY_HISTORY = 124;
	 public static final int UPDATE_REMINDER = 125;
	 public static final int GET_REMINDER = 126;

	 public static final int SEARCH_INQUIRY_PARTY = 128;

	 public static final int CREATE_NEW_INQUIRY = 130;
	 public static final int GET_ACTION_ITEM_LIST = 131;
	 public static final int CREATE_ACTION_ITEM = 132;
	 public static final int UPDATE_ACTION_ITEM = 133;
	 public static final int DELETE_ACTION_ITEMS = 134;
	 public static final int ADD_QUICK_FORM_INQUIRY = 135;
	 public static final int GET_INQUIRY_ADDRESS_PHONE = 136;
	 public static final int UPDATE_INQUIRY_ADDRESS_PHONE = 137;
	 public static final int CREATE_INQUIRY_WORK_FLOW = 138;
	 public static final int UPDATE_INQUIRY = 139;
	 public static final int SEARCH_MY_ACTION_ITEMS = 140;
	 public static final int GET_MY_REMINDERS = 141;
	 public static final int GET_MY_REMINDER_DETAIL = 142;

	 public static final int GET_MY_ACTION_ITEM_DETAILS = 144;

	 public static final int SWAP_DEFAULT_ACTION_ITEM_SEQUENCE_NO = 146;
	 public static final int SWAP_ACTION_ITEM_SEQUENCE_NO = 147;
	 public static final int GET_INQUIRY_ISSUE_TYPES = 148; //used by METHOD: BaseInquiryAction.getInquiryTypeList


	 public static final int GET_INQUIRER_DETAILS = 151;



 	 /*
 	  * Part of INquiry Management Cleanup by Jaya Shyam Narayana Lingamchetty 18 Sep 2006
 	  * New Services Start
 	  */

 	//Caters for My Inquiries, Search Inquiries, Fileter Inquiries, Member Iquiry List
	public static final int DELETE_INQUIRIES = 150;
	public static final int SEARCH_INQUIRIES = 155;
	public static final int INQ_TYPES_GET_ALL_INQUIRY_TYPES = 156;
    public static final int INQ_TYPES_GET_QUICK_FORM_INQUIRY_TYPES = 157;
    public static final int INQ_TYPES_GET_NON_QUICK_FORM_INQUIRY_TYPES = 158;
    public static final int MARK_DELETE_INQUIRIES = 159;
	public static final int UPDATE_INQUIRY_STATUS_OVERDUE = 160;
	public static final int SEND_INQUIRY_EXPIRED_ITEMS = 161;
	public static final int GET_INQ_ASG_TO_LIST = 162;
	public static final int GET_INQ_OWN_LIST = 163;
	public static final int GET_INQ_HIST_USER_LIST = 164;
	public static final int GET_INQ_COMMENTS_LOG = 165;
	public static final int GET_EXPIRED_INQUIRIES = 166;
	public static final int GET_EXPIRED_ACTIONITEMS = 167;
	public static final int SEND_BULK_EMAIL = 168;
	public static final int RESEND_EMAIL_ACK = 169;
	public static final int GET_MA_ACT_LOCK = 170;
	public static final int UPDATE_MA_ACT_LOCK = 171;
	public static final int UPDATE_INQUIRY_CURRENT_OWNERS = 172;


/* Inquiry Module Clean-up; TO BE DELETED Start */

	 //public static final int CREATE_INQUIRY = 101;
	 //public static final int SEARCH_INQUIRY = 102;
	 public static final int SEARCH_MEMBERSHIP_FOR_INQUIRY = 127;
	 public static final int SEARCH_INQUIRY_FOR_PARTY = 129;
	 public static final int DELETE_MY_ACTION_ITEMS = 143;
	 public static final int GET_MY_INQUIRIES = 145;
	 public static final int GET_DELETED_INQUIRIES = 149;
	 //public static final int DELETED_INQUIRIES = 150;
	 //public static final int GET_DESIGNATED_USER_DETAILS = 152;
	 //public static final int CREATE_AUDIT = 153;
	 //public static final int LIST_AUDITS = 154;

/* Inquiry Module Clean-up TO BE DELETED End */

 	 /*
 	  * Part of INquiry Management Cleanup by Jaya Shyam Narayana Lingamchetty 18 Sep 2006
 	  * New Services End

	/*
	 * Logon  Controller Entries  Constants range reserved 200 - 299
	 */
	 public static final int  PREP_STARTUP_LOAD = 200;
	 public static final int  AUTHENTICATE_LOGON = 201;
	 public static final int  LOGOUT = 202;

	/*
	 * Adjustments Modeling Related Service Constants range reserved 1000 - 1099
	 */
	 public static final int  GET_MODEL_LIST = 1000;
	 public static final int  ADD_MODEL = 1001;
	 public static final int  GET_TRIGGER_LIST = 1002;
	 public static final int  GET_MODEL = 1003;
	 public static final int  GET_TRIGGER = 1004;
	 public static final int  DELETE_TRIGGER = 1005;
	 public static final int  ADD_TRIGGERS = 1006;
	 public static final int  UPDATE_MODEL = 1007;
	 public static final int  UPDATE_TRIGGER = 1008;
	 public static final int  SCHEDULE_ADJ_RUN = 1009;
	 public static final int  GET_MODEL_RESULTS_LIST = 1010;
	 public static final int  TRIGGER_SEARCH = 1011;
	 public static final int  POST_MODEL_RESULTS=1012;
	 public static final int  UPDATE_TRIGGER_SEARCH_RESULTS=1013;
	 public static final int  ADD_OVERLAPPING_TRIGGERS = 1014;
	 public static final int  DELETE_OVERLAPPING_TRIGGERS = 1015;
	 public static final int  GET_ADJUSTMENT_RESULTS = 1016;
	 public static final int  GET_MANUAL_ADJUSTMENTS = 1017;
	 public static final int  ADD_MANUAL_ADJUSTMENT  = 1018;
	 public static final int  LOAD_MANUAL_ADJUSTMENTS = 1019;
	 public static final int  UPDATE_MANUAL_ADJUSTMENTS = 1020;
	 public static final int  GET_ADJUSTMENT_RESULTS_LEVEL2 = 1021;
	 public static final int  GET_ADJUSTMENT_RESULTS_LEVEL3 = 1022;
	 public static final int  GET_ADJUSTMENT_RESULTS_LEVEL4 = 1023;
	 public static final int  POST_ADJUSTMENT_RESULTS = 1024;
	 public static final int  GET_UNEDITED_TRIGGERS_LIST = 1025;
	 public static final int  BULK_UPDATE_ADJ_TRIGGERS = 1026;
	 public static final int  ADJUSTMENT_PARTY_SEARCH = 1027;
	 public static final int  ADJUSTMENT_WORK_SEARCH = 1028;

	// EWA Controller service names
	 public static final int  EWA_GET_RPI_LIST = 1102;
	 public static final int  EWA_SUBMIT_WIT_INQUIRY = 2012;
	 public static final int  WIT_GET_INQUIRY_TYPES = 2013;
	 public static final int  EWA_GET_CATEGORY_TYPES = 2014;
	 public static final int  WIT_GET_CATEGORY_TYPES = 2015;
	 public static final int  WIT_SEARCH_PARTY = 2022;
	 //TO BE DELETED - START
	 public static final int  EWA_GET_RPI_DETAILS = 1103;
	 //	TO BE DELETED - END

//	TO BE DELETED - START
	 public static final int  EWA_SUBMIT_RPI_DETAILS = 1104;
	 //	TO BE DELETED - END

	 public static final int  EWA_GET_REP_DETAILS = 1105;
	 public static final int  EWA_SUBMIT_REP_DETAILS = 1106;
	 public static final int  EWA_GET_MY_INQUIRIES_LIST = 2005;
	 public static final int  EWA_GET_MY_INQUIRY_DETAILS = 2006;
	 public static final int  EWA_GET_MY_INQUIRY_ATTACHMENT = 2007;
	 public static final int  EWA_GET_MY_INQUIRY_ATTACHMENT_LIST = 2008;
	 public static final int  EWA_CREATE_INQUIRY_ATTACHMENT_TEMP = 2009;
	 public static final int  EWA_GET_MY_INQUIRY_ATTACH_LIST_TEMP = 2010;
	 public static final int  EWA_DELETE_INQUIRY_ATTACHMENT_TEMP = 2011;
	 
	 
	 public static final int  EWA_GET_MEMBER_STATUS_DETAILS = 2018;
	 public static final int  EWA_CANCEL_RESIGNATION_REQUEST = 2019;
	 public static final int  EWA_UPDATE_MEMBER_RIGHTS_PREFERENCES = 2020;
	 public static final int  EWA_SUBMIT_RESIGNATION_REQUEST = 2021;
	 
	 
	 

	 public static final int  CREATE_USER = 1107;
	 public static final int  DELETE_USER = 1108;
	 public static final int  CHANGE_PASSWORD = 1109;
	 public static final int  RESET_PASSWORD = 1110;
	 public static final int  UPDATE_USER = 1111;
	 public static final int  SET_PASSWORD_STATUS = 1112;
	 public static final int  SET_USER_STATUS = 1113;
	 public static final int  SEARCH_USERPROFILES = 1114;
	 public static final int  RETRIEVE_USERPROFILE = 1115;
	 public static final int  ADD_TO_USERPRO = 2501;
	 public static final int  UPDATE_USER_GROUPS = 2502;
	 public static final int  RETRIEVE_USER_GROUPS = 2503;
	 public static final int  SEARCH_PROFILE_DETAILS = 2504;	 


	 public static final int  RETRIEVE_ASCAP_USERPROFILE = 2000;
	 public static final int  UPDATE_ASCAP_USERPASSWORD = 2001;
	 public static final int  UPDATE_ASCAP_USER = 2002;
	 public static final int  RESET_ASCAP_USERPASSWORD = 2003;

	 public static final int ADMIN_INSERT_USER_LOGIN_HISTORY = 2016;
	 public static final int ADMIN_UPDATE_USER_LOGIN_HISTORY = 2017;

	 

	 public static final int EWA_FIRST_TIME_LOGIN = 1131;

	 public static final int GET_MAIN_USER_DETAILS_FROM_PTY_ID = 1132;


  	 public static final int GET_ENTERPRISE_SESSION = 1180;

	 /*****Service Controllers for PREP Reports POC from 1800 BEGIN*****/
	 public static final int PREP_VIEW_REPORT_INSTANCE = 1800;
	 public static final int PREP_VIEW_INSTANCE = 1801;
	 public static final int PREP_EXECUTE_REPORT= 1802;
 	 public static final int PREP_EXECUTE_INSTANCE_LATTER = 1803;
 	 public static final int PREP_EXECUTE_INSTANCE_NOW = 1807;
	 public static final int PREP_SHOW_INPUTS = 1804;
	 public static final int PREP_VIEW_INSTANCE_CSV=1805;
	 public static final int GET_REPORT_TYPE=1806;
       public static final int PREP_SEARCH_REPORT=1807;
	 public static final int PREP_CANCEL_INSTANCE=1808;
     	 public static final int PREP_VALIDATE_USER=1809;
       public static final int PREP_VALIDATE_REPORT=1810;
	 public static final int PREP_ADMIN_SEARCH_REPORT=1811;
     	 public static final int PREP_ADMIN_GET_REPORT_INFO=1812;
	 public static final int PREP_ADMIN_UPDATE_REPORT_INFO=1813;

	 public static final int PREP_SHOW_LRRP01_REQUEST_LIST = 1814;
	 public static final int PREP_UPD_CSTM_RPT_REQ_STA= 1815;
	 public static final int PREP_VIEW_REPORT_REQUEST_DETAILS = 1816;

	 public static final int PREP_VIEW_REPORT_ARCHIVES = 1817;
	 public static final int RETURN_REPORT_INSTANCE = 1818;
	 public static final int RESET_HIDDEN_RETURNED_REPORT_INSTANCES = 1819;
     	 /*****Service Controllers for PREP Reports END*****/


	 // Service Controller to reload the lookup data cashe.
	 public static final int RELOAD_LOOKUPDATA = 2600;
 	 public static final int RELOAD_PREP_CONSTANTS = 2601;
	 public static final int RELOAD_PREP_LOG = 2602;
	 public static final int RELOAD_SECURITY_CREDENTIALS = 2603;

	 // LRS module service names
  	 public static final int CREATE_REPORTS_LIST = 2722;
  	 public static final int ADD_TO_REPORTS_LIST = 2723;
  	
  	
  	//ace constants
  	public static final int SEARCH_WRITERS  = 2728;
  	public static final int SEARCH_WRITER_WORKS  = 2729;
  	public static final int SEARCH_PERFORMERS  = 2730;
  	public static final int SEARCH_PERFORMER_WORKS  = 2731;
  	public static final int SEARCH_PUBLISHERS  = 2732;
  	public static final int SEARCH_WORKS  = 2733;
	public static final int SEARCH_WORKS_AND_DETAILS = 2734;
	
  	
  	//File Upload 
	public static final int APM_UPLOAD_FILE  = 2750;
	
  	

}
