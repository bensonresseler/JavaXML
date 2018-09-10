package com.company;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("elements.xml");
        XPathFactory factory = XPathFactory.newDefaultInstance();
        XPath xpath = factory.newXPath();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Geef een symbool: ");
        String symbool = scanner.nextLine();
        String path = String.format("elements/element[symbol='%s']/name/text()", symbool);
        String naam = (String) xpath.evaluate(path, doc, XPathConstants.STRING);
        if (naam.equals("")) System.out.println("Dit element bestaat niet.");
        else System.out.printf("Het element met symbool %s heet %s.%n", symbool, naam);
    }
}