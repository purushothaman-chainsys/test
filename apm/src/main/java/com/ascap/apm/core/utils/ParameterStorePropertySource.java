package com.ascap.apm.core.utils;

import org.springframework.core.env.PropertySource;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.ParameterNotFoundException;

public class ParameterStorePropertySource extends PropertySource<AWSSimpleSystemsManagement> {

    public ParameterStorePropertySource(String name, AWSSimpleSystemsManagement source) {
        super(name, source);
    }

    @Override
    public Object getProperty(String parameterName) {
        try {
            return source.getParameter(new GetParameterRequest().withName(parameterName).withWithDecryption(true))
                .getParameter().getValue();
        } catch (ParameterNotFoundException exception) {
            return null;
        }
    }

}
