package br.edu.ifrn.eventos.interfaces;

import javax.ejb.Remote;

import br.edu.ifrn.eventos.dominio.Evento;

@Remote
public interface CadastroEventoRemote {
	public void CadastrarEventos(Evento evento);
	//public List<TipoTrabalho> listarTipoTrabalhos();
	//public void excluirTipoTrabalho(TipoTrabalho tipoTrabalho);
	
}
