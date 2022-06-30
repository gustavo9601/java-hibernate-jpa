package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.util.JpaUtil;

public class HibernateActualizar {
    public static void main(String[] args) {
        EntityManager entityManager = JpaUtil.getEntityManagerFactory();
        try {
            // inicia la transaccion
            entityManager.getTransaction().begin();

            // crea un nuevo cliente
            Cliente cliente = entityManager.find(Cliente.class, 1L);
            System.out.println("cliente antes=\t" + cliente);
            cliente.setApellido("Marquez sEl mas Paaaaros");

            // actualiza el cliente en la base de datos
            entityManager.merge(cliente);

            // cuando comitea devuelve el id del cliente
            System.out.println("cliente despues=\t" + cliente);

            // comitea el cambio, hace efectivo el cambio en la base de datos
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // si hay un error, se deshace el cambio
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            // cierra la conexion
            entityManager.close();
        }
    }
}
