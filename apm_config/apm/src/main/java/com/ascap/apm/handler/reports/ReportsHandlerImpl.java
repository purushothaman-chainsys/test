package com.ascap.apm.handler.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.handler.BaseHandler;
import com.ascap.apm.service.report.ReportsService;
import com.ascap.apm.vob.reports.Report;
import com.ascap.apm.vob.reports.ReportListSearch;

@Service("reportsHandler")
public class ReportsHandlerImpl extends BaseHandler implements ReportsHandler {

    private static final long serialVersionUID = 3260668370321869797L;

    @Autowired
    private ReportsService reportsService;

    public PREPContext setReportListPagination(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        PREPContext outputContext = new PREPContext();
        PREPContext inputContextNew = new PREPContext();
        try {
            List listOfReports = null;
            List<Object> inputValueObjects = inputContext.getInputValueObjects();
            ReportListSearch reportSearchVOB = null;
            HttpServletRequest request = null;
            // // retrieve the agreementSearchVOB from the inputContext
            if (inputValueObjects != null && inputValueObjects.size() > 0) {
                Iterator iterator = inputValueObjects.iterator();
                while (iterator.hasNext()) {
                    Object object = iterator.next();
                    if (object instanceof HttpServletRequest) {
                        request = (HttpServletRequest) object;
                    } else {
                        reportSearchVOB = (ReportListSearch) object;

                    }

                }

                inputContextNew.addInputValueObject(reportSearchVOB);
                outputContext = getReportList(inputContextNew);

                List<Object> outValueObjects = outputContext.getOutputValueObjects();

                if ((outValueObjects != null) && (outValueObjects.size() > 0)) {
                    reportSearchVOB = (ReportListSearch) outValueObjects.iterator().next();
                }
                listOfReports = reportSearchVOB.getReports();
                // sending the list to TAM for Filtering
                List<Object> filteredList = getTAMFilteredReportsList(listOfReports, request);
                int listsize = filteredList.size();
                reportSearchVOB.setNumberOfRecordsFound(filteredList.size());

                if (listsize != 0) {
                    int fromIndex = reportSearchVOB.getFromIndex() - 1;
                    int toIndex = reportSearchVOB.getToIndex() - 1;
                    if (fromIndex >= listsize) {
                        fromIndex = listsize - 1;

                    }
                    if (toIndex > listsize) {
                        toIndex = listsize;
                    }
                    List<Object> sublist = new ArrayList<>();
                    for (int i = fromIndex; i < toIndex; i++) {
                        sublist.add(filteredList.get(i));

                    }
                    reportSearchVOB.setSearchResults(sublist);
                    reportSearchVOB.setReports(sublist);
                }

                outputContext.addOutputValueObject(reportSearchVOB);

            }

        } catch (PrepSystemException pse) {
            throw pse;
        } catch (PrepFunctionalException pfe) {
            throw pfe;
        }

        catch (Exception e) {
            log.error(e.getMessage());
            String errorStrings[] = new String[1];
            errorStrings[0] = e.getMessage();
            throw new PrepSystemException("error.report.common.basereport.pagination.exception", errorStrings);

        }

        return outputContext;
    }

    public PREPContext getReportList(PREPContext prepCtx) throws PrepSystemException, PrepFunctionalException {

        if (log.isDebugEnabled()) {
            log.debug("Entering getReportList method in ReportControllerBean");
        }

        PREPContext outputContext = new PREPContext();
        try {
            List<Object> inputValueObjects = prepCtx.getInputValueObjects();
            ReportListSearch reportListSearch = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                reportListSearch = (ReportListSearch) inputValueObjects.iterator().next();
            }

            reportListSearch = reportsService.getReportsList(reportListSearch);

            outputContext.addOutputValueObject(reportListSearch);
        } catch (PrepSystemException pse) {
            throw pse;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.report.common.ejb.exception");
        }

        if (log.isDebugEnabled()) {
            log.debug("Exiting getReportList method in ReportControllerBean");
        }
        return outputContext;

    }

    private List getTAMFilteredReportsList(List listOfReports, HttpServletRequest request) throws Exception {

        // System.out.print("Entering into hasPermission method");

        boolean haveAccess = false;
        String name = null;
        List filteredList = new ArrayList();

        String userId = ((UserProfile) request.getSession().getAttribute(UserProfile.HTTP_SESSION_KEY)).getUserId();
        System.out.println("user id" + userId);
        UserProfile userProfile = (UserProfile) request.getSession().getAttribute(UserProfile.HTTP_SESSION_KEY);

        // todo: Temp solution to turn on/off the security (Call to TAM). Need to be removed later.
        if (!userProfile.isSecurityEnabled()) {
            if (log.isDebugEnabled()) {
                log.debug("BaseReportAction - getTAMFilteredReportsList() method - security disabled. returning null.");
            }
            return null;
        }

        for (int i = 0; i < listOfReports.size(); i++) {
            name = ((Report) listOfReports.get(i)).getReportName();

            System.out.println("name of the report::" + name);

            filteredList.add(listOfReports.get(i));

        }

        return filteredList;
    }
}
