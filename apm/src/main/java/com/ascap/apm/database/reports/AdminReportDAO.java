package com.ascap.apm.database.reports;

import java.util.List;

import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.vob.reports.AdminReport;

/**
 * @author vzayyadevara To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public interface AdminReportDAO {

    /**
     * Method updateReportAdminTables.
     * 
     * @param adminreportSearchVOB
     */
    public List<Object> adminUpdateAllTables(List<Object> ceList, String userID) throws PrepSystemException;

    /**
     * Method adminGetDefaultValues.
     * 
     * @param adminReport
     * @return AdminReport
     * @throws PrepSystemException
     */
    public AdminReport adminGetDefaultValues(AdminReport adminReport) throws PrepSystemException;

    /**
     * Method adminUpdateInputValues.
     * 
     * @param adminReport
     * @return AdminReport
     * @throws PrepSystemException
     */
    public AdminReport adminUpdateInputValues(AdminReport adminReport) throws PrepSystemException;

}
