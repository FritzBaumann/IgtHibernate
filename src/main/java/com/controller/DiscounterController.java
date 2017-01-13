package com.controller;

import com.entities.Customer;
import com.entities.dao.CustomerDao;
import com.entities.dao.DiscounterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/discount")
public class DiscounterController {
    @Autowired
    private CustomerDao _customerDao;
    @Autowired
    private DiscounterDao _discounterDao;

    @RequestMapping(value = "/calculate")
    @ResponseBody
    public String create(int customerId) {
        try {
            Customer customer = _customerDao.getCustomer(customerId);
            boolean discount = _discounterDao.checkDiscount(customer);

            if(discount){
                return "Customer qualifies for Discount.";
            }else{
                return "Customer does not qualify for Discount.";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    @RequestMapping(value = "/calculateInDetail")
    @ResponseBody
    public String calculateDetail(int customerId) {
        try {
            Customer customer = _customerDao.getCustomer(customerId);
            return _discounterDao.checkDiscountDetailed(customer);

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    @RequestMapping(value = "/grant")
    @ResponseBody
    public String grantDiscount(int customerId){
        // Placeholder Service

        try{
            Customer customer = _customerDao.getCustomer(customerId);
        }catch (Exception ex){
            return ex.getMessage();
        }

        return "Discount Granted.";
    }

    @RequestMapping(value = "/deny")
    @ResponseBody
    public String denyDiscount(int customerId){
        // Placeholder Service

        try{
            Customer customer = _customerDao.getCustomer(customerId);
        }catch (Exception ex){
            return ex.getMessage();
        }

        return "Discount Denied.";
    }

    @RequestMapping(value = "/calculateAverageSales")
    @ResponseBody
    public String calculateAverageSales(int customerId){
        try {
            Customer customer = _customerDao.getCustomer(customerId);
            return _discounterDao.averageSales(customer);

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}