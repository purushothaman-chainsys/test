package com.ascap.apm.controller.utils;

import java.util.Comparator;

/**
 * @author shyam_narayana This Comparator used for Sorting HtmlSelectOption
 *         Objects only. This compares on the Description of the
 *         HtmlSelectOption Object. Uses Strings compareTo Feature.
 */
public class DescriptionComparator implements Comparator<Object> {

	/**
	 * @see java.util.Comparator#compare(Object, Object)
	 */
	public int compare(Object o1, Object o2) {
		return (((HtmlSelectOption) o1).getDisplayName().compareTo(((HtmlSelectOption) o2).getDisplayName()));
	}
}
