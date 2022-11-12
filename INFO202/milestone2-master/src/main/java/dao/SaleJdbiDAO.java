package dao;

import domain.Sale;
import domain.SaleItem;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.time.LocalDateTime;

public interface SaleJdbiDAO extends SaleDAO {

    @SqlUpdate(" INSERT INTO SALE (DATE, CUSTOMER_ID, STATUS) VALUES (:date, :customer.customerId, :status)")
    @GetGeneratedKeys
    Integer insertSale(@BindBean Sale sale);

    @SqlUpdate(" INSERT INTO SALE_ITEM (QUANTITY_PURCHASED, SALE_PRICE, PRODUCT_ID, SALE_ID) VALUES (:quantityPurchased, :salePrice, :product.productId, :saleId)")
    void insertSaleItem(@BindBean SaleItem item, @Bind("saleId") Integer saleId);

    @SqlUpdate(" UPDATE PRODUCT SET QUANTITY_IN_STOCK = QUANTITY_IN_STOCK - :quantityPurchased WHERE PRODUCT_ID = :product.productId")
    void updateStockLevel(@BindBean SaleItem item);

    @Override
    @Transaction
    default void save(Sale sale) {
        // save current date
        sale.setDate(LocalDateTime.now());

        // set sale status
        sale.setStatus("NEW ORDER");

        // call the insertSale method, and get the generated sale ID.
        Integer saleId = insertSale(sale);
        sale.setSaleId(saleId);

        // loop through the sale's items.
        for (SaleItem item : sale.getItems()) {
            insertSaleItem(item, saleId);
            updateStockLevel(item);
        }

    }
}