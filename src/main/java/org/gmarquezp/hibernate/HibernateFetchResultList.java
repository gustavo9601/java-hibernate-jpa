package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.util.JpaUtil;

import java.util.List;

public class HibernateFetchResultList {
    public static void main(String[] args) {

        EntityManager entityManager = JpaUtil.getEntityManagerFactory();

        List<Cliente> clientes = entityManager.createQuery("SELECT c FROM Cliente c " +
                        "LEFT OUTER JOIN c.clienteDetalle", Cliente.class)
                .getResultList();


        System.out.println("*".repeat(50));
        clientes.forEach(cliente -> {
            System.out.println("\n cliente = \n");
            System.out.println(cliente);
        });

        entityManager.close();


    }
}
