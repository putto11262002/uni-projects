/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import domain.Customer;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 *
 * @author putsuthisrisinlpa
 */
public interface CustomerJdbiDAO extends CustomerDAO {
    
    @Override
    @SqlQuery("SELECT EXISTS (SELECT * FROM CUSTOMER WHERE USERNAME = :username AND password = :password)")
    public Boolean validateCredentials(@Bind("username") String username, @Bind("password") String password);
    
    @Override
    @SqlUpdate("INSERT INTO CUSTOMER (USERNAME, PASSWORD, FIRST_NAME, SURNAME, EMAIL_ADDRESS, SHIPPING_ADDRESS) VALUES (:username, :password, :firstName, :surname, :emailAddress, :shippingAddress)")
    public void save(@BindBean Customer aCustomer);
    
    @Override
    @SqlUpdate("DELETE FROM CUSTOMER WHERE USERNAME = :username")
    public void delete(@BindBean Customer aCustomer);
    
    @Override
    @SqlQuery("SELECT * FROM CUSTOMER WHERE USERNAME = :username")
    @RegisterBeanMapper(Customer.class)
    public Customer getByUsername(@Bind("username") String username);
    
}
