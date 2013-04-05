package br.edu.ifrn.eventos.interfaces;

import java.util.List;

import javax.ejb.Remote;

import br.edu.ifrn.eventos.dominio.Atividade;

@Remote
public interface AtividadeDAORemote {
	public List<Atividade> getAtividades();
	public Atividade getDetalheAtividade(Atividade atividade);
}
