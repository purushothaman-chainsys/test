package com.ascap.apm.controller.usage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.handler.usage.UsageHandler;
import com.ascap.apm.vob.usage.ApmActiveSurveyQuarter;
import com.ascap.apm.vob.usage.EOCallLetterConfig;
import com.ascap.apm.vob.usage.EOFile;
import com.ascap.apm.vob.usage.EOFileList;
import com.ascap.apm.vob.usage.EOFileListForm;
import com.ascap.apm.vob.usage.EOSupplierFormat;

@Controller
public class EOFilesMultiController extends BaseUsageController {

    private static final String EO_FILE_LIST_SUMMARY = "eoFileListSummary"; 

    @Autowired
    private UsageHandler usageHandler;

    @RequestMapping(value = "/usage/eoFiles", method = { RequestMethod.GET, RequestMethod.POST })
    public String doWork(@ModelAttribute EOFileListForm eoFileListForm , HttpServletRequest request,
        HttpServletResponse response , Model model)
            throws Exception {

        log.debug("Entering doWork of EOFilesMultiController");
        try {

            if(! ValidationUtils.isEmptyOrNull(eoFileListForm.getOperationType())) {
                if("LIST".equals(eoFileListForm.getOperationType())) {
                    return getEOFileList( model , eoFileListForm, request, response, null);
                }
                else if("LOAD2EO".equals(eoFileListForm.getOperationType())) {
                    return loadToEO( model , eoFileListForm, request, response);
                }
                else if("LOAD2APM".equals(eoFileListForm.getOperationType())) {
                    return loadToAPM( model , eoFileListForm, request, response);
                }
                else if("UPDATEFILEINVENTORY".equals(eoFileListForm.getOperationType())  || "IGNORE_ERRORS".equals(eoFileListForm.getOperationType() )) {
                    return updateFileInventory(model, eoFileListForm, request, response);
                } 
                else if ("CHANNEL_LIST".equals(eoFileListForm.getOperationType())) {
                    return "ChannelList";
                }
            }
            else {
                return getEOFileList( model, eoFileListForm, request, response,null);
            }		
        } 
        catch(Exception e) {
            log.error("Error Caught in doWork");
            return EO_FILE_LIST_SUMMARY;
        }
        log.debug("Exiting doWork of EOFilesMultiController");
        return null;
    }


    private String updateFileInventory(Model model,
        EOFileListForm eoFileListForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {

        log.debug("Entering updateFileInventory of EOFilesMultiController");

        String forwardKey = "usage/eoFileListSummary";

        if(eoFileListForm == null) {
            return forwardKey;
        }

        String[] selectedIndex = eoFileListForm.getSelectedIndex();

        if(selectedIndex == null || selectedIndex.length<=0) {
            return forwardKey;
        }

        List<Object> col = new ArrayList<>();
        EOFile eoFile = null;

        for(int i =0;i<selectedIndex.length;i++) {			
            String val = selectedIndex[i];
            log.debug("update FileInventory val: "+val);
            if(val != null) {
                String[] result = val.split("\\s");
                if(result != null && result.length >= 2) {
                    eoFile = new EOFile();
                    eoFile.setSuppCode(result[0]);
                    eoFile.setPerfQuarter(result[1]);
                    if(result.length >= 4){
                        eoFile.setPerfPeriod(result[3]); 
                    }
                    if("IGNORE_ERRORS".equals(eoFileListForm.getOperationType())) {
                        eoFile.setErrorCorrectedStatus("IG");
                    }
                    else if("UPDATEFILEINVENTORY".equals(eoFileListForm.getOperationType())) { 
                        eoFile.setErrorCorrectedStatus("PE");
                    }
                    else {
                        log.debug("Invalid operation type received.. Returning null");
                        return  this.getEOFileList( model,  eoFileListForm, request, response, null);
                    }
                    log.debug("EOFile " + eoFile);
                    col.add(eoFile);
                }
            }
        }
        EOFileList eoFileList = new EOFileList();

        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        List<Object> outValueObjects = null; 


        try {
            BeanUtils.copyProperties(eoFileList, eoFileListForm);
            eoFileList.setSearchResults(col);

            inputContext.addInputValueObject(eoFileList); 

            outputContext = usageHandler.updateFileInventory(inputContext);

            outValueObjects = outputContext.getOutputValueObjects();

            if(outValueObjects != null && outValueObjects.size() > 0) {
                eoFileList  = (EOFileList)outValueObjects.iterator().next();
                BeanUtils.copyProperties(eoFileListForm, eoFileList);
                eoFileListForm.setOperationType(null);
                eoFileListForm.setFileIds(null);

                model.addAttribute("systemmessage", getMessage("error.apm.eoload.batchcontrol.updateSucess"));
                eoFileListForm.setSelectedIndex(null);
                return this.getEOFileList(model , eoFileListForm, request, response, null);
            }	

        }		

        catch(PrepFunctionalException pe) {
            log.error("PrepFunctionalException Caught in EOFilesMultiController  :" , pe);
            String [] dyanValues = pe.getDynaValues();
            model.addAttribute("systemerror", getMessage(pe.getErrorKey() , dyanValues));
        }
        catch(PrepSystemException pse) {
            log.error("PrepSystemException Caught in EOFilesMultiController  :" , pse);			
            model.addAttribute("systemerror", getMessage(pse.getErrorKey()));
        }
        catch(Exception pe) {
            log.error("Exception Caught in EOFilesMultiController  :" , pe);
            model.addAttribute("systemerror", getMessage("system.error"));
        }		
        log.debug("Exiting updateFileInventory of EOFilesMultiController");

        eoFileListForm.setSelectedIndex(null);

        return this.getEOFileList( model, eoFileListForm, request, response, null);

    }

    private String loadToEO( Model model,
        EOFileListForm eoFileListForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {

        log.debug("Entering loadToEO of EOFilesMultiController");

        String forwardKey = EO_FILE_LIST_SUMMARY;

        if(eoFileListForm == null) {
            return forwardKey;
        }

        String[] selectedIndex = eoFileListForm.getSelectedIndex();
        String[] fileIds = eoFileListForm.getFileIds();
        String[] perfQuarters = eoFileListForm.getPerfQuarters();
        String[] donotProcessFlags = eoFileListForm.getDonotProcessFlags();
        String[] perfPeriods = eoFileListForm.getPerfPeriods();
        String[] supplierTypes = eoFileListForm.getSupplierTypes();


        if(selectedIndex == null || selectedIndex.length<=0) {
            return forwardKey;
        }

        List<Object> col = new ArrayList<>();
        EOFile eoFile = null;
        int selectedRow = 0;
        for(int i =0;i<selectedIndex.length;i++) {
            try {
                selectedRow = Integer.parseInt(selectedIndex[i]);
            }
            catch(Exception e) {
                log.error("Invalid Row Number received");
                throw e;
            }
            eoFile = new EOFile();
            eoFile.setFileId(fileIds[selectedRow]);
            eoFile.setPerfQuarter(perfQuarters[selectedRow]);
            eoFile.setDonotProcessFlag(donotProcessFlags[selectedRow]);
            eoFile.setPerfPeriod(perfPeriods[selectedRow]);
            eoFile.setSupplierType(supplierTypes[selectedRow]);
            col.add(eoFile);
            log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX donotProcessFlags: " + donotProcessFlags[selectedRow]);

        }

        EOFileList eoFileList = new EOFileList();

        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        List<Object> outValueObjects = null;

        try {
            BeanUtils.copyProperties(eoFileList, eoFileListForm);
            eoFileList.setSearchResults(col);

            inputContext.addInputValueObject(eoFileList);

            outputContext = usageHandler.loadToEO(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();

            if(outValueObjects != null && outValueObjects.size() > 0) {
                eoFileList  = (EOFileList)outValueObjects.iterator().next();
                BeanUtils.copyProperties(eoFileListForm, eoFileList);
                eoFileListForm.setOperationType(null);
                eoFileListForm.setFileIds(null);
                eoFileListForm.setSelectedIndex(null);

                return this.getEOFileList(model , eoFileListForm, request, response, null);
            }	
        }		

        catch(PrepFunctionalException pe) {
            log.error("PrepFunctionalException Caught in EOFilesMultiController  :" , pe);			
            model.addAttribute("systemerror", getMessage(pe.getErrorKey()));
            forwardKey = EO_FILE_LIST_SUMMARY;
        }
        catch(PrepSystemException pe) {
            log.error("PrepSystemException Caught in EOFilesMultiController  :" , pe);			
            model.addAttribute("systemerror", getMessage(pe.getErrorKey()));
            forwardKey = EO_FILE_LIST_SUMMARY;
        }
        catch(Exception pe) {
            log.error("Exception Caught in EOFilesMultiController  :" , pe);
            model.addAttribute("system.error", getMessage("systemerror"));
            forwardKey = EO_FILE_LIST_SUMMARY;
        }

        log.debug("Exiting loadToEO of EOFilesMultiController");
        return forwardKey;
    }

    private String loadToAPM( Model model,
        EOFileListForm eoFileListForm, HttpServletRequest request,
        HttpServletResponse response) throws Exception {

        log.debug("Entering loadToAPM of EOFilesMultiController");
        String forwardKey = EO_FILE_LIST_SUMMARY;

        if(eoFileListForm == null) {
            return forwardKey;
        }

        String[] selectedIndex = eoFileListForm.getSelectedIndex();
        if(selectedIndex == null || selectedIndex.length <=0) {
            return forwardKey;
        }

        // Get threshold from oracle apm params
        String rollupThreshold = null;

        PREPContext inputContextThreshold = getPREPContext(request);
        PREPContext outputContextThreshold = null;
        try {

            inputContextThreshold.addInputValueObject(new EOFileList());
            outputContextThreshold = usageHandler.getRollupThreshold(inputContextThreshold);

            List<Object> outValueObjectsThreshold = outputContextThreshold.getOutputValueObjects();

            EOFileList eoFileListThreshold = null;

            if(outValueObjectsThreshold != null && outValueObjectsThreshold.size() > 0) {
                eoFileListThreshold  = (EOFileList) outValueObjectsThreshold.iterator().next();

                rollupThreshold = eoFileListThreshold.getRollupThreshold();
            }	
        }

        catch(PrepFunctionalException pe) {
            log.error("PrepFunctionalException Caught in EOFilesMultiController  :" , pe);
            String [] dyanValues = pe.getDynaValues();
            model.addAttribute("systemerror", getMessage(pe.getErrorKey() , dyanValues));
        }
        catch(PrepSystemException pe) {
            log.error("PrepSystemException Caught in EOFilesMultiController  :" , pe);			
            model.addAttribute("systemerror", getMessage(pe.getErrorKey()));
        }
        catch(Exception pe) {
            log.error("Exception Caught in EOFilesMultiController  :" , pe);
            model.addAttribute("systemerror", getMessage("systemerror"));
        }		

        List<Object> col = new ArrayList<>();
        EOFile eoFile = null;
        for(int i =0;i<selectedIndex.length;i++) {			
            String val = selectedIndex[i];
            if(val != null) {
                String[] result = val.split("\\s");
                if(result != null) {
                    eoFile = new EOFile();
                    eoFile.setSuppCode(result[0]);      
                    eoFile.setPerfQuarter(result[1]);  
                    eoFile.setDistQuarter(result[2]);  
                    log.debug("loadToAPM val"+val);
                    log.debug("In EOFilesMultiController, SuppCode: "+result[0] +" PerfQtr: "+result[1] + " DistQtr: "+result[2]);
                    if (result.length >= 5) { 
                        eoFile.setPerfPeriod(result[4]);  
                        log.debug("In EOFilesMultiController, PerfPeriod: "+result[4] );
                    } 
                    col.add(eoFile);
                }
            }
        }


        EOFileList eoFileList = new EOFileList();

        PREPContext inputContext = getPREPContext(request);
        PREPContext outputContext = null;
        List<Object> outValueObjects = null;

        eoFileList.setRollupThreshold(rollupThreshold);

        try {
            BeanUtils.copyProperties(eoFileList, eoFileListForm);
            eoFileList.setSearchResults(col);

            inputContext.addInputValueObject(eoFileList);

            outputContext = usageHandler.loadToAPM(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();

            if(outValueObjects != null && outValueObjects.size() > 0) {
                eoFileList  = (EOFileList)outValueObjects.iterator().next();
                BeanUtils.copyProperties(eoFileListForm, eoFileList);
                eoFileListForm.setOperationType(null);
                eoFileListForm.setFileIds(null);
                eoFileListForm.setLoadThresholdExceeded(null);
                return this.getEOFileList( model, eoFileListForm, request, response, null);
            }	
        }		

        catch(PrepFunctionalException pe) {
            log.error("PrepFunctionalException Caught in EOFilesMultiController  :" , pe);

            if("error.apm.eoload.rollup.gt.threshold".equals(pe.getErrorKey())) {
                eoFileListForm.setOperationType(null);
                eoFileListForm.setSelectedIndex(selectedIndex);
                eoFileListForm.setLoadThresholdExceeded("Y");
                return this.getEOFileList( model, eoFileListForm, request, response, "Y");
            }
            else {
                eoFileListForm.setLoadThresholdExceeded(null);
            }

            String[] dyanValues = pe.getDynaValues();
            model.addAttribute("systemerror", getMessage(pe.getErrorKey() , dyanValues));
        }
        catch(PrepSystemException pe) {
            log.error("PrepSystemException Caught in EOFilesMultiController  :" , pe);			
            model.addAttribute("systemerror", getMessage(pe.getErrorKey()));
        }
        catch(Exception pe) {
            log.error("Exception Caught in EOFilesMultiController  :" , pe);
            model.addAttribute("systemerror", getMessage("system.error"));
        }		
        log.debug("Exiting loadToAPM of EOFilesMultiController");

        eoFileListForm.setSelectedIndex(null);
        eoFileListForm.setLoadThresholdExceeded(null);
        return this.getEOFileList( model , eoFileListForm, request, response, null);
    }

    private String getEOFileList(Model model,
        EOFileListForm eoFileListForm, HttpServletRequest request,
        HttpServletResponse response, String loadThresholdWarning) throws Exception {

        log.debug("Entering getEOFileList of EOFilesMultiController, operationType=" + eoFileListForm.getOperationType() + ", filterFileStatus="+eoFileListForm.getFilterFileStatus());
        String forwardKey = "usage/eoFileListSummary";

        if(eoFileListForm.getSelectedIndex() != null && eoFileListForm.getSelectedIndex().length != 0) {
            log.debug("selected rows: " + eoFileListForm.getSelectedIndex().length + " "+ eoFileListForm.getSelectedIndex()[0]);
        }
        else {
            log.debug("Nothing is selected ");
        }

        try {

            EOFileList eoFileList = null;
            PREPContext inputContext = getPREPContext(request);
            PREPContext outputContext = null;
            PREPContext activeSurvYearoutputContext = null;
            List<Object> outValueObjects = null;
            List<Object> activeSurvYearoutValueObjects = null;

            ArrayList<String> apmSupplierList = null;

            eoFileList = new EOFileList();
            BeanUtils.copyProperties(eoFileList, eoFileListForm);

            if(eoFileListForm != null && eoFileListForm.getFilterFileStatus() != null && "LOAD2EO_PEND".equals(eoFileListForm.getFilterFileStatus())) {
                PREPContext apmSuppliersOoutputContext = null;
                List<?> apmSuppliersOutValueObjects = null;
                List<?> apmSuppliersCol = null;
                apmSuppliersOoutputContext = usageHandler.getApmSuppliers(inputContext);
                apmSuppliersOutValueObjects = apmSuppliersOoutputContext.getOutputValueObjects();
                if(apmSuppliersOutValueObjects != null && apmSuppliersOutValueObjects.size() > 0) {
                    apmSuppliersCol = (List<?>) apmSuppliersOutValueObjects.iterator().next();
                    Iterator<?> suppItr = null;
                    if(apmSuppliersCol != null && apmSuppliersCol.size() > 0) {
                        suppItr = apmSuppliersCol.iterator();
                        EOSupplierFormat eoSupplierFormat = null;
                        apmSupplierList = new ArrayList<String>();
                        while(suppItr.hasNext()) {
                            eoSupplierFormat = (EOSupplierFormat) suppItr.next();
                            apmSupplierList.add(eoSupplierFormat.getApmSuppCode());
                        }
                    }
                }
            }

            /* First Get the Active Year quarter from PAPM */

            ApmActiveSurveyQuarter apmActiveSurveyQuarter = null;
            activeSurvYearoutputContext = usageHandler.getApmActiveSurveyQuarter(inputContext);
            activeSurvYearoutValueObjects = activeSurvYearoutputContext.getOutputValueObjects();
            if(activeSurvYearoutValueObjects != null && activeSurvYearoutValueObjects.size() > 0) {
                apmActiveSurveyQuarter  = (ApmActiveSurveyQuarter)activeSurvYearoutValueObjects.iterator().next();
            }

            log.debug("Active Survey Year returned is " + apmActiveSurveyQuarter);

            if(apmActiveSurveyQuarter != null) {
                eoFileList.setStartDate(apmActiveSurveyQuarter.getStartDate());
                eoFileList.setEndDate(apmActiveSurveyQuarter.getEndDate());
                eoFileList.setActiveSurveyQuarterApm(apmActiveSurveyQuarter.getActiveSurveyQuarter());
            }
            inputContext.addInputValueObject(eoFileList);

            outputContext = usageHandler.getEOFileList(inputContext);
            outValueObjects = outputContext.getOutputValueObjects();

            if(outValueObjects != null && outValueObjects.size() > 0) {
                eoFileList  = (EOFileList)outValueObjects.iterator().next();
                /* For Pending Load to EO files set APM Supplier Flag by from Suppformat table in PAPM*/
                if(eoFileList != null && eoFileList.getFilterFileStatus() != null && "LOAD2EO_PEND".equals(eoFileList.getFilterFileStatus())) {
                    List<?> results = eoFileList.getSearchResults();					
                    if(results != null && results.size() > 0) {
                        Iterator<?> itr = results.iterator();
                        EOFile tempEOFile = null;
                        while(itr.hasNext()) {
                            tempEOFile = (EOFile) itr.next();
                            if(tempEOFile != null && apmSupplierList != null) {
                                if(apmSupplierList.contains(tempEOFile.getSuppCode())) {							
                                    tempEOFile.setApmSuppFormatFlag("Y");
                                }
                                else {
                                    tempEOFile.setApmSuppFormatFlag("N");
                                }
                            }
                        }
                    }
                }
                /* For Sucessfully loaded files set the sampling completed flag from the call letter config in EO and Music User Control in PAPM*/
                if(eoFileList != null && eoFileList.getFilterFileStatus() != null && "SUCCESS_FILES".equals(eoFileList.getFilterFileStatus())) {

                    List<?> censusSuppliers = apmActiveSurveyQuarter.getCensusSuppliers();					
                    List<EOCallLetterConfig> callLetters = eoFileList.getCallLetters();
                    if(callLetters != null && callLetters.size() > 0) {
                        Iterator<EOCallLetterConfig> itr = callLetters.iterator();
                        EOCallLetterConfig eoCallLetterConfig = null;
                        while(itr.hasNext()) {
                            eoCallLetterConfig = (EOCallLetterConfig) itr.next();							

                            String callLetterFull =  eoCallLetterConfig.getCallLetterFull();

                            if(!ValidationUtils.isEmptyOrNull(callLetterFull) && censusSuppliers.size() > 0) {
                                Iterator<?> itrCensuls = censusSuppliers.iterator();
                                EOCallLetterConfig eoCensusConfig = null;
                                while(itrCensuls.hasNext()) {
                                    eoCensusConfig = (EOCallLetterConfig) itrCensuls.next();
                                    if(callLetterFull.equals(eoCensusConfig.getCallLetterFull())) {
                                        eoCallLetterConfig.setSamplingCompletedFlag(eoCensusConfig.getSamplingCompletedFlag());
                                    }										
                                }
                            }
                        }
                    }

                    Map<String,String> m = new HashMap <String,String>();

                    if(callLetters != null && callLetters.size() > 0) {
                        Iterator<EOCallLetterConfig> itr = callLetters.iterator();
                        EOCallLetterConfig eoCallLetterConfig = null;
                        while(itr.hasNext()) {
                            eoCallLetterConfig = (EOCallLetterConfig) itr.next();

                            if(! m.containsKey(eoCallLetterConfig.getSuppCode()) || "Y".equals(m.get(eoCallLetterConfig.getSuppCode()))) {
                                m.put(eoCallLetterConfig.getSuppCode(), eoCallLetterConfig.getSamplingCompletedFlag());
                            }
                        }
                    }

                    List<?> results = eoFileList.getSearchResults();					
                    if(results != null && results.size() > 0) {
                        Iterator<?> itr = results.iterator();
                        EOFile tempEOFile = null;
                        while(itr.hasNext()) {
                            tempEOFile = (EOFile) itr.next();
                            if(m.containsKey(tempEOFile.getSuppCode())) {
                                if("Y".equals(m.get(tempEOFile.getSuppCode()))) {
                                    tempEOFile.setSamplingCompletedFlag("Y");
                                }
                                else if("N".equals(m.get(tempEOFile.getSuppCode()))) {
                                    tempEOFile.setSamplingCompletedFlag("N");
                                } 
                                else {
                                    tempEOFile.setSamplingCompletedFlag("NA");
                                }								
                            } 
                            else {
                                tempEOFile.setSamplingCompletedFlag("NA");
                            }								
                        }
                    }
                }

                BeanUtils.copyProperties(eoFileListForm, eoFileList);
                if("LOAD2APM_PEND".equals(eoFileListForm.getFilterFileStatus()) && loadThresholdWarning != null && "Y".equals(loadThresholdWarning)) {
                    eoFileListForm.setLoadThresholdExceeded("Y");
                }
                if("LOAD2APM_PEND".equals(eoFileListForm.getFilterFileStatus())) {
                    /*
                     * Set default Distribution Quarter as XXXXXX for all suppliers-perfquarter combinations.
                     * This XXXXXX value will be replaced by actual value when users select a value for Distribution Quarter on the screen.
                     */
                    List<?> results = eoFileList.getSearchResults();					
                    if(results != null && results.size() > 0) {
                        Iterator<?> itr = results.iterator();
                        EOFile tempEOFile = null;
                        while(itr.hasNext()) {
                            tempEOFile = (EOFile) itr.next();
                            tempEOFile.setDistQuarter("XXXXXX");
                        }
                    }

                    /*
                     * 
                     * Reset the value for check box (selectedIndex) with the values received in the input
                     */
                    if( loadThresholdWarning != null && "Y".equals(loadThresholdWarning)) {
                        String sIndex[] = eoFileListForm.getSelectedIndex();


                        if(sIndex != null && sIndex.length > 0) {
                            for(int i =0;i<sIndex.length;i++) {			
                                String val = sIndex[i];

                                if(val != null) {
                                    String[] result = val.split("\\s");
                                    if(result != null && result.length == 3) {

                                        List<?> r = eoFileList.getSearchResults();					
                                        if(r != null && r.size() > 0) {
                                            Iterator<?> itr = r.iterator();
                                            EOFile tempEOFile = null;
                                            while(itr.hasNext()) {
                                                tempEOFile = (EOFile) itr.next();
                                                if( ValidationUtils.isEmptyOrNull(tempEOFile.getFileId()) && tempEOFile.getSuppCode().equals(result[0]) && tempEOFile.getPerfQuarter().equals(result[1]) ) {
                                                    tempEOFile.setDistQuarter(result[2]);

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                request.setAttribute("eoFileListForm", eoFileListForm);
                if(!"LOAD2APM_PEND".equals(eoFileListForm.getFilterFileStatus())) {
                    eoFileListForm.setSelectedIndex(null);
                }
                eoFileList.setSearchResults(null);
                eoFileList.setNavigationType(null);
                eoFileListForm.setPerfQuarters(null);

            }
            else {
                return forwardKey;
            }

        }
        catch(PrepFunctionalException pe) {
            log.error("PrepFunctionalException Caught in EOFilesMultiController  :" , pe);			
            model.addAttribute("systemerror", getMessage(pe.getErrorKey()));
            forwardKey = EO_FILE_LIST_SUMMARY;
        }
        catch(PrepSystemException pe) {
            log.error("PrepSystemException Caught in EOFilesMultiController  :" , pe);			
            model.addAttribute("systemerror", getMessage(pe.getErrorKey()));
            forwardKey = EO_FILE_LIST_SUMMARY;
        }
        catch(Exception pe) {
            log.error("Exception Caught in EOFilesMultiController  :" , pe);
            model.addAttribute("systemerror", getMessage("system.error"));
            forwardKey = EO_FILE_LIST_SUMMARY;
        }
        log.debug("Exiting getEOFileList of EOFilesMultiController");
        return forwardKey;
    }
}
