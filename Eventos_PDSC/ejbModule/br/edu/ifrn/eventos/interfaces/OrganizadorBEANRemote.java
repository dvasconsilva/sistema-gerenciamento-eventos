package br.edu.ifrn.eventos.interfaces;

import java.util.List;

import javax.ejb.Remote;

import br.edu.ifrn.eventos.dominio.Organizador;
import br.edu.ifrn.eventos.dominio.Participante;

@Remote
public interface OrganizadorBEANRemote {
	public List<Participante> getParticipantes(int id_Atividade);
	public void salvarPresenca(Participante participante);
	
	public Organizador buscarOrganizador(String email);

	public void CadastrarOrganizador(Organizador organizador);
	public List<Organizador> ListarOrganizador();
	public void excluirOrganizador(Organizador organizador);
	
	public void alterar(Organizador organizador);
	public boolean verificarOrganizador(String cpf);
}
