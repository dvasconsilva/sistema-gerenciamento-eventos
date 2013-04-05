package br.ifrn.eventos.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagens {
	public void sucesso(FacesContext context, String msg) {
		context.addMessage(null, new FacesMessage(msg, ""));
	}
	
	public void erro(FacesContext context, String msg) {
		context.addMessage(null,  new FacesMessage(FacesMessage.SEVERITY_ERROR,msg, ""));
	}
}
