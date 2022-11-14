/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import domain.Customer;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author putsuthisrisinlpa
 */
public class CustomerCollectionsDAO implements CustomerDAO {
    
    private static final Map<String, Customer> customers = new HashMap<>();
    
    @Override
    public void save(Customer aCustomer){
        customers.put(aCustomer.getUsername(), aCustomer);
    }
    
    @Override 
    public boolean authenticate(String username, String password){
        Customer result = customers.get(username);
        if(result == null) return false;
        return result.getPassword().equals(password);
    }
    
    @Override
    public Customer getByUsername(String username){
        return customers.get(username);
    }
    
    @Override
    public void delete(Customer aCustomer){
        customers.remove(aCustomer.getUsername());
    }
    
}
