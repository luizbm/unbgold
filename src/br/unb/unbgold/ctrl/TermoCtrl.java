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
import javax.ws.rs.core.Response;

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
	public Response  buscarTermosPorIdOntologiaNovo(List<KeyValue> ids){
		if(ids != null) {
			for (KeyValue kv: ids) {
				System.out.println(kv.getValue()+" "+kv.getLabel());
			}
		}
		return  Response.ok() //200
				.entity("teste")
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.header("access-control-max-age", "31536000")
				.header("access-control-allow-headers", "Accept, Accept-Language, Content-Language, Content-Type, X-ACCESS_TOKEN, X-CSRF-Token, Access-Control-Allow-Origin, Authorization, Origin, x-requested-with, Content-Range, Content-Disposition, Content-Description")
				.header("access-control-expose-headers", "Cache-Control, Content-Language, Content-Type, Expires, Last-Modified, Pragma, Content-Length")
//				.header("", "")
				.allow("OPTIONS").build();
	}
}
