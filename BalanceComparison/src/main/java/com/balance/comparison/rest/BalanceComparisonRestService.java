package com.balance.comparison.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.balance.comparison.executor.DataPartitionerExecutorService;
import com.balance.comparison.model.BalanceComparisonRequest;

/**
 * rest web-service servlet class 
 * @author Tanu
 *
 * http://localhost:8080/BalanceComparison/compareBalance
   http://localhost:8080/BalanceComparison/compareBalance
 *
 * {"name" : "abc"}
 
 {
 "dateFrom":201712,
 "dateFrom":201712,
 "measure": "all"
 }
 *
 */

@Component
@Path("/compareBalance")
public class BalanceComparisonRestService {

	private static Logger LOG =  Logger.getLogger(BalanceComparisonRestService.class);

	// inject request validator service
	
	@Autowired
	private DataPartitionerExecutorService dataPartitionerExecutorService; 
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response compareBlancesBetweenAccounts(@RequestBody BalanceComparisonRequest clientRequest, 
			@Context HttpServletRequest httpServletRequest ) throws Exception {

		LOG.info("test logging");

	// validate input request
		
		// call compare all partioon on datapartition service
		// this will return a final aggregated map 
		
		List<String> comparedData = dataPartitionerExecutorService.compareDataForAllPartitons(clientRequest);
		LOG.info("final compared Data set:  "+comparedData);
		return Response.status(200).entity("hello world: "+clientRequest.getName()).build();
		
	}

	public void setDataPartitionerExecutorService(
			DataPartitionerExecutorService dataPartitionerExecutorService) {
		this.dataPartitionerExecutorService = dataPartitionerExecutorService;
	}

	public DataPartitionerExecutorService getDataPartitionerExecutorService() {
		return dataPartitionerExecutorService;
	}
	
	
	
}
