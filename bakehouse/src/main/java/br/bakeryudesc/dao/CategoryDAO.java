package br.bakeryudesc.dao;

import br.bakeryudesc.model.Category;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class CategoryDAO extends AbstractDAO<Category,Long> {

    public CategoryDAO(EntityManager entityManager) {
        super(entityManager, Category.class);
    }

    public Category findByName(String name) {
        try {
            TypedQuery<Category> query = entityManager.createQuery("SELECT c FROM Category c WHERE c.name = :name", Category.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
