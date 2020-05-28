package com.ascap.apm.vob.admin;

import java.io.Serializable;
import java.util.List;

import com.ascap.apm.vob.BaseSearchVOB;

/**
 * @author Raju_Ayanampudi To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class PrepConfigParamList extends BaseSearchVOB {

    private static final long serialVersionUID = 4971588090715906972L;

    private String[] paramIds;

    private List<Serializable> prepConfigParamCollection;

    private String filter = "active";

    private String actionType;

    /**
     * Returns the actionType.
     * 
     * @return String
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * Returns the filter.
     * 
     * @return String
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Returns the paramIds.
     * 
     * @return String[]
     */
    public String[] getParamIds() {
        return paramIds;
    }

    /**
     * Returns the prepConfigParamCollection.
     * 
     * @return Collection
     */
    public List<Serializable> getPrepConfigParamCollection() {
        return prepConfigParamCollection;
    }

    /**
     * Sets the actionType.
     * 
     * @param actionType The actionType to set
     */
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    /**
     * Sets the filter.
     * 
     * @param filter The filter to set
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * Sets the paramIds.
     * 
     * @param paramIds The paramIds to set
     */
    public void setParamIds(String[] paramIds) {
        this.paramIds = paramIds;
    }

    /**
     * Sets the prepConfigParamCollection.
     * 
     * @param prepConfigParamList<Serializable> The prepConfigParamList<Serializable> to set
     */
    public void setPrepConfigParamCollection(List<Serializable> prepConfigParamCollection) {
        this.prepConfigParamCollection = prepConfigParamCollection;
    }

}
