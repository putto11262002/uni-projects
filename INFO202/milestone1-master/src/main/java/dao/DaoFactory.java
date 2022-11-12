/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author putsuthisrisinlpa
 */
public class DaoFactory {
    
    public static ProductDAO getProductDAO(){
//        return new ProductCollectionsDAO();
        return JdbiDaoFactory.getProductDAO();
    }
    
    public static CustomerDAO getCustomerDAO(){
//        return new CustomerCollectionsDAO();
        return JdbiDaoFactory.getCustomerDAO();
    }
    
}
