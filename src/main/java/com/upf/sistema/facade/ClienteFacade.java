/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upf.sistema.facade;

import com.upf.sistema.entity.ClienteEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
@Stateless
public class ClienteFacade extends AbstractFacade<ClienteEntity> {

    @PersistenceContext(unitName = "SistemaPrimeFacesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(ClienteEntity.class);
    }

    private List<ClienteEntity> entityList;

    public List<ClienteEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT c FROM ClienteEntity c ORDER BY c.nome");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<ClienteEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }

    public ClienteEntity buscarPorEmail(String email) {
        ClienteEntity cliente = null;
        try {
            Query query = getEntityManager()
                .createQuery("SELECT c FROM ClienteEntity c WHERE c.email = :email");
            query.setParameter("email", email);

            if (!query.getResultList().isEmpty()) {
                cliente = (ClienteEntity) query.getSingleResult();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return cliente;
    }
}