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

import br.unb.unbgold.dao.DatasetDao;
import br.unb.unbgold.dao.FrequenciaDao;
import br.unb.unbgold.dao.OntologiaDao;
import br.unb.unbgold.dao.ParametroDao;
import br.unb.unbgold.dao.Parametro_tipoDao;
import br.unb.unbgold.dao.TermoDao;
import br.unb.unbgold.model.ConjuntoDados;
import br.unb.unbgold.model.Frequencia;
import br.unb.unbgold.model.Frequencia_dia;
import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Parametro;
import br.unb.unbgold.model.Parametro_tipo;
import br.unb.unbgold.model.Termo;
import br.unb.unbgold.util.KeyValue;

@Path("/dataset")
public class DatasetCtrl {

	private DatasetDao datasetDao;
	private TermoDao termoDao;
	
	@PostConstruct
	private void init() {
		datasetDao = new DatasetDao();
		termoDao = new TermoDao();
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
	@Path("/buscaPorTipo/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ConjuntoDados> buscaPorTipo(@PathParam("id") int id){
		List<ConjuntoDados> lista = new ArrayList<ConjuntoDados>();
		try {
			lista = datasetDao.buscaPorTipo(id);
			
//			lista = notaDao.listasNotas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}	
	
	
	@GET
	@Path("/frequencia")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Frequencia> getFrequencias(){
		List<Frequencia> lista = new ArrayList<Frequencia>();
		try {
			lista = new FrequenciaDao().getAll();
			
//			lista = notaDao.listasNotas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}	
	
	@GET
	@Path("/frequenciaDia/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Frequencia_dia> getFrequenciaDias(@PathParam("id") int id){
		List<Frequencia_dia> lista = new ArrayList<Frequencia_dia>();
		try {
			lista = new FrequenciaDao().getDiasDaFrequencia(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}	
	
	@GET
	@Path("/getParametros/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Parametro> getParametros(@PathParam("id") int id){
		List<Parametro> lista = new ArrayList<Parametro>();
		try {
			lista = new ParametroDao().getPorConjuntoDeDados(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@GET
	@Path("/getOntologiaTermos/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Termo> getOntologiaTermos(@PathParam("id") int id){
		List<Termo> lista = new ArrayList<Termo>();
		try {
			List<Termo> ts = termoDao.buscarPorConjuntoDados(id);
			List<KeyValue> kvs = new ArrayList<KeyValue>();
			for(Termo t : ts) {
				kvs.add(new KeyValue(t.getOntologia().getId_ontologia(), t.getOntologia().getNm_ontologia()));
			}
			lista = termoDao.getBuscarPorIdsOntologia(kvs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@GET
	@Path("/getParametrosTipo")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Parametro_tipo> getParametrosTipo(){
		List<Parametro_tipo> lista = new ArrayList<Parametro_tipo>();
		try {
			lista = new Parametro_tipoDao().getAll();
		} catch (Exception e) {
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
