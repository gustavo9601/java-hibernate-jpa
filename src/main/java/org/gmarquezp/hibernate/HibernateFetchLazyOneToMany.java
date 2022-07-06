package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.util.JpaUtil;

public class HibernateFetchLazyOneToMany {
    public static void main(String[] args) {

        EntityManager entityManager = JpaUtil.getEntityManagerFactory();

        Cliente cliente = entityManager.find(Cliente.class, 1L);
        //System.out.println("cliente = " + cliente);
        
        entityManager.close();
        
    }
}
