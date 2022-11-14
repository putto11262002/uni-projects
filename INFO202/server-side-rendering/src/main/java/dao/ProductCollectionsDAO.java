package dao;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark George
 */
public class ProductCollectionsDAO implements ProductDAO {

    private static final Multimap<String, Product> categories = HashMultimap.create();
    private static final Map<String, Product> products = new HashMap<>();

//    @SuppressWarnings("OverridableMethodCallInConstructor")
//    public ProductCollectionsDAO() {
//        saveProduct(new Product("WD1234", "Slimy Widget", "A widget that is covered in some kind of nasty shmoo.", "Widgets", new BigDecimal("7.32"), new BigDecimal(35)));
//        saveProduct(new Product("WD1234", "Green Widget", "A widget that has gone mouldy.", "Widgets", new BigDecimal("21.43"), new BigDecimal(3)));
//        saveProduct(new Product("DH8832", "Dodgy Doohicky", "A doohicky that might work, or it might not...", "Doohickies", new BigDecimal("12.32"), new BigDecimal(5)));
//        saveProduct(new Product("DH8832", "Polkadot Doohicky", "A doohicky that is covered in spots.", "Doohickies", new BigDecimal("43.23"), new BigDecimal(6)));
//    }

    @Override
    public void saveProduct(Product product) {
        products.put(product.getProductId(), product);
        categories.put(product.getCategory(), product);
    }

    @Override
    public void removeProduct(Product product) {
        products.remove(product.getProductId());
        categories.remove(product.getCategory(), product);
    }

    @Override
    public Collection<Product> getProducts() {
        return products.values();
    }

    @Override
    public Collection<String> getCategories() {
        return categories.keySet();
    }

    @Override
    public Product searchById(String id) {
        return products.get(id);
    }

    @Override
    public Collection<Product> filterByCategory(String category) {
        return categories.get(category);
    }

}
