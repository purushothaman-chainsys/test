package com.ascap.apm.vob;

import java.util.List;

import com.ascap.apm.common.context.UserPreference;
import com.ascap.apm.common.helpers.PrepExtPropertyHelper;
import com.ascap.apm.common.utils.constants.Constants;
import com.ascap.apm.common.utils.constants.PrepPropertiesConstants;

/**
 * BaseSearchVOB that contains the search results, seach criteria that was used for search, column need tobe sorted on,
 * the record index need tobe retrieved from, total number of reords found and the UserPreference object. their search
 * criteria parameters that are specific to the search.
 * 
 * @author Raju_Ayanampudi
 * @version $Revision: 1.18 $ $Date: Jan 19 2011 17:46:04 $
 */
public class BaseSearchVOB extends BaseVOB {

    private static final long serialVersionUID = -1918901910652037897L;

    int currentPageNumber = 0;

    int totalPages = -1;

    boolean sSAConfigured;

    // column name to be sorted on
    String sortColumn;

    // order to be displayed.
    String sortOrder;

    // index of the search result records that can be used for pagination.
    int index;

    // User preferences
    UserPreference userPreferences;

    // List<Serializable> that contains the SearchResult VOBS.
    private List<Object> searchResults;

    // total number of records found for that search. Will be set to the threshold if exceeds.
    int numberOfRecordsFound;

    // total number of records found for that search.
    int numberOfRecordsFoundbySearch;

    // first/last/prev/next button
    String navigationType;

    String pageNumber;

    // Alternate Search
    BaseSearchVOB alternateSearch;

    String showBackToSearchButton;

    private String paginationOverrideRequired = "N";

    private String backToSearchResults;

    /**
     * All Value Objects/beans MUST implement this method for easy debugging and also for easy intrepreation of the
     * object return a valid string constructed out of all the meaningful attributes in the VOB
     */
    public String toString() {
        return "The total number of records found are :" + numberOfRecordsFound;
    }

    /**
     * Returns the numberOfRecordsFound.
     * 
     * @return int
     */
    public int getNumberOfRecordsFound() {
        return numberOfRecordsFound;
    }

    public List<Object> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Object> searchResults) {
        this.searchResults = searchResults;
    }

    public void setNumberOfRecordsFound(int numberOfRecordsFound) {
        this.setNumberOfRecordsFound(numberOfRecordsFound, true);
    }

    /**
     * Sets the numberOfRecordsFound. And calculates the index based on the navigation button selected.
     * 
     * @param The total numberOfRecordsFound
     */
    public void setNumberOfRecordsFound(int numberOfRecordsFound, boolean limitToMaxThreshold) {
        this.numberOfRecordsFoundbySearch = numberOfRecordsFound;
        this.numberOfRecordsFound = numberOfRecordsFound;

        int maxSearchResultsThreshold = Integer.parseInt(
            PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.MAX_SEARCHRESULTS_THRESHOLD));

        if (this.userPreferences != null) {
            maxSearchResultsThreshold = this.userPreferences.getMaxSearchResults();
        }

        if (limitToMaxThreshold && numberOfRecordsFound > maxSearchResultsThreshold) {
            this.numberOfRecordsFound = maxSearchResultsThreshold;
        }

        if (!(this.navigationType == null || "".equalsIgnoreCase(this.navigationType))
            && this.userPreferences != null) {
            int resultsPerPage = this.userPreferences.getNofSrchRsltsPerPage();
            if (!this.navigationType.equalsIgnoreCase(Constants.PAGE_NAVIGATION_NEXT)
                && !this.navigationType.equalsIgnoreCase(Constants.PAGE_NAVIGATION_PREVIOUS)
                && !this.navigationType.equalsIgnoreCase(Constants.PAGE_NAVIGATION_CURRENT)
                && !this.navigationType.equalsIgnoreCase(Constants.PAGE_NAVIGATION_FIRST)
                && !this.navigationType.equalsIgnoreCase(Constants.PAGE_NAVIGATION_LAST)) {
                int page = 1;
                try {
                    page = Integer.parseInt(this.navigationType);
                    if (page <= 0)
                        page = 1;
                } catch (NumberFormatException nfe) {
                    page = 1;
                }
                this.index = (page - 1) * resultsPerPage;
                if (this.index >= this.numberOfRecordsFound)
                    navigationType = Constants.PAGE_NAVIGATION_LAST;
            }
            if (this.navigationType.equalsIgnoreCase(Constants.PAGE_NAVIGATION_NEXT)) {
                this.index = this.index + resultsPerPage;
            }
            if (this.navigationType.equalsIgnoreCase(Constants.PAGE_NAVIGATION_PREVIOUS)) {
                this.index = (this.index - resultsPerPage > 0) ? (this.index - resultsPerPage) : 0;
            }
            if (this.navigationType.equalsIgnoreCase(Constants.PAGE_NAVIGATION_CURRENT)) {
                // Use the existing index
            }
            if (this.navigationType.equalsIgnoreCase(Constants.PAGE_NAVIGATION_FIRST)) {
                this.index = 0;
            }
            if (this.navigationType.equalsIgnoreCase(Constants.PAGE_NAVIGATION_LAST)) {
                if (this.numberOfRecordsFound % resultsPerPage > 0) {
                    this.index = (this.numberOfRecordsFound / resultsPerPage) * resultsPerPage;
                } else {
                    this.index = this.numberOfRecordsFound - resultsPerPage;
                }
            }
        }

        this.navigationType = null;
    }

    /**
     * Returns the index.
     * 
     * @return int
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the sortColumn.
     * 
     * @return String
     */
    public String getSortColumn() {
        return sortColumn;
    }

    /**
     * Returns the sortOrder.
     * 
     * @return String
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * Returns the userPreferences.
     * 
     * @return UserPreference
     */
    public UserPreference getUserPreferences() {
        return userPreferences;
    }

    /**
     * Sets the index.
     * 
     * @param index The index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Sets the sortColumn.
     * 
     * @param sortColumn The sortColumn to set
     */
    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    /**
     * Sets the sortOrder.
     * 
     * @param sortOrder The sortOrder to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Sets the userPreferences.
     * 
     * @param userPreferences The userPreferences to set
     */
    public void setUserPreferences(UserPreference userPreferences) {
        this.userPreferences = userPreferences;
    }

    /**
     * Returns the navigationType.
     * 
     * @return String
     */
    public String getNavigationType() {
        return navigationType;
    }

    /**
     * Sets the navigationType.
     * 
     * @param navigationType The navigationType to set
     */
    public void setNavigationType(String navigationType) {
        this.navigationType = navigationType;
    }

    /**
     * Returns the alternateSearch.
     * 
     * @return BaseSearchVOB
     */
    public BaseSearchVOB getAlternateSearch() {
        return alternateSearch;
    }

    /**
     * Sets the alternateSearch.
     * 
     * @param alternateSearch The alternateSearch to set
     */
    public void setAlternateSearch(BaseSearchVOB alternateSearch) {
        this.alternateSearch = alternateSearch;
    }

    /**
     * Gives the index of the row number from which record to be loaded.
     * 
     * @return int
     */
    public int getFromIndex() {
        if ("Y".equals(this.getPaginationOverrideRequired())) {
            return 1;
        }

        // Need to be added 1, as the index starts from 0 where as the ROW_NUM starts from 1.
        return this.index + 1;
    }

    /**
     * Gives the index of the row number until which record to be loaded.
     * 
     * @return int
     */
    public int getToIndex() {
        int noOfResultsPerPage = 0;
        if (this.userPreferences != null) {
            noOfResultsPerPage = userPreferences.getNofSrchRsltsPerPage();
        }
        if ("Y".equals(this.getPaginationOverrideRequired())) {
            return this.index + numberOfRecordsFound + 1;
        }

        // Need to be added 1, as the index starts from 0 where as the ROW_NUM starts from 1.
        int toIndex = this.index + noOfResultsPerPage + 1;
        if (this.index + noOfResultsPerPage > this.numberOfRecordsFound) {
            toIndex = this.numberOfRecordsFound + 1;
        }
        return toIndex;
    }

    public int getToIndexWithoutCount() {
        int noOfResultsPerPage = 0;
        if (this.userPreferences != null) {
            noOfResultsPerPage = userPreferences.getNofSrchRsltsPerPage();
        }
        if ("Y".equals(this.getPaginationOverrideRequired())) {
            return this.index + numberOfRecordsFound + 1;
        }

        // Need to be added 1, as the index starts from 0 where as the ROW_NUM starts from 1.
        return this.index + noOfResultsPerPage + 1;
    }

    /**
     * Returns the sSAConfigured.
     * 
     * @return boolean
     */
    public boolean isSSAConfigured() {
        return sSAConfigured;
    }

    /**
     * Sets the sSAConfigured.
     * 
     * @param sSAConfigured The sSAConfigured to set
     */
    public void setSSAConfigured(boolean sSAConfigured) {
        this.sSAConfigured = sSAConfigured;
    }

    /**
     * Returns the numberOfRecordsFoundbySearch.
     * 
     * @return int
     */
    public int getNumberOfRecordsFoundbySearch() {
        return numberOfRecordsFoundbySearch;
    }

    /**
     * Sets the numberOfRecordsFoundbySearch.
     * 
     * @param numberOfRecordsFoundbySearch The numberOfRecordsFoundbySearch to set
     */
    public void setNumberOfRecordsFoundbySearch(int numberOfRecordsFoundbySearch) {
        this.numberOfRecordsFoundbySearch = numberOfRecordsFoundbySearch;
    }

    /**
     * Sets the paginationOverrideRequired.
     * 
     * @param paginationOverrideRequired The paginationOverrideRequired to set
     */
    public void setPaginationOverrideRequired(String paginationOverrideRequired) {
        this.paginationOverrideRequired = paginationOverrideRequired;
    }

    /**
     * Returns the paginationOverrideRequired.
     * 
     * @return String
     */
    public String getPaginationOverrideRequired() {
        return paginationOverrideRequired;
    }

    /**
     * @return Returns the pageNumber.
     */
    public String getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber The pageNumber to set.
     */
    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * @return Returns the showBackToSearchButton.
     */
    public String getShowBackToSearchButton() {
        return showBackToSearchButton;
    }

    /**
     * @param showBackToSearchButton The showBackToSearchButton to set.
     */
    public void setShowBackToSearchButton(String showBackToSearchButton) {
        this.showBackToSearchButton = showBackToSearchButton;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPageNumber() {
        int currentPageNumber = 0;
        if ((userPreferences != null) && (userPreferences.getNofSrchRsltsPerPage() > 0))
            currentPageNumber = index / userPreferences.getNofSrchRsltsPerPage() + 1;

        return currentPageNumber;
    }

    /**
     * Returns the totalPages.
     * 
     * @return int
     */
    public int getTotalPages() {
        int totalPage = -1;
        if ((userPreferences != null) && (userPreferences.getNofSrchRsltsPerPage() > 0))
            totalPage = numberOfRecordsFound % userPreferences.getNofSrchRsltsPerPage() > 0
                ? numberOfRecordsFound / userPreferences.getNofSrchRsltsPerPage() + 1
                : numberOfRecordsFound / userPreferences.getNofSrchRsltsPerPage();
        return totalPage;
    }

    public String getBackToSearchResults() {
        return backToSearchResults;
    }

    public void setBackToSearchResults(String backToSearchResults) {
        this.backToSearchResults = backToSearchResults;
    }

}
