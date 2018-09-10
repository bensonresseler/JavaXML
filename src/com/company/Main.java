package com.company;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("elements.xml");
        XPathFactory factory = XPathFactory.newDefaultInstance();
        XPath xpath = factory.newXPath();
        javax.xml.xpath.XPathExpression expression =  xpath.compile("elements/element/symbol/text()");
        NodeList symbolen = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < symbolen.getLength(); i++) {
            System.out.println("Symbool: " + symbolen.item(i).getNodeValue());

    }
}}