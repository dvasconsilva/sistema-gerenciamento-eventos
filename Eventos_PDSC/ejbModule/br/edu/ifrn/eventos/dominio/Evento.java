package br.edu.ifrn.eventos.dominio;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "evento")
public class Evento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nome;
	private String descricao;
	private boolean gratuito;
	private int valor;
	@Temporal(TemporalType.DATE)
	private Date dataInicialSubmissao;
	@Temporal(TemporalType.DATE)
	private Date dataFinalSubmissao;
	@Temporal(TemporalType.DATE)
	private Date dataInicialAvaliacao;
	@Temporal(TemporalType.DATE)
	private Date dataFinalAvaliacao;
	@Temporal(TemporalType.DATE)
	private Date dataAprovacaoTrabalho;
	@Temporal(TemporalType.DATE)
	private Date dataInicialInscricao;
	@Temporal(TemporalType.DATE)
	private Date dataFinalInscricao;
	
	@Temporal(TemporalType.DATE)
	private Date dataInicialEvento;
	@Temporal(TemporalType.DATE)
	private Date dataFinalEvento;
	
	@OneToMany(cascade =CascadeType.ALL, mappedBy = "evento")
	private List<PeriodoInscricao> periodoInscricao;
	
	//nao existiria, seria unidirecional
	@ManyToMany(cascade= CascadeType.ALL)
	private List<Participante> participantes;
	
	@OneToMany(mappedBy = "evento")
	private List<Atividade> atividade;
	
	public Evento() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public boolean isGratuito() {
		return gratuito;
	}
	public void setGratuito(boolean gratuito) {
		this.gratuito = gratuito;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}

	
	public Date getDataInicialSubmissao() {
		return dataInicialSubmissao;
	}

	public void setDataInicialSubmissao(Date dataInicialSubmissao) {
		this.dataInicialSubmissao = dataInicialSubmissao;
	}

	public Date getDataFinalSubmissao() {
		return dataFinalSubmissao;
	}

	public void setDataFinalSubmissao(Date dataFinalSubmissao) {
		this.dataFinalSubmissao = dataFinalSubmissao;
	}

	public Date getDataInicialAvaliacao() {
		return dataInicialAvaliacao;
	}

	public void setDataInicialAvaliacao(Date dataInicialAvaliacao) {
		this.dataInicialAvaliacao = dataInicialAvaliacao;
	}

	public Date getDataFinalAvaliacao() {
		return dataFinalAvaliacao;
	}

	public void setDataFinalAvaliacao(Date dataFinalAvaliacao) {
		this.dataFinalAvaliacao = dataFinalAvaliacao;
	}

	public Date getDataAprovacaoTrabalho() {
		return dataAprovacaoTrabalho;
	}

	public void setDataAprovacaoTrabalho(Date dataAprovacaoTrabalho) {
		this.dataAprovacaoTrabalho = dataAprovacaoTrabalho;
	}

	public Date getDataInicialInscricao() {
		return dataInicialInscricao;
	}

	public void setDataInicialInscricao(Date dataInicialInscricao) {
		this.dataInicialInscricao = dataInicialInscricao;
	}

	public Date getDataFinalInscricao() {
		return dataFinalInscricao;
	}

	public void setDataFinalInscricao(Date dataFinalInscricao) {
		this.dataFinalInscricao = dataFinalInscricao;
	}

	public Date getDataInicialEvento() {
		return dataInicialEvento;
	}

	public void setDataInicialEvento(Date dataInicialEvento) {
		this.dataInicialEvento = dataInicialEvento;
	}

	public Date getDataFinalEvento() {
		return dataFinalEvento;
	}

	public void setDataFinalEvento(Date dataFinalEvento) {
		this.dataFinalEvento = dataFinalEvento;
	}

	public List<Participante> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Participante> participantes) {
		this.participantes = participantes;
	}

	public List<PeriodoInscricao> getPeriodoInscricao() {
		return periodoInscricao;
	}

	public void setPeriodoInscricao(List<PeriodoInscricao> periodoInscricao) {
		this.periodoInscricao = periodoInscricao;
	}

	public List<Participante> getParticipante() {
		return participantes;
	}

	public void setParticipante(List<Participante> participante) {
		this.participantes = participante;
	}

	public List<Atividade> getAtividade() {
		return atividade;
	}

	public void setAtividade(List<Atividade> atividade) {
		this.atividade = atividade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
}
