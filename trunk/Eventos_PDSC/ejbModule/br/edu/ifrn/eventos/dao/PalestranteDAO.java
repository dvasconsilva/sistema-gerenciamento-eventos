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


/**
 * Session Bean implementation class PalestranteDAO
 */
@Stateless
public class PalestranteDAO implements PalestranteDAORemote {

	
	@PersistenceContext
	private EntityManager em;
	private List<Palestrante> palestrantes = new ArrayList<Palestrante>();
	
	
	

	@Override
	public TrabalhoSubmetido submeterTrabalho(TrabalhoSubmetido trabalho) {
		return em.merge(trabalho);
	}

	@Override
	public List<TipoTrabalho> listarTiposTrabalhos() {
		return  this.em.createQuery("select t from TipoTrabalho t", TipoTrabalho.class).getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Palestrante> ListarPalestrantes() {
		return em.createNativeQuery("select * from palestrante " +
				"where coalesce(text(id_trabalho),'blabla') = 'blabla'", Palestrante.class).getResultList();
	}    
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TrabalhoSubmetido> statusTrabalhos() {
		Query query = em.createNativeQuery("select t.id, t.titulo, t.status, t.descricao, " +
				"t.qtdparticipantes, t.id_tipoTrabalho from trabalhosubmetido as t, " +
				"palestrante as p, usuario as u where t.id = p.id_trabalho and p.usuario_id = u.id and " +
				"u.email = 'andrie.anderson1@gmail.com'", TrabalhoSubmetido.class);
		
		return query.getResultList();
		
	}
	
	@Override
	public TipoTrabalho listarTipo(int id){
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
		palestrantes.add(palestrante);
	}

	@Override
	public void updatePalestrante(Palestrante palestrante) {
		em.merge(palestrante);
		
	}

	@Override
	public void cadastrarAvaliacao(AvaliacaoTrabalho avaliacao) {
		em.persist(avaliacao);
		
	}
	
}
