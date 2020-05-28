package com.ascap.apm.service.preferences;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.service.BaseService;
import com.ascap.apm.servicehelpers.preferences.PreferencesHelper;

@Service("preferencesServices")
public class PreferencesServicesImpl extends BaseService implements PreferencesServices {

    @Autowired
    private PreferencesHelper preferencesHelper;

    /**
     * Method submitPreferences.
     * @param preferences
     * @return Preferences
     * @throws PrepSystemException
     */
    public UserPreference submitPreferences(UserPreference preferences)
        throws PrepSystemException, PrepFunctionalException {
        if (log.isDebugEnabled()) {
            log.debug("Entering PreferencesServicesImpl - submitPreferences method");
        }
        preferences = preferencesHelper.submitPreferences(preferences);
        if (log.isDebugEnabled()) {
            log.debug("Exiting PreferencesServicesImpl - submitPreferences method");
        }
        return preferences;
    }
}
