package br.edu.ifrn.eventos.MBEAN;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import br.edu.ifrn.eventos.dominio.Atividade;
import br.edu.ifrn.eventos.dominio.Organizador;
import br.edu.ifrn.eventos.dominio.Participante;
import br.edu.ifrn.eventos.dominio.TipoTrabalho;
import br.edu.ifrn.eventos.interfaces.AtividadeDAORemote;
import br.edu.ifrn.eventos.interfaces.CadastroUsuarioRemote;
import br.edu.ifrn.eventos.interfaces.OrganizadorBEANRemote;
import br.edu.ifrn.eventos.interfaces.PalestranteDAORemote;
import br.edu.ifrn.eventos.interfaces.PermissaoPapelDAORemote;
import br.edu.ifrn.eventos.interfaces.TipoTrabalhoDAORemote;
import br.edu.ifrn.eventos.interfaces.UsuarioDAORemote;
import br.edu.ifrn.eventos.seguranca.dominio.Papel;
import br.edu.ifrn.eventos.seguranca.dominio.Usuario;
import br.ifrn.eventos.util.Mensagens;

@ManagedBean
@SessionScoped
public class OrganizadorMB {

	@EJB
	private OrganizadorBEANRemote organizadorBEAN;
	@EJB
	private PalestranteDAORemote palestranteDAO;
	@EJB
	private TipoTrabalhoDAORemote tipoTrabalhoBEAN;
	@EJB
	private AtividadeDAORemote atividadeBEAN;
	@EJB
	private UsuarioDAORemote usuarioBEAN;
	@EJB
	private CadastroUsuarioRemote cadastroBEAN;
	@EJB
	private PermissaoPapelDAORemote permissaoBEAN;
	private Atividade atividade = new Atividade();
	private Organizador organizador = new Organizador();
	private Usuario usuario = new Usuario();

	private TipoTrabalho tipoT = new TipoTrabalho();
	private List<Participante> lista_participantes = new ArrayList<Participante>();
	private String tituloAtividade;
	private int index = 0;

	public String listarParticipantes(int id) {
		lista_participantes = this.organizadorBEAN.getParticipantes(id);
		return "/organizador/marcar_presenca.xhtml";
	}

	public String imprimirListarParticipantes(int id) {
		lista_participantes = this.organizadorBEAN.getParticipantes(id);
		tituloDaAtividade(id);
		return "/organizador/inscritos_atividade.xhtml";
	}

	public List<Atividade> getAtividades() {
		return this.atividadeBEAN.getAtividades(index + 1);
	}

	public void tituloDaAtividade(int id) {
		Atividade at = this.atividadeBEAN.getAtividade(id);
		tituloAtividade = at.getTrabalho().getTitulo();
	}

	public void onTabChange(TabChangeEvent event) {
		TabView tv = (TabView) event.getComponent();
		this.index = tv.getActiveIndex();

	}

	public String salvar() {
		for (Participante p : lista_participantes) {
			this.organizadorBEAN.salvarPresenca(p);
		}

		return "/listar_atividades.xhtml";
	}

	public void change(ValueChangeEvent event) {
		System.out.println(event.getNewValue().toString());
	}

	public List<SelectItem> getTipoTrabalho() {
		List<TipoTrabalho> tipos = palestranteDAO.listarTiposTrabalhos();
		List<SelectItem> itens = new ArrayList<SelectItem>(tipos.size());

		for (TipoTrabalho t : tipos) {
			itens.add(new SelectItem(t.getId(), t.getTrabalhoTipo()));
		}

		return itens;
	}

	
	public void cadastrarOrganizador() throws UnsupportedEncodingException,
			javax.mail.MessagingException {
	
		if(!this.organizadorBEAN.verificarOrganizador(usuario.getCpf())){
			
			Usuario verificarUsuario = this.usuarioBEAN.buscarUsuario(usuario
					.getCpf());
			
			if (verificarUsuario == null) {
				usuario.setSenha("eventos");
				Usuario u = this.cadastroBEAN.CadastrarUsuario(usuario);
				Papel papel = this.permissaoBEAN
						.buscarPapel("Role_Organizador");
				this.permissaoBEAN.adicionarPermissao(u, papel);

				organizador.setUsuario(u);
				this.organizadorBEAN.CadastrarOrganizador(organizador);
				
				new Mensagens().sucesso(FacesContext.getCurrentInstance(), "Organizador Cadastrado com Sucesso!");
				usuario = new Usuario();
			} else if(verificarUsuario != null){
				Papel papel = this.permissaoBEAN
						.buscarPapel("Role_Organizador");
				this.permissaoBEAN.adicionarPermissao(verificarUsuario, papel);

				organizador.setUsuario(verificarUsuario);
				this.organizadorBEAN.CadastrarOrganizador(organizador);
				new Mensagens().sucesso(FacesContext.getCurrentInstance(), "Organizador Cadastrado com Sucesso!");
				usuario = new Usuario();
			}
		}else {
			new Mensagens().erro(FacesContext.getCurrentInstance(), "Este usuario j� � organizador");
		}

	}

	public List<TipoTrabalho> getTiposTrabalho() {
		return this.tipoTrabalhoBEAN.listarTipoTrabalhos();
	}

	public List<Participante> getLista_participantes() {
		return lista_participantes;
	}

	public void setLista_participantes(List<Participante> lista_participantes) {
		this.lista_participantes = lista_participantes;
	}

	public TipoTrabalho getTipoT() {
		return tipoT;
	}

	public void setTipoT(TipoTrabalho tipoT) {
		this.tipoT = tipoT;
	}

	public String getTituloAtividade() {
		return tituloAtividade;
	}

	public void setTituloAtividade(String tituloAtividade) {
		this.tituloAtividade = tituloAtividade;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public Organizador getOrganizador() {
		return organizador;
	}

	public void setOrganizador(Organizador organizador) {
		this.organizador = organizador;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
