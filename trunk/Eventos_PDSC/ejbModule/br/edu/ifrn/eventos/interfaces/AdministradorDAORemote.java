package br.edu.ifrn.eventos.interfaces;

import java.util.List;

import javax.ejb.Remote;

import br.edu.ifrn.eventos.dominio.Atividade;

@Remote
public interface AdministradorDAORemote {
	public List<Atividade> listarAtividades(int id);
}
