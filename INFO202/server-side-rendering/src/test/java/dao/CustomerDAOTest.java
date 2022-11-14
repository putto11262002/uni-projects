/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dao;

import domain.Customer;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author putsuthisrisinlpa
 */
public class CustomerDAOTest {
    
    private CustomerDAO dao;
    
    private Customer c1;
    private Customer c2;
    
    @BeforeAll
    public static void initialise() {

        try{
            JdbiDaoFactory.setJdbcUri("jdbc:h2:mem:tests;INIT=runscript from 'schema.sql'");
        }catch (IllegalStateException e){
            System.out.println("Test Uri has already been set");
        }
    }
    
    @BeforeEach
    public void setUp() {
       dao = DaoFactory.getCustomerDAO();
          
       c1 = new Customer("c1_username", "c1_password", "c1_firstName", "c1_lastName", "c1_address", "c1_email");
       c2 = new Customer("c2_username", "c2_password", "c2_firstName", "c2_lastName", "c2_address", "c2_email");
       
       dao.save(c1);
    }
    
    @AfterEach
    public void tearDown() {
        dao.delete(c1);
        dao.delete(c2);
    }
    
    @Test
    public void testSave() {
        // make sure that c2 does not yet exist
        assertThat(dao.getByUsername(c2.getUsername()), is(nullValue()));
        
        // save c2
        dao.save(c2);
        
        // make sure that c2 now exists
        assertThat(dao.getByUsername(c2.getUsername()), is(c2));
    }

    @Test
    public void testAuthenticate() {
        // use the wrong password to authenticate
        // sign in with c1's username with c2's password
        assertThat(dao.authenticate(c1.getUsername(), c2.getPassword()), is(false));
        
        // sign in with customer that does not exist
        // authenticate must return false
        assertThat(dao.authenticate(c2.getUsername(), c2.getPassword()), is(false));
        
        // sign in with correct username and password
        assertThat(dao.authenticate(c1.getUsername(), c1.getPassword()), is(true));
        
    }

    @Test
    public void testGetByUsername() {
        Customer result = dao.getByUsername(c1.getUsername());
        
        assertThat(result, is(c1));
    }

    @Test
    public void testDelete() {
        // verity that c1 exist
        assertThat(dao.getByUsername(c1.getUsername()), is(c1));
        
        dao.delete(c1);
        
        // verify that c1 no longer exist
        assertThat(dao.getByUsername(c1.getUsername()), is(nullValue()));
    }
   
}
