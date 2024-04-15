package br.upf.projetojfprimefaces.controller;

import br.upf.projetojfprimefaces.entity.PessoaEntity;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    public LoginController() {
    }

    @EJB
    private br.upf.projetojfprimefaces.facade.PessoaFacade ejbFacade;

    private PessoaEntity pessoa;
    private String user;
    public void prepareAutenticarPessoa() {
        pessoa = new PessoaEntity();
    }

    @PostConstruct
    public void init() {
        prepareAutenticarPessoa();
    }

    public String validarLogin() {

        PessoaEntity usuario;

        usuario = ejbFacade.buscarUser(user);

        if (autenticarPessoaSenha(pessoa.getSenha(), usuario.getSenha())) {

            return "/index.xhtml?faces-redirect=true";
        } else {
            FacesMessage fm = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Falha no Login!",
                    "Email ou senha incorreto!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return null;
        }
    }

    public boolean autenticarPessoaSenha(String senha, String senhaDB) {
        boolean autenticacaoValida = false;

        if (senha.equals(senhaDB)) {
            autenticacaoValida = true;
        }
        return autenticacaoValida;
    }

    public PessoaEntity getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaEntity pessoa) {
        this.pessoa = pessoa;
    }

    public br.upf.projetojfprimefaces.facade.PessoaFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(br.upf.projetojfprimefaces.facade.PessoaFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
