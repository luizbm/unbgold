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

import br.unb.unbgold.dao.OntologiaDao;
import br.unb.unbgold.model.Ontologia;

@Path("/ontologia")
public class OntologiaCtrl {

	private OntologiaDao ontologiaDao;
	
	@PostConstruct
	private void init() {
		ontologiaDao = new OntologiaDao();
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ontologia> listaOntologia(){
		List<Ontologia> lista = null;
		try {
			;
			lista = ontologiaDao.getAll();
			
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
	public Ontologia getOntologia(@PathParam("id") int id) {
		Ontologia ontologia = null;
		try {
			ontologia = ontologiaDao.get(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ontologia;
	}
	
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String novaOntologia(Ontologia ontologia) {
		String msg = "";
		try {
			ontologiaDao.add(ontologia);
			msg = "Ontologia cadastrada com sucesso";
		} catch (Exception e) {
			msg = "Erro ao cadastrar a ontologia";
			e.printStackTrace();
		}
		
		return msg;
	}	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarOntologia(Ontologia ontologia, @PathParam("id") int id) {
		String msg = "";
		
		try {
			ontologiaDao.alter(ontologia);
			msg = "Ontologia editada com sucesso";
		} catch (Exception e) {
			msg = "Erro ao editar a ontologia";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String excluirOntologia(@PathParam("id") int id) {
		String msg = "";
		
		try {
			ontologiaDao.delete(id);
			msg = "Ontologia removida com sucesso";
		} catch (Exception e) {
			msg = "Erro ao remover a ontologia";
			e.printStackTrace();
		}
		
		return msg;
	}
}
