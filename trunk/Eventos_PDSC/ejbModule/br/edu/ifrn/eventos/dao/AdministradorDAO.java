package br.edu.ifrn.eventos.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifrn.eventos.dominio.Atividade;
import br.edu.ifrn.eventos.dominio.TrabalhoSubmetido;
import br.edu.ifrn.eventos.interfaces.AdministradorDAORemote;

/**
 * Session Bean implementation class AdministradorDAO
 * @author ANDRIE
 */
@Stateless
@LocalBean
public class AdministradorDAO implements AdministradorDAORemote {

    @PersistenceContext
    private EntityManager em;
	
    public AdministradorDAO() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<Atividade> listarAtividades(int id) {
		
		return this.em.createQuery(
				"select a from Atividade a where a.trabalho.tipoTrabalho.id = " + id,
				Atividade.class).getResultList();
		
	}

}
