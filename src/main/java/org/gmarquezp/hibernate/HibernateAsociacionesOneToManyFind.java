package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.entity.Direccion;
import org.gmarquezp.hibernate.util.JpaUtil;

import java.nio.file.DirectoryStream;

public class HibernateAsociacionesOneToManyFind {
    public static void main(String[] args) {


        EntityManager em = JpaUtil.getEntityManagerFactory();

        try{

            em.getTransaction().begin();

            Cliente cliente = em.find(Cliente.class, 1L);

            Direccion direccion1 = new Direccion("Calle 1000", 1000);
            Direccion direccion2 = new Direccion("Calle 2000", 2000);


            // Pusheando direcciones al cliente
            cliente.getDirecciones().add(direccion1);
            cliente.getDirecciones().add(direccion2);

            // Actualizando el cliente con las direcciones
            em.merge(cliente);

            em.getTransaction().commit();
            System.out.println("cliente actualizado = " + cliente);
            System.out.println("Cantidad direcciones = " + cliente.getDirecciones().size());

            // Otra transaccion

            em.getTransaction().begin();

            Cliente cliente2 = em.find(Cliente.class, cliente.getId());
            // Cuando nace desde un find, es necesario enviar la instancia obtenida dede la bd para direcciones

            Direccion direccion1Busqueda = em.find(Direccion.class, 1L);
            cliente.getDirecciones().remove(direccion1Busqueda); // Eliminamos la direccion 1
            em.merge(cliente2); // Actualizamos el cliente eliminando la direccion
            em.getTransaction().commit();

            System.out.println("cliente actualizado = " + cliente2);
            System.out.println("Cantidad direcciones = " + cliente2.getDirecciones().size());
        }catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }

    }
}
