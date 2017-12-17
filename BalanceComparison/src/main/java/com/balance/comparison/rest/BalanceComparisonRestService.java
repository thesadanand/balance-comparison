package com.balance.comparison.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.balance.comparison.model.BalanceComparisonRequest;

/**
 * rest web-service servlet class 
 * @author Tanu
 *
 * http://localhost:8080/BalanceComparison/compareBalance
   http://localhost:8080/BalanceComparison/compareBalance
 *
 * {"name" : "abc"}
 */

@Component
@Path("/compareBalance")
public class BalanceComparisonRestService {

	private static Logger LOG =  Logger.getLogger(BalanceComparisonRestService.class);

	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response compareBlancesBetweenAccounts(@RequestBody BalanceComparisonRequest clientRequest, 
			@Context HttpServletRequest httpServletRequest ) throws Exception {

		LOG.info("test logging");

		return Response.status(200).entity("hello world: "+clientRequest.getName()).build();
	}

}
