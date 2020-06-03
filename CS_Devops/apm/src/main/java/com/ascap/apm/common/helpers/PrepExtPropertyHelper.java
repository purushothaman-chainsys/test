package com.ascap.apm.common.helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.simplesystemsmanagement.model.Parameter;
/**
 * @author Naveen_Ramappa To change this generated comment edit the template
 *         variable "typecomment": Window>Preferences>Java>Templates. To enable
 *         and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
@Service("prepExtPropertyHelper")
public class PrepExtPropertyHelper {

	@Autowired
	private List<Parameter> propertyStoreList;

	private static List<Parameter> pStoreList;

	private static PrepExtPropertyHelper extPropertyHelper = null;

	private static Map<String, String> propertyMap;

	private PrepExtPropertyHelper() {

	}

	@PostConstruct
	private void initParameterStorePropertySource() {
		pStoreList = this.propertyStoreList;
	}

	public static synchronized PrepExtPropertyHelper getInstance() {
		if (extPropertyHelper == null) {
			extPropertyHelper = new PrepExtPropertyHelper();
			propertyMap = new HashMap<>();
			init();
		}
		return extPropertyHelper;
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
			name = (String) propertyMap.get("/APM/" + name);
		}
		return name;
	}

}
