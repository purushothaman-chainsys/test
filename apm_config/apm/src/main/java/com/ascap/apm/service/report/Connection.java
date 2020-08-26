package com.ascap.apm.service.report;

import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.common.helpers.PrepExtPropertyHelper;
import com.ascap.apm.common.utils.constants.PrepPropertiesConstants;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;

/**
 * @author mzbamidi To change this generated comment edit the template variable "type-comment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class Connection {

    private Connection connection = new Connection();

    static IEnterpriseSession es = null;

    static IEnterpriseSession esSpl = null;

    protected static LogHelper log = new LogHelper("com.ascap.apm.common.service.reports.Connection");

    private Connection() {

    }

    public static IEnterpriseSession getSpecialEnterPriseSession() throws Exception {

        if (log.isDebugEnabled()) {
            log.debug("Entering getSpecialEnterPriseSession method in Connection");
        }

        try {
            String username = PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_SPL_USER);
            String password =
                PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_SPL_PASSWORD);
            String authType =
                PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_AUTH_TYPE);
            String server = PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_HOST);

            if (log.isDebugEnabled()) {
                log.debug("spl - user:" + username);
                log.debug("spl - authType:" + authType);
                log.debug("spl - server:" + server);
            }

            if ((esSpl == null)) {
                esSpl = CrystalEnterprise.getSessionMgr().logon(username, password, server, authType);
                log.debug("getSpecialEnterPriseSession()- created session, value: " + esSpl);
            }

        } catch (Exception e) {
            log.error("Exception in getSpecialEnterPriseSession() in Connection.java");
            esSpl = null;
            try {
                String username =
                    PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_SPL_USER);
                String password =
                    PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_SPL_PASSWORD);
                String authType =
                    PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_AUTH_TYPE);
                String server = PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_HOST);
                esSpl = CrystalEnterprise.getSessionMgr().logon(username, password, server, authType);
            } catch (Exception ie) {
                esSpl = null;
                log.error("Exception in Exception getSpecialEnterPriseSession() in Connection.java");
                throw e;

            }

        }

        if (log.isDebugEnabled()) {
            log.debug("Exiting getSpecialEnterPriseSession method in Connection");
        }

        return esSpl;
    }

    public static IEnterpriseSession getEnterPriseSession() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering getEnterPriseSession method in Connection");
        }

        try {

            String username = PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_USER);
            String password = PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_PASSWORD);
            String authType =
                PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_AUTH_TYPE);
            String server = PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_HOST);

            log.debug("Enters : getEnterPriseSession()");
            log.debug("user:" + username);
            log.debug("password:" + password);
            log.debug("authType:" + authType);
            log.debug("server:" + server);

            if ((es == null)) {
                log.debug("getEnterPriseSession()-Before Creating Session" + es);
                es = CrystalEnterprise.getSessionMgr().logon(username, password, server, authType);
                log.debug("getEnterPriseSession()-After session, value:" + es);
            }

        } catch (Exception e) {
            // e.printStackTrace();
            log.error("Exception in getEnterpriseSession() in Connection.java");
            es = null;
            try {
                String username = PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_USER);
                String password =
                    PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_PASSWORD);
                String authType =
                    PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_AUTH_TYPE);
                String server = PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_HOST);
                es = CrystalEnterprise.getSessionMgr().logon(username, password, server, authType);
            } catch (Exception ie) {
                es = null;
                log.error("Exception in Exception getEnterpriseSession() in Connection.java");
                throw e;

            }

        }

        if (log.isDebugEnabled()) {
            log.debug("Exiting getEnterPriseSession method in Connection");
        }
        return es;

    }

    public static IEnterpriseSession forceGetEnterpriseSession() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering forceGetEnterpriseSession method in Connection");
        }

        es = null;
        getEnterPriseSession();

        if (log.isDebugEnabled()) {
            log.debug("Exiting forceGetEnterpriseSession method in Connection");
        }
        return es;
    }

    public static IEnterpriseSession checkSession() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering checkSession method in Connection");
        }

        if (log.isDebugEnabled()) {
            log.debug("Exiting checkSession method in Connection");
        }
        return es;
    }

    public static synchronized void logoff() {
        if (log.isDebugEnabled()) {
            log.debug("Entering logoff method in Connection");
        }

        try {
            if (es != null) {
                es.logoff();
                es = null;
            }
            if (esSpl != null) {
                esSpl.logoff();
                esSpl = null;
            }
        } catch (Exception e) {
            es = null;
            esSpl = null;
            log.error("Exception while logoff");
        }

        if (log.isDebugEnabled()) {
            log.debug("Exiting logoff method in Connection");
        }
    }

}
