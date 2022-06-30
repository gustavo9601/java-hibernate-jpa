package org.gmarquezp.hibernate.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    /*
    * Singleton para el EntityManagerFactory
    *
    * Permite obtener un EntityManagerFactory que se encarga de crear EntityManagers
    /
     */
    private static final EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();
    private static final EntityManagerFactory buildEntityManagerFactory() {
        // archivo persistence.xml =>     <persistence-unit name="ejemplo-hibernate-jpa" transaction-type="RESOURCE_LOCAL">
        return Persistence.createEntityManagerFactory("ejemplo-hibernate-jpa");
    }

    // Devolviendo la factoria de EntityManager
    public static EntityManager getEntityManagerFactory() {
        return entityManagerFactory.createEntityManager();
    }

}
