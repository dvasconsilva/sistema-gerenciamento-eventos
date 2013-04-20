package br.edu.ifrn.eventos.interfaces;

import java.util.List;

import javax.ejb.Remote;

import br.edu.ifrn.eventos.dominio.AvaliacaoTrabalho;
import br.edu.ifrn.eventos.dominio.Palestrante;
import br.edu.ifrn.eventos.dominio.TipoTrabalho;
import br.edu.ifrn.eventos.dominio.TrabalhoSubmetido;
import br.edu.ifrn.eventos.seguranca.dominio.Papel;
import br.edu.ifrn.eventos.seguranca.dominio.Usuario;

@Remote
public interface PalestranteDAORemote {
	public TrabalhoSubmetido submeterTrabalho(TrabalhoSubmetido trabalho);
	public List<TipoTrabalho> listarTiposTrabalhos();
	public TipoTrabalho listarTipo(int id);
	
	
	public void adicionaPalestrantes(Palestrante palestrante);
	public void removePalestrante(Palestrante palestrante);
	public List<Palestrante> getPalestrantes();
	public List<Palestrante> ListarPalestrantes();
	public List<Palestrante> ListarPalestrantes(int id_trabalho);
	public void updatePalestrante(Palestrante palestrante);
	public List<TrabalhoSubmetido> statusTrabalhos(String usuario);
	
	public void cadastrarAvaliacao(AvaliacaoTrabalho avaliacao);
	public void limparLista();
}
