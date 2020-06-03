package com.ascap.apm.vob.reports;

import java.io.Serializable;
import java.util.List;

import com.ascap.apm.common.utils.cache.PrepKeyValueObject;

public class Parameter implements Serializable {
	
	/**
     * 
     */
    private static final long serialVersionUID = 6476429994377418148L;

    //unique parameter Id
	private int paramID;
	
	// name of the parameter
	private String paramName;
	
//	 name of the prompt
	private String paramLabel;
	
	//description of the parameter
	private String description;
	
	//report id of the parameter
	private String reportID;
	
	//input parameter name of the crystal report
	private String reportParameterID;
	
    private String inputValue;

	//data type of the UI parameter
	private String dataType;
	
	//type of the parameter(single/LO)
	private String parameterType;
	
	//default value from CES/application
	private String parameterValue;  //defaultValue
	
	//DB query to get LOV from DB
	private String LOVQuery;
	
	//comments
	private String comments;
	
	// List of values
	private List<Object>  listofDefaultValues;
	private List<Object>  rangeValues;
	private List<PrepKeyValueObject>  multipleValues;
	private List<Object>  inputRangeValues;
	
	// list of validations to be performed on this parameter
	private List<Object> validations;
	
	private boolean descreteValued;
	private boolean rangeValued;
	private boolean multiValued;
	private boolean required;
	private boolean defaultList = false;
	private boolean customValued = true;
	
	public Parameter( int paramID,
					  String paramName,
					  String description,
					  String reportID,
					  String reportParameterID,
					  String inputValue,
					  String dataType,
					  String parameterType,
					  String parameterValue,
					  String LOVQuery,
					  String comments,
					  List<Object> listofDefaultValues,
					  List<Object> validations) {
		this.paramID=paramID;
		this.paramName=paramName;
		this.description=description;
		this.reportID=reportID;
		this.reportParameterID=reportParameterID;
		this.inputValue = inputValue;		
		this.dataType=dataType;
		this.parameterType=parameterType;
		this.parameterValue=parameterValue;
		this.LOVQuery=LOVQuery;
		this.comments=comments;
		this.listofDefaultValues=listofDefaultValues;
		this.validations=validations;			  	
					  	
					  	
	}
					  
					  
					  
		
	public Parameter(){
	}
	
	public Parameter getParameter(){
		
	return null;
		
	}

	/**
	 * Returns the comments.
	 * @return String
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Returns the dataType.
	 * @return String
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * Returns the description.
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the inputValue.
	 * @return String
	 */
	public String getInputValue() {
		return inputValue;
	}

	/**
	 * Returns the listofDefaultValues.
	 * @return List
	 */
	public List<Object> getListofDefaultValues() {
		return listofDefaultValues;
	}

	/**
	 * Returns the lOVQuery.
	 * @return String
	 */
	public String getLOVQuery() {
		return LOVQuery;
	}

	/**
	 * Returns the parameterType.
	 * @return String
	 */
	public String getParameterType() {
		return parameterType;
	}

	/**
	 * Returns the parameterValue.
	 * @return String
	 */
	public String getParameterValue() {
		return parameterValue;
	}

	/**
	 * Returns the paramID.
	 * @return String
	 */
	public int getParamID() {
		//System.out.println("getParamID" +paramID);
		return paramID;
	}

	/**
	 * Returns the paramName.
	 * @return String
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * Returns the reportID.
	 * @return String
	 */
	public String getReportID() {
		return reportID;
	}

	/**
	 * Returns the reportParameterID.
	 * @return String
	 */
	public String getReportParameterID() {
		return reportParameterID;
	}

	/**
	 * Returns the validations.
	 * @return List
	 */
	public List<Object> getValidations() {
		return validations;
	}

	/**
	 * Sets the comments.
	 * @param comments The comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * Sets the dataType.
	 * @param dataType The dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * Sets the description.
	 * @param description The description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the inputValue.
	 * @param inputValue The inputValue to set
	 */
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	/**
	 * Sets the listofDefaultValues.
	 * @param listofDefaultValues The listofDefaultValues to set
	 */
	public void setListofDefaultValues(List<Object> listofDefaultValues) {
		this.listofDefaultValues = listofDefaultValues;
	}

	/**
	 * Sets the lOVQuery.
	 * @param lOVQuery The lOVQuery to set
	 */
	public void setLOVQuery(String lOVQuery) {
		LOVQuery = lOVQuery;
	}

	/**
	 * Sets the parameterType.
	 * @param parameterType The parameterType to set
	 */
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	/**
	 * Sets the parameterValue.
	 * @param parameterValue The parameterValue to set
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	/**
	 * Sets the paramID.
	 * @param paramID The paramID to set
	 */
	public void setParamID(int paramID) {
		this.paramID = paramID;
	}

	/**
	 * Sets the paramName.
	 * @param paramName The paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * Sets the reportID.
	 * @param reportID The reportID to set
	 */
	public void setReportID(String reportID) {
		
		this.reportID = reportID;
	}

	/**
	 * Sets the reportParameterID.
	 * @param reportParameterID The reportParameterID to set
	 */
	public void setReportParameterID(String reportParameterID) {
		this.reportParameterID = reportParameterID;
	}

	/**
	 * Sets the validations.
	 * @param validations The validations to set
	 */
	public void setValidations(List<Object> validations) {
		this.validations = validations;
	}

	/**
	 * Returns the inputRangeValues.
	 * @return List
	 */
	public List<Object> getInputRangeValues() {
		return inputRangeValues;
	}

	

	/**
	 * Returns the multipleValues.
	 * @return List
	 */
	public List<PrepKeyValueObject> getMultipleValues() {
		return multipleValues;
	}

	/**
	 * Returns the rangeValues.
	 * @return List
	 */
	public List<Object> getRangeValues() {
		return rangeValues;
	}

	/**
	 * Sets the inputRangeValues.
	 * @param inputRangeValues The inputRangeValues to set
	 */
	public void setInputRangeValues(List<Object> inputRangeValues) {
		this.inputRangeValues = inputRangeValues;
	}

	
	/**
	 * Sets the multipleValues.
	 * @param multipleValues The multipleValues to set
	 */
	public void setMultipleValues(List<PrepKeyValueObject> multipleValues) {
		this.multipleValues = multipleValues;
	}

	/**
	 * Sets the rangeValues.
	 * @param rangeValues The rangeValues to set
	 */
	public void setRangeValues(List<Object> rangeValues) {
		this.rangeValues = rangeValues;
	}

	/**
	 * Returns the descreteValued.
	 * @return boolean
	 */
	public boolean isDescreteValued() {
		return descreteValued;
	}

	/**
	 * Returns the rangeValued.
	 * @return boolean
	 */
	public boolean isRangeValued() {
		return rangeValued;
	}

	/**
	 * Returns the required.
	 * @return boolean
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * Returns the rultiValued.
	 * @return boolean
	 */
	public boolean isMultiValued() {
		return multiValued;
	}

	/**
	 * Sets the descreteValued.
	 * @param descreteValued The descreteValued to set
	 */
	public void setDescreteValued(boolean descreteValued) {
		this.descreteValued = descreteValued;
	}

	/**
	 * Sets the rangeValued.
	 * @param rangeValued The rangeValued to set
	 */
	public void setRangeValued(boolean rangeValued) {
		this.rangeValued = rangeValued;
	}

	/**
	 * Sets the required.
	 * @param required The required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * Sets the rultiValued.
	 * @param rultiValued The rultiValued to set
	 */
	public void setMultiValued(boolean multiValued) {
		this.multiValued = multiValued;
	}

	/**
	 * Returns the defaultList.
	 * @return boolean
	 */
	public boolean isDefaultList() {
		return defaultList;
	}

	/**
	 * Sets the defaultList.
	 * @param defaultList The defaultList to set
	 */
	public void setDefaultList(boolean defaultList) {
		this.defaultList = defaultList;
	}

/**
 * @return Returns the paramLabel.
 */
public String getParamLabel() {
	return paramLabel;
}
/**
 * @param paramLabel The paramLabel to set.
 */
public void setParamLabel(String paramLabel) {
	this.paramLabel = paramLabel;
}

/**
 * @return the customValued
 */
public boolean isCustomValued() {
	return customValued;
}

/**
 * @param customValued the customValued to set
 */
public void setCustomValued(boolean customValued) {
	this.customValued = customValued;
}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Parameter [paramID=");
	builder.append(paramID);
	builder.append(", paramName=");
	builder.append(paramName);
	builder.append(", paramLabel=");
	builder.append(paramLabel);
	builder.append(", description=");
	builder.append(description);
	builder.append(", reportID=");
	builder.append(reportID);
	builder.append(", reportParameterID=");
	builder.append(reportParameterID);
	builder.append(", inputValue=");
	builder.append(inputValue);
	builder.append(", dataType=");
	builder.append(dataType);
	builder.append(", parameterType=");
	builder.append(parameterType);
	builder.append(", parameterValue=");
	builder.append(parameterValue);
	builder.append(", LOVQuery=");
	builder.append(LOVQuery);
	builder.append(", comments=");
	builder.append(comments);
	builder.append(", listofDefaultValues=");
	builder.append(listofDefaultValues);
	builder.append(", rangeValues=");
	builder.append(rangeValues);
	builder.append(", multipleValues=");
	builder.append(multipleValues);
	builder.append(", inputRangeValues=");
	builder.append(inputRangeValues);
	builder.append(", validations=");
	builder.append(validations);
	builder.append(", descreteValued=");
	builder.append(descreteValued);
	builder.append(", rangeValued=");
	builder.append(rangeValued);
	builder.append(", multiValued=");
	builder.append(multiValued);
	builder.append(", required=");
	builder.append(required);
	builder.append(", defaultList=");
	builder.append(defaultList);
	builder.append(", customValued=");
	builder.append(customValued);
	builder.append("]");
	return builder.toString();
}
}
