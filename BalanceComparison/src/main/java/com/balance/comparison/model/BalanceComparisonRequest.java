package com.balance.comparison.model;

import java.util.Arrays;
import java.util.List;

/**
 * POJO to mapping client JSON request into java object
 * @author Tanu
 *
 */
public class BalanceComparisonRequest {

	private String name;

	private int dateFrom;
	private int dateTo;
	private String measure;
	private List<String> sources = Arrays.asList("icici","sbi");

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(int dateFrom) {
		this.dateFrom = dateFrom;
	}

	public int getDateTo() {
		return dateTo;
	}

	public void setDateTo(int dateTo) {
		this.dateTo = dateTo;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public List<String> getSources() {
		return sources;
	}

	public void setSources(List<String> sources) {
		this.sources = Arrays.asList("icici","sbi");
	}
}
