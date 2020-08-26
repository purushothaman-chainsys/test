package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseSearchVOB;

public class LogRequestSummary extends BaseSearchVOB {


    /**
     * 
     */
    private static final long serialVersionUID = 6408237608809015278L;
    private String targetSurveyYearQuarter; 
    private String activeqtr; 
    private String callLetter; 
    private String filterSupplierCode;
    private String filtertargetSurveyYearQuarter;
    private String filterStaus;
    private String filterSupplierCodeOrig;
    private String filtertargetSurveyYearQuarterOrig;
    private String filterStausOrig;
    private String operationType;
    private String[] selections;
    private String canDeleteLogRequest; 

    private int totalPg;
    private int fromIdx;
    private int toIdx;
    private String navigation;
    private int searchResultsPerPg; 
    private String firstPg;
    private String lastPg;

    private String searchResultRows;

    private String searchRequested;

    private int currentPg;
    private String[] selectedLogRequest;
    private String[] lmIdWorkIdStr;

    public String[] getLmIdWorkIdStr() {
        return lmIdWorkIdStr;
    }
    public void setLmIdWorkIdStr(String[] lmIdWorkIdStr) {
        this.lmIdWorkIdStr = lmIdWorkIdStr;
    }
    public String[] getSelectedLogRequest() {
        return selectedLogRequest;
    }
    public void setSelectedLogRequest(String[] selectedLogRequest) {
        this.selectedLogRequest = selectedLogRequest;
    }
    public int getCurrentPg() {
        return currentPg;
    }
    public void setCurrentPg(int currentPg) {
        this.currentPg = currentPg;
    }
    public int getTotalPg() {
        return totalPg;
    }

    public void setTotalPg(int totalPg) {
        this.totalPg = totalPg;
    }

    public int getFromIdx() {
        return fromIdx;
    }

    public void setFromIdx(int fromIdx) {
        this.fromIdx = fromIdx;
    }

    public int getToIdx() {
        return toIdx;
    }

    public void setToIdx(int toIdx) {
        this.toIdx = toIdx;
    }



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SamplingSummary [targetSurveyYearQuarter=");
        builder.append(targetSurveyYearQuarter); 
        builder.append(", callLetter=");
        builder.append(callLetter);
        builder.append("]");
        return builder.toString();
    }

    public String getTargetSurveyYearQuarter() {
        return targetSurveyYearQuarter;
    }

    public void setTargetSurveyYearQuarter(String targetSurveyYearQuarter) {
        this.targetSurveyYearQuarter = targetSurveyYearQuarter;
    }
    public String getCallLetter() {
        return callLetter;
    }

    public void setCallLetter(String callLetter) {
        this.callLetter = callLetter;
    }

    public String getFilterSupplierCode() {
        return filterSupplierCode;
    }

    public void setFilterSupplierCode(String filterSupplierCode) {
        this.filterSupplierCode = filterSupplierCode;
    }

    public String getFiltertargetSurveyYearQuarter() {
        return filtertargetSurveyYearQuarter;
    }

    public void setFiltertargetSurveyYearQuarter(
        String filtertargetSurveyYearQuarter) {
        this.filtertargetSurveyYearQuarter = filtertargetSurveyYearQuarter;
    }

    public String getFilterStaus() {
        return filterStaus;
    }

    public void setFilterStaus(String filterStaus) {
        this.filterStaus = filterStaus;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String[] getSelections() {
        return selections;
    } 

    public void setSelections(String[] selections) {
        this.selections = selections;
    }

    public String getSearchResultRows() {
        return searchResultRows;
    }

    public void setSearchResultRows(String searchResultRows) {
        this.searchResultRows = searchResultRows;
    }

    public String getSearchRequested() {
        return searchRequested;
    }

    public void setSearchRequested(String searchRequested) {
        this.searchRequested = searchRequested;
    }

    public String getActiveqtr() {
        return activeqtr;
    }

    public void setActiveqtr(String activeqtr) {
        this.activeqtr = activeqtr;
    }


    public String getNavigation() {
        return navigation;
    }

    public void setNavigation(String navigation) {
        this.navigation = navigation;
    }

    public int getSearchResultsPerPg() {
        return searchResultsPerPg;
    }

    public void setSearchResultsPerPg(int searchResultsPerPg) {
        this.searchResultsPerPg = searchResultsPerPg;
    }

    public String getFirstPg() {
        return firstPg;
    }

    public void setFirstPg(String firstPg) {
        this.firstPg = firstPg;
    }

    public String getLastPg() {
        return lastPg;
    }

    public void setLastPg(String lastPg) {
        this.lastPg = lastPg;
    }

    public String getFilterSupplierCodeOrig() {
        return filterSupplierCodeOrig;
    }

    public void setFilterSupplierCodeOrig(String filterSupplierCodeOrig) {
        this.filterSupplierCodeOrig = filterSupplierCodeOrig;
    }

    public String getFiltertargetSurveyYearQuarterOrig() {
        return filtertargetSurveyYearQuarterOrig;
    }

    public void setFiltertargetSurveyYearQuarterOrig(
        String filtertargetSurveyYearQuarterOrig) {
        this.filtertargetSurveyYearQuarterOrig = filtertargetSurveyYearQuarterOrig;
    }

    public String getFilterStausOrig() {
        return filterStausOrig;
    }

    public void setFilterStausOrig(String filterStausOrig) {
        this.filterStausOrig = filterStausOrig;
    }

    public String getCanDeleteLogRequest() {
        return canDeleteLogRequest;
    }

    public void setCanDeleteLogRequest(String canDeleteLogRequest) {
        this.canDeleteLogRequest = canDeleteLogRequest;
    }

}
