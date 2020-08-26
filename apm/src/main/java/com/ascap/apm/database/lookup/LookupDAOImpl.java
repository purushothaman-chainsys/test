package com.ascap.apm.database.lookup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.exception.database.lookup.LookupTableLoadException;
import com.ascap.apm.common.utils.cache.lookup.LookupTableCache;
import com.ascap.apm.common.utils.constants.Constants;
import com.ascap.apm.database.BaseDAO;

/**
 * @author Sudhar_Krishnamachary DAO Helper class used by the LookupTableHelper to access the database to read the
 *         lookup tables
 */
@Repository
public class LookupDAOImpl extends BaseDAO implements LookupDAO {

    @Autowired
    public JdbcTemplate ascapJdbcTemplate;

    /**
     * Constructor for LookupDAOImpl.
     */
    public LookupDAOImpl() {
        super();
    }

    /**
     * Method getLookupTableRecords. given the tablename and column names it reads the data and return in the records in
     * a hashtable, the keyvalue being the primary key column and description is value
     * 
     * @param tableName
     * @param primaryKeyColumn
     * @param descriptionColumn
     * @return Hashtable
     */
    @Transactional(value = "ascapTxManager", readOnly = true)
    public LookupTableCache getLookupTableRecords(String keyName, String sqlQeury) throws LookupTableLoadException {
        LookupTableCache lookupTableCache = new LookupTableCache();
        SqlRowSet sqlRowSet = null;
        try {
            sqlRowSet = ascapJdbcTemplate.queryForRowSet(sqlQeury);
            while (sqlRowSet.next()) {
                if (sqlRowSet.getString(1) != null && sqlRowSet.getString(2) != null) {
                    lookupTableCache.add(sqlRowSet.getString(1), sqlRowSet.getString(2));
                }
            }

            log.debug("getLookupTableRecords lookupTablRecords = " + lookupTableCache.toString());
            return lookupTableCache;
        } catch (Exception e) {
            // Escalate this to higher layer
            String[] errorStrings = new String[2];
            errorStrings[0] = keyName;
            errorStrings[1] = sqlQeury;
            throw new LookupTableLoadException("error.database.lookup.loaderror", errorStrings, e);
        }
    }

    @Transactional(value = "ascapTxManager", readOnly = true)
    public String getSingleOutputValue(String valueType) throws PrepSystemException {
        SqlRowSet sqlRowSet = null;
        String sqlQeury = null;
        try {
            if (Constants.SINGLE_OUTPUT_VALUE_TYPE_TARGET_DIS.equals(valueType)) {
                sqlQeury = Constants.SINGLE_OUTPUT_QUERY_TARGET_DIS;
            }
            if (sqlQeury == null) {
                return null;
            }
            StringBuilder sqlStatementBuffer = new StringBuilder(sqlQeury);
            sqlRowSet = ascapJdbcTemplate.queryForRowSet(sqlStatementBuffer.toString());
            String outputString = null;
            while (sqlRowSet.next()) {
                outputString = sqlRowSet.getString(1);
            }
            log.debug("getSingleOutputValue singleOutputValue = " + outputString);
            return outputString;
        } catch (Exception e) {
            throw new PrepSystemException("error.database.lookup.loaderror");
        }
    }
}
