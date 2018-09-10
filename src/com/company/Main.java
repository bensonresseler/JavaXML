package com.company;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.management.Attribute;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("boeken2.xml");

        Element boekElement = doc.createElement("boek");
        Element titelElement = doc.createElement("titel");
        Element beoordelinglElement = doc.createElement("beoordeling");
        Element auteurElement = doc.createElement("auteur");

        doc.getDocumentElement().appendChild(boekElement);
        boekElement.appendChild(titelElement);
        boekElement.appendChild(beoordelinglElement);
        boekElement.appendChild(auteurElement);

        Text titelTekstElement = doc.createTextNode("Harry Potter and the Philosophers Stone");
        Text beoordelingTekstElement = doc.createTextNode("heel goed");
        Text auteurTekstElement = doc.createTextNode("J. K. Rowling");

        titelElement.appendChild(titelTekstElement);
        beoordelinglElement.appendChild(beoordelingTekstElement);
        auteurElement.appendChild(auteurTekstElement);

        auteurElement.setAttribute("geslacht", "v");

        TransformerFactory tff = TransformerFactory.newDefaultInstance();
        Transformer transformer = tff.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult("boeken2.xml");
        transformer.transform(source, result);
        result = new StreamResult(System.out);
        transformer.transform(source, result);
    }
}