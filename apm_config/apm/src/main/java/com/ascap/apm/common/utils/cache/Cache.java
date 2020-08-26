package com.ascap.apm.common.utils.cache;

import java.io.Serializable;
import java.util.Map;

import com.ascap.apm.common.exception.cache.CacheLockedException;
import com.ascap.apm.common.exception.cache.InvalidCacheException;
import com.ascap.apm.common.exception.cache.KeyNotFoundException;

/**
 * @author Sudhar_Krishnamachar Base Inteface for any type of cache
 */
public interface Cache {

    public void flush() throws InvalidCacheException;

    public Object get(Object aKey) throws KeyNotFoundException, InvalidCacheException;

    public void add(Object aKey, Serializable aValue) throws InvalidCacheException, CacheLockedException;

    public void addAll(Map<Object, Serializable> aMap) throws InvalidCacheException, CacheLockedException;

    public boolean contains(Object aKey) throws InvalidCacheException;

    public void remove(Object aKey) throws InvalidCacheException, CacheLockedException;

    public int size() throws InvalidCacheException;

}
