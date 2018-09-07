package com.company;

import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws SAXException, IOException {

        try {
            SchemaFactory schemaFactory = SchemaFactory.newDefaultInstance();
            Schema schema = schemaFactory.newSchema(new StreamSource("boeken.xsd"));

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource("boeken.xml"));

            System.out.println("Validatie is gelukt.");

        } catch (SAXException|IOException ex){
            System.out.println(ex.getMessage());
        }


    }
}
