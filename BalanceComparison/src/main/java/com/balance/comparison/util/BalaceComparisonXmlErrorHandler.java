package com.balance.comparison.util;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class BalaceComparisonXmlErrorHandler implements ErrorHandler {

	private static Logger LOG = Logger.getLogger(BalaceComparisonXmlErrorHandler.class);
	
	public void warning(SAXParseException exception) throws SAXException {
		LOG.warn("\nWARNING");
		exception.printStackTrace();
	}

	public void error(SAXParseException exception) throws SAXException {
		LOG.error("\nError occured while validating input xml", exception);
		throw exception;
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		LOG.error("\nFatal-Error occured while validating input xml", exception);
		throw exception;
	}

}
