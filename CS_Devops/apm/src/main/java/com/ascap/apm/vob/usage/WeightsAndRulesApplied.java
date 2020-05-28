package com.ascap.apm.vob.usage;

import com.ascap.apm.vob.BaseVOB;

/**
 * This class has the Weights and Rules applied on a work performance during a distribution (as described in the Issue
 * 932). This information is available in the table CALC_WGT_HIST.
 * 
 * @author Jaya Shyam Narayana Lingamchetty
 */
public class WeightsAndRulesApplied extends BaseVOB {

    private static final long serialVersionUID = -8482049329368486692L;

    private String wPerformanceHeaderId = null;

    private String wWorkPerformanceId = null;

    /**
     * Distribution Id
     */
    private String wDistributionId = null;

    /**
     * � The Music User Weight that was actually applied to the performance. This field will contain the Music User
     * Weight that was assigned to or calculated for the Music User. For most Music User Types the value will be the
     * result of a �look- up� but for the types below the weights will be calculated during the distribution
     * calculation. o Pay Per View [Revenue for Program / Average Revenue for All Programs] o General Internet Streaming
     * (for programs containing non-Features) [{Distributable revenue/ Credit value} / Total points for site] o General
     * Internet Interactive [{Distributable revenue/ Credit value} / Total points for site] o General Ringtones
     * [{Distributable revenue/ Credit value} / Total points for site]
     */
    private String musicUserWeight = null;

    /**
     * � The Live Pop Economic Component [Revenue for Performance Header / Live Pop Average Revenue ]
     */
    private String livePopEconomicComponent = null;

    /**
     * � The Live Pop Music Density Component
     */
    private String livePopMusicDensityComponent = null;

    /**
     * � The Credits Per Site per Play (applicable only to General Internet Streaming credit calculation)
     */
    private String creditsPerSitePerPlay = null;

    /**
     * � The Points for Title (applicable only to General Internet Interactive and General Ringtones credit calculation)
     */
    private String pointsForTitle = null;

    /**
     * � The Use Weight that was applied to the performance
     */
    private String useWeight = null;

    /**
     * � The Use Weight Rule ID that was selected to determine the �Use Weight�.
     */
    private String useWeightRuleId = null;

    /**
     * � The Credit Symbol for the use Weight
     */
    private String creditSymbolForUseWeight = null;

    /**
     * � The Time of Day Weight that was applied to the performance
     */
    private String timeOfDayWeight = null;

    /**
     * � The Time of Day Indicator that was selected to determine the �Time of Day Weight�.
     */
    private String perSetCode = null;

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder outStrBuff;
        outStrBuff = new StringBuilder();
        outStrBuff.append("com.ascap.apm.common.vob.usage.WeightsAndRulesApplied {").append("wPerformanceHeaderId= '")
            .append(wPerformanceHeaderId).append("', ").append("wWorkPerformanceId= '").append(wWorkPerformanceId)
            .append("', ").append("wDistributionId= '").append(wDistributionId).append("', ")
            .append("musicUserWeight= '").append(musicUserWeight).append("', ").append("livePopEconomicComponent= '")
            .append(livePopEconomicComponent).append("', ").append("livePopMusicDensityComponent= '")
            .append(livePopMusicDensityComponent).append("', ").append("creditsPerSitePerPlay= '")
            .append(creditsPerSitePerPlay).append("', ").append("pointsForTitle= '").append(pointsForTitle)
            .append("', ").append("useWeight= '").append(useWeight).append("', ").append("useWeightRuleId= '")
            .append(useWeightRuleId).append("', ").append("creditSymbolForUseWeight= '")
            .append(creditSymbolForUseWeight).append("', ").append("timeOfDayWeight= '").append(timeOfDayWeight)
            .append("', ").append("perSetCode= '").append(perSetCode).append("', ").append("'}");
        return (outStrBuff.toString());
    }

    /**
     * Returns the creditsPerSitePerPlay.
     * 
     * @return String
     */
    public String getCreditsPerSitePerPlay() {
        return creditsPerSitePerPlay;
    }

    /**
     * Returns the creditSymbolForUseWeight.
     * 
     * @return String
     */
    public String getCreditSymbolForUseWeight() {
        return creditSymbolForUseWeight;
    }

    /**
     * Returns the livePopEconomicComponent.
     * 
     * @return String
     */
    public String getLivePopEconomicComponent() {
        return livePopEconomicComponent;
    }

    /**
     * Returns the livePopMusicDensityComponent.
     * 
     * @return String
     */
    public String getLivePopMusicDensityComponent() {
        return livePopMusicDensityComponent;
    }

    /**
     * Returns the musicUserWeight.
     * 
     * @return String
     */
    public String getMusicUserWeight() {
        return musicUserWeight;
    }

    /**
     * Returns the pointsForTitle.
     * 
     * @return String
     */
    public String getPointsForTitle() {
        return pointsForTitle;
    }

    /**
     * Returns the timeOfDayWeight.
     * 
     * @return String
     */
    public String getTimeOfDayWeight() {
        return timeOfDayWeight;
    }

    /**
     * Returns the useWeight.
     * 
     * @return String
     */
    public String getUseWeight() {
        return useWeight;
    }

    /**
     * Returns the useWeightRuleId.
     * 
     * @return String
     */
    public String getUseWeightRuleId() {
        return useWeightRuleId;
    }

    /**
     * Sets the creditsPerSitePerPlay.
     * 
     * @param creditsPerSitePerPlay The creditsPerSitePerPlay to set
     */
    public void setCreditsPerSitePerPlay(String creditsPerSitePerPlay) {
        this.creditsPerSitePerPlay = creditsPerSitePerPlay;
    }

    /**
     * Sets the creditSymbolForUseWeight.
     * 
     * @param creditSymbolForUseWeight The creditSymbolForUseWeight to set
     */
    public void setCreditSymbolForUseWeight(String creditSymbolForUseWeight) {
        this.creditSymbolForUseWeight = creditSymbolForUseWeight;
    }

    /**
     * Sets the livePopEconomicComponent.
     * 
     * @param livePopEconomicComponent The livePopEconomicComponent to set
     */
    public void setLivePopEconomicComponent(String livePopEconomicComponent) {
        this.livePopEconomicComponent = livePopEconomicComponent;
    }

    /**
     * Sets the livePopMusicDensityComponent.
     * 
     * @param livePopMusicDensityComponent The livePopMusicDensityComponent to set
     */
    public void setLivePopMusicDensityComponent(String livePopMusicDensityComponent) {
        this.livePopMusicDensityComponent = livePopMusicDensityComponent;
    }

    /**
     * Sets the musicUserWeight.
     * 
     * @param musicUserWeight The musicUserWeight to set
     */
    public void setMusicUserWeight(String musicUserWeight) {
        this.musicUserWeight = musicUserWeight;
    }

    /**
     * Sets the pointsForTitle.
     * 
     * @param pointsForTitle The pointsForTitle to set
     */
    public void setPointsForTitle(String pointsForTitle) {
        this.pointsForTitle = pointsForTitle;
    }

    /**
     * Sets the timeOfDayWeight.
     * 
     * @param timeOfDayWeight The timeOfDayWeight to set
     */
    public void setTimeOfDayWeight(String timeOfDayWeight) {
        this.timeOfDayWeight = timeOfDayWeight;
    }

    /**
     * Sets the useWeight.
     * 
     * @param useWeight The useWeight to set
     */
    public void setUseWeight(String useWeight) {
        this.useWeight = useWeight;
    }

    /**
     * Sets the useWeightRuleId.
     * 
     * @param useWeightRuleId The useWeightRuleId to set
     */
    public void setUseWeightRuleId(String useWeightRuleId) {
        this.useWeightRuleId = useWeightRuleId;
    }

    /**
     * Returns the perSetCode.
     * 
     * @return String
     */
    public String getPerSetCode() {
        return perSetCode;
    }

    /**
     * Sets the perSetCode.
     * 
     * @param perSetCode The perSetCode to set
     */
    public void setPerSetCode(String perSetCode) {
        this.perSetCode = perSetCode;
    }

    /**
     * Returns the wDistributionId.
     * 
     * @return String
     */
    public String getWDistributionId() {
        return wDistributionId;
    }

    /**
     * Returns the wPerformanceHeaderId.
     * 
     * @return String
     */
    public String getWPerformanceHeaderId() {
        return wPerformanceHeaderId;
    }

    /**
     * Returns the wWorkPerformanceId.
     * 
     * @return String
     */
    public String getWWorkPerformanceId() {
        return wWorkPerformanceId;
    }

    /**
     * Sets the wDistributionId.
     * 
     * @param wDistributionId The wDistributionId to set
     */
    public void setWDistributionId(String wDistributionId) {
        this.wDistributionId = wDistributionId;
    }

    /**
     * Sets the wPerformanceHeaderId.
     * 
     * @param wPerformanceHeaderId The wPerformanceHeaderId to set
     */
    public void setWPerformanceHeaderId(String wPerformanceHeaderId) {
        this.wPerformanceHeaderId = wPerformanceHeaderId;
    }

    /**
     * Sets the wWorkPerformanceId.
     * 
     * @param wWorkPerformanceId The wWorkPerformanceId to set
     */
    public void setWWorkPerformanceId(String wWorkPerformanceId) {
        this.wWorkPerformanceId = wWorkPerformanceId;
    }

}
