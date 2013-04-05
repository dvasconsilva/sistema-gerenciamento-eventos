package br.edu.ifrn.eventos.dao;

import java.io.UnsupportedEncodingException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifrn.eventos.dominio.Evento;
import br.edu.ifrn.eventos.interfaces.CadastroEventoRemote;
import br.edu.ifrn.eventos.seguranca.dominio.Usuario;


/**
 * Session Bean implementation class CadastroEvento
 */
@Stateless
@LocalBean
public class CadastroEvento implements CadastroEventoRemote {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void CadastrarEventos(Evento evento) {
		em.persist(evento);
		
	}
	
	public Evento CadastrarEvento(Evento evento) throws MessagingException{
		evento = em.merge(evento);
		return evento;
	}
	

	
}
