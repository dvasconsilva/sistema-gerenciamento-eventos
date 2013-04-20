package br.edu.ifrn.eventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifrn.eventos.dominio.TipoTrabalho;
import br.edu.ifrn.eventos.interfaces.TipoTrabalhoDAORemote;

@Stateless
public class TipoTrabalhoDAO implements TipoTrabalhoDAORemote{
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public void CadastrarTipoTrabalho(TipoTrabalho tipoTrabalho){
		em.persist(tipoTrabalho);
	}

	@Override
	public List<TipoTrabalho> listarTipoTrabalhos() {
		return  this.em.createQuery("select t from TipoTrabalho t", TipoTrabalho.class).getResultList();
	}


	@Override
	public void excluirTipoTrabalho(TipoTrabalho tipoTrabalho) {
		this.em.remove(em.getReference(TipoTrabalho.class,
				tipoTrabalho.getId()));
		
	}

	@Override
	public void atualizar(TipoTrabalho tipoTrabalho) {
		
		System.out.println(tipoTrabalho.getTrabalhoTipo());
		this.em.merge(tipoTrabalho);
		
	}

}
