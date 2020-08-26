package com.ascap.apm.handler;

import java.io.Serializable;

import com.ascap.apm.common.helpers.LogHelper;

/**
 * @author Sudhar_Krishnamachar To change this generated comment edit the template variable "type comment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public abstract class BaseHandler implements Serializable {

    private static final long serialVersionUID = -91495625551697992L;

    protected LogHelper log = new LogHelper(this.getClass().getName());

}
