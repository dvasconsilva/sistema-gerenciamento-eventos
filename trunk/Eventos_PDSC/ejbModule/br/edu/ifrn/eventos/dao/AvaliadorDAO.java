package br.edu.ifrn.eventos.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.edu.ifrn.eventos.dominio.AvaliacaoTrabalho;
import br.edu.ifrn.eventos.dominio.Avaliador;
import br.edu.ifrn.eventos.dominio.TrabalhoSubmetido;
import br.edu.ifrn.eventos.interfaces.AvaliadorDAORemote;

/**
 * Session Bean implementation class AvaliadorDAO
 */
@Stateless
@LocalBean
public class AvaliadorDAO implements AvaliadorDAORemote {

	@PersistenceContext
	private EntityManager em;
	List<AvaliacaoTrabalho> listAvaliador = new ArrayList<AvaliacaoTrabalho>();
	
	public AvaliadorDAO() {

	}

	@Override
	public List<TrabalhoSubmetido> listarTrabalhos(int id) {
		return this.em.createQuery(
						"select t from TrabalhoSubmetido t where t.tipoTrabalho.id = " + id,
						TrabalhoSubmetido.class).getResultList();

	}

	@Override
	public TrabalhoSubmetido avaliarTrabalho(int id) {
		System.out.println(id + " ===========================");
		Query query = em.createQuery(
				"select t from TrabalhoSubmetido t where t.id =:id",
				TrabalhoSubmetido.class);
		query.setParameter("id", id);

		return (TrabalhoSubmetido) query.getSingleResult();

	}

	// recupera avaliacao para setar nota do avaliador
	@Override
	public AvaliacaoTrabalho buscaAvaliacao(TrabalhoSubmetido trabalho) {
		Query query = this.em.createNativeQuery(
				"select * from avaliacao where id_trabalho = "
						+ trabalho.getId(), AvaliacaoTrabalho.class);
		return (AvaliacaoTrabalho) query.getSingleResult();
	}

	// buscar avaliador passando usuario -- nao implementado --

	@Override
	public Avaliador buscarAvaliador(String email) {
		Query query = this.em.createQuery("select a from Avaliador a where a.usuario.username = :email ",
		Avaliador.class);
		query.setParameter("email", email);
		return (Avaliador)query.getSingleResult();
	}

	@Override
	public void salvarAvaliacao(AvaliacaoTrabalho avaliacao, Avaliador avaliador) {
		
		System.out.println(avaliador.getUsuario().getNome());
		
		System.out.println(avaliacao.getTrabalho().getTitulo());
		avaliacao = this.em.merge(avaliacao);
		listAvaliador.add(avaliacao);
		avaliador.setAvaliacaoTrabalho(listAvaliador);
		this.em.merge(avaliador);

	}

	@Override
	public void CadastrarAvaliador(Avaliador avaliador) {
		em.persist(avaliador);
	}

	@Override
	public List<Avaliador> ListarAvaliador() {
		return this.em
				.createQuery("select a from Avaliador a", Avaliador.class)
				.getResultList();
	}

	@Override
	public List<Avaliador> ListarUltimos() {
		TypedQuery<Avaliador> query = this.em.createQuery(
				"select x from Avaliador x LIMIT 3", Avaliador.class);
		return query.getResultList();
	}

	@Override
	public void excluirAvaliador(Avaliador avaliador) {
		this.em.remove(em.getReference(Avaliador.class, avaliador.getId()));

	}

	@Override
	public void alterar(Avaliador avaliador) {
		this.em.merge(avaliador);
		
	}

	@Override
	public void AlterarAvaliador(Avaliador avaliador) {
		this.em.merge(avaliador);
		
	}

}
