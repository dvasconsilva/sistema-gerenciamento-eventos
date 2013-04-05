package br.edu.ifrn.eventos.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import br.edu.ifrn.eventos.enums.StatusTrabalhoEnum;

@Entity
@Table(name = "trabalhoSubmetido")
public class TrabalhoSubmetido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titulo;
	
	@Lob
	private String descricao;
	
	private int qtdParticipantes;
	@Enumerated(EnumType.STRING)
	private StatusTrabalhoEnum status;


	@OneToMany(mappedBy = "trabalho", cascade = CascadeType.ALL)
	private List<AvaliacaoTrabalho> avaliacao;

	@ManyToOne
	@JoinColumn(name = "id_tipoTrabalho")
	private TipoTrabalho tipoTrabalho;

	@OneToMany(mappedBy = "trabalho", cascade = CascadeType.ALL)
	private List<Palestrante> palestrantes;

	public TrabalhoSubmetido() {
		super();

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public int getQtdParticipantes() {
		return qtdParticipantes;
	}

	public void setQtdParticipantes(int qtdParticipantes) {
		this.qtdParticipantes = qtdParticipantes;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public StatusTrabalhoEnum getStatus() {
		return status;
	}

	public void setStatus(StatusTrabalhoEnum status) {
		this.status = status;
	}


	public TipoTrabalho getTipoTrabalho() {
		return tipoTrabalho;
	}

	public void setTipoTrabalho(TipoTrabalho tipoTrabalho) {
		this.tipoTrabalho = tipoTrabalho;
	}

	public List<Palestrante> getPalestrante() {
		return palestrantes;
	}

	public void setPalestrante(List<Palestrante> palestrante) {
		this.palestrantes = palestrante;
	}

	public List<AvaliacaoTrabalho> getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(List<AvaliacaoTrabalho> avaliacao) {
		this.avaliacao = avaliacao;
	}

	public List<Palestrante> getPalestrantes() {
		return palestrantes;
	}

	public void setPalestrantes(List<Palestrante> palestrantes) {
		this.palestrantes = palestrantes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((avaliacao == null) ? 0 : avaliacao.hashCode());
		result = prime * result + id;
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
		TrabalhoSubmetido other = (TrabalhoSubmetido) obj;
		if (avaliacao == null) {
			if (other.avaliacao != null)
				return false;
		} else if (!avaliacao.equals(other.avaliacao))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
