package com.entities.dao;

import javax.transaction.Transactional;

import com.entities.Customer;
import com.entities.Discounter;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class CustomerDao {
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(Customer customer) {
        getSession().save(customer);
    }

    public void update(Customer customer) {
        getSession().update(customer);
    }

    @SuppressWarnings("unchecked")
    public Customer getCustomer(int id) {
        String hql = "from Customer where customerId like :id";
        Session session = getSession();
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        List results = query.list();
        return (Customer) results.get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Customer> getCustomerByName(String lastName, String firstName){
        String hql = "from Customer where lastName like :lastName and firstName like :firstName";
        Session session = getSession();
        Query query = session.createQuery(hql);
        query.setString("firstName", firstName);
        query.setString("lastName", lastName);

        return (List<Customer>) query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Customer> getAllCustomerObjects() {
        String hql = "from Customer";
        Session session = getSession();
        Query query = session.createQuery(hql);

        return (List<Customer>) query.list();
    }

    @SuppressWarnings("unchecked")
    public String getCustomers(){
        Session session = getSession();
        Criteria crit = session.createCriteria(Customer.class);
        List customers = crit.list();
        String customersList = "";

        for(Object u : customers){
            customersList += "FirstName: "+((Customer) u).getFirstName()+"   LastName: "+((Customer) u).getLastName()+"   BirthDate: "+((Customer) u).getBirthDate()+"   Address: "+((Customer) u).getAdress()+" <br>";
        }
        return customersList;
    }

    @SuppressWarnings("unchecked")
    public String getPeerGroup(Customer customer){
        Session session = getSession();
        Criteria crit = session.createCriteria(Customer.class);
        List customers = crit.list();
        String peerGroupList = "";

        for(Object u : customers){
            if(((Customer) u).getBirthDate().getYear() -5 < ((Customer) customer).getBirthDate().getYear() && customer.getBirthDate().getYear() + 5 > ((Customer) customer).getBirthDate().getYear() ){
                peerGroupList += "FirstName: "+((Customer) u).getFirstName()+"   LastName: "+((Customer) u).getLastName()+"   BirthDate: "+((Customer) u).getBirthDate()+"   Address: "+((Customer) u).getAdress()+" <br>";
            }
        }

        return peerGroupList;
    }

}
