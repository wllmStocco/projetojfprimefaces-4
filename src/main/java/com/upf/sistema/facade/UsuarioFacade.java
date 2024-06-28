/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upf.sistema.facade;

import com.upf.sistema.entity.UsuarioEntity;
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
public class UsuarioFacade extends AbstractFacade<UsuarioEntity> {
    
    @PersistenceContext(unitName = "SistemaPrimeFacesPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public UsuarioFacade() {
        super(UsuarioEntity.class);
    }
    
    private List<UsuarioEntity> entityList;

    public List<UsuarioEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT u FROM UsuarioEntity u ORDER BY u.nome");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<UsuarioEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }
    public UsuarioEntity buscarPorEmail(String email, String senha) {
        UsuarioEntity usuario = new UsuarioEntity();
        try {
            // utilizando JPQL para construir a query
            Query query = getEntityManager()
                .createQuery("SELECT u FROM UsuarioEntity u WHERE u.email = :email AND u.senha = :senha");
            query.setParameter("email", email);
            query.setParameter("senha", senha);

            // verifica se existe algum resultado para não gerar exceção
            if (!query.getResultList().isEmpty()) {
                usuario = (UsuarioEntity) query.getSingleResult();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return usuario;
    }
}
