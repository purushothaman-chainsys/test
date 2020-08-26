package com.ascap.apm.common.utils.cache.lookup;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ascap.apm.common.exception.PrepSystemException;
import com.ascap.apm.common.exception.cache.InvalidCacheException;
import com.ascap.apm.common.exception.cache.KeyNotFoundException;
import com.ascap.apm.common.exception.database.lookup.LookupTableLoadException;
import com.ascap.apm.common.helpers.LogHelper;
import com.ascap.apm.database.lookup.LookupDAO;

/**
 * @author Sudhar_Krishnamachar Lookup Tables helper This will be a singleton instance and will be loaded once during
 *         the startup
 */
@Service("lookupTablesHelper")
public class LookupTablesHelper {

    private static LookupDAO lookupDAO;

    @Autowired
    private LookupDAO lDAO;

    @PostConstruct
    private void initDAO() {
        lookupDAO = this.lDAO;
    }

    protected static LogHelper log = new LogHelper("LookupTablesHelper");

    /**
     * reload request flag
     */
    private static boolean isReload = false;

    /**
     * holds all the PREP application look up table caches
     */
    private static Map<Object, LookupTableCache> caches;

    /**
     * holds all the PREP application look up table caches
     */
    private static LookupTablesHelper lookupTablesHelper = null;

    /**
     * private Constructor for LookupTablesHelper.
     */
    private LookupTablesHelper() {
        super();
    }

    /**
     * Method getInstance.
     * 
     * @return LookupTablesHelper
     */
    public static synchronized LookupTablesHelper getInstance() {

        if (isReload) {
            isReload = false;
            lookupTablesHelper = null;
        }
        if (lookupTablesHelper != null)
            return lookupTablesHelper;
        lookupTablesHelper = new LookupTablesHelper();
        // read the file name from the properties
        lookupTablesHelper.init();

        return lookupTablesHelper;
    }

    /**
     * Method reloadInstance.
     * 
     * @return LookupTablesHelper
     */
    public static synchronized LookupTablesHelper reloadInstance() {
        isReload = true;
        return getInstance();
    }

    /**
     * clean all the caches and the cache list
     */
    private static void initCache() {
        cleanup();
        caches = new HashMap<>();
    }

    /**
     * clean all the caches and the cache list
     */
    private static void cleanup() {
        if (caches != null)
            caches.clear();
        caches = null;
    }

    private int init() {
        InputStream inputStream = null;
        try {

            // initialize the cache
            initCache();

            SAXBuilder builder = new SAXBuilder();
            inputStream = this.getClass().getResourceAsStream("/xml/prep_lookuptables.xml");
            Document doc = builder.build(inputStream);
            // Get a List of all direct children as Element objects
            List<?> lookupTables = (List<?>) doc.getRootElement().getChildren("lookuptable");
            Element lookupTable = null;
            for (int i = 0; i < lookupTables.size(); i++) {
                lookupTable = (Element) lookupTables.get(i);

                String sqlQuery = lookupTable.getChild("sqlquery").getText();
                String keyName = lookupTable.getChild("keyname").getText();
                log.debug("Loading lookup table : " + keyName + ", " + sqlQuery);

                loadLookupTable(sqlQuery, keyName);
            }
            log.debug("All lookup Tables loaded! caches = " + caches.toString());
            return 1;

        } catch (LookupTableLoadException e) {
            log.error(e.getMessage());
            return -1;
        } catch (JDOMException e) {
            return -2;
        } catch (IOException e) {
            return -3;
        } catch (Exception e) {
            return -4;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }
    }

    /**
     * loads the lookup for the specific table
     */
    private void loadLookupTable(String sqlQuery, String keyName) throws LookupTableLoadException {
        LookupTableCache lookupTableCache = lookupDAO.getLookupTableRecords(keyName, sqlQuery);
        caches.put(keyName, lookupTableCache);
    }

    public String getSingleOutputValue(String valueType) throws PrepSystemException {
        return lookupDAO.getSingleOutputValue(valueType);
    }

    /**
     * load method that will be called from start up
     */
    public String getLookupTableItemDescription(String lookupTableKey, String lookupTableItemKey)
        throws InvalidCacheException, KeyNotFoundException {
        if (caches == null)
            throw new InvalidCacheException("utils.cache.cachenull");
        LookupTableCache lookupTableCache = caches.get(lookupTableKey);
        if (lookupTableCache == null) {
            String[] errorStrings = new String[1];
            errorStrings[0] = lookupTableKey;
            throw new InvalidCacheException("utils.cache.cachenotfound", errorStrings);
        }
        String itemDescription = lookupTableCache.get(lookupTableItemKey);
        if (itemDescription == null) {
            String[] errorStrings = new String[2];
            errorStrings[0] = lookupTableItemKey;
            errorStrings[1] = lookupTableKey;
            throw new InvalidCacheException("utils.cache.keynotfound", errorStrings);
        }
        return itemDescription;
    }

    /**
     * load method that will be called from start up
     */
    public LookupTableCache getLookupTable(String lookupTableKey) throws InvalidCacheException {
        if (caches == null)
            throw new InvalidCacheException("utils.cache.cachenull");
        LookupTableCache lookupTableCache = caches.get(lookupTableKey);
        if (lookupTableCache == null) {
            String[] errorStrings = new String[1];
            errorStrings[0] = lookupTableKey;
            throw new InvalidCacheException("utils.cache.cachenotfound", errorStrings);
        }
        return lookupTableCache;
    }
}
