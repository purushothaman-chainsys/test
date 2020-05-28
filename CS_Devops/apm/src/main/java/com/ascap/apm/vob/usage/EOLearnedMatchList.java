package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseSearchVOB;

public class EOLearnedMatchList extends BaseSearchVOB {


    /**
     * 
     */
    private static final long serialVersionUID = 2248710240604611994L;
    //	private String[] selectedIds;
    private String operationType;
    private String eoLearnedMatchType;


    private String filterMatchTypeCode;
    private String filterSupplierCode;
    private String filterWorkId;
    private String filterIdValue;
    private String filterMatchIdValue;
    private String filterMultWorkId;

    private String multWorkUrl;
    private String multWorkWorkId;
    private String multWorkMultWorkId;
    private String multWorkSelectedId;
    private String[] multWorkId;
    private String[] selectedIndex;
    private String[] lmIdWorkIdStr;
    private String multWorkSupplierCode;
    private String[] matchType;

    public String[] getMatchType() {
        return matchType;
    }

    public void setMatchType(String[] matchType) {
        this.matchType = matchType;
    }

    public String getMultWorkSupplierCode() {
        return multWorkSupplierCode;
    }

    public void setMultWorkSupplierCode(String multWorkSupplierCode) {
        this.multWorkSupplierCode = multWorkSupplierCode;
    }

    public String[] getLmIdWorkIdStr() {
        return lmIdWorkIdStr;
    }

    public void setLmIdWorkIdStr(String[] lmIdWorkIdStr) {
        this.lmIdWorkIdStr = lmIdWorkIdStr;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getEoLearnedMatchType() {
        return eoLearnedMatchType;
    }

    public void setEoLearnedMatchType(String eoLearnedMatchType) {
        this.eoLearnedMatchType = eoLearnedMatchType;
    }

    public String[] getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(String[] selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public String getFilterMatchTypeCode() {
        return filterMatchTypeCode;
    }

    public void setFilterMatchTypeCode(String filterMatchTypeCode) {
        this.filterMatchTypeCode = filterMatchTypeCode;
    }

    public String getFilterSupplierCode() {
        return filterSupplierCode;
    }

    public void setFilterSupplierCode(String filterSupplierCode) {
        this.filterSupplierCode = filterSupplierCode;
    }

    public String getFilterWorkId() {
        return filterWorkId;
    }

    public void setFilterWorkId(String filterWorkId) {
        this.filterWorkId = filterWorkId;
    }

    public String getFilterIdValue() {
        return filterIdValue;
    }

    public void setFilterIdValue(String filterIdValue) {
        this.filterIdValue = filterIdValue;
    }

    public String getFilterMatchIdValue() {
        return filterMatchIdValue;
    }

    public void setFilterMatchIdValue(String filterMatchIdValue) {
        this.filterMatchIdValue = filterMatchIdValue;
    }

    public String getMultWorkUrl() {
        return multWorkUrl;
    }

    public void setMultWorkUrl(String multWorkUrl) {
        this.multWorkUrl = multWorkUrl;
    }

    public String getMultWorkWorkId() {
        return multWorkWorkId;
    }

    public void setMultWorkWorkId(String multWorkWorkId) {
        this.multWorkWorkId = multWorkWorkId;
    }

    public String getMultWorkMultWorkId() {
        return multWorkMultWorkId;
    }

    public void setMultWorkMultWorkId(String multWorkMultWorkId) {
        this.multWorkMultWorkId = multWorkMultWorkId;
    }

    public String getMultWorkSelectedId() {
        return multWorkSelectedId;
    }

    public void setMultWorkSelectedId(String multWorkSelectedId) {
        this.multWorkSelectedId = multWorkSelectedId;
    }

    public String[] getMultWorkId() {
        return multWorkId;
    }

    public void setMultWorkId(String[] multWorkId) {
        this.multWorkId = multWorkId;
    }

    public String getFilterMultWorkId() {
        return filterMultWorkId;
    }

    public void setFilterMultWorkId(String filterMultWorkId) {
        this.filterMultWorkId = filterMultWorkId;
    }
}
