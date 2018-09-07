package com.company;

import org.xml.sax.SAXException;
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
    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Stop");
    }
}