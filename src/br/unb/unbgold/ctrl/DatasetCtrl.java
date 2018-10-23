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
import br.unb.unbgold.model.ConjuntoDados;

@Path("/dataset")
public class DatasetCtrl {

	private DatasetDao datasetDao;
	
	@PostConstruct
	private void init() {
		datasetDao = new DatasetDao();
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ConjuntoDados> listaDataset(){
		List<ConjuntoDados> lista = null;
		try {
			;
			lista = datasetDao.getAll();
			
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
	public ConjuntoDados getDataset(@PathParam("id") int id) {
		ConjuntoDados dataset = null;
		try {
			dataset = datasetDao.get(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataset;
	}
	
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String novaDataset(ConjuntoDados dataset) {
		String msg = "";
		try {
			datasetDao.add(dataset);
			msg = "Dataset cadastrada com sucesso";
		} catch (Exception e) {
			msg = "Erro ao cadastrar a dataset";
			e.printStackTrace();
		}
		
		return msg;
	}	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarDataset(ConjuntoDados dataset, @PathParam("id") int id) {
		String msg = "";
		
		try {
			datasetDao.alter(dataset);
			msg = "Dataset editada com sucesso";
		} catch (Exception e) {
			msg = "Erro ao editar a dataset";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String excluirDataset(@PathParam("id") int id) {
		String msg = "";
		
		try {
			datasetDao.delete(id);
			msg = "Dataset removida com sucesso";
		} catch (Exception e) {
			msg = "Erro ao remover a dataset";
			e.printStackTrace();
		}
		
		return msg;
	}

}
