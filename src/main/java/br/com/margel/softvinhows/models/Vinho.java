package br.com.margel.softvinhows.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name="vinhos")
public class Vinho extends BasicBean{

	@Column(nullable=false)
	private String nome;
	
	@Enumerated(EnumType.STRING)
	private Tipo tipo;
	
	@Column(nullable=false,precision=10, scale=3)
	private BigDecimal peso;
	
	@Column(name="preco_sugerido")
	private BigDecimal precoSugerido;
	
	public Vinho() {
		
	}
	
	public Vinho(String nome,BigDecimal peso, BigDecimal precoSugerido, Tipo tipo) {
		this.nome = nome;
		this.peso = peso;
		this.precoSugerido = precoSugerido;
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public BigDecimal getPrecoSugerido() {
		return precoSugerido;
	}

	public void setPrecoSugerido(BigDecimal precoSugerido) {
		this.precoSugerido = precoSugerido;
	}

	public static enum Tipo{
		TINTO("Tinto"),
		BRANCO("Branco"),
		ROSADO("Rosado"),
		ESPUMANTE("Espumante")
		;
		private String descricao;
		private Tipo(String descricao) {
			this.descricao = descricao;
		}
		@Override
		public String toString() {
			return descricao;
		}
	}

	@Override
	public String toString() {
		return "Vinho [nome=" + nome + ", tipo=" + tipo + ", peso=" + peso + ", precoSugerido=" + precoSugerido
				+ ", getId()=" + getId() + "]";
	}
	
	
}