package com.ascap.apm.common.utils.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ascap.apm.common.exception.cache.CacheLockedException;
import com.ascap.apm.common.exception.cache.InvalidCacheException;
import com.ascap.apm.common.exception.cache.KeyNotFoundException;

/**
 * @author Sudhar_Krishnamachar Base Cache Class for PREP Modules
 */
public class BaseCache implements Cache, Serializable {

    private static final long serialVersionUID = -8769963748853754137L;

    /**
     * hashtable that holds the cache items
     */
    protected HashMap<Object, Serializable> cacheItems;

    private List<Serializable> originalDBOrderedItems;

    /**
     * string that identifies this cache. A user friendly name to cache
     */
    private String cacheIdentifier;

    /**
     * a flag that indicates if this cache is locked
     */
    protected boolean locked;

    /**
     * Constructor for BaseCache.
     */
    public BaseCache() {
        super();
        cacheItems = new HashMap<>();
        originalDBOrderedItems = new ArrayList<>();
    }

    /**
     * Constructor for BaseCache with initial size.
     */
    public BaseCache(int initSize) {
        super();
        cacheItems = new HashMap<>(initSize);
        originalDBOrderedItems = new ArrayList<>(initSize);
    }

    /**
     * @see com.ascap.apm.common.utils.cache.Cache#flush()
     */
    public void flush() throws InvalidCacheException {
        if (cacheItems != null) {
            cacheItems.clear();
            originalDBOrderedItems.clear();
        }
        throw new InvalidCacheException("utils.cache.cachenull");
    }

    /**
     * @see com.ascap.apm.common.utils.cache.Cache#get(Object)
     */
    public Object get(Object aKey) throws KeyNotFoundException, InvalidCacheException {
        if (cacheItems != null) {
            Object obj = cacheItems.get(aKey);
            if (obj == null) {
                String[] errorStrings = new String[2];
                errorStrings[0] = aKey.toString(); // using the strig version of the object
                errorStrings[1] = getCacheIdentifier();
                throw new KeyNotFoundException("utils.cache.keynotfound", errorStrings);
            }
            return obj;
        } else
            throw new InvalidCacheException("utils.cache.cachenull");
    }

    /**
     * @see com.ascap.apm.common.utils.cache.Cache#add(Object, Object)
     */
    public void add(Object aKey, Serializable aValue) throws InvalidCacheException, CacheLockedException {
        if (cacheItems != null) {
            if (isLocked()) {
                throw new CacheLockedException("cache with cache id " + getCacheIdentifier() + " is read only");
            }
            cacheItems.put(aKey, aValue);

            PrepKeyValueObject prepKeyValueObject = new PrepKeyValueObject();
            prepKeyValueObject.setKey(aKey.toString());
            prepKeyValueObject.setValue(aValue.toString());

            originalDBOrderedItems.add(prepKeyValueObject);
        } else
            throw new InvalidCacheException("utils.cache.cachenull");
    }

    /**
     * @see com.ascap.apm.common.utils.cache.Cache#addAll(Map)
     */
    public void addAll(Map<Object, Serializable> aMap) throws InvalidCacheException, CacheLockedException {
        if (cacheItems != null) {
            if (isLocked()) {
                String[] errorStrings = new String[1];
                errorStrings[0] = getCacheIdentifier();
                throw new CacheLockedException("utils.cache.cachelocked", errorStrings);
            }
            cacheItems.putAll(aMap);
        } else
            throw new InvalidCacheException("utils.cache.cachenull");
    }

    /**
     * @see com.ascap.apm.common.utils.cache.Cache#contains(Object)
     */
    public boolean contains(Object aKey) throws InvalidCacheException {
        if (cacheItems != null) {
            return cacheItems.containsKey(aKey);
        } else
            throw new InvalidCacheException("utils.cache.cachenull");
    }

    /**
     * @see com.ascap.apm.common.utils.cache.Cache#remove(Object)
     */
    public void remove(Object aKey) throws InvalidCacheException, CacheLockedException {
        if (cacheItems != null) {
            if (isLocked()) {
                String[] errorStrings = new String[1];
                errorStrings[0] = getCacheIdentifier();
                throw new CacheLockedException("utils.cache.cachelocked", errorStrings);
            }
            cacheItems.remove(aKey);
        } else
            throw new InvalidCacheException("utils.cache.cachenull");
    }

    /**
     * @see com.ascap.apm.common.utils.cache.Cache#size(Object)
     */
    public int size() throws InvalidCacheException {
        if (cacheItems != null) {
            return cacheItems.size();
        } else
            throw new InvalidCacheException("utils.cache.cachenull");
    }

    /**
     * Returns the cacheIdentifier.
     * 
     * @return String
     */
    public String getCacheIdentifier() {
        return cacheIdentifier;
    }

    /**
     * Returns the locked.
     * 
     * @return boolean
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the cacheIdentifier.
     * 
     * @param cacheIdentifier The cacheIdentifier to set
     */
    public void setCacheIdentifier(String cacheIdentifier) {
        this.cacheIdentifier = cacheIdentifier;
    }

    /**
     * Sets the locked.
     * 
     * @param locked The locked to set
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Get Items in the cache as a collection This ONLY return the Values and NOT the keys This method can be overridden
     * by the derived classes to facilitate returning unmodifiable collections. The returns a modifiable collection
     */
    public List<Serializable> getItems() throws InvalidCacheException {
        if (cacheItems != null) {
            return new ArrayList<>(cacheItems.values());
        } else {
            throw new InvalidCacheException("utils.cache.cachenull");
        }
    }

    /**
     * Get a Map representation of key, value pair in the cache This method can be overridden by the derived classes to
     * facilitate returning unmodifiable collections. The returns a modifiable hash table
     */
    public Map<Object, Serializable> getItemsWithKeys() throws InvalidCacheException {
        if (cacheItems != null) {
            return cacheItems;
        } else
            throw new InvalidCacheException("utils.cache.cachenull");
    }

    /**
     * Method getOriginalDBOrderedItemsWithKeys.
     * 
     * @return List
     * @throws InvalidCacheException
     */
    public List<Serializable> getOriginalDBOrderedItemsWithKeys() throws InvalidCacheException {
        if (originalDBOrderedItems != null) {
            return originalDBOrderedItems;
        } else {
            throw new InvalidCacheException("utils.cache.cachenull");
        }
    }

}
