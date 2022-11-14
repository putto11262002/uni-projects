package web;

import dao.ProductDAO;
import dao.SaleDAO;
import domain.Product;
import domain.Sale;
import domain.SaleItem;
import io.jooby.Jooby;
import io.jooby.StatusCode;

public class SaleModule extends Jooby {

    private SaleDAO saleDao;
    private ProductDAO productDao;

    public SaleModule(SaleDAO saleDao, ProductDAO productDao){
        this.saleDao= saleDao;
        this.productDao = productDao;

        post("/api/sales", ctx -> {
            Sale sale = ctx.body().to(Sale.class);
            // get sale price from db
            for (SaleItem item: sale.getItems()){
                Product product = productDao.searchById(item.getProduct().getProductId());
                item.setSalePrice(product.getListPrice());
            }
            saleDao.save(sale);
            return ctx.send(StatusCode.CREATED);
        });
    }
}
