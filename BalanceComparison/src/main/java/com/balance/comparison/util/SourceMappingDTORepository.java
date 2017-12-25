package com.balance.comparison.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.xml.sax.SAXException;

import com.balance.comparison.dto.BalanceComparisonDTO;



/**
 * Used to validate and load all input data to in-memory map
 * @author Tanu
 *
 */
public class SourceMappingDTORepository {

	private static Logger LOG = Logger.getLogger(SourceMappingDTORepository.class);

	private final Map<String, BalanceComparisonDTO> dtoMap = new ConcurrentHashMap<String, BalanceComparisonDTO>();

		@Value(value="${config.approch}")
	private String configurationApproch; // = "XML";

	public void loadInputData(){
		LOG.info("configuration approach used is: "+configurationApproch);
		if("XML".equalsIgnoreCase(configurationApproch)){
			loadXMLConfigurations();
		}
		else if("JSON".equalsIgnoreCase(configurationApproch))
			loadJSONConfigurations();
	}

	private void loadXMLConfigurations() {
		LOG.info("Validating source xml files against schema");
		//		Resource path = new ClassPathResource(BalanceComparisonConstants.INPUT_XML_FILES_DIR);
		File xmlFolder = null;
		try {
			xmlFolder = new File(BalanceComparisonConstants.INPUT_XML_FILES_DIR);
		} catch (Exception e) {
			LOG.error("Error while searching for input xml ",e);
			e.printStackTrace();
		}
		File[] listOfInputXml = xmlFolder.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.endsWith("xml");
			}
		});

		if(listOfInputXml!=null){
			for(File xmlInputFile : listOfInputXml){
				String[] inputFileName = xmlInputFile.getName().split("\\.");
				if(validateXMLInput(xmlInputFile, inputFileName[0])){
					try {
						JAXBContext jaxbContext = JAXBContext.newInstance(com.balance.comparison.dto.ObjectFactory.class);
						Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
						JAXBElement<BalanceComparisonDTO> balanceComparisonDTO = 
								(JAXBElement)unmarshaller.unmarshal(xmlInputFile);
						dtoMap.put(inputFileName[0], balanceComparisonDTO.getValue());
					} catch (JAXBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}else
			LOG.error("no more valid xml files to parse");
	}

	private boolean validateXMLInput(File xmlInputFile, String fileName) {

		LOG.info("Applying xsd based validation on input xml file: "+fileName);
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			Schema schema = factory.newSchema(new File(BalanceComparisonConstants.XSD_FILE_PATH));
			Validator validator = schema.newValidator();
			validator.setErrorHandler(new BalaceComparisonXmlErrorHandler());
			validator.validate(new StreamSource(xmlInputFile));
		} catch (SAXException e) {
			LOG.error("Failed Schema based validations on input xml file: "+fileName);
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			LOG.error("Failed Schema based validations on input xml file: "+fileName);
			e.printStackTrace();
			return false;
		}
		LOG.info("Passed Schema based validations on input xml file: "+fileName);
		return true;
	}

	//TODO:
	private void loadJSONConfigurations() {
		// TODO Auto-generated method stub

	}

	public Map<String, BalanceComparisonDTO> getDtoMap() {
		return dtoMap;
	}

	public BalanceComparisonDTO getDtoMapForSource(String source) {
		return dtoMap.get(source);
	}
	
//	public List<BalanceComparisonDTO> get

}
