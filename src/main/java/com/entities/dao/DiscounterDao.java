package com.entities.dao;

import com.entities.Customer;
import com.entities.Discounter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DiscounterDao {
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(Discounter discounter) {
        getSession().save(discounter);
    }

    public void update(Discounter discounter) {
        getSession().update(discounter);
    }


    public boolean checkDiscount(Customer customer) {
        Session session = getSession();
        Criteria crit = session.createCriteria(Customer.class);
        List costumers = crit.list();
        double allSales = 0;
        for (Object u : costumers) {
            allSales = allSales + ((Customer) u).getSales();
        }
        if (customer.getSales() > (allSales * 0.10)) {
            return true;
        } else {
            System.out.print(customer.getSales());

            double allSalesForPeer = 0;
            for (Object costumersToWork : costumers) {

                if (customer.getBirthDate().getYear() - 5 < ((Customer) costumersToWork).getBirthDate().getYear()
                        && customer.getBirthDate().getYear() + 5 > ((Customer) costumersToWork).getBirthDate().getYear()) {
                    allSalesForPeer = allSalesForPeer + ((Customer) costumersToWork).getSales();
                }
            }
            if (customer.getSales() > (allSalesForPeer * 0.20)) {
                return true;
            }
            return false;
        }
    }

    public String getCustomers(){
        Session session = getSession();
        Criteria crit = session.createCriteria(Customer.class);
        List customers = crit.list();
        String customersList = "";

        for(Object u : customers){
            customersList += "FirstName: "+((Customer) u).getFirstName()+" LastName: "+((Customer) u).getLastName()+" BirthDate: "+((Customer) u).getBirthDate()+" Address: "+((Customer) u).getAdress()+" <br>";
        }
        return customersList;
    }

    public String getPeerGroup(Customer customer){
        Session session = getSession();
        Criteria crit = session.createCriteria(Customer.class);
        List customers = crit.list();
        String peerGroupList = "";

        for(Object u : customers){
            if(((Customer) u).getBirthDate().getYear() -5 < ((Customer) customer).getBirthDate().getYear() && customer.getBirthDate().getYear() + 5 > ((Customer) customer).getBirthDate().getYear() ){
                peerGroupList += "FirstName: "+((Customer) u).getFirstName()+" LastName: "+((Customer) u).getLastName()+" BirthDate: "+((Customer) u).getBirthDate()+" Address: "+((Customer) u).getAdress()+" <br>";
            }
        }

        return peerGroupList;
    }

    public double aggregatedSales(){
        Session session = getSession();
        Criteria crit = session.createCriteria(Customer.class);
        List customers = crit.list();
        double sales = 0;

        for(Object u : customers){
            sales += ((Customer) u).getSales();
        }
        return sales;
    }

    public double peerGroupSales(Customer customer){
        Session session = getSession();
        Criteria crit = session.createCriteria(Customer.class);
        List customers = crit.list();
        double sales = 0;

        for(Object u : customers){
            if(((Customer) u).getBirthDate().getYear() -5 < ((Customer) customer).getBirthDate().getYear() && customer.getBirthDate().getYear() + 5 > ((Customer) customer).getBirthDate().getYear() ){
                sales += ((Customer) u).getSales();
            }
        }
        return sales;
    }

}
