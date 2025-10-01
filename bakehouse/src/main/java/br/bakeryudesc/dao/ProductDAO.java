package br.bakeryudesc.dao;

import br.bakeryudesc.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductDAO extends AbstractDAO<Product,Long> {

    public ProductDAO(EntityManager entityManager) {
        super(entityManager, Product.class);
    }

    public List<Product> findByName(String name) {
        String jpql = "SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(:name)";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

}
