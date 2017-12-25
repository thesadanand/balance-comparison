package com.balance.comparison.executor;

import java.util.List;

import org.apache.log4j.Logger;

import com.balance.comparison.util.SourceMappingDTORepository;

public class DataComparisonService {

	private static Logger LOG = Logger.getLogger(DataComparisonService.class);
	
	private SourceMappingDTORepository sourceMappingDTORepository;
	
	public String compareData(List<String> aggregatedData) {
		
		LOG.info("inside DataComparisonService");
		
		// TODO Auto-generated method stub
		LOG.info("comparing data for source : "+aggregatedData);
		LOG.info("SourceMappingDTORepository: "+sourceMappingDTORepository.toString());
		return "compared data for "+aggregatedData;
	}

	public SourceMappingDTORepository getSourceMappingDTORepository() {
		return sourceMappingDTORepository;
	}

	public void setSourceMappingDTORepository(
			SourceMappingDTORepository sourceMappingDTORepository) {
		this.sourceMappingDTORepository = sourceMappingDTORepository;
	}
	
	
}
