package com.ascap.apm.servicehelpers.logon;

import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;

public interface LogonHelper {

    public UserProfile authenticateUser(UserProfile userProfile) throws PrepSystemException, PrepFunctionalException;

    public UserProfile logout(UserProfile userProfile) throws PrepSystemException, PrepFunctionalException;
}
