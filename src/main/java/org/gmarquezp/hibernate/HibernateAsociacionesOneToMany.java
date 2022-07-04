package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.entity.Direccion;
import org.gmarquezp.hibernate.util.JpaUtil;

public class HibernateAsociacionesOneToMany {
    public static void main(String[] args) {


        EntityManager em = JpaUtil.getEntityManagerFactory();

        try{

            em.getTransaction().begin();

            Cliente cliente = new Cliente();
            cliente.setNombre("Javier");
            cliente.setApellido("Perez");
            cliente.setFormaPago("TC");

            Direccion direccion1 = new Direccion("Calle 1", 20);
            Direccion direccion2 = new Direccion("Calle 2", 1000);


            // Pusheando direcciones al cliente
            cliente.getDirecciones().add(direccion1);
            cliente.getDirecciones().add(direccion2);

            // Guardando el cliente con las direcciones
            em.persist(cliente);

            em.getTransaction().commit();
            System.out.println("cliente creado = " + cliente);
            System.out.println("Cantidad direcciones = " + cliente.getDirecciones().size());

            // Otra transaccion

            em.getTransaction().begin();

            Cliente cliente2 = em.find(Cliente.class, cliente.getId());
            cliente.getDirecciones().remove(direccion1); // Eliminamos la direccion 1
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
