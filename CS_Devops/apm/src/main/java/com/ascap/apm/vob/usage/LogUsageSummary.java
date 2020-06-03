package com.ascap.apm.vob.usage;

import java.util.List;

import com.ascap.apm.vob.BaseSearchVOB;

public class LogUsageSummary extends BaseSearchVOB {


    /**
     * 
     */
    private static final long serialVersionUID = 6408237608809015278L;


    private String operationType;

    private String headerid;

    private String callLetter;    
    private String startDate; 
    private String endDate; 
    private String accountNumber; 

    private String[] titles;
    private String[] artists;
    private String[] usetypes;
    private String[] durations; 
    private String[] instvocals;
    private String[] workids;
    private String[] statuses;
    private String[] plays;

    private String[] updWrkperfids;
    private String[] updTitles;
    private String[] updArtists;
    private String[] updUsetypes;
    private String[] updDurations; 
    private String[] updInstvocals;
    private String[] updWorkids;
    private String[] updStatuses;
    private String[] updPlays;

    private String[] insTitles;
    private String[] insArtists;
    private String[] insWorkids;
    private String[] insDurations; 
    private String[] insPlays;
    private String[] insUsetypes; 
    private String[] insInstvocals;  

    private String filterSupplierCode;
    private String filtertargetSurveyYearQuarter;
    private String filterStaus;
    private int logRequestPg;
    private List<LogUsageRequest> logUsages;
    private String[] selectedIndex;

    private String[] selections;


    private String searchRequested;

    private int currentPg;
    private int totalPg;
    private int fromIdx;
    private int toIdx;
    private String navigation;
    private int searchResultsPerPg;
    private String firstPg;
    private String lastPg;
    private String searchResultRows;



    public String getOperationType() {
        return operationType;
    }


    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }


    public String getCallLetter() {
        return callLetter;
    }


    public void setCallLetter(String callLetter) {
        this.callLetter = callLetter;
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


    public String getAccountNumber() {
        return accountNumber;
    }


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public String[] getSelections() {
        return selections;
    }


    public void setSelections(String[] selections) {
        this.selections = selections;
    }


    public String[] getTitles() {
        return titles;
    }


    public void setTitles(String[] titles) {
        this.titles = titles;
    }


    public String[] getArtists() {
        return artists;
    }


    public void setArtists(String[] artists) {
        this.artists = artists;
    }


    public String[] getUsetypes() {
        return usetypes;
    }


    public void setUsetypes(String[] usetypes) {
        this.usetypes = usetypes;
    }


    public String[] getDurations() {
        return durations;
    }


    public void setDurations(String[] durations) {
        this.durations = durations;
    }


    public String[] getInstvocals() {
        return instvocals;
    }


    public void setInstvocals(String[] instvocals) {
        this.instvocals = instvocals;
    }


    public String[] getWorkids() {
        return workids;
    }


    public void setWorkids(String[] workids) {
        this.workids = workids;
    }


    public String[] getStatuses() {
        return statuses;
    }


    public void setStatuses(String[] statuses) {
        this.statuses = statuses;
    }


    public String getHeaderid() {
        return headerid;
    }


    public void setHeaderid(String headerid) {
        this.headerid = headerid;
    }


    public String[] getPlays() {
        return plays;
    }


    public void setPlays(String[] plays) {
        this.plays = plays;
    }


    public List<LogUsageRequest> getLogUsages() {
        return logUsages;
    }


    public void setLogUsages(List<LogUsageRequest> logUsages) {
        this.logUsages = logUsages;
    }


    public String[] getSelectedIndex() {
        return selectedIndex;
    }


    public void setSelectedIndex(String[] selectedIndex) {
        this.selectedIndex = selectedIndex;
    }


    public String[] getUpdTitles() {
        return updTitles;
    }


    public void setUpdTitles(String[] updTitles) {
        this.updTitles = updTitles;
    }


    public String[] getUpdArtists() {
        return updArtists;
    }


    public void setUpdArtists(String[] updArtists) {
        this.updArtists = updArtists;
    }


    public String[] getUpdUsetypes() {
        return updUsetypes;
    }


    public void setUpdUsetypes(String[] updUsetypes) {
        this.updUsetypes = updUsetypes;
    }


    public String[] getUpdDurations() {
        return updDurations;
    }


    public void setUpdDurations(String[] updDurations) {
        this.updDurations = updDurations;
    }


    public String[] getUpdInstvocals() {
        return updInstvocals;
    }


    public void setUpdInstvocals(String[] updInstvocals) {
        this.updInstvocals = updInstvocals;
    }


    public String[] getUpdWorkids() {
        return updWorkids;
    }


    public void setUpdWorkids(String[] updWorkids) {
        this.updWorkids = updWorkids;
    }


    public String[] getUpdStatuses() {
        return updStatuses;
    }


    public void setUpdStatuses(String[] updStatuses) {
        this.updStatuses = updStatuses;
    }


    public String[] getUpdPlays() {
        return updPlays;
    }


    public void setUpdPlays(String[] updPlays) {
        this.updPlays = updPlays;
    }


    public String[] getUpdWrkperfids() {
        return updWrkperfids;
    }


    public void setUpdWrkperfids(String[] updWrkperfids) {
        this.updWrkperfids = updWrkperfids;
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


    public String[] getInsTitles() {
        return insTitles;
    }


    public void setInsTitles(String[] insTitles) {
        this.insTitles = insTitles;
    }


    public String[] getInsArtists() {
        return insArtists;
    }


    public void setInsArtists(String[] insArtists) {
        this.insArtists = insArtists;
    }


    public String[] getInsWorkids() {
        return insWorkids;
    }


    public void setInsWorkids(String[] insWorkids) {
        this.insWorkids = insWorkids;
    }


    public String[] getInsDurations() {
        return insDurations;
    }


    public void setInsDurations(String[] insDurations) {
        this.insDurations = insDurations;
    }


    public String[] getInsPlays() {
        return insPlays;
    }


    public void setInsPlays(String[] insPlays) {
        this.insPlays = insPlays;
    }


    public String[] getInsUsetypes() {
        return insUsetypes;
    }


    public void setInsUsetypes(String[] insUsetypes) {
        this.insUsetypes = insUsetypes;
    }


    public String[] getInsInstvocals() {
        return insInstvocals;
    }


    public void setInsInstvocals(String[] insInstvocals) {
        this.insInstvocals = insInstvocals;
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




    public int getLogRequestPg() {
        return logRequestPg;
    }


    public void setLogRequestPg(int logRequestPg) {
        this.logRequestPg = logRequestPg;
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








}
