package com.ascap.apm.handler.logon;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.exception.PrepFunctionalException;
import com.ascap.apm.common.exception.PrepSystemException;

public interface LogonHandler {

    public PREPContext authenticateLogon(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;
    
    public PREPContext loadOnStartup(PREPContext inputContext) throws PrepSystemException;
    
    public PREPContext logout(PREPContext inputContext) throws PrepSystemException, PrepFunctionalException;
    
    
}
