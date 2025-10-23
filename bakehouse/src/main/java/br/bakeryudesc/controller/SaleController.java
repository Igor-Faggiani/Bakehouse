package br.bakeryudesc.controller;

import br.bakeryudesc.dao.CustomerDAO;
import br.bakeryudesc.dao.ProductDAO;
import br.bakeryudesc.dao.SaleDAO;
import br.bakeryudesc.model.Customer;
import br.bakeryudesc.model.Product;
import br.bakeryudesc.model.Sale;
import br.bakeryudesc.model.SaleItem;
import br.bakeryudesc.utils.PersistenceInstance;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleController {

        private final EntityManager entityManager;
        private final SaleDAO saleDAO;
        private final ProductDAO productDAO;
        private final CustomerDAO customerDAO;

        public SaleController() {
            this.entityManager = PersistenceInstance.getEntityManager();
            this.saleDAO = new SaleDAO(entityManager);
            this.productDAO = new ProductDAO(entityManager);
            this.customerDAO = new CustomerDAO(entityManager);
        }

    public void createSale(Long customerId, List<Product> itemsToPurchase) {
        EntityTransaction transaction = null;
        BigDecimal totalAmount = BigDecimal.ZERO;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Customer customer = customerDAO.findById(customerId);
            if (customer == null) {
                throw new RuntimeException("Customer id " + customerId + " not found.");
            }

            Map<Product, Integer> aggregatedItems = new HashMap<>();
            for (Product product : itemsToPurchase) {
                aggregatedItems.put(product, aggregatedItems.getOrDefault(product, 0) + 1);
            }

            Sale newSale = new Sale();
            newSale.setCustomer(customer);

            for (Map.Entry<Product, Integer> entry : aggregatedItems.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();

                if (quantity <= 0) {
                    continue;
                }

                Product managedProduct = entityManager.merge(product);

                if (managedProduct.getStockQuantity() < quantity) {
                    throw new RuntimeException("Insufficient stock for the product: " + managedProduct.getName()
                            + ". Available: " + managedProduct.getStockQuantity());
                }

                managedProduct.setStockQuantity(managedProduct.getStockQuantity() - quantity);

                SaleItem saleItem = new SaleItem();
                saleItem.setProduct(managedProduct);
                saleItem.setQuantity(quantity);
                saleItem.setUnitPrice(managedProduct.getPrice());

                newSale.addItem(saleItem);

                totalAmount = totalAmount.add(managedProduct.getPrice().multiply(BigDecimal.valueOf(quantity)));
            }

            if (totalAmount.compareTo(BigDecimal.ZERO) == 0) {
                throw new RuntimeException("The sale cannot have a total value of zero.");
            }

            int pointsEarned = totalAmount.divide(new BigDecimal("10"), 0, RoundingMode.FLOOR).intValue();
            customer.setTotalPoints(customer.getTotalPoints() + pointsEarned);

            newSale.setTotalAmount(totalAmount);
            newSale.setPointsEarned(pointsEarned);
            newSale.setSaleDate(LocalDateTime.now());

            entityManager.persist(newSale);

            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failure while processing the sell: " + e.getMessage(), e);
        }
    }

        public Sale findSaleById(Long saleId) {
            return saleDAO.findById(saleId);
        }

        public List<Sale> findAllSales() {
            return saleDAO.findAll();
        }

        public List<Sale> findSalesByCustomerId(Long customerId) {
            return saleDAO.findByCustomerId(customerId);
        }

        public void close() {
            if (this.entityManager != null && this.entityManager.isOpen()) {
                this.entityManager.close();
            }
        }

}
