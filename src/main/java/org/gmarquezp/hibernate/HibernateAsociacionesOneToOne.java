package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.entity.ClienteDetalle;
import org.gmarquezp.hibernate.util.JpaUtil;

public class HibernateAsociacionesOneToOne {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManagerFactory();

        try {


            //Creando el detalle
            em.getTransaction().begin();
            ClienteDetalle clienteDetalle = new ClienteDetalle(true,900L );
            em.persist(clienteDetalle);
            em.getTransaction().commit();

            //Creando el cliente con el detalle
            em.getTransaction().begin();

            Cliente cliente = new Cliente();
            cliente.setNombre("Lauyra");
            cliente.setApellido("Meli");
            cliente.setFormaPago("TC");

            // Asociamos el cliente detalle al cliente
            cliente.setClienteDetalle(clienteDetalle);
            // Guardando el cliente
            em.persist(cliente);

            em.getTransaction().commit();
            System.out.println("cliente creado = " + cliente);

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
