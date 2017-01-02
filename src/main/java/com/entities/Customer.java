package com.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int customerId;
    @NotNull
    private String lastName;
    @NotNull
    private String firstName;
    @NotNull
    private String adress;
    @NotNull
    private Date birthDate;
    @NotNull
    private double sales;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "appointmentId")
    private Set<Appointment> appointment;

    @Override
    public String
    toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", adress='" + adress + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    public Customer() {
    }

    public Customer(int customerId) {
        this.customerId = customerId;
    }

    public Customer(String lastName, String firstName, Date birthDate, String adress) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.adress = adress;
        this.sales = 0;
    }

    public String getAdress() {
        return adress;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getCustomerId() {
        return customerId;
    }


    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Set<Appointment> getAppointment() {
        return appointment;
    }

    public void setAppointment(Set<Appointment> appointment) {
        this.appointment = appointment;
    }
}