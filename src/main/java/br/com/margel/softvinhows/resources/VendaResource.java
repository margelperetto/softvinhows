package br.com.margel.softvinhows.resources;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import br.com.margel.softvinhows.Db;
import br.com.margel.softvinhows.models.ItemVenda;
import br.com.margel.softvinhows.models.Venda;

@Path("/venda")
public class VendaResource extends BasicBeanResource<Venda>{

	public VendaResource() {
		super(Venda.class);
	}
	
	@Override
	public Venda save(Venda obj) {
		
		Venda venda = super.save(obj);

		for(ItemVenda item : obj.getItens()){
			item.setVenda(venda);
			if(item.getId()==0l){
				Db.em().persist(item);
			}else{
				Db.em().merge(item);
			}
			Db.em().detach(item);
		}
		
		Db.em().detach(venda);
		
		return get(venda.getId());
	}
	
	@Override
	public Venda get(long id) {
		Venda venda = super.get(id);
		if(venda==null){
			return null;
		}
		venda.setItens(listItens(venda.getId()));
		return venda;
	}
	
	@Override
	public List<Venda> listAll() {
		List<Venda> vendas = super.listAll();
		for(Venda v : vendas){
			v.setItens(listItens(v.getId()));
		}
		return vendas;
	}

	private List<ItemVenda> listItens(long idVenda) {

		TypedQuery<ItemVenda> query = Db.em().createQuery(
				"SELECT i FROM ItemVenda i JOIN i.venda v WHERE v.id = :id_venda"
				,ItemVenda.class);
		
		query.setParameter("id_venda", idVenda);
		
		return query.getResultList();
	}
	
	@GET
	@Path("calcular_frete/{distancia}/{peso}")
	@Produces(MediaType.APPLICATION_JSON)
	public BigDecimal calcularFrete(@PathParam("distancia")BigDecimal distancia, @PathParam("peso")BigDecimal peso){
		System.out.println("Calculando frete para distância: "+distancia+" e peso "+peso);
		return calcularTotalFrete(distancia, peso);
	}
	
	@Override
	protected void validarSave(Venda v) throws WebApplicationException {
		if(v.getCliente()==null || v.getCliente().getId()==0){
			throw builBadRequest("Cliente inválido ou não adicionado na venda! "+v.getCliente());
		}
		if(v.getItens()==null || v.getItens().isEmpty()){
			throw builBadRequest("Nenhum item adicionado na venda!");
		}
		BigDecimal totalItens = BigDecimal.ZERO;
		BigDecimal pesoTotal = BigDecimal.ZERO;
		for(ItemVenda i : v.getItens()){
			validarItemCalcularTotal(i);
			totalItens = totalItens.add(i.getTotalItem());
			pesoTotal = pesoTotal.add(i.getPesoTotalItem());
		}
		if(v.getTotalFrete()==null){
			v.setTotalFrete(calcularTotalFrete(v.getDistancia(), pesoTotal));
		}
		v.setDataVenda(new Timestamp(System.currentTimeMillis()));
		v.setPesoTotal(pesoTotal);
		v.setTotalItens(totalItens);
		v.setTotalGeral(totalItens.add(v.getTotalFrete()));
	}

	private void validarItemCalcularTotal(ItemVenda i) {
		if(i.getVinho()==null){
			throw builBadRequest("Nenhum vinho informado para o item de venda! Qtd.: "+i.getQuantidade()+" x Vl.: "+i.getValorUnitario());
		}
		if(i.getPesoVinho()==null || i.getPesoVinho().signum()==0){
			throw builBadRequest("Peso do vinho '"+i.getVinho()+"' não informado!");
		}
		if(i.getQuantidade()==null || i.getQuantidade().signum()==0){
			throw builBadRequest("Nenhuma quantidade informada para o vinho '"+i.getVinho()+"' !");
		}
		if(i.getValorUnitario()==null || i.getValorUnitario().signum()==0){
			throw builBadRequest("Valor unitário não informado para o vinho '"+i.getVinho()+"' !");
		}
		i.setTotalItem(i.getQuantidade().multiply(i.getValorUnitario()));
		i.setPesoTotalItem(i.getQuantidade().multiply(i.getPesoVinho()));
	}
	
	
	/**
	 *  Fórmula para o cálculo do frete: <br>
	 *  1) Para cada 1 kilo do total do pedido, considerar R$5 no calculo do frente;  <br>
	 *  2) Distancias até 100km considerar somente valor calculado do peso do pedido, 
	 *     distancias maiores que 100km considerar valor calculado do peso do pedido 
	 *     vezes distancia da entrega divido por 100.
	 */
	public static BigDecimal calcularTotalFrete(BigDecimal distancia, BigDecimal peso) {
		
		if(distancia == null || distancia.signum()<0){
			throw builBadRequest("Distância '"+distancia+"' inválida ou não informada para calcular frete!");
		}
		if(peso == null || peso.signum()<=0){
			throw builBadRequest("Peso '"+peso+"' inválido ou não informado para calcular frete!");
		}
		if(distancia.signum()==0){
			return BigDecimal.ZERO;
		}else{
			BigDecimal cem = new BigDecimal("100");
			BigDecimal fretePorPeso = peso.multiply(new BigDecimal("5"));
			if(distancia.compareTo(cem)>0){
				return fretePorPeso.multiply(distancia).divide(cem,2,RoundingMode.DOWN);
			}else{
				return fretePorPeso.setScale(2, RoundingMode.DOWN);
			}
		}
	}

}
