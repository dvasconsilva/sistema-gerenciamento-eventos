package br.edu.ifrn.eventos.MBEAN;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;

import br.edu.ifrn.eventos.dominio.Atividade;
import br.edu.ifrn.eventos.dominio.Participante;
import br.edu.ifrn.eventos.interfaces.AtividadeDAORemote;
import br.edu.ifrn.eventos.interfaces.ParticipanteMBRemote;
import br.ifrn.eventos.util.Mensagens;

@ManagedBean
@SessionScoped
public class ParticipanteMB {
	
	@EJB
	private AtividadeDAORemote atividadeBEAN;
	
	@EJB
	private ParticipanteMBRemote participanteBEAN;
	
	private Atividade atividade;
	private List<Atividade> atividadeAdicinada = new ArrayList<Atividade>();
	
	public List<Atividade> getListarAtividades(){
		return this.atividadeBEAN.getAtividades();
	}
	
	public String detalhesAtividade(Atividade a){
		atividade = this.atividadeBEAN.getDetalheAtividade(a);
		return "/participante/detalhe_atividade.xhtml";
	}

	public String inscreverAtividade(){
		String username = new UsuarioMB().UsuarioLogado();
		Participante participante = this.participanteBEAN.getParticipante(username);
		
		atividadeAdicinada.add(atividade);
		participante.setAtividade(atividadeAdicinada);
		
		this.participanteBEAN.adicionarAtividade(participante);
		
		return "/participante/listar_atividades.xhtml";
	}
	
	
	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	
	

}
