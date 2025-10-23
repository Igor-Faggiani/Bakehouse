package br.bakeryudesc.controller;

import br.bakeryudesc.dao.CategoryDAO;
import br.bakeryudesc.dao.ProductDAO;
import br.bakeryudesc.model.Category;
import br.bakeryudesc.model.Product;
import br.bakeryudesc.utils.PersistenceInstance;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProductController {

    private final EntityManager entityManager;
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    public ProductController() {
        this.entityManager = PersistenceInstance.getEntityManager();
        this.productDAO = new ProductDAO(entityManager);
        this.categoryDAO = new CategoryDAO(entityManager);
    }

    public void createProduct(String name, BigDecimal price, boolean redeemable, int redeemablePoints, int stockQuantity, Long categoryId) {
        Category category = categoryDAO.findById(categoryId);

        if (category != null) {
            Product newProduct = new Product();
            newProduct.setName(name);
            newProduct.setPrice(price);
            newProduct.setRedeemable(redeemable);
            newProduct.setPointsCost(redeemablePoints);
            newProduct.setStockQuantity(stockQuantity);
            newProduct.setCategory(category);

            productDAO.save(newProduct);
        } else {
            System.err.println("Error: Category ID " + categoryId + " not found.");
        }
    }

    public void updateProduct(Product product) {
        if (product != null) {
            productDAO.update(product);
        }
    }

    public void deleteProduct(Product productId) {
        productDAO.softDelete(productId);
    }

    public Product findProductById(Long productId) {
        return productDAO.findById(productId);
    }

    public List<Product> findAllProducts() {
        return productDAO.findAll();
    }

    public List<Product> findAllProductsInStock() {
        return productDAO.findAll().stream().filter(product -> product.getStockQuantity() > 0).toList();
    }

    public void close() {
        if (this.entityManager != null && this.entityManager.isOpen()) {
            this.entityManager.close();
        }
    }
}
