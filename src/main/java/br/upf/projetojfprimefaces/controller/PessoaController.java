package br.upf.projetojfprimefaces.controller;

import br.upf.projetojfprimefaces.entity.PessoaEntity;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Named(value = "pessoaController")
@SessionScoped
public class PessoaController implements Serializable {

    @EJB
    private br.upf.projetojfprimefaces.facade.PessoaFacade ejbFacade;

    public PessoaController() {
    }

    private PessoaEntity pessoa = new PessoaEntity();
    private List<PessoaEntity> pessoaList = new ArrayList<>();
    private PessoaEntity selected;

    public PessoaEntity getSelected() {
        return selected;
    }

    public void setSelected(PessoaEntity selected) {
        this.selected = selected;
    }

    public PessoaEntity getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaEntity pessoa) {
        this.pessoa = pessoa;
    }

    public List<PessoaEntity> getPessoaList() {
        return ejbFacade.buscarTodos();
    }

    public void setPessoaList(List<PessoaEntity> pessoaList) {
        this.pessoaList = pessoaList;
    }

    private void exibirMensagem() {
        String msg = "Contato adicionado: " + pessoa.getNome();
        FacesMessage fm = new FacesMessage(msg);
        FacesContext.getCurrentInstance().addMessage(msg, fm);
    }

    protected void initializeEmbeddableKey() {
    }

    public PessoaEntity prepareCreate() {
        selected = new PessoaEntity();
        selected.setDatahorareg(Calendar.getInstance().getTime());
        initializeEmbeddableKey();
        return selected;
    }

    public void cancelar() {
        selected = null;
        pessoa = null;
    }

    public void adicionarPessoa() {
        Date agora = new Date();
        pessoa.setDatahorareg(agora);

        ejbFacade.createReturn(pessoa);
        exibirMensagem();
        pessoa = new PessoaEntity();
    }

    public void editarPessoa() {
        ejbFacade.edit(selected);
        selected = null;
        FacesMessage fm = new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Sucesso!",
                "Registro alterado com sucesso.");
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public void deletarPessoa() {
        ejbFacade.remove(selected);
        selected = null;
        FacesMessage fm = new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Sucesso!",
                "Registro excluído com sucesso.");
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

}
