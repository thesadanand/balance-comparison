package com.balance.comparison.executor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.balance.comparison.dto.BalanceComparisonDTO;
import com.balance.comparison.dto.TransactionDTO;
import com.balance.comparison.util.BalanceComparisonConstants;
import com.balance.comparison.util.SourceMappingDTORepository;

public class DataReterivalServiceCallable implements Callable<String> { 


	private static Logger LOG = Logger.getLogger(DataReterivalServiceCallable.class);

	SourceMappingDTORepository sourceMappingDTORepository;

	Map<String, Double> aggregatedMap = new ConcurrentHashMap<String, Double>();

	private String source;

	//inject static map from spring context
	private String rptPrd;

	public DataReterivalServiceCallable(String source, String rptPrd, 
			SourceMappingDTORepository sourceMappingDTORepository2) {
		this.source = source;
		this.rptPrd = rptPrd;
		this.sourceMappingDTORepository = sourceMappingDTORepository2;
	}

	public String call() throws Exception {
		//do a map lookup and get data for that source;
		// aggregate the data;
		String str = Thread.currentThread().getName().concat(": got data for source: "+source+" for rptPrd: "+this.rptPrd);
		LOG.info(str);
		LOG.info("<==>:: "+source+"~"+this.rptPrd);
		BalanceComparisonDTO balanceComparisonDTO =  sourceMappingDTORepository.getDtoMapForSource(source);

		Map<String, Double> aggregatedData = aggregateData(balanceComparisonDTO.getTransaction());
		LOG.info("aggregated data size for source: "+source+ "rpt prd: "+rptPrd+"  "+aggregatedData);
		return str;

	}

	private Map<String, Double> aggregateData(List<TransactionDTO> transactionDTOList) {

		String creditTransctionkey = source+"~"+rptPrd+"~c";
		LOG.info("credit transction map key: "+creditTransctionkey);

		String debitTransctionkey = source+"~"+rptPrd+"~d";
		LOG.info("debit transction map key: "+debitTransctionkey);
		String dtoKey = null;
		double dtoAmt = 0;
		for(TransactionDTO dto : transactionDTOList) {
			if(dto.getDate()==Long.parseLong(rptPrd)){
				dtoKey = source+"~"+dto.getDate()+"~"+dto.getType();
				dtoAmt = dto.getAmount();
				if(aggregatedMap.get(dtoKey)==null)
					aggregatedMap.put(dtoKey,dtoAmt);
				else{
					dtoAmt = aggregatedMap.get(dtoKey);
					dtoAmt+=dto.getAmount();
					aggregatedMap.put(dtoKey,dtoAmt);
					//					if(dto.getType().equalsIgnoreCase(BalanceComparisonConstants.DEBIT_TRANS)) 
					//						aggregatedMap.put(debitTransctionkey, dotAmt);
					//					else if(dto.getType().equalsIgnoreCase(BalanceComparisonConstants.CREDIT_TRANS)) 
					//						aggregatedMap.put(creditTransctionkey,dotAmt);
				}

			}
			/*double amt=0;
			if((aggregatedMap.get(debitTransctionkey)==null) ||
					(aggregatedMap.get(debitTransctionkey)==null))	{
				aggregatedMap.put(dtoKey,dto.getAmount());
			}*/
		}
		return aggregatedMap;
	}

}
