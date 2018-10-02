package br.unb.unbgold.ctrl;

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

import br.unb.unbgold.dao.DatasetDao;
import br.unb.unbgold.dao.ParametroDao;
import br.unb.unbgold.model.Parametro;

@Path("/parametro")
public class ParametroCtrl {

	private ParametroDao parametroDao;
	
	@PostConstruct
	private void init() {
		parametroDao = new ParametroDao();
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Parametro> listaParametro(){
		List<Parametro> lista = null;
		try {
			;
			lista = parametroDao.getAll();
			
//			lista = notaDao.listasNotas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}
	
	@GET
	@Path("/dataset/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Parametro> listaParametroPorDataset(@PathParam("id") int id){
		List<Parametro> lista = null;
		try {
			;
			lista = parametroDao.findByDataset(new DatasetDao().get(id));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	
	@GET
	@Path("/{id}")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Parametro getParametro(@PathParam("id") int id) {
		Parametro parametro = null;
		try {
			parametro = parametroDao.get(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parametro;
	}
	
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String novaParametro(Parametro parametro) {
		String msg = "";
		try {
			parametroDao.add(parametro);
			msg = "Parametro cadastrada com sucesso";
		} catch (Exception e) {
			msg = "Erro ao cadastrar a parametro";
			e.printStackTrace();
		}
		
		return msg;
	}	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarParametro(Parametro parametro, @PathParam("id") int id) {
		String msg = "";
		
		try {
			parametroDao.alter(parametro);
			msg = "Parametro editada com sucesso";
		} catch (Exception e) {
			msg = "Erro ao editar a parametro";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String excluirParametro(@PathParam("id") int id) {
		String msg = "";
		
		try {
			parametroDao.delete(id);
			msg = "Parametro removida com sucesso";
		} catch (Exception e) {
			msg = "Erro ao remover a parametro";
			e.printStackTrace();
		}
		
		return msg;
	}
}
