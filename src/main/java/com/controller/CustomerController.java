package com.controller;

import com.entities.Customer;
import com.entities.Discounter;
import com.entities.dao.CustomerDao;
import com.entities.dao.DiscounterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {
    @Autowired
    private CustomerDao _customerDao;
    @Autowired
    private DiscounterDao _discounterDao;

    @RequestMapping(value = "/add")
    @ResponseBody
    public String create(String lastName, String firstName, Date birthDate, String adress) {
        try {
            Customer customer = new Customer(lastName, firstName, birthDate, adress);
            Discounter discounter = new Discounter(0,customer);
            _customerDao.save(customer);
            _discounterDao.save(discounter);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Customer succesfully added!";
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public String getCustomer(int customerId){

        Customer customer = _customerDao.getCustomer(customerId);
        try {

            return "Firstname: "+customer.getFirstName()+" Lastname: "+customer.getLastName()+" Birthdate: "+customer.getBirthDate()+" Adress: "+customer.getAdress();

        }catch (Exception ex) {
            return ex.getMessage();
        }

    }

    @RequestMapping(value = "/sales")
    @ResponseBody
    public String getSales(int customerId){

        Customer customer = _customerDao.getCustomer(customerId);
        try {
            double sales = customer.getSales();
            return "Customer sales: "+ String.valueOf(sales);
        }catch (Exception ex){
            return ex.getMessage();
        }
    }

}
