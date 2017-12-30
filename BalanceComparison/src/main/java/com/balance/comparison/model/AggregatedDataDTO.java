package com.balance.comparison.model;

import java.util.Map;

public class AggregatedDataDTO { 

	private String Source;

	private String rptPrd;

	private String transactionType;

	private Map<String, Double> dataMap;

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}

	public Map<String, Double> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Double> dataMap) {
		this.dataMap = dataMap;
	}

	public String getRptPrd() {
		return rptPrd;
	}

	public void setRptPrd(String rptPrd) {
		this.rptPrd = rptPrd;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "AggregatedDataDTO [Source=" + Source + ", rptPrd=" + rptPrd
				+ ", transactionType=" + transactionType + ", dataMap="
				+ dataMap.toString() + "]";
	}

}
