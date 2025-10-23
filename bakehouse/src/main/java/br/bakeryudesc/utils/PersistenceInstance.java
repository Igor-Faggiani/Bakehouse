package br.bakeryudesc.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceInstance {

    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("bakehouse");

    public static EntityManager entityManager;

    public static EntityManager getEntityManager() {
        entityManager = FACTORY.createEntityManager();
        return entityManager;
    }

}
