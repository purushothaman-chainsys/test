package com.ascap.apm.vob.reports.rest;

import java.util.ArrayList;

public class Parameter {
	
	private String document;
	private String paramAttDpId;
	private String paramAttType;
	private String paramAttOptional;
	private String id;
	private String technicalName;
	private String name;

	private String answerAttType;
	private String answerAttConstrained;
	
	private String infoAttCardinality;
	
	private String lovAttRefreshable;
	private String lovAttPartial;
	private String lovAttHierarchical;
	private String lovId;
	private ArrayList<String> lov;
	
	private ArrayList<String> values;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Parameter [document=");
		builder.append(document);
		builder.append(", paramAttDpId=");
		builder.append(paramAttDpId);
		builder.append(", paramAttType=");
		builder.append(paramAttType);
		builder.append(", paramAttOptional=");
		builder.append(paramAttOptional);
		builder.append(", id=");
		builder.append(id);
		builder.append(", technicalName=");
		builder.append(technicalName);
		builder.append(", name=");
		builder.append(name);
		builder.append(", answerAttType=");
		builder.append(answerAttType);
		builder.append(", answerAttConstrained=");
		builder.append(answerAttConstrained);
		builder.append(", infoAttCardinality=");
		builder.append(infoAttCardinality);
		builder.append(", lovAttRefreshable=");
		builder.append(lovAttRefreshable);
		builder.append(", lovAttPartial=");
		builder.append(lovAttPartial);
		builder.append(", lovAttHierarchical=");
		builder.append(lovAttHierarchical);
		builder.append(", lovId=");
		builder.append(lovId);
		builder.append(", lov=");
		builder.append(lov);
		builder.append(", values=");
		builder.append(values);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the document
	 */
	public String getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(String document) {
		this.document = document;
	}

	/**
	 * @return the paramAttDpId
	 */
	public String getParamAttDpId() {
		return paramAttDpId;
	}

	/**
	 * @param paramAttDpId the paramAttDpId to set
	 */
	public void setParamAttDpId(String paramAttDpId) {
		this.paramAttDpId = paramAttDpId;
	}

	/**
	 * @return the paramAttType
	 */
	public String getParamAttType() {
		return paramAttType;
	}

	/**
	 * @param paramAttType the paramAttType to set
	 */
	public void setParamAttType(String paramAttType) {
		this.paramAttType = paramAttType;
	}

	/**
	 * @return the paramAttOptional
	 */
	public String getParamAttOptional() {
		return paramAttOptional;
	}

	/**
	 * @param paramAttOptional the paramAttOptional to set
	 */
	public void setParamAttOptional(String paramAttOptional) {
		this.paramAttOptional = paramAttOptional;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the technicalName
	 */
	public String getTechnicalName() {
		return technicalName;
	}

	/**
	 * @param technicalName the technicalName to set
	 */
	public void setTechnicalName(String technicalName) {
		this.technicalName = technicalName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the answerAttType
	 */
	public String getAnswerAttType() {
		return answerAttType;
	}

	/**
	 * @param answerAttType the answerAttType to set
	 */
	public void setAnswerAttType(String answerAttType) {
		this.answerAttType = answerAttType;
	}

	/**
	 * @return the answerAttConstrained
	 */
	public String getAnswerAttConstrained() {
		return answerAttConstrained;
	}

	/**
	 * @param answerAttConstrained the answerAttConstrained to set
	 */
	public void setAnswerAttConstrained(String answerAttConstrained) {
		this.answerAttConstrained = answerAttConstrained;
	}

	/**
	 * @return the infoAttCardinality
	 */
	public String getInfoAttCardinality() {
		return infoAttCardinality;
	}

	/**
	 * @param infoAttCardinality the infoAttCardinality to set
	 */
	public void setInfoAttCardinality(String infoAttCardinality) {
		this.infoAttCardinality = infoAttCardinality;
	}

	/**
	 * @return the lovAttRefreshable
	 */
	public String getLovAttRefreshable() {
		return lovAttRefreshable;
	}

	/**
	 * @param lovAttRefreshable the lovAttRefreshable to set
	 */
	public void setLovAttRefreshable(String lovAttRefreshable) {
		this.lovAttRefreshable = lovAttRefreshable;
	}

	/**
	 * @return the lovAttPartial
	 */
	public String getLovAttPartial() {
		return lovAttPartial;
	}

	/**
	 * @param lovAttPartial the lovAttPartial to set
	 */
	public void setLovAttPartial(String lovAttPartial) {
		this.lovAttPartial = lovAttPartial;
	}

	/**
	 * @return the lovAttHierarchical
	 */
	public String getLovAttHierarchical() {
		return lovAttHierarchical;
	}

	/**
	 * @param lovAttHierarchical the lovAttHierarchical to set
	 */
	public void setLovAttHierarchical(String lovAttHierarchical) {
		this.lovAttHierarchical = lovAttHierarchical;
	}

	/**
	 * @return the lovId
	 */
	public String getLovId() {
		return lovId;
	}

	/**
	 * @param lovId the lovId to set
	 */
	public void setLovId(String lovId) {
		this.lovId = lovId;
	}

	/**
	 * @return the lov
	 */
	public ArrayList<String> getLov() {
		return lov;
	}

	/**
	 * @param lov the lov to set
	 */
	public void setLov(ArrayList<String> lov) {
		this.lov = lov;
	}

	/**
	 * @return the values
	 */
	public ArrayList<String> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(ArrayList<String> values) {
		this.values = values;
	}
	
}
