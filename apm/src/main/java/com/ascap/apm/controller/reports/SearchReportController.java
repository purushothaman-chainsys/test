package com.ascap.apm.controller.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.handler.reports.ReportHandler;
import com.ascap.apm.vob.BaseSearchVOB;
import com.ascap.apm.vob.reports.ListReports;
import com.ascap.apm.vob.reports.ReportListSearch;

@Controller
@RequestMapping("/report")
@SessionAttributes("listReports")
public class SearchReportController extends BaseReportController {

    private static final String SYSTEMMESSAGE = "systemmessage";

    private static final String SYSTEMERROR = "systemerror";

    private static final String REPORTSLIST = "reports/reportsList";

    private static final String REPORT_ERROR = "ap.report.error";

    private static final String LIST_REPORTS = "listReports";

    private static final String MODULE = "module";

    /**
     * Constructor
     */
    public SearchReportController() {
        super();

    }
    @ModelAttribute("listReports")
    public ListReports setReportForm() {
        return new ListReports();
    }
    
    @Autowired
    private ReportHandler reportHandler;

    @RequestMapping(value = {"/reportList","/report"})
    public ModelAndView SearchReport(@ModelAttribute("listReports") ListReports listReports,
        HttpServletRequest request, Model model) throws Exception {
        log.debug("Entering SearchReport in SearchReportController");
        String operationFlag = null;
        try {
            String module = null;
            module = request.getParameter(MODULE);
            if (module == null || "".equals(module)) {
                module = (String) request.getSession().getAttribute(MODULE);
            }
            if (module == null) {
                module = listReports.getModuleName().trim();
            } else {
                listReports.setModuleName(module);
                listReports.setNavigationType(null);
            }
            request.getSession().setAttribute(MODULE, module);
            log.debug("Module::" + module);
            log.debug("operationFlag::" + operationFlag);
            return new ModelAndView(searchReports(listReports, request, model));
        } catch (Exception e) {
            log.error("ERROR in SearchReportController SearchReport " + e);
            log.error(e.getMessage());
            model.addAttribute(SYSTEMERROR,
                getMessage("id"));
        }
        return new ModelAndView("reports/inputParameters");
    }

    public String searchReports(ListReports listReports, HttpServletRequest request, Model model) throws Exception {
        log.debug("Entering searchReports method in SearchReportController");
        String forwardKey = "";
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
                BeanUtils.copyProperties(listReportsVOB, listReports);
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
                outputContext = reportHandler.setReportListPagination(inputContext);
            } catch (PrepFunctionalException dae) {
                log.warn(dae.getMessage());
                model.addAttribute(SYSTEMMESSAGE,
                    getMessage(REPORT_ERROR,dae.getMessage()));
                forwardKey = REPORTSLIST;
            } catch (PrepSystemException pse) {
                log.error(pse.getMessage());
                model.addAttribute(SYSTEMERROR,
                    getMessage(REPORT_ERROR,pse.getMessage()));
                forwardKey = REPORTSLIST;
            }
            if (!model.containsAttribute(SYSTEMERROR)) {
                ReportListSearch searchVob = null;
                List<Object> outValueObjects = outputContext.getOutputValueObjects();
                if ((outValueObjects != null) && (!outValueObjects.isEmpty())) {
                    searchVob = (ReportListSearch) outValueObjects.iterator().next();
                }
                searchVob.setBaseFlag(true);
                BeanUtils.copyProperties(listReports, searchVob);
                searchVob.setBaseFlag(false);
                List<Object> list = searchVob.getSearchResults();
                listReports.setReports(list);
                listReports.setModuleName(searchVob.getModuleName());
                if (inputContext.getUserSessionState().getSearch() == null) {
                    BaseSearchVOB baseSearchVob = new BaseSearchVOB();
                    inputContext.getUserSessionState().setSearch(baseSearchVob);
                }
                inputContext.getUserSessionState().getSearch().setAlternateSearch(searchVob);
                request.getSession().setAttribute(LIST_REPORTS, listReports);
                request.getSession().setAttribute("report", null);
                model.addAttribute(LIST_REPORTS, listReports);
                // set the UserSessionState objec to the session.
                setUserSessionState(request, inputContext.getUserSessionState());
                forwardKey = REPORTSLIST;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            model.addAttribute(SYSTEMERROR,
                getMessage(REPORT_ERROR,e.getMessage()));
            forwardKey = REPORTSLIST;
        }

        if (log.isDebugEnabled()) {
            log.debug("Exiting searchReports method in SearchReportController");
        }
        // Forwards the control to SearchResults page.
        return forwardKey;
    }

}
