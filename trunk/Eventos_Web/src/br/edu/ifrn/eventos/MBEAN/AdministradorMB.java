package br.edu.ifrn.eventos.MBEAN;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;

import br.edu.ifrn.eventos.dominio.Atividade;
import br.edu.ifrn.eventos.dominio.TipoTrabalho;
import br.edu.ifrn.eventos.interfaces.AdministradorDAORemote;
import br.edu.ifrn.eventos.interfaces.AtividadeDAORemote;
import br.edu.ifrn.eventos.interfaces.TipoTrabalhoDAORemote;

@ManagedBean
@SessionScoped
public class AdministradorMB {

	@EJB
	private AdministradorDAORemote adminBean;
	private int index;

	@EJB
	private TipoTrabalhoDAORemote tipoTrabalhoBEAN;

	@EJB
	private AtividadeDAORemote atividadeBean;
	
	private List<Atividade> atividades = new ArrayList<Atividade>();
	
	public void getTrabalhos() {
		atividades = this.atividadeBean.getAtividades();
		
		
	}
	
	public String horarioLocal(){
		getTrabalhos();
		return "/administrador/horario_local_atividade.xhtml";
	}
	
	public String salvar(){
		for (Atividade a : atividades) {
			this.atividadeBean.editarAtividade(a);
			System.out.println(a.getId());
		}
		return "/administracao.xhtml";
	}
	
	public List<TipoTrabalho> getTipoTrabalho() {
		return this.tipoTrabalhoBEAN.listarTipoTrabalhos();
	}

	

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

}
