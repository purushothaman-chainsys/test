package com.ascap.apm.vob;

/**
 * @author Pratap Sanikommu
 * @version $Revision: 1.0 $ $Date: Sep 08 2003 18:25:48 $ BaseMultiVOB - Base Multi Page VOB
 */
public abstract class BaseMultiVOB extends BaseVOB {

    private static final long serialVersionUID = -4021001362345323094L;

    /**
     * All Multi Page spread Value Objects/beans MUST implement this method for easy debugging and also for easy
     * intrepreation of the object return a valid string constructed out of all the meaningful attributes in the VOB
     */
    public abstract String toString();
}
