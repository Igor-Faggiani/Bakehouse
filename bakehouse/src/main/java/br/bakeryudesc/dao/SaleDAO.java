package br.bakeryudesc.dao;

import br.bakeryudesc.model.Sale;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

public class SaleDAO extends AbstractDAO<Sale, Long> {

    public SaleDAO(EntityManager entityManager) {
        super(entityManager, Sale.class);
    }

    public List<Sale> findByCustomerId(Long customerId) {
        TypedQuery<Sale> query = entityManager.createQuery("SELECT s FROM Sale s WHERE s.customer.id = :customerId ORDER BY s.saleDate DESC", Sale.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    public List<Sale> findByDateRange(LocalDateTime start, LocalDateTime end) {
        TypedQuery<Sale> query = entityManager.createQuery("SELECT s FROM Sale s WHERE s.saleDate BETWEEN :start AND :end ORDER BY s.saleDate DESC", Sale.class);
        query.setParameter("start", start);
        query.setParameter("end", end);
        return query.getResultList();
    }

}
