package com.ascap.apm.handler.usage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.handler.BaseHandler;
import com.ascap.apm.service.usage.UsageService;
import com.ascap.apm.vob.usage.ApmActiveSurveyQuarter;
import com.ascap.apm.vob.usage.ApmChannelList;
import com.ascap.apm.vob.usage.ApmFileList;
import com.ascap.apm.vob.usage.ApmFileUpload;
import com.ascap.apm.vob.usage.ApmLearnedMatch;
import com.ascap.apm.vob.usage.ApmLearnedMatchList;
import com.ascap.apm.vob.usage.ApmPerformanceBulkRequestList;
import com.ascap.apm.vob.usage.ApmWork;
import com.ascap.apm.vob.usage.CatalogSamplingSummary;
import com.ascap.apm.vob.usage.EOFileList;
import com.ascap.apm.vob.usage.EOLearnedMatch;
import com.ascap.apm.vob.usage.EOLearnedMatchList;
import com.ascap.apm.vob.usage.EOSupplierFormat;
import com.ascap.apm.vob.usage.Exemption;
import com.ascap.apm.vob.usage.ExemptionList;
import com.ascap.apm.vob.usage.LogRequestSummary;
import com.ascap.apm.vob.usage.LogUsageSummary;
import com.ascap.apm.vob.usage.MusicUserSearch;
import com.ascap.apm.vob.usage.PerformanceSearch;
import com.ascap.apm.vob.usage.ProgramPerformance;
import com.ascap.apm.vob.usage.SamplingRequest;
import com.ascap.apm.vob.usage.SamplingSummary;
import com.ascap.apm.vob.usage.WorkPerformance;
import com.ascap.apm.vob.usage.WorkPerformancesList;

/**
 * Session Bean implementation class UsageHandlerImpl
 */
@Service("usageHandler")
@Transactional(value = "ascapTxManager")
public class UsageHandlerImpl extends BaseHandler implements UsageHandler {

    private static final long serialVersionUID = -7005902747917440203L;

    private static final String ERROR_USAGE_DELETEPROGRAMPERFORMANCES_UNKNOWNEXCEPTION =
        "error.usage.deleteProgramPerformances.unknownexception";

    private static final String ERROR_DELETEWORKPERFORMANCESLIST =
        "error.usage.deleteWorkPerformancesList.unknownexception";

    private static final String ERROR_USAGE_COMMON_EXCEPTION = "error.usage.common.exception";

    @Autowired
    private UsageService usageService;

    /**
     * Method deleteWorkPerformance.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    public PREPContext deleteWorkPerformances(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering deleteWorkPerformances method in UsageHandlerImpl");
        // VObs and outputContext
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        PerformanceSearch performanceSearch = null;
        PerformanceSearch outperformanceSearch = null;
        List<Object> invalidWorkPeformances = new ArrayList<>();

        try {
            inputValueObjects = inputContext.getInputValueObjects();
            // checking the Input Parameters
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                performanceSearch = (PerformanceSearch) inputValueObjects.iterator().next();
                // Calling Usage Service
                outperformanceSearch = usageService.deleteWorkPerformances(performanceSearch);
                invalidWorkPeformances = outperformanceSearch.getInvalidWorkPerformances();
                outputContext.addOutputErrorObject(outperformanceSearch);
            }
            performanceSearch = (PerformanceSearch) inputContext.getUserSessionState().getSearch();

            // Calling Usage Service
            outperformanceSearch = usageService.searchWorkPerformances(performanceSearch);
            if (invalidWorkPeformances != null && !invalidWorkPeformances.isEmpty()) {
                outperformanceSearch.setInvalidWorkPerformances(invalidWorkPeformances);
                log.debug("+++++++++++++++++++++++++333" + outperformanceSearch.getInvalidWorkPerformances().size());
            }
            outputContext.addOutputValueObject(outperformanceSearch);
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.deleteWorkPerformances.unknownexception");
        }
        log.debug("Exiting deleteWorkPerformances method in UsageHandlerImpl");
        return outputContext;
    }

    /**
     * Method getProgramPerformance.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(readOnly = true, value = "ascapTxManager")
    public PREPContext getProgramPerformance(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering getProgramPerformance method in UsageHandlerImpl");
        // VObs and outputContext
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        String performanceId = null;
        String versionNumber = null;
        ProgramPerformance programPerformance = null;
        ProgramPerformance outProgramPerformance = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects();
            // checking the Input Parameters
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                programPerformance = (ProgramPerformance) inputValueObjects.iterator().next();
                performanceId = programPerformance.getPerformanceHeaderId();
                versionNumber = programPerformance.getVersionNumber();
                // Calling Usage Service
                if (versionNumber != null) {
                    if ("".equalsIgnoreCase(versionNumber.trim())) {
                        outProgramPerformance = usageService.getProgramPerformance(Long.parseLong(performanceId),
                            Integer.parseInt(versionNumber));
                    }
                } else {
                    outProgramPerformance = usageService.getProgramPerformance(Long.parseLong(performanceId));
                }
                outputContext.addOutputValueObject(outProgramPerformance);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.getProgramPerformance.unknownexception");
        }

        log.debug("Exiting getProgramPerformance method in UsageHandlerImpl");
        return outputContext;

    }

    /**
     * Method deleteProgramPerformances.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    public PREPContext deleteProgramPerformances(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering deleteProgramPerformances method in UsageHandlerImpl");
        // VObs and outputContext
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        PerformanceSearch deletePerformanceSearch = null;
        PerformanceSearch outperformanceSearch = null;
        PerformanceSearch performanceSearch = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects();
            // checking the Input Parameters
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                deletePerformanceSearch = (PerformanceSearch) inputValueObjects.iterator().next();
                // Calling Usage Service
                outperformanceSearch = usageService.deleteProgramPerformances(deletePerformanceSearch);

                outputContext.addOutputErrorObject(outperformanceSearch);
            }
            performanceSearch = (PerformanceSearch) inputContext.getUserSessionState().getSearch();

            outperformanceSearch = usageService.searchProgramPerformances(performanceSearch);

            outputContext.addOutputValueObject(outperformanceSearch);
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_DELETEPROGRAMPERFORMANCES_UNKNOWNEXCEPTION);
        }

        log.debug("Exiting deleteProgramPerformances method in UsageHandlerImpl");
        return outputContext;
    }

    public PREPContext updateAssignedUsers(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering updateAssignedUsers method in UsageHandlerImpl");
        // VObs and outputContext
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        PerformanceSearch updatedPerformanceSearch = null;
        PerformanceSearch outperformanceSearch = null;
        PerformanceSearch performanceSearch = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects();
            // checking the Input Parameters
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                updatedPerformanceSearch = (PerformanceSearch) inputValueObjects.iterator().next();
                outperformanceSearch = usageService.updateAssignedUsers(updatedPerformanceSearch);
                outputContext.addOutputErrorObject(outperformanceSearch);
            }
            performanceSearch = (PerformanceSearch) inputContext.getUserSessionState().getSearch();
            outperformanceSearch = usageService.searchProgramPerformances(performanceSearch);
            outputContext.addOutputValueObject(outperformanceSearch);
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_DELETEPROGRAMPERFORMANCES_UNKNOWNEXCEPTION);
        }

        log.debug("Exiting deleteProgramPerformances method in UsageHandlerImpl");
        return outputContext;
    }

    /**
     * Method searchProgramPerformances.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(readOnly = true, value = "ascapTxManager")
    public PREPContext searchProgramPerformances(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering searchProgramPerformances method in UsageHandlerImpl");
        // VObs and outputContext
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        PerformanceSearch performanceSearch = null;
        PerformanceSearch outperformanceSearch = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects();
            // checking the Input Parameters
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                performanceSearch = (PerformanceSearch) inputValueObjects.iterator().next();
                // Calling Usage Service
                outperformanceSearch = usageService.searchProgramPerformances(performanceSearch);
                outputContext.addOutputValueObject(outperformanceSearch);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.searchProgramPerformances.unknownexception");
        }

        log.debug("Exiting searchProgramPerformances method in UsageHandlerImpl");
        return outputContext;
    }

    /**
     * Method searchWorkPerformances.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(readOnly = true, value = "ascapTxManager")
    public PREPContext searchWorkPerformances(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering searchWorkPerformances method in UsageHandlerImpl");
        // VObs and outputContext
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        PerformanceSearch performanceSearch = null;
        PerformanceSearch outperformanceSearch = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            // checking the Input Parameters
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                performanceSearch = (PerformanceSearch) inputValueObjects.iterator().next();
                // Calling Usage Service
                outperformanceSearch = usageService.searchWorkPerformances(performanceSearch);

                outputContext.addOutputValueObject(outperformanceSearch);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.searchWorkPerformances.unknownexception");
        }
        log.debug("Exiting searchWorkPerformances method in UsageHandlerImpl");
        return outputContext;
    }

    /**
     * Method getWorkPerformance.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(readOnly = true, value = "ascapTxManager")
    public PREPContext getWorkPerformance(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getWorkPerformance method in UsageHandlerImpl");
        /* VObs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        WorkPerformance workPerformance = null;
        WorkPerformance outWorkPerformance = null;
        String performanceId = null;
        String versionNumber = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects();
            /* checking the Input Parameters */
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                workPerformance = (WorkPerformance) inputValueObjects.iterator().next();
                performanceId = workPerformance.getWorkPerformanceId();
                versionNumber = workPerformance.getVersionNumber();
                // Calling Usage Service
                if (versionNumber != null) {
                    if ("".equalsIgnoreCase(versionNumber.trim())) {
                        outWorkPerformance = usageService.getWorkPerformance(Long.parseLong(performanceId),
                            Integer.parseInt(versionNumber));
                    }
                } else {
                    outWorkPerformance = usageService.getWorkPerformance(Long.parseLong(performanceId));
                }
                outputContext.addOutputValueObject(outWorkPerformance);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.getWorkPerformance.unknownexception");
        }
        log.debug("Exiting getWorkPerformance method in UsageHandlerImpl");
        return outputContext;
    }

    public PREPContext updateProgramPerformance(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering updateProgramPerformance method in UsageHandlerImpl");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ProgramPerformance programPerformance = null;
        ProgramPerformance outProgramPerformanceVob = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects(); /* checking the Input Parameters */
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                programPerformance = (ProgramPerformance) inputValueObjects.iterator().next();
                // Actual service Methods to Call usageService
                outProgramPerformanceVob = usageService.updateProgramPerformance(programPerformance);
                outputContext.addOutputValueObject(outProgramPerformanceVob);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.updateProgramPerformance.unknownexception");
        }
        log.debug("Exiting updateProgramPerformance method in UsageHandlerImpl");
        return outputContext;
    }

    /**
     * Method getPerformanceMusicUserInfo.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(readOnly = true, value = "ascapTxManager")
    public PREPContext getPerformanceMusicUserInfo(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getPerformanceMusicUserInfo method in UsageHandlerImpl");

        // VObs and outputContext
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ProgramPerformance programPerformance = null;
        ProgramPerformance outProgramPerformance = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            // checking the Input Parameters
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                programPerformance = (ProgramPerformance) inputValueObjects.iterator().next();
                // get The Services Required
                outProgramPerformance = usageService.getPerformanceMusicUserInfo(programPerformance);
                outputContext.addOutputValueObject(outProgramPerformance);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage());
            throw new PrepSystemException("error.usage.getPerformanceMusicUserInfo.unknownexception");
        }
        log.debug("Exiting getPerformanceMusicUserInfo method in UsageHandlerImpl");
        return outputContext;
    }

    /**
     * Method addProgramPerformance.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    public PREPContext addProgramPerformance(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering addProgramPerformance method in UsageHandlerImpl");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ProgramPerformance programPerformance = null;
        ProgramPerformance outProgramPerformanceVob = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects(); /* checking the Input Parameters */
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                programPerformance = (ProgramPerformance) inputValueObjects.iterator().next();
                // Actual service Methods to Call usageService
                outProgramPerformanceVob = usageService.addProgramPerformance(programPerformance);
                outputContext.addOutputValueObject(outProgramPerformanceVob);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage());
            throw new PrepSystemException("error.usage.addProgramPerformance.unknownexception");
        }
        log.debug("Exiting addProgramPerformance method in UsageHandlerImpl");
        return outputContext;
    }

    /**
     * Method getChannelList.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(value = "eoDSTxManager", readOnly = true)
    public PREPContext getChannelList(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering getChannelList method in UsageHandlerImpl");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmChannelList apmChannelList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmChannelList = (ApmChannelList) inputValueObjects.iterator().next();
                apmChannelList = usageService.getChannelList(apmChannelList);
                outputContext.addOutputValueObject(apmChannelList);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        log.debug("Exiting getChannelList method in UsageHandlerImpl");

        return outputContext;

    }

    /**
     * Method updateChannelList.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(value = "eoDSTxManager")
    public PREPContext updateChannelList(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering updateChannelList method in UsageHandlerImpl");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmChannelList apmChannelList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmChannelList = (ApmChannelList) inputValueObjects.iterator().next();
                apmChannelList = usageService.updateChannelList(apmChannelList);
                outputContext.addOutputValueObject(apmChannelList);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting updateChannelList method in UsageHandlerImpl");
        return outputContext;
    }

    public PREPContext getCatalogSamplingSummary(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        if (log.isDebugEnabled()) {
            log.debug("Entering UsageHandletImpl - getCatalogSamplingSummary method ");
        }
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        CatalogSamplingSummary samplingSummary = null;
        CatalogSamplingSummary outSamplingDetails = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                samplingSummary = (CatalogSamplingSummary) inputValueObjects.iterator().next();
                outSamplingDetails = usageService.getCatalogSamplingSummary(samplingSummary);
                outputContext.addOutputValueObject(outSamplingDetails);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            if (log.isDebugEnabled()) {
                log.error(e.getMessage());
            }
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        if (log.isDebugEnabled()) {
            log.debug("Exiting UsageHandlerImpl - getCatalogSamplingSummary method ");
        }
        return outputContext;
    }

    public PREPContext deleteWorkPerformancesList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering deleteWorkPerformancesList method in UsageHandlerImpl");

        /* Vobs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;

        WorkPerformancesList workPerformancesList = null;
        PerformanceSearch workPerformancesToDelete = null;
        PerformanceSearch outWorkPerformancesToDelete = null;
        WorkPerformancesList outworkPerformancesList = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects();
            /* checking the Input Parameters */
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                workPerformancesList = (WorkPerformancesList) inputValueObjects.iterator().next();
                workPerformancesToDelete = new PerformanceSearch();
                workPerformancesToDelete.setSelectedIds(workPerformancesList.getSelectedIds());
                workPerformancesToDelete.setUserId(workPerformancesList.getUserId());
                workPerformancesToDelete.setDeleteType(workPerformancesList.getDeleteType());
                /* get The Services Required */

                /* Actual service Methods to Call usageService */
                outWorkPerformancesToDelete = usageService.deleteWorkPerformances(workPerformancesToDelete);

                outputContext.addOutputErrorObject(outWorkPerformancesToDelete);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_DELETEWORKPERFORMANCESLIST);
        }

        try {
            inputValueObjects = inputContext.getInputValueObjects();
            /* checking the Input Parameters */
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                workPerformancesList = (WorkPerformancesList) inputValueObjects.iterator().next();
                /* get The Services Required */

                /* Actual service Methods to Call usageService */
                outworkPerformancesList = usageService.getWorkPerformancesList(workPerformancesList);
                if (outWorkPerformancesToDelete != null)
                    outworkPerformancesList
                        .setInvalidWorkPerformances(outWorkPerformancesToDelete.getInvalidWorkPerformances());
                outputContext.addOutputValueObject(outworkPerformancesList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_DELETEWORKPERFORMANCESLIST);
        }
        return outputContext;
    }

    public PREPContext updateWorkPerformancesBulk(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering updateWorkPerformancesBulk method in UsageHandlerImpl");
        /* Vobs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;

        WorkPerformancesList workPerformancesList = null;
        PerformanceSearch workPerformancesToDelete = null;
        WorkPerformancesList outworkPerformancesList = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects();
            /* checking the Input Parameters */
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                workPerformancesList = (WorkPerformancesList) inputValueObjects.iterator().next();
                workPerformancesToDelete = new PerformanceSearch();
                workPerformancesToDelete.setSelectedIds(workPerformancesList.getSelectedIds());
                workPerformancesToDelete.setUserId(workPerformancesList.getUserId());

                usageService.updateWorkPerformancesBulk(workPerformancesList);

            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_DELETEWORKPERFORMANCESLIST);
        }

        try {
            inputValueObjects = inputContext.getInputValueObjects();
            /* checking the Input Parameters */
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                workPerformancesList = (WorkPerformancesList) inputValueObjects.iterator().next();
                outworkPerformancesList = usageService.getWorkPerformancesList(workPerformancesList);
                outputContext.addOutputValueObject(outworkPerformancesList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_DELETEWORKPERFORMANCESLIST);
        }
        log.debug("Exiting updateWorkPerformancesBulk method in UsageHandlerImpl");
        return outputContext;
    }

    public PREPContext addWorkPerformance(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering addWorkPerformance method in UsageHandlerImpl");

        /* VObs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;

        WorkPerformance workPerformance = null;
        WorkPerformance outWorkPerformance = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects();
            /* checking the Input Parameters */
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                workPerformance = (WorkPerformance) inputValueObjects.iterator().next();
                outWorkPerformance = usageService.addWorkPerformance(workPerformance);
                outputContext.addOutputValueObject(outWorkPerformance);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.addWorkPerformance.unknownexception");
        }
        log.debug("Exiting addWorkPerformance method in UsageHandlerImpl");
        return outputContext;
    }

    public PREPContext updateWorkPerformance(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering updateWorkPerformance method in UsageHandlerImpl");
        /* VObs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        WorkPerformance workPerformance = null;
        WorkPerformance outWorkPerformance = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                workPerformance = (WorkPerformance) inputValueObjects.iterator().next();
                outWorkPerformance = usageService.updateWorkPerformance(workPerformance);
                outputContext.addOutputValueObject(outWorkPerformance);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.updateWorkPerformance.unknownexception");
        }
        log.debug("Exiting updateWorkPerformance method in UsageHandlerImpl");
        return outputContext;
    }

    public PREPContext getWorkPerformancesList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getWorkPerformancesList method in UsageHandlerImpl");
        /* Vobs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        WorkPerformancesList workPerformancesList = null;
        WorkPerformancesList outworkPerformancesList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            /* checking the Input Parameters */
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                workPerformancesList = (WorkPerformancesList) inputValueObjects.iterator().next();
                /* get The Services Required */

                /* Actual service Methods to Call usageService */
                outworkPerformancesList = usageService.getWorkPerformancesList(workPerformancesList);
                outputContext.addOutputValueObject(outworkPerformancesList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.getWorkPerformancesList.unknownexception");
        }
        return outputContext;
    }

    public PREPContext addToMedleyWorkPerformance(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering addToMedleyWorkPerformance method in UsageHandlerImpl");
        /* Vobs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;

        WorkPerformancesList workPerformancesList = null;
        WorkPerformancesList outworkPerformancesList = null;

        WorkPerformancesList medleyInputworkPerformancesList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            /* checking the Input Parameters */
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                medleyInputworkPerformancesList = (WorkPerformancesList) inputValueObjects.iterator().next();
                /* get The Services Required */

                /* Actual service Methods to Call usageService */
                usageService.addToMedleyWorkPerformance(medleyInputworkPerformancesList);
                outputContext.addOutputErrorObject(outworkPerformancesList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.addToMedleyWorkPerformance.unknownexception");
        }

        try {
            inputValueObjects = inputContext.getInputValueObjects();
            /* checking the Input Parameters */
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                workPerformancesList = (WorkPerformancesList) inputValueObjects.iterator().next();
                /* get The Services Required */

                /* Actual service Methods to Call usageService */
                outworkPerformancesList = usageService.getWorkPerformancesList(workPerformancesList);
                outputContext.addOutputValueObject(outworkPerformancesList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.addToMedleyWorkPerformance.unknownexception");
        }

        return outputContext;
    }

    public PREPContext removeFromMedleyWorkPerformance(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering removeFromMedleyWorkPerformance method in UsageHandlerImpl");

        /* Vobs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;

        WorkPerformancesList workPerformancesList = null;
        WorkPerformancesList outworkPerformancesList = null;

        WorkPerformancesList medleyInputworkPerformancesList = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects();
            /* checking the Input Parameters */
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                medleyInputworkPerformancesList = (WorkPerformancesList) inputValueObjects.iterator().next();

                usageService.removeFromMedleyWorkPerformance(medleyInputworkPerformancesList);
                outputContext.addOutputErrorObject(outworkPerformancesList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.removeFromMedleyWorkPerformance.unknownexception");
        }

        try {
            inputValueObjects = inputContext.getInputValueObjects();
            /* checking the Input Parameters */
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                workPerformancesList = (WorkPerformancesList) inputValueObjects.iterator().next();
                /* get The Services Required */

                /* Actual service Methods to Call usageService */
                outworkPerformancesList = usageService.getWorkPerformancesList(workPerformancesList);
                outputContext.addOutputValueObject(outworkPerformancesList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.usage.removeFromMedleyWorkPerformance.unknownexception");
        }

        return outputContext;
    }

    /**
     * Method getCatalogManualMatchList.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(value = "ascapTxManager", readOnly = true)
    public PREPContext getCatalogManualMatchList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        if (log.isDebugEnabled()) {
            log.debug("Entering getCatalogManualMatchList method in UsageHandlerImpl");
        }

        /* VObs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmPerformanceBulkRequestList apmFileList = null;
        ApmPerformanceBulkRequestList outsampleDateListVob = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmFileList = (ApmPerformanceBulkRequestList) inputValueObjects.iterator().next();
                outsampleDateListVob = usageService.getCatalogManualMatchList(apmFileList);
                outputContext.addOutputValueObject(outsampleDateListVob);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        if (log.isDebugEnabled()) {
            log.debug("Exiting getCatalogManualMatchList method in UsageHandlerImpl");
        }
        return outputContext;
    }

    /**
     * Method updateCatalogManualMatchList.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    public PREPContext updateCatalogManualMatchList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        if (log.isDebugEnabled()) {
            log.debug("Entering updateCatalogManualMatchList method in UsageHandlerImpl");
        }

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmPerformanceBulkRequestList apmFileList = null;
        ApmPerformanceBulkRequestList outsampleDateListVob = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmFileList = (ApmPerformanceBulkRequestList) inputValueObjects.iterator().next();
                outsampleDateListVob = usageService.updateCatalogManualMatchList(apmFileList);
                outputContext.addOutputValueObject(outsampleDateListVob);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting updateCatalogManualMatchList method in UsageHandlerImpl");
        return outputContext;

    }

    public PREPContext cloneCatalogPerformances(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering cloneCatalogPerformances method in UsageHandlerImpl");
        /* VObs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmPerformanceBulkRequestList apmFileList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmFileList = (ApmPerformanceBulkRequestList) inputValueObjects.iterator().next();
                apmFileList = usageService.cloneCatalogPerformances(apmFileList);
                outputContext.addOutputValueObject(apmFileList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting cloneCatalogPerformances method in UsageHandlerImpl");
        return outputContext;

    }

    public PREPContext loadToAPM(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHandlerImpl - loadToAPM method ");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        EOFileList eoFileList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {

                eoFileList = (EOFileList) inputValueObjects.iterator().next();

                eoFileList = usageService.loadToAPM(eoFileList);

                outputContext.addOutputValueObject(eoFileList);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        log.debug("Exiting UsageHandlerImpl - loadToAPM method ");

        return outputContext;

    }

    public PREPContext getApmSuppliers(PREPContext inputContext) throws PrepSystemException {
        log.debug("Entering UsageHandlerImpl - getApmSuppliers method ");

        PREPContext outputContext = new PREPContext();
        try {
            List<EOSupplierFormat> col = usageService.getApmSuppliers();
            outputContext.addOutputValueObject(col);
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        log.debug("Exiting UsageHandlerImpl - getApmSuppliers method ");

        return outputContext;

    }

    public PREPContext getApmActiveSurveyQuarter(PREPContext inputContext) throws PrepSystemException {
        log.debug("Entering UsageHandlerImpl - getApmActiveSurveyQuarter method ");

        PREPContext outputContext = new PREPContext();
        ApmActiveSurveyQuarter apmActiveSurveyQuarter = null;
        try {
            apmActiveSurveyQuarter = usageService.getApmActiveSurveyQuarter();
            outputContext.addOutputValueObject(apmActiveSurveyQuarter);
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        log.debug("Exiting UsageHandlerImpl - getApmActiveSurveyQuarter method ");

        return outputContext;

    }

    @Transactional("eoDSTxManager")
    public PREPContext getEOFileList(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHandlerImpl - getEOFileList method ");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        EOFileList eoFileList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {

                eoFileList = (EOFileList) inputValueObjects.iterator().next();

                eoFileList = usageService.getEOFileList(eoFileList);

                outputContext.addOutputValueObject(eoFileList);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        log.debug("Exiting UsageHandlerImpl - getEOFileList method ");

        return outputContext;
    }

    public PREPContext getRollupThreshold(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering UsageHandlerImpl - getRollupThreshold method ");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        EOFileList eoFileList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {

                eoFileList = (EOFileList) inputValueObjects.iterator().next();

                String rollupThreshold = usageService.getRollupThreshold();
                eoFileList.setRollupThreshold(rollupThreshold);

                outputContext.addOutputValueObject(eoFileList);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        log.debug("Exiting UsageHandlerImpl - getRollupThreshold method ");

        return outputContext;
    }

    public PREPContext updateFileInventory(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHandlerImpl - updateFileInventory method ");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        EOFileList eoFileList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {

                eoFileList = (EOFileList) inputValueObjects.iterator().next();

                eoFileList = usageService.updateFileInventory(eoFileList);

                outputContext.addOutputValueObject(eoFileList);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        if (log.isDebugEnabled()) {
            log.debug("Exiting UsageHandlerImpl - updateFileInventory method ");
        }

        return outputContext;

    }

    public PREPContext loadToEO(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHandlerImpl - loadToEO method ");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        EOFileList eoFileList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {

                eoFileList = (EOFileList) inputValueObjects.iterator().next();

                eoFileList = usageService.loadToEO(eoFileList);

                outputContext.addOutputValueObject(eoFileList);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("Error", e);
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        log.debug("Exiting UsageHandlerImpl - loadToEO method ");

        return outputContext;

    }

    public PREPContext getLogRequestSummary(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHandlerImpl - getLogRequestSummary method ");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;

        LogRequestSummary inputLogRequestSummary = null;
        LogRequestSummary outputLogRequestSummary = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                inputLogRequestSummary = (LogRequestSummary) inputValueObjects.iterator().next();
                outputLogRequestSummary = usageService.getLogRequestSummary(inputLogRequestSummary);
                outputContext.addOutputValueObject(outputLogRequestSummary);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        log.debug("Exiting UsageHandlerImpl - getLogRequestSummary method ");

        return outputContext;
    }

    public PREPContext updateLogRequestSummary(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHandlerImpl - updateLogRequestSummary method ");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;

        LogRequestSummary inputLogRequestSummary = null;
        LogRequestSummary outputLogRequestSummary = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                inputLogRequestSummary = (LogRequestSummary) inputValueObjects.iterator().next();
                outputLogRequestSummary = usageService.updateLogRequestSummary(inputLogRequestSummary);
                outputContext.addOutputValueObject(outputLogRequestSummary);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        if (log.isDebugEnabled()) {
            log.debug("Exiting UsageHandlerImpl - updateLogRequestSummary method ");
        }

        return outputContext;

    }

    /**
     * Method getSamplingSummary.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(value = "ascapTxManager")
    public PREPContext getSamplingSummary(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering getSamplingSummary method in UsageHandlerImpl ");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        SamplingSummary samplingSummary = null;
        SamplingSummary outSamplingDetails = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                samplingSummary = (SamplingSummary) inputValueObjects.iterator().next();
                outSamplingDetails = usageService.getSamplingSummary(samplingSummary);
                outputContext.addOutputValueObject(outSamplingDetails);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting getSamplingSummary method in UsageHandlerImpl ");
        return outputContext;

    }

    public PREPContext getApmPerfBulkRequestList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getApmPerfBulkRequestList method in UsageHandlerImpl");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmPerformanceBulkRequestList apmFileList = null;
        ApmPerformanceBulkRequestList outsampleDateListVob = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmFileList = (ApmPerformanceBulkRequestList) inputValueObjects.iterator().next();
                outsampleDateListVob = usageService.getApmPerfBulkRequestList(apmFileList);
                outputContext.addOutputValueObject(outsampleDateListVob);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting getApmPerfBulkRequestList method in UsageHandlerImpl");
        return outputContext;
    }

    public PREPContext seacrhFiles(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        if (log.isDebugEnabled()) {
            log.debug("Entering seacrhFiles method in UsageHandlerImpl");
        }
        /* VObs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmFileList apmFileList = null;
        ApmFileList outsampleDateListVob = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            /* checking the Input Parameters */
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmFileList = (ApmFileList) inputValueObjects.iterator().next();
                /* get The Services Required */
                if (apmFileList == null) {
                    log.debug("NULL FILE INSISDE CONTROLLER");
                }
                /* Actual service Methods to Call usageService */
                outsampleDateListVob = usageService.seacrhFiles(apmFileList);
                outputContext.addOutputValueObject(outsampleDateListVob);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting seacrhFiles method in UsageHandlerImpl");
        return outputContext;
    }

    /**
     * @see com.ascap.apm.controller.usage.UsageControllerLocalInterface#seacrhParty(PREPContext)
     */
    public PREPContext seacrhParty(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering UsageHandlerImpl - searchParty method");
        PREPContext outputContext = new PREPContext();
        try {
            List<Object> inputValueObjects = inputContext.getInputValueObjects();
            MusicUserSearch musicUserSearch = null;
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                musicUserSearch = (MusicUserSearch) inputValueObjects.iterator().next();
            }
            musicUserSearch = usageService.searchMusicUser(musicUserSearch);
            outputContext.addOutputValueObject(musicUserSearch);
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("error.membership.exception");
        }
        log.debug("Exiting UsageHandlerImpl - searchParty method");
        return outputContext;
    }

    public PREPContext getLogUsageSummary(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering UsageHandlerImpl - getLogUsageSummary method ");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;

        LogUsageSummary inputLogUsageSummary = null;
        LogUsageSummary outputLogUsageSummary = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                inputLogUsageSummary = (LogUsageSummary) inputValueObjects.iterator().next();
                outputLogUsageSummary = usageService.getLogUsageSummary(inputLogUsageSummary);
                outputContext.addOutputValueObject(outputLogUsageSummary);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting UsageHandlerImpl - getLogUsageSummary method ");
        return outputContext;
    }

    public PREPContext insertLogUsageSummary(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHandlerImpl - insertLogUsageSummary method ");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;

        LogUsageSummary inputLogUsageSummary = null;
        LogUsageSummary outputLogUsageSummary = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                inputLogUsageSummary = (LogUsageSummary) inputValueObjects.iterator().next();
                outputLogUsageSummary = usageService.insertLogUsageSummary(inputLogUsageSummary);
                outputContext.addOutputValueObject(outputLogUsageSummary);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        log.debug("Exiting UsageHandlerImpl - insertLogUsageSummary method ");
        return outputContext;
    }

    public PREPContext updateLogUsageSummary(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHandlerImpl - updateLogUsageSummary method ");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;

        LogUsageSummary inputLogUsageSummary = null;
        LogUsageSummary outputLogUsageSummary = null;

        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                inputLogUsageSummary = (LogUsageSummary) inputValueObjects.iterator().next();
                outputLogUsageSummary = usageService.updateLogUsageSummary(inputLogUsageSummary);
                outputContext.addOutputValueObject(outputLogUsageSummary);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        log.debug("Exiting UsageHandlerImpl - updateLogUsageSummary method ");
        return outputContext;
    }

    /**
     * Method getExemptionList.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(value = "ascapTxManager", readOnly = true)
    public PREPContext getExemptionList(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering getExemptionList method in UsageHandlerImpl");
        /* VObs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ExemptionList exemptionList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                exemptionList = (ExemptionList) inputValueObjects.iterator().next();
                exemptionList = usageService.getExemptionList(exemptionList);
                outputContext.addOutputValueObject(exemptionList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.error(pfe.getMessage());
            throw new PrepFunctionalException(pfe.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting getExemptionList method in UsageHandlerImpl ");
        return outputContext;
    }

    /**
     * Method addExemption.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(value = "ascapTxManager")
    public PREPContext addExemption(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering addExemption method in UsageHandlerImpl");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        Exemption exemption = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                exemption = (Exemption) inputValueObjects.iterator().next();
                exemption = usageService.addExemption(exemption);
                outputContext.addOutputValueObject(exemption);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.error(pfe.getMessage());
            throw new PrepFunctionalException(pfe.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting addExemption method in UsageHandlerImpl");
        return outputContext;
    }

    /**
     * Method getExemption.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(value = "ascapTxManager", readOnly = true)
    public PREPContext getExemption(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getExemption method in UsageHandlerImpl");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        Exemption exemption = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                exemption = (Exemption) inputValueObjects.iterator().next();
                exemption = usageService.getExemption(exemption);
                outputContext.addOutputValueObject(exemption);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw new PrepFunctionalException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting getExemption method in UsageHandlerImpl");
        return outputContext;
    }

    /**
     * Method updateExemption.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(value = "ascapTxManager")
    public PREPContext updateExemption(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering updateExemption method in UsageHandlerImpl");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        Exemption exemption = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                exemption = (Exemption) inputValueObjects.iterator().next();
                exemption = usageService.updateExemption(exemption);
                outputContext.addOutputValueObject(exemption);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.error(pfe.getMessage());
            throw new PrepFunctionalException(pfe.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting updateExemption method in UsageHandlerImpl");
        return outputContext;
    }

    /**
     * Method deleteExemptions.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(value = "ascapTxManager")
    public PREPContext deleteExemptions(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering deleteExemptions method in UsageHandlerImpl");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ExemptionList exemptionList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                exemptionList = (ExemptionList) inputValueObjects.iterator().next();
                exemptionList = usageService.deleteExemptions(exemptionList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.error(pfe.getMessage());
            throw new PrepFunctionalException(pfe.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                exemptionList = usageService.getExemptionList(exemptionList);
                outputContext.addOutputValueObject(exemptionList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw new PrepFunctionalException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting deleteExemptions method in UsageHandlerImpl");
        return outputContext;
    }

    /**
     * Method cancelSampling.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(value = "ascapTxManager")
    public PREPContext cancelSampling(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering cancelSampling method in UsageHandlerImpl ");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        SamplingSummary samplingSummary = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                samplingSummary = (SamplingSummary) inputValueObjects.iterator().next();
                samplingSummary = usageService.cancelSampling(samplingSummary);
                outputContext.addOutputValueObject(samplingSummary);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (PrepFunctionalException pfe) {
            log.error(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting cancelSampling method in UsageHandlerImpl ");
        return outputContext;
    }

    /**
     * Method getSamplingDetails.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(value = "ascapTxManager")
    public PREPContext getSamplingDetails(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering getSamplingDetails method in UsageHandlerImpl ");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        SamplingSummary samplingSummary = null;
        SamplingSummary outSamplingDetails = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                samplingSummary = (SamplingSummary) inputValueObjects.iterator().next();
                outSamplingDetails = usageService.getSamplingDetails(samplingSummary);
                outputContext.addOutputValueObject(outSamplingDetails);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (PrepFunctionalException pfe) {
            log.error(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting getSamplingDetails method in UsageHandlerImpl ");
        return outputContext;
    }

    /**
     * Method updateSamplingSummary.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(value = "ascapTxManager")
    public PREPContext updateSamplingSummary(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering updateSamplingSummary method in UsageHandlerImpl ");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        SamplingRequest samplingRequest = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                samplingRequest = (SamplingRequest) inputValueObjects.iterator().next();
                samplingRequest = usageService.updateSamplingSummary(samplingRequest);
                outputContext.addOutputValueObject(samplingRequest);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting updateSamplingSummary method in UsageHandlerImpl ");
        return outputContext;
    }

    /**
     * Method addSamplingRequest.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(value = "ascapTxManager")
    public PREPContext addSamplingRequest(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering addSamplingRequest method in UsageHandlerImpl ");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        SamplingRequest samplingRequest = null;
        SamplingSummary samplingSummary = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                samplingSummary = (SamplingSummary) inputValueObjects.iterator().next();
                samplingRequest = new SamplingRequest();
                samplingRequest.setUserId(samplingSummary.getUserId());
                samplingRequest.setCallLetter(samplingSummary.getCallLetter());
                samplingRequest.setTargetSurveyYearQuarter(samplingSummary.getTargetSurveyYearQuarter());
                samplingRequest.setStepCode(samplingSummary.getStepNumber());
                samplingRequest
                    .setNumberOfPerformancesToBeSampled(samplingSummary.getNumberOfPerformancesToBeSampled());
                samplingRequest = usageService.addSamplingRequest(samplingRequest);
                log.debug("Created New Sapling Requuest: " + samplingRequest);
                samplingSummary = usageService.updateSamplingResults(samplingSummary);
                samplingRequest = usageService.updateSamplingSummary(samplingRequest);
                outputContext.addOutputValueObject(samplingSummary);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting addSamplingRequest method in UsageHandlerImpl ");
        return outputContext;
    }

    /**
     * Method bypassSampling.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws RemoteException
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    @Transactional(value = "ascapTxManager")
    public PREPContext bypassSampling(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering bypassSampling method in UsageHandlerImpl ");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        SamplingRequest samplingRequest = null;
        SamplingSummary samplingSummary = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                samplingSummary = (SamplingSummary) inputValueObjects.iterator().next();
                samplingRequest = new SamplingRequest();
                samplingRequest.setUserId(samplingSummary.getUserId());
                samplingRequest.setCallLetter(samplingSummary.getCallLetter());
                samplingRequest.setTargetSurveyYearQuarter(samplingSummary.getTargetSurveyYearQuarter());
                samplingRequest.setStepCode(samplingSummary.getStepNumber());
                samplingRequest
                    .setNumberOfPerformancesToBeSampled(samplingSummary.getNumberOfPerformancesToBeSampled());
                log.debug("Created New Sapling Requuest: " + samplingRequest);
                samplingRequest = usageService.bypassSampling(samplingRequest);
                outputContext.addOutputValueObject(samplingRequest);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting bypassSampling method in UsageHandlerImpl ");
        return outputContext;
    }

    public PREPContext cloneWorkPerformances(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering cloneWorkPerformances method in UsageHandlerImpl");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmPerformanceBulkRequestList apmFileList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmFileList = (ApmPerformanceBulkRequestList) inputValueObjects.iterator().next();
                apmFileList = usageService.cloneWorkPerformances(apmFileList);
                outputContext.addOutputValueObject(apmFileList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting cloneWorkPerformances method in UsageHandlerImpl");
        return outputContext;
    }

    /**
     * Method updateAssignedUsersToWorkPerf.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    public PREPContext updateAssignedUsersToWorkPerf(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering updateAssignedUsersToWorkPerf method in UsageHeandlerImpl");
        List<Object> inputValueObjects = null;
        ApmPerformanceBulkRequestList apmFileList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmFileList = (ApmPerformanceBulkRequestList) inputValueObjects.iterator().next();
                usageService.updateAssignedUsersToWorkPerf(apmFileList);

            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting updateAssignedUsersToWorkPerf method in UsageHandlerImpl");
        return inputContext;
    }

    /**
     * Method getMedleyWorkInformation.
     * 
     * @param inputContext
     * @return PREPContext
     * @throws PrepSystemException
     * @throws PrepFunctionalException
     */
    public PREPContext getMedleyWorkInformation(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getMedleyWorkInformation method in UsageHeandlerImpl");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmPerformanceBulkRequestList apmFileList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmFileList = (ApmPerformanceBulkRequestList) inputValueObjects.iterator().next();
                apmFileList = usageService.getMedleyWorkInformation(apmFileList);
                outputContext.addOutputValueObject(apmFileList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting getMedleyWorkInformation method in UsageHandlerImpl");
        return outputContext;
    }

    public PREPContext updateApmPerfBulkRequestList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering updateApmPerfBulkRequestList method in UsageHendlerImpl");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmPerformanceBulkRequestList apmFileList = null;
        ApmPerformanceBulkRequestList outsampleDateListVob = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmFileList = (ApmPerformanceBulkRequestList) inputValueObjects.iterator().next();
                outsampleDateListVob = usageService.updateApmPerfBulkRequestList(apmFileList);
                outputContext.addOutputValueObject(outsampleDateListVob);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting updateApmPerfBulkRequestList method in UsageHeandlerImpl");
        return outputContext;
    }

    @Transactional("eoDSTxManager")
    public PREPContext deleteEOLearnedMatchList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering deleteEOLearnedMatchList method in UsageHandlerImpl");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        EOLearnedMatchList apmLearnedMatchList = null;
        EOLearnedMatchList outApmLearnedMatchList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmLearnedMatchList = (EOLearnedMatchList) inputValueObjects.iterator().next();
                outApmLearnedMatchList = usageService.deleteEOLearnedMatchList(apmLearnedMatchList);
                outputContext.addOutputValueObject(outApmLearnedMatchList);
            }
        } catch (PrepFunctionalException | PrepSystemException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting deleteEOLearnedMatchList method in UsageHandlerImpl");
        return outputContext;
    }

    @Transactional("eoDSTxManager")
    public PREPContext getEOLearnedMatchList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering getEOLearnedMatchList method in UsageHandlerImpl");
        /* VObs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        EOLearnedMatchList eoLearnedMatchList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                eoLearnedMatchList = (EOLearnedMatchList) inputValueObjects.iterator().next();
                eoLearnedMatchList = usageService.getEOLearnedMatchList(eoLearnedMatchList);
                outputContext.addOutputValueObject(eoLearnedMatchList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw new PrepFunctionalException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting getEOLearnedMatchList method in UsageHandlerImpl");
        return outputContext;

    }

    @SuppressWarnings("unchecked")
    public PREPContext validateApmWorks(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHandlerImpl - validateApmWorks method ");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        List<ApmWork> col = null;
        List<ApmWork> outCol = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {

                col = (List<ApmWork>) inputValueObjects.iterator().next();
                outCol = usageService.validateApmWorks(col);
                outputContext.addOutputValueObject(outCol);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        log.debug("Exiting UsageHandlerImpl - validateApmWorks method ");
        return outputContext;
    }

    @Transactional("eoDSTxManager")
    public PREPContext updateEOLearnedMatchList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering UsageHandlerImpl - updateEOLearnedMatchList method ");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        EOLearnedMatchList eoLearnedMatchList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {

                eoLearnedMatchList = (EOLearnedMatchList) inputValueObjects.iterator().next();
                eoLearnedMatchList = usageService.updateEOLearnedMatchList(eoLearnedMatchList);
                outputContext.addOutputValueObject(eoLearnedMatchList);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        if (log.isDebugEnabled()) {
            log.debug("Exiting UsageHandlerImpl - updateEOLearnedMatchList method ");
        }

        return outputContext;
    }

    @Transactional("eoDSTxManager")
    public PREPContext getEOLearnedMatchMedleyWorkInformation(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {

        log.debug("Entering UsageHandlerImpl - getEOLearnedMatchMedleyWorkInformation method ");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        EOLearnedMatchList eoLearnedMatchList = null;
        EOLearnedMatch eoLearnedMatch = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {

                eoLearnedMatchList = (EOLearnedMatchList) inputValueObjects.iterator().next();
                eoLearnedMatch = usageService.getEOLearnedMatchMedleyWorkInformation(eoLearnedMatchList);
                outputContext.addOutputValueObject(eoLearnedMatch);
            }
        } catch (PrepSystemException | PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        if (log.isDebugEnabled()) {
            log.debug("Exiting UsageHandlerImpl - getEOLearnedMatchMedleyWorkInformation method ");
        }

        return outputContext;
    }

    @Transactional("eoDSTxManager")
    public PREPContext getMultWorkIdSequence(PREPContext inputContext) throws PrepSystemException {
        log.debug("Entering getMultWorkIdSequence method in UsageHandlerImpl");
        PREPContext outputContext = new PREPContext();
        String multWorkId = null;
        try {

            multWorkId = usageService.getMultWorkIdSequence();
            outputContext.addOutputValueObject(multWorkId);

        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        log.debug("Exiting getMultWorkIdSequence method in UsageHandlerImpl");
        return outputContext;
    }

    @Transactional("eoDSTxManager")
    public PREPContext addEOLearnedMatch(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering addEOLearnedMatch method in UsageHandlerImpl");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        EOLearnedMatch apmLearnedMatch = null;
        EOLearnedMatch outApmLearnedMatch = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmLearnedMatch = (EOLearnedMatch) inputValueObjects.iterator().next();
                outApmLearnedMatch = usageService.addEOLearnedMatch(apmLearnedMatch);
                outputContext.addOutputValueObject(outApmLearnedMatch);
            }
        } catch (PrepFunctionalException | PrepSystemException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting addEOLearnedMatch method in UsageHandlerImpl");
        return outputContext;
    }

    @Transactional("eoDSTxManager")
    public PREPContext updateEOLearnedMatchMultiple(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering updateEOLearnedMatchMultiple method in UsageHandlerImpl");
        /* VObs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        EOLearnedMatch apmLearnedMatch = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmLearnedMatch = (EOLearnedMatch) inputValueObjects.iterator().next();
                apmLearnedMatch = usageService.updateEOLearnedMatchMultiple(apmLearnedMatch);
                outputContext.addOutputValueObject(apmLearnedMatch);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw new PrepFunctionalException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting updateEOLearnedMatchMultiple method in UsageHandlerImpl");
        return outputContext;
    }

    @Override
    public PREPContext getApmLearnedMatch(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        if (log.isDebugEnabled()) {
            log.debug("Entering getApmLearnedMatch method in UsageHandlerImpl");
        }

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmLearnedMatch apmLearnedMatch = null;
        ApmLearnedMatch outApmLearnedMatch = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmLearnedMatch = (ApmLearnedMatch) inputValueObjects.iterator().next();
                outApmLearnedMatch = usageService.getApmLearnedMatch(apmLearnedMatch);
                outputContext.addOutputValueObject(outApmLearnedMatch);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting getApmLearnedMatch method in UsageHandlerImpl");
        return outputContext;
    }

    @Override
    public PREPContext getApmLearnedMatchList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getApmLearnedMatchList method in UsageHandlerImpl");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmLearnedMatchList apmLearnedMatchList = null;
        ApmLearnedMatchList outApmLearnedMatchList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmLearnedMatchList = (ApmLearnedMatchList) inputValueObjects.iterator().next();
                outApmLearnedMatchList = usageService.getApmLearnedMatchList(apmLearnedMatchList);
                outputContext.addOutputValueObject(outApmLearnedMatchList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting getApmLearnedMatchList method in UsageHandlerImpl");
        return outputContext;
    }

    @Override
    public PREPContext deleteApmLearnedMatchList(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering deleteApmLearnedMatchList method in UsageHandlerImpl");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmLearnedMatchList apmFileList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmFileList = (ApmLearnedMatchList) inputValueObjects.iterator().next();
                apmFileList.getIndex();
                apmFileList.getFilterResultViewType();
                usageService.deleteApmLearnedMatchList(apmFileList);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        log.debug("Exiting deleteApmLearnedMatchList method in UsageHandlerImpl");
        return outputContext;

    }

    @Override
    public PREPContext updateApmLearnedMatch(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering updateApmLearnedMatch method in UsageHandlerImpl");
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmLearnedMatchList apmFileList = null;
        ApmLearnedMatchList outsampleDateListVob = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmFileList = (ApmLearnedMatchList) inputValueObjects.iterator().next();
                outsampleDateListVob = usageService.updateApmLearnedMatch(apmFileList);
                if (outsampleDateListVob.getInvalidGroups() != null
                    && !outsampleDateListVob.getInvalidGroups().isEmpty()) {
                    outputContext.addOutputValueObject(outsampleDateListVob);
                    return outputContext;
                }
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting updateApmLearnedMatch method in UsageHandlerImpl");
        return outputContext;

    }

    @Override
    public PREPContext getLearnedMatchMedleyWorkInformation(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering getLearnedMatchMedleyWorkInformation method in UsageHandlerImpl");
        /* VObs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmLearnedMatchList apmFileList = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmFileList = (ApmLearnedMatchList) inputValueObjects.iterator().next();
                ApmLearnedMatch apmLearnedMatch = usageService.getLearnedMatchMedleyWorkInformation(apmFileList);
                outputContext.addOutputValueObject(apmLearnedMatch);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting getLearnedMatchMedleyWorkInformation method in UsageHandlerImpl");
        return outputContext;

    }

    @Override
    public PREPContext updateApmLearnedMatchMultiple(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering updateApmLearnedMatchMultiple method in UsageHandlerImpl");
        /* VObs and outputContext */
        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmLearnedMatch apmLearnedMatch = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmLearnedMatch = (ApmLearnedMatch) inputValueObjects.iterator().next();
                apmLearnedMatch = usageService.updateApmLearnedMatchMultiple(apmLearnedMatch);
                outputContext.addOutputValueObject(apmLearnedMatch);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pse) {
            log.error(pse.getMessage());
            throw new PrepFunctionalException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        log.debug("Exiting updateApmLearnedMatchMultiple method in UsageHandlerImpl");
        return outputContext;

    }

    @Override
    public PREPContext addApmLearnedMatch(PREPContext inputContext)
        throws PrepSystemException, PrepFunctionalException {
        log.debug("Entering addApmLearnedMatch method in UsageHandlerImpl");

        PREPContext outputContext = new PREPContext();
        List<Object> inputValueObjects = null;
        ApmLearnedMatch apmLearnedMatch = null;
        ApmLearnedMatch outApmLearnedMatch = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();

            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                apmLearnedMatch = (ApmLearnedMatch) inputValueObjects.iterator().next();
                outApmLearnedMatch = usageService.addApmLearnedMatch(apmLearnedMatch);
                outputContext.addOutputValueObject(outApmLearnedMatch);
            }
        } catch (PrepFunctionalException pfex) {
            log.error(pfex.getMessage());
            throw pfex;
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw pse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }

        log.debug("Exiting addApmLearnedMatch method in UsageHandlerImpl");
        return outputContext;
    }

    public PREPContext loadFile2Usage(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException {
        if (log.isDebugEnabled()) {
            log.debug("Entering UsageControllerBean - loadFile2Avps method ");
        }

        PREPContext outputContext = new PREPContext();
        Collection<?> inputValueObjects = null;
        ApmFileUpload userFileUpload = null;
        try {
            inputValueObjects = inputContext.getInputValueObjects();
            if (inputValueObjects != null && !inputValueObjects.isEmpty()) {
                userFileUpload = (ApmFileUpload) inputValueObjects.iterator().next();
                usageService.loadFile2Usage(userFileUpload);
            }
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (PrepFunctionalException pfe) {
            log.warn(pfe.getMessage());
            throw pfe;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException(ERROR_USAGE_COMMON_EXCEPTION);
        }
        try {
            UserPreference userPreference = null;
            userPreference = inputContext.getUserSessionState().getUserPreference();
            ApmFileList usageFileSearchVOBIn = new ApmFileList();
            ApmFileList usageFileSearchVOB = null;
            usageFileSearchVOBIn.setUserPreferences(userPreference);
            usageFileSearchVOB = usageService.seacrhFiles(usageFileSearchVOBIn);
            outputContext.addOutputValueObject(usageFileSearchVOB);
        } catch (PrepSystemException pse) {
            log.error(pse.getMessage());
            throw new PrepSystemException(pse.getErrorKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PrepSystemException("us.error.common.exception");
        }

        if (log.isDebugEnabled()) {
            log.debug("Exiting UsageControllerBean - loadFile2Avps method ");
        }

        return outputContext;

    }
}
