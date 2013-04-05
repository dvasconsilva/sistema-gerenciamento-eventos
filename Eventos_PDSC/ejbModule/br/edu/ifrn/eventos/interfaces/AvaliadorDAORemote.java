package br.edu.ifrn.eventos.interfaces;

import java.util.List;

import javax.ejb.Remote;

import br.edu.ifrn.eventos.dominio.AvaliacaoTrabalho;
import br.edu.ifrn.eventos.dominio.Avaliador;
import br.edu.ifrn.eventos.dominio.TrabalhoSubmetido;

@Remote
public interface AvaliadorDAORemote {
	public List<TrabalhoSubmetido> listarTrabalhos(int id);
	public TrabalhoSubmetido avaliarTrabalho(int id);
	public AvaliacaoTrabalho buscaAvaliacao(TrabalhoSubmetido trabalho);
	public void salvarAvaliacao(AvaliacaoTrabalho avaliacao, Avaliador avaliador);
	public Avaliador buscarAvaliador(String email);
	
	public void CadastrarAvaliador(Avaliador avaliador);
	public List<Avaliador> ListarAvaliador();
	public void excluirAvaliador(Avaliador avaliador);
	public List<Avaliador> ListarUltimos();
	public void alterar(Avaliador avaliador);
	void AlterarAvaliador(Avaliador avaliador);
}
