package com.balance.comparison.executor;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.balance.comparison.model.AggregatedDataDTO;
import com.balance.comparison.model.ComparedDataDTO;
import com.balance.comparison.util.SourceMappingDTORepository;

public class DataComparisonService {

	private static Logger LOG = Logger.getLogger(DataComparisonService.class);

	private SourceMappingDTORepository sourceMappingDTORepository;

	/**
	 * compare the aggregated data for each source~rptPrd~TransctionType combination
	 * @param aggregatedDataList
	 * @return comparedDataList
	 */
	public ComparedDataDTO compareData(List<AggregatedDataDTO> aggregatedDataList) {

		LOG.info("inside DataComparisonService");

		ComparedDataDTO comparedDataDTO = new ComparedDataDTO();
		comparedDataDTO = populateComparsionDTO(0, aggregatedDataList,comparedDataDTO);
		comparedDataDTO = populateComparsionDTO(1, aggregatedDataList,comparedDataDTO);
		comparedDataDTO = compareBalances(comparedDataDTO);
		LOG.info("comparing data for source : "+aggregatedDataList);
		LOG.info("SourceMappingDTORepository: "+sourceMappingDTORepository.toString());
		return comparedDataDTO;
	}

	/**
	 * does the actual comparison between the 2 sources
	 * @param comparedDataDTO
	 * @return
	 */
	private ComparedDataDTO compareBalances(
			ComparedDataDTO comparedDataDTO) {
		double blance1 = comparedDataDTO.getSource1AggreBalance();
		double blance2 = comparedDataDTO.getSource2AggreBalance();
		double absDiff = blance1 >= blance1 ? Math.abs(blance1-blance2) : Math.abs(blance2-blance1);
		comparedDataDTO.setBalanceDiff(absDiff);
		double perDiff = (absDiff/((blance1+blance2)/2) * 100);
		comparedDataDTO.setBalanceDiffPerct(perDiff);
		return comparedDataDTO;
	}

	/**
	 * populates the comparedDataDTO with aggregated details for source-1 
	 * @param aggregatedDataList
	 * @param comparedDataDTO
	 * @return
	 */
	private ComparedDataDTO populateComparsionDTO( int index, 
			List<AggregatedDataDTO> aggregatedDataList, ComparedDataDTO comparedDataDTO) {
		AggregatedDataDTO aggregatedDataDTO = aggregatedDataList.get(index);
		// in-case of data is available on only one side	
		if(aggregatedDataDTO.getSource()==null)
				return null;
		if(index==0){
			if(aggregatedDataDTO.getSource()==null)
				return null;
			comparedDataDTO.setSourceOne(aggregatedDataDTO.getSource());
			comparedDataDTO.setRptPrd(aggregatedDataDTO.getRptPrd());
			comparedDataDTO.setTranscationType(aggregatedDataDTO.getTransactionType());
			String source1Balancekey = comparedDataDTO.getSourceOne()+"~"+comparedDataDTO.getRptPrd()+"~"+comparedDataDTO.getTranscationType();
			Map<String, Double> dataMap = aggregatedDataDTO.getDataMap();
			for(String key: dataMap.keySet()){
				if(key.equalsIgnoreCase(source1Balancekey))
					comparedDataDTO.setSource1AggreBalance(aggregatedDataDTO.getDataMap().get(source1Balancekey));
			}
		}else{
			if(aggregatedDataDTO.getSource()==null)
				return null;
			comparedDataDTO.setSourceTwo(aggregatedDataDTO.getSource());
			comparedDataDTO.setRptPrd(aggregatedDataDTO.getRptPrd());
			comparedDataDTO.setTranscationType(aggregatedDataDTO.getTransactionType());
			String source2Balancekey = comparedDataDTO.getSourceTwo()+"~"+comparedDataDTO.getRptPrd()+"~"+comparedDataDTO.getTranscationType();
			Map<String, Double> dataMap = aggregatedDataDTO.getDataMap();
			for(String key: dataMap.keySet()){
				if(key.equalsIgnoreCase(source2Balancekey))
					comparedDataDTO.setSource2AggreBalance(aggregatedDataDTO.getDataMap().get(source2Balancekey));
			}
		}
		return comparedDataDTO;
	}

	public SourceMappingDTORepository getSourceMappingDTORepository() {
		return sourceMappingDTORepository;
	}

	public void setSourceMappingDTORepository(
			SourceMappingDTORepository sourceMappingDTORepository) {
		this.sourceMappingDTORepository = sourceMappingDTORepository;
	}

}
