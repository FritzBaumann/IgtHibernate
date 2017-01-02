package com.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int appointmentId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @NotNull
    private double value;

    public Appointment(Customer customer, double value) {
        this.value = value;
        this.customer = customer;
    }

    public Appointment() {
    }
    public Customer getCustomer() {
        return customer;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
