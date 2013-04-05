package br.edu.ifrn.eventos.seguranca.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.validator.constraints.Email;

import br.edu.ifrn.eventos.interfaces.UsuarioDAORemote;
import br.edu.ifrn.eventos.seguraca.AutenticacaoHelper;
import br.edu.ifrn.eventos.seguranca.dominio.Usuario;

@Stateless
public class UsuarioDAO implements UsuarioDAORemote {

	@PersistenceContext
	private EntityManager em;

	// Busca usuario pelo cpf
	@Override
	public Usuario buscarUsuario(String cpf) {
		try {
			Query query = em.createQuery(
					"select u from Usuario u where u.cpf=:cpf", Usuario.class);
			return (Usuario) query.setParameter("cpf", cpf).getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}
	
	// Busca usuario pelo email
		@Override
		public Usuario buscarUsuarioEmail(String email) {
			try {
				Query query = em.createQuery(
						"select u from Usuario u where u.username=:email", Usuario.class);
				return (Usuario) query.setParameter("email", email).getSingleResult();
			} catch (Exception e) {
				return null;
			}

		}

	@Override
	public void remover(Usuario usuario) {
		this.em.remove(usuario);
		
	}

	@Override
	public boolean verificarUsuarioPalestrante(String cpf) {
		
		return false;
	}

	@Override
	public Usuario usuarioCadastrado(String cpf) {
		try{
			return this.em.createQuery("select u from Usuario u where " +
					"u.cpf = '445.444.675-09' and u.ativo = false", Usuario.class).getSingleResult();
		}catch(Exception e){
			return null;
		}
		
	}

	@Override
	public void removerCPF(String cpf) {
		Query query = this.em.createQuery("DELETE FROM Usuario u WHERE u.cpf =:cpf");
		query.setParameter("cpf", cpf);
		query.executeUpdate();
	}

	@Override
	public Usuario buscaUsuarioCadastrado(String cpf) {
		try{
			Query query = this.em.createQuery("SELECT u FROM Usuario u WHERE u.cpf=:cpf" +
					" and u.password = :senha", Usuario.class);
			query.setParameter("cpf", cpf);
			query.setParameter("senha", "eventos");
			return (Usuario) query.getSingleResult();
		}catch(Exception e){
			return null;
		}
		
	}
}
