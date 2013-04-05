package br.edu.ifrn.eventos.interfaces;

import java.io.UnsupportedEncodingException;

import javax.ejb.Remote;
import javax.mail.MessagingException;

import br.edu.ifrn.eventos.seguranca.dominio.Papel;
import br.edu.ifrn.eventos.seguranca.dominio.Usuario;

@Remote
public interface CadastroUsuarioRemote {
	public Usuario CadastrarUsuario(Usuario usuario) throws MessagingException, UnsupportedEncodingException;
	public Usuario AlterarUsuario(Usuario usuario) throws MessagingException,UnsupportedEncodingException;
}
