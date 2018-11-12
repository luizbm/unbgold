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

import br.unb.unbgold.dao.ColunaDao;
import br.unb.unbgold.dao.DatasetDao;
import br.unb.unbgold.model.Coluna;
import br.unb.unbgold.model.Ontologia;

@Path("/coluna")
public class ColunaCtrl {

	private ColunaDao colunaDao;
	
	@PostConstruct
	private void init() {
		colunaDao = new ColunaDao();
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coluna> listaOntologia(){
		List<Coluna> lista = null;
		try {
			;
			lista = colunaDao.getAll();
			
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
	public List<Coluna> getColunasPorDataset(@PathParam("id") int id) {
		List<Coluna> lista = new ArrayList<Coluna>();
		try {
			lista = colunaDao.findByDataset(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		lista.add(new Coluna());
		return lista;
	}
	
	@GET
	@Path("/{id}")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Coluna getColuna(@PathParam("id") int id) {
		Coluna coluna = null;
		try {
			coluna = colunaDao.get(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return coluna;
	}
	
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String novaColuna(Coluna coluna) {
		String msg = "";
		try {
			colunaDao.add(coluna);
			msg = "Coluna cadastrada com sucesso";
		} catch (Exception e) {
			msg = "Erro ao cadastrar a coluna";
			e.printStackTrace();
		}
		
		return msg;
	}	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarColuna(Coluna coluna, @PathParam("id") int id) {
		String msg = "";
		
		try {
			colunaDao.alter(coluna);
			msg = "Coluna editada com sucesso";
		} catch (Exception e) {
			msg = "Erro ao editar a coluna";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String excluirColuna(@PathParam("id") int id) {
		String msg = "";
		
		try {
			colunaDao.delete(id);
			msg = "Coluna removida com sucesso";
		} catch (Exception e) {
			msg = "Erro ao remover a coluna";
			e.printStackTrace();
		}
		
		return msg;
	}
}
