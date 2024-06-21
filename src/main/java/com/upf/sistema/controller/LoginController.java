/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.upf.sistema.controller;

import com.upf.sistema.entity.UsuarioEntity;
import jakarta.annotation.PostConstruct;
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
        // Simulação de verificação de credenciais (substitua por lógica real de autenticação)
        if ("teste@teste.com".equals(usuario.getEmail()) && "123".equals(usuario.getSenha())) {
            return "/index.xhtml?faces-redirect=true";
        } else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha no Login!", "Email ou senha incorretos!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return null;
        }
    }


}
