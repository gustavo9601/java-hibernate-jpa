package org.gmarquezp.hibernate;


import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.util.JpaUtil;

import java.util.List;


public class HibernateCriteria {
    public static void main(String[] args) {
        EntityManager entityManager = JpaUtil.getEntityManagerFactory();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Cliente> query = criteriaBuilder.createQuery(Cliente.class);

        Root<Cliente> from = query.from(Cliente.class);

        // select * from cliente;
        query.select(from);


        List<Cliente> clientes = entityManager.createQuery(query)
                .getResultList();
        System.out.println("Listando clientes");
        clientes.forEach(System.out::println);


        System.out.println("=".repeat(100));
        System.out.println("Listar con where");
        query = criteriaBuilder.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from)
                .where(criteriaBuilder.equal(from.get("nombre"), "Juan"));
        clientes = entityManager.createQuery(query).getResultList();
        clientes.forEach(System.out::println);


        System.out.println("=".repeat(100));
        System.out.println("Listar con where Like con parametro");
        query = criteriaBuilder.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        ParameterExpression<String> nombreParam = criteriaBuilder.parameter(String.class, "nombreParam");

        query.select(from)
                // .where(criteriaBuilder.like(from.get("nombre"), "%J%"));
                .where(criteriaBuilder.like(from.get("nombre"), nombreParam)); // le pasamos el parametro de criteriaBuilder

        clientes = entityManager.createQuery(query)
                .setParameter("nombreParam", "%G%")
                .getResultList();

        clientes.forEach(System.out::println);


        System.out.println("========== filtrar usando predicados mayor que o mayor igual que ===========");
        query = criteriaBuilder.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).where(criteriaBuilder.gt(from.get("id"), 2L));
        clientes = entityManager.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        query = criteriaBuilder.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        query.select(from).where(criteriaBuilder.ge(criteriaBuilder.length(from.get("nombre")), 6L));
        clientes = entityManager.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========== consulta con los predicados conjuncion and y disyunsion or ==========");

        query = criteriaBuilder.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        Predicate porNombre = criteriaBuilder.equal(from.get("nombre"), "Andres");
        Predicate porFormaPago = criteriaBuilder.equal(from.get("formaPago"), "debito");
        Predicate p3 = criteriaBuilder.ge(from.get("id"), 4L);
        query.select(from).where(criteriaBuilder.and(p3, criteriaBuilder.or(porNombre, porFormaPago)));
        clientes = entityManager.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========== consultas con order by asc desc =========");

        query = criteriaBuilder.createQuery(Cliente.class);
        from = query.from(Cliente.class);

        query.select(from).orderBy(criteriaBuilder.desc(from.get("nombre")), criteriaBuilder.asc(from.get("apellido")));
        clientes = entityManager.createQuery(query).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========== consulta por id ===========");
        query = criteriaBuilder.createQuery(Cliente.class);
        from = query.from(Cliente.class);
        ParameterExpression<Long> idParam = criteriaBuilder.parameter(Long.class, "id");

        query.select(from).where(criteriaBuilder.equal(from.get("id"), idParam));

        Cliente cliente = entityManager.createQuery(query)
                .setParameter("id", 1L)
                .getSingleResult();
        System.out.println(cliente);

        System.out.println("========== consulta solo el nombre de los clientes ==========");
        CriteriaQuery<String> queryString = criteriaBuilder.createQuery(String.class);
        from = queryString.from(Cliente.class);
        queryString.select(from.get("nombre"));
        List<String> nombres = entityManager.createQuery(queryString).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("========== consulta solo el nombre de los clientes unicos con distinct ==========");
        queryString = criteriaBuilder.createQuery(String.class);
        from = queryString.from(Cliente.class);
        queryString.select(criteriaBuilder.upper(from.get("nombre"))).distinct(true);
        nombres = entityManager.createQuery(queryString).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("=========== consulta por nombres y apellidos concatenados ==========");
        queryString = criteriaBuilder.createQuery(String.class);
        from = queryString.from(Cliente.class);

        queryString.select(criteriaBuilder.concat(criteriaBuilder.concat(from.get("nombre"), " "), from.get("apellido")));
        nombres = entityManager.createQuery(queryString).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("=========== consulta por nombres y apellidos concatenados upper o lower ==========");
        queryString = criteriaBuilder.createQuery(String.class);
        from = queryString.from(Cliente.class);

        queryString.select(criteriaBuilder.upper(criteriaBuilder.concat(criteriaBuilder.concat(from.get("nombre"), " "), from.get("apellido"))));
        nombres = entityManager.createQuery(queryString).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("========== consulta de campos personalizados del entity cliente ============");
        CriteriaQuery<Object[]> queryObject = criteriaBuilder.createQuery(Object[].class);
        from = queryObject.from(Cliente.class);
        queryObject.multiselect(from.get("id"), from.get("nombre"), from.get("apellido"));
        List<Object[]> registros = entityManager.createQuery(queryObject).getResultList();
        registros.forEach(reg -> {
            Long id = (Long) reg[0];
            String nombre = (String) reg[1];
            String apellido = (String) reg[2];
            System.out.println("id=" + id + ", nombre=" + nombre + ", apellido=" + apellido);
        });

        System.out.println("========== consulta de campos personalizados del entity cliente con where ============");
        queryObject = criteriaBuilder.createQuery(Object[].class);
        from = queryObject.from(Cliente.class);
        queryObject.multiselect(from.get("id"), from.get("nombre"), from.get("apellido")).where(criteriaBuilder.like(from.get("nombre"), "%lu%"));
        registros = entityManager.createQuery(queryObject).getResultList();
        registros.forEach(reg -> {
            Long id = (Long) reg[0];
            String nombre = (String) reg[1];
            String apellido = (String) reg[2];
            System.out.println("id=" + id + ", nombre=" + nombre + ", apellido=" + apellido);
        });

        System.out.println("========== consulta de campos personalizados del entity cliente con where id ============");
        queryObject = criteriaBuilder.createQuery(Object[].class);
        from = queryObject.from(Cliente.class);
        queryObject.multiselect(from.get("id"), from.get("nombre"), from.get("apellido")).where(criteriaBuilder.equal(from.get("id"), 2L));
        Object[] registro = entityManager.createQuery(queryObject).getSingleResult();

        Long id = (Long) registro[0];
        String nombre = (String) registro[1];
        String apellido = (String) registro[2];
        System.out.println("id=" + id + ", nombre=" + nombre + ", apellido=" + apellido);

        System.out.println("=========== contar registros de la consulta con count ==========");

        CriteriaQuery<Long> queryLong = criteriaBuilder.createQuery(Long.class);
        from = queryLong.from(Cliente.class);
        queryLong.select(criteriaBuilder.count(from.get("id")));
        Long count = entityManager.createQuery(queryLong).getSingleResult();
        System.out.println(count);

        System.out.println("========== sumar datos de algun campo de la table ==========");
        queryLong = criteriaBuilder.createQuery(Long.class);
        from = queryLong.from(Cliente.class);
        queryLong.select(criteriaBuilder.sum(from.get("id")));
        Long sum = entityManager.createQuery(queryLong).getSingleResult();
        System.out.println(sum);

        System.out.println("========== consulta con el maximo id ==========");
        queryLong = criteriaBuilder.createQuery(Long.class);
        from = queryLong.from(Cliente.class);
        queryLong.select(criteriaBuilder.max(from.get("id")));
        Long max = entityManager.createQuery(queryLong).getSingleResult();
        System.out.println(max);

        System.out.println("========== consulta con el minimo id ==========");
        queryLong = criteriaBuilder.createQuery(Long.class);
        from = queryLong.from(Cliente.class);
        queryLong.select(criteriaBuilder.min(from.get("id")));
        Long min = entityManager.createQuery(queryLong).getSingleResult();
        System.out.println(min);

        System.out.println("=========== ejemplo varios resultados de funciones de agregacion en una sola consulta ==========");

        queryObject = criteriaBuilder.createQuery(Object[].class);
        from = queryObject.from(Cliente.class);
        queryObject.multiselect(criteriaBuilder.count(from.get("id"))
                , criteriaBuilder.sum(from.get("id"))
                , criteriaBuilder.max(from.get("id"))
                , criteriaBuilder.min(from.get("id")));

        registro = entityManager.createQuery(queryObject).getSingleResult();

        count = (Long)registro[0];
        sum = (Long) registro[1];
        max = (Long) registro[2];
        min = (Long) registro[3];

        System.out.println("count=" + count + ", sum=" + sum + ", max=" + max + ", min=" + min);



        entityManager.close();

    }
}
