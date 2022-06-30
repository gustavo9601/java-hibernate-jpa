package org.gmarquezp.hibernate.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.gmarquezp.hibernate.entity.Cliente;

import java.util.List;

public class ClienteRepository implements CrudRepository<Cliente> {

    private EntityManager entityManager;

    public ClienteRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Cliente> listar() {
        return this.entityManager
                .createQuery("select c from Cliente c", Cliente.class)
                .getResultList();
    }

    @Override
    public Cliente porId(Long id) {
        try {
            return this.entityManager
                    .createQuery("select c from Cliente c where c.id = ?1", Cliente.class)
                    .setParameter(1, id)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Error al obtener el cliente con id: " + id);
            // e.printStackTrace();
        }
        return null;
    }

    @Override
    public void guardar(Cliente cliente) {
        if (cliente.getId() == null) {
            // Guadamos un nuevo registo
            this.entityManager.persist(cliente);
        } else {
            // Actualizamos
            this.entityManager.merge(cliente);
        }
    }

    @Override
    public void eliminar(Long id) {
        Cliente cliente = this.porId(id);
        if (cliente != null) {
            this.entityManager.remove(cliente);
        }
    }
}
