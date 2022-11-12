/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import domain.Customer;

/**
 *
 * @author putsuthisrisinlpa
 */
public interface CustomerDAO extends CredentialsValidator {
    
    void save(Customer aCustomer);
    
    Customer getByUsername(String username);
    
    void delete(Customer aCustomer);
    
}
