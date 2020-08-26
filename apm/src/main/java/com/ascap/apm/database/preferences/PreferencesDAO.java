package com.ascap.apm.database.preferences;

import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepSystemException;

/**
 * @author Vishesh_Satyam To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public interface PreferencesDAO {

    public UserPreference getPreferences(UserPreference preferences) throws PrepSystemException;

    public UserPreference submitPreferences(UserPreference preferences) throws PrepSystemException;

}
