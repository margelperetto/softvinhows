package br.com.margel.softvinhows.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="clientes")
public class Cliente extends BasicBean{

	@Column(nullable=false)
	private String nome;
	
	public Cliente() {
	}
	
	public Cliente(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return getId()+" - "+nome;
	}
}