package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.entity.Factura;
import org.gmarquezp.hibernate.util.JpaUtil;

public class HibernateAsociacionesManyToOneFind {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManagerFactory();

        try{
            em.getTransaction().begin();

            // Capturando el cliente
            Cliente cliente = em.find(Cliente.class, 1L);

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
