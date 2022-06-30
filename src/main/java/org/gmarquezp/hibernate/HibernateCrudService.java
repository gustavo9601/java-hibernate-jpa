package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.services.ClienteService;
import org.gmarquezp.hibernate.services.ClienteServiceImpl;
import org.gmarquezp.hibernate.util.JpaUtil;

public class HibernateCrudService {
    public static void main(String[] args) {

        EntityManager entityManager = JpaUtil.getEntityManagerFactory();

        ClienteService clienteService = new ClienteServiceImpl(entityManager);

        System.out.println("Listando clientes");
        clienteService.listar()
                .forEach(cliente -> System.out.println(cliente));


        System.out.println("Cliente por ID: 1");
        if(clienteService.porId(100L).isPresent()) {
            System.out.println(clienteService.porId(100L).get());
        }
        else {
            System.out.println("No existe el cliente con ID: 1");
        }

        System.out.println("Guardando cliente");
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setApellido("Perez");
        cliente.setFormaPago("Efectivo");
        clienteService.guardar(cliente);

        System.out.println("Actualizando cliente con id 2");
        cliente = clienteService.porId(2L).get();
        cliente.setNombre("Lauraaaaaa");
        clienteService.guardar(cliente);


         System.out.println("Eliminando cliente con id 3");
         clienteService.eliminar(3L);


        // Cerrando al final toda la conexion
        entityManager.close();



    }
}
