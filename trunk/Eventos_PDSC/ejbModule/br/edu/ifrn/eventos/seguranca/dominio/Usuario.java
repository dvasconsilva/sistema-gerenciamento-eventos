package br.edu.ifrn.eventos.seguranca.dominio;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.Email;

import br.edu.ifrn.eventos.enums.SexoEnum;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)
	private String cpf;
	private String nome;

	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@Enumerated(EnumType.STRING)
	private SexoEnum sexo;

	@Email
	@Column(unique = true)
	private String username;
	private String password;
	private String chaveConfirmacao;
	private boolean ativo;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Permissao> permissoes;

	public Usuario() {
		super();
	}

	
	public Usuario(String cpf, String nome, String email, String senha,
			boolean ativo) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.username = email;
		this.password = senha;
		this.ativo = ativo;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public SexoEnum getSexo() {
		return sexo;
	}

	public void setSexo(SexoEnum sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return username;
	}

	public void setEmail(String email) {
		this.username = email;
	}

	public String getSenha() {
		return password;
	}

	public void setSenha(String senha) {
		this.password = senha;
	}

	public String getChaveConfirmacao() {
		return chaveConfirmacao;
	}

	public void setChaveConfirmacao(String chaveConfirmacao) {
		this.chaveConfirmacao = chaveConfirmacao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + id;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		Usuario other = (Usuario) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
