package br.bakeryudesc.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDAO<T, ID extends Serializable> {

    protected EntityManager entityManager;
    private final Class<T> entityClass;

    public AbstractDAO(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    public void save(T entity) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public T update(T entity) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            T updatedEntity = entityManager.merge(entity);
            transaction.commit();
            return updatedEntity;
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void deleteById(ID id) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            T entity = findById(id);
            if (entity != null) {
                entityManager.remove(entity);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public T findById(ID id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll() {
        CriteriaQuery<T> cq = entityManager.getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        return entityManager.createQuery(cq).getResultList();
    }
}
