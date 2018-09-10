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
        SAXParserFactory spf = SAXParserFactory.newDefaultInstance();
        spf.setNamespaceAware(true);
        SAXParser saxparser = spf.newSAXParser();
        List<Film> films = new ArrayList<>();
        saxparser.parse("films.xml", new MyContentHandler(films));

        films.forEach(System.out::println);
    }
}

class Film {
    private String titel;
    private String genre;
    private int jaar;

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getJaar() {
        return jaar;
    }

    public void setJaar(int jaar) {
        this.jaar = jaar;
    }

    @Override
    public String toString() {
        return "Film{" +
                "titel='" + titel + '\'' +
                ", jaar='" + jaar + '\'' +
                ", genre=" + genre +
                '}';
    }
}

class MyContentHandler extends DefaultHandler {
    private StringBuilder tekstBuilder = new StringBuilder();
    private List<Film> films;
    private Film film;

    public MyContentHandler(List<Film> films) {
        this.films = films;
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start document.");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Einde document.");
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tekstBuilder.append(ch,start,length);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tekstBuilder.setLength(0);
        if (localName.equals("film")) {
            film = new Film();
            for (int i = 0; i < attributes.getLength(); i++) {
                film.setGenre(attributes.getValue(i));
            }
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (localName){
            case "titel":
                film.setTitel(tekstBuilder.toString());
                break;
            case "jaar":
                film.setJaar(Integer.parseInt(tekstBuilder.toString()));
                break;
            case "film":
                films.add(film);
                break;
        }
        tekstBuilder.setLength(0);
    }
}