/*
 * Created on Nov 17, 2009
 *
 *  To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ascap.apm.controller.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.ascap.apm.common.context.UserProfile;
import com.ascap.apm.common.utils.cache.CredentialsHelper;


/**
 * @author Raju_Ayanampudi
 */
public class SecurityUtils {

    private SecurityUtils() {

    }

    public static boolean hasPermission(UserProfile userProfile, String objectName) {
        return hasPermission(userProfile, objectName, null);
    }

    public static boolean hasPermission(UserProfile userProfile, String objectName, String type) {
        boolean haveAccess = false;

        List<Serializable> groups = userProfile.getGroups();
        try {
            if (groups != null && !groups.isEmpty()) {
                Iterator<?> groupsIterator = groups.iterator();
                String group = "";
                while (groupsIterator.hasNext()) {
                    group = ((String) groupsIterator.next()).toUpperCase();
                    Collection<?> groupCredentials =
                        (Collection<?>) CredentialsHelper.getInstance().getCredentials().get(group.toUpperCase());

                    if (groupCredentials != null && groupCredentials.contains(objectName.toUpperCase())) {
                        haveAccess = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            haveAccess = false;
        }

        return haveAccess;
    }

}
