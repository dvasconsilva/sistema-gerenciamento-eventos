package br.edu.ifrn.eventos.dao;

import java.util.List;

import br.edu.ifrn.eventos.dominio.Atividade;
import br.edu.ifrn.eventos.dominio.Organizador;
import br.edu.ifrn.eventos.interfaces.OrganizadorDAORemote;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class OrganizadorDAO
 */
@Stateless
@LocalBean
public class OrganizadorDAO implements OrganizadorDAORemote {

	@PersistenceContext
	private EntityManager em;

    public OrganizadorDAO() {
    }

	@Override
	public List<Atividade> listarAtividades() {
		return this.em.createQuery(
				"select at from Atividade at",
				Atividade.class).getResultList();
	}

	@Override
	public Organizador buscarOrganizador(String email) {
		Query query = this.em.createQuery("select o from Organizador o where o.usuario.username = :email ",
				Organizador.class);
				query.setParameter("email", email);
				return (Organizador)query.getSingleResult();
	}

	@Override
	public void CadastrarOrganizador(Organizador organizador) {
		em.persist(organizador);
	}

	@Override
	public List<Organizador> ListarOrganizador() {
		return this.em
				.createQuery("select o from Organizador o", Organizador.class)
				.getResultList();
	}

	@Override
	public void excluirOrganizador(Organizador organizador) {
		this.em.remove(em.getReference(Organizador.class, organizador.getId()));		
	}

	@Override
	public void alterar(Organizador organizador) {
		this.em.merge(organizador);		
	}

	@Override
	public void AlterarOrganizador(Organizador organizador) {
		this.em.merge(organizador);		
	}

}
