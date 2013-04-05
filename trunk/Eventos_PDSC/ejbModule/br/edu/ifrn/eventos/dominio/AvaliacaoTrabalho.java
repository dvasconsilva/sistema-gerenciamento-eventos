package br.edu.ifrn.eventos.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "avaliacao")
public class AvaliacaoTrabalho implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private double notaFinal;


	@ManyToMany(mappedBy = "avaliacaoTrabalho")
	private List<Avaliador> avaliadores;

	@ManyToOne
	@JoinColumn(name = "id_trabalho")
	private TrabalhoSubmetido trabalho;

	public AvaliacaoTrabalho() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getNotaFinal() {
		return notaFinal;
	}

	public void setNotaFinal(double notaFinal) {
		this.notaFinal = notaFinal;
	}

	

	public List<Avaliador> getAvaliadores() {
		return avaliadores;
	}

	public void setAvaliadores(List<Avaliador> avaliadores) {
		this.avaliadores = avaliadores;
	}

	public List<Avaliador> getAvaliador() {
		return avaliadores;
	}

	public void setAvaliador(List<Avaliador> avaliador) {
		this.avaliadores = avaliador;
	}

	public TrabalhoSubmetido getTrabalho() {
		return trabalho;
	}

	public void setTrabalho(TrabalhoSubmetido trabalho) {
		this.trabalho = trabalho;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((avaliadores == null) ? 0 : avaliadores.hashCode());
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
		AvaliacaoTrabalho other = (AvaliacaoTrabalho) obj;
		if (avaliadores == null) {
			if (other.avaliadores != null)
				return false;
		} else if (!avaliadores.equals(other.avaliadores))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	
}
