/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.upf.sistema.controller;

import com.upf.sistema.entity.UsuarioEntity;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

/**
 *
 * @author Usuario
 */
@Named(value = "loginController")
@RequestScoped
public class LoginController {

    @EJB com.upf.sistema.facade.UsuarioFacade ejbFacade;
    
    private UsuarioEntity usuario;

    @PostConstruct
    public void init() {
        usuario = new UsuarioEntity();
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
    
    public String validarLogin() {
        UsuarioEntity usuarioDB = ejbFacade.buscarPorEmail(usuario.getEmail(), usuario.getSenha());
        if ((usuarioDB != null && usuarioDB.getId() != 0)) {
            // caso as credenciais forem válidas, então direciona para página index
            return "/index.xhtml?faces-redirect=true";
        } else {
            // senão, exibe uma mensagem de falha...
            FacesMessage fm = new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                "Falha no Login!",
                "Email ou senha incorreto!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return null;
        }
    }

}
