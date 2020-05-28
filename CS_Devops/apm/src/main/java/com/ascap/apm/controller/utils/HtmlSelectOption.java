package com.ascap.apm.controller.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.common.utils.cache.PrepKeyValueObject;
import com.ascap.apm.common.utils.cache.lookup.LookupTableCache;
import com.ascap.apm.common.utils.cache.lookup.LookupTables;

/**
 * This class is a bean, used to represent one option in an HTML drop-down 'select' list. It contains two properties -
 * see {@link getDisplayName()} and {@link getInternalId()} for a description. Useful in a struts Form class for
 * constructing a select list to pass to the jsp with the <tt><html:select></tt> and <tt><html:option></tt> tags.
 *
 * @author Saravanan Sengottaiyan
 */
public class HtmlSelectOption implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3955471625434196775L;

    private String id;

    private String displayName;

    protected static LogHelper log = new LogHelper("HtmlSelectOption");

    /**
     * Creates an HtmlSelectOption with the specified property values.
     * 
     * @param id the internal ID.
     * @param displayName the displayName.
     */
    public HtmlSelectOption() {
    }

    /**
     * Creates an HtmlSelectOption with the specified property values.
     * 
     * @param id the internal ID.
     * @param displayName the displayName.
     */
    public HtmlSelectOption(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    /**
     * Retrieves the 'display name': this is the name for this option that is shown to the system user through the
     * presentation layer (the <tt>labelProperty</tt> attribute of the <tt><html:option></tt> struts tag).
     * 
     * @return the display name for this option.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Retrieves the internal ID: this is the ID that is used internally by the application to identify this particular
     * option. (the <tt>property</tt> attribute of the <tt><html:option></tt> struts tag).
     * 
     * @return the internal id for this option.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the display name.
     * 
     * @param displayName the displayName.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Sets the internal Id.
     * 
     * @param internalId the internal Id.
     */
    public void setId(String internalId) {
        this.id = internalId;
    }

    /*
     * Returns a collection when given a map
     */
    public static List<Object> getOptionsValues(Map<?, ?> hmap) {

        List<Object> arrayList = new ArrayList<>();
        Set<?> se = hmap.keySet();
        Iterator<?> it = se.iterator();

        while (it.hasNext()) {
            Object key = it.next();
            Object test = hmap.get(key);
            arrayList.add(new HtmlSelectOption(key.toString(), test.toString()));
        }
        Collections.sort(arrayList, new DescriptionComparator());
        return arrayList;

    }

    /**
     * Method getOptionsValues.
     * 
     * @param arrList
     * @return Collection of originalDBOrderedItems
     */
    public static List<Object> getOptionsValues(List<?> arrList) {

        ArrayList<Object> arrayList = new ArrayList<>();
        Iterator<?> it = arrList.iterator();

        PrepKeyValueObject prepKeyValueObject = null;

        while (it.hasNext()) {
            prepKeyValueObject = (PrepKeyValueObject) it.next();
            String key = prepKeyValueObject.getKey();
            String value = prepKeyValueObject.getValue();
            arrayList.add(new HtmlSelectOption(key, value));
        }
        return arrayList;
    }

    /**
     * Given a lookUpKey, it returns a Collection of key value pairs.
     * 
     * @param lookUpKey lookUpKey name from lookuptables config file.
     * @return a Collection of key value pairs.
     */
    public static List<Object> getLookUpTable(String lookUpKey) {

        LookupTableCache xLkpCache;
        List<?> xList = new ArrayList<>();

        List<Object> col = null;
        try {
            xLkpCache = LookupTables.getLookupTable(lookUpKey);
            xList = xLkpCache.getOriginalDBOrderedItemsWithKeys();
        } catch (Exception e) {
            log.debug("Lookup Eexception: ", e);
            Map<String, String> xmap = new HashMap<>();
            xmap.put("", "Lookup failed");
            return getOptionsValues(xmap);
        }
        col = getOptionsValues(xList);
        return col;

    }

    /**
     * Given a lookUpKey, it returns a Collection of key value pairs.
     * 
     * @param lookUpKey lookUpKey name from lookuptables config file.
     * @return a Collection of key value pairs.
     */
    public static Collection<Object> getLookUpTableModified(String lookUpKey, String[] delKeys) {

        // get the collection from the table
        Collection<Object> col = getLookUpTable(lookUpKey);

        if ((delKeys == null) || (delKeys.length == 0))
            return col;

        // create an arrayList from the string[] of delKeys
        Collection<Object> delEntries = new ArrayList<>();
        for (int i = 0; i < delKeys.length; i++) {
            delEntries.add((Object) delKeys[i]);
        }

        // loop throught the collection and remove the entries present in the delEntries
        Iterator<?> itr = col.iterator();
        HtmlSelectOption htmlSelectOption = null;
        while (itr.hasNext()) {
            htmlSelectOption = (HtmlSelectOption) itr.next();
            if (delEntries.contains((htmlSelectOption.getId())))
                itr.remove();
        }

        return col;

    }

    /**
     * Given a lookUpKey and an id, it returns the description (or value) for that id.
     * 
     * @param lookUpKey lookUpKey name from lookuptables config file.
     * @param id the id for which description is needed.
     * @return the description (or value).
     */
    public static String getLookUpTable(String lookUpKey, String id) {

        LookupTableCache xLkpCache;
        Map<Object, Serializable> xmap = null;

        try {
            xLkpCache = LookupTables.getLookupTable(lookUpKey);
            xmap = xLkpCache.getItemsWithKeys();
        } catch (Exception e) {
            log.debug("Lookup Eexception: lookUpKey :'" + lookUpKey + "',id='" + id + "'");
            log.debug("Lookup Eexception: ", e);
            xmap = new HashMap<>();
            xmap.put("", "Lookup failed");
        }
        Object desc = null;
        if (id != null) {
            desc = xmap.get(id);
        } else {
            log.debug("Lookup Exception: lookUpKey :'" + lookUpKey + "',id='" + id + "'");
        }
        if (desc != null) {
            return String.valueOf(desc);
        } else {
            return "";
        }
    }

    /**
     * overriding this method is important for getting the correct object from a collection since the equals method
     * should tell it equal if just the id is matching :) and one more important thing a null id is treated as not equal
     * so returns false ;).
     * 
     * @author Jaya Shyam Narayana Lingamchetty
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object that) {
        if (that == null) {
            return false;
        }
        if (this.id == null) {
            return false;
        }
        return this.id.equals(that);

    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder outBuff;
        outBuff = new StringBuilder();
        outBuff.append("HtmlSelectOption{id='").append(this.id).append("', displayName='").append(this.displayName)
            .append("'}");
        return (outBuff.toString());
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        if (this.id != null) {
            return this.id.hashCode();
        } else {
            return 0;
        }
    }

}
