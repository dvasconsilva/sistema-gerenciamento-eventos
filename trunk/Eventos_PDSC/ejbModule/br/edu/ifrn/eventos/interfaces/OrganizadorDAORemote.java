package br.edu.ifrn.eventos.interfaces;

import java.util.List;

import javax.ejb.Remote;

import br.edu.ifrn.eventos.dominio.Atividade;
import br.edu.ifrn.eventos.dominio.Organizador;

@Remote
public interface OrganizadorDAORemote {

	public List<Atividade> listarAtividades();
	public Organizador buscarOrganizador(String email);

	public void CadastrarOrganizador(Organizador organizador);
	public List<Organizador> ListarOrganizador();
	public void excluirOrganizador(Organizador organizador);
	
	public void alterar(Organizador organizador);
	void AlterarOrganizador(Organizador organizador);
	
}
