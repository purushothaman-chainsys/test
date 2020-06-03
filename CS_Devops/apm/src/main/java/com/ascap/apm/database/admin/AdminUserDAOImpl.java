package com.ascap.apm.database.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.common.utils.constants.AdminQueries;
import com.ascap.apm.database.BaseDAO;
import com.ascap.apm.vob.admin.UserLoginHistory;

/**
 * @author Kinshuk_Kale
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
@Repository("adminUserDAO")
public class AdminUserDAOImpl extends BaseDAO implements AdminUserDAO {

	@Autowired
	public JdbcTemplate ascapJdbcTemplate;

	/**
	 * Method getDesignatedUserDetails.
	 * 
	 * @param userProfileSearch
	 * @return UserProfilesSearch
	 * @throws PrepSystemException
	 */
	public UserProfile getDesignatedUserDetails(UserProfile profile) throws PrepSystemException {
		log.debug("Entering getDesignatedUserDetails method in AdminUserDAOImpl " + profile.getUserId());

		UserProfile userProfile = null;
		SqlRowSet rowSet = null;
		try {
			rowSet = ascapJdbcTemplate.queryForRowSet(AdminQueries.GET_DESIGNATED_USER_DETAILS, profile.getUserId());

			while (rowSet.next()) {

				if (userProfile == null) {
					userProfile = new UserProfile();
				}

				userProfile.setUserName(rowSet.getString("USR_ID"));
				userProfile.setUserId(rowSet.getString("USR_ID"));
				userProfile.setFirstName(rowSet.getString("FNAME"));
				userProfile.setLastName(rowSet.getString("LNAME"));
				userProfile.setEmailId(rowSet.getString("EM_ADR"));
				userProfile.setUserType(rowSet.getString("USR_TYP_CDE"));
				userProfile.setUserLoginStatusId(rowSet.getString("USER_STA_ID"));
				userProfile.setUserAccessPermissionCode(rowSet.getString("USER_ACS_PER_CDE"));
				userProfile.setActivationSource(rowSet.getString("ACT_SRC"));
				userProfile.setDepartmentId(rowSet.getString("USER_DPT"));

			}

			if (userProfile != null && (this.getSuccessfulLoginCount(profile.getUserId()) == 0)) {
				userProfile.setFirstTimeLoginInd("Y");
			}

		} catch (Exception se) {
			log.debug("SQLException : " + se);
			throw new PrepSystemException("error.inquiry.sql.sqlexception", se);
		}

		log.debug("Exiting getDesignatedUserDetails method in AdminUserDAOImpl UserProfile" + userProfile);

		return userProfile;
	}

	/**
	 * Method getSuccessfulLoginCount.
	 * 
	 * @param username
	 * @return int
	 * @throws PrepSystemException
	 */
	private int getSuccessfulLoginCount(String userName) throws PrepSystemException {
		log.debug("Entering getSuccessfulLoginCount method in AdminUserDAOImpl");
		int count = 0;

		try {
			count = ascapJdbcTemplate.queryForObject(AdminQueries.GET_USER_SUCCESSFUL_LOGIN_CNT,
					new Object[] { userName }, Integer.class);
		} catch (Exception se) {
			log.debug("SQLException : " + se);
			throw new PrepSystemException("error.inquiry.sql.sqlexception", se);

		}
		log.debug("Exiting getSuccessfulLoginCount method in AdminUserDAOImpl");
		return count;
	}

	/**
	 * Method insertLoginHistory.
	 * 
	 * @param attempt
	 * @throws PrepSystemException
	 */
	public UserLoginHistory insertLoginHistory(UserLoginHistory attempt) throws PrepSystemException {
		log.debug("Entering insertLoginHistory method in AdminUserDAOImpl");

		boolean userFound = false;
		String userName = null;

		String loginHistoryId = null;
		SqlRowSet rowSet = null;

		if (attempt == null) {
			log.error("Should Never Happen attempt vob in AdminUserDAOImpl.insertLoginHistory() is NULL ");
			return attempt;
		}

		try {
			log.debug("Inside AdminUserDAOImpl.insertLoginHistory() 1 getUserName() - " + attempt.getUserName());

			rowSet = ascapJdbcTemplate.queryForRowSet(AdminQueries.GET_USER, attempt.getUserName());

			if (rowSet.next()) {
				userFound = true;
				userName = attempt.getUserName().toLowerCase();
			}

			log.debug("Inside AdminUserDAOImpl.insertLoginHistory() 2 getUserName() - " + attempt.getUserName());

			if (userFound) {
				loginHistoryId = this.getSequence(AdminQueries.GET_USER_LOGIN_HST_SEQ);

				ascapJdbcTemplate.update(AdminQueries.INSERT_LOGIN_ATTEMPT_RECORD, loginHistoryId, userName,
						attempt.getAttemptCode(), attempt.getSessionStatusCode(), attempt.getSessionKey(),
						attempt.getAccessTypeCode(), attempt.getUserAgent(), attempt.getHistoryTypeCode());
				attempt.setUserLoginHistoryId(loginHistoryId);
			} else {
				log.error(
						"Inside AdminUserDAOImpl.insertLoginHistory() is user is NOT FOUND :" + attempt.getUserName());
			}

		} catch (Exception se) {
			log.debug("Exception : " + se);
			throw new PrepSystemException("error.inquiry.sql.sqlexception", se);

		}

		log.debug("Exiting insertLoginHistory method in AdminUserDAOImpl");

		return attempt;
	}

	private String getSequence(String query) throws PrepSystemException {
		String outSequenceNo = null;
		SqlRowSet rowSet = null;
		try {
			rowSet = ascapJdbcTemplate.queryForRowSet(query);

			while (rowSet.next()) {
				outSequenceNo = rowSet.getString(1);
			}

		} catch (Exception se) {
			log.debug(se);
			throw new PrepSystemException("error.admin.sql.sqlexception", se);

		}
		return (outSequenceNo);
	}

	public List<Serializable> retrieveUserGroups(String userId) throws PrepSystemException {

		log.debug("Entering retrieveUserGroups method in AdminUserDAOImpl");
		List<Serializable> groups = null;
		if (userId == null) {
			log.debug("Input User Id is null");
			return groups;
		}
		groups = new ArrayList<>();
		SqlRowSet rowSet = null;
		String retrieveQuery = AdminQueries.RETRIEVE_USER_GROUPS_QRY;

		log.debug("Retrieve Query: " + retrieveQuery + "  " + userId);
		try {
			rowSet = ascapJdbcTemplate.queryForRowSet(retrieveQuery, userId);
			while (rowSet.next()) {
				groups.add(rowSet.getString("FGA_GRP_CDE"));
			}
		} catch (Exception se) {
			log.debug(se);
			throw new PrepSystemException("admin.error.common.sql.sqlexception", se);

		}

		log.debug("Exiting retrieveUserGroups method in AdminUserDAOImpl");
		return groups;
	}

	/**
	 * Method updateLoginHistory.
	 * 
	 * @param attempt
	 * @throws PrepSystemException
	 */
    public UserLoginHistory updateLoginHistory(UserLoginHistory attempt) throws PrepSystemException {
        log.debug("Entering updateLoginHistory method in AdminUserDAO");

        if (attempt == null) {
            log.error("Should Never Happen attempt vob in AdminUserDAO.updateLoginHistory() is NULL ");
            return attempt;
        }
        if (!ValidationUtils.isValidLong(attempt.getUserLoginHistoryId())) {
            log.error(
                "Should Never Happen attempt vob in AdminUserDAO.updateLoginHistory().getUserLoginHistoryId() is NOT VALID LONG ");
            return attempt;
        }
        try {
            ascapJdbcTemplate.update(AdminQueries.UPDATE_LOGIN_HISTORY_RECORD, attempt.getSessionStatusCode(),
                attempt.getUserLoginHistoryId());
        } catch (DataAccessException se) {
            log.debug("Exception : " + se);
            throw new PrepSystemException("error.inquiry.sql.sqlexception", se);
        } catch (NullPointerException ne) {
            log.debug("NullpointerException : " + ne);
            throw new PrepSystemException("error.inquiry.common.NullpointerException", ne);
        } catch (NumberFormatException nf) {
            log.debug("NumberFormatException : " + nf);
            throw new PrepSystemException("error.inquiry.common.NumberFormatException", nf);
        }
        log.debug("Exiting updateLoginHistory method in AdminUserDAO");
        return attempt;
    }
}
