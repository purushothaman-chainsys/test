package com.ascap.apm.common.utils.cache.lookup;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ascap.apm.common.exception.cache.InvalidCacheException;
import com.ascap.apm.common.exception.cache.KeyNotFoundException;

/**
 * @author Sudhar_Krishnamachar Lookup Tables that will be used by the entire PREP application to get look up data
 */
public class LookupTables {

    /**
     * Constructor for LookupTables.
     */
    private LookupTables() {
        super();
    }

    /**
     * Method getLookupTableItemDescription. Returns a description for a specific item key in a specifc look up table
     * 
     * @param lookupTableKey String key to the look up table, has to be exactly same as what is in the the
     *            prep_lookuptable.xml file.
     * @param lookupTableItemKey String key to the specific item in the lookup table
     * @return String value of the specific key in specified look up table
     * @throws InvalidCacheException
     * @throws KeyNotFoundException
     */
    public static String getLookupTableItemDescription(String lookupTableKey, String lookupTableItemKey)
        throws InvalidCacheException, KeyNotFoundException {
        return LookupTablesHelper.getInstance().getLookupTableItemDescription(lookupTableKey, lookupTableItemKey);
    }

    /**
     * Method getLookupTableItems. Returns Read Only Collection of items, values ONLY without Keys in a specifc look up
     * table
     * 
     * @param lookupTableKey String key to the look up table, has to be exactly same as what is in the the
     *            prep_lookuptable.xml file.
     * @return Collection Collection of items
     * @throws InvalidCacheException
     */
    public static List<Serializable> getLookupTableItems(String lookupTableKey) throws InvalidCacheException {
        LookupTableCache lookupTableCache = LookupTablesHelper.getInstance().getLookupTable(lookupTableKey);
        if (lookupTableCache != null) {
            return lookupTableCache.getItems();
        } else
            throw new InvalidCacheException("error.utils.cache.cachenull");
    }

    /**
     * Method getLookupTableItems. Returns Read Only Map of items with key value pairs in a specifc look up table
     * 
     * @param lookupTableKey String key to the look up table, has to be exactly same as what is in the the
     *            prep_lookuptable.xml file.
     * @return Collection Collection of items
     * @throws InvalidCacheException
     */
    public static Map<Object, Serializable> getLookupTableItemsWithKeys(String lookupTableKey)
        throws InvalidCacheException {
        LookupTableCache lookupTableCache = LookupTablesHelper.getInstance().getLookupTable(lookupTableKey);
        if (lookupTableCache != null) {
            return lookupTableCache.getItemsWithKeys();
        } else
            throw new InvalidCacheException("error.utils.cache.cachenull");
    }

    /**
     * Method getLookupTable. get the specific lookup table cache object for the give table key
     * 
     * @param lookupTableKey String key to the look up table, has to be exactly same as what is in the the
     *            prep_lookuptable.xml file.
     * @return LookupTableCache
     * @throws InvalidCacheException
     */
    public static LookupTableCache getLookupTable(String lookupTableKey) throws InvalidCacheException {
        return LookupTablesHelper.getInstance().getLookupTable(lookupTableKey);
    }

    public static String getSingleOutputValue(String valueType) throws Exception {
        return LookupTablesHelper.getInstance().getSingleOutputValue(valueType);
    }

}
