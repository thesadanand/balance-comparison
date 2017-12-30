package com.balance.comparison.executor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.balance.comparison.model.AggregatedDataDTO;
import com.balance.comparison.model.BalanceComparisonRequest;
import com.balance.comparison.util.SourceMappingDTORepository;

public class DataRetreivalService {

	private static Logger LOG = Logger.getLogger(DataRetreivalService.class);

	//as we compare against 2 sources
	private final static int NUM_THREADS_FOR_DATA_RETRIEVAL=2;

	@Autowired
	private SourceMappingDTORepository sourceMappingDTORepository;

	/**
	 * 
	 * @param request
	 * @param rptPrd
	 * @return
	 */
	public List<AggregatedDataDTO> retrieveData(BalanceComparisonRequest request, String rptPrd) {

		LOG.info("inside DataReterivalService: ");

		ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS_FOR_DATA_RETRIEVAL);
		List<String> sources = request.getSources();
		List<Future<Map<String,Double>>> retrievdFutureDataList = new ArrayList<Future<Map<String,Double>>>();

		for(String source: sources){
			Callable<Map<String, Double>> dataRetrievalCallable  = new DataReterivalServiceCallable(source,rptPrd,sourceMappingDTORepository);
			Future<Map<String, Double>> retrievedDataFuture = executorService.submit(dataRetrievalCallable);
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

		List<AggregatedDataDTO> aggregatedDataDTOList = new ArrayList<AggregatedDataDTO>();
		aggregatedDataDTOList = aggregateDataFromSources(retrievdFutureDataList, aggregatedDataDTOList);
		LOG.info("aggregated Data List for both sources: for rptPrd: "+rptPrd+": "+aggregatedDataDTOList);
		return aggregatedDataDTOList;

	}

	private List<AggregatedDataDTO> aggregateDataFromSources(
			List<Future<Map<String, Double>>> retrievdFutureDataList, List<AggregatedDataDTO> aggregatedDataDTOList) {
			AggregatedDataDTO dto; 
		for (Future<Map<String, Double>> future : retrievdFutureDataList) {
			try {
				dto = new AggregatedDataDTO();
				setDTODetails(dto,future);
				dto.setDataMap((Map)future.get());
				aggregatedDataDTOList.add(dto);
				LOG.info(""+(Map)future.get());
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		return aggregatedDataDTOList;
	}

	
	private void setDTODetails(AggregatedDataDTO dto,
			Future<Map<String, Double>> future) {
		try {
				Map<String, Double> futureMap = (Map)future.get();
				for(String key: futureMap.keySet()){
					String[] tokens = key.split("~");
					dto.setSource(tokens[0]);
					dto.setRptPrd(tokens[1]);
					dto.setTransactionType(tokens[2]);
				}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/** No Longer Needed
	 * aggregation service logic 
	 * @param retrievdFutureDataList
	 * @return
	 */
	/*private List<String> aggregateDataFromSourcesTemp(
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
*/
}
