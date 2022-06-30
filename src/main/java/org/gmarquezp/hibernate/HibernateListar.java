package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.util.JpaUtil;

import java.util.List;

public class HibernateListar {
    public static void main(String[] args) {
        // Obteniendo un EntityManager
        EntityManager entityManager = JpaUtil.getEntityManagerFactory();

        // createQuery => crea una consulta en ves de la tabla se pasa el objeto de la clase que se quiere consultar
        List<Cliente> clientes = entityManager
                .createQuery("SELECT c FROM Cliente c", Cliente.class)
                .getResultList();


        clientes.forEach(cliente -> System.out.println(cliente));

        // Cerrando el EntityManager
        entityManager.close();
    }


}
