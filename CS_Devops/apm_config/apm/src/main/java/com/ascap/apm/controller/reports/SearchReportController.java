package com.ascap.apm.controller.reports;


import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.handler.reports.ReportsHandler;
import com.ascap.apm.vob.BaseSearchVOB;
import com.ascap.apm.vob.reports.ReportListSearch;

@Controller
public class SearchReportController extends BaseReportController {

    private static final String SYSTEMMESSAGE = "systemmessage";

    private static final String SYSTEMERROR = "systemerror";

    /**
     * Constructor
     */
    public SearchReportController() {
        super();

    }

    @Autowired
    private ReportsHandler reportsHandler;

    @RequestMapping(value = "/reportList", method = RequestMethod.GET)
    public String SearchReport(@ModelAttribute("reportListSearch") ReportListSearch reportListSearch,@RequestParam("module") String module, HttpServletRequest request, Model model)
        throws Exception {
        log.debug("Entering SearchReport in SearchReportController");
        ReportListSearch listReports = new ReportListSearch();
        String operationFlag = null;

        try {
            System.out.println("Entered SearchReportController");
            module = listReports.getModuleName();
            System.out.println(module);
            operationFlag = listReports.getOperationFlag();
            module = request.getParameter("module");
            if (module == null || "".equals(module)) {
                module = (String) request.getSession().getAttribute("module");
            }
            if (module == null) {
                module = listReports.getModuleName().trim();
            } else {
                listReports.setModuleName(module);
                listReports.setNavigationType(null);
            }
            request.getSession().setAttribute("module", module);
            log.debug("Module::" + module);
            log.debug("operationFlag::" + operationFlag);
            return searchReports(listReports, request, model);
        } catch (Exception e) {
            log.error("ERROR in SearchReportController SearchReport " + e);
            log.error(e.getMessage());
            model.addAttribute(SYSTEMMESSAGE,
                messageSource.getMessage("id", new Object[] {"name"}, Locale.getDefault()));
        }
        return "reports/inputParameters";
    }

    public String searchReports(ReportListSearch listReports, HttpServletRequest request, Model model) throws Exception {
        log.debug("Entering searchReports method in SearchReportController");

        String forwardKey = new String();

        try {
            // Create input and output contexts
            PREPContext inputContext = getPREPContext(request);
            PREPContext outputContext = null;

            ReportListSearch listReportsVOB = null;

            // If it's a new search criteria, it creates a new SearchVOB and copies all the properties.
            // If it is not a new search ie., pagination then gets the SearchVOB from the session and set only
            // navigationType.

            if ((listReports.getBackToSearchResults() == null)
                && (listReports.getNavigationType() == null || listReports.getNavigationType().trim().equals(""))) {

                listReportsVOB = new ReportListSearch();

                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();

                listReports.setUserPreferences(userPreferences);

            } else {
                listReportsVOB = (ReportListSearch) inputContext.getUserSessionState().getSearch().getAlternateSearch();
                if (listReportsVOB != null) {
                    listReportsVOB.setNavigationType(listReports.getNavigationType());
                    listReportsVOB.setModuleName(listReports.getModuleName());
                    if (listReportsVOB.getUserPreferences() == null) {
                        UserPreference userPreferences =
                            getPREPContext(request).getUserSessionState().getUserPreference();
                        listReportsVOB.setUserPreferences(userPreferences);

                    }
                } else {

                    listReportsVOB = new ReportListSearch();
                    listReportsVOB.setNavigationType(null);
                    listReportsVOB.setModuleName(listReports.getModuleName());
                    UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                    listReportsVOB.setUserPreferences(userPreferences);

                }

            }

            // Set Input VOBS to inputContext
            inputContext.addInputValueObject(request);
            inputContext.addInputValueObject(listReportsVOB);

            try {
                outputContext = reportsHandler.setReportListPagination(inputContext);
            } catch (PrepFunctionalException dae) {
                log.warn(dae.getMessage());
                model.addAttribute("system.message",
                    messageSource.getMessage("report.error", new Object[] {}, Locale.getDefault()));
                forwardKey = "reports/reportsList";
            } catch (PrepSystemException pse) {
                log.error(pse.getMessage());
                model.addAttribute(SYSTEMERROR,
                    messageSource.getMessage("report.error", new Object[] {}, Locale.getDefault()));
                forwardKey = "reports/reportsList";
            }
            if (!model.containsAttribute(SYSTEMERROR)) {
                listReports = new ReportListSearch();
                ReportListSearch searchVob = null;
                List<Object> outValueObjects = outputContext.getOutputValueObjects();

                if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                    searchVob = (ReportListSearch) outValueObjects.iterator().next();
                }

                // Copy the the search results output to the Form, remove the search results collection from the
                // searchVOb and set it to the session.
                List<?> list = searchVob.getSearchResults();
                listReports.setReports(list);
                listReports.setModuleName(searchVob.getModuleName());
                // make the search results null

               // searchVob.setSearchResults(null);

                if (inputContext.getUserSessionState().getSearch() == null) {
                    BaseSearchVOB baseSearchVob = new BaseSearchVOB();
                    inputContext.getUserSessionState().setSearch(baseSearchVob);
                }
                inputContext.getUserSessionState().getSearch().setAlternateSearch(searchVob);
                request.setAttribute("reportListSearch", listReports);
                request.getSession().setAttribute("reportListSearch", listReports);
                request.getSession().setAttribute("reportForm", null);
                model.addAttribute("reportListSearch", listReports);
                // set the UserSessionState objec to the session.
                setUserSessionState(request, inputContext.getUserSessionState());

                forwardKey = "reports/reportsList";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            model.addAttribute(SYSTEMERROR,
                messageSource.getMessage("report.error", new Object[] {}, Locale.getDefault()));
            forwardKey = "reports/reportsList";
        }

        if (log.isDebugEnabled()) {
            log.debug("Exiting searchReports method in SearchReportAction");
        }

        // Forwards the control to SearchResults page.

        return forwardKey;
    }

}
