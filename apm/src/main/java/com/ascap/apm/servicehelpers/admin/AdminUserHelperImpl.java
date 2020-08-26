package com.ascap.apm.servicehelpers.admin;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.MessageProperties;
import com.ascap.apm.database.admin.AdminUserDAO;
import com.ascap.apm.servicehelpers.BaseHelper;

/**
 * @author Raju_Ayanampudi To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
@Service("adminUserHelper")
@Transactional(value = "ascapTxManager")
public class AdminUserHelperImpl extends BaseHelper implements AdminUserHelper {

    private static final long serialVersionUID = 5320750099169551073L;

    protected MessageProperties messageProps = MessageProperties.getInstance("ErrorMessages");

    @Autowired
    private AdminUserDAO adminUserDAO;

    /**
     * Method insertLoginHistory.
     * 
     * @param userProfile
     * @return UserProfile
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    public UserProfile insertLoginHistory(UserProfile userProfile) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering insertLoginHistory method in AdminUserHelperImpl");
        if (userProfile == null) {
            log.error("Invalid argument to AdminUserHelperImpl.insertLoginHistory() userProfile passed is NULL ");
            return userProfile;
        }
        adminUserDAO.insertLoginHistory(userProfile.getLoginSessionInfo());
        log.debug("exiting insertLoginHistory method in AdminUserHelperImpl");
        return userProfile;
    }

    public List<Serializable> retrieveUserGroups(String userId) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering retrieveUserGroups in AdminUserHelperImpl, userId " + userId);
        List<Serializable> groups = adminUserDAO.retrieveUserGroups(userId);
        log.debug("Exiting retrieveUserGroups in AdminUserHelperImpl");
        return groups;
    }
}
