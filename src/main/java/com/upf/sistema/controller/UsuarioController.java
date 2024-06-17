/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.upf.sistema.controller;

import com.upf.sistema.entity.UsuarioEntity;
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
@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    private UsuarioEntity usuario = new UsuarioEntity();
    private List<UsuarioEntity> usuarioList = new ArrayList<>();
    private UsuarioEntity selected;

    // Getters and Setters
    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public List<UsuarioEntity> getUsuarioList() {
        return usuarioList;
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

    // Methods
    private int gerarId() {
        return usuarioList.isEmpty() ? 1 : usuarioList.size() + 1;
    }

    private void exibirMensagem(String summary, String detail) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public void adicionarUsuario() {
        try {
            usuario.setId(gerarId());
            usuarioList.add(usuario);
            exibirMensagem("Info", "Usuário adicionado: " + usuario.getNome());
            usuario = new UsuarioEntity(); // Resetar o objeto para um novo cadastro
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao adicionar usuário: " + e.getMessage());
        }
    }

    public void editarUsuario() {
        try {
            int index = usuarioList.indexOf(selected);
            if (index != -1) {
                usuarioList.set(index, selected);
                exibirMensagem("Sucesso", "Registro alterado com sucesso.");
            } else {
                exibirMensagem("Erro", "Usuário não encontrado para edição.");
            }
            selected = null;
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao editar usuário: " + e.getMessage());
        }
    }

    public void deletarUsuario() {
        try {
            int index = usuarioList.indexOf(selected);
            if (index != -1) {
                usuarioList.remove(index);
                exibirMensagem("Sucesso", "Registro excluído com sucesso.");
            } else {
                exibirMensagem("Erro", "Usuário não encontrado para exclusão.");
            }
            selected = null;
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao deletar usuário: " + e.getMessage());
        }
    }
}