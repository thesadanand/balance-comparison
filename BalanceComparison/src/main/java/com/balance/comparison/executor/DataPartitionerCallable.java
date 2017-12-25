package com.balance.comparison.executor;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.balance.comparison.model.BalanceComparisonRequest;

public class DataPartitionerCallable implements Callable<String> {

	private static Logger LOG = Logger.getLogger(DataPartitionerCallable.class);

	private DataRetreivalService dataRetrievalService;

	private DataComparisonService comparisonService;

	private BalanceComparisonRequest request;

	private String rptPrd;

	public DataPartitionerCallable(DataRetreivalService dataReterivalService,
									DataComparisonService comparisonService, String rptPrd,
										BalanceComparisonRequest request) {
		this.dataRetrievalService = dataReterivalService;
		this.comparisonService = comparisonService;
		this.request = request;
		this.rptPrd = rptPrd;
	}

	public String call() throws Exception {

		LOG.info("Retrieving and comparing data for reporting period : "+this.rptPrd);
		
		List<String> aggregatedData = retrievData();

		//iterate though this list and crate a comparison request
		for (String string : aggregatedData) {
			// create a new comparison request object and pass to compare data
			// it will return one single compared data object
			//			compareData(string);
		}

		return compareData(aggregatedData);
	}

	private String compareData(List<String> aggregatedData) {
		return comparisonService.compareData(aggregatedData);
	}

	private List<String> retrievData() {
		return dataRetrievalService.getAggregatedData(request, rptPrd);
	}	

}
