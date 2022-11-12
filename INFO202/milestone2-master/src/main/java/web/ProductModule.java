/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web;

import dao.ProductDAO;
import domain.Product;
import io.jooby.Jooby;
import io.jooby.StatusCode;
import java.util.Collection;

/**
 *
 * @author sutpu703
 */
public class ProductModule extends Jooby {
	ProductDAO dao; 
	
	public ProductModule(ProductDAO dao){
		this.dao = dao;
		
		get("/api/products", ctx -> dao.getProducts());
		
		get("/api/products/{id}", ctx -> {
			String productId = ctx.path("id").value();
			Product product = dao.searchById(productId);
			if (product == null){
				return ctx.send(StatusCode.NOT_FOUND);
			}
			return product;
		});
		
		get("/api/categories", ctx -> dao.getCategories());
		
		get("/api/categories/{category}", ctx -> {
			String category = ctx.path("category").value();
			Collection<Product> products = dao.filterByCategory(category);
			return products;
		});
	}
	
}
