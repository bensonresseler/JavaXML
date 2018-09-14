package com.company;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Scanner scanner = new Scanner(System.in);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        System.out.print("Geef bestandsnaam: ");
        String bestandsnaam = scanner.nextLine();

        Document doc = getXMLDocument(db, bestandsnaam);
        doc.normalizeDocument();
        toonChildren(doc);


        System.out.print("Geef voornaam: ");
        String voornaam = scanner.nextLine();
        System.out.print("Geef achternaam: ");
        String achternaam = scanner.nextLine();
        System.out.print("Geef geboortejaar: ");
        int geboortjaar = Integer.parseInt(scanner.nextLine());
        System.out.print("Geef geboortemaand: ");
        int geboortmaand = Integer.parseInt(scanner.nextLine());
        System.out.print("Geef geboortedag: ");
        int geboortdag = Integer.parseInt(scanner.nextLine());
        LocalDate geboortedatum = LocalDate.of(geboortjaar,geboortmaand,geboortdag);
        Persoon persoon = new Persoon(voornaam, achternaam, geboortedatum);

        voegPersoonToe(doc, persoon, bestandsnaam);
        toonChildren(doc);
    }
    private static void voegPersoonToe(Document doc, Persoon persoon, String bestandsnaam) throws TransformerException {
        Element personenElement = doc.getDocumentElement();
        Element persoonElement = doc.createElement("persoon");
        Element voornaamElement = doc.createElement("voornaam");
        Element achternaamElement = doc.createElement("achternaam");
        Element geboortedatumElement = doc.createElement("geboortedatum");
        voornaamElement.setTextContent(persoon.getVoornaam());
        achternaamElement.setTextContent(persoon.getAchternaam());
        geboortedatumElement.setTextContent(persoon.getGeboortedatumString());
        persoonElement.appendChild(voornaamElement);
        persoonElement.appendChild(achternaamElement);
        persoonElement.appendChild(geboortedatumElement);
        personenElement.appendChild(persoonElement);

        bewaarBestand(doc, bestandsnaam);
    }
    private static void bewaarBestand(Document doc, String output) throws TransformerException {
        TransformerFactory tff = TransformerFactory.newDefaultInstance();
        DOMSource source = new DOMSource(doc);
        Transformer transformer = tff.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
    }
    private static void toonChildren(Document doc){
        Element rootElement = doc.getDocumentElement();
        for(int i=0;i<rootElement.getChildNodes().getLength();i++){
            if (rootElement.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element persoonElement = (Element) rootElement.getChildNodes().item(i);
                Element voornaamElement = (Element) persoonElement.getElementsByTagName("voornaam").item(0);
                Element achternaamElement = (Element) persoonElement.getElementsByTagName("achternaam").item(0);
                Element geboortedatumElement = (Element) persoonElement.getElementsByTagName("geboortedatum").item(0);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate geboortedatum = LocalDate.parse(geboortedatumElement.getTextContent(), formatter);

                Persoon p =  new Persoon (voornaamElement.getTextContent(), achternaamElement.getTextContent(), geboortedatum);
                System.out.printf("%s %s geboren op %s (%d jaar oud).%n", p.getVoornaam(), p.getAchternaam(), p.getGeboortedatumString(), p.getLeeftijd());
            }
        }
    }
    private static Document getXMLDocument(DocumentBuilder db, String bestandsnaam) throws IOException, SAXException {
        File file = new File(bestandsnaam);
        Document doc;
        if (file.exists()){
            doc = db.parse(file);
        }else{
            System.out.println("Bestand bestaat nog niet. Het wordt gecreëerd.");
            doc = db.newDocument();
            doc.appendChild(doc.createElement("personen"));
        }
        return doc;
    }
}

class Persoon {
    private String voornaam;
    private String achternaam;
    private LocalDate geboortedatum;
    private String geboortedatumString;
    private int leeftijd;

    public Persoon(String voornaam, String achternaam, LocalDate geboortedatum) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public int getLeeftijd() {
        LocalDate datumVandaag = LocalDate.now();
        Period periode = Period.between(geboortedatum, datumVandaag);
        this.leeftijd = periode.getYears();
        return leeftijd;
    }

    public String getGeboortedatumString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return geboortedatum.format(formatter);
    }

}