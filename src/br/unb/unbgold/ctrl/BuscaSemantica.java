package br.unb.unbgold.ctrl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.jena.graph.Graph;
import org.apache.jena.iri.IRIFactory;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.tdb.TDBFactory;

import br.unb.unbgold.dao.OntologiaDao;
import br.unb.unbgold.dao.PublicacaoDao;
import br.unb.unbgold.dao.TriplaDao;
import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Publicacao;
import br.unb.unbgold.util.Catalogo;
import br.unb.unbgold.util.ObjectLink;

@Path("/busca")
public class BuscaSemantica {

	@GET
	@Path("/{valor}")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Catalogo> busca_semantica(@PathParam("valor") String valor){
		//@PathParam("valor") 
		List<Catalogo> catalogos = new ArrayList<Catalogo>();
		TriplaDao triplaDao = new TriplaDao();
		catalogos = triplaDao.buscaSemantica(valor);
		return catalogos;
	}
	
	@GET
	@Path("detalhar/{valor}")
	@Produces(MediaType.APPLICATION_JSON)
	public Catalogo busca_detalhar(@PathParam("valor") String valor){
	    Catalogo catalogo = new Catalogo();
	    TriplaDao triplaDao = new TriplaDao();
	    catalogo = triplaDao.detalhaCatalogo(valor);
	    return catalogo;
	 }
	
	

	@GET
	@Path("/relacoes")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Publicacao> testeRelacao() {
		PublicacaoDao publicacaoDao = new PublicacaoDao();
		Publicacao publicacao;
		List<Publicacao> ligacoes = new ArrayList<Publicacao>();
		List<Publicacao> ligados = new ArrayList<Publicacao>();
		try {
			publicacao = publicacaoDao.get(2);
			ligacoes = publicacaoDao.findLigacoes(publicacao);
			ligados = publicacaoDao.findLigados(publicacao);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ligacoes;
	}
	
}
