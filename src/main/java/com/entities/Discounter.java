package com.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Discounter implements Serializable{

    @Id
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "customerId")
    public Customer customer;

    @NotNull
    public double amount;

    public Discounter(double amount, Customer customer) {
        this.amount = amount;
        this.customer = customer;
    }
    public Discounter() {

    }

}
