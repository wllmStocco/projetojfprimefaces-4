/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.upf.sistema.controller;

import com.upf.sistema.entity.UsuarioEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Usuario
 */
@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    @EJB
    private com.upf.sistema.facade.UsuarioFacade ejbFacade;
    
    private UsuarioEntity usuario = new UsuarioEntity();
    private List<UsuarioEntity> usuarioList = new ArrayList<>();
    private UsuarioEntity selected;
    private int nextId = 1;

    // Getters and Setters
    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public List<UsuarioEntity> getUsuarioList() {
        return ejbFacade.buscarTodos();
    }

    public void setUsuarioList(List<UsuarioEntity> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public UsuarioEntity getSelected() {
        return selected;
    }

    public void setSelected(UsuarioEntity selected) {
        this.selected = selected;
    }
    
    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public UsuarioEntity prepareAdicionar() {
        usuario = new UsuarioEntity();
        return usuario;
    }
    
    private void addErrorMessage(String msg) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
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
                    ejbFacade.createReturn(usuario);
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
    
    public void adicionarUsuario() {
        Date datahoraAtual = new Timestamp(System.currentTimeMillis());
        usuario.setDatahorareg(datahoraAtual);
        persist( UsuarioController.PersistAction.CREATE,
                 "Registro alterado com sucesso!");
    }

    public void editarUsuario() {
        persist( UsuarioController.PersistAction.UPDATE,
                 "Registro alterado com sucesso!");
    }

    public void deletarUsuario() {
        persist( UsuarioController.PersistAction.DELETE,
                 "Registro alterado com sucesso!");
    }
}