package br.bakeryudesc.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceInstance {

    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("padaria-pu");

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }

}
