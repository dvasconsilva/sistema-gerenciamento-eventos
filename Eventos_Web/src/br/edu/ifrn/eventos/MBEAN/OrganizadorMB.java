package br.edu.ifrn.eventos.MBEAN;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.MessagingException;

import br.edu.ifrn.eventos.dominio.Atividade;
import br.edu.ifrn.eventos.dominio.Organizador;
import br.edu.ifrn.eventos.interfaces.CadastroUsuarioRemote;
import br.edu.ifrn.eventos.interfaces.OrganizadorDAORemote;
import br.edu.ifrn.eventos.interfaces.PermissaoPapelDAORemote;
import br.edu.ifrn.eventos.interfaces.UsuarioDAORemote;
import br.edu.ifrn.eventos.seguranca.dominio.Papel;
import br.edu.ifrn.eventos.seguranca.dominio.Usuario;

@ManagedBean
@SessionScoped
public class OrganizadorMB {

	@EJB
	private OrganizadorDAORemote organizadorBean;
	
	@EJB
	private CadastroUsuarioRemote usuarioMB;
	
	@EJB
	private UsuarioDAORemote usuarioBEAN;
	
	@EJB
	private PermissaoPapelDAORemote permissaoBEAN;
		
	private UsuarioMB uMB = new UsuarioMB();
	private Atividade atividade = new Atividade();
	private Organizador organizador = new Organizador();
	private Usuario usuario = new Usuario();
	private String filtroBusca = "";
	
	/*
	public List<Atividade> getAtividades() {
		return this.organizadorBean.listarAtividades();
	}*/

	// crud organizador
	//retorna os organizadores cadastrados
	public List<Organizador> getListarOrganizadores() throws IOException {
		return this.organizadorBean.ListarOrganizador();
	}

	public void excluirOrganizador(Organizador organizador) {
		System.out.println("excluir organizador: "
				+ organizador.getUsuario().getNome());
		this.organizadorBean.excluirOrganizador(organizador);

		//try {
			//this.buscar();
		//} catch (IOException exception) {
		//	exception.printStackTrace();
		//}
	}
	
	public String salvar(){
		organizador = this.organizadorBean.buscarOrganizador(uMB.UsuarioLogado());
		
		return "/ListarOrganizador.xhtml";
	}

	
	
	public void cadastrarOrganizador() throws MessagingException, UnsupportedEncodingException {
		Usuario verificarUsuario = this.usuarioBEAN.buscarUsuario(usuario.getCpf());
		
		if(verificarUsuario == null){
			usuario.setSenha("eventos");
			Usuario u  = this.usuarioMB.CadastrarUsuario(usuario);
			Papel papel = this.permissaoBEAN.buscarPapel("Role_Organizador");
			this.permissaoBEAN.adicionarPermissao(u, papel);
			
			organizador.setUsuario(u);
			this.organizadorBean.CadastrarOrganizador(organizador);
		}else{
			Papel papel = this.permissaoBEAN.buscarPapel("Role_Organizador");
			this.permissaoBEAN.adicionarPermissao(verificarUsuario, papel);
			
			organizador.setUsuario(verificarUsuario);
			this.organizadorBean.CadastrarOrganizador(organizador);
		}
	}

	
	public String update() throws UnsupportedEncodingException, MessagingException{
		usuario = this.usuarioMB.AlterarUsuario(usuario);
		organizador.setUsuario(usuario);
		this.organizadorBean.AlterarOrganizador(organizador);
		usuario = new Usuario();
		organizador = new Organizador();
		return "/Listar_Organizador.xhtml";
	}
	
	public String atualizar(Organizador organizador){
		this.usuario = organizador.getUsuario();
		this.organizador = organizador;
		return "editar_organizador.xhtml";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getFiltroBusca() {
		return filtroBusca;
	}

	public void setFiltroBusca(String filtroBusca) {
		this.filtroBusca = filtroBusca;
	}

	public Organizador getOrganizador() {
		return organizador;
	}

	public void setOrganizador(Organizador organizador) {
		this.organizador = organizador;
	}

	
}
