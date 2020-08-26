package com.ascap.apm.database.admin;

import java.util.List;

import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.vob.admin.Group;

/**
 * @author Raju_Ayanampudi
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface AdminDAO {
	
	public List<String> getCredentials(Group group) throws PrepSystemException;
	
	public List<Group> retrieveGroups() throws PrepSystemException;
	
}
