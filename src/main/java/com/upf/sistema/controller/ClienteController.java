/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.upf.sistema.controller;

import com.upf.sistema.entity.ClienteEntity;
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
@Named(value = "clienteController")
@SessionScoped
public class ClienteController implements Serializable {

    private ClienteEntity cliente = new ClienteEntity();
    private List<ClienteEntity> clienteList = new ArrayList<>();
    private ClienteEntity selected;
    private int nextId = 1;

    // Getters and Setters
    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public List<ClienteEntity> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<ClienteEntity> clienteList) {
        this.clienteList = clienteList;
    }

    public ClienteEntity getSelected() {
        return selected;
    }

    public void setSelected(ClienteEntity selected) {
        this.selected = selected;
    }
    
    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    // Methods
    private int gerarId() {
        return nextId++;
    }

    private void exibirMensagem(String summary, String detail) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public void adicionarCliente() {
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            exibirMensagem("Erro", "Nome do cliente é obrigatório.");
            return;
        }
        try {
            cliente.setId(gerarId());
            clienteList.add(cliente);
            exibirMensagem("Info", "Cliente adicionado: " + cliente.getNome());
            cliente = new ClienteEntity(); // Resetar o objeto para um novo cadastro
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao adicionar cliente: " + e.getMessage());
        }
    }

    public void editarCliente() {
        if (selected == null) {
            exibirMensagem("Erro", "Nenhum cliente selecionado para edição.");
            return;
        }
        try {
            int index = clienteList.indexOf(selected);
            if (index != -1) {
                clienteList.set(index, selected);
                exibirMensagem("Sucesso", "Registro alterado com sucesso.");
            } else {
                exibirMensagem("Erro", "Cliente não encontrado para edição.");
            }
            selected = null;
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao editar cliente: " + e.getMessage());
        }
    }

    public void deletarCliente() {
        if (selected == null) {
            exibirMensagem("Erro", "Nenhum cliente selecionado para exclusão.");
            return;
        }
        try {
            int index = clienteList.indexOf(selected);
            if (index != -1) {
                clienteList.remove(index);
                exibirMensagem("Sucesso", "Registro excluído com sucesso.");
            } else {
                exibirMensagem("Erro", "Cliente não encontrado para exclusão.");
            }
            selected = null;
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao deletar cliente: " + e.getMessage());
        }
    }
}