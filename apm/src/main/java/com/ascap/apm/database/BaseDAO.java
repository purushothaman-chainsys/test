package com.ascap.apm.database;

import java.sql.SQLException;

import com.ascap.apm.common.helpers.LogHelper;

/**
 * @author Manoj Puli To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class BaseDAO {

    protected LogHelper log = new LogHelper(this.getClass().getName());

    public String retrieveExceptions(SQLException ex) {
        StringBuilder outStrBuff;
        outStrBuff = new StringBuilder("\n--- SQLException caught ---\n");
        while (ex != null) {
            outStrBuff.append("\nMessage:   ").append(ex.getMessage()).append("\nSQLState:  ").append(ex.getSQLState())
                .append("\nErrorCode: ").append(ex.getErrorCode());
            ex = ex.getNextException();
            outStrBuff.append("\n");
        }
        return (outStrBuff.toString());
    }

}
