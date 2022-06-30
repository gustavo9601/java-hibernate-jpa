package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.util.JpaUtil;

import javax.swing.*;

public class HibernateCrear {
    public static void main(String[] args) {

        EntityManager entityManager = JpaUtil.getEntityManagerFactory();
        try {
            // inicia la transaccion
            entityManager.getTransaction().begin();

            // crea un nuevo cliente
            Cliente cliente = new Cliente();
            cliente.setNombre("Gustavo");
            cliente.setApellido("Marquez");
            cliente.setFormaPago("Tarjeta de crédito");


            // guarda el cliente en la base de datos
            entityManager.persist(cliente);

            // cuando comitea devuelve el id del cliente
            System.out.println("cliente =\t" + cliente);

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
