package com.ascap.apm.handler.logon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.cache.CredentialsHelper;
import com.ascap.apm.common.utils.cache.lookup.LookupTablesHelper;
import com.ascap.apm.handler.BaseHandler;
import com.ascap.apm.service.logon.LogonService;

/**
 * Session Bean implementation class LogonHandlerImpl
 */
@Service("logonHandler")
@Transactional(value = "ascapTxManager")
public class LogonHandlerImpl extends BaseHandler implements LogonHandler {

    private static final long serialVersionUID = 6650082811586355031L;

    @Autowired
    private LogonService logonService;

    /**
     * Method authenticateLogon.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Override
    public PREPContext authenticateLogon(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering LogonHandlerImpl - authenticateLogon method");

        PREPContext outputContext = null;
        try {
            outputContext = new PREPContext();
            List<Object> inputValueObjects = inputContext.getInputValueObjects();
            UserProfile userProfileOutput = null;
            UserProfile userProfile = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                userProfile = (UserProfile) inputValueObjects.iterator().next();
            }

            userProfileOutput = logonService.authenticateLogon(userProfile);
            if (userProfileOutput != null) {
                log.debug("***Authentication success ***");

            }
            outputContext.addOutputValueObject(userProfileOutput);
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException dae) {
            log.warn(dae.getMessage());
            throw dae;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.common.exception");
        }

        log.debug("Exiting LogonHandlerImpl - authenticateLogon method");
        return outputContext;
    }

    /*
     * (non-Javadoc)
     * @see com.ascap.apm.controller.logon.LogonControllerLocalInterface#loadOnStartup(
     * com.ascap.apm.common.context.PREPContext)
     */
    @Override
    public PREPContext loadOnStartup(PREPContext inputContext) throws PrepSystemException {
        log.debug("Entering LogonHandlerImpl - loadOnStartup method");
        boolean securityEnabled = false;
        List<Object> inputValueObjects = inputContext.getInputValueObjects();
        if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
            securityEnabled = (Boolean) inputValueObjects.iterator().next();
        }
        // loads the Credentials singleton class
        if (securityEnabled) {
            CredentialsHelper.getInstance();
        }
        // loads the Lookup tables singleton class
        LookupTablesHelper.getInstance();
        log.debug("Exiting LogonHandlerImpl - loadOnStartup method");
        return null;
    }

    /**
     * Method logout.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Override
    public PREPContext logout(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering LogonHandlerImpl - logout method");
        PREPContext outputContext = null;
        try {
            outputContext = new PREPContext();
            List<Object> inputValueObjects = inputContext.getInputValueObjects();
            UserProfile userProfileOutput = null;
            UserProfile userProfile = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                userProfile = (UserProfile) inputValueObjects.iterator().next();
            }
            userProfileOutput = logonService.logout(userProfile);
            outputContext.addOutputValueObject(userProfileOutput);
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException dae) {
            log.warn(dae.getMessage());
            throw dae;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.common.exception");
        }
        log.debug("Exiting LogonHandlerImpl - logout method");
        return outputContext;
    }

}
