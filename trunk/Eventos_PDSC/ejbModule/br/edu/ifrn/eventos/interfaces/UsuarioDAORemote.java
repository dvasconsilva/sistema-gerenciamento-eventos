package br.edu.ifrn.eventos.interfaces;

import javax.ejb.Remote;

import br.edu.ifrn.eventos.seguranca.dominio.Usuario;

@Remote
public interface UsuarioDAORemote {
	public Usuario buscarUsuario(String cpf);
	public Usuario buscarUsuarioEmail(String email);
	public void remover(Usuario usuario);
	public void removerCPF(String cpf);
	public boolean verificarUsuarioPalestrante(String cpf);
	public Usuario usuarioCadastrado(String cpf);
	public Usuario buscaUsuarioCadastrado(String email);
}
