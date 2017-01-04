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
import java.util.List;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {
    @Autowired
    private CustomerDao _customerDao;
    @Autowired
    private DiscounterDao _discounterDao;

    // Add Customer to Database Service
    @RequestMapping(value = "/add")
    @ResponseBody
    public String create(String lastName, String firstName, Date birthDate, String adress) {
        try {
            Customer customer = new Customer(lastName, firstName, birthDate, adress);
            Discounter discounter = new Discounter(0, customer);
            _customerDao.save(customer);
            _discounterDao.save(discounter);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Customer succesfully added!";
    }

    // Primary impl. for Task "Get customer data from database"
    @RequestMapping(value = "/get")
    @ResponseBody
    public String getCustomer(int customerId) {

        Customer customer = _customerDao.getCustomer(customerId);
        try {

            return "Firstname: " + customer.getFirstName() + "   Lastname: " + customer.getLastName() + "   Birthdate: " + customer.getBirthDate() + "   Adress: " + customer.getAdress();

        } catch (Exception ex) {
            return ex.getMessage();
        }

    }

    // Primary impl. for Task "Get data of all customers from database
    @RequestMapping(value = "/getAll")
    @ResponseBody
    public String getAllCustomers() {

        String result = "";

        try {
            result = _discounterDao.getCustomers();
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return result;

    }

    // Primary impl. for Task "Get data of all customers from database
    @RequestMapping(value = "/getPeerGroup")
    @ResponseBody
    public String getPeerGroup(int customerId) {

        String result = "";

        try {
            Customer customer = _customerDao.getCustomer(customerId);
            result = _discounterDao.getPeerGroup(customer);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return result;

    }

    //Primary impl. for Task "Calculate customer sales"
    @RequestMapping(value = "/sales")
    @ResponseBody
    public String getSales(int customerId) {

        Customer customer = _customerDao.getCustomer(customerId);
        try {
            double sales = customer.getSales();
            return "Customer sales: " + String.valueOf(sales);
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    //Primary impl. for Task "Calculate aggregated sales of all customers"
    @RequestMapping(value = "/aggregratedSales")
    @ResponseBody
    public String getAggregatedSales() {

        double sales = 0;
        try {
            double allSales = _discounterDao.aggregatedSales();
            return "Aggregated sales: " + String.valueOf(allSales);
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    //Primary impl. for Task "Calculate aggregated sales of peerGroup"
    @RequestMapping(value = "/aggregratedPeerGroupSales")
    @ResponseBody
    public String getAggregatedPeerGroupSales(int customerId) {

        double sales = 0;

        try {
            Customer customer = (Customer) _customerDao.getCustomer(customerId);
            double peerGroupSales = _discounterDao.peerGroupSales(customer);
            return "Aggregated PeerGroup sales: " + String.valueOf(peerGroupSales);
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }


    /* Secondary Service Implementations */


    // 2nd Impl. for Task "Calculate customer sales"
    @RequestMapping(value = "/businessVolume")
    @ResponseBody
    public String getBusinessVolume(int customerId) {

        Customer customer = _customerDao.getCustomer(customerId);
        try {
            double businessVolume = customer.getSales();
            String customerName = "";
            customerName += customer.getFirstName() + " " + customer.getLastName();
            return "Business Volume of Customer : " + customerName +", (id: "+ customer.getCustomerId()+")  is " + String.valueOf(businessVolume) + " Euro.";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    // 3rd Impl. for Task "Calculate customer sales"
    @RequestMapping(value = "/salesByName")
    @ResponseBody
    public String getCustomerTurnover(String lastName, String firstName) {

        List<Customer> customerList = _customerDao.getCustomerByName(lastName, firstName);
        String customerSalesString = "";
        try {

            for (Customer customer : customerList) {
                customerSalesString +="Id: "+customer.getCustomerId()+" <br> "+customer.getLastName() + " , " + customer.getFirstName() + ". Sales: " + customer.getSales() + " <br><br> ";

            }
            return customerSalesString;

        } catch (Exception ex) {
            return ex.getMessage();
        }

    }

    // 2nd impl. for Task "Get customer data from database"
    @RequestMapping(value = "/getData")
    @ResponseBody
    public String getCustomerData(int customerId) {

        Customer customer = _customerDao.getCustomer(customerId);
        try {

            return "Firstname = " + customer.getFirstName() + "<br> Lastname = " + customer.getLastName() + "<br> Birthdate = " + customer.getBirthDate() + "<br>   Address = " + customer.getAdress();

        } catch (Exception ex) {
            return ex.getMessage();
        }

    }

    // 3rd impl. for Task "Get customer data from database"
    @RequestMapping(value = "/getByName")
    @ResponseBody
    public String getCustomerByName(String lastName, String firstName) {

        List<Customer> customersList = _customerDao.getCustomerByName(lastName, firstName);
        String customersString = "";
        try {
            for (Customer customer : customersList) {
                customersString +="Id: "+customer.getCustomerId()+" <br> "+ "Firstname = " + customer.getFirstName() + "<br> Lastname = " + customer.getLastName() + "<br> Birthdate = " + customer.getBirthDate() + "<br>   Address = " + customer.getAdress() + " <br><br> ";
            }
            return customersString;

        } catch (Exception ex) {
            return ex.getMessage();
        }

    }


}
