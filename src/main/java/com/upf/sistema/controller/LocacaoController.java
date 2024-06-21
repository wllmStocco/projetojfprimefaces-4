package com.upf.sistema.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */

import com.upf.sistema.entity.LocacaoEntity;
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
        return locacaoList;
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

    // Métodos Auxiliares
    private int gerarId() {
        return nextId++;
    }

    private void exibirMensagem(String summary, String detail) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    // Métodos de Ação
    public void adicionarLocacao() {
        if (locacao.getCliente() == null || locacao.getVeiculo() == null || locacao.getDataInicio() == null || locacao.getDataFim() == null) {
            exibirMensagem("Erro", "Todos os campos são obrigatórios.");
            return;
        }
        try {
            locacao.setId(gerarId());
            locacaoList.add(locacao);
            exibirMensagem("Info", "Locação adicionada: ID " + locacao.getId());
            locacao = new LocacaoEntity(); // Resetar o objeto para um novo cadastro
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao adicionar locação: " + e.getMessage());
        }
    }

    public void editarLocacao() {
        if (selected == null) {
            exibirMensagem("Erro", "Nenhuma locação selecionada para edição.");
            return;
        }
        try {
            int index = locacaoList.indexOf(selected);
            if (index != -1) {
                locacaoList.set(index, selected);
                exibirMensagem("Sucesso", "Locação alterada com sucesso.");
            } else {
                exibirMensagem("Erro", "Locação não encontrada para edição.");
            }
            selected = null;
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao editar locação: " + e.getMessage());
        }
    }

    public void deletarLocacao() {
        if (selected == null) {
            exibirMensagem("Erro", "Nenhuma locação selecionada para exclusão.");
            return;
        }
        try {
            int index = locacaoList.indexOf(selected);
            if (index != -1) {
                locacaoList.remove(index);
                exibirMensagem("Sucesso", "Locação excluída com sucesso.");
            } else {
                exibirMensagem("Erro", "Locação não encontrada para exclusão.");
            }
            selected = null;
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao deletar locação: " + e.getMessage());
        }
    }
}