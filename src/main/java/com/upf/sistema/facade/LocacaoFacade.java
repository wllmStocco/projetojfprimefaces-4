/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upf.sistema.facade;

import com.upf.sistema.entity.LocacaoEntity;
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
public class LocacaoFacade extends AbstractFacade<LocacaoEntity> {

    @PersistenceContext(unitName = "SistemaPrimeFacesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LocacaoFacade() {
        super(LocacaoEntity.class);
    }

    private List<LocacaoEntity> entityList;

    public List<LocacaoEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT l FROM LocacaoEntity l ORDER BY l.dataInicio");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<LocacaoEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }

    public LocacaoEntity buscarPorId(int id) {
        LocacaoEntity locacao = null;
        try {
            locacao = getEntityManager().find(LocacaoEntity.class, id);
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return locacao;
    }
}