package com.ascap.apm.servicehelpers.reports;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DOMHelper {

	/**
	 * Sets a DOM Element property, name, and value
	 * 
	 * @param property
	 * @param name
	 * @param value
	 * @return
	 */
	public static Element setElementAttributes(Element property, String name, String value) {
		property.setAttribute("name", name);
		property.setAttribute("value", value);

		return property;
	}

	/**
	 * Creates a DOM document from a String
	 * 
	 * @param incomingString
	 * @return
	 * @throws ParserConfigurationException
	 * @throws UnsupportedEncodingException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document createDocumentFromString(String incomingString) throws ParserConfigurationException, UnsupportedEncodingException, SAXException, IOException {

		Document document = null;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		document = docBuilder.parse(new InputSource(new ByteArrayInputStream(incomingString.getBytes("utf-8"))));
		document.getDocumentElement().normalize();

		return document;
	}

	/**
	 * Creates a DOM Document
	 * 
	 * @return
	 * @throws ParserConfigurationException
	 */
	public static Document createDocument() throws ParserConfigurationException {
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();

		return docBuilder.newDocument();
	}

	/**
	 * Creates a String from a DOM Document
	 * 
	 * @param doc
	 * @return
	 * @throws TransformerException
	 */
	public static String createStringFromDocument(Document doc) throws TransformerException {
		// Create Transformer
		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans = transfac.newTransformer();
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");

		// Create String from document
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(doc);
		trans.transform(source, result);

		return sw.toString();
	}

}
