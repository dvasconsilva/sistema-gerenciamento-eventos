package br.edu.ifrn.eventos.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.ifrn.eventos.dominio.Atividade;
import br.edu.ifrn.eventos.dominio.Participante;
import br.edu.ifrn.eventos.interfaces.ParticipanteMBRemote;

/**
 * Session Bean implementation class ParticipanteMB
 * @author ANDRIE
 */
@Stateless
@LocalBean
public class ParticipanteMB implements ParticipanteMBRemote {

    @PersistenceContext
    private EntityManager em;
	
    public ParticipanteMB() {
        
    }

	@Override
	public Participante getParticipante(String email) {
		Query query = this.em.createQuery("select p from Participante p where p.usuario.username =:email", Participante.class);
		query.setParameter("email", email);
		return (Participante) query.getSingleResult();
	}

	@Override
	public void adicionarAtividade(Participante participante) {
		
		this.em.merge(participante);
		
	}

	
}
