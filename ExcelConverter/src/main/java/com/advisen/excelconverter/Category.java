package com.advisen.excelconverter;

import java.util.List;

public class Category {
	private String name;
	private Integer firstRow;
	private List<StgLossDataUIField> data;
	private Integer lastRow;	
	private String status;

	public List<StgLossDataUIField> getData() {
		return data;
	}
	public void setData(List<StgLossDataUIField> data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getFirstRow() {
		return firstRow;
	}
	public void setFirstRow(Integer firstRow) {
		this.firstRow = firstRow;
	}
	public Integer getLastRow() {
		return lastRow;
	}
	public void setLastRow(Integer lastRow) {
		this.lastRow = lastRow;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
