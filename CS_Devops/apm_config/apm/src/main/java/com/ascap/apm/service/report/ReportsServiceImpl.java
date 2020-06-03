package com.ascap.apm.service.report;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.service.BaseService;
import com.ascap.apm.vob.reports.Report;
import com.ascap.apm.vob.reports.ReportListSearch;
import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;

/**
 * ReportService.java interacts with Crystal Enterprise server to exceute the requested serives
 * 
 * @author Murali Bamidi (mzbamidi@kanbay.com) Revision History:
 *         ------------------------------------------------------------------------ Modified by Comments Date/Time
 *         ------------------------------------------------------------------------ Raja Rao Modified 01-09-2006
 *         ------------------------------------------------------------------------
 */

@Service("reportsService")
public class ReportsServiceImpl extends BaseService implements ReportsService {

    private static final long serialVersionUID = 8525690557385216031L;

    public IInfoStore getIstore() throws Exception {
        if (log.isDebugEnabled())
            log.debug("Start of getIstore()");

        IInfoStore iStore = null;
        IEnterpriseSession eSession = null;

        try {
            if (log.isDebugEnabled())
                log.debug("getIstore()-Before session");
            eSession = Connection.getEnterPriseSession();
            if (eSession != null) {
                iStore = (IInfoStore) eSession.getService("", "InfoStore");
                if (log.isDebugEnabled())
                    log.debug("getIstore()-after session");
            }

        } catch (SDKException ske) {
            log.error("getIstore()-In SDK Exception");
            try {
                eSession = Connection.forceGetEnterpriseSession();
                if (eSession != null) {
                    iStore = (IInfoStore) eSession.getService("", "InfoStore");
                }
            } catch (Exception se) {
                log.error("getIstore()-Inside Exception block in SDKException");
                log.error(se.getMessage());
                String errorStrings[] = new String[1];
                errorStrings[0] = se.getMessage();
                throw new PrepSystemException("error.report.common.infostore.exception", errorStrings);

            }

        } catch (Exception e) {
            log.error("getIstore()-Inside Exception block");
            try {
                eSession = Connection.forceGetEnterpriseSession();
                if (eSession != null) {
                    iStore = (IInfoStore) eSession.getService("", "InfoStore");
                }
            } catch (Exception ie) {
                log.error("getIstore()-Inside Exception block in Exception");
                log.error(ie.getMessage());
                String errorStrings[] = new String[1];
                errorStrings[0] = ie.getMessage();
                throw ie;

            }
        }
        if (log.isDebugEnabled())
            log.debug("End of getIstore()");
        return iStore;

    }

    /**
     * Method getInfoObjects.
     * 
     * @param query
     * @return IInfoObjects
     */
    public IInfoObjects getInfoObjects(String query) throws PrepSystemException {

        IInfoObjects infoObjects = null;
        IInfoStore iStore = null;

        if (log.isDebugEnabled())
            log.debug("Start of getInfoObjects(String query)");

        try {
            if (log.isDebugEnabled()) {
                log.debug("getInfoObjects()- Before gettting Istore");
            }
            iStore = getIstore();
            if (iStore != null) {
                infoObjects = (IInfoObjects) iStore.query(query);
                if (log.isDebugEnabled()) {
                    log.debug("getInfoObjects()- After gettting Istore");
                }
            }

        } catch (SDKException ske) {
            log.error("getInfoObjects()-In SDK Exception");
            try {
                Connection.logoff();
                iStore = getIstore();
                if (iStore != null) {
                    infoObjects = (IInfoObjects) iStore.query(query);
                }
            } catch (Exception se) {
                log.error("getInfoObjects()-Inside Exception in  SDK Exception");
                log.error(se.getMessage());
                String errorStrings[] = new String[1];
                errorStrings[0] = se.getMessage();
                throw new PrepSystemException("error.report.common.infoobject.exception", errorStrings);
            }
        } catch (Exception e) {
            log.error("getInfoObjects()-Inside Exception block:  " + e.getMessage());
            try {
                Connection.logoff();
                iStore = getIstore();
                if (iStore != null) {
                    infoObjects = (IInfoObjects) iStore.query(query);
                }
            } catch (Exception se) {
                log.error("getInfoObjects()-Inside Exception in Exception block:  " + se.getMessage());
                log.error(se.getMessage());
                String errorStrings[] = new String[1];
                errorStrings[0] = se.getMessage();
                throw new PrepSystemException("error.report.common.infoobject.exception", errorStrings);
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("End of getInfoObjects(String query)");
        }

        return infoObjects;
    }

    /**
     * Method getReportsList.
     * 
     * @param moduleName
     * @return ArrayList
     */

    public ReportListSearch getReportsList(ReportListSearch reportListSearch)
        throws PrepSystemException, PrepFunctionalException {

        String moduleName = reportListSearch.getModuleName();
        ArrayList reportsList = new ArrayList();
        IInfoObjects oInfoObjects = null;
        IInfoObject infoObject = null;
        String queryFolder = null;
        String queryReport = null;
        long id = 0;
        if (log.isDebugEnabled())
            log.debug("Strat of getReportsList()." + moduleName);

        try {
            queryReport =
                "Select SI_ID,SI_NAME,SI_DESCRIPTION,SI_PROGID,SI_INSTANCE, SI_KIND from CI_INFOOBJECTS Where SI_INSTANCE=0 And SI_KIND='CrystalReport' ORDER BY SI_NAME ASC";
            log.debug(queryReport);

            oInfoObjects = getInfoObjects(queryReport);

            try {
                if (oInfoObjects != null && oInfoObjects.size() > 0) {
                    for (Iterator iterator = oInfoObjects.iterator(); iterator.hasNext();) {

                        Report report = new Report();

                        infoObject = (IInfoObject) iterator.next();
                        if (!infoObject.isInstance()) {

                            report.setDescription(infoObject.getDescription());
                            report.setReportID(infoObject.getID() + "");
                            report.setReportName(infoObject.getTitle());
                            report.setReportType(infoObject.getKind());
                            reportsList.add(report);
                        }

                    }
                }
            } catch (Exception sdke) {
                log.error("@@@ReportList Exception 2@@@" + sdke.getMessage());
                Connection.logoff();
                oInfoObjects = getInfoObjects(queryFolder);
                id = ((IInfoObject) oInfoObjects.get(0)).getID();
                queryReport =
                    "Select SI_ID,SI_NAME,SI_DESCRIPTION,SI_PROGID,SI_INSTANCE, SI_KIND from CI_INFOOBJECTS Where SI_INSTANCE=0 And SI_KIND='CrystalReport' ORDER BY SI_NAME ASC";
                oInfoObjects = getInfoObjects(queryReport);

                for (Iterator iterator = oInfoObjects.iterator(); iterator.hasNext();) {
                    Report report = new Report();
                    infoObject = (IInfoObject) iterator.next();
                    if (!infoObject.isInstance()) {
                        report.setDescription(infoObject.getDescription());
                        report.setReportID(infoObject.getID() + "");
                        report.setReportName(infoObject.getTitle());
                        report.setReportType(infoObject.getKind());
                        reportsList.add(report);
                    }

                }

            }

        } catch (SDKException sdke) {
            log.error("getReportsList():-SDK Exception  " + sdke.getMessage());
            String errorStrings[] = new String[1];
            errorStrings[0] = sdke.getMessage();
            throw new PrepSystemException("error.report.sdk.reportlist.exception", errorStrings);
        } catch (Exception e) {
            log.error("getReportsList():-Exception  " + e.getMessage());
            log.error(e.getMessage());
            String errorStrings[] = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException("error.report.common.reportlist.exception", errorStrings);

        }
        if (log.isDebugEnabled())
            log.debug("End of getReportsList()");
        reportListSearch.setReports(reportsList);
        return reportListSearch;

    }

}
