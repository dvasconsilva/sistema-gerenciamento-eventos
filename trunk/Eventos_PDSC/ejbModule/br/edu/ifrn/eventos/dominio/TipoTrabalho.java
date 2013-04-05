package br.edu.ifrn.eventos.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tipoTrabalho")
public class TipoTrabalho implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String trabalhoTipo;

	@OneToMany(mappedBy = "tipoTrabalho")
	private List<TrabalhoSubmetido> trabalhos;

	public TipoTrabalho() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getTrabalhoTipo() {
		return trabalhoTipo;
	}

	public void setTrabalhoTipo(String trabalhoTipo) {
		this.trabalhoTipo = trabalhoTipo;
	}

	public List<TrabalhoSubmetido> getTrabalhos() {
		return trabalhos;
	}

	public void setTrabalhos(List<TrabalhoSubmetido> trabalhos) {
		this.trabalhos = trabalhos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((trabalhos == null) ? 0 : trabalhos.hashCode());
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
		TipoTrabalho other = (TipoTrabalho) obj;
		if (id != other.id)
			return false;
		if (trabalhos == null) {
			if (other.trabalhos != null)
				return false;
		} else if (!trabalhos.equals(other.trabalhos))
			return false;
		return true;
	}
	
	

}
