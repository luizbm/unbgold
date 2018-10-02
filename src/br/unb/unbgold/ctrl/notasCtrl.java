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

import br.unb.unbgold.dao.NotaDao;
import br.unb.unbgold.model.Nota;

@Path("/notas")
public class notasCtrl {

	private NotaDao notaDao;
	
	@PostConstruct
	private void init() {
		notaDao = new NotaDao();
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Nota> listaNotas(){
		List<Nota> lista = null;
		try {
			;
			lista = notaDao.getAll();
			
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
	public Nota getNota(@PathParam("id") int idNota) {
		Nota nota = null;
		
		try {
			nota = notaDao.getNota(idNota);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return nota;
	}
	
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String novaNota(Nota nota) {
		String msg = "";
		System.out.println(nota.getTitulo());
		
		try {
			notaDao.addNota(nota);
			msg = "Nota cadastrada com sucesso";
		} catch (Exception e) {
			msg = "Erro ao cadastrar a nota";
			e.printStackTrace();
		}
		
		return msg;
	}	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarNota(Nota nota, @PathParam("id") int idNota) {
		String msg = "";
		System.out.println(nota.getTitulo());
		
		try {
			notaDao.alterNota(nota);
			msg = "Nota editada com sucesso";
		} catch (Exception e) {
			msg = "Erro ao editar a nota";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String excluirNota(@PathParam("id") int idNota) {
		String msg = "";
		System.out.println(idNota);
		
		try {
			notaDao.delNota(idNota);
			msg = "Nota removida com sucesso";
		} catch (Exception e) {
			msg = "Erro ao remover a nota";
			e.printStackTrace();
		}
		
		return msg;
	}
}
