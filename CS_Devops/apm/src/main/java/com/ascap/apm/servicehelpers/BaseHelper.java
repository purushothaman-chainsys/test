package com.ascap.apm.servicehelpers;

import java.io.Serializable;

import com.ascap.apm.common.helpers.LogHelper;

/**
 * @author Sudhar_Krishnamachar To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class BaseHelper implements Serializable {

    private static final long serialVersionUID = 4540884756470596967L;

    protected LogHelper log = new LogHelper(this.getClass().getName());

}
