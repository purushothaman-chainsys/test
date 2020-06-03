package com.ascap.apm.servicehelpers.logon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.exception.logon.LogonException;
import com.ascap.apm.common.utils.constants.AdminConstants;
import com.ascap.apm.database.admin.AdminUserDAO;
import com.ascap.apm.database.preferences.PreferencesDAO;
import com.ascap.apm.servicehelpers.BaseHelper;
import com.ascap.apm.vob.admin.UserLoginHistory;

/**
 * @author Raju_Ayanampudi To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
@Service("logonHelper")
@Transactional(value = "ascapTxManager")
public class LogonHelperImpl extends BaseHelper implements LogonHelper {

    private static final long serialVersionUID = -7833015147522940694L;

    private static final String ERROR_LOGON_LOGONFAILEDEXCEPTION = "ap.error.logon.logonfailedexception";

    private static final String EXCEPTION = "Exception :";

    @Autowired
    private AdminUserDAO adminUserDAO;

    @Autowired
    private PreferencesDAO preferencesDAO;

    @Override
    public UserProfile authenticateUser(UserProfile userProfile) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering authenticateUser method in LogonHelperImpl with User Id " + userProfile.getUserId());

        UserProfile userProfileOutput = null;
        String userName = userProfile.getUserId();

        try {
            userProfileOutput = adminUserDAO.getDesignatedUserDetails(userProfile);

            userProfileOutput.setIsSecurityEnabled(userProfile.isSecurityEnabled());

            UserPreference userPreference = new UserPreference();
            userPreference.setUserId(userProfileOutput.getUserId());

            userProfileOutput.setUserPreference(preferencesDAO.getPreferences(userPreference));

            userProfileOutput.setGroups(adminUserDAO.retrieveUserGroups(userProfile.getUserId()));

            // Logs the login history into USER_LOGIN_HST table.
            UserLoginHistory attempt = null;
            attempt = userProfile.getLoginSessionInfo();

            if (attempt == null) {
                attempt = new UserLoginHistory();
            }

            attempt.setAccessTypeCode(AdminConstants.LOGIN_HST_SESSION_ACCESS_TYPE_READ_WRITE);
            attempt.setSessionStatusCode(AdminConstants.LOGIN_HST_SESSION_STATUS_ACTIVE);
            attempt.setHistoryTypeCode(AdminConstants.LOGIN_HISTORY_TYPE_CODE_LOGIN);

            attempt.setUserName(userName);
            attempt.setAttemptCode(AdminConstants.LOGIN_ATTEMPT_SUCCESSFUL);
            adminUserDAO.insertLoginHistory(attempt);
            userProfileOutput.setLoginSessionInfo(attempt);

        } catch (Exception e) {
            log.debug(EXCEPTION + e);
            throw new LogonException(ERROR_LOGON_LOGONFAILEDEXCEPTION);
        }

        log.debug("Exiting authenticateUser method in LogonHelperImpl with User Id " + userProfileOutput.getUserId()
            + " of type " + userProfileOutput.getUserType());

        return userProfileOutput;
    }

    @Override
    public UserProfile logout(UserProfile userProfile) throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering logout method in LogonHelperImpl ");

        UserLoginHistory attempt = null;

        if (userProfile == null) {
            return userProfile;
        }

        attempt = userProfile.getLoginSessionInfo();

        if (attempt == null) {
            return userProfile;
        }

        log.debug("Entering logout method in LogonHelperImpl with User Id " + userProfile.getUserId());

        String userName = userProfile.getUserId();
        try {
            // Logs the login history into USER_LOGIN_HST table.
            attempt.setSessionStatusCode(AdminConstants.LOGIN_HST_SESSION_STATUS_LOGGEDOUT);

            attempt.setUserName(userName);
            adminUserDAO.updateLoginHistory(attempt);

            // end of has to be removed later
        } catch (Exception e) {
            log.debug(EXCEPTION + e);
            throw new LogonException(ERROR_LOGON_LOGONFAILEDEXCEPTION);
        }

        log.debug("Exiting logout method in LogonHelperImpl with User Id " + userProfile.getUserId() + " of type "
            + userProfile.getUserType());

        return userProfile;
    }
}
