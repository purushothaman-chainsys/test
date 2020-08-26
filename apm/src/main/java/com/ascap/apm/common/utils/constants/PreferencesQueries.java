package com.ascap.apm.common.utils.constants;

/**
 * @author Raju_Ayanampudi To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class PreferencesQueries {

    private PreferencesQueries() {

    }

    public static final String DELETE_FLAG_YES = "Y";

    public static final String DELETE_FLAG_NO = "N";

    // Queries for Sequences of table
    public static final String SEQUENCE_PREP_CONFIG_ID = "SELECT PREP_CONFG_ID.NEXTVAL FROM DUAL";

    // Queries for Preferences

    public static final String GET_PREPFERENCES =
        "select USER_ID, DFLT_HOME_MOD, MAX_SRCH_RSL,PG_SRCH_RSL,SORT_ORDER,EMAIL_ADR, nvl(THEME_TYP_CDE, '"
            + PrepConstants.THEME_DEFAULT + "') THEME_TYP_CDE, BG_THEME_FL "
            + " from USER_PREF WHERE UPPER(USER_ID) =? and DEL_FL ='N' ";

    public static final String UPDATE_PREPFERENCES =
        "update  USER_PREF SET DFLT_HOME_MOD = ? ,MAX_SRCH_RSL = ?,PG_SRCH_RSL = ?,SORT_ORDER = ?, EMAIL_ADR = ?, UPD_DT = SYSDATE, THEME_TYP_CDE = ?, BG_THEME_FL = ? "
            + " WHERE USER_ID = ? and DEL_FL ='N' ";

    public static final String CREATE_PREPFERENCES =
        "insert into USER_PREF(USER_ID, DFLT_HOME_MOD, MAX_SRCH_RSL, PG_SRCH_RSL, SORT_ORDER, EMAIL_ADR, CRE_DT, DEL_FL, BG_THEME_FL) "
            + " values(?, ?, ?, ?, ?, ?, SYSDATE, 'N',? )";

    public static final String IS_USER_ID_EXIST_FOR_PREPFERENCES =
        "select USER_ID " + " from USER_PREF WHERE USER_ID = ? and DEL_FL = 'N' ";

}
