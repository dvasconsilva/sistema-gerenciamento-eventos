package br.edu.ifrn.eventos.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.ifrn.eventos.dominio.Atividade;
import br.edu.ifrn.eventos.interfaces.AtividadeDAORemote;

/**
 * Session Bean implementation class AtividadeDAO
 */
@Stateless
@LocalBean
public class AtividadeDAO implements AtividadeDAORemote {

    @PersistenceContext
    private EntityManager em;
	
    public AtividadeDAO() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<Atividade> getAtividades() {
		return this.em.createQuery("Select a from Atividade a", Atividade.class).getResultList();
	}

	@Override
	public Atividade getDetalheAtividade(Atividade atividade) {
		Query query = this.em.createQuery("select a from Atividade a where a.id = :id", Atividade.class);
		query.setParameter("id", atividade.getId());
		
		return (Atividade) query.getSingleResult();
	}

	@Override
	public void editarAtividade(Atividade atividade) {
		em.merge(atividade);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Atividade> getAtividades(int id) {
		
	Query query = this.em.createNativeQuery("select a.id, a.data, a.horariofim, a.horarioinicio, " +
			"a.local, a.id_evento, a.trabalho_id from atividade a, trabalhosubmetido t " +
			"where a.trabalho_id = t.id and t.id_tipoTrabalho = :id", Atividade.class);
	
	   return query.setParameter("id", id).getResultList();
	}

	@Override
	public Atividade getAtividade(int id) {
		Query query = this.em.createQuery("Select a from Atividade a where a.id = :id", Atividade.class);
		return (Atividade) query.setParameter("id", id).getSingleResult();
	}

	

}
