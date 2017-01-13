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
            result = _customerDao.getCustomers();
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return result;

    }

    // Primary impl. for Task "Get data of peergroup from database
    @RequestMapping(value = "/getPeerGroup")
    @ResponseBody
    public String getPeerGroup(int customerId) {

        String result = "";

        try {
            Customer customer = _customerDao.getCustomer(customerId);
            result = _customerDao.getPeerGroup(customer);
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


    /* Diverging Services */

    @RequestMapping( value = "/getCustomerName")
    @ResponseBody
    public String getCustomerName(int customerId){

        Customer customer = _customerDao.getCustomer(customerId);
        return "CustomerName: "+customer.getFirstName()+" "+customer.getLastName();
    }

    @RequestMapping( value = "/getCustomerBirthdate")
    @ResponseBody
    public String getCustomerBirthdate(int customerId){

        Customer customer = _customerDao.getCustomer(customerId);
        return "Customer Birthdate: "+customer.getBirthDate();
    }

    @RequestMapping( value = "/getCustomerAddress")
    @ResponseBody
    public String getCustomerAddress(int customerId){

        Customer customer = _customerDao.getCustomer(customerId);
        return "Customer Address: "+customer.getAdress();
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
            return "Business Volume of Customer : " + customerName + ", (id: " + customer.getCustomerId() + ")  is " + String.valueOf(businessVolume) + " Euro.";
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
                customerSalesString += "Id: " + customer.getCustomerId() + " <br> " + customer.getLastName() + " , " + customer.getFirstName() + ". Sales: " + customer.getSales() + " <br><br> ";

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
                customersString += "Id: " + customer.getCustomerId() + " <br> " + "Firstname = " + customer.getFirstName() + "<br> Lastname = " + customer.getLastName() + "<br> Birthdate = " + customer.getBirthDate() + "<br>   Address = " + customer.getAdress() + " <br><br> ";
            }
            return customersString;

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    // 2nd impl. for Task "Get data of all customers from database
    @RequestMapping(value = "/getEveryone")
    @ResponseBody
    public String getEveryone() {

        String result = "";

        try {
            List<Customer> customerList = _customerDao.getAllCustomerObjects();
            for (Customer customer : customerList) {
                result += "ID: " + customer.getCustomerId() + ", Name: " + customer.getFirstName() + " " + customer.getLastName() + " , Birthdate: " + customer.getBirthDate() + " , Address:" + customer.getAdress() + " <br><br>";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return result;

    }

    // 3rd impl. for Task "Get data of all customers from database
    @RequestMapping(value = "/getTableOfAllCustomers")
    @ResponseBody
    public String getDataOfAllCustomers() {

        String result = "<table border=\"1\"> <tr> <th>ID</th> <th>Firstname</th> <th>Lastname</th> <th>Birthdate</th> <th>Address</th> <tr>";

        try {
            List<Customer> customerList = _customerDao.getAllCustomerObjects();
            for (Customer customer : customerList) {
                result += "<tr>  <td> " + customer.getCustomerId() + "</td> <td>" + customer.getFirstName() + "</td> <td>" + customer.getLastName() + "</td> <td>" + customer.getBirthDate() + "</td> <td>" + customer.getAdress() + "</td> </tr>";
            }
            result += "</table>";
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return result;

    }

    // 2nd impl. for Task "Get data of peergroup from database"
    @RequestMapping(value = "/PeerGroup")
    @ResponseBody
    public String getPeerGroupData(int customerId) {

        String result = "";
        Customer targetCustomer = _customerDao.getCustomer(customerId);

        try {
            List<Customer> customerList = _customerDao.getAllCustomerObjects();
            for (Customer customer : customerList) {
                if (customer.getBirthDate().getYear() - 5 < targetCustomer.getBirthDate().getYear() && targetCustomer.getBirthDate().getYear() + 5 > customer.getBirthDate().getYear()) {
                    result += "ID: " + customer.getCustomerId() + ", Name: " + customer.getFirstName() + " " + customer.getLastName() + " , Birthdate: " + customer.getBirthDate() + " , Address:" + customer.getAdress() + " <br><br>";
                }
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return result;

    }

    // 3rd impl. for Task "Get data of peergroup from database"
    @RequestMapping(value = "/PeerGroupTable")
    @ResponseBody
    public String getPeerGroupTable(int customerId) {

        String result = "<table border=\"1\"> <tr> <th>ID</th> <th>Firstname</th> <th>Lastname</th> <th>Birthdate</th> <th>Address</th> <tr>";

        Customer targetCustomer = _customerDao.getCustomer(customerId);

        try {
            List<Customer> customerList = _customerDao.getAllCustomerObjects();
            for (Customer customer : customerList) {
                if (customer.getBirthDate().getYear() - 5 < targetCustomer.getBirthDate().getYear() && targetCustomer.getBirthDate().getYear() + 5 > customer.getBirthDate().getYear()) {
                    result += "<tr>  <td> " + customer.getCustomerId() + "</td> <td>" + customer.getFirstName() + "</td> <td>" + customer.getLastName() + "</td> <td>" + customer.getBirthDate() + "</td> <td>" + customer.getAdress() + "</td> </tr>";
                }
            }
            result += "</table>";
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return result;
    }

    //2nd impl. for Task "Calculate aggregated sales of all customers"
    @RequestMapping(value = "/allCustomersSalestotal")
    @ResponseBody
    public String getAllCustomersSalestotal() {

        String result = "";
        double salesTotal = 0;

        try {
            List<Customer> customerList = _customerDao.getAllCustomerObjects();
            for (Customer customer : customerList) {
                result += "<tr>  <td> " + customer.getCustomerId() + "</td> <td>" + customer.getFirstName() + " " + customer.getLastName() + "</td> <td>" + customer.getSales() + "</td> </tr>";
                salesTotal += customer.getSales();
            }
            result += "</table> <br><br> Sales: " + salesTotal;
        } catch (
                Exception ex)

        {
            return ex.getMessage();
        }
        return result;

    }

    //3rd impl. for Task "Calculate aggregated sales of all customers"
    @RequestMapping(value = "/allSales")
    @ResponseBody
    public String getAllSalesAsTable() {

        double salesTotal = 0;
        String result = "";

        try {
            List<Customer> customerList = _customerDao.getAllCustomerObjects();
            for (Customer customer : customerList) {
                result += "ID: " + customer.getCustomerId() + "- Sales: " + customer.getSales() + " <br><br>";
                salesTotal += customer.getSales();
            }
            result += "<br> Sales in Total: " + salesTotal;
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return result;

    }

    //2nd impl. for Task "Calculate aggregated sales of peergroup"
    @RequestMapping(value = "/PeerGroupSalestotal")
    @ResponseBody
    public String getPeerGroupSalestotal(int customerId) {

        Customer targetCustomer = _customerDao.getCustomer(customerId);
        String result = "";
        double salesTotal = 0;

        try {
            List<Customer> customerList = _customerDao.getAllCustomerObjects();
            for (Customer customer : customerList) {
                if (customer.getBirthDate().getYear() - 5 < targetCustomer.getBirthDate().getYear() && targetCustomer.getBirthDate().getYear() + 5 > customer.getBirthDate().getYear()) {
                    result += "ID: " + customer.getCustomerId() + ", Name: " + customer.getFirstName() + " " + customer.getLastName() + ", Sales: " + customer.getSales() + " <br><br>";
                }
                salesTotal += customer.getSales();
            }
            result += "<br> Sales in Total: " + salesTotal;
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return result;

    }

    //3rd impl. for Task "Calculate aggregated sales of peergroup"
    @RequestMapping(value = "/PeerSales")
    @ResponseBody
    public String getPeerSales(int customerId) {

        Customer targetCustomer = _customerDao.getCustomer(customerId);
        String result = "";
        double salesTotal = 0;

        try {
            List<Customer> customerList = _customerDao.getAllCustomerObjects();
            for (Customer customer : customerList) {
                if (customer.getBirthDate().getYear() - 5 < targetCustomer.getBirthDate().getYear() && targetCustomer.getBirthDate().getYear() + 5 > customer.getBirthDate().getYear()) {
                    result += "ID: " + customer.getCustomerId() + "- Sales: " + customer.getSales() + " <br><br>";
                }
                salesTotal += customer.getSales();
            }
            result += "<br> Total Peer Sales: " + salesTotal;
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return result;

    }




    // Add DummyData Service
    @RequestMapping(value = "/dummyData")
    @ResponseBody
    public String addDummyData() {

        String[] lastNames = {"Mustermann", "Musterfrau", "Njord", "Normalverbraucher", "Njord", "Jenkins"};
        String[] firstNames = {"Max", "Marlene", "Freya", "Otto", "Thor", "Leroy"};
        String[] adresses = {"Musterstraße", "Musterstraße", "Asgard", "Normalstraße", "Asgard", "Ironforge"};
        Date[] birthDates = {new Date(1990 - 10 - 11), new Date(1990 - 10 - 11), new Date(2000 - 12 - 12), new Date(1985 - 10 - 12), new Date(2002 - 11 - 14), new Date(1980 - 14 - 16)};
        double[] sales = {123.3, 200, 400, 40.50, 93.8, 310};

        for (int i = 0; i < 6; i++) {
            Customer customer = new Customer(lastNames[i], firstNames[i], birthDates[i], adresses[i]);
            Discounter discounter = new Discounter(0, customer);
            customer.setSales(sales[i]);
            _customerDao.save(customer);
            _discounterDao.save(discounter);
        }

        return "DummyData successfully added.";
    }

}
