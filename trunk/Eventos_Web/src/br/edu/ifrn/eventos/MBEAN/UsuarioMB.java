package br.edu.ifrn.eventos.MBEAN;


import java.io.UnsupportedEncodingException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;



import br.edu.ifrn.eventos.dominio.Palestrante;
import br.edu.ifrn.eventos.enums.SexoEnum;
import br.edu.ifrn.eventos.interfaces.CadastroUsuarioRemote;
import br.edu.ifrn.eventos.interfaces.PermissaoPapelDAORemote;
import br.edu.ifrn.eventos.interfaces.UsuarioDAORemote;
import br.edu.ifrn.eventos.seguraca.AutenticacaoHelper;
import br.edu.ifrn.eventos.seguranca.dominio.Papel;
import br.edu.ifrn.eventos.seguranca.dominio.Usuario;
import br.ifrn.eventos.util.Mensagens;

@ManagedBean
@SessionScoped
public class UsuarioMB {

	@EJB
	private CadastroUsuarioRemote cadUsuario;

	@EJB
	private UsuarioDAORemote usuarioMB;
	
	@EJB
	private PermissaoPapelDAORemote permissaoMB;

	private Usuario usuario = new Usuario();
	private String username = "", senha = "", repetirSenha = "";
	private String sexo;
	

	public void cadastrarUsuario() throws MessagingException,
			UnsupportedEncodingException {

			if(!buscaCadastro()){
			if (usuarioMB.buscarUsuario(usuario.getCpf()) == null) 
			{
				usuario.setAtivo(false);
				usuario.setSenha(usuario.getSenha());
				CadastrarUsuarioSistema(usuario, "Role_Participante");
			} else{
				new Mensagens().erro(FacesContext.getCurrentInstance(),
						"O cpf cadastrado");
			}
			usuario = new Usuario();

			}
	}

	//Faz uma busca para verifica se o usuario já se encontra 'pré-cadastrado'
	public boolean buscaCadastro() throws UnsupportedEncodingException, MessagingException{
		Usuario usuarioBusca = this.usuarioMB.buscaUsuarioCadastrado(usuario.getCpf());
		if(usuarioBusca != null){
			
			usuarioBusca.setSenha(usuario.getSenha());
			usuarioBusca.setEmail(usuario.getEmail());
			usuarioBusca.setNome(usuario.getNome());
			usuarioBusca.setAtivo(true);
			
			CadastrarUsuarioSistema(usuarioBusca, "Role_Participante");
			usuario = new Usuario();
			return true;
		}else{
		
			return false;
		}
	}
	
	public void CadastrarUsuarioSistema(Usuario usuario, String papelUsuario) throws UnsupportedEncodingException, MessagingException{
		if (!usuario.getSenha().equals(repetirSenha)) {
			new Mensagens().erro(FacesContext.getCurrentInstance(),
					"Informe a senha corretamente");
		}else{
		
		if (sexo.equals("masculino")) {
			usuario.setSexo(SexoEnum.MASCULINO);
		} else if (sexo.equals("feminino")) {
			usuario.setSexo(SexoEnum.FEMININO);
		}
		
		usuario.setChaveConfirmacao(AutenticacaoHelper.toMD5(usuario
				.getEmail() + usuario.getSenha()));
		
		// cadastra contato do usuario e permissao
		Usuario verificarCadatro = this.cadUsuario.CadastrarUsuario(usuario);
		Papel papel = this.permissaoMB.buscarPapel(papelUsuario);
		
		this.permissaoMB.adicionarPermissao(verificarCadatro, papel);
		
		new Mensagens().sucesso(FacesContext.getCurrentInstance(),
					"Usuario Cadastrado com sucesso");
		}
	}
	//logout do sistema
	public String logout() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(
				false);
		session.invalidate();

		return "Eventos_Web2/protecao/login.xhtml";
	}

	
	//retorna email do usuario logado
	public String UsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		return externalContext.getUserPrincipal() != null ? externalContext
				.getUserPrincipal().toString() : "null";

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getRepetirSenha() {
		return repetirSenha;
	}

	public void setRepetirSenha(String repetirSenha) {
		this.repetirSenha = repetirSenha;
	}

}
