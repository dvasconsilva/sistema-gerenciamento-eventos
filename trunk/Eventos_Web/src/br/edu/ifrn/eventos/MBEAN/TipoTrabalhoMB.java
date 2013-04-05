package br.edu.ifrn.eventos.MBEAN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import br.edu.ifrn.eventos.dominio.TipoTrabalho;
import br.edu.ifrn.eventos.interfaces.TipoTrabalhoDAORemote;

@ManagedBean
@SessionScoped
public class TipoTrabalhoMB {
	@EJB
	private TipoTrabalhoDAORemote tipoTrabalhoRemote;
	

	private TipoTrabalho tipotrabalho = new TipoTrabalho();
	private List<TipoTrabalho> tipostrabalhos = new ArrayList<TipoTrabalho>();

	public TipoTrabalho getTipotrabalho() {
		return tipotrabalho;
	}

	public void setTipotrabalho(TipoTrabalho tipotrabalho) {
		this.tipotrabalho = tipotrabalho;
	}

	public List<TipoTrabalho> getTipostrabalhos() {
		this.tipostrabalhos = this.tipoTrabalhoRemote.listarTipoTrabalhos();
		return tipostrabalhos;
	}

	public void setTipostrabalhos(List<TipoTrabalho> tipostrabalhos) {
		this.tipostrabalhos = tipostrabalhos;
	}

	
	public TipoTrabalhoMB() {
		
	}

	public void buscar() throws IOException {
			this.tipostrabalhos = this.tipoTrabalhoRemote.listarTipoTrabalhos();
	}
	public void excluirTiposTrabalhos(TipoTrabalho tipoTrabalho) {
		this.tipoTrabalhoRemote.excluirTipoTrabalho(tipoTrabalho);

		try {
			this.buscar();
		} catch (IOException exception) {
			FacesContext contex = FacesContext.getCurrentInstance();
			contex.addMessage(null, new FacesMessage(FacesMessage.FACES_MESSAGES,"Tipo de trabalho nao pode ser excluído"));
			exception.printStackTrace();
		}
	}
	public void cadastrarTipoTrabalho() throws MessagingException {
		this.tipoTrabalhoRemote.CadastrarTipoTrabalho(tipotrabalho);
	
		tipotrabalho = new TipoTrabalho();
		FacesContext contex = FacesContext.getCurrentInstance();
		contex.addMessage(null, new FacesMessage(FacesMessage.FACES_MESSAGES,"Avaliador Cadastrado com Sucesso"));
	}
	
	public String update(){
		this.tipoTrabalhoRemote.CadastrarTipoTrabalho(tipotrabalho);
		tipotrabalho = new TipoTrabalho();
		return "/Listar_tipotrabalho.xhtml";
	}
	
	public String atualizar(TipoTrabalho u){
		this.tipotrabalho = u;
		return "editar_tipotrabalho.xhtml";
	}
}
