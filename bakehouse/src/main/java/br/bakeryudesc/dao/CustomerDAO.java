package br.bakeryudesc.dao;

import br.bakeryudesc.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class CustomerDAO extends AbstractDAO<Customer, Long> {

    public CustomerDAO(EntityManager entityManager) {
        super(entityManager, Customer.class);
    }

    public Customer findByCpf(String cpf) {
        try {
            TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.cpf = :cpf", Customer.class);
            query.setParameter("cpf", cpf);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
