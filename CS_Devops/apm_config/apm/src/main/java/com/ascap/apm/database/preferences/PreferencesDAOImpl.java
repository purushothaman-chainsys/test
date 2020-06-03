package com.ascap.apm.database.preferences;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.helpers.PrepExtPropertyHelper;
import com.ascap.apm.common.utils.constants.PreferencesQueries;
import com.ascap.apm.common.utils.constants.PrepConstants;
import com.ascap.apm.common.utils.constants.PrepPropertiesConstants;
import com.ascap.apm.database.BaseDAO;

/**
 * @author Vishesh_Satyam To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
@Repository("preferencesDAO")
public class PreferencesDAOImpl extends BaseDAO implements PreferencesDAO {

    @Autowired
    public JdbcTemplate ascapJdbcTemplate;

    /**
     * Method getPreferences.
     * 
     * @param preferences
     * @return UserPreference
     * @throws PrepSystemException
     */
    public UserPreference getPreferences(UserPreference preferences) throws PrepSystemException {
        log.debug("Entering PreferencesDAOImpl - getPreferences method" + preferences.getUserId());
        SqlRowSet rowSet = null;
        String userId = preferences.getUserId();
        UserPreference preferencesOutput = new UserPreference();
        preferencesOutput.setUserId(userId);

        try {
            rowSet = ascapJdbcTemplate.queryForRowSet(PreferencesQueries.GET_PREPFERENCES,
                new Object[] {userId.toUpperCase()});

            log.debug("Get Preferences Query with UserId - " + userId + " : " + PreferencesQueries.GET_PREPFERENCES);

            if (rowSet.next()) {
                preferencesOutput.setUserId(rowSet.getString("USER_ID"));
                preferencesOutput.setDefaultPage(rowSet.getString("DFLT_HOME_MOD"));
                preferencesOutput.setMaxSearchResults(rowSet.getInt("MAX_SRCH_RSL"));
                preferencesOutput.setNofSrchRsltsPerPage(rowSet.getInt("PG_SRCH_RSL"));
                preferencesOutput.setSortOrder(rowSet.getString("SORT_ORDER"));
                preferencesOutput.setEmailReminder(rowSet.getString("EMAIL_ADR"));
                preferencesOutput.setThemeTypeCode(rowSet.getString("THEME_TYP_CDE"));
                preferencesOutput.setBackgroundThemeFlag(rowSet.getString("BG_THEME_FL"));
            } else {
                preferencesOutput.setUserId(userId);
                preferencesOutput.setMaxSearchResults(Integer.parseInt(PrepExtPropertyHelper.getInstance()
                    .getPropertyValue(PrepPropertiesConstants.MAX_SEARCHRESULTS_THRESHOLD)));
                preferencesOutput.setNofSrchRsltsPerPage(Integer.parseInt(PrepExtPropertyHelper.getInstance()
                    .getPropertyValue(PrepPropertiesConstants.DEFAULT_NUMBER_OF_RESULTS_PER_PAGE)));
                preferencesOutput.setThemeTypeCode(PrepConstants.THEME_DEFAULT);
                preferencesOutput.setBackgroundThemeFlag(PrepConstants.THEME_BG_FLAG);
            }

        } catch (Exception se) {
            log.debug(se);
            throw new PrepSystemException("error.distribution.sql.sqlexception", se);

        }
        log.debug("Exiting PreferencesDAOImpl - getPreferences method");
        return preferencesOutput;
    }

    /**
     * Method submitPreferences.
     * 
     * @param preferences
     * @return UserPreference
     * @throws PrepSystemException
     */
    public UserPreference submitPreferences(UserPreference preferences) throws PrepSystemException {
        log.debug("Entering  PreferencesDAOImpl  - submitPreferences method");
        String userId = preferences.getUserId();
        boolean valid = true;
        int insertCount = 0;
        try {
            // First check whether that id exist or not
            log.debug("User Id Exists Query: " + PreferencesQueries.IS_USER_ID_EXIST_FOR_PREPFERENCES + " " + userId);

            SqlRowSet rs = ascapJdbcTemplate.queryForRowSet(PreferencesQueries.IS_USER_ID_EXIST_FOR_PREPFERENCES,
                new Object[] {userId});
            while (rs.next()) {
                valid = false;
            }
            List<Object> params = new ArrayList<>();

            if (valid) {
                log.debug("Create Preferences Query: " + PreferencesQueries.CREATE_PREPFERENCES + " " + userId);

                params.add(preferences.getUserId());
                params.add(preferences.getDefaultPage());
                params.add(preferences.getMaxSearchResults());
                params.add(preferences.getNofSrchRsltsPerPage());
                params.add(preferences.getSortOrder());
                params.add(preferences.getEmailReminder());
                params.add(preferences.getBackgroundThemeFlag());
                insertCount = ascapJdbcTemplate.update(PreferencesQueries.CREATE_PREPFERENCES, params.toArray());

            } else {
                log.debug("Update Preferences Query: " + PreferencesQueries.UPDATE_PREPFERENCES + ": " + userId
                    + ", backgound theme flag " + preferences.getBackgroundThemeFlag());

                params.add(preferences.getDefaultPage());
                params.add(preferences.getMaxSearchResults());
                params.add(preferences.getNofSrchRsltsPerPage());
                params.add(preferences.getSortOrder());
                params.add(preferences.getEmailReminder());
                params.add(preferences.getThemeTypeCode());
                params.add(preferences.getBackgroundThemeFlag());
                params.add(preferences.getUserId());
                insertCount = ascapJdbcTemplate.update(PreferencesQueries.UPDATE_PREPFERENCES, params.toArray());
            }

        } catch (Exception se) {
            log.error(se.getMessage());
            log.debug(se.getMessage());

            throw new PrepSystemException("error.distribution.sql.sqlexception", se);
        }

        log.debug("Exiting PreferencesDAOImpl - submitPreferences method, updatecount: " + insertCount);
        return preferences;
    }

}
