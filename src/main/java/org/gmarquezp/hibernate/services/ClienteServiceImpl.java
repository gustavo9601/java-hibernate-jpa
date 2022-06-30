package org.gmarquezp.hibernate.services;

import jakarta.persistence.EntityManager;
import org.gmarquezp.hibernate.entity.Cliente;
import org.gmarquezp.hibernate.repositories.ClienteRepository;
import org.gmarquezp.hibernate.repositories.CrudRepository;

import java.util.List;
import java.util.Optional;

public class ClienteServiceImpl implements ClienteService {


    private EntityManager entityManager;
    private CrudRepository<Cliente> clienteRepository;

    public ClienteServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.clienteRepository = new ClienteRepository(this.entityManager);
    }

    @Override
    public List<Cliente> listar() {
        return this.clienteRepository.listar();
    }

    @Override
    public Optional<Cliente> porId(Long id) {
        return Optional.ofNullable(this.clienteRepository.porId(id));
    }

    @Override
    public void guardar(Cliente cliente) {
        try{
            this.entityManager.getTransaction().begin();

            this.clienteRepository.guardar(cliente);

            this.entityManager.getTransaction().commit();
        }catch (Exception e){
            this.entityManager.getTransaction().rollback();
            System.out.println("Error al guardar el cliente: " + cliente);
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {
        try{
            this.entityManager.getTransaction().begin();

            this.clienteRepository.eliminar(id);

            this.entityManager.getTransaction().commit();
        }catch (Exception e){
            this.entityManager.getTransaction().rollback();
            System.out.println("Error al eliminar el cliente con id: " + id);
            e.printStackTrace();
        }
    }
}
