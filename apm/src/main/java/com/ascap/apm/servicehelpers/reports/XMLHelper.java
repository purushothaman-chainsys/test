package com.ascap.apm.servicehelpers.reports;


import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ascap.apm.common.utils.cache.PrepKeyValueObject;
import com.ascap.apm.common.utils.constants.PrepConstants;
import com.ascap.apm.vob.reports.Parameter;
import com.ascap.apm.vob.reports.rest.Logon;

public class XMLHelper {
	
	public String buildLogonXML(Logon logon) {
		String response = null;
		try {
			Document doc = DOMHelper.createDocument();
			if (logon != null) {
				Element attrs = doc.createElement("attrs");
				doc.appendChild(attrs);
			
				Element attr = null; 
					
				attr = doc.createElement("attr");
				attr.setAttribute("name", "userName");
				attr.setAttribute("type", "string");
				attr.setTextContent(logon.getUserName());
				attrs.appendChild(attr);

				attr = doc.createElement("attr");
				attr.setAttribute("name", "password");
				attr.setAttribute("type", "string");
				attr.setTextContent(logon.getPassword());
				attrs.appendChild(attr);

				attr = doc.createElement("attr");
				attr.setAttribute("name", "auth");
				attr.setAttribute("type", "string");
				attr.setTextContent(logon.getAuth());
				attrs.appendChild(attr);
			}

			response = DOMHelper.createStringFromDocument(doc);
		} catch (Exception e) {
			System.out.println("Exception occurred while generating Logon XML Object");
			e.printStackTrace();
		}
		
		return response;
	}
	
	public String parseLogonTokenXML(String incomingXML) {
		
		String response = null;

		try {
			Document document = DOMHelper.createDocumentFromString(incomingXML);
			XPath xPath = XPathFactory.newInstance().newXPath();
			if (document != null) {
				System.out.println("Document"+document.getElementsByTagName("logonToken").getLength());
				response = "\""+((Node) xPath.evaluate("//*[@name='logonToken']", document, XPathConstants.NODE)).getTextContent()+"\"";
			}
		} catch (Exception e) {
			System.out.println("Exception occurred while generating Logontoken Object from Incoming XML");
			e.printStackTrace();
		}
		
		return response;
	}

	public ArrayList<Parameter> parseParametersXML(String incomingXML) {
		
		ArrayList<Parameter> response = new ArrayList<>();

		try {
			
			Document document = DOMHelper.createDocumentFromString(incomingXML);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			if (document != null) {
				NodeList parameters = document.getElementsByTagName("parameter");
				if(parameters != null && parameters.getLength() > 0){
					for (int i = 0; i < parameters.getLength(); i++){
						Node paramterNode = parameters.item(i);
						Parameter  parameter = new Parameter();
						parameter.setParamName(((Node)xPath.evaluate("name", paramterNode, XPathConstants.NODE)).getTextContent());
						if(PrepConstants.WEBI_PROMPT_OPTIONAL_TRUE.equalsIgnoreCase(paramterNode.getAttributes().getNamedItem("optional").getTextContent())){
							parameter.setRequired(false);
						} else {
							parameter.setRequired(true);
						}
						
						Node answerNode = (Node)xPath.evaluate("answer", paramterNode, XPathConstants.NODE);
						parameter.setParameterType(PrepConstants.WEBI_TO_REPORT_MAPPING.get(answerNode.getAttributes().getNamedItem("type").getTextContent()));
						Node infoNode = (Node)xPath.evaluate("info", answerNode, XPathConstants.NODE);
						if(PrepConstants.WEBI_PROMPT_CORDINALITY_MULTIPLE.equalsIgnoreCase(infoNode.getAttributes().getNamedItem("cardinality").getTextContent())){
							parameter.setMultiValued(true);
						} else {
							parameter.setMultiValued(false);
						}
						
						Node lovNode = (Node)xPath.evaluate("lov", infoNode, XPathConstants.NODE);
						if(lovNode != null){
							NodeList paramListNode = (NodeList)xPath.evaluate("values/value", lovNode, XPathConstants.NODESET);
							if(paramListNode != null && paramListNode.getLength() > 0){
								ArrayList<PrepKeyValueObject> parameterValues = new ArrayList<>();
								PrepKeyValueObject keyValueObject = null;
								for(int j = 0; j < paramListNode.getLength(); j++){
										keyValueObject = new PrepKeyValueObject();
										keyValueObject.setKey((paramListNode.item(j)).getTextContent());
										keyValueObject.setValue((paramListNode.item(j)).getTextContent());
										parameterValues.add(keyValueObject);
								}
								parameter.setDefaultList(true);
								parameter.setMultipleValues(parameterValues);
							}
						}
						System.out.println("yyyyyyyyyyyyyyy"+parameter);
						response.add(parameter);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception occurred while generating Parameters Object from Incoming XML");
			e.printStackTrace();
		}
		
		return response;
	}
	
	public String buildScheduleXML(String parametersXML, List<Parameter> parameters) {
		
		String response = null;
		
		try {
			Document document = DOMHelper.createDocumentFromString(parametersXML);
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			if (document != null) {
				NodeList parametersNodeList = document.getElementsByTagName("parameter");
				if(parameters != null && parametersNodeList.getLength() > 0){
					for (int i = 0; i < parametersNodeList.getLength(); i++){
						Node paramterNode = parametersNodeList.item(i);
						Node answerNode = (Node)xPath.evaluate("answer", paramterNode, XPathConstants.NODE);
						if(answerNode != null){
							Node valuesNode = (Node)xPath.evaluate("values", answerNode, XPathConstants.NODE);
							valuesNode.getParentNode().removeChild(valuesNode);
				            if(parameters.get(i) != null){
					            Element newValuesElement = document.createElement("values");
					            Element newValueElement = document.createElement("value");
				            	Parameter parameter = (Parameter)parameters.get(i);
				            	newValueElement.setTextContent(parameter.getInputValue());
				            	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+parameter.getInputValue());
					            newValuesElement.appendChild(newValueElement);
					            answerNode.appendChild(newValuesElement);
				            }
						}
					}
				}
				response = DOMHelper.createStringFromDocument(document);
			}
		} catch (Exception e) {
			System.out.println("Exception occurred while generating Logon XML Object");
			e.printStackTrace();
		}
		
		System.out.println("#################################"+response);
		return response;
	}

}
