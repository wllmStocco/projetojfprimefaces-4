/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.upf.sistema.controller;

import com.upf.sistema.entity.VeiculoEntity;
import jakarta.inject.Named;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
@Named(value = "veiculoController")
@Dependent
public class VeiculoController {

    /**
     * Creates a new instance of VeiculoController
     */
    public VeiculoController() {
    }
    
    private VeiculoEntity veiculo = new VeiculoEntity();
    private List<VeiculoEntity> veiculoList = new ArrayList<>();
    private VeiculoEntity selected;

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
    
    private void exibirMensagem(String summary, String detail) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    private int gerarId() {
        int id = 1;
        if (!veiculoList.isEmpty()) {
            id = veiculoList.size() + 1;
        }
        return id;
    }
    
    public void adicionarVeiculo() {
        try {
            veiculo.setId(gerarId());
            veiculoList.add(veiculo);
            exibirMensagem("Info", "Veiculo adicionado: " + veiculo.getModelo());
            veiculo = new VeiculoEntity(); // Resetar o objeto para um novo cadastro
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao adicionar Veiculo: " + e.getMessage());
        }
    }

    public void editarVeiculo() {
        try {
            int index = veiculoList.indexOf(selected);
            if (index != -1) {
                veiculoList.set(index, selected);
                exibirMensagem("Sucesso", "Registro alterado com sucesso.");
            } else {
                exibirMensagem("Erro", "Veiculo não encontrado para edição.");
            }
            selected = null;
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao editar usuário: " + e.getMessage());
        }
    }

    public void deletarVeiculo() {
        try {
            int index = veiculoList.indexOf(selected);
            if (index != -1) {
                veiculoList.remove(index);
                exibirMensagem("Sucesso", "Registro excluído com sucesso.");
            } else {
                exibirMensagem("Erro", "Veiculo não encontrado para exclusão.");
            }
            selected = null;
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao deletar usuário: " + e.getMessage());
        }
    }
}
