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

import com.balance.comparison.model.BalanceComparisonRequest;
import com.balance.comparison.util.BalanceComparisonUtils;

/**
 * 
 * @author Tanu
 *
 */
public class DataPartitionerExecutorService {


	private static Logger LOG = Logger.getLogger(DataPartitionerExecutorService.class);

	DataRetreivalService dataRetreivalExecutorService;

	DataComparisonService dataComparisonExecutorService;

	// inject data comparison service

	@Autowired
	BalanceComparisonUtils balanceComparisonUtils;

	private int numOfThreads= 2;

	public List<String> compareDataForAllPartitons(BalanceComparisonRequest request){

		// get all reporting periods for the given from and to date range
		List<String> rptPrds = balanceComparisonUtils.getRrpPrd(request.getDateFrom(), request.getDateTo());
		LOG.info("Total Number of Reporting Periods: "+rptPrds);

		
		// call comparison service on each partion map returned 
		LOG.info("comparing all partitions with "+numOfThreads+" threads");

		
		// call data retiival for each partions using n threads
		ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
		List<Future<String>> compareFutureList = new ArrayList<Future<String>>();
		for(String rptPrd: rptPrds){
			Callable<String> callable = 
					new DataPartitionerCallable(dataRetreivalExecutorService, dataComparisonExecutorService, rptPrd,request);
			Future<String> futureResponse = executorService.submit(callable);
			compareFutureList.add(futureResponse);
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

		List<String> futurListOutput = new ArrayList<String>();
		for (Future<String> future : compareFutureList) {
			try {
				futurListOutput.add(future.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return futurListOutput;

	}

	public DataRetreivalService getDataRetreivalExecutorService() {
		return dataRetreivalExecutorService;
	}

	public void setDataRetreivalExecutorService(
			DataRetreivalService dataRetreivalExecutorService) {
		this.dataRetreivalExecutorService = dataRetreivalExecutorService;
	}

	public DataComparisonService getDataComparisonExecutorService() {
		return dataComparisonExecutorService;
	}

	public void setDataComparisonExecutorService(
			DataComparisonService dataComparisonExecutorService) {
		this.dataComparisonExecutorService = dataComparisonExecutorService;
	}



}
