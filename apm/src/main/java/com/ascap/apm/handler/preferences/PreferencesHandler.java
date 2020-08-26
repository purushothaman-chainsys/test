package com.ascap.apm.handler.preferences;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;

public interface PreferencesHandler {

    public PREPContext submitPreferences(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;
        
}

