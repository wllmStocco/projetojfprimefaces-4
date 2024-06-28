/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.upf.sistema.controller;

import com.upf.sistema.entity.VeiculoEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
@Named(value = "veiculoController")
@SessionScoped
public class VeiculoController implements Serializable {

    @EJB
    private com.upf.sistema.facade.VeiculoFacade ejbFacade;

    private VeiculoEntity veiculo = new VeiculoEntity();
    private List<VeiculoEntity> veiculoList = new ArrayList<>();
    private VeiculoEntity selected;
    private int nextId = 1;

    // Getters and Setters
    public VeiculoEntity getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(VeiculoEntity veiculo) {
        this.veiculo = veiculo;
    }

    public List<VeiculoEntity> getVeiculoList() {
        return ejbFacade.buscarTodos();
    }

    public void setVeiculoList(List<VeiculoEntity> veiculoList) {
        this.veiculoList = veiculoList;
    }

    public VeiculoEntity getSelected() {
        return selected;
    }

    public void setSelected(VeiculoEntity selected) {
        this.selected = selected;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public VeiculoEntity prepareAdicionar() {
        veiculo = new VeiculoEntity();
        return veiculo;
    }

    private void addErrorMessage(String msg) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    private void addSuccessMessage(String msg) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("sucessInfo", fm);
    }

    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }

    private void persist(PersistAction persistAction, String successMessage) {
        try {
            if (null != persistAction) {
                switch (persistAction) {
                    case CREATE:
                        ejbFacade.createReturn(veiculo);
                        break;
                    case UPDATE:
                        ejbFacade.edit(selected);
                        selected = null;
                        break;
                    case DELETE:
                        ejbFacade.remove(selected);
                        selected = null;
                        break;
                    default:
                        break;
                }
            }
            addSuccessMessage(successMessage);
        } catch (EJBException ex) {
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) {
                msg = cause.getLocalizedMessage();
            }
            if (msg.length() > 0) {
                addErrorMessage(msg);
            } else {
                addErrorMessage(ex.getLocalizedMessage());
            }
        } catch (Exception ex) {
            addErrorMessage(ex.getLocalizedMessage());
        }
    }

    public void adicionarVeiculo() {
        persist(VeiculoController.PersistAction.CREATE, "Veículo criado com sucesso!");
    }

    public void editarVeiculo() {
        persist(VeiculoController.PersistAction.UPDATE, "Veículo atualizado com sucesso!");
    }

    public void deletarVeiculo() {
        persist(VeiculoController.PersistAction.DELETE, "Veículo removido com sucesso!");
    }
}