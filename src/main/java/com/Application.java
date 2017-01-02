package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// WebService URL's:

/* Kunde: */
// Hinzufügen:          http://localhost:8080/customer/add?lastName=Albers&firstName=Hans&birthDate=1990-01-01&adress=hallo
// Abrufen:             http://localhost:8080/customer/get?customerId=1
// Umsatz abfragen:

/* Bestellungen */
// Hinzufügen:          http://localhost:8080/appointment/add?customerId=1&value=123.2
//  http://localhost:8080/discount/calculate?customerId=1
//  http://localhost:8080/validateWSDL?wsdl_url=https://svn.apache.org/repos/asf/airavata/sandbox/xbaya-web/test/Calculator.wsdl