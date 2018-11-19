package br.unb.unbgold.ctrl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.unb.unbgold.dao.TermoDao;
import br.unb.unbgold.model.Termo;
import br.unb.unbgold.util.KeyValue;

@Path("/termo")
public class TermoCtrl {

	private TermoDao termoDao;
	
	@PostConstruct
	private void init() {
		termoDao = new TermoDao();
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Termo> listaTermo(){
		List<Termo> lista = null;
		try {
			;
			lista = termoDao.getAll();
			
//			lista = notaDao.listasNotas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}
	
	@GET
	@Path("/{id}")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Termo getTermo(@PathParam("id") int id) {
		Termo termo = null;
		try {
			termo = termoDao.get(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return termo;
	}
	
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String novaTermo(Termo termo) {
		String msg = "";
		try {
			termoDao.add(termo);
			msg = "Termo cadastrada com sucesso";
		} catch (Exception e) {
			msg = "Erro ao cadastrar a termo";
			e.printStackTrace();
		}
		
		return msg;
	}	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarTermo(Termo termo, @PathParam("id") int id) {
		String msg = "";
		
		try {
			termoDao.alter(termo);
			msg = "Termo editada com sucesso";
		} catch (Exception e) {
			msg = "Erro ao editar a termo";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String excluirTermo(@PathParam("id") int id) {
		String msg = "";
		
		try {
			termoDao.delete(id);
			msg = "Termo removida com sucesso";
		} catch (Exception e) {
			msg = "Erro ao remover a termo";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	@PUT
	@Path("/buscarSelectPorIdsOntologia")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<KeyValue> buscarTermosPorIdOntologia(List<KeyValue> ids){
		if(ids != null) {
			for (KeyValue kv: ids) {
				System.out.println(kv.getValue()+" "+kv.getLabel());
			}
		}
		return ids;
	}
	
	
	@PUT
	@Path("/buscarSelectPorIdsOntologiaNovo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<KeyValue>  buscarTermosPorIdOntologiaNovo(List<KeyValue> ids){
		List<KeyValue> retorno = new ArrayList<KeyValue>();
		if(ids != null) {
			try {
				List<Termo> termos = termoDao.getBuscarPorIdsOntologia(ids);
				
				for (Termo termo : termos) {
					retorno.add(new KeyValue(termo.getId_termo(),termo.getOntologia().getPrefixo_ontologia()+":"+termo.getNm_termo()));
				}
				System.out.println(termos.size());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (KeyValue kv: ids) {
				System.out.println(kv.getValue()+" "+kv.getLabel());
			}
		}
		return  retorno;
	}
}
