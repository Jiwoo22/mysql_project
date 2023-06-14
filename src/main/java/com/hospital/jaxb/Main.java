package com.hospital.jaxb;

import jakarta.xml.bind.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        try {
            File xmlFile = new File("src/test/hospital.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Hospital.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Hospital hospital = (Hospital) unmarshaller.unmarshal(xmlFile);

            List<Employee> employees = hospital.getEmployees();
            for (Employee employee : employees) {
                System.out.println("Employee Name: " + employee.getName());
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
