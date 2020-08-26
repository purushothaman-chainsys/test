package com.ascap.apm.servicehelpers.preferences;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.database.preferences.PreferencesDAO;
import com.ascap.apm.servicehelpers.BaseHelper;

@Service("preferencesHelper")
public class PreferencesHelperImpl extends BaseHelper implements PreferencesHelper {

    /**
     * 
     */
    private static final long serialVersionUID = -6277425802973067068L;
    @Autowired
    private PreferencesDAO preferencesDAO;

    /**
     * Method submitPreferences.
     * 
     * @param preferences
     * @return UserPreference
     * @throws PrepSystemException
     */
    public UserPreference submitPreferences(UserPreference preferences)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering PreferencesHelperImpl - submitPreferences method");
        preferences = preferencesDAO.submitPreferences(preferences);
        log.debug("Exiting PreferencesHelperImpl - submitPreferences method");
        return preferences;
    }

}
