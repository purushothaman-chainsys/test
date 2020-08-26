package com.ascap.apm.controller.usage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.UsageCommonValidations;
import com.ascap.apm.common.utils.constants.Constants;
import com.ascap.apm.common.utils.constants.UsageConstants;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.InfoBarItemVOB;
import com.ascap.apm.vob.usage.PerformanceSearch;
import com.ascap.apm.vob.usage.ProgramPerformance;
import com.ascap.apm.vob.usage.UsageInfoBar;

/**
 * Program Performance Multi Action adds, updates, deletes Program Performance
 * 
 * @author Manoj Puli
 * @version $Revision: 1.17 $ $Date: Oct 05 2012 12:34:32 $
 */

@Controller
public class ProgramPerformanceMultiController extends BaseUsageController {

    private static final String USPROGRAMPERFORMANCE = "/usage/usProgramPerformance";

    private static final String USWORKPERFORMANCESSEARCHRESULTS = "/usage/usWorkPerformancesSearchResults";

    private static final String SYSTEMERROR = "systemerror";

    private static final String SYSTEMMESSAGE = "systemmessage";

    private static final String ERRORPAGE = "error";

    private static final String PROGRAM_PERFORMANCE = "Program Performance";

    private static final String PROGRAMPERFORMANCE = "programPerformance";

    private static final String AUDIT_USAGE_PROGRAMPERFORMANCE_CREATED = "audit.usage.programPerformance.created";

    private static final String USAGE_UPDATE_PROGRAMPERFORMANCE_CREATED = "usage.updateprogramPerformance.created";

    private static final String USAGE_PROGRAMPERFORMANCE_ADDPROGRAMPERFORMANCE_CREATED =
        "usage.programperformance.addprogramPerformance.created";

    private static final String ERROR_USAGE_INVALIDPARAMETER_MUSICUSER = "error.usage.invalidparameter.musicUser";

    private static final String ERROR_MESSAGE_CREATEPROGRAM =
        "Error Caught in ProgramPerformanceMultiAction.createProgramPerformance:";

    private static final String ERROR_MESSAGE_UPDATEPROGRAM =
        "Error Caught in ProgramPerformanceMultiAction.updateProgramPerformance:";

    private static final String ERROR_MESSAGE_UPDATE_MUSIC_INFO =
        "Error Caught in ProgramPerformanceMultiAction.updateMusicUserInformation:";

    private static final String PERFORMANCE_MUSIC_INFO = "PERFORMANCE_MUSIC_USER_INFO_FIXED_MODE";

    public ProgramPerformanceMultiController() {
        super();
    }

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/programPerformanceMultiAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String performanceMultiAction(@Valid @ModelAttribute ProgramPerformance programPerformance,
        BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response, Model m,
        RedirectAttributes redirectAttrbute) throws Exception {
        log.debug("Entering performanceMultiAction method in ProgramPerformanceMultiController");
        String actionSelected = null;
        String forwordKey = "";

        actionSelected = request.getParameter("actionSelected");
        log.debug("ProgramPerformanceMultiAction.performanceMultiAction actionSelected :" + actionSelected);

        if (actionSelected != null) {
            if ("CREATE_PROGRAM_PERFORMANCE".equalsIgnoreCase(actionSelected)) {
                return createProgramPerformance(programPerformance, request, m, bindingResult);
            } else if ("EDIT_MUSIC_USER_INFORMATION".equalsIgnoreCase(actionSelected)) {
                forwordKey = editMusicUserInformation(programPerformance, m);
            } else if ("UPDATE_MUSIC_USER_INFORMATION".equalsIgnoreCase(actionSelected)) {
                forwordKey = updateMusicUserInformation(programPerformance, request, m, bindingResult);
            } else if ("UPDATE_PROGRAM_PERFORMANCE".equalsIgnoreCase(actionSelected)) {
                return updateProgramPerformance(programPerformance, request, m, bindingResult);
            } else if ("RETRIEVE_WORK_PERFORMANCES_LIST".equalsIgnoreCase(actionSelected)) {
                return retrieveWorkPerformancesList(programPerformance, request, m, redirectAttrbute);
            } else {
                return forwordKey;
            }
        } else {
            return forwordKey;
        }
        return forwordKey;
    }

    /**
     * Method createProgramPerformance.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public String createProgramPerformance(ProgramPerformance programPerformance, HttpServletRequest request, Model m,
        BindingResult result) throws Exception {

        log.debug("Entering createProgramPerformance method in ProgramPerformanceMultiController");

        if (result.hasErrors()) {
            return USPROGRAMPERFORMANCE;
        }
        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;

        String forwardKey = "";
        programPerformance.setVersionNumber("0");
        programPerformance.setIsCurrentVersion("Y");
        programPerformance.setSourceSystem(UsageConstants.SOURCE_SYSTEM_PREP);

        try {

            programPerformance.setDuration(deriveDuration(programPerformance));

            // Set Input VOBS to inputContext
            inputContext.addInputValueObject(programPerformance);

            // Call CreateAgreement service
            outputContext = usageHandler.addProgramPerformance(inputContext);

        } catch (PrepSystemException pse) {
            log.debug(ERROR_MESSAGE_CREATEPROGRAM + pse);
            log.error(pse.getMessage());
            log.error(ERROR_MESSAGE_CREATEPROGRAM, pse);
            m.addAttribute(SYSTEMERROR, pse.getErrorKey());
            forwardKey = ERRORPAGE;
        } catch (PrepFunctionalException pfex) {

            log.debug(ERROR_MESSAGE_CREATEPROGRAM + pfex);
            log.error(pfex.getMessage());
            log.warn(ERROR_MESSAGE_CREATEPROGRAM + pfex);
            m.addAttribute(SYSTEMERROR, pfex.getErrorKey());
            forwardKey = ERRORPAGE;
        } catch (Exception ex) {
            log.debug(ERROR_MESSAGE_CREATEPROGRAM + ex);
            log.error(ex.getMessage());
            log.error(ERROR_MESSAGE_CREATEPROGRAM, ex);
            m.addAttribute(SYSTEMERROR, ex.getMessage());
            forwardKey = ERRORPAGE;
        }

        if (!m.asMap().containsKey(SYSTEMERROR) && outputContext != null) {
            programPerformance = new ProgramPerformance();
            List<Object> outValueObjects = outputContext.getOutputValueObjects();
            if ((outValueObjects != null) && !outValueObjects.isEmpty()) {
                programPerformance = (ProgramPerformance) outValueObjects.iterator().next();
            }

            // set the newly created ProgramPerformanceID to the UserSessionState object in the session.
            inputContext.getUserSessionState().setId(programPerformance.getPerformanceHeaderId());
            inputContext.getUserSessionState().setModuleName(Constants.USAGE_MODULE);
            setUserSessionState(request, inputContext.getUserSessionState());
            programPerformance.setMusicUserInformationMode(PERFORMANCE_MUSIC_INFO);
            programPerformance.setOperationMode("RETRIEVE_MODE");
            m.addAttribute(PROGRAMPERFORMANCE, programPerformance);

            // Prepare the infobar collection (only when it is fresh search), set it to the UsageInfoBar VOB and add to
            // the session.
            // If it comes from different module through context search, need to create new UsageInfoBar object
            UsageInfoBar usageInfoBar = null;
            try {
                usageInfoBar = (UsageInfoBar) inputContext.getUserSessionState().getInfoBar();
            } catch (ClassCastException ce) {
                usageInfoBar = new UsageInfoBar();
            }

            if (usageInfoBar == null) {
                usageInfoBar = new UsageInfoBar();
            }
            usageInfoBar.setProgramPerformanceInfo(this.getProgramPerformanceInfoBarList(programPerformance));
            inputContext.getUserSessionState().setInfoBar(usageInfoBar);
            m.addAttribute(SYSTEMMESSAGE,
                messageSource.getMessage(USAGE_PROGRAMPERFORMANCE_ADDPROGRAMPERFORMANCE_CREATED,
                    new Object[] {PROGRAM_PERFORMANCE}, Locale.getDefault()));

            forwardKey = USPROGRAMPERFORMANCE;
        }

        log.debug("Exiting createProgramPerformance method in ProgramPerformanceMultiAction");
        return forwardKey;
    }

    /**
     * Method editMusicUserInformation, Edit Music User Info changes the MusicUserInfoMode to EditMode so that the User
     * can Edit the Music User Info in the ProgramPerformance. This does not require any call to the Service.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public String editMusicUserInformation(ProgramPerformance programPerformance, Model m) {

        log.debug("Entering editMusicUserInformation method in ProgramPerformanceMultiController");
        programPerformance.setMusicUserInformationMode("PERFORMANCE_MUSIC_USER_INFO_EDIT_MODE");
        m.addAttribute(PROGRAMPERFORMANCE, programPerformance);
        log.debug("Exiting editMusicUserInformation method in ProgramPerformanceMultiController :"
            + programPerformance.getMusicUserInformationMode());
        return USPROGRAMPERFORMANCE;
    }

    /**
     * Method updateProgramPerformance.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public String updateProgramPerformance(ProgramPerformance programPerformance, HttpServletRequest request, Model m,
        BindingResult result) throws Exception {
        int a = 0;
        log.debug("Entering updateProgramPerformance method in ProgramPerformanceMultiAction");
        if (result.hasErrors()) {
            return USPROGRAMPERFORMANCE;
        }
        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        ProgramPerformance outProgramPerformance = null;
        String forwardKey = "";

        try {
            // Create ProgramPerformance VOB and Set data from Action Form
            // Set Input VOBS to inputContext
            inputContext.addInputValueObject(programPerformance);

            outputContext = usageHandler.updateProgramPerformance(inputContext);
        } catch (PrepSystemException pse) {
            log.debug(ERROR_MESSAGE_UPDATEPROGRAM + pse);
            log.error(pse.getMessage());
            log.error(ERROR_MESSAGE_UPDATEPROGRAM, pse);
            m.addAttribute(SYSTEMERROR, pse.getErrorKey());
            forwardKey = ERRORPAGE;
        } catch (PrepFunctionalException pfex) {
            log.debug(ERROR_MESSAGE_UPDATEPROGRAM + pfex);
            log.error(pfex.getMessage());
            log.warn(ERROR_MESSAGE_UPDATEPROGRAM + pfex);
            m.addAttribute(SYSTEMERROR, pfex.getErrorKey());
            forwardKey = ERRORPAGE;
        } catch (Exception ex) {
            log.debug(ERROR_MESSAGE_UPDATEPROGRAM + ex);
            log.error(ex.getMessage());
            log.error(ERROR_MESSAGE_UPDATEPROGRAM, ex);
            m.addAttribute(SYSTEMERROR, ex.getMessage());
            forwardKey = ERRORPAGE;
        }

        if (!m.containsAttribute(SYSTEMERROR)) {
            programPerformance = new ProgramPerformance();
            List<Object> outValueObjects = null;
            if (outputContext != null) {
                outValueObjects = outputContext.getOutputValueObjects();
            }
            if ((outValueObjects != null) && !outValueObjects.isEmpty()) {
                outProgramPerformance = (ProgramPerformance) outValueObjects.iterator().next();
            }

            // set the newly created ProgramPerformanceID to the UserSessionState object in the session.
            if (outProgramPerformance != null) {
                inputContext.getUserSessionState().setId(outProgramPerformance.getPerformanceHeaderId());
            }
            inputContext.getUserSessionState().setModuleName(Constants.USAGE_MODULE);
            setUserSessionState(request, inputContext.getUserSessionState());

            programPerformance.setMusicUserInformationMode(PERFORMANCE_MUSIC_INFO);
            programPerformance.setOperationMode("RETRIEVE_MODE");
            request.setAttribute(PROGRAMPERFORMANCE, programPerformance);

            // Info bar settings
            // Prepare the infobar collection (only when it is fresh search), set it to the UsageInfoBar VOB and add to
            // the session.
            // If it comes from different module through context search, need to create new UsageInfoBar object
            UsageInfoBar usageInfoBar = null;
            try {
                usageInfoBar = (UsageInfoBar) inputContext.getUserSessionState().getInfoBar();
            } catch (ClassCastException ce) {
                usageInfoBar = new UsageInfoBar();
            }

            if (usageInfoBar == null) {
                usageInfoBar = new UsageInfoBar();
            }
            usageInfoBar.setProgramPerformanceInfo(this.getProgramPerformanceInfoBarList(outProgramPerformance));
            inputContext.getUserSessionState().setInfoBar(usageInfoBar);
            m.addAttribute(SYSTEMMESSAGE, messageSource.getMessage(USAGE_UPDATE_PROGRAMPERFORMANCE_CREATED,
                new Object[] {PROGRAM_PERFORMANCE}, Locale.getDefault()));
            forwardKey = USPROGRAMPERFORMANCE;
        }
        log.debug("Exiting updateProgramPerformance method in ProgramPerformanceMultiAction");
        return forwardKey;
    }

    /**
     * Method updateMusicUserInformation.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public String updateMusicUserInformation(ProgramPerformance programPerformance, HttpServletRequest request, Model m,
        BindingResult result) throws Exception {

        log.debug("Entering updateMusicUserInformation method in ProgramPerformanceMultiAction");
        // Create input and output contexts
        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        String musicUserErrors = programPerformance.getIsInvalidMusicUser();
        if (result.hasErrors()) {
            return USPROGRAMPERFORMANCE;
        }
        ProgramPerformance outProgramPerformanceForm = null;
        ProgramPerformance outProgramPerformance = null;
        ProgramPerformance dbProgramPerformance = null;
        String forwardKey = "";

        try {
            // Create ProgramPerformance VOB and Set data from Action Form
            programPerformance.setDuration(deriveDuration(programPerformance));
            BeanUtils.copyProperties(programPerformance, programPerformance);
            programPerformance.setMusicUserTypeCode(null);
            programPerformance.setMusicUserTypeDescription(null);
            programPerformance.setLicenseTypeCode(null);
            programPerformance.setLicenseTypeDescription(null);
            programPerformance.setMusicUserCallLetterSuffix(null);
            programPerformance.setMusicUserCallLetterOnly(null);
            programPerformance.setMusicUserCallLetterSuffixOnly(null);
            programPerformance.setMusicUserFirstName(null);
            programPerformance.setMusicUserLastName(null);
            programPerformance.setMusicUserFullName(null);
            if (UsageCommonValidations.isValidLong(programPerformance.getPerformanceHeaderId())) {
                inputContext = getPREPContext(request);
                dbProgramPerformance = new ProgramPerformance();
                dbProgramPerformance.setPerformanceHeaderId(programPerformance.getPerformanceHeaderId());
                inputContext.addInputValueObject(dbProgramPerformance);
                outputContext = usageHandler.getProgramPerformance(inputContext);
                List<Object> outTempValueObjects = outputContext.getOutputValueObjects();

                if ((outTempValueObjects != null) && !outTempValueObjects.isEmpty()) {
                    dbProgramPerformance = (ProgramPerformance) outTempValueObjects.iterator().next();
                    log.debug(
                        "ProgramPerformanceMultiAction.updateMusicUserInformation() Getting the Performance Object As in the Database is :"
                            + dbProgramPerformance);
                    if (dbProgramPerformance != null) {
                        dbProgramPerformance.setMusicUserId(programPerformance.getMusicUserId());
                        dbProgramPerformance.setPerformanceStartDate(programPerformance.getPerformanceStartDate());
                        dbProgramPerformance.setPerformanceStartTime(programPerformance.getPerformanceStartTime());
                        dbProgramPerformance.setPerformanceEndDate(programPerformance.getPerformanceEndDate());
                        dbProgramPerformance.setPerformanceEndTime(programPerformance.getPerformanceEndTime());
                        dbProgramPerformance.setDuration(programPerformance.getDuration());
                        dbProgramPerformance.setMusicUserTypeCode(null);
                        dbProgramPerformance.setMusicUserTypeDescription(null);
                        dbProgramPerformance.setLicenseTypeCode(null);
                        dbProgramPerformance.setLicenseTypeDescription(null);
                        dbProgramPerformance.setMusicUserCallLetterSuffix(null);
                        dbProgramPerformance.setMusicUserCallLetterOnly(null);
                        dbProgramPerformance.setMusicUserCallLetterSuffixOnly(null);
                        dbProgramPerformance.setMusicUserFirstName(null);
                        dbProgramPerformance.setMusicUserLastName(null);
                        dbProgramPerformance.setMusicUserFullName(null);
                    } else {
                        dbProgramPerformance = programPerformance;
                    }
                } else {
                    dbProgramPerformance = programPerformance;
                }
            } else {
                dbProgramPerformance = programPerformance;
            }

            inputContext = getPREPContext(request);
            // Set Input VOBS to inputContext
            inputContext.addInputValueObject(dbProgramPerformance);
            // Call CreateAgreement service
            outputContext = usageHandler.getPerformanceMusicUserInfo(inputContext);
            log.debug("ProgramPerformanceMultiAction.updateMusicUserInformation Call to Service Successfull");

        } catch (PrepSystemException pse) {
            log.debug(ERROR_MESSAGE_UPDATE_MUSIC_INFO + pse);
            log.error(pse.getMessage());
            log.error(ERROR_MESSAGE_UPDATE_MUSIC_INFO, pse);

            m.addAttribute(SYSTEMERROR, pse.getErrorKey());
            return ERRORPAGE;
        } catch (PrepFunctionalException pfex) {
            log.debug(ERROR_MESSAGE_UPDATE_MUSIC_INFO + pfex);
            log.error(pfex.getMessage());
            log.warn(ERROR_MESSAGE_UPDATE_MUSIC_INFO + pfex);
            m.addAttribute(SYSTEMERROR, pfex.getErrorKey());
            return ERRORPAGE;
        } catch (Exception ex) {
            log.debug(ERROR_MESSAGE_UPDATE_MUSIC_INFO + ex);
            log.error(ex.getMessage());
            log.error(ERROR_MESSAGE_UPDATE_MUSIC_INFO, ex);
            m.addAttribute(SYSTEMERROR, ex.getMessage());
            return ERRORPAGE;
        }

        if (!m.containsAttribute(SYSTEMERROR)) {
            outProgramPerformanceForm = new ProgramPerformance();
            List<Object> outValueObjects = outputContext.getOutputValueObjects();

            if ((outValueObjects != null) && !outValueObjects.isEmpty()) {
                outProgramPerformance = (ProgramPerformance) outValueObjects.iterator().next();
                log.debug("ProgramPerformanceMultiAction.updateMusicUserInformation() outProgramPerformance is :"
                    + outProgramPerformance);
                if (outProgramPerformance != null) {
                    BeanUtils.copyProperties(outProgramPerformanceForm, outProgramPerformance);
                } else {
                    m.addAttribute(SYSTEMERROR, messageSource.getMessage(ERROR_USAGE_INVALIDPARAMETER_MUSICUSER,
                        new Object[] {PROGRAM_PERFORMANCE}, Locale.getDefault()));
                    BeanUtils.copyProperties(outProgramPerformanceForm, programPerformance);
                }
            }

            // Preserving Opearion Mode
            outProgramPerformanceForm.setOperationMode(programPerformance.getOperationMode());
            outProgramPerformanceForm.setIsInvalidMusicUser(musicUserErrors);

            if (UsageCommonValidations.isEmptyOrNull(outProgramPerformanceForm.getMusicUserTypeCode())
                || UsageCommonValidations.isEmptyOrNull(outProgramPerformanceForm.getLicenseTypeCode())) {
                m.addAttribute(PROGRAMPERFORMANCE, outProgramPerformanceForm);
            } else {
                outProgramPerformanceForm.setMusicUserInformationMode(PERFORMANCE_MUSIC_INFO);
                m.addAttribute(PROGRAMPERFORMANCE, outProgramPerformanceForm);
            }
            forwardKey = USPROGRAMPERFORMANCE;
        } else {
            forwardKey = USPROGRAMPERFORMANCE;
            programPerformance.setMusicUserInformationMode("PERFORMANCE_MUSIC_USER_INFO_EDIT_MODE");
            programPerformance.setIsInvalidMusicUser(musicUserErrors);
            m.addAttribute(PROGRAMPERFORMANCE, programPerformance);
        }
        log.debug("Exiting updateMusicUserInformation method in ProgramPerformanceMultiAction");
        return forwardKey;
    }

    private String deriveDuration(ProgramPerformance inputForm) {
        SimpleDateFormat simpleTimeFormat;

        simpleTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        simpleTimeFormat.setLenient(false);

        long startMillis = 0;
        long endMillis = 0;
        String durationString = "";
        try {
            if (simpleTimeFormat.parse(inputForm.getPerformanceStartDate() + " " + inputForm.getPerformanceStartTime())
                .after(simpleTimeFormat
                    .parse(inputForm.getPerformanceEndDate() + " " + inputForm.getPerformanceEndTime()))) {
            } else {
                startMillis = simpleTimeFormat
                    .parse(inputForm.getPerformanceStartDate() + " " + inputForm.getPerformanceStartTime()).getTime();
                endMillis = simpleTimeFormat
                    .parse(inputForm.getPerformanceEndDate() + " " + inputForm.getPerformanceEndTime()).getTime();
                durationString = String.valueOf(((endMillis - startMillis) / 1000) + 1);
            }
        } catch (ParseException pse) {
            log.error(pse.getMessage());
        }
        return durationString;
    }

    private List<Object> getProgramPerformanceInfoBarList(ProgramPerformance programPerformanceVob) {
        List<Object> outcoll = null;

        // Check if programPerformanceVob is null if null just return the empty collection
        if (programPerformanceVob == null) {
            return outcoll;
        }

        outcoll = new ArrayList<>();

        if (StringUtils.isNotEmpty(programPerformanceVob.getMusicUserId())) {
            outcoll.add(new InfoBarItemVOB("Music User Id", programPerformanceVob.getMusicUserId()));
        }
        if (StringUtils.isNotEmpty(
            programPerformanceVob.getMusicUserLastName() + programPerformanceVob.getMusicUserCallLetterSuffix())) {
            outcoll.add(new InfoBarItemVOB("Music User Call Letter/Names/Name",
                (programPerformanceVob.getMusicUserCallLetterSuffix() == null ? ""
                    : programPerformanceVob.getMusicUserCallLetterSuffix())
                    + " - "
                    + (programPerformanceVob.getMusicUserLastName() == null ? ""
                        : programPerformanceVob.getMusicUserLastName())
                    + "," + (programPerformanceVob.getMusicUserFirstName() == null ? ""
                        : programPerformanceVob.getMusicUserFirstName())));
        }

        if (StringUtils.isNotEmpty(programPerformanceVob.getMusicUserTypeCode())) {
            outcoll.add(new InfoBarItemVOB("Music User Type", programPerformanceVob.getMusicUserTypeCode() + " - "
                + programPerformanceVob.getMusicUserTypeDescription()));
        }

        if (StringUtils.isNotEmpty(programPerformanceVob.getPerformanceHeaderId())) {
            outcoll.add(new InfoBarItemVOB("Performance Header Id", programPerformanceVob.getPerformanceHeaderId()));
        }

        if (StringUtils.isNotEmpty(programPerformanceVob.getSeriesCode())) {
            outcoll.add(new InfoBarItemVOB("Series Code", programPerformanceVob.getSeriesCode()));
        }

        if (StringUtils.isNotEmpty(programPerformanceVob.getSeriesTitle())) {
            outcoll.add(new InfoBarItemVOB("Series Title", programPerformanceVob.getSeriesTitle()));
        }

        if (StringUtils.isNotEmpty(programPerformanceVob.getShowProgramCode())) {
            outcoll.add(new InfoBarItemVOB("Program Code", programPerformanceVob.getShowProgramCode()));
        }

        if (StringUtils.isNotEmpty(programPerformanceVob.getProgramTitle())) {
            outcoll.add(new InfoBarItemVOB("Program Title", programPerformanceVob.getProgramTitle()));
        }

        return outcoll;
    }

    public String retrieveWorkPerformancesList(ProgramPerformance programPerformance, HttpServletRequest request,
        Model m, RedirectAttributes redirectAttrbute) throws Exception {

        log.debug("Entering retrieveWorkPerformancesList method in ProgramPerformanceMultiAction");
        PerformanceSearch outWorkPerformancesList = null;
        outWorkPerformancesList = new PerformanceSearch();
        outWorkPerformancesList.setProgramPerformanceId(programPerformance.getPerformanceHeaderId());
        outWorkPerformancesList.setPerformanceSearchType("WORK_PERFORMANCE");
        outWorkPerformancesList.setActionSelected("SEARCH_WORK_PERFORMANCES");
        redirectAttrbute.addFlashAttribute("performanceSearch", outWorkPerformancesList);
        log.debug("Exiting retrieveWorkPerformancesList method in ProgramPerformanceMultiAction :"
            + programPerformance.getMusicUserInformationMode());
        return "redirect:/usageHomeSearch";

    }

}
