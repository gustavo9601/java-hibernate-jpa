package org.gmarquezp.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.entity.Factura;
import org.gmarquezp.hibernate.util.JpaUtil;

import java.util.List;


public class HibernateFetchManyToOneCriteria {
    public static void main(String[] args) {

        EntityManager entityManager = JpaUtil.getEntityManagerFactory();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Factura> query = criteriaBuilder.createQuery(Factura.class);

        Root<Factura> from = query.from(Factura.class);
        // añade el join fetch de la relacion many to one
        // "cliente" // nombre de la relacion many to one
        from.fetch("cliente", JoinType.INNER);

        query.select(from);

        List<Factura> facturaList = entityManager.createQuery(query).getResultList();

        System.out.println("*".repeat(50));
        facturaList.forEach(factura -> {
            System.out.println("factura = \n");
            System.out.println(factura);
            System.out.println("\nCliente factura=\t" + factura.getCliente().getNombre());
        });

        entityManager.close();

    }
}
