package br.com.margel.softvinhows.models;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="itens_vendas")
public class ItemVenda extends BasicBean{
	
	@ManyToOne(optional=false)
	private Vinho vinho;
	
	@ManyToOne(optional=false) @JsonIgnore
	private Venda venda;
	
	@Column(name="quantidade", nullable=false)
	private BigDecimal quantidade;
	
	@Column(name="valor_unitario", nullable=false)
	private BigDecimal valorUnitario;
	
	@Column(name="total_item",nullable=false)
	private BigDecimal totalItem;

	public Vinho getVinho() {
		return vinho;
	}

	public void setVinho(Vinho vinho) {
		this.vinho = vinho;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public BigDecimal getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(BigDecimal totalItem) {
		this.totalItem = totalItem;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	
	@Override
	public String toString() {
		return getId()+" - "+vinho+" "+totalItem;
	}
}