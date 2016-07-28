package br.com.kloros.domain;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class Usuario extends GenericDomain {

	@Column(length = 32)
	private String senha; // formato MD5

	@Column(nullable = false)
	private Character tipo;

	@Column(nullable = false)
	private Boolean ativo;

	@OneToOne
	@JoinColumn(nullable = false)
	private Pessoa pessoa;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	@Transient
	public String getTipoFormatado() {
		String tipoFormatado = "Nao Informado";
		if (this.tipo == 'A') {
			tipoFormatado = "Administrador";
		} else if (this.tipo == 'G') {
			tipoFormatado = "Getente";
		} else if (this.tipo == 'B') {
			tipoFormatado = "Balconista";
		} 
		return tipoFormatado;
	}

	public Boolean getAtivo() {
		return ativo;
	}
	
	@Transient
	public String getAtivoFormatado() {
		if (ativo) {
			return "Sim";
		} else {
			return "Nao";
		}
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
