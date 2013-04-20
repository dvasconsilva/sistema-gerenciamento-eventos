package br.edu.ifrn.eventos.interfaces;

import java.util.List;

import javax.ejb.Remote;

import br.edu.ifrn.eventos.dominio.TipoTrabalho;


@Remote
public interface TipoTrabalhoDAORemote {
	public void CadastrarTipoTrabalho(TipoTrabalho tipoTrabalho);
	public List<TipoTrabalho> listarTipoTrabalhos();
	public void excluirTipoTrabalho(TipoTrabalho tipoTrabalho);
	public void atualizar(TipoTrabalho tipoTrabalho);
	}
