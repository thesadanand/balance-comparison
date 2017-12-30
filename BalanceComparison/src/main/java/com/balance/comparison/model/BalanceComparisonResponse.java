package com.balance.comparison.model;

import java.util.List;

public class BalanceComparisonResponse {

	private int responseCode;
	private String responseMessage;
	private List<ComparedDataDTO> resultData;
/*	private List<String> columns = Arrays.asList("source-1", "source-2", "month", "T-Type", "source1-balance",
			"sourc2-balance", "diff", "%-diff");
*/	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
/*	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}*/
	public List<ComparedDataDTO> getResultData() {
		return resultData;
	}
	public void setResultData(List<ComparedDataDTO> resultData) {
		this.resultData = resultData;
	}

	@Override
	public String toString() {
		return "BalanceComparisonResponse [responseCode=" + responseCode
				+ ", responseMessage=" + responseMessage + ", resultData=" + resultData + "]";
	}
}
