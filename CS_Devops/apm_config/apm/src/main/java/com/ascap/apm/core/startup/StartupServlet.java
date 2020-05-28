package com.ascap.apm.core.startup;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.web.context.ServletConfigAware;

import com.ascap.apm.common.context.PREPContext;
import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.controller.utils.StringArrayInputConverter;
import com.ascap.apm.handler.logon.LogonHandler;
import com.ascap.apm.handler.logon.LogonHandlerImpl;

public class StartupServlet extends HttpServlet implements ServletConfigAware {

	private static final long serialVersionUID = -4162615108053758491L;

	private static LogHelper log = new LogHelper("StartupServlet");

	@Override
	public void init(ServletConfig servletConfig) {

		try {
			// ********************* Start of loadStartup() **************************
			loadStartup();
			// ********************* End of loadStartup() **************************

			// Creating and Registering String[] converter (which will be use by BeanUtils
			// to removes blank dropdown selection)
			log.debug("Registering StringArrayInputConverter Converter ");
			Converter stringArrayInputConverter = new StringArrayInputConverter();
			ConvertUtils.register(stringArrayInputConverter, String[].class);
		} catch (Exception exception) {
			log.debug("Error Loading Properties from prep_lookuptables.xml : ", exception);
		}

	}

	private void loadStartup() {
		LogonHandler logonHandler = null;
		Boolean isSecurityEnabled = null;
		PREPContext inputContext = null;
		try {
			// Temp variable to turn on/off the security (Call to TAM).
			isSecurityEnabled = true;
			inputContext = new PREPContext();
			// Set Input VOBS to inputContext
			inputContext.addInputValueObject(isSecurityEnabled);
			logonHandler = new LogonHandlerImpl();
			logonHandler.loadOnStartup(inputContext);
		} catch (Exception ne) {
			log.error(ne.getMessage());
		}
	}

	@Override
	public void setServletConfig(ServletConfig arg0) {

	}
}
