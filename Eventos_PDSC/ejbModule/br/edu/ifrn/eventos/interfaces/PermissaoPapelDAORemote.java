package br.edu.ifrn.eventos.interfaces;

import javax.ejb.Remote;

import br.edu.ifrn.eventos.seguranca.dominio.Papel;
import br.edu.ifrn.eventos.seguranca.dominio.Permissao;
import br.edu.ifrn.eventos.seguranca.dominio.Usuario;

@Remote
public interface PermissaoPapelDAORemote {
	public void adicionarPermissao(Usuario usuario, Papel papel);
	public Papel buscarPapel(String papel);
	public boolean verificarPermissaoUsuario(Usuario usuario, Papel papel);
}
