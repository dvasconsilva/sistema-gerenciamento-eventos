package br.edu.ifrn.eventos.MBEAN;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;


import br.edu.ifrn.eventos.dominio.AvaliacaoTrabalho;
import br.edu.ifrn.eventos.dominio.Avaliador;
import br.edu.ifrn.eventos.dominio.TipoTrabalho;
import br.edu.ifrn.eventos.dominio.TrabalhoSubmetido;
import br.edu.ifrn.eventos.interfaces.AvaliadorDAORemote;
import br.edu.ifrn.eventos.interfaces.CadastroUsuarioRemote;
import br.edu.ifrn.eventos.interfaces.PermissaoPapelDAORemote;
import br.edu.ifrn.eventos.interfaces.TipoTrabalhoDAORemote;
import br.edu.ifrn.eventos.interfaces.UsuarioDAORemote;
import br.edu.ifrn.eventos.seguraca.AutenticacaoHelper;
import br.edu.ifrn.eventos.seguranca.dominio.Papel;
import br.edu.ifrn.eventos.seguranca.dominio.Usuario;
import br.ifrn.eventos.util.Mensagens;

@ManagedBean
@SessionScoped
public class AvaliadorMB {

	@EJB
	private AvaliadorDAORemote avaliadorBean;
	
	@EJB
	private CadastroUsuarioRemote usuarioMB;
	
	@EJB
	private UsuarioDAORemote usuarioBEAN;
	
	@EJB
	private PermissaoPapelDAORemote permissaoBEAN;
	
	@EJB
	private TipoTrabalhoDAORemote tipoTrabalhoBEAN;
	
	private Integer rating, rating2, rating3, rating4;
	
	private UsuarioMB uMB = new UsuarioMB();
	private TrabalhoSubmetido trabalho = new TrabalhoSubmetido();
	private AvaliacaoTrabalho avaliacao = new AvaliacaoTrabalho();
	private Avaliador avaliador = new Avaliador();
	private Usuario usuario = new Usuario();
	private String filtroBusca = "";
	private int index = 0;

	public List<TrabalhoSubmetido> getTrabalhos() {
		return this.avaliadorBean.listarTrabalhos(index + 1);
	}

	public String avaliarTrabalho(int id) {
		trabalho = this.avaliadorBean.avaliarTrabalho(id);
		System.out.println(trabalho.getDescricao());
		return "/avaliador/avaliar_trabalho.xhtml";
	}

	public String salvar() {
		avaliacao = this.avaliadorBean.buscaAvaliacao(trabalho);

		this.avaliacao.setNotaFinal(avaliacao.getNotaFinal() + calcularMedia()); // insere a media
		avaliador = this.avaliadorBean.buscarAvaliador(uMB.UsuarioLogado());
		
		this.avaliadorBean.salvarAvaliacao(avaliacao, avaliador);
		
		new Mensagens().sucesso(FacesContext.getCurrentInstance(), "Trabalhos Avaliado com sucesso");
		return "/avaliador/listar_trabalhos.xhtml";
	}

	public Double calcularMedia() {
		System.out.println(String.valueOf(((rating + rating2 + rating3 + rating4) / 4)));
		System.out.println(((rating + rating2 + rating3 + rating4) / 4));
		
		return Double.parseDouble(String.valueOf(((rating + rating2 + rating3 + rating4) / 4)));
	}

	// crud avaliador
	//retorna os avaliadores cadastrados
	public List<Avaliador> getListarAvaliadores() throws IOException {
		return this.avaliadorBean.ListarAvaliador();
	}

	public void excluirAvaliador(Avaliador avaliador) {
		System.out.println("excluir avaliador: "
				+ avaliador.getUsuario().getNome());
		this.avaliadorBean.excluirAvaliador(avaliador);

		//try {
			//this.buscar();
		//} catch (IOException exception) {
		//	exception.printStackTrace();
		//}
	}

	public void cadastrarAvaliador() throws MessagingException, UnsupportedEncodingException {
		Usuario verificarUsuario = this.usuarioBEAN.buscarUsuario(usuario.getCpf());
		
		if(verificarUsuario == null){
			usuario.setSenha("eventos");
			Usuario u  = this.usuarioMB.CadastrarUsuario(usuario);
			Papel papel = this.permissaoBEAN.buscarPapel("Role_Avaliador");
			this.permissaoBEAN.adicionarPermissao(u, papel);
			
			avaliador.setUsuario(u);
			this.avaliadorBean.CadastrarAvaliador(avaliador);
		}else{
			Papel papel = this.permissaoBEAN.buscarPapel("Role_Avaliador");
			this.permissaoBEAN.adicionarPermissao(verificarUsuario, papel);
			
			avaliador.setUsuario(verificarUsuario);
			this.avaliadorBean.CadastrarAvaliador(avaliador);
		}
	}

	
	public String update() throws UnsupportedEncodingException, MessagingException{
		usuario = this.usuarioMB.AlterarUsuario(usuario);
		avaliador.setUsuario(usuario);
		this.avaliadorBean.AlterarAvaliador(avaliador);
		usuario = new Usuario();
		avaliador = new Avaliador();
		return "/Listar_Avaliador.xhtml";
	}
	
	public void onTabChange(TabChangeEvent event) {  
		TabView tv = (TabView) event.getComponent();
        this.index = tv.getActiveIndex();
		
    }  
	
	public List<TipoTrabalho> getTipoTrabalho(){
		return this.tipoTrabalhoBEAN.listarTipoTrabalhos();
	}
	
	public String atualizar(Avaliador avaliador){
		this.usuario = avaliador.getUsuario();
		this.avaliador = avaliador;
		return "editar_avaliador.xhtml";
	}
	public TrabalhoSubmetido getTrabalho() {
		return trabalho;
	}

	public void setTrabalho(TrabalhoSubmetido trabalho) {
		this.trabalho = trabalho;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getRating2() {
		return rating2;
	}

	public void setRating2(Integer rating2) {
		this.rating2 = rating2;
	}

	public Integer getRating3() {
		return rating3;
	}

	public void setRating3(Integer rating3) {
		this.rating3 = rating3;
	}

	public Integer getRating4() {
		return rating4;
	}

	public void setRating4(Integer rating4) {
		this.rating4 = rating4;
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

	public Avaliador getAvaliador() {
		return avaliador;
	}

	public void setAvaliador(Avaliador avaliador) {
		this.avaliador = avaliador;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	
}
