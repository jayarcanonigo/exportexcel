package com.advisen.excelconverter;

import java.util.List;

public class StgLossDataUIField {
	private String category;
	private String riskType;
	private String caseStatus;
	private Integer column;
	private List<FieldNameValue> fieldNameValue;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getRiskType() {
		return riskType;
	}
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	public String getCaseStatus() {
		return caseStatus;
	}
	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}	
	public Integer getColumn() {
		return column;
	}
	public void setColumn(Integer column) {
		this.column = column;
	}
	public List<FieldNameValue> getFieldNameValue() {
		return fieldNameValue;
	}
	public void setFieldNameValue(List<FieldNameValue> fieldNameValue) {
		this.fieldNameValue = fieldNameValue;
	}
	
}
