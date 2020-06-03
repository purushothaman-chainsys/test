package com.ascap.apm.controller.usage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.controller.utils.Utils;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.MusicUserSearch;
import com.ascap.apm.vob.usage.PerformanceSearch;

@Controller
@RequestMapping("/usage")
public class MusicUserSearchController extends BaseUsageController {

    @Autowired
    private UsageHandler usageHandler;

    private static final String USPAARTYSEARCH = "usage/usPartySearch";

    private static final String ERRORPAGE = "error";

    private static final String SYSTEMERROR = "systemerror";

    @RequestMapping(value = "/musicUserSearch", method = {RequestMethod.GET, RequestMethod.POST})
    public String musicUserSearch(@ModelAttribute("musicUserSearch") MusicUserSearch musicUserSearch,
        BindingResult result, HttpServletRequest request, Model model) throws Exception {

        if (Utils.isEmptyOrNull(musicUserSearch.getOperationType())) {
            return USPAARTYSEARCH;
        }
        return musicUserPartySearch(musicUserSearch, request, model);
    }

    private String musicUserPartySearch(MusicUserSearch musicUserSearchForm, HttpServletRequest request, Model m)
        throws Exception {

        if (Utils.isEmptyOrNull(musicUserSearchForm.getFilterEffectiveDate())) {
            m.addAttribute(SYSTEMERROR, getMessage("us.error.apm.effectivedate.required"));
            return USPAARTYSEARCH;
        }
        MusicUserSearch musicUserSearch = null;
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        String forwardKey = USPAARTYSEARCH;
        List<Object> outValueObjects = null;
        PerformanceSearch performanceSearch = null;
        if (musicUserSearchForm.getNavigationType() == null
            || "FIRST".equalsIgnoreCase(musicUserSearchForm.getNavigationType())
            || "".equalsIgnoreCase(musicUserSearchForm.getNavigationType().trim())) {
            musicUserSearch = new MusicUserSearch();

            UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
            musicUserSearchForm.setUserPreferences(userPreferences);
            BeanUtils.copyProperties(musicUserSearch, musicUserSearchForm);

        } else {
            if (inputContext.getUserSessionState().getSearch() != null) {
                musicUserSearch = (MusicUserSearch) inputContext.getUserSessionState().getSearch().getAlternateSearch();
                if (musicUserSearch == null) {
                    musicUserSearch = new MusicUserSearch();
                }
                musicUserSearch.setNavigationType(musicUserSearchForm.getNavigationType());

            } else {
                musicUserSearch = new MusicUserSearch();
                UserPreference userPreferences = getPREPContext(request).getUserSessionState().getUserPreference();
                musicUserSearchForm.setUserPreferences(userPreferences);
                BeanUtils.copyProperties(musicUserSearch, musicUserSearchForm);
            }
        }
        try {
            inputContext.addInputValueObject(musicUserSearch);
            outputContext = new PREPContext();
            try {
                // Call SearchAgreement service
                musicUserSearchForm = null;
                outputContext = usageHandler.seacrhParty(inputContext);
                outValueObjects = outputContext.getOutputValueObjects();
                if ((outValueObjects != null) && !outValueObjects.isEmpty()) {
                    musicUserSearchForm = new MusicUserSearch();
                    musicUserSearch = (MusicUserSearch) outValueObjects.iterator().next();
                    BeanUtils.copyProperties(musicUserSearchForm, musicUserSearch);
                    m.addAttribute("musicUserSearch", musicUserSearchForm);
                    forwardKey = USPAARTYSEARCH;
                }
                if (inputContext.getUserSessionState().getSearch() == null) {
                    performanceSearch = new PerformanceSearch();
                    performanceSearch.setAlternateSearch(musicUserSearch);
                } else {
                    inputContext.getUserSessionState().getSearch().setAlternateSearch(musicUserSearch);
                }
            } catch (PrepFunctionalException dae) {
                log.warn(dae.getMessage());
                m.addAttribute(SYSTEMERROR, dae.getErrorKey());
                forwardKey = USPAARTYSEARCH;
            } catch (PrepSystemException pse) {
                log.error(pse.getMessage());
                m.addAttribute(SYSTEMERROR, pse.getErrorKey());
                forwardKey = ERRORPAGE;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("Error in musicUserPartySearch method in MusicUserSearchController" + e);
            forwardKey = ERRORPAGE;
        }
        log.debug("Exiting musicUserPartySearch method in MusicUserSearchController");
        return forwardKey;
    }

}
