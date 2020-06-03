package com.ascap.apm.service.preferences;

import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;

public interface PreferencesServices {

    public UserPreference submitPreferences(UserPreference preferences)
        throws PrepSystemException, PrepFunctionalException;

}
