package com.ascap.apm.controller.utils;

import java.util.ArrayList;
import org.apache.commons.beanutils.Converter;
import com.ascap.apm.common.helpers.LogHelper;

/**
 * @author Raju_Ayanampudi
 */
public class StringArrayInputConverter implements Converter {

    protected LogHelper log = new LogHelper(this.getClass().getName());

    /**
     * @see org.apache.commons.beanutils.Converter#convert(Class, Object)
     */

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Object convert(Class clazz, Object value) {
        String[] output = new String[0];
        try {
            if (value instanceof String[]) {
                String[] input = (String[]) value;
                if (input.length > 0) {
                    ArrayList<String> outputList = new ArrayList<>();
                    for (int i = 0; i < input.length; i++) {
                        if (input[i] != null && !input[i].trim().equals("")) {
                            outputList.add(input[i].trim());
                        }
                    }
                    return (outputList.toArray(new String[outputList.size()]));
                } else {
                    return input;
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());

        }
        return output;
    }
}
