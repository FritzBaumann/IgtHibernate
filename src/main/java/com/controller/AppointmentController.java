package com.controller;

import com.entities.Appointment;
import com.entities.Customer;
import com.entities.dao.AppointmentDao;
import com.entities.dao.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/appointment", method = RequestMethod.GET)
public class AppointmentController {
    @Autowired
    private AppointmentDao _appointmentDao;
    @Autowired
    private CustomerDao _customerDao;

    @RequestMapping(value = "/add")
    @ResponseBody
    public String create(int customerId, double value) {
        try {
            Customer customer = _customerDao.getCustomer(customerId);
            customer.setSales(customer.getSales()+value);
            Appointment appointment = new Appointment(customer, value);
            _customerDao.update(customer);
            _appointmentDao.save(appointment);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Appointment succesfully saved!";
    }
}


