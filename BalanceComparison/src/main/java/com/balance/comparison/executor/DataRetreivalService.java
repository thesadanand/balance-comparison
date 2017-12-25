package com.balance.comparison.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.balance.comparison.model.BalanceComparisonRequest;
import com.balance.comparison.util.BalanceComparisonConstants;
import com.balance.comparison.util.BalanceComparisonUtils;
import com.balance.comparison.util.SourceMappingDTORepository;

public class DataRetreivalService {

	private static Logger LOG = Logger.getLogger(DataRetreivalService.class);

	//as we compare against 2 sources
	private final static int NUM_THREADS_FOR_DATA_RETRIEVAL=2;
	
	@Autowired
	private SourceMappingDTORepository sourceMappingDTORepository;

	public List<String> getAggregatedData(BalanceComparisonRequest request, String rptPrd) {
		// as we have 2 sources
		LOG.info("inside DataReterivalService: ");

		ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS_FOR_DATA_RETRIEVAL);
		List<String> sources = request.getSources();
		List<Future<String>> retrievdFutureDataList = new ArrayList<Future<String>>();

		for(String source: sources){
			Callable<String> dataRetrievalCallable  = new DataReterivalServiceCallable(source,rptPrd,sourceMappingDTORepository);
			Future<String> retrievedDataFuture = executorService.submit(dataRetrievalCallable);
			retrievdFutureDataList.add(retrievedDataFuture);
		}

		executorService.shutdown();

		while(!executorService.isTerminated()){
			boolean allThreadsCompleted = false;
			try {
				allThreadsCompleted = executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(allThreadsCompleted)
				break;
		}

		List<String> aggregatedDataList = aggregateDataFromSources(retrievdFutureDataList);

		LOG.info("aggregated Data List for both sources: for rptPrd: "+rptPrd+": "+aggregatedDataList);
		return aggregatedDataList;

	}

	/**
	 * aggregation service logic 
	 * @param retrievdFutureDataList
	 * @return
	 */
	private List<String> aggregateDataFromSources(
			List<Future<String>> retrievdFutureDataList) {
		// iterate over this list and aggregate data form both the sources
		List<String> aggregatedDataList = new ArrayList<String>();
		for (Future<String> future : retrievdFutureDataList) {
			try {
				aggregatedDataList.add(future.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return aggregatedDataList;
	}

}
