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
import javax.persistence.metamodel.ListAttribute;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import br.edu.ifrn.eventos.dominio.Atividade;
import br.edu.ifrn.eventos.dominio.Palestrante;
import br.edu.ifrn.eventos.dominio.Participante;
import br.edu.ifrn.eventos.dominio.TipoTrabalho;
import br.edu.ifrn.eventos.interfaces.AtividadeDAORemote;
import br.edu.ifrn.eventos.interfaces.PalestranteDAORemote;
import br.edu.ifrn.eventos.interfaces.ParticipanteMBRemote;
import br.edu.ifrn.eventos.interfaces.TipoTrabalhoDAORemote;
import br.ifrn.eventos.util.Mensagens;

@ManagedBean
@SessionScoped
public class ParticipanteMB {
	
	@EJB
	private AtividadeDAORemote atividadeBEAN;
	
	@EJB
	private ParticipanteMBRemote participanteBEAN;
	
	@EJB 
	private PalestranteDAORemote palestranteBEAN;
	
	@EJB
	private TipoTrabalhoDAORemote tipoTrabalhoBEAN;
	
	private Atividade atividade;
	private List<Atividade> atividadeAdicinada = new ArrayList<Atividade>();
	private List<Palestrante> palestrantes = new ArrayList<Palestrante>();
	

	private int index = 0;
	
	public List<Atividade> getListarAtividades(){
		return this.atividadeBEAN.getAtividades(index + 1);
	}
	
	public String detalhesAtividade(Atividade a){
		atividade = this.atividadeBEAN.getDetalheAtividade(a);
		palestrantes = this.palestranteBEAN.ListarPalestrantes(a.getTrabalho().getId());
		
		System.out.println(palestrantes.size());
		return "/participante/detalhe_atividade.xhtml";
	}

	public String inscreverAtividade(){
		String username = new UsuarioMB().UsuarioLogado();
		Participante participante = this.participanteBEAN.getParticipante(username);
		
		atividadeAdicinada.add(atividade);
		participante.setAtividade(atividadeAdicinada);
		
		this.participanteBEAN.adicionarAtividade(participante);
				
		index = 0;
		
		getListarAtividades();
		
		return "/participante/listar_atividades.xhtml";
	}
	
	public void onTabChange(TabChangeEvent event) {  
		TabView tv = (TabView) event.getComponent();
        this.index = tv.getActiveIndex();
        
        
		
    }  
	
	public List<TipoTrabalho> getTipoTrabalho(){
		return this.tipoTrabalhoBEAN.listarTipoTrabalhos();
	}
	
	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public List<Palestrante> getPalestrantes() {
		return palestrantes;
	}

	public void setPalestrantes(List<Palestrante> palestrantes) {
		this.palestrantes = palestrantes;
	}
	
	

}
