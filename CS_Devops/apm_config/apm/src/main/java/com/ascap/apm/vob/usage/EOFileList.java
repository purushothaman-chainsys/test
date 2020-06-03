package com.ascap.apm.vob.usage;

import java.util.List;

import com.ascap.apm.common.utils.ValidationUtils;
import com.ascap.apm.vob.BaseSearchVOB;

public class EOFileList extends BaseSearchVOB {

    /**
     * 
     */
    private static final long serialVersionUID = 8852251834465425082L;

    private String filterSupplierCode;
    private String filterFileStatus;

    private String filterPerformanceQuarter;
    private String filterDistributionQuarter;
    private String filterCompletedSupplierCode;
    private String filterPerfPeriod;


    private String newReviewFiles;
    private String newReviewFilesFiltered;
    private String channelReviewFiles;
    private String rolllupReviewFiles;
    private String eoLoadFiles;
    private String apmLoadFiles;
    private String errorFiles;
    private String noProcessFiles;
    private String successFiles;
    private String successFilesByPeriod;


    private String startDate;
    private String endDate;
    private String activeSurveyQuarterApm;
    private String activeSurveyQuarterEo;
    private String currentPerformanceQuarterEO;
    private String currentPerformanceQuarterApm;

    private String currentDistQuarterEO;

    private List<EOCallLetterConfig> callLetters;

    private String rollupThreshold;

    private String loadThresholdExceeded;


    public String getFilterSupplierCode() {
        return filterSupplierCode;
    }
    public void setFilterSupplierCode(String filterSupplierCode) {
        this.filterSupplierCode = filterSupplierCode;
    }
    public String getFilterFileStatus() {
        return filterFileStatus;
    }
    public void setFilterFileStatus(String filterFileStatus) {
        this.filterFileStatus = filterFileStatus;
    }
    public String getNewReviewFiles() {
        return newReviewFiles;
    }
    public void setNewReviewFiles(String newReviewFiles) {
        this.newReviewFiles = newReviewFiles;
    }
    public String getChannelReviewFiles() {
        return channelReviewFiles;
    }
    public void setChannelReviewFiles(String channelReviewFiles) {
        this.channelReviewFiles = channelReviewFiles;
    }
    public String getRolllupReviewFiles() {
        return rolllupReviewFiles;
    }
    public void setRolllupReviewFiles(String rolllupReviewFiles) {
        this.rolllupReviewFiles = rolllupReviewFiles;
    }
    public String getEoLoadFiles() {
        return eoLoadFiles;
    }
    public void setEoLoadFiles(String eoLoadFiles) {
        this.eoLoadFiles = eoLoadFiles;
    }
    public String getApmLoadFiles() {
        return apmLoadFiles;
    }
    public void setApmLoadFiles(String apmLoadFiles) {
        this.apmLoadFiles = apmLoadFiles;
    }
    public String getErrorFiles() {
        return errorFiles;
    }
    public void setErrorFiles(String errorFiles) {
        this.errorFiles = errorFiles;
    }
    public String getNoProcessFiles() {
        return noProcessFiles;
    }
    public void setNoProcessFiles(String noProcessFiles) {
        this.noProcessFiles = noProcessFiles;
    }
    public String getSuccessFiles() {
        return successFiles;
    }
    public void setSuccessFiles(String successFiles) {
        this.successFiles = successFiles;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getActiveSurveyQuarterApm() {
        return activeSurveyQuarterApm;
    }
    public void setActiveSurveyQuarterApm(String activeSurveyQuarterApm) {
        this.activeSurveyQuarterApm = activeSurveyQuarterApm;
        if(activeSurveyQuarterApm != null && activeSurveyQuarterApm.length() == 6) {
            String yearPart = activeSurveyQuarterApm.substring(0,4);
            String monthPart = activeSurveyQuarterApm.substring(4);
            String quarter = "";
            if("03".equals(monthPart)) {
                quarter = "Q1";
            }
            else if("06".equals(monthPart)) {
                quarter = "Q2";
            }
            else if("09".equals(monthPart)) {
                quarter = "Q3";
            }
            else if("12".equals(monthPart)) {
                quarter = "Q4";
            } 
            if(!"".equals(quarter)) {
                this.activeSurveyQuarterEo = yearPart+quarter;
            }
        }

        String currPerfQtr = "";
        if(!ValidationUtils.isEmptyOrNull(activeSurveyQuarterEo) && activeSurveyQuarterEo.trim().length() == 6) {
            activeSurveyQuarterEo = activeSurveyQuarterEo.trim();
            String yearPart = activeSurveyQuarterEo.substring(0, 4);
            String qtrPart = activeSurveyQuarterEo.substring(5);
            int yearPartNum = 0;
            int qtrPartNum = 0;
            try {
                yearPartNum = Integer.parseInt(yearPart);
                qtrPartNum = Integer.parseInt(qtrPart);
            }
            catch(Exception e) {}

            int currPerfQtrNum = qtrPartNum - 2;
            if(currPerfQtrNum == 1 || currPerfQtrNum == 2) {
                currPerfQtr = yearPartNum + "Q" + currPerfQtrNum;
            }
            else if(currPerfQtrNum == 0) {
                currPerfQtr = (yearPartNum - 1) + "Q4"; 
            }
            else if(currPerfQtrNum == -1) {
                currPerfQtr = (yearPartNum - 1) + "Q3"; 
            }
            this.setCurrentPerformanceQuarterEO(currPerfQtr);	
        }
    }

    public String getActiveSurveyQuarterEo() {
        return activeSurveyQuarterEo;
    }
    public void setActiveSurveyQuarterEo(String activeSurveyQuarterEo) {
        this.activeSurveyQuarterEo = activeSurveyQuarterEo;
    }
    public List<EOCallLetterConfig> getCallLetters() {
        return callLetters;
    }
    public void setCallLetters(List<EOCallLetterConfig> callLetters) {
        this.callLetters = callLetters;
    }
    public String getCurrentPerformanceQuarterEO() {
        return currentPerformanceQuarterEO;
    }
    public void setCurrentPerformanceQuarterEO(String currentPerformanceQuarterEO) {
        this.currentPerformanceQuarterEO = currentPerformanceQuarterEO;
    }
    public String getCurrentPerformanceQuarterApm() {
        return currentPerformanceQuarterApm;
    }
    public void setCurrentPerformanceQuarterApm(String currentPerformanceQuarterApm) {
        this.currentPerformanceQuarterApm = currentPerformanceQuarterApm;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EOFileList [filterSupplierCode=");
        builder.append(filterSupplierCode);
        builder.append(", filterFileStatus=");
        builder.append(filterFileStatus);
        builder.append(", newReviewFiles=");
        builder.append(newReviewFiles);
        builder.append(", channelReviewFiles=");
        builder.append(channelReviewFiles);
        builder.append(", rolllupReviewFiles=");
        builder.append(rolllupReviewFiles);
        builder.append(", eoLoadFiles=");
        builder.append(eoLoadFiles);
        builder.append(", apmLoadFiles=");
        builder.append(apmLoadFiles);
        builder.append(", errorFiles=");
        builder.append(errorFiles);
        builder.append(", noProcessFiles=");
        builder.append(noProcessFiles);
        builder.append(", successFiles=");
        builder.append(successFiles);
        builder.append(", startDate=");
        builder.append(startDate);
        builder.append(", endDate=");
        builder.append(endDate);
        builder.append(", activeSurveyQuarterApm=");
        builder.append(activeSurveyQuarterApm);
        builder.append(", activeSurveyQuarterEo=");
        builder.append(activeSurveyQuarterEo);
        builder.append(", currentPerformanceQuarterEO=");
        builder.append(currentPerformanceQuarterEO);
        builder.append(", currentPerformanceQuarterApm=");
        builder.append(currentPerformanceQuarterApm);
        builder.append("]");
        return builder.toString();
    }
    public String getNewReviewFilesFiltered() {
        return newReviewFilesFiltered;
    }
    public void setNewReviewFilesFiltered(String newReviewFilesFiltered) {
        this.newReviewFilesFiltered = newReviewFilesFiltered;
    }
    public String getRollupThreshold() {
        return rollupThreshold;
    }
    public void setRollupThreshold(String rollupThreshold) {
        this.rollupThreshold = rollupThreshold;
    }
    public String getLoadThresholdExceeded() {
        return loadThresholdExceeded;
    }
    public void setLoadThresholdExceeded(String loadThresholdExceeded) {
        this.loadThresholdExceeded = loadThresholdExceeded;
    }
    public String getFilterPerformanceQuarter() {
        return filterPerformanceQuarter;
    }
    public void setFilterPerformanceQuarter(String filterPerformanceQuarter) {
        this.filterPerformanceQuarter = filterPerformanceQuarter;
    }
    public String getFilterDistributionQuarter() {
        return filterDistributionQuarter;
    }
    public void setFilterDistributionQuarter(String filterDistributionQuarter) {
        this.filterDistributionQuarter = filterDistributionQuarter;
    }
    public String getSuccessFilesByPeriod() {
        return successFilesByPeriod;
    }
    public void setSuccessFilesByPeriod(String successFilesByPeriod) {
        this.successFilesByPeriod = successFilesByPeriod;
    }
    public String getFilterCompletedSupplierCode() {
        return filterCompletedSupplierCode;
    }
    public void setFilterCompletedSupplierCode(String filterCompletedSupplierCode) {
        this.filterCompletedSupplierCode = filterCompletedSupplierCode;
    }
    public String getCurrentDistQuarterEO() { 

        //this.activeSurveyQuarterApm = activeSurveyQuarterApm;
        if(activeSurveyQuarterApm != null && activeSurveyQuarterApm.length() == 6) {
            String yearPart = activeSurveyQuarterApm.substring(0,4);
            String monthPart = activeSurveyQuarterApm.substring(4);
            String quarter = "";
            if("03".equals(monthPart)) {
                quarter = "D1";
            }
            else if("06".equals(monthPart)) {
                quarter = "D2";
            }
            else if("09".equals(monthPart)) {
                quarter = "D3";
            }
            else if("12".equals(monthPart)) {
                quarter = "D4";
            } 
            if(!"".equals(quarter)) {
                this.currentDistQuarterEO = yearPart+quarter;
            }

        }
        return currentDistQuarterEO;
    }
    public void setCurrentDistQuarterEO(String currentDistQuarterEO) {
        this.currentDistQuarterEO = currentDistQuarterEO;
    }
    public String getFilterPerfPeriod() {
        return filterPerfPeriod;
    }
    public void setFilterPerfPeriod(String filterPerfPeriod) {
        this.filterPerfPeriod = filterPerfPeriod;
    }

}
