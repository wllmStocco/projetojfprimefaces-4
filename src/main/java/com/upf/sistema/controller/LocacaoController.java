package com.upf.sistema.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */

import com.upf.sistema.entity.LocacaoEntity;
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
@Named(value = "locacaoController")
@SessionScoped
public class LocacaoController implements Serializable {

    @EJB
    private com.upf.sistema.facade.LocacaoFacade ejbFacade;

    private LocacaoEntity locacao = new LocacaoEntity();
    private List<LocacaoEntity> locacaoList = new ArrayList<>();
    private LocacaoEntity selected;
    private int nextId = 1;

    // Getters and Setters
    public LocacaoEntity getLocacao() {
        return locacao;
    }

    public void setLocacao(LocacaoEntity locacao) {
        this.locacao = locacao;
    }

    public List<LocacaoEntity> getLocacaoList() {
        return ejbFacade.buscarTodos();
    }

    public void setLocacaoList(List<LocacaoEntity> locacaoList) {
        this.locacaoList = locacaoList;
    }

    public LocacaoEntity getSelected() {
        return selected;
    }

    public void setSelected(LocacaoEntity selected) {
        this.selected = selected;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public LocacaoEntity prepareAdicionar() {
        locacao = new LocacaoEntity();
        return locacao;
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
                        ejbFacade.createReturn(locacao);
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

    public void adicionarLocacao() {
        persist(LocacaoController.PersistAction.CREATE, "Locação criada com sucesso!");
    }

    public void editarLocacao() {
        persist(LocacaoController.PersistAction.UPDATE, "Locação atualizada com sucesso!");
    }

    public void deletarLocacao() {
        persist(LocacaoController.PersistAction.DELETE, "Locação removida com sucesso!");
    }
}