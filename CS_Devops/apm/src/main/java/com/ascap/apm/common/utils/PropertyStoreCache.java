package com.ascap.apm.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.simplesystemsmanagement.model.Parameter;

@Service("propertyStoreCache")
public class PropertyStoreCache {

	@Autowired
	private List<Parameter> propertyStoreList;
	
	private static List<Parameter> pStoreList;
	
	@PostConstruct
    private void initParameterStorePropertySource() {
		pStoreList = this.propertyStoreList;
    }
	
	private static PropertyStoreCache propertyStoreCache = null;
	
	private static Map<String, String> propertyMap;
	
	public static PropertyStoreCache getInstance() {
        if (propertyStoreCache == null) {
        	propertyStoreCache = new PropertyStoreCache();
        	propertyMap = new HashMap<>();
        	init();
        }
        return propertyStoreCache;
    }

	private static void init() {
		if (pStoreList != null && pStoreList.size() > 0) {
			for (Parameter parameter : pStoreList) {
				propertyMap.put(parameter.getName(), parameter.getValue());
			}
		}
		
	}
	
	public String getPropertyValue(String name) {
        if (name != null) {
        	name = (String) propertyMap.get("/APM/"+name);
        }
        return name;
    }
	
}
