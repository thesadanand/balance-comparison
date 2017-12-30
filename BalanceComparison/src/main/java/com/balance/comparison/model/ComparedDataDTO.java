package com.balance.comparison.model;

public class ComparedDataDTO {

	private String sourceOne;

	private String sourceTwo;

	private String rptPrd;

	private String transcationType;

	private double source1AggreBalance;

	private double source2AggreBalance;

	private double balanceDiff;

	private double balanceDiffPerct;

	public String getSourceOne() {
		return sourceOne;
	}

	public void setSourceOne(String sourceOne) {
		this.sourceOne = sourceOne;
	}

	public String getSourceTwo() {
		return sourceTwo;
	}

	public void setSourceTwo(String sourceTwo) {
		this.sourceTwo = sourceTwo;
	}

	public String getRptPrd() {
		return rptPrd;
	}

	public void setRptPrd(String rptPrd) {
		this.rptPrd = rptPrd;
	}

	public String getTranscationType() {
		return transcationType;
	}

	public void setTranscationType(String transcationType) {
		this.transcationType = transcationType;
	}

	public double getBalanceDiff() {
		return balanceDiff;
	}

	public void setBalanceDiff(double balanceDiff) {
		this.balanceDiff = balanceDiff;
	}

	public double getBalanceDiffPerct() {
		return balanceDiffPerct;
	}

	public void setBalanceDiffPerct(double balanceDiffPerct) {
		this.balanceDiffPerct = balanceDiffPerct;
	}

	public double getSource1AggreBalance() {
		return source1AggreBalance;
	}

	public void setSource1AggreBalance(double source1AggreBalance) {
		this.source1AggreBalance = source1AggreBalance;
	}

	public double getSource2AggreBalance() {
		return source2AggreBalance;
	}

	public void setSource2AggreBalance(double source2AggreBalance) {
		this.source2AggreBalance = source2AggreBalance;
	}


	@Override
	public String toString() {
		return "ComparedDataDTO [sourceOne=" + sourceOne + ", sourceTwo="
				+ sourceTwo + ", rptPrd=" + rptPrd + ", transcationType="
				+ transcationType + ", source1AggreBalance="
				+ source1AggreBalance + ", source2AggreBalance="
				+ source2AggreBalance + ", balanceDiff=" + balanceDiff
				+ ", balanceDiffPerct=" + balanceDiffPerct + "]";
	}


}
