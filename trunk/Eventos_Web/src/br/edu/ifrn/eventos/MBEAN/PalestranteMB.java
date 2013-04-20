package br.edu.ifrn.eventos.MBEAN;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
import br.edu.ifrn.eventos.seguranca.dominio.Permissao;
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
	private Usuario usuarioLogado = new Usuario();
	private Palestrante palestrante = null;
	private List<Palestrante> listaPalestrantes = new ArrayList<Palestrante>();

	public PalestranteMB() {
		palestrante = new Palestrante();
	}

	@PostConstruct
	public void inserirUsuarioListaPalestrante() {

		usuarioLogado = this.usuarioDAO.buscarUsuarioEmail(new UsuarioMB()
				.UsuarioLogado());

		palestrante.setUsuario(usuarioLogado);
		this.palestranteDAO.adicionaPalestrantes(palestrante);

	}

	@PreDestroy
	public void limparLista() {
		this.palestranteDAO.limparLista();
	}

	public String submeterTrabalho() {

		trabalho.setTipoTrabalho(palestranteDAO.listarTipo(tipoT.getId()));
		trabalho.setStatus(StatusTrabalhoEnum.AGUARDANDO);
		trabalho2 = palestranteDAO.submeterTrabalho(trabalho);
		avaliacao.setTrabalho(trabalho2);
		this.palestranteDAO.cadastrarAvaliacao(avaliacao);
		associarTrabalhoPalestrante();
		trabalho = new TrabalhoSubmetido();

		new Mensagens().sucesso(FacesContext.getCurrentInstance(),
				"Trabalho submetido com Sucesso");

		return "../index.xhtml";
	}

	// Adiciona palestrantes ao trabalho

	public void adicionarPalestrante() throws UnsupportedEncodingException,
			MessagingException {

		if (this.palestranteDAO.getPalestrantes().size() < 2) {
			Usuario addUsuario = this.usuarioDAO.buscarUsuario(usuario.getCpf());
			
			
			if (!usuarioLogado.getCpf().equals(usuario.getCpf())) 
			{
				
				if (addUsuario != null) {

					palestrante.setUsuario(addUsuario);
					this.palestranteDAO.adicionaPalestrantes(palestrante);

				} else if (addUsuario == null) {
					
					Usuario usuarioNaoCadastrado = new Usuario(
							usuario.getCpf(), usuario.getNome(),
							usuario.getEmail(), "eventos", false);

					usuarioNaoCadastrado = this.cadastroUsuarioDAO
							.CadastrarUsuario(usuarioNaoCadastrado);

					palestrante.setUsuario(usuarioNaoCadastrado);
					this.palestranteDAO.adicionaPalestrantes(palestrante);

				}
			}else{
				new Mensagens().erro(FacesContext.getCurrentInstance(),
						"Este Palestrante já foi adicionado");
			}
		} else
			new Mensagens().erro(FacesContext.getCurrentInstance(),
					"Quantidade máxima de palestrantes é 2");

	}

	// remove palestrante da lista
	public void remover(Palestrante palestrante) {
		if (palestrante.getUsuario().getEmail() == usuarioLogado.getEmail()) {
			new Mensagens().erro(FacesContext.getCurrentInstance(),
					"Este palestrante n�o pode ser removido");
		} else
			this.palestranteDAO.removePalestrante(palestrante);
	}

	public Papel buscarPapel() {
		return this.permissaoBEAN.buscarPapel("Role_Palestrante");
	}

	public void adicionarPermissao(Usuario addUsuario, Papel papel) {
		this.permissaoBEAN.adicionarPermissao(addUsuario, papel);
	}

	// lista palestrantes
	public List<Palestrante> getListarPalestrantes() {
		listaPalestrantes = this.palestranteDAO.getPalestrantes();
		return listaPalestrantes;
	}

	// busca usuario
	public void associarTrabalhoPalestrante() {
		for (Palestrante p : listaPalestrantes) {
			p.setTrabalho(trabalho2);

			if (!this.permissaoBEAN.verificarPermissaoUsuario(p.getUsuario(),
					buscarPapel())) {
				this.permissaoBEAN.adicionarPermissao(p.getUsuario(),
						buscarPapel());
			}

			this.palestranteDAO.updatePalestrante(p);
			System.out.println("ppppppppppppppppppppppppp");
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

	// lista trabalhos submetidos exibindo seu status
	public List<TrabalhoSubmetido> getStatusTrabalho() {
		return this.palestranteDAO.statusTrabalhos(new UsuarioMB()
				.UsuarioLogado());
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

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

}
