package com.ascap.apm.handler.preferences;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.handler.BaseHandler;
import com.ascap.apm.service.preferences.PreferencesServicesImpl;

@Service("preferenceHandler")
@Transactional(value = "ascapTxManager")
public class PreferencesHandlerImpl extends BaseHandler implements PreferencesHandler {

    /**
     * 
     */
    private static final long serialVersionUID = 5795933366286638203L;
    @Autowired
    private PreferencesServicesImpl preferencesServices;

    /**
     * Method submitPreferences.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    public PREPContext submitPreferences(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering PreferencesServicesImpl - submitPreferences method");
        PREPContext outputContext = new PREPContext();
        UserPreference preferences = null;
        try {
            List<Object> inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                preferences = (UserPreference) inputValueObjects.iterator().next();
            }
            preferences = preferencesServices.submitPreferences(preferences);

            outputContext.addOutputValueObject(preferences);
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("ps.error.common.exception");
        }
        if (log.isDebugEnabled()) {
            log.debug("Exiting PreferencesServicesImpl - submitPreferences method");
        }
        return outputContext;
    }

}
