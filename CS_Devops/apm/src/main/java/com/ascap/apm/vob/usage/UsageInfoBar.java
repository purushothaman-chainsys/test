package com.ascap.apm.vob.usage;

import java.util.List;

import com.ascap.apm.vob.BaseInfoBarVOB;

/**
 * UsageInfoBar is a VOB to hold the InfoBars used in Usage Module.
 * 
 * @author Jaya Shyam Narayana Lingamchetty
 * @version $Revision: 1.4 $ $Date: Mar 18 2004 18:36:58 $
 */
public class UsageInfoBar extends BaseInfoBarVOB {

    private static final long serialVersionUID = 4638731708661350065L;

    private List<Object> musicUserInfo = null;

    private List<Object> programUserInfo = null;

    private List<Object> sampleSrchCriteriaInfo = null;

    // Iteration II Coding Start
    private List<Object> contextSearchCriteriaInfo = null;

    private List<Object> programPerformanceInfo = null;

    private List<Object> workPerformanceInfo = null;
    // Iteration II Coding End

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "";
    }

    /**
     * Returns the musicUserInfo.
     * 
     * @return List<Serializable>
     */
    public List<Object> getMusicUserInfo() {
        return musicUserInfo;
    }

    /**
     * Sets the musicUserInfo.
     * 
     * @param musicUserInfo The musicUserInfo to set
     */
    public void setMusicUserInfo(List<Object> musicUserInfo) {
        this.musicUserInfo = musicUserInfo;
    }

    /**
     * Returns the sampleSrchCriteriaInfo.
     * 
     * @return List<Serializable>
     */
    public List<Object> getSampleSrchCriteriaInfo() {
        return sampleSrchCriteriaInfo;
    }

    /**
     * Sets the sampleSrchCriteriaInfo.
     * 
     * @param sampleSrchCriteriaInfo The sampleSrchCriteriaInfo to set
     */
    public void setSampleSrchCriteriaInfo(List<Object> sampleSrchCriteriaInfo) {
        this.sampleSrchCriteriaInfo = sampleSrchCriteriaInfo;
    }

    /**
     * Returns the programUserInfo.
     * 
     * @return List<Serializable>
     */
    public List<Object> getProgramUserInfo() {
        return programUserInfo;
    }

    /**
     * Sets the programUserInfo.
     * 
     * @param programUserInfo The programUserInfo to set
     */
    public void setProgramUserInfo(List<Object> programUserInfo) {
        this.programUserInfo = programUserInfo;
    }

    /**
     * Returns the contextSearchCriteriaInfo.
     * 
     * @return List<Serializable>
     */
    public List<Object> getContextSearchCriteriaInfo() {
        return contextSearchCriteriaInfo;
    }

    /**
     * Returns the programPerformanceInfo.
     * 
     * @return List<Serializable>
     */
    public List<Object> getProgramPerformanceInfo() {
        return programPerformanceInfo;
    }

    /**
     * Returns the workPerformanceInfo.
     * 
     * @return List<Serializable>List<Serializable>
     */
    public List<Object> getWorkPerformanceInfo() {
        return workPerformanceInfo;
    }

    /**
     * Sets the contextSearchCriteriaInfo.
     * 
     * @param contextSearchCriteriaInfo The contextSearchCriteriaInfo to set
     */
    public void setContextSearchCriteriaInfo(List<Object> contextSearchCriteriaInfo) {
        this.contextSearchCriteriaInfo = contextSearchCriteriaInfo;
    }

    /**
     * Sets the programPerformanceInfo.
     * 
     * @param programPerformanceInfo The programPerformanceInfo to set
     */
    public void setProgramPerformanceInfo(List<Object> programPerformanceInfo) {
        this.programPerformanceInfo = programPerformanceInfo;
    }

    /**
     * Sets the workPerformanceInfo.
     * 
     * @param workPerformanceInfo The workPerformanceInfo to set
     */
    public void setWorkPerformanceInfo(List<Object> workPerformanceInfo) {
        this.workPerformanceInfo = workPerformanceInfo;
    }

}
