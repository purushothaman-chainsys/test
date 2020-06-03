package com.ascap.apm.common.utils.cache;

import com.ascap.apm.vob.BaseVOB;

/**
 * @author Manoj_Puli To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class PrepKeyValueObject extends BaseVOB {

    private static final long serialVersionUID = 7880747620056602112L;

    private String key = null;

    private String value = null;

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "";
    }

    /**
     * Returns the key.
     * 
     * @return String
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the value.
     * 
     * @return String
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the key.
     * 
     * @param key The key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Sets the value.
     * 
     * @param value The value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

}
