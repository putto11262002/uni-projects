/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author putsuthisrisinlpa
 */
public class ProductDAOTest {

    private ProductDAO dao;

    private Product p1;
    private Product p2;
    private Product p3;
    
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
        dao = DaoFactory.getProductDAO();

        p1 = new Product("p1_id", "p1_name", "p1_description", "category_1", new BigDecimal("10.00"), new BigDecimal(20));
        p2 = new Product("p2_id", "p2_name", "p2_description", "category_2", new BigDecimal("12.50"), new BigDecimal(50));
        p3 = new Product("p3_id", "p3_name", "p3_description", "category_3", new BigDecimal("20.30"), new BigDecimal(15));

        dao.saveProduct(p1);
        dao.saveProduct(p2);
    }

    @AfterEach
    public void tearDown() {
        dao.removeProduct(p1);
        dao.removeProduct(p2);
        dao.removeProduct(p3);
    }

    @Test
    public void testFilterByCategory() {
        assertThat(dao.filterByCategory("category_1"), contains(p1));
        assertThat(dao.filterByCategory("category_2"), contains(p2));
        assertThat(dao.filterByCategory("category_3"), hasSize(0));

        // make sure that we haven't swapped/lost any fields
        Product result = dao.filterByCategory("category_1").stream().filter(p -> p.getProductId().equals(p1.getProductId())).findFirst().get();
        assertThat(result, samePropertyValuesAs(p1));
    }

    @Test
    public void testGetCategories() {
        assertThat(dao.getCategories(), containsInAnyOrder(p1.getCategory(), p2.getCategory()));
    }

    @Test
    public void testGetProducts() {
        Collection<Product> products = dao.getProducts();
        assertThat(products, hasSize(2));
        assertThat(products, hasItem(p1));
        assertThat(products, hasItem(p2));

        // make sure that we haven't swapped/lost any fields
        Product result = products.stream()
                .filter(p -> p.getProductId().equals(p1.getProductId())).findFirst().get();
        assertThat(result, samePropertyValuesAs(p1));
    }

    @Test
    public void testRemoveProduct() {
        // make sure that p1 already exists
        assertThat(dao.searchById(p1.getProductId()), is(p1));
        assertThat(dao.getProducts(), hasSize(2));

        // delete p1
        dao.removeProduct(p1);

        // make sure that p1 no longer exists
        assertThat(dao.searchById(p1.getProductId()), is(nullValue()));
        assertThat(dao.getProducts(), hasSize(1));
    }

    @Test
    public void testSaveProduct() {
        // make sure that p3 does not yet exist
        assertThat(dao.searchById(p3.getProductId()), is(nullValue()));
        assertThat(dao.getProducts(), hasSize(2));
        // save s3
        dao.saveProduct(p3);

        // make sure that p3 now exists
        assertThat(dao.searchById(p3.getProductId()), is(p3));
        assertThat(dao.getProducts(), hasSize(3));
    }

    @Test
    public void testSearchById() {
        Product result = dao.searchById(p1.getProductId());
        assertThat(result, is(p1));
        // make sure that we haven't swapped/lost any fields
        assertThat(result, samePropertyValuesAs(p1));
    }

}
