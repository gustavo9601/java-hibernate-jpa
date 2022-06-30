package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.util.JpaUtil;

public class HibernateListarWhere {
    public static void main(String[] args) {
        EntityManager entityManager = JpaUtil.getEntityManagerFactory();

        System.out.println("Listando clientes con forma de pago 'Tarjeta de crédito'");
        entityManager.createQuery("SELECT c FROM Cliente c WHERE c.formaPago = ?1", Cliente.class)
                .setParameter(1, "Debito") // seteamos el parametro 1
                .getResultList() // obtenemos la lista de resultados
                .forEach(cliente -> System.out.println(cliente));

        Cliente cliente = entityManager.createQuery("SELECT c FROM Cliente c WHERE c.nombre= ?1", Cliente.class)
                .setParameter(1, "Gustavo") // seteamos el parametro 1
                .getSingleResult(); // obtenemos un unico registro, de devolver mas o ninguna devuelve exepcion
        System.out.println("un solo cliente = " + cliente);


        System.out.println("Cliente por id");
        Cliente cliente1 = entityManager.find(Cliente.class, 1L); // find busca por la llave primaria y adicional cachea la respuesta en la sesion
        System.out.println("cliente1 = " + cliente1);

        // Cerrando el EntityManager
        entityManager.close();

    }
}
