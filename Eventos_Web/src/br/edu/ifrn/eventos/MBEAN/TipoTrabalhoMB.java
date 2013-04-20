package br.edu.ifrn.eventos.MBEAN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import org.primefaces.event.RowEditEvent;

import br.edu.ifrn.eventos.dominio.TipoTrabalho;
import br.edu.ifrn.eventos.interfaces.TipoTrabalhoDAORemote;
import br.ifrn.eventos.util.Mensagens;

@ManagedBean
@SessionScoped
public class TipoTrabalhoMB {
	@EJB
	private TipoTrabalhoDAORemote tipoTrabalhoRemote;
	
	private TipoTrabalho tipotrabalho = new TipoTrabalho();
	private List<TipoTrabalho> arrayTipoTrabalho = new ArrayList<TipoTrabalho>();
	

	@PostConstruct
	public void listarTrabalhos(){
		arrayTipoTrabalho = this.tipoTrabalhoRemote.listarTipoTrabalhos();
	}


	public void excluirTiposTrabalhos(TipoTrabalho tipoTrabalho) {
		this.tipoTrabalhoRemote.excluirTipoTrabalho(tipoTrabalho);

		
	}
	public void cadastrarTipoTrabalho() throws MessagingException {
		this.tipoTrabalhoRemote.CadastrarTipoTrabalho(tipotrabalho);
	
		tipotrabalho = new TipoTrabalho();
		FacesContext contex = FacesContext.getCurrentInstance();
		contex.addMessage(null, new FacesMessage(FacesMessage.FACES_MESSAGES,"Avaliador Cadastrado com Sucesso"));
	}
	

	public String atualizar(TipoTrabalho u){
		this.tipotrabalho = u;
		return "editar_tipotrabalho.xhtml";
	}
	
	public void onEdit(RowEditEvent event) {
		this.tipoTrabalhoRemote.atualizar(((TipoTrabalho) event.getObject()));
        System.out.println(">>>>>>>>>>>>>>>>>> " + ((TipoTrabalho) event.getObject()).getId());  
    }  
      
    public void onCancel(RowEditEvent event) {  
        System.out.println("<<<<<<<<<<<<<<<<<<<< Cancelado");
    }  
    
    public TipoTrabalho getTipotrabalho() {
		return tipotrabalho;
	}

	public void setTipotrabalho(TipoTrabalho tipotrabalho) {
		this.tipotrabalho = tipotrabalho;
	}


	public List<TipoTrabalho> getArrayTipoTrabalho() {
		return arrayTipoTrabalho;
	}


	public void setArrayTipoTrabalho(List<TipoTrabalho> arrayTipoTrabalho) {
		this.arrayTipoTrabalho = arrayTipoTrabalho;
	}
}
