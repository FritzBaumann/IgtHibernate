### IgtHibernate
#### WS URL's
#####Kunde:
Hinzufügen:          http://localhost:8080/customer/add?lastName=Muster&firstName=Max&birthDate=1990-01-01&adress=MusterAdresse

Abrufen:             http://localhost:8080/customer/get?customerId=1

Umsatz abfragen:     http://localhost:8080/customer/sales?customerId=1

#####Bestellungen
Hinzufügen:          http://localhost:8080/appointment/add?customerId=1&value=123.2

Auf Rabatt prüfen:   http://localhost:8080/discount/calculate?customerId=1