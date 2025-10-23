package br.bakeryudesc.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
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

    public void softDelete(T entity) {
        EntityTransaction transaction = null;
        try {
            Method method = entity.getClass().getMethod("setDeletedAt", LocalDateTime.class);
            method.invoke(entity, LocalDateTime.now());

            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failure while trying to do soft delete.", e);
        }
    }

    public T findById(ID id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);

        cq.select(root).where(cb.isNull(root.get("deletedAt")));

        return entityManager.createQuery(cq).getResultList();
    }
}
