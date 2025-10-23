package br.bakeryudesc.dao;

import br.bakeryudesc.model.PointRedemption;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PointRedemptionDAO extends AbstractDAO<PointRedemption, Long> {
    public PointRedemptionDAO(EntityManager entityManager) {
        super(entityManager, PointRedemption.class);
    }

    public List<PointRedemption> findByCustomerId(Long customerId) {
        TypedQuery<PointRedemption> query = entityManager.createQuery("SELECT pr FROM PointRedemption pr WHERE pr.customer.id = :customerId ORDER BY pr.redemptionDate DESC", PointRedemption.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

}
