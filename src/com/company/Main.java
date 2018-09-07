package com.company;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newDefaultInstance();
        Schema schema = schemaFactory.newSchema(new StreamSource("boeken.xsd"));

        SAXParserFactory spf = SAXParserFactory.newDefaultInstance();
        spf.setSchema(schema);
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();

        saxParser.parse("boeken.xml",new MyContentHandler());
    }
}

class MyContentHandler extends DefaultHandler {
    private StringBuilder tekstBuilder = new StringBuilder();

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Begin van het document.");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Einde van het document.");
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        System.out.println("Er is een fout.");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.printf("Begin element <%s>%n", localName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tekstBuilder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.printf("De tekst van %s is %s%n", localName, tekstBuilder.toString());
        tekstBuilder.setLength(0); // terug leegmaken
    }
}