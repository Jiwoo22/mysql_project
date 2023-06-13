package com.hospital;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLValidation {

    public static void main(String[] args) {
        String xmlFilePath = "src/test/hospital.xml";

        // Validate XML against XSD
        boolean isValid = validateXMLSchema(xmlFilePath);
        System.out.println("XML validation result: " + isValid);

        // Parse XML using DOM parser
        if (isValid) {
            parseXMLWithDOM(xmlFilePath);
        }

    }

    private static boolean validateXMLSchema(String xmlFilePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setFeature("http://apache.org/xml/features/validation/schema", true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(null);
            builder.parse(xmlFilePath);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void parseXMLWithDOM(String xmlFilePath){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFilePath);

            // get all employee's names
            NodeList employeeList = document.getElementsByTagName("employee");

            // Iterate over the employee elements and print their names
            for (int i = 0; i < employeeList.getLength(); i++) {
                Element employeeElement = (Element) employeeList.item(i);
                Element nameElement = (Element) employeeElement.getElementsByTagName("name").item(0);
                String name = nameElement.getTextContent();
                System.out.println(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
