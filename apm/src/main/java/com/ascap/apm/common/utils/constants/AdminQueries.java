package com.ascap.apm.common.utils.constants;

/**
 * @author Raju_Ayanampudi To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class AdminQueries {

    private AdminQueries() {

    }

    public static final String DELETE_FLAG_YES = "Y";

    public static final String DELETE_FLAG_NO = "N";

    public static final String GET_GROUP_CREDENTIALS = "SELECT DISTINCT UPPER(FGA_OBJ_ID) FGA_OBJ_ID "
        + "  FROM FGA_GRP_CRDL " + " WHERE (UPPER(FGA_GRP_CDE) = UPPER(?) "
        + "        OR FGA_GRP_CDE IN (SELECT PRNT_FGA_GRP_CDE " + "                             FROM FGA_CHLD_PRNT_GRP "
        + "                            WHERE UPPER(CHLD_FGA_GRP_CDE) = UPPER(?) AND DEL_FL = 'N'  AND APP_NA = 'APM' )) "
        + "       AND INC_EXC_IND = 'I' " + "       AND DEL_FL = 'N' AND APP_NA = 'APM' " + "MINUS "
        + "SELECT DISTINCT UPPER(FGA_OBJ_ID) FGA_OBJ_ID " + "  FROM FGA_GRP_CRDL "
        + " WHERE UPPER(FGA_GRP_CDE) = UPPER(?) AND INC_EXC_IND = 'E' AND DEL_FL = 'N' AND APP_NA = 'APM' ";

    public static final String RETRIEVE_GROUPS =
        "SELECT UPPER(FGA_GRP_CDE) FGA_GRP_CDE FROM FGA_GRP WHERE DEL_FL = 'N' AND APP_NA = 'APM' ";

    public static final String RETRIEVE_USER_GROUPS_QRY =
        "SELECT FGA_GRP_CDE  FROM FGA_USR_GRP WHERE UPPER (USR_ID) = UPPER (?) AND DEL_FL = 'N' ";

    // Queries for Sequences of table
    public static final String SEQUENCE_PREP_CONFIG_ID = "SELECT PREP_CONFG_ID.NEXTVAL FROM DUAL";

    public static final String GET_ALL_USETYPES =
        " SELECT USE_TYP_CDE, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM (SELECT  rownum rn, USE_TYP_CDE, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM USE_TYP WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y') order by USE_TYP_CDE) WHERE RN BETWEEN ? AND ?";

    public static final String GET_USETYPES_COUNT =
        "SELECT COUNT(1) FROM USE_TYP WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y')";

    public static final String GET_USETYPE_BY_CODE =
        " SELECT USE_TYP_CDE, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT, DEL_FL FROM USE_TYP WHERE UPPER(USE_TYP_CDE) = ?";

    public static final String DELETE_USETYPE = "UPDATE USE_TYP SET DEL_FL = 'Y' WHERE USE_TYP_CDE = ?";

    public static final String ADD_USETYPE =
        "INSERT INTO USE_TYP (USE_TYP_CDE, NA, DES, CRE_ID, CRE_DT) VALUES(UPPER(?), ?, ?, ?, SYSDATE)";

    public static final String UPDATE_USETYPE =
        "UPDATE USE_TYP SET NA = ?, DES = ?, UPD_ID = ?, UPD_DT = SYSDATE, DEL_FL = 'N'  WHERE USE_TYP_CDE = UPPER(?)";

    public static final String GET_ALL_SAMPLETYPES =
        " SELECT SAM_TYP_CDE, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM ( SELECT rownum rn, SAM_TYP_CDE, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM SAM_TYP WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y') order by SAM_TYP_CDE) WHERE RN BETWEEN ? AND ? ";

    public static final String GET_SAMPLETYPES_COUNT =
        "SELECT COUNT(1) FROM SAM_TYP WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y')";

    public static final String GET_SAMPLETYPE_BY_CODE =
        " SELECT SAM_TYP_CDE, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT, DEL_FL FROM SAM_TYP WHERE UPPER(SAM_TYP_CDE) = ? ";

    public static final String DELETE_SAMPLETYPE = "UPDATE SAM_TYP SET DEL_FL = 'Y' WHERE SAM_TYP_CDE = ?";

    public static final String ADD_SAMPLETYPE =
        "INSERT INTO SAM_TYP (SAM_TYP_CDE, NA, DES, CRE_ID, CRE_DT) VALUES(UPPER(?), ?, ?, ?, SYSDATE)";

    public static final String UPDATE_SAMPLETYPE =
        "UPDATE SAM_TYP SET NA = ?, DES = ?, UPD_ID = ?, UPD_DT = SYSDATE, DEL_FL = 'N' WHERE SAM_TYP_CDE = UPPER(?)";

    public static final String GET_ALL_SURVEYTYPES =
        " SELECT SUR_TYP_CDE, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM ( SELECT rownum rn, SUR_TYP_CDE, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM SUR_TYP WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y') order by SUR_TYP_CDE) WHERE RN BETWEEN ? AND ? ";

    public static final String GET_SURVEYTYPES_COUNT =
        "SELECT COUNT(1) FROM SUR_TYP WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y')";

    public static final String GET_SURVEYTYPE_BY_CODE =
        " SELECT SUR_TYP_CDE, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT, DEL_FL FROM SUR_TYP WHERE UPPER(SUR_TYP_CDE) = ? ";

    public static final String DELETE_SURVEYTYPE = "UPDATE SUR_TYP SET DEL_FL = 'Y' WHERE SUR_TYP_CDE = ?";

    public static final String ADD_SURVEYTYPE =
        "INSERT INTO SUR_TYP (SUR_TYP_CDE, NA, DES, CRE_ID, CRE_DT) VALUES(UPPER(?), ?, ?, ?, SYSDATE)";

    public static final String UPDATE_SURVEYTYPE =
        "UPDATE SUR_TYP SET NA = ?, DES = ?, UPD_ID = ?, UPD_DT = SYSDATE, DEL_FL = 'N' WHERE SUR_TYP_CDE = UPPER(?)";

    public static final String GET_ALL_LICENSETYPES =
        " SELECT LIC_TYP_CDE, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM ( SELECT rownum rn, LIC_TYP_CDE, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM LIC_TYP WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y') order by LIC_TYP_CDE) WHERE RN BETWEEN ? AND ? ";

    public static final String GET_LICENSETYPES_COUNT =
        "SELECT COUNT(1) FROM LIC_TYP WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y')";

    public static final String GET_LICENSETYPE_BY_CODE =
        " SELECT LIC_TYP_CDE, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT, DEL_FL FROM LIC_TYP WHERE UPPER(LIC_TYP_CDE) = ? ";

    public static final String DELETE_LICENSETYPE = "UPDATE LIC_TYP SET DEL_FL = 'Y' WHERE LIC_TYP_CDE = ?";

    public static final String ADD_LICENSETYPE =
        "INSERT INTO LIC_TYP (LIC_TYP_CDE, NA, DES, CRE_ID, CRE_DT) VALUES(UPPER(?), ?, ?, ?, SYSDATE)";

    public static final String UPDATE_LICENSETYPE =
        "UPDATE LIC_TYP SET NA = ?, DES = ?, UPD_ID = ?, UPD_DT = SYSDATE, DEL_FL = 'N' WHERE LIC_TYP_CDE = UPPER(?)";

    public static final String GET_ALL_RFPTYPES =
        " SELECT RFP_TYP, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM ( SELECT rownum rn, RFP_TYP, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM RFP_TYP WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y') order by RFP_TYP) WHERE RN BETWEEN ? AND ? ";

    public static final String GET_RFPTYPES_COUNT =
        "SELECT COUNT(1) FROM RFP_TYP WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y')";

    public static final String GET_RFPTYPE_BY_CODE =
        " SELECT RFP_TYP, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT, DEL_FL FROM RFP_TYP WHERE UPPER(RFP_TYP) = ? ";

    public static final String DELETE_RFPTYPE = "UPDATE RFP_TYP SET DEL_FL = 'Y' WHERE RFP_TYP = ?";

    public static final String ADD_RFPTYPE =
        "INSERT INTO RFP_TYP (RFP_TYP, NA, DES, CRE_ID, CRE_DT) VALUES(UPPER(?), ?, ?, ?, SYSDATE)";

    public static final String UPDATE_RFPTYPE =
        "UPDATE RFP_TYP SET NA = ?, DES = ?, UPD_ID = ?, UPD_DT = SYSDATE, DEL_FL = 'N' WHERE RFP_TYP = UPPER(?)";

    public static final String GET_ALL_MUSUSERTYPES =
        " SELECT MUS_USER_TYP_CDE, MUS_USER_GRP, NA, MUS_USER_TYP_DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM ( SELECT rownum rn, MUS_USER_TYP_CDE, MUS_USER_GRP, NA, MUS_USER_TYP_DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM MUS_USER_TYP WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y') order by MUS_USER_TYP_CDE) WHERE RN BETWEEN ? AND ? ";

    public static final String GET_MUSUSERTYPES_COUNT =
        "SELECT COUNT(1) FROM MUS_USER_TYP WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y')";

    public static final String GET_MUSUSERTYPE_BY_CODE =
        " SELECT MUS_USER_TYP_CDE, MUS_USER_GRP, NA, MUS_USER_TYP_DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT, DEL_FL FROM MUS_USER_TYP WHERE UPPER(MUS_USER_TYP_CDE) = ? ";

    public static final String DELETE_MUSUSERTYPE = "UPDATE MUS_USER_TYP SET DEL_FL = 'Y' WHERE MUS_USER_TYP_CDE = ?";

    public static final String ADD_MUSUSERTYPE =
        "INSERT INTO MUS_USER_TYP (MUS_USER_TYP_CDE, NA, MUS_USER_TYP_DES, CRE_ID, CRE_DT, MUS_USER_GRP) VALUES(UPPER(?), ?, ?, ?, SYSDATE, ?)";

    public static final String UPDATE_MUSUSERTYPE =
        "UPDATE MUS_USER_TYP SET NA = ?, MUS_USER_TYP_DES = ?, UPD_ID = ?, UPD_DT = SYSDATE, DEL_FL = 'N', MUS_USER_GRP = ? WHERE MUS_USER_TYP_CDE = UPPER(?)";

    public static final String GET_ALL_CERMONIALAWARDCATEGORIES =
        "SELECT CERMNL_CAT_CDE, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM (SELECT rownum rn,CERMNL_CAT_CDE, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM CERMNL_AWD_CAT WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y') order by CERMNL_CAT_CDE) WHERE RN BETWEEN ? AND ? ";

    public static final String GET_CERMONIALAWARDCATEGORIES_COUNT =
        "SELECT COUNT(1) FROM CERMNL_AWD_CAT WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y')";

    public static final String GET_CERMONIALAWARDCATEGORY_BY_CODE =
        " SELECT CERMNL_CAT_CDE, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT, DEL_FL FROM CERMNL_AWD_CAT WHERE UPPER(CERMNL_CAT_CDE) = ? ";

    public static final String DELETE_CERMONIALAWARDCATEGORY =
        "UPDATE CERMNL_AWD_CAT SET DEL_FL = 'Y' WHERE CERMNL_CAT_CDE = ?";

    public static final String ADD_CERMONIALAWARDCATEGORY =
        "INSERT INTO CERMNL_AWD_CAT (CERMNL_CAT_CDE, NA, DES, CRE_ID, CRE_DT) VALUES(UPPER(?), ?, ?, ?, SYSDATE)";

    public static final String UPDATE_CERMONIALAWARDCATEGORY =
        "UPDATE CERMNL_AWD_CAT SET NA = ?, DES = ?, UPD_ID = ?, UPD_DT = SYSDATE, DEL_FL = 'N' WHERE CERMNL_CAT_CDE = UPPER(?)";

    public static final String GET_ALL_CERMONIALAWARDGENRES =
        "SELECT CERMNL_AWD_GEN_ID, CAT, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM (SELECT rownum rn, CERMNL_AWD_GEN_ID, CAT, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM CERMNL_AWD_GEN WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y') order by CERMNL_AWD_GEN_ID) WHERE RN BETWEEN ? AND ? ";

    public static final String GET_CERMONIALAWARDGENRES_COUNT =
        "SELECT COUNT(1) FROM CERMNL_AWD_GEN WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y')";

    public static final String GET_CERMONIALAWARDGENRE_BY_CODE =
        " SELECT CERMNL_AWD_GEN_ID, CAT, NA, DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT, DEL_FL FROM CERMNL_AWD_GEN WHERE UPPER(CERMNL_AWD_GEN_ID) = ? ";

    public static final String DELETE_CERMONIALAWARDGENRE =
        "UPDATE CERMNL_AWD_GEN SET DEL_FL = 'Y' WHERE CERMNL_AWD_GEN_ID = ?";

    public static final String ADD_CERMONIALAWARDGENRE =
        "INSERT INTO CERMNL_AWD_GEN (CERMNL_AWD_GEN_ID, NA, DES, CRE_ID, CRE_DT, CAT) VALUES(UPPER(?), ?, ?, ?, SYSDATE, ?)";

    public static final String UPDATE_CERMONIALAWARDGENRE =
        "UPDATE CERMNL_AWD_GEN SET NA = ?, DES = ?, UPD_ID = ?, UPD_DT = SYSDATE, DEL_FL = 'N', CAT = ? WHERE CERMNL_AWD_GEN_ID = UPPER(?)";

    public static final String GET_ALL_CERMONIALAWARDTITLES =
        "SELECT CERMNL_AWD_TTL_ID, NA, CERMNL_AWD_TTL, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM (SELECT rownum rn, CERMNL_AWD_TTL_ID, NA, CERMNL_AWD_TTL, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM CERMNL_AWD_TTL WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y') order by CERMNL_AWD_TTL_ID) WHERE RN BETWEEN ? AND ? ";

    public static final String GET_CERMONIALAWARDTITLES_COUNT =
        "SELECT COUNT(1) FROM CERMNL_AWD_TTL WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y')";

    public static final String GET_CERMONIALAWARDTITLE_BY_CODE =
        " SELECT CERMNL_AWD_TTL_ID, NA, CERMNL_AWD_TTL, CRE_ID, CRE_DT, UPD_ID, UPD_DT, DEL_FL FROM CERMNL_AWD_TTL WHERE UPPER(CERMNL_AWD_TTL_ID) = ? ";

    public static final String DELETE_CERMONIALAWARDTITLE =
        "UPDATE CERMNL_AWD_TTL SET DEL_FL = 'Y' WHERE CERMNL_AWD_TTL_ID = ?";

    public static final String ADD_CERMONIALAWARDTITLE =
        "INSERT INTO CERMNL_AWD_TTL (CERMNL_AWD_TTL_ID, NA, CERMNL_AWD_TTL, CRE_ID, CRE_DT) VALUES(UPPER(?), ?, ?, ?, SYSDATE)";

    public static final String UPDATE_CERMONIALAWARDTITLE =
        "UPDATE CERMNL_AWD_TTL SET NA = ?, CERMNL_AWD_TTL = ?, UPD_ID = ?, UPD_DT = SYSDATE, DEL_FL = 'N' WHERE CERMNL_AWD_TTL_ID = UPPER(?)";

    public static final String GET_ALL_EXPLTTYPES =
        "SELECT EXPLT_TYP_CDE, NA, EXPLT_TYP_DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM (SELECT rownum rn, EXPLT_TYP_CDE, NA, EXPLT_TYP_DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT FROM EXPLT_TYP WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y') order by EXPLT_TYP_CDE) WHERE RN BETWEEN ? AND ? ";

    public static final String GET_EXPLTTYPES_COUNT =
        "SELECT COUNT(1) FROM EXPLT_TYP WHERE (DEL_FL IS NULL OR DEL_FL <> 'Y')";

    public static final String GET_EXPLTTYPE_BY_CODE =
        " SELECT EXPLT_TYP_CDE, NA, EXPLT_TYP_DES, CRE_ID, CRE_DT, UPD_ID, UPD_DT, DEL_FL FROM EXPLT_TYP WHERE UPPER(EXPLT_TYP_CDE) = ? ";

    public static final String DELETE_EXPLTTYPE = "UPDATE EXPLT_TYP SET DEL_FL = 'Y' WHERE EXPLT_TYP_CDE = ?";

    public static final String ADD_EXPLTTYPE =
        "INSERT INTO EXPLT_TYP (EXPLT_TYP_CDE, NA, EXPLT_TYP_DES, CRE_ID, CRE_DT) VALUES(UPPER(?), ?, ?, ?, SYSDATE)";

    public static final String UPDATE_EXPLTTYPE =
        "UPDATE EXPLT_TYP SET NA = ?, EXPLT_TYP_DES = ?, UPD_ID = ?, UPD_DT = SYSDATE, DEL_FL = 'N' WHERE EXPLT_TYP_CDE = UPPER(?)";

    /***********************************************
     * Queries for Prep Configuration Parameter Use case
     */
    // PREP_CONFIG_PAR_TYP, PREP_CONFG_PAR_TYP_CDE,

    public static final String GET_PREP_CONFIG_PARAM_LIST_COUNT_ALL =
        "SELECT COUNT(*) FROM PREP_CONFIG WHERE DEL_FL = 'N' ";

    // PREP_CONFG_ID, PREP_CONFG_PAR_TYP_CDE, PAR, STT_DT, END_DT, DEL_FL
    public static final String GET_PREP_CONFIG_PARAM_LIST_COUNT_ACTIVE =
        "SELECT COUNT(*) FROM PREP_CONFIG WHERE DEL_FL = 'N' AND STT_DT <  SYSDATE AND END_DT >  SYSDATE ";

    public static final String GET_PREP_CONFIG_PARAM_LIST_ALL =
        " select PREP_CONFG_ID PARAM_ID, PREP_CONFG_PAR_TYP_CDE PARAM_CDE,  PARAM_NAME, PAR PARAM_VALUE, STT_DT, END_DT from"
            + " ( select ROWNUM RN, PREP_CONFG_ID , A.PREP_CONFG_PAR_TYP_CDE ,B.DES PARAM_NAME,  PAR , to_char(STT_DT, 'MM/DD/YYYY') STT_DT, to_char(END_DT, 'MM/DD/YYYY') END_DT "
            + " from PREP_CONFIG A, PREP_CONFIG_PAR_TYP B " + "  where  A.DEL_FL= '" + DELETE_FLAG_NO
            + "' and A.PREP_CONFG_PAR_TYP_CDE = B.PREP_CONFG_PAR_TYP_CDE order by  STT_DT, END_DT) "
            + " WHERE RN >= ? AND RN < ?  order by  STT_DT, END_DT";

    public static final String GET_PREP_CONFIG_PARAM_LIST_ACTIVE =
        " select PREP_CONFG_ID PARAM_ID, PREP_CONFG_PAR_TYP_CDE PARAM_CDE, PARAM_NAME, PAR PARAM_VALUE, STT_DT, END_DT from"
            + " ( select ROWNUM RN, PREP_CONFG_ID , A.PREP_CONFG_PAR_TYP_CDE , B.DES PARAM_NAME, PAR , to_char(STT_DT, 'MM/DD/YYYY') STT_DT, to_char(END_DT, 'MM/DD/YYYY') END_DT "
            + " from PREP_CONFIG A, PREP_CONFIG_PAR_TYP B  " + "  where  A.DEL_FL= '" + DELETE_FLAG_NO
            + "' AND  A.PREP_CONFG_PAR_TYP_CDE = B.PREP_CONFG_PAR_TYP_CDE AND STT_DT < SYSDATE AND END_DT > SYSDATE "
            + " order by  STT_DT, END_DT) " + " WHERE RN >= ? AND RN < ?  order by  STT_DT, END_DT";

    public static final String GET_PREP_CONFIG_PARAM_LIST_ALL_TO_CACHE =
        " select PREP_CONFG_ID PARAM_ID, A.PREP_CONFG_PAR_TYP_CDE PARAM_CDE,  PAR PARAM_VALUE,"
            + " to_char(STT_DT, 'MM/DD/YYYY') STT_DT, to_char(END_DT, 'MM/DD/YYYY') END_DT "
            + " from PREP_CONFIG A, PREP_CONFIG_PAR_TYP B " + " where   A.DEL_FL= '" + DELETE_FLAG_NO
            + "' and   A.PREP_CONFG_PAR_TYP_CDE = B.PREP_CONFG_PAR_TYP_CDE " + " order by  STT_DT, END_DT ";

    public static final String GET_PREP_CONFIG_PARAM =
        " select  PREP_CONFG_ID PARAM_ID, PREP_CONFG_PAR_TYP_CDE ,PREP_CONFG_PAR_TYP_CDE PARAM_NAME, PAR PARAM_VALUE, to_char(STT_DT, 'MM/DD/YYYY') STT_DT, to_char(END_DT, 'MM/DD/YYYY') END_DT  "
            + " from PREP_CONFIG  " + " where PREP_CONFG_ID = ? " + " and DEL_FL= '" + DELETE_FLAG_NO + "' ";

    public static final String ADD_PREP_CONFIG_PARAM =
        "insert into PREP_CONFIG(PREP_CONFG_ID , PREP_CONFG_PAR_TYP_CDE, PAR, STT_DT, END_DT, CRE_ID, CRE_DT, DEL_FL)"
            + "VALUES (?, ?, ? , TO_DATE(?, 'MM/DD/YYYY'), TO_DATE(?, 'MM/DD/YYYY'), 'TEST_SYSTEM' ,SYSDATE, '"
            + DELETE_FLAG_NO + "')";

    public static final String DELETE_PREP_CONFIG_PARAM =
        " update PREP_CONFIG set DEL_FL='Y' where PREP_CONFG_ID = ? and DEL_FL = 'N' ";

    public static final String UPDATE_PREP_CONFIG_PARAM =
        " update PREP_CONFIG set PAR = ?, STT_DT = TO_DATE(?, 'MM/DD/YYYY'),END_DT = TO_DATE(?, 'MM/DD/YYYY'), UPD_ID = ?, UPD_DT = SYSDATE where PREP_CONFG_ID = ?  and DEL_FL = 'N'";

    /*
     * Validation Queries
     */
    public static final String IS_OVERLAP_PREP_CONFIG_PARAM_EXIST_FOR_ADD = " SELECT count(*) from PREP_CONFIG  "
        + " where  PREP_CONFG_PAR_TYP_CDE = ? AND ( " + " (" + "    (" + "        (  "
        + "            TO_DATE(?,'MM/DD/YYYY') >= TO_DATE(TO_CHAR(STT_DT,'MM/DD/YYYY'),'MM/DD/YYYY') " + "          OR "
        + "            TO_DATE(?,'MM/DD/YYYY') <= TO_DATE(TO_CHAR(END_DT,'MM/DD/YYYY'),'MM/DD/YYYY')" + "         ) "
        + "      and" + "         ("
        + "           TO_DATE(?,'MM/DD/YYYY') <= TO_DATE(TO_CHAR(END_DT,'MM/DD/YYYY'),'MM/DD/YYYY')" + "         )"
        + "    )" + " AND " + "   (   " + "         ("
        + "            TO_DATE(?,'MM/DD/YYYY') >= TO_DATE(TO_CHAR(STT_DT,'MM/DD/YYYY'),'MM/DD/YYYY') " + "          OR "
        + " 	       TO_DATE(?,'MM/DD/YYYY') <= TO_DATE(TO_CHAR(END_DT,'MM/DD/YYYY'),'MM/DD/YYYY')" + "         )"
        + "       AND " + "         ("
        + "            TO_DATE(?,'MM/DD/YYYY') >= TO_DATE(TO_CHAR(STT_DT,'MM/DD/YYYY'),'MM/DD/YYYY')" + "         )"
        + "    ) " + " )  " + " AND (DEL_FL IS NULL OR DEL_FL <> 'Y') )";

    public static final String IS_OVERLAP_PREP_CONFIG_PARAM_EXIST_FOR_UPDATE =

        " SELECT count(*)  from PREP_CONFIG  " + " where  PREP_CONFG_PAR_TYP_CDE = ? AND ( " + " (" + "    ("
            + "        (  "
            + "            TO_DATE(?,'MM/DD/YYYY') >= TO_DATE(TO_CHAR(STT_DT,'MM/DD/YYYY'),'MM/DD/YYYY') "
            + "          OR "
            + "            TO_DATE(?,'MM/DD/YYYY') <= TO_DATE(TO_CHAR(END_DT,'MM/DD/YYYY'),'MM/DD/YYYY')"
            + "         ) " + "      and" + "         ("
            + "           TO_DATE(?,'MM/DD/YYYY') <= TO_DATE(TO_CHAR(END_DT,'MM/DD/YYYY'),'MM/DD/YYYY')" + "         )"
            + "    )" + " AND " + "   (   " + "         ("
            + "            TO_DATE(?,'MM/DD/YYYY') >= TO_DATE(TO_CHAR(STT_DT,'MM/DD/YYYY'),'MM/DD/YYYY') "
            + "          OR "
            + " 	       TO_DATE(?,'MM/DD/YYYY') <= TO_DATE(TO_CHAR(END_DT,'MM/DD/YYYY'),'MM/DD/YYYY')" + "         )"
            + "       AND " + "         ("
            + "            TO_DATE(?,'MM/DD/YYYY') >= TO_DATE(TO_CHAR(STT_DT,'MM/DD/YYYY'),'MM/DD/YYYY')" + "         )"
            + "    ) " + " )  " + " AND (DEL_FL IS NULL OR DEL_FL <> 'Y') )" + " AND PREP_CONFG_ID != ? ";

    public static final String GET_DESIGNATED_USERS =
        "SELECT USR_ID, FNAME, LNAME, PTY_ID FROM USER_PRO WHERE PTY_ID = ? AND USR_TYP_CDE = ? AND DEL_FL <> 'Y'";

    public static final String CREATE_DESIGNATED_USER =
        "INSERT INTO user_pro " + "            (usr_id, fname, lname, usr_typ_cde, em_adr, "
            + "             phy_cny_cde, ph_nr, adr_ln1, adr_ln2, cty, st_cde, psl_cde, "
            + "             cny_cde, del_fl, cre_id, pty_id, area_cde, cre_dt, sal, suf, "
            + "             suf_oth, prov, mid_na, act_src " + "            ) " + "     VALUES (?, ?, ?, ?, ?, "
            + "             ?, ?, ?, ?, ?, ?, ?, " + "             ?, ?, ?, ?, ?, SYSDATE, ?, ?, "
            + "             ?, ?, ?, ? )";

    public static final String CREATE_USER_IN_TAM_USERS =
        "INSERT INTO tam_users " + "            (user_id, fst_na, lst_na, usr_typ_cde, del_fl, cre_dt, "
            + "             user_sta_id, rgsy_uid, cre_id " + "            ) "
            + "     VALUES (?, ?, ?, ?, 'N', SYSDATE, " + "             ?, ?, ? " + "            ) ";

    public static final String CREATE_PREP_USER =
        "INSERT INTO USER_PRO(USR_ID, FNAME, LNAME, USR_TYP_CDE, EM_ADR, PHY_CNY_CDE, PH_NR, ADR_LN1, ADR_LN2, CTY, ST_CDE, PSL_CDE, CNY_CDE, DEL_FL, CRE_ID, CRE_DT) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)  ";

    public static final String CREATE_SECRET_QUESTIONS = "INSERT INTO MA_SCRT_QUES "
        + "(MA_SCRT_QUES_ID, USR_ID, QUES_CDE, ANS, QUES_CDE_LST_ID, CRE_ID, CRE_DT, DEL_FL) "
        + "(SELECT MA_SCRT_QUES_ID.NEXTVAL, ?, ?, PKG_PREP_SECURITY.FNC_SCRAMBLE(?), ?, ?, SYSDATE, 'N' FROM DUAL) ";

    public static final String GET_USER_ROLE_ASSIGNMENT =
        "SELECT DISTINCT(MA_USR_GRP_CDE) FROM MA_USR_GRP_ASGMT WHERE USR_ID = ? AND DEL_FL = 'N' ";

    public static final String DELETE_USER_ROLE_ASSIGNMENT = "DELETE FROM MA_USR_GRP_ASGMT WHERE UPPER(USR_ID) = ? ";

    public static final String GET_ELEM_GROUPS_FROM_DB =
        "SELECT DISTINCT(MA_GRP_CDE) FROM MA_USR_GRP_LNK WHERE MA_USR_GRP_CDE IN "
            + "(SELECT MA_USR_GRP_CDE FROM MA_USR_GRP_ASGMT WHERE USR_ID = ? AND DEL_FL = 'N') AND DEL_FL= 'N'";

    public static final String CREATE_NEW_USER_ROLE_ASSIGNMENT =
        "INSERT INTO MA_USR_GRP_ASGMT  " + "(MA_USR_GRP_ASGMT_ID, USR_ID, MA_USR_GRP_CDE, CRE_ID, CRE_DT) "
            + " SELECT MA_USR_GRP_ASGMT_ID.NEXTVAL, ?, ?, ?, SYSDATE  FROM DUAL ";

    public static final String GET_DESIGNATED_USER_DETAILS =
        "SELECT A.USR_ID, " + "       A.FNAME, " + "       A.LNAME, " + "       A.USR_TYP_CDE, " + "       A.EM_ADR, "
            + "       A.USER_DPT, " + "       A.USER_ACS_PER_CDE, " + "       A.act_src, " + "       B.user_sta_id "
            + "  FROM USER_PRO A, TAM_USERS B " + " WHERE     A.USR_ID = B.USER_ID "
            + "       AND UPPER(A.USR_ID) = UPPER(?) " + "       AND a.usr_typ_cde = 'PREP' "
            + "       AND A.DEL_FL = 'N' " + "       AND B.DEL_FL = 'N' ";

    public static final String GET_MAIN_USER_DETAILS_FROM_PTY_ID_COUNT =
        "SELECT COUNT(1) FROM TAM_USERS TU, USER_PRO UP LEFT OUTER JOIN PTY P ON P.PTY_ID = UP.PTY_ID WHERE UP.PTY_ID = ? AND UP.USR_TYP_CDE IN (?, ?) AND UP.USR_ID = TU.USER_ID";

    public static final String GET_MAIN_USER_DETAILS_FROM_PTY_ID =
        "SELECT UP.usr_id, N.fst_na fname, N.na lname, UP.usr_typ_cde, UP.pty_id, "
            + "       p.pty_typ_cde, p.pty_rol_typ_cde, sec.ques_cde, PKG_PREP_SECURITY.FNC_DESCRAMBLE(sec.ans) as ans, UP.em_adr, UP.phy_cny_cde, "
            + "       UP.ph_nr, UP.adr_ln1, UP.adr_ln2, UP.cty, UP.st_cde, UP.psl_cde, "
            + "       UP.cny_cde, UP.usr_typ_cde, tu.user_sta_id, " + "       (SELECT ms.sta "
            + "          FROM mbr_sta ms " + "         WHERE ms.pty_id = p.pty_id " + "           AND ms.del_fl = 'N' "
            + "           AND ms.vld_invld_ind = 'Y' " + "           AND (SYSDATE BETWEEN ms.eff_dt AND ms.end_dt) "
            + "           AND ROWNUM = 1) sta, " + "        (SELECT sta fnl_sta " + "          FROM (SELECT di.sta, "
            + "                       di.pty_id, " + "                       RANK () "
            + "                       OVER (PARTITION BY di.pty_id "
            + "                             ORDER BY di.eff_dt DESC NULLS LAST) " + "                          rn "
            + "                  FROM pprep.mbr_sta di "
            + "                 WHERE di.del_fl = 'N' AND di.vld_invld_ind = 'Y')  "
            + "         WHERE pty_id = p.pty_id AND rn = 1) fnl_sta,  " + "       p.decd_ind, up.act_src "
            + "  FROM tam_users tu, ma_scrt_ques sec,user_pro UP, pty p, pty_na n  " + " WHERE UP.pty_id = ? "
            + "   AND UP.usr_typ_cde IN (?, ?) " + "   AND UP.usr_id = tu.user_id "
            + "   AND UP.usr_id = sec.usr_id(+) " + "   AND p.pty_id(+) = UP.pty_id " + "	AND P.pty_id = N.pty_id(+) "
            + "	AND N.pty_na_typ_cde(+) = 'PA' " + "   and sec.del_fl(+) = 'N' "
            + "   ORDER BY sec.ques_cde_lst_id asc ";

    public static final String GET_MAIN_USER_DETAILS_FROM_PTY_ID_NO_LOGINID =
        "SELECT '' usr_id, n.fst_na fname, n.na lname, "
            + "       DECODE (p.pty_typ_cde, 'SUPPB', 'EWAS', 'EWAM') usr_typ_cde, p.pty_id, "
            + "       p.pty_typ_cde, p.pty_rol_typ_cde, NULL ques_cde, NULL ans, NULL em_adr, "
            + "       NULL phy_cny_cde, NULL ph_nr, NULL adr_ln1, NULL adr_ln2, NULL cty, "
            + "       NULL st_cde, NULL psl_cde, NULL cny_cde, 1 user_sta_id, " + "       (SELECT ms.sta "
            + "          FROM mbr_sta ms " + "         WHERE ms.pty_id = p.pty_id " + "           AND ms.del_fl = 'N' "
            + "           AND ms.vld_invld_ind = 'Y' " + "           AND (SYSDATE BETWEEN ms.eff_dt AND ms.end_dt) "
            + "           AND ROWNUM = 1) sta, " + "        (SELECT sta fnl_sta " + "          FROM (SELECT di.sta, "
            + "                       di.pty_id, " + "                       RANK () "
            + "                       OVER (PARTITION BY di.pty_id "
            + "                             ORDER BY di.eff_dt DESC NULLS LAST) " + "                          rn "
            + "                  FROM pprep.mbr_sta di "
            + "                 WHERE di.del_fl = 'N' AND di.vld_invld_ind = 'Y')  "
            + "         WHERE pty_id = p.pty_id AND rn = 1) fnl_sta,  " + "       p.decd_ind, NULL act_src "
            + "  FROM pty p, pty_na n " + " WHERE p.pty_id = ? AND p.pty_id = n.pty_id AND n.pty_na_typ_cde = 'PA' ";

    /** <!-- End ascapdevel changes --> **/

    public static final String UPDATE_DESIGNATED_USER_DETAILS = "UPDATE user_pro " + "   SET fname = ?, "
        + "       lname = ?, " + "       em_adr = ?, " + "       adr_ln1 = ?, " + "       adr_ln2 = ?, "
        + "       cty = ?, " + "       st_cde = ?, " + "       psl_cde = ?, " + "       cny_cde = ?, "
        + "       phy_cny_cde = ?, " + "       ph_nr = ?, " + "       upd_id = ?, " + "       area_cde = ?, "
        + "       upd_dt = SYSDATE, " + "	   prov = ?, " + "	   suf = ?, " + "	   suf_oth = ?, "
        + "	   sal = ?, " + "	   mid_na = ?	   " + " WHERE UPPER(usr_id) = ? ";

    public static final String DELETE_DESIGNATED_USER = "UPDATE USER_PRO SET DEL_FL = 'Y' WHERE upper(USR_ID) = ?";

    public static final String DELETE_DESIGNATED_USER_FROM_TAM_USERS =
        "UPDATE TAM_USERS SET DEL_FL = 'Y' WHERE upper(USER_ID) = ?";

    public static final String GET_DESIGNATED_USER_IDS =
        "SELECT USR_ID FROM USR_PRO WHERE PTY_ID = ? AND DEL_FL <> 'Y'";

    public static final String GET_PARTY_NAME =
        "SELECT trim(FST_NA || ' ' || NA) PARTYNAME, FST_NA, NA  FROM PTY_NA WHERE PTY_ID = ? AND PTY_NA_TYP_CDE = ?";

    public static final String GET_FULL_PARTY_NAME =
        "SELECT trim(SAL || ' ' || FST_NA || ' ' || MID_INIT || ' '|| NA || ' ' || DECODE(SUF,'Other',SUF_OTH,SUF)) PARTYNAME, SAL, FST_NA, MID_INIT, NA, SUF  FROM PTY_NA WHERE PTY_ID = ? AND PTY_NA_TYP_CDE = ?";

    public static final String REMOVE_OLD_SECRET_QUESTIONS_ANSWERS =
        "UPDATE ma_scrt_ques SET DEL_FL ='Y', UPD_ID = ?, UPD_DT =  SYSDATE WHERE USR_ID = ? ";

    public static final String CREATE_NEW_SECRET_QUESTIONS_ANSWERS =

        "INSERT INTO ma_scrt_ques "
            + "            (ma_scrt_ques_id, usr_id, ques_cde, ans, ques_cde_lst_id, cre_id, cre_dt, del_fl) "
            + "   (SELECT ma_scrt_ques_id.NEXTVAL, ?, ?, PKG_PREP_SECURITY.FNC_SCRAMBLE(?), ?, ?, SYSDATE, 'N' "
            + "      FROM DUAL) ";

    public static final String UPDATE_USER_STATUS =
        "UPDATE TAM_USERS SET USER_STA_ID = ?, UPD_ID = ?, UPD_DT =  SYSDATE WHERE USER_ID = ?";

    public static final String GET_USER_ROLES =
        "SELECT GRP_NA FROM EWA_USER_GRP, EWA_GRP WHERE upper(USER_ID) = ? AND EWA_GRP.GRP_ID = EWA_USER_GRP.GRP_ID";

    public static final String ADD_USER_ROLES =
        "INSERT INTO EWA_USER_GRP (USER_GRP_ID, GRP_ID, USER_ID, CRE_ID, CRE_DT, DEL_FL) VALUES(EWA_USR_GRP_SEQ.NEXTVAL, (SELECT GRP_ID FROM EWA_GRP WHERE GRP_NA = ? AND ROWNUM = 1) , ?, ?, SYSDATE, 'N')";

    public static final String DELETE_USER_ROLES = "DELETE FROM EWA_USER_GRP WHERE upper(USER_ID) = ?";

    public static final String GET_USER = "SELECT USR_ID FROM USER_PRO WHERE upper(USR_ID) = upper(?) ";

    public static final String GET_USER_LOGIN_HST_SEQ = "SELECT USER_LOGIN_HST_SEQ.NEXTVAL FROM dual";

    public static final String GET_USER_SUCCESSFUL_LOGIN_CNT =
        "SELECT COUNT (USER_LOGIN_HST_ID) AS LOGIN_COUNT FROM USER_LOGIN_HST WHERE USER_ID = ? AND USER_LOGIN_ATTEMPT_TYP_ID = "
            + AdminConstants.LOGIN_ATTEMPT_SUCCESSFUL;

    public static final String INSERT_LOGIN_ATTEMPT_RECORD =
        "INSERT INTO USER_LOGIN_HST (USER_LOGIN_HST_ID, USER_ID, USER_LOGIN_ATTEMPT_TYP_ID, DT, LOGIN_SESS_STA, LOGIN_SESS_KEY, LOGIN_ACCS_TYP_CDE, LOGIN_USR_AGNT, USER_LOGIN_HST_TYP_CDE) VALUES(?, ?, ?, SYSDATE, ?, ?, ?, SUBSTRB(?,1,1000), ?)";

    public static final String UPDATE_LOGIN_HISTORY_RECORD =
        "UPDATE USER_LOGIN_HST SET LOGIN_SESS_STA=?, LOGIN_SESS_END_DT = SYSDATE WHERE USER_LOGIN_HST_ID = ? AND NVL(LOGIN_SESS_STA,'"
            + AdminConstants.LOGIN_HST_SESSION_STATUS_ACTIVE + "') = '" + AdminConstants.LOGIN_HST_SESSION_STATUS_ACTIVE
            + "'";

    public static final String GET_FAILD_ATTEMPTS_COUNT =
        "SELECT COUNT (1) " + "  FROM (SELECT ROWNUM rn, lvl1.* " + "          FROM (SELECT   * "
            + "                    FROM user_login_hst " + "                   WHERE UPPER (user_id) = ? "
            + "                ORDER BY dt DESC) lvl1) lvl2 " + " WHERE lvl2.rn < 4 AND user_login_attempt_typ_id = ? ";

    public static final String GET_CHILD_USER_DETAILS = "SELECT distinct pnt_pty_id pnt_pty_id, "
        + "       child_pty_id, p.usr_id, " + "       p.fname, p.lname, p.usr_typ_cde, p.pty_id, "
        + "       p.em_adr, p.phy_cny_cde, p.ph_nr, p.area_cde, p.adr_ln1, p.adr_ln2, "
        + "       p.cty, p.st_cde, p.psl_cde, p.cny_cde, p.usr_typ_cde, tu.user_sta_id, "
        + "       TRIM ((   DECODE (pn.sal, '', '', (pn.sal || ' ')) "
        + "              || DECODE (pn.fst_na, '', '', (pn.fst_na || ' ')) "
        + "              || DECODE (pn.mid_init, '', '', (pn.mid_init || ' ')) "
        + "              || DECODE (pn.na, '', '', (pn.na || ' ')) "
        + "              || DECODE (pn.suf, '', '', (pn.suf || ' ')) " + "             ) " + "            ) pty_name "
        + "  FROM rel r, user_pro p, tam_users tu, pty_na pn " + " WHERE r.rel_typ_cde = 'PSUPP' "
        + "   AND r.pnt_pty_id = ? " + "   AND child_pty_id = p.pty_id " + "   AND p.usr_typ_cde = 'EWAM' "
        + "   AND tu.user_id = p.usr_id " + "   AND p.pty_id = pn.pty_id " + "   AND pn.pty_na_typ_cde = 'PA' "
        + "   AND r.del_fl = 'N' " + "   AND p.del_fl = 'N' " + "   AND tu.del_fl = 'N' ";

    public static final String GET_CHILD_USER_DETAILS_NO_LOGINID =
        " SELECT distinct pnt_pty_id pnt_pty_id,  " + "    child_pty_id, '' usr_id,  "
            + "    null fname, null lname, 'EWAM' usr_typ_cde, pn.pty_id, null question_cde, null ans,  "
            + "    null em_adr, null phy_cny_cde, null ph_nr, null area_cde, null adr_ln1, null adr_ln2,  "
            + "    null cty, null st_cde, null psl_cde, null cny_cde, null usr_typ_cde, 2 user_sta_id,  "
            + "    TRIM ((   DECODE (pn.sal, '', '', (pn.sal || ' '))  "
            + "           || DECODE (pn.fst_na, '', '', (pn.fst_na || ' '))  "
            + "           || DECODE (pn.mid_init, '', '', (pn.mid_init || ' '))  "
            + "           || DECODE (pn.na, '', '', (pn.na || ' '))  "
            + "           || DECODE (pn.suf, '', '', (pn.suf || ' '))  " + "          )  " + "         ) pty_name  "
            + "    FROM rel r, pty_na pn  " + "    WHERE r.rel_typ_cde = 'PSUPP'  " + "      AND r.pnt_pty_id = ? "
            + "      AND child_pty_id = pn.pty_id  " + "      AND pn.pty_na_typ_cde = 'PA'  "
            + "      AND r.del_fl = 'N'   " + " ORDER BY lower(pty_name) ";

    // where del_fl like '%' is required in this query because filter append "AND ...."
    public static final String GET_USERPROFILES_COUNT =
        "SELECT /*+  INDEX(p PTY_PK1) */ COUNT (1) " + "  FROM user_pro, tam_users tu, pty p "
            + " WHERE tu.user_id = user_pro.usr_id AND user_pro.pty_id = p.pty_id(+) AND p.del_fl(+) = 'N' ";

    public static final String GET_USERPROFILES =
        "SELECT usr_id, fname, lname, usr_typ_cde, sta, user_acs_per_cde, decd_ind, pty_sta "
            + "  FROM (SELECT /*+  INDEX(p PTY_PK1) */ ROW_NUMBER () OVER (ORDER BY fname, lname) rn, u.usr_id, "
            + "               u.fname, u.lname, u.usr_typ_cde, s.user_sta_des sta, "
            + "               u.user_acs_per_cde, p.decd_ind, " + "               (SELECT ms.sta "
            + "                  FROM mbr_sta ms " + "                 WHERE ms.pty_id = p.pty_id "
            + "                   AND ms.del_fl = 'N' " + "                   AND ms.vld_invld_ind = 'Y' "
            + "                   AND (SYSDATE BETWEEN ms.eff_dt AND ms.end_dt) "
            + "                   AND ROWNUM = 1) pty_sta "
            + "          FROM user_pro u, ewa_user_sta s, tam_users tu, pty p "
            + "         WHERE tu.user_sta_id = s.user_sta_id " + "           AND tu.user_id = u.usr_id "
            + "           AND u.pty_id = p.pty_id(+) " + "           AND p.del_fl(+) = 'N' ";

    public static final String RETRIEVE_TAMUSER =
        "SELECT USER_ID,   FST_NA,  LST_NA, 'PREP' as USR_TYP_CDE, DPT FROM TAM_USERS WHERE USER_ID = ?";

    public static final String ADD_TO_USERPRO =
        "INSERT INTO USER_PRO (USR_ID, FNAME, LNAME, USR_TYP_CDE, USER_DPT, CRE_ID, CRE_DT) VALUES (?, ?, ?, ?, ?, ?, SYSDATE)";

    public static final String GET_TAMUSERS_COUNT =
        "SELECT COUNT (1) " + "  FROM tam_users a " + " WHERE NOT EXISTS (SELECT user_id "
            + "                     FROM user_pro upr " + "                    WHERE a.user_id = upr.usr_id) "
            + "   AND a.usr_typ_cde = 'PREP' " + "   AND del_fl = 'N' ";

    public static final String GET_TAMUSERS_PREFIX = "SELECT user_id, fst_na, lst_na, rgsy_uid, dpt, usr_typ_cde "
        + "          FROM  " + "          (SELECT ROW_NUMBER() OVER (ORDER BY fst_na, lst_na) rn, user_id, "
        + "                 fst_na, lst_na, rgsy_uid, dpt, usr_typ_cde  " + "          FROM " + "          tam_users a "
        + "         WHERE NOT EXISTS (SELECT user_id " + "                                 FROM user_pro upr "
        + "                                WHERE a.user_id = upr.usr_id) "
        + "           AND a.usr_typ_cde = 'PREP' AND del_fl = 'N' ";

    public static final String GET_TAMUSERS_SUFFIX = ") a";

    public static final String GET_ASCAP_USER_DETAILS =
        "SELECT UP.usr_id, UP.fname, UP.lname, UP.usr_typ_cde, UP.em_adr, "
            + "       tu.user_sta_id, UP.user_des, UP.user_dpt, UP.mid_na, eus.user_sta_des, "
            + "       ud.user_dpt_na, UP.del_fl, UP.pty_id, p.prof_id, p.prof_name, " + "       UP.user_acs_per_cde "
            + "  FROM user_pro UP, " + "       ewa_user_sta eus, " + "       user_dpt ud, " + "       tam_users tu, "
            + "       (SELECT prof_id, prof_name " + "          FROM prof_v pv, " + "               app_env ae, "
            + "               (SELECT par " + "                  FROM prep_config "
            + "                 WHERE prep_confg_par_typ_cde = 'TENV' "
            + "                   AND SYSDATE BETWEEN stt_dt AND end_dt " + "                   AND del_fl = 'N') pc "
            + "         WHERE pc.par = ae.app_env_cd " + "           AND ae.ver_num = pv.ver_num "
            + "           AND pv.del_fl = 'N' " + "           AND ae.del_fl = 'N') p " + " WHERE usr_id = ? "
            + "   AND eus.user_sta_id = tu.user_sta_id " + "   AND ud.user_dpt_id(+) = UP.user_dpt "
            + "   AND tu.user_id = UP.usr_id " + "   AND tu.prof_id = p.prof_id(+) ";

    public static final String UPDATE_ASCAP_USER_DETAILS = "UPDATE user_pro " + "SET user_dpt = ?, " + "em_adr = ?, "
        + "del_fl = ?, " + "user_acs_per_cde = ?, " + "upd_dt = SYSDATE, " + "upd_id = ? " + "WHERE usr_id = ?";

    public static final String UPDATE_USER_EMAIL =
        "UPDATE  user_pro " + "SET em_adr = ?, " + "upd_dt = SYSDATE, " + "upd_id = ? " + "WHERE usr_id = ?";

    public static final String UPDATE_USER_ACCESS_PERMISSION =
        "UPDATE  user_pro " + "SET user_acs_per_cde = ?, " + "upd_dt = SYSDATE, " + "upd_id = ? " + "WHERE usr_id = ?";

    public static final String UPDATE_TAM_USER_PROFILE_ID = "UPDATE tam_users " + "   SET prof_id = ?, "
        + "       upd_id = ?, " + "       upd_dt = SYSDATE " + " WHERE user_id = ? ";

    public static final String GET_GROUPS_FOR_PROFILE_ID = "		SELECT   prof_id, elm_grp_name, inq_rol_cd "
        + "	    FROM prof_grp_v pgv, " + "	         app_env ae, " + "	         (SELECT par "
        + "	            FROM prep_config " + "	           WHERE prep_confg_par_typ_cde = 'TENV' "
        + "	             AND SYSDATE between stt_dt and end_dt " + "	             AND del_fl = 'N') pc "
        + "	   WHERE pgv.prof_id = ? " + "	     AND pc.par = ae.app_env_cd " + "	     AND ae.ver_num = pgv.ver_num "
        + "	     AND ae.del_fl = 'N' " + "	ORDER BY elm_grp_name ";

    public static final String UPDATE_ASCAP_USER_INQ_FLAGS =
        "UPDATE tam_users " + "SET inq_asg_to_fl = ?, inq_own_fl = ?, " + "upd_dt = SYSDATE " + "WHERE user_id = ?";

    public static final String VERIFY_TAM_USER = "SELECT del_fl FROM tam_users WHERE user_id = ? ";

    public static final String CREATE_CORSP_EMAIL = "INSERT INTO CORSP ";

    public static final String GET_PRIMARY_EMAIL = "SELECT ctc_dtl_id " + "  FROM ctc_dtl "
        + " WHERE ctc_dtl_typ_cde = ? AND del_fl = 'N' AND em_typ_cde = ? AND pty_id = ? ";

    public static final String UPDATE_PRIMARY_EMAIL = "UPDATE ctc_dtl " + " SET em_adr = ? , prm_eml_opt_typ_cde = ?"
        + " WHERE pty_id = ? AND EM_TYP_CDE = ? AND DEL_FL = 'N' ";

    public static final String CTC_DTL_NEXTVAL = "SELECT ctc_dtl_id.nextval from dual ";

    public static final String CORSP_NEXTVAL = "SELECT corsp_id.nextval from dual ";

    public static final String INSERT_EMAIL_INTO_CTC_DTL = "INSERT INTO CTC_DTL "
        + "   (CTC_DTL_ID, PTY_ID, FST_PT_OF_CTC_IND, DEL_FL, CRE_ID, CRE_DT, EM_ADR, CTC_DTL_TYP_CDE, EM_TYP_CDE, PRM_EMl_OPT_TYP_CDE) "
        + " VALUES " + "   (?, ?, 'N', 'N', 'MAACTIVATION', SYSDATE, ?, ?, ?, ?) ";

    public static final String INSERT_EMAIL_INTO_CORSP =
        "INSERT INTO CORSP " + "   (CORSP_ID, CORSP_TYP_CDE, CTC_DTL_ID, DEL_FL, CRE_ID, CRE_DT, PTY_ID) " + " VALUES "
            + "   (?, ?, ?, 'N', 'MAACTIVATION',SYSDATE, ?) ";

    public static final String GET_MA_ACCOUNT_ACCESS_PERMISSION = "SELECT USER_ACS_PER_CDE " + "  FROM USER_PRO "
        + " WHERE DEL_FL <> 'Y' AND PTY_ID = ? AND USR_TYP_CDE = 'EWAM' ";

    public static final String GET_MA_USER_FORGOT_CREDENTIALS = "SELECT COUNT (*) OVER () usr_acct_fnd, "
        + "       (SELECT REPLACE (pkg_prep_security.fnc_descramble (ai.alt_id), " + "                        '-' "
        + "                       ) alt_id " + "          FROM alt_id ai, pty_na pa "
        + "         WHERE ai.del_fl = 'N' " + "           AND pa.del_fl = 'N' "
        + "           AND ai.pty_na_id = pa.pty_na_id " + "           AND pa.pty_id = UP.pty_id "
        + "           AND pa.pty_na_typ_cde = 'PA' " + "           AND ai.alt_typ_cde IN ('SSN', 'TAXID')"
        + "			AND rownum = 1) ssn_tax_id, "
        + "       up.usr_id, up.usr_typ_cde, up.em_adr, msq1.ques_cde ques_cde1, "
        + "       pkg_prep_security.fnc_descramble (msq1.ans) ans1, " + "       msq2.ques_cde ques_cde2, "
        + "       pkg_prep_security.fnc_descramble (msq2.ans) ans2, "
        + "       up.fname, up.lname, up.user_acs_per_cde, tu.user_sta_id "
        + "  FROM user_pro UP, ma_scrt_ques msq1, ma_scrt_ques msq2, tam_users tu " + " WHERE UP.del_fl = 'N' "
        + "   AND msq1.del_fl(+) = 'N' " + "   AND msq2.del_fl(+) = 'N' " + "   AND tu.del_fl(+) = 'N' "
        + "   AND up.usr_id = tu.user_id " + "   AND up.usr_id = msq1.usr_id(+) " + "   AND up.usr_id = msq2.usr_id(+) "
        + "   AND msq1.ques_cde_lst_id(+) = 1 " + "   AND msq2.ques_cde_lst_id(+) = 2 ";

    public static final String GET_TOM_VERSION_NUMBER = "SELECT ver_num " + "  FROM app_env ae, "
        + "       (SELECT par " + "          FROM prep_config " + "         WHERE     prep_confg_par_typ_cde = 'TENV' "
        + "               AND SYSDATE BETWEEN stt_dt AND end_dt " + "               AND del_fl = 'N') pc "
        + " WHERE pc.par = ae.app_env_cd AND ae.del_fl = 'N' AND ROWNUM = 1 ";

    public static final String PROFILE_OBJECT_DETAILS_SEARCH_COUNT = "SELECT COUNT(*)";

    public static final String PROFILE_OBJECT_DETAILS_SEARCH_SELECT_CLAUSE =
        "SELECT rn, mod_id, mod_name, scrn_id, scrn_name, pref, obj_id, obj_name, obj_typ_des ";

    public static final String PROFILE_SCREEN_DETAILS_SEARCH_SELECT_CLAUSE =
        "SELECT rn, mod_id, mod_name, scrn_id, scrn_name, pref ";

    public static final String PROFILE_OBJECT_DETAILS_SEARCH_FROM_CLAUSE =
        "  FROM (SELECT ROWNUM rn, inner.*  " + "          FROM (  SELECT DISTINCT pdv.mod_id,  "
            + "                                  pdv.mod_name,  " + "                                  pdv.scrn_id,  "
            + "                                  pdv.scrn_name,  " + "                                  pdv.pref,  "
            + "                                  pdv.obj_id,  " + "                                  pdv.obj_name,  "
            + "                                  pdv.obj_typ_des  " + "                    FROM prof_det_v pdv  ";

    public static final String PROFILE_SCREEN_DETAILS_SEARCH_FROM_CLAUSE = "  FROM (SELECT ROWNUM rn, inner.*  "
        + "          FROM (  SELECT DISTINCT pdv.mod_id,  " + "                                  pdv.mod_name,  "
        + "                                  pdv.scrn_id,  " + "                                  pdv.scrn_name,  "
        + "                                  pdv.pref  " + "                    FROM prof_det_v pdv  ";

    public static final String PROFILE_OBJECT_DETAILS_SEARCH_WHERE_CLAUSE =
        "                   WHERE pdv.ver_num = ?  ";

    public static final String PROFILE_OBJECT_DETAILS_SEARCH_ORDERBY_CLAUSE =
        "                ORDER BY mod_name, pref) inner) outer  ";

    public static final String PROFILE_OBJECT_DETAILS_SEARCH_OUTER_WHERE_CLAUSE =
        "WHERE outer.rn >= ? AND outer.rn < ?  ";

    public static final String PROFILE_OBJECT_DETAILS_SEARCH_USER_DEPT_CLAUSE =
        "SELECT NULL FROM DUAL UNION SELECT DISTINCT prof_id FROM tam_users tu, user_pro UP WHERE     tu.user_id = UP.usr_id AND tu.prof_id IS NOT NULL AND UP.usr_typ_cde = 'PREP' ";

    public static final String PROFILE_USER_DETAILS_SEARCH_COUNT = "SELECT COUNT(*)";

    public static final String PROFILE_USER_DETAILS_SEARCH_SELECT_CLAUSE =
        "SELECT rn,  usr_id,  usr_na,  user_dpt_na,  prof_name ";

    public static final String PROFILE_USER_DETAILS_SEARCH_FROM_CLAUSE = "    FROM (SELECT ROWNUM rn, inner.* "
        + "          FROM (SELECT up.usr_id, "
        + "                       TRIM (UP.fname) || DECODE (UP.fname, NULL, '', ' ') || TRIM (UP.lname)  usr_na, "
        + "                       ud.user_dpt_na, " + "                       p.prof_name "
        + "                  FROM user_pro UP, " + "                       tam_users tu, "
        + "                       (select prof_id, prof_name, des from prof_v pv where pv.ver_num = ? and pv.del_fl = 'N') p, "
        + "                       user_dpt ud ";

    public static final String PROFILE_USER_DETAILS_SEARCH_WHERE_CLAUSE =
        "                 WHERE     UP.usr_id = tu.user_id " + "                       AND UP.usr_typ_cde = 'PREP' "
            + "                       AND tu.prof_id = p.prof_id "
            + "                       AND UP.user_dpt = ud.user_dpt_id(+) "
            + "                       AND UP.del_fl = 'N' " + "                       AND tu.del_fl = 'N' "
            + "                       AND ud.del_fl(+) = 'N' ";

    public static final String PROFILE_USER_DETAILS_SEARCH_ORDERBY_CLAUSE =
        "                ORDER BY usr_na) inner) outer  ";

    public static final String PROFILE_USER_DETAILS_SEARCH_OUTER_WHERE_CLAUSE =
        "WHERE outer.rn >= ? AND outer.rn < ?  ";

    public static final String PROFILE_USER_DETAILS_SEARCH_USER_PROFILE_CLAUSE =
        "SELECT DISTINCT pdv.prof_id FROM prof_det_v pdv WHERE pdv.ver_num = ? ";

    public static final String GET_SP_PARTY_ID =
        "SELECT r.PNT_PTY_ID SP_PTY_ID FROM rel r WHERE r.child_pty_id = ?   AND r.rel_typ_cde = 'PSUPP' AND r.del_fl <> 'Y'";

}
