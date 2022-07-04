package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.entity.Factura;
import org.gmarquezp.hibernate.util.JpaUtil;

public class HibernateAsociacionesOneToManyBidireccional {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManagerFactory();
        try {

            em.getTransaction().begin();
            Cliente cliente = new Cliente();
            cliente.setNombre("Gustavo");
            cliente.setApellido("Marquez");
            cliente.setFormaPago("Efectivo");

            Factura factura1 = new Factura("Factura 1", 500L);
            Factura factura2 = new Factura("Factura 2", 1500L);

            // Pusehando facturas
            cliente.addFacturas(factura1)
                    .addFacturas(factura2);

            // seteando en doble via el id de la relacion
            // revisar la entidad cliente que ya lo hace en el metodo addFactura
            // factura1.setCliente(cliente);
            // factura2.setCliente(cliente);


            // Guardando el cliente
            em.persist(cliente);

            em.getTransaction().commit();

            System.out.println("Cliente guardado con facturas=\t" + cliente);




            // Otra transaccion
            em.getTransaction().begin();

            Factura factura3 = em.find(Factura.class, 1L);
            // Eliminamos la factura por el cliente
            cliente.getFacturas().remove(factura3);

            // Seteando a null el cliente en la factura, para que se borre la relacion
            factura3.setCliente(null);

            // actualizando
            em.merge(cliente);
            em.getTransaction().commit();

            System.out.println("cliente eliminando factura = " + cliente);




        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
