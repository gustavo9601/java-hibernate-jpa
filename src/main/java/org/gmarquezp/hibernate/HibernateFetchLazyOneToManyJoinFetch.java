package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.util.JpaUtil;

public class HibernateFetchLazyOneToManyJoinFetch {
    public static void main(String[] args) {

        EntityManager entityManager = JpaUtil.getEntityManagerFactory();

        // left join fetch  // reliza el join y con el fethc, fuerza al DTO interno a poblar la relacion
        Cliente cliente = entityManager.createQuery("SELECT c FROM Cliente c " +
                        "left join fetch c.direcciones " +
                        "left join fetch c.clienteDetalle " +
                        " WHERE c.id = :id", Cliente.class)
                .setParameter("id", 1L)
                .getSingleResult();
        System.out.println("cliente = " + cliente);
        
        entityManager.close();
        
    }
}
