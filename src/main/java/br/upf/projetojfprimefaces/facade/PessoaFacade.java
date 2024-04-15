package br.upf.projetojfprimefaces.facade;

import br.upf.projetojfprimefaces.entity.PessoaEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless           
public class PessoaFacade extends AbstractFacade<PessoaEntity> {

    @PersistenceContext(unitName = "ProjetojfprimefacesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PessoaFacade() {
        super(PessoaEntity.class);
    }

    private List<PessoaEntity> entityList;

    public List<PessoaEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT p FROM PessoaEntity p order by p.nome");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<PessoaEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }

    public PessoaEntity buscarUser(String user) {
        PessoaEntity entityList = null;
        try {
            Query query = getEntityManager().createQuery("SELECT p FROM PessoaEntity p WHERE p.email = :email");
            query.setParameter("email", user);

            entityList = (PessoaEntity) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhum usuário encontrado com o email fornecido.");
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
            e.printStackTrace(); 
        }
        return entityList;
    }
}
