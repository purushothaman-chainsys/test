package com.ascap.apm.database.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.constants.AdminQueries;
import com.ascap.apm.database.BaseDAO;
import com.ascap.apm.vob.admin.Group;

/**
 * @author Raju_Ayanampudi DAO that handles all the DataBase operations on Distribution Lookup tables
 */
@Repository("adminDAO")
public class AdminDAOImpl extends BaseDAO implements AdminDAO {

    @Autowired
    public JdbcTemplate ascapJdbcTemplate;

    @Transactional(value = "ascapTxManager", readOnly = true)
    public List<String> getCredentials(Group group) throws PrepSystemException {

        log.debug("Entering getCredentials method in AdminDAOImpl");

        List<String> credentials = new ArrayList<>();
        SqlRowSet rowSet = null;
        try {

            log.debug("Query GET_GROUP_CREDENTIALS: " + AdminQueries.GET_GROUP_CREDENTIALS);

            rowSet = ascapJdbcTemplate.queryForRowSet(AdminQueries.GET_GROUP_CREDENTIALS, group.getGroupCode(),
                group.getGroupCode(), group.getGroupCode());
            while (rowSet.next()) {
                credentials.add(rowSet.getString("FGA_OBJ_ID"));
            }

        } catch (Exception se) {
            log.debug(se);
            throw new PrepSystemException("admin.error.common.sql.sqlexception", se);

        }
        log.debug("Exiting getCredentials method in AdminDAOImpl");
        return credentials;
    }

    @Transactional(value = "ascapTxManager", readOnly = true)
    public List<Group> retrieveGroups() throws PrepSystemException {

        log.debug("Entering retrieveGroups method in AdminDAOImpl");

        SqlRowSet rowSet = null;
        List<Group> groups = new ArrayList<>();
        String searchQuery = AdminQueries.RETRIEVE_GROUPS;

        try {
            log.debug("Search Query " + searchQuery);
            rowSet = ascapJdbcTemplate.queryForRowSet(searchQuery);
            while (rowSet.next()) {
                Group group = new Group();
                group.setGroupCode(rowSet.getString("FGA_GRP_CDE"));
                groups.add(group);
            }
        } catch (Exception se) {
            log.debug(se);
            throw new PrepSystemException("admin.error.common.sql.sqlexception", se);

        }
        log.debug("Exiting retrieveGroups method in AdminDAOImpl");
        return groups;
    }

}
