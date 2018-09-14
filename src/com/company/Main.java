package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException {
        Document doc = createXMLDocument();
        Element rootElement = doc.createElement("films");
        doc.appendChild(rootElement);
        Scanner scanner= new Scanner(System.in);
        System.out.print("Geef bestandsnaam: ");
        String bestandsnaam = scanner.nextLine();
        if (!bestandsnaam.endsWith(".xml")) bestandsnaam = bestandsnaam + ".xml";

        ArrayList<Film> filmlijst = new ArrayList<Film>();
        System.out.print("Geef filmtitel ('stop' om te stoppen): ");
        String titel = scanner.nextLine();

        while (!titel.equals("stop")) {
            System.out.print("Geef jaartal: ");
            int jaar = Integer.parseInt(scanner.nextLine());
            System.out.print("Geef regisseur: ");
            String regisseur = scanner.nextLine();

            Film film = new Film(titel, jaar, regisseur);
            filmlijst.add(film);

            System.out.print("Geef filmtitel ('stop' om te stoppen): ");
            titel = scanner.nextLine();
        }

        createFilmElement(doc, filmlijst);
        writeXMLFilm(doc, bestandsnaam);
    }


    private static void writeXMLFilm(Document doc, String bestandsnaam) throws TransformerException {
        TransformerFactory tff = TransformerFactory.newDefaultInstance();
        Transformer transformer = tff.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(bestandsnaam);
        transformer.transform(source, result);;
    }

    private static void createFilmElement(Document doc, ArrayList<Film> filmlijst) {
        Element rootElement = doc.getDocumentElement();
        for (Film film:filmlijst) {
            Element filmElement = doc.createElement("film");
            Element titelElement = doc.createElement("titel");
            Element jaarElement = doc.createElement("jaar");
            Element regisseurElement = doc.createElement("regisseur");
            titelElement.setTextContent(film.getTitel());
            jaarElement.setTextContent(Integer.toString(film.getJaar()));
            regisseurElement.setTextContent(film.getRegisseur());
            filmElement.appendChild(titelElement);
            filmElement.appendChild(jaarElement);
            filmElement.appendChild(regisseurElement);
            rootElement.appendChild(filmElement);
        }
    }

    private static Document createXMLDocument() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
        doc.setXmlStandalone(true);
        return doc;
    }
}

class Film {
    private String titel;
    private int jaar;
    private String regisseur;

    public Film(String titel, int jaar, String regisseur) {
        this.titel = titel;
        this.jaar = jaar;
        this.regisseur = regisseur;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public int getJaar() {
        return jaar;
    }

    public void setJaar(int jaar) {
        this.jaar = jaar;
    }

    public String getRegisseur() {
        return regisseur;
    }

    public void setRegisseur(String regisseur) {
        this.regisseur = regisseur;
    }
}