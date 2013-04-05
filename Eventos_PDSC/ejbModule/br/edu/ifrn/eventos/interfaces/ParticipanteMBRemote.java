package br.edu.ifrn.eventos.interfaces;

import javax.ejb.Remote;

import br.edu.ifrn.eventos.dominio.Participante;

@Remote
public interface ParticipanteMBRemote {
	public Participante getParticipante(String email);
	public void adicionarAtividade(Participante participante);
}
