package com.ascap.apm.common.utils.cache.lookup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ascap.apm.common.exception.cache.InvalidCacheException;
import com.ascap.apm.common.exception.cache.KeyNotFoundException;
import com.ascap.apm.common.utils.cache.BaseCache;

/**
 * @author Sudhar_Krishnamachar LookupTable cache thats used to load look up tables
 */
public class LookupTableCache extends BaseCache {

    /**
     * 
     */
    private static final long serialVersionUID = -4986861474530339730L;

    private static final String UTILS_CACHE_CACHENULL = "utils.cache.cachenull";

    /**
     * Constructor for LookupTableCache.
     */
    public LookupTableCache() {
        super();
    }

    /**
     * Constructor for LookupTableCache.
     * 
     * @param initSize
     */
    public LookupTableCache(int initSize) {
        super(initSize);
    }

    /**
     * gets a specific key from the look up table cache
     */
    public String get(String aKey) throws KeyNotFoundException {
        if (cacheItems != null) {
            String keyValue;
            try {
                keyValue = (String) super.get(aKey);
            } catch (InvalidCacheException e) {
                throw new KeyNotFoundException(UTILS_CACHE_CACHENULL);
            }
            return keyValue;
        }
        throw new KeyNotFoundException(UTILS_CACHE_CACHENULL);
    }

    /**
     * gets the cache table id for this cache
     */
    public String getLookupTableId() {
        return super.getCacheIdentifier();
    }

    public String toString() {
        return cacheItems.toString();
    }

    /**
     * Get Items in the cache as a UnModifiable Collection This ONLY return the Values and NOT the keys This method is
     * overridden. The base class has an exact method that returns a modifiable collection. LookupTable cache items
     * cannot be modified at run time.
     */
    @Override
    public List<Serializable> getItems() throws InvalidCacheException {
        if (cacheItems != null) {
            return new ArrayList<>(Collections.unmodifiableCollection(cacheItems.values()));
        } else
            throw new InvalidCacheException(UTILS_CACHE_CACHENULL);
    }

    /**
     * Get the un modifiable Map with key, value pair in the cache This method is overridden. The base class has an
     * exact method that returns a modifiable hashtable. LookupTable cache items cannot be modified at run time.
     */
    @Override
    public Map<Object, Serializable> getItemsWithKeys() throws InvalidCacheException {
        if (cacheItems != null) {
            return Collections.unmodifiableMap(cacheItems);
        } else
            throw new InvalidCacheException(UTILS_CACHE_CACHENULL);
    }

}
