package com.ascap.apm.common.utils.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.database.admin.AdminDAO;
import com.ascap.apm.vob.admin.Group;

/**
 * @author Raju_Ayanampudi Credentials helper This will be a singleton instance and will be loaded once during the
 *         startup
 */
@Service("credentialsHelper")
public class CredentialsHelper {

    protected static LogHelper log = new LogHelper("CredentialsHelper");

    private static boolean isReload = false;

    /**
     * holds all the PREP application credentials
     */
    private Map<Object, Object> credentials;

    /**
     * holds all the PREP application credentials caches
     */
    private static CredentialsHelper credentialsHelper = null;

    private static AdminDAO adminDAO;

    @Autowired
    private AdminDAO aDAO;

    @PostConstruct
    private void initDAO() {
        adminDAO = this.aDAO;
    }

    /**
     * private Constructor for CredentialsHelper.
     */
    private CredentialsHelper() {
        super();
    }

    /**
     * Method getInstance.
     * 
     * @return CredentialsHelper
     */
    public static synchronized CredentialsHelper getInstance() throws PrepSystemException {
        if (isReload) {
            isReload = false;
            credentialsHelper = null;
        }
        if (credentialsHelper != null)
            return credentialsHelper;

        credentialsHelper = new CredentialsHelper();
        credentialsHelper.init();
        return credentialsHelper;
    }

    /**
     * clean all the caches and the cache list
     */
    private void initCredentialsCache() {
        cleanup();
        credentials = new HashMap<>();
    }

    /**
     * clean all the caches and the cache list
     */
    private void cleanup() {
        if (credentials != null)
            credentials.clear();
        credentials = null;
    }

    public static synchronized CredentialsHelper reloadInstance() throws PrepSystemException {
        isReload = true;
        return getInstance();
    }

    private int init() throws PrepSystemException {
        try {
            // initialize the cache
            initCredentialsCache();

            List<Group> groupNames = adminDAO.retrieveGroups();
            Group group = null;
            List<String> groupCredentials = null;

            for (int i = 0; i < groupNames.size(); i++) {
                group = groupNames.get(i);
                log.debug("Getting Credentials of the GroupUser " + group.getGroupCode());

                groupCredentials = adminDAO.getCredentials(group);
                if (groupCredentials != null)
                    credentials.put(group.getGroupCode().toUpperCase(), groupCredentials);

                log.debug("\nSet Credentials done for GroupUser " + group.getGroupCode() + "***** "
                    + credentials.get(group.getGroupCode()));

            }

            log.debug("All Credentials loaded!");
            return 1;
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new PrepSystemException("error.admin.credentials.exception");
        }
    }

    /**
     * Returns the credentials.
     * 
     * @return Hashtable
     */
    public Map<Object, Object> getCredentials() {
        return credentials;
    }
    
}
