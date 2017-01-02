package com.entities.dao;

import javax.transaction.Transactional;

import com.entities.Customer;
import com.entities.Discounter;
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
    public Customer getCustomer(int id){
        String hql = "from Customer where customerId like :id";
        Session session= getSession();
        Query query = session.createQuery(hql);
        query.setInteger("id",id);
        List results = query.list();
        return (Customer)results.get(0);
    }

}
