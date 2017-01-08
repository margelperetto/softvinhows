package br.com.margel.softvinhows.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="vendas")
public class Venda extends BasicBean{

	@ManyToOne(optional=false)
	private Cliente cliente;
	
	@Column(name="data_venda")
	private Timestamp dataVenda;
	
	@Column
	private BigDecimal distancia;
	
	@Column(name="peso_total",precision=10, scale=3)
	private BigDecimal pesoTotal;
	
	@Column(name="total_itens")
	private BigDecimal totalItens;
	
	@Column(name="total_frete")
	private BigDecimal totalFrete;
	
	@Column(name="total_geral")
	private BigDecimal totalGeral;
	
	@Transient
	private List<ItemVenda> itens;
	
	public Venda() {
	}

	public Venda(Cliente cliente, BigDecimal distancia, List<ItemVenda> itens) {
		this.cliente = cliente;
		this.distancia = distancia;
		this.itens = itens;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getDistancia() {
		return distancia;
	}

	public void setDistancia(BigDecimal distancia) {
		this.distancia = distancia;
	}

	public BigDecimal getTotalItens() {
		return totalItens;
	}

	public void setTotalItens(BigDecimal totalItens) {
		this.totalItens = totalItens;
	}

	public BigDecimal getTotalFrete() {
		return totalFrete;
	}

	public void setTotalFrete(BigDecimal totalFrete) {
		this.totalFrete = totalFrete;
	}

	public List<ItemVenda> getItens() {
		return itens;
	}

	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}
	
	public BigDecimal getTotalGeral() {
		return totalGeral;
	}
	
	public void setTotalGeral(BigDecimal totalGeral) {
		this.totalGeral = totalGeral;
	}

	public BigDecimal getPesoTotal() {
		return pesoTotal;
	}

	public void setPesoTotal(BigDecimal pesoTotal) {
		this.pesoTotal = pesoTotal;
	}
	
	public Timestamp getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Timestamp dataVenda) {
		this.dataVenda = dataVenda;
	}

	@Override
	public String toString() {
		return "Venda [cliente=" + cliente + ", dataVenda=" + dataVenda + ", distancia=" + distancia + ", pesoTotal=" + pesoTotal
				+ ", totalItens=" + totalItens + ", totalFrete=" + totalFrete + ", totalGeral=" + totalGeral
				+ ", getId()=" + getId() + "]";
	}

}