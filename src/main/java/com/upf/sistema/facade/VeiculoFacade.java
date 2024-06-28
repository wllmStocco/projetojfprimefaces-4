/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upf.sistema.facade;

import com.upf.sistema.entity.VeiculoEntity;
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
public class VeiculoFacade extends AbstractFacade<VeiculoEntity> {

    @PersistenceContext(unitName = "SistemaPrimeFacesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VeiculoFacade() {
        super(VeiculoEntity.class);
    }

    private List<VeiculoEntity> entityList;

    public List<VeiculoEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT v FROM VeiculoEntity v ORDER BY v.modelo");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<VeiculoEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }

    public VeiculoEntity buscarPorPlaca(String placa) {
        VeiculoEntity veiculo = null;
        try {
            Query query = getEntityManager()
                .createQuery("SELECT v FROM VeiculoEntity v WHERE v.placa = :placa");
            query.setParameter("placa", placa);

            if (!query.getResultList().isEmpty()) {
                veiculo = (VeiculoEntity) query.getSingleResult();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return veiculo;
    }
}