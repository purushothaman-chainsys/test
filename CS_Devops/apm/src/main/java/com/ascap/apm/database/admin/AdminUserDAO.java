package com.ascap.apm.database.admin;

import java.io.Serializable;
import java.util.List;

import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.vob.admin.UserLoginHistory;

/**
 * @author Kinshuk_Kale
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to 
 * Window>Preferences>Java>Code Generation.
 */

public interface AdminUserDAO {

	public UserProfile getDesignatedUserDetails(UserProfile profile) throws PrepSystemException;
	
	public UserLoginHistory insertLoginHistory(UserLoginHistory attempt) throws PrepSystemException;
	
	public List<Serializable> retrieveUserGroups(String userId) throws PrepSystemException;
	
	public UserLoginHistory updateLoginHistory(UserLoginHistory attempt) throws PrepSystemException;
}
