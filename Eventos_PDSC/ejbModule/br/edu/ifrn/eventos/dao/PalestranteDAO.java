package br.edu.ifrn.eventos.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.ifrn.eventos.dominio.AvaliacaoTrabalho;
import br.edu.ifrn.eventos.dominio.Palestrante;
import br.edu.ifrn.eventos.dominio.TipoTrabalho;
import br.edu.ifrn.eventos.dominio.TrabalhoSubmetido;
import br.edu.ifrn.eventos.interfaces.PalestranteDAORemote;
import br.edu.ifrn.eventos.seguranca.dominio.Papel;
import br.edu.ifrn.eventos.seguranca.dominio.Permissao;
import br.edu.ifrn.eventos.seguranca.dominio.Usuario;

/**
 * Session Bean implementation class PalestranteDAO
 */
@Stateless
public class PalestranteDAO implements PalestranteDAORemote {

	@PersistenceContext
	private EntityManager em;
	private List<Palestrante> palestrantes = null;
	
	public PalestranteDAO(){
		System.out.println("Construido");
		palestrantes = new ArrayList<Palestrante>();
	}

	@Override
	public TrabalhoSubmetido submeterTrabalho(TrabalhoSubmetido trabalho) {
		return em.merge(trabalho);
	}

	@Override
	public List<TipoTrabalho> listarTiposTrabalhos() {
		return this.em.createQuery("select t from TipoTrabalho t",
				TipoTrabalho.class).getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Palestrante> ListarPalestrantes() {
		return em
				.createNativeQuery(
						"select * from palestrante "
								+ "where coalesce(text(id_trabalho),'blabla') = 'blabla'",
						Palestrante.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrabalhoSubmetido> statusTrabalhos(String usuario) {
		Query query = em
				.createNativeQuery(
						"select t.id, t.titulo, t.status, t.descricao, "
								+ "t.qtdparticipantes, t.id_tipoTrabalho from trabalhosubmetido as t, "
								+ "palestrante as p, usuario as u where t.id = p.id_trabalho and p.usuario_id = u.id and "
								+ "u.username = :email",
						TrabalhoSubmetido.class);

		query.setParameter("email", usuario);
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Palestrante> ListarPalestrantes(int id_trabalho) {
		Query query = this.em.createQuery(
				"select p from Palestrante p where p.trabalho.id = :id",
				Palestrante.class);

		return query.setParameter("id", id_trabalho).getResultList();
	}

	@Override
	public TipoTrabalho listarTipo(int id) {
		return em.find(TipoTrabalho.class, id);
	}

	@Override
	public void removePalestrante(Palestrante palestrante) {
		this.palestrantes.remove(palestrante);

	}

	@Override
	public List<Palestrante> getPalestrantes() {

		return palestrantes;
	}

	@Override
	public void adicionaPalestrantes(Palestrante palestrante) {
		System.out.println("Antes " +palestrantes.size());
		palestrantes.add(palestrante);
		
		System.out.println("Antes " +palestrantes.size());
	}

	@Override
	public void updatePalestrante(Palestrante palestrante) {
		em.merge(palestrante);

	}

	@Override
	public void cadastrarAvaliacao(AvaliacaoTrabalho avaliacao) {
		em.persist(avaliacao);

	}

	@Override
	public void limparLista() {
		System.out.println("limpo");
		palestrantes = new ArrayList<Palestrante>();
		System.out.println(palestrantes.size());
		
	}

}
