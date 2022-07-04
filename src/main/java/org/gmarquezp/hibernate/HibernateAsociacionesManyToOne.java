package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.entity.Factura;
import org.gmarquezp.hibernate.util.JpaUtil;

public class HibernateAsociacionesManyToOne {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManagerFactory();

        try{
            em.getTransaction().begin();

            Cliente cliente = new Cliente();
            cliente.setNombre("Oscar");
            cliente.setApellido("Perez");
            cliente.setFormaPago("Efectivo");

            // Guardando el cliente
            em.persist(cliente);

            Factura factura = new Factura("Factura 1", 100L);
            factura.setCliente(cliente);

            // Guardando la factura
            em.persist(factura);
            System.out.println("factura creada = " + factura);


            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }



    }
}
