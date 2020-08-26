package com.ascap.apm.service.logon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.service.BaseService;
import com.ascap.apm.servicehelpers.logon.LogonHelper;

/**
 * Session Bean implementation class LogonServiceImpl
 */
@Service("logonService")
public class LogonServiceImpl extends BaseService implements LogonService {

    private static final long serialVersionUID = 5101185810352652031L;

    @Autowired
    private LogonHelper logonHelper;

    /**
     * Method authenticateLogon.
     * 
     * @param userProfile
     * @return UserProfile
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Override
    public UserProfile authenticateLogon(UserProfile userProfile) throws PrepSystemException, PrepFunctionalException {
        UserProfile userProfileOutput = null;
        try {
            log.debug("Entering authenticateLogon method in LogonServiceImpl");

            userProfileOutput = logonHelper.authenticateUser(userProfile);

            log.debug("Exiting authenticateLogon method in LogonServiceImpl");
        } catch (PrepSystemException | PrepFunctionalException e) {
            log.error(e.getMessage());
        }

        return userProfileOutput;
    }

    /**
     * Method logout.
     * 
     * @param userProfile
     * @return UserProfile
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Override
    public UserProfile logout(UserProfile userProfile) throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering logout method in LogonServiceImpl");
        UserProfile userProfileOutput = logonHelper.logout(userProfile);

        log.debug("Exiting logout method in LogonServiceImpl");
        return userProfileOutput;
    }

}
