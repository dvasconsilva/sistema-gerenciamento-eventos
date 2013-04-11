package br.edu.ifrn.eventos.seguranca.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.ifrn.eventos.interfaces.PermissaoPapelDAORemote;
import br.edu.ifrn.eventos.seguranca.dominio.Papel;
import br.edu.ifrn.eventos.seguranca.dominio.Permissao;
import br.edu.ifrn.eventos.seguranca.dominio.Usuario;

/**
 * Session Bean implementation class PermissaoPapelDAO
 * 
 * @author ANDRIE
 */
@Stateless
@LocalBean
public class PermissaoPapelDAO implements PermissaoPapelDAORemote {

	@PersistenceContext
	private EntityManager em;
	
	public PermissaoPapelDAO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void adicionarPermissao(Usuario usuario, Papel papel) {
		Permissao p = new Permissao();
		p.setUsuario(usuario);
		p.setPapel(papel);
		em.persist(p);
		
		p = null;

	}

	@Override
	public Papel buscarPapel(String papel) {

		Query query = em.createQuery(
				"select p from Papel p where p.role = :papel", Papel.class);
		query.setParameter("papel", papel);
		return (Papel) query.getSingleResult();

	}
	
	@Override
	public Permissao verificarPermissaoUsuario(Usuario usuario, Papel papel) {
		try{
			
			Query query = this.em.createQuery("select p from Permissao p where p.usuario.id = :id_usuario and " +
					"p.papel.id = :id_papel", Usuario.class);
			
			query.setParameter("id_usuario", usuario.getId());
			query.setParameter("id_papel", papel.getId());
			
			return (Permissao)query.getSingleResult();
		}catch (Exception e){
			return null;
		}
	}

}
