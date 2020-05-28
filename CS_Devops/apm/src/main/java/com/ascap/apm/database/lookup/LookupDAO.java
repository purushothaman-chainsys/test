package com.ascap.apm.database.lookup;

import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.exception.database.lookup.LookupTableLoadException;
import com.ascap.apm.common.utils.cache.lookup.LookupTableCache;

public interface LookupDAO {

	public LookupTableCache getLookupTableRecords(String keyName, String sqlQeury) throws LookupTableLoadException;
	
	public String getSingleOutputValue(String valueType) throws PrepSystemException;
}
