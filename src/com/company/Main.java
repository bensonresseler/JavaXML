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
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newDefaultInstance();
        Schema schema = schemaFactory.newSchema(new StreamSource("boeken.xsd"));

        SAXParserFactory spf = SAXParserFactory.newDefaultInstance();
        spf.setSchema(schema);
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();

        List<String> titels = new ArrayList<>();
        saxParser.parse("boeken.xml",new MyContentHandler(titels));
        System.out.println("De titels: ");
        titels.forEach(System.out::println);

    }
}

class MyContentHandler extends DefaultHandler {
    private List<String> titels = new ArrayList<>();
    private StringBuilder tekstBuilder = new StringBuilder();

    public MyContentHandler(List<String> titels) {
        this.titels = titels;
    }

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

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tekstBuilder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equals("titel")){
            titels.add(tekstBuilder.toString());
        }
        tekstBuilder.setLength(0); // terug leegmaken
    }
}