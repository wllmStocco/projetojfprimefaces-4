/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.upf.sistema.controller;

import com.upf.sistema.entity.VeiculoEntity;
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
        return veiculoList;
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

    // Métodos
    private int gerarId() {
        return nextId++;
    }

    private void exibirMensagem(String summary, String detail) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public void adicionarVeiculo() {
        if (veiculo.getModelo() == null || veiculo.getModelo().isEmpty()) {
            exibirMensagem("Erro", "Modelo do veículo é obrigatório.");
            return;
        }
        try {
            veiculo.setId(gerarId());
            veiculoList.add(veiculo);
            exibirMensagem("Info", "Veículo adicionado: " + veiculo.getModelo());
            veiculo = new VeiculoEntity(); // Resetar o objeto para um novo cadastro
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao adicionar veículo: " + e.getMessage());
        }
    }

    public void editarVeiculo() {
        if (selected == null) {
            exibirMensagem("Erro", "Nenhum veículo selecionado para edição.");
            return;
        }
        try {
            int index = veiculoList.indexOf(selected);
            if (index != -1) {
                veiculoList.set(index, selected);
                exibirMensagem("Sucesso", "Registro alterado com sucesso.");
            } else {
                exibirMensagem("Erro", "Veículo não encontrado para edição.");
            }
            selected = null;
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao editar veículo: " + e.getMessage());
        }
    }

    public void deletarVeiculo() {
        if (selected == null) {
            exibirMensagem("Erro", "Nenhum veículo selecionado para exclusão.");
            return;
        }
        try {
            int index = veiculoList.indexOf(selected);
            if (index != -1) {
                veiculoList.remove(index);
                exibirMensagem("Sucesso", "Registro excluído com sucesso.");
            } else {
                exibirMensagem("Erro", "Veículo não encontrado para exclusão.");
            }
            selected = null;
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao deletar veículo: " + e.getMessage());
        }
    }
}