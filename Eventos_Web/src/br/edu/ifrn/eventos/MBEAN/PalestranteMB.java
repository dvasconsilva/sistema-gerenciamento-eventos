package br.edu.ifrn.eventos.MBEAN;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.mail.MessagingException;

import br.edu.ifrn.eventos.dao.PalestranteDAO;
import br.edu.ifrn.eventos.dominio.AvaliacaoTrabalho;
import br.edu.ifrn.eventos.dominio.Palestrante;
import br.edu.ifrn.eventos.dominio.TipoTrabalho;
import br.edu.ifrn.eventos.dominio.TrabalhoSubmetido;
import br.edu.ifrn.eventos.enums.StatusTrabalhoEnum;
import br.edu.ifrn.eventos.interfaces.CadastroUsuarioRemote;
import br.edu.ifrn.eventos.interfaces.PalestranteDAORemote;
import br.edu.ifrn.eventos.interfaces.PermissaoPapelDAORemote;
import br.edu.ifrn.eventos.interfaces.UsuarioDAORemote;
import br.edu.ifrn.eventos.seguranca.dominio.Papel;
import br.edu.ifrn.eventos.seguranca.dominio.Usuario;
import br.ifrn.eventos.util.Mensagens;

@ManagedBean
@SessionScoped
public class PalestranteMB {

	@EJB
	private PalestranteDAORemote palestranteDAO;
	
	@EJB
	private UsuarioDAORemote usuarioDAO;
	
	@EJB
	private CadastroUsuarioRemote cadastroUsuarioDAO;

	@EJB
	private PermissaoPapelDAORemote permissaoBEAN;
	
	private TrabalhoSubmetido trabalho = new TrabalhoSubmetido();
	private TrabalhoSubmetido trabalho2 = new TrabalhoSubmetido();
	private AvaliacaoTrabalho avaliacao = new AvaliacaoTrabalho();
	private TipoTrabalho tipoT = new TipoTrabalho();
	private Usuario usuario = new Usuario();
	private Palestrante palestrante = new Palestrante();
	private List<Palestrante> listaPalestrantes = new ArrayList<Palestrante>();
	

	
	@PostConstruct
	public void inserirUsuarioListaPalestrante(){
	Usuario usuarioLogado = this.usuarioDAO.buscarUsuarioEmail(new UsuarioMB().UsuarioLogado());
	
	 Papel papel = this.permissaoBEAN.buscarPapel("Role_Palestrante");
	this.permissaoBEAN.adicionarPermissao(usuarioLogado, papel);
	
	palestrante.setUsuario(usuarioLogado);
	//this.palestranteDAO.adicionaPalestrantes(palestrante);
	System.out.println("------------------------------- 1");
	palestrantes(palestrante);
	}
	
	public void submeterTrabalho() {
		trabalho.setTipoTrabalho(palestranteDAO.listarTipo(tipoT.getId()));
		trabalho.setPalestrante(this.palestranteDAO.getPalestrantes());
		trabalho.setStatus(StatusTrabalhoEnum.AGUARDANDO);
		trabalho2 = palestranteDAO.submeterTrabalho(trabalho);
		avaliacao.setTrabalho(trabalho2);
		this.palestranteDAO.cadastrarAvaliacao(avaliacao);
		associarTrabalhoPalestrante();
		trabalho = new TrabalhoSubmetido();
		
		new Mensagens().sucesso(FacesContext.getCurrentInstance(), "Trabalho submetido com Sucesso");
		
	}

	//Adiciona palestrantes ao trabalho
	
	public void adicionarPalestrante() throws UnsupportedEncodingException, MessagingException{
		Usuario addUsuario = this.usuarioDAO.buscarUsuario(usuario.getCpf());
		
		//limita a quantidade de palestrantes para 2
		if(listaPalestrantes.size() < 2){
			if(addUsuario != null){
				
				Papel papel = this.permissaoBEAN.buscarPapel("Role_Palestrante");
				this.permissaoBEAN.adicionarPermissao(addUsuario, papel);
				
				palestrante.setUsuario(addUsuario);
				//this.palestranteDAO.adicionaPalestrantes(palestrante); //Associa ao trabalhoa a submeter
				palestrantes(palestrante);
				System.out.println("------------------------------- 2");
				
			}else
				if(addUsuario == null){
					Usuario usuarioNaoCadastrado = new Usuario(usuario.getCpf(), 
						usuario.getNome(), usuario.getEmail(), "eventos", false);
				
					usuarioNaoCadastrado = this.cadastroUsuarioDAO.CadastrarUsuario(usuarioNaoCadastrado);
					Papel papel = this.permissaoBEAN.buscarPapel("Role_Palestrante");
					this.permissaoBEAN.adicionarPermissao(usuarioNaoCadastrado, papel);
					
					palestrante.setUsuario(usuarioNaoCadastrado);
					palestrantes(palestrante);
					System.out.println("------------------------------- 3");
				}	
		}else
			System.out.println("o numero de palestrantes foi excedido");
	}
	
	//remove palestrante da lista
	public void remover(Palestrante palestrante){
		if(palestrante.getUsuario().getEmail() == new UsuarioMB().UsuarioLogado()){
			new Mensagens().erro(FacesContext.getCurrentInstance(), "Este Palestrante nao pode ser removido");
		}else{
		if(!usuarioDAO.verificarUsuarioPalestrante("")){
			
			//verifica se o usuario foi cadastrado ou foi adicionado
			Usuario verificarUsuario = usuarioDAO.usuarioCadastrado("");
			
			//se foi adicionado, então remove dos usuarios do sistema
			if(verificarUsuario != null){
				this.usuarioDAO.removerCPF(verificarUsuario.getCpf());
				removerPalestrante(palestrante);
			}else
				removerPalestrante(palestrante);
		}
		}
	}
	
	//lista palestrantes
	public List<Palestrante>getListarPalestrantes(){
		return listaPalestrantes;
	}
	
	public void palestrantes(Palestrante palestrante){
		System.out.println("??????");
		listaPalestrantes.add(palestrante);
	}
	
	public void removerPalestrante(Palestrante palestrante){
		listaPalestrantes.remove(palestrante);
	}
	
	//busca usuario 
	public void associarTrabalhoPalestrante(){
		//listaPalestrantes = this.palestranteDAO.ListarPalestrantes();
		
		for (Palestrante p : listaPalestrantes) {
			p.setTrabalho(trabalho2);
			this.palestranteDAO.updatePalestrante(p);
		}
	}

	public List<SelectItem> getTipoTrabalho() {
		List<TipoTrabalho> tipos = palestranteDAO.listarTiposTrabalhos();
		List<SelectItem> itens = new ArrayList<SelectItem>(tipos.size());

		for (TipoTrabalho t : tipos) {
			itens.add(new SelectItem(t.getId(), t.getTrabalhoTipo()));
		}

		return itens;
	}

	//lista trabalhos submetidos exibindo seu status
	public List<TrabalhoSubmetido> getStatusTrabalho(){
		return this.palestranteDAO.statusTrabalhos();
	}
	
	public TipoTrabalho getTipoT() {
		return tipoT;
	}

	public void setTipoT(TipoTrabalho tipoT) {
		this.tipoT = tipoT;
	}

	public TrabalhoSubmetido getTrabalho() {
		return trabalho;
	}

	public void setTrabalho(TrabalhoSubmetido trabalho) {
		this.trabalho = trabalho;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
