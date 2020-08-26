package com.ascap.apm.vob;

/**
 * @author Pratap Sanikommu
 * @version $Revision: 1.2 $ $Date: Dec 04 2003 12:36:12 $ BaseInfoBarVOB - Base Info Bar VOB
 */
public abstract class BaseInfoBarVOB extends BaseVOB {

    private static final long serialVersionUID = -1655582260332743413L;

    /**
     * All Info Bar Value Objects/beans MUST implement this method for easy debugging and also for easy intrepreation of
     * the object return a valid string constructed out of all the meaningful attributes in the VOB
     */
    public abstract String toString();
}
