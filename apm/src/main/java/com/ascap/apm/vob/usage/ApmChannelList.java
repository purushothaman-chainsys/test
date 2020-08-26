package com.ascap.apm.vob.usage;

import java.util.List;

import com.ascap.apm.vob.BaseSearchVOB;

public class ApmChannelList extends BaseSearchVOB {

	private static final long serialVersionUID = -605018072654845859L;
	
	private String filterSupplierCode = null;
	private String filterChannelType = null;
	private String operationType = null;
	private List<Object> channelSuppliers = null;
	
	private String countUnassigned = null;
	private String countClassical = null;
	private String countNonClassical = null;
	private String countNonMusical = null;
	
	private String[] selectedIndex = null;
	private String[] newChannelType = null;
	private String[] selectedIds = null;

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getFilterSupplierCode() {
		return filterSupplierCode;
	}

	public void setFilterSupplierCode(String filterSupplierCode) {
		this.filterSupplierCode = filterSupplierCode;
	}

	public String getFilterChannelType() {
		return filterChannelType;
	}

	public void setFilterChannelType(String filterChannelType) {
		this.filterChannelType = filterChannelType;
	}

	public List<Object> getChannelSuppliers() {
		return channelSuppliers;
	}

	public void setChannelSuppliers(List<Object> channelSuppliers) {
		this.channelSuppliers = channelSuppliers;
	}

	public String getCountUnassigned() {
		return countUnassigned;
	}

	public void setCountUnassigned(String countUnassigned) {
		this.countUnassigned = countUnassigned;
	}

	public String getCountClassical() {
		return countClassical;
	}

	public void setCountClassical(String countClassical) {
		this.countClassical = countClassical;
	}

	public String getCountNonClassical() {
		return countNonClassical;
	}

	public void setCountNonClassical(String countNonClassical) {
		this.countNonClassical = countNonClassical;
	}

	public String getCountNonMusical() {
		return countNonMusical;
	}

	public void setCountNonMusical(String countNonMusical) {
		this.countNonMusical = countNonMusical;
	}

	public String[] getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(String[] selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public String[] getNewChannelType() {
		return newChannelType;
	}

	public void setNewChannelType(String[] newChannelType) {
		this.newChannelType = newChannelType;
	}

	public String[] getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String[] selectedIds) {
		this.selectedIds = selectedIds;
	}
	
	

}
