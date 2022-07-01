package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.dominio.ClienteDto;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.util.JpaUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HibernateQL {
    public static void main(String[] args) {
        EntityManager entityManager = JpaUtil.getEntityManagerFactory();

        System.out.println("=".repeat(50));
        System.out.println("Clientes");
        List<Cliente> clientes = entityManager
                .createQuery("SELECT c FROM Cliente c", Cliente.class)
                .getResultList();

        clientes.forEach(System.out::println);

        System.out.println("=".repeat(50));
        System.out.println("Cliente por id");
        Cliente cliente = entityManager
                .createQuery("SELECT c FROM Cliente c WHERE c.id = :id", Cliente.class)
                .setParameter("id", 1L)
                .getSingleResult();
        System.out.println(cliente);

        System.out.println("=".repeat(50));
        System.out.println("Cliente por nombre");
        String nombreCliente = entityManager
                .createQuery("SELECT c.nombre FROM Cliente c WHERE c.nombre = :nombre", String.class)
                .setParameter("nombre", "Gustavo")
                .setMaxResults(1) // limit
                .getSingleResult();
        System.out.println("nombreCliente = " + nombreCliente);


        System.out.println("=".repeat(50));
        System.out.println("Cliente varios campos");
        // Es un onjeto[] generico ya que devuelve varios tipos de datos
        Object[] clienteVariosCampos = entityManager
                .createQuery("SELECT c.nombre, c.apellido FROM Cliente c", Object[].class)
                .setMaxResults(1) // limit
                .getSingleResult();
        System.out.println("Nombre:=\t" + clienteVariosCampos[0] + "\tApellido:=\t"+ clienteVariosCampos[1]);

        System.out.println("=".repeat(50));
        System.out.println("Clientes varios campos");
        // devuelve una lista de objetos genericos
        List<Object[]> clientesVariosCampos = entityManager
                .createQuery("SELECT c.nombre, c.apellido FROM Cliente c", Object[].class)
                .getResultList();

        clientesVariosCampos.forEach(c -> {
            System.out.println("Nombre:=\t" + c[0] + "\tApellido:=\t"+ c[1]);
        });


        System.out.println("=".repeat(50));
        System.out.println("Cliente y varios campos");

        clientesVariosCampos = entityManager
                .createQuery("SELECT c, c.formaPago FROM Cliente c", Object[].class)
                .getResultList();

        clientesVariosCampos.forEach(c -> {
            System.out.println("Cliente:=\t" + c[0] + "\tForma pago:=\t"+ c[1]);
        });


        System.out.println("=".repeat(50));
        System.out.println("Clientes de varios campos sin devolver objetos genericos, pero teniendo un constructor que defina los campos");
        clientes = entityManager
                .createQuery("SELECT new Cliente (c.nombre, c.apellido) FROM Cliente c", Cliente.class)
                .getResultList();
        clientes.forEach(System.out::println);



        System.out.println("=".repeat(50));
        System.out.println("Clientes de varios campos sin devolver objetos genericos, usando DTO");
        List<ClienteDto> clientesDto = entityManager
                // org.gmarquezp.hibernate.dominio // Como no es un etity es necesario especificar todo el package
                .createQuery("SELECT new org.gmarquezp.hibernate.dominio.ClienteDto (c.id, c.nombre) FROM Cliente c", ClienteDto.class)
                .getResultList();
        clientesDto.forEach(System.out::println);


        System.out.println("=".repeat(50));
        System.out.println("Clientes con nombres Unicos (Distinct)");
        List<String> nombresUnicos = entityManager
                .createQuery("SELECT DISTINCT(c.nombre) FROM Cliente c", String.class)
                .getResultList();
        System.out.println("nombresUnicos = " + nombresUnicos);

        System.out.println("=".repeat(50));
        System.out.println("Cantidad de clientes (count)");
        Long cantidadClientes = entityManager
                .createQuery("SELECT COUNT(c) FROM Cliente c", Long.class)
                .getSingleResult();
        System.out.println("cantidadClientes = " + cantidadClientes);


        System.out.println("=".repeat(50));
        System.out.println("Cliente nombres completos y mayuscula (concat), LIKE");
        List<String> nombresCompletos = entityManager
                .createQuery("SELECT UPPER(CONCAT(c.nombre, ' ', c.apellido)) FROM Cliente c WHERE c.nombre LIKE :parametro", String.class)
                .setParameter("parametro", "%J%")
                .getResultList();
        nombresCompletos.forEach(System.out::println);


        System.out.println("========== consultas por rangos ==========");
//        clientes = em.createQuery("select c from Cliente c where c.id between 2 and 5", Cliente.class).getResultList();
        clientes = entityManager.createQuery("select c from Cliente c where c.nombre between 'J' and 'Q'", Cliente.class).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========== consulta con orden ==========");
        clientes = entityManager.createQuery("select c from Cliente c order by c.nombre asc, c.apellido asc", Cliente.class).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("=========== consulta con total de registros de la tabla ===========");
        Long total = entityManager.createQuery("select count(c) as total from Cliente c", Long.class).getSingleResult();
        System.out.println(total);

        System.out.println("========== consulta con valor minimo del id ==========");
        Long minId = entityManager.createQuery("select min(c.id) as minimo from Cliente c", Long.class).getSingleResult();
        System.out.println(minId);

        System.out.println("========== consulta con max / ultimo id ==========");
        Long maxId = entityManager.createQuery("select max(c.id) as maximo from Cliente c", Long.class).getSingleResult();
        System.out.println(maxId);

        System.out.println("=========== consulta con nombre y su largo ==========");
        List<Object[]>  registros = entityManager
                .createQuery("select c.nombre, length(c.nombre) from Cliente c", Object[].class)
                .getResultList();
        registros.forEach(reg ->{
            String nom = (String) reg[0];
            Integer largo = (Integer) reg[1];
            System.out.println("nombre=" + nom + ", largo=" + largo);
        });

        System.out.println("========== consulta con el nombre mas corto ==========");
        Integer minLargoNombre = entityManager.createQuery("select min(length(c.nombre)) from Cliente c", Integer.class).getSingleResult();
        System.out.println(minLargoNombre);

        System.out.println("========== consulta con el nombre mas largo ==========");
        Integer maxLargoNombre = entityManager.createQuery("select max(length(c.nombre)) from Cliente c", Integer.class).getSingleResult();
        System.out.println(maxLargoNombre);

        System.out.println("========== consultas resumen funciones agregaciones count min max avg sum ==========");
        Object[] estadisticas = entityManager.createQuery("select min(c.id), max(c.id), sum(c.id), count(c.id), avg(length(c.nombre)) from Cliente c", Object[].class)
                .getSingleResult();
        Long min = (Long) estadisticas[0];
        Long max = (Long) estadisticas[1];
        Long sum = (Long) estadisticas[2];
        Long count = (Long) estadisticas[3];
        Double avg = (Double) estadisticas[4];
        System.out.println("min=" + min + ", max=" + max + ", sum=" + sum + ", count=" + count + ", avg=" + avg);

        System.out.println("=========== consulta con el nombre mas corto y su largo ==========");
        registros = entityManager.createQuery("select c.nombre, length(c.nombre) from Cliente c where " +
                        "length(c.nombre) = (select min(length(c.nombre)) from Cliente c)", Object[].class)
                .getResultList();
        registros.forEach(reg -> {
            String nom = (String) reg[0];
            Integer largo = (Integer) reg[1];
            System.out.println("nombre=" + nom + ", largo=" + largo);
        });

        System.out.println("========== consulta para obtener el ultimo registro ==========");
        Cliente ultimoCliente = entityManager.createQuery("select c from Cliente c where c.id = (select max(c.id) from Cliente c)", Cliente.class)
                .getSingleResult();
        System.out.println(ultimoCliente);

        System.out.println("========== consulta where in =========");
        clientes = entityManager.createQuery("select c from Cliente c where c.id in :ids", Cliente.class)
                .setParameter("ids", Arrays.asList(1L, 2L, 10L, 6L))
                .getResultList();
        clientes.forEach(System.out::println);
        
        
        // cerrando la conexion
        entityManager.close();

    }
}
