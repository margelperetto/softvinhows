package br.com.margel.softvinhows.resources;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.ws.rs.Path;

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
		boolean add = obj.getId()<=0;
		
		Venda venda = super.save(obj);

		if(add){
			for(ItemVenda item : obj.getItens()){
				item.setVenda(venda);
				Db.em().persist(item);
			}
		}
		return venda;
	}
	
	@Override
	public Venda get(long id) {
		Venda venda = super.get(id);
		venda.setItens(listItens(venda.getId()));
		return venda;
	}
	
	@Override
	public List<Venda> listAll() {
		System.out.println("Listando todas as vendas");
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

}
