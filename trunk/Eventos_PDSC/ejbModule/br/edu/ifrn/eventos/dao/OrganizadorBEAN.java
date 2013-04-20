package br.edu.ifrn.eventos.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.ifrn.eventos.dominio.Organizador;
import br.edu.ifrn.eventos.dominio.Participante;
import br.edu.ifrn.eventos.interfaces.OrganizadorBEANRemote;

/**
 * Session Bean implementation class OrganizadorBEAN
 * 
 * @author ANDRIE
 */
@Stateless
@LocalBean
public class OrganizadorBEAN implements OrganizadorBEANRemote {

	@PersistenceContext
	private EntityManager em;

	public OrganizadorBEAN() {

	}

	@Override
	public List<Participante> getParticipantes(int id_Atividade) {

		return this.em.createQuery("select p from Participante p",
				Participante.class).getResultList();
	}

	@Override
	public void salvarPresenca(Participante participante) {
		this.em.merge(participante);

	}

	@Override
	public Organizador buscarOrganizador(String email) {
		Query query = this.em
				.createQuery(
						"select o from Organizador o where o.usuario.username = :email ",
						Organizador.class);
		query.setParameter("email", email);
		return (Organizador) query.getSingleResult();
	}

	@Override
	public void CadastrarOrganizador(Organizador organizador) {
		em.persist(organizador);
	}

	@Override
	public List<Organizador> ListarOrganizador() {
		return this.em.createQuery("select o from Organizador o",
				Organizador.class).getResultList();
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
	public boolean verificarOrganizador(String cpf) {
		try {
			Query query = this.em.createQuery(
					"select o from Organizador o where o.usuario.cpf = :cpf",
					Organizador.class);
			query.setParameter("cpf", cpf).getSingleResult();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
