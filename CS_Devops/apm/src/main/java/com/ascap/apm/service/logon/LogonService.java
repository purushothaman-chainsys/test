package com.ascap.apm.service.logon;
import java.rmi.RemoteException;

import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;

public interface LogonService {
	
	/**
	 * Method authenticateLogon.
	 * @param userProfile
	 * @return UserProfile
	 * @throws RemoteException
	 * @throws PrepSystemException
	 * @throws PrepFunctionalException
	 */
    public UserProfile authenticateLogon(UserProfile userProfile)
        throws PrepSystemException, PrepFunctionalException;

	
	/**
	 * Method logout.
	 * @param userProfile
	 * @return UserProfile
	 * @throws RemoteException
	 * @throws PrepSystemException
	 * @throws PrepFunctionalException
	 */
	public UserProfile logout(UserProfile userProfile)
        throws  PrepSystemException, PrepFunctionalException;
	
}
