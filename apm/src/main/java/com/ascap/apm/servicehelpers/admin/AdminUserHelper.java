package com.ascap.apm.servicehelpers.admin;

import java.io.Serializable;
import java.util.List;

import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;

public interface AdminUserHelper {

	public UserProfile insertLoginHistory(UserProfile userProfile) throws  PrepSystemException, PrepFunctionalException;
    
	public List<Serializable> retrieveUserGroups(String userId) throws  PrepSystemException, PrepFunctionalException;
    
}
