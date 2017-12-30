package com.balance.comparison.executor;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.balance.comparison.model.AggregatedDataDTO;
import com.balance.comparison.model.BalanceComparisonRequest;
import com.balance.comparison.model.ComparedDataDTO;

public class DataPartitionerCallable implements Callable<ComparedDataDTO> {

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

	public ComparedDataDTO call() throws Exception {

		LOG.info("Retrieving and comparing data for reporting period : "+this.rptPrd);
		
		List<AggregatedDataDTO> aggregatedDataList = retrievDataForEachPartition();

		//iterate though this list and crate a comparison request
		/*for (AggregatedDataDTO dto: aggregatedData) {
			// create a new comparison request object and pass to compare data
			// it will return one single compared data object
			//			compareData(string);
		}*/
		
		
		return compareDataForEachPartiton(aggregatedDataList);
	
	}


	private List<AggregatedDataDTO> retrievDataForEachPartition() {
		return dataRetrievalService.retrieveData(request, rptPrd);
	}	

	private ComparedDataDTO compareDataForEachPartiton(List<AggregatedDataDTO> aggregatedDataList) {
		return comparisonService.compareData(aggregatedDataList);
	}

}
