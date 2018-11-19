package br.unb.unbgold.ctrl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.unb.unbgold.dao.Instancia_ckanDao;
import br.unb.unbgold.model.Instancia_ckan;
import br.unb.unbgold.util.KeyValue;

@Path("/ckan")
public class CkanCtrl {

	private Instancia_ckanDao ckanDao;
	
	@PostConstruct
	private void init() {
		ckanDao = new Instancia_ckanDao();
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Instancia_ckan> listaOntologia(){
		List<Instancia_ckan> lista = null;
		try {
			;
			lista = ckanDao.getAll();
			
//			lista = notaDao.listasNotas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}
	@GET
	@Path("/select/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<KeyValue> listaCkanSelect(){
		List<KeyValue> lista = new ArrayList<KeyValue>();
		try {
			List<Instancia_ckan> ckans = ckanDao.getAll();
			for(Instancia_ckan ic : ckans) {
				lista.add(new KeyValue(ic.getId_instancia_ckan(), ic.getDesc_instancia_ckan()));
			}
//			lista = notaDao.listasNotas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}
	
	
	
	
}
