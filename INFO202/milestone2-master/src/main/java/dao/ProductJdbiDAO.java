/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import domain.Customer;
import domain.Product;
import java.util.Collection;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 *
 * @author putsuthisrisinlpa
 */
public interface ProductJdbiDAO extends ProductDAO {
    
    @Override
    @SqlQuery("SELECT * FROM PRODUCT WHERE CATEGORY = :category")
    @RegisterBeanMapper(Product.class)
    public Collection<Product> filterByCategory(@Bind("category") String category);
    
    @Override
    @SqlQuery("SELECT DISTINCT CATEGORY FROM PRODUCT")
    public Collection<String> getCategories();
    
    @Override
    @SqlQuery("SELECT * FROM PRODUCT")
    @RegisterBeanMapper(Product.class)
    public Collection<Product> getProducts();
    
    @Override
    @SqlUpdate("DELETE FROM PRODUCT WHERE PRODUCT_ID  = :productId")
    public void removeProduct(@BindBean Product aProduct);
    
    @Override
    @SqlUpdate("MERGE INTO PRODUCT (PRODUCT_ID, NAME, DESCRIPTION, CATEGORY, LIST_PRICE, QUANTITY_IN_STOCK) VALUES (:productId, :name, :description, :category, :listPrice, :quantityInStock)")
    public void saveProduct(@BindBean Product aProduct);
    
    @Override
    @SqlQuery("SELECT * FROM PRODUCT WHERE PRODUCT_ID = :id")
    @RegisterBeanMapper(Product.class)
    public Product searchById(@Bind("id") String id);
    
}
