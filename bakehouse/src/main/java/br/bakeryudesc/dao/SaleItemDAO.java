package br.bakeryudesc.dao;

import br.bakeryudesc.model.SaleItem;

import javax.persistence.EntityManager;

public class SaleItemDAO extends AbstractDAO<SaleItem, Long> {

    public SaleItemDAO(EntityManager entityManager) {
        super(entityManager, SaleItem.class);
    }

}
