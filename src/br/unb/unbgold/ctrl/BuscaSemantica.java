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
	public Response busca_semantica(@PathParam("valor") String valor){
		//@PathParam("valor") 
		List<Catalogo> catalagos = new ArrayList<Catalogo>();
		
		String msg = "";
		String consulta = "";
		String directory = "rdfs/catalogo";
		Dataset ds = TDBFactory.createDataset(directory) ;
		ds.begin(ReadWrite.READ) ;
		String iri_graph = "http://dados.unb.br/catalago";
		Model m = ds.getNamedModel(iri_graph);
		System.out.println("Model := "+m);
		System.out.println(ds);
		 IRIFactory iriFactory = IRIFactory.semanticWebImplementation();
		 Graph graph = m.getGraph();
		 System.out.println("Graph := "+graph);
	     if (graph.isEmpty()) {
	            final Resource product1 = m.createResource(
	                    iriFactory.construct( "directory" )
	                        .toString() );

	            final Property hasName = m.createProperty(iri_graph, "#hasName");
	            final Statement stmt = m.createStatement(
	                    product1, hasName, m.createLiteral("Beach Ball","en") );
	            System.out.println("Statement = " + stmt);

	            m.add(stmt);

	            // just for fun
	            System.out.println("Triple := " + stmt.asTriple().toString());
	        }else {
	        	System.out.println("Graph is not Empty; it has "+graph.size()+" Statements");
	        }
	        
		List<Ontologia> ontologias = new ArrayList<Ontologia>();
		OntologiaDao ontologiaDao = new OntologiaDao();
		try {
			ontologias.add(ontologiaDao.get(2));
			ontologias.add(ontologiaDao.get(8));
			ontologias.add(ontologiaDao.get(9));
			ontologias.add(ontologiaDao.get(10));
			ontologias.add(ontologiaDao.get(11));
			ontologias.add(ontologiaDao.get(5));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String prefixo = "";
		for (Ontologia ontologia : ontologias) {
			prefixo += " PREFIX "+ontologia.getPrefixo_ontologia()+": <"+ontologia.getUrl_ontologia()+"#> ";
		}
		//valor = "Graduação";
		consulta += prefixo+" SELECT "
				+ " ?description "
				+ " ?title "
				+ " ?identifier "
				+ " ?source "
				+ " ?organization "
				+ " ?frequency "
				+ " ?type "
				+ " ?date "
				+ " ?created "
				+ " ?language "
				+ " ?dataset " + 
		"WHERE " + 
		" { ?x dc:description ?description . "
		+ " ?x dc:Title ?title  . "
		+ " ?x dc:identifier ?identifier ."
		+ " ?x dcterms:source ?source . "
		+ " ?x foaf:Organization ?organization . "
		+ " ?x dcterms:Frequency ?frequency . "
		+ " ?x dc:type ?type . "
		+ " ?x dc:date ?date . "
		+ " ?x dcterms:created ?created . "
		+ " ?x dcterms:language ?language . "
		+ " ?x dcmitype:Dataset ?dataset . "
		+ " ?x dcam:VocabularyEncodingScheme ?vocabulary . "
		+ " ?x dcterms:FileFormat ?fileFormat . "
		+ " ?x dc:subject ?subject . "
		+ " ?x dcterms:Relation ?relation . "
		+ " FILTER (regex(?title, \""+valor+"\", \"i\" )"
				+ " || regex(?description, \""+valor+"\", \"i\" )"
				+ " || regex(?identifier, \""+valor+"\", \"i\" )"
				+ " || regex(?source, \""+valor+"\", \"i\" )"
				+ " || regex(?organization, \""+valor+"\", \"i\" )"
				+ " || regex(?frequency, \""+valor+"\", \"i\" )"
				+ " || regex(?type, \""+valor+"\", \"i\" )"
				+ " || regex(?date, \""+valor+"\", \"i\" )"
				+ " || regex(?created, \""+valor+"\", \"i\" )"
				+ " || regex(?date, \""+valor+"\", \"i\" )"
				+ " || regex(?language, \""+valor+"\", \"i\" )"
				+ " || regex(?Dataset, \""+valor+"\", \"i\" )"
				+ " || regex(?vocabulary, \""+valor+"\", \"i\" )"
				+ " || regex(?fileFormat, \""+valor+"\", \"i\" )"
				+ " || regex(?subject, \""+valor+"\", \"i\" )"
				+ " || regex(?relation, \""+valor+"\", \"i\" )"
		+ "  ) . "

		+ " } " ;
		System.out.println(consulta);
		Query query = QueryFactory.create(consulta) ;
	    QueryExecution qexec = QueryExecutionFactory.create(query, m) ;
	    try {
	          ResultSet results = qexec.execSelect() ;
	          System.out.println("Total: "+results.getRowNumber()+", Graphs: "+graph.size());
	          for ( ; results.hasNext() ; )
	          {
	        	  Catalogo catalogo = this.preeencheCatalago(results.nextSolution());
	        	  catalogo.setSubject(this.buscarSubjects(prefixo, catalogo.getIdentifier(), m));
	        	  catalogo.setFileFormat(this.buscarFormantFile(prefixo, catalogo.getIdentifier(), m));
	        	  catalogo.setVocabularyEncodingScheme(this.buscarVocabulario(prefixo, catalogo.getIdentifier(), m));
	        	  catalogo.setRelation(this.buscarDadosConectados(prefixo, catalogo.getIdentifier(), m));
	        	  if(this.naoExiste(catalagos, catalogo)) {
	        		  catalagos.add(catalogo);
	        	  }
	          }
	          
	    } catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
		} 

	        // Close the dataset.
	    ds.close();
	        

	    
	    
//		System.out.println(valor);
	    
		return Response.status(200).entity(catalagos).header("Access-Control-Allow-Origin", "*").build();
	}
	
	private Boolean naoExiste(List<Catalogo> catalogos, Catalogo catalogo) {
		Boolean retorno = true;
		for(Catalogo cat : catalogos) {
			if(cat.getIdentifier().equals(catalogo.getIdentifier())){
				retorno = false;
				break;
			}
		}
		return retorno;
		
	}
	
	private List<String> buscarSubjects(String prefixo, String identifier, Model m){
		List<String> tags = new ArrayList<String>();
		
		String consulta = prefixo+" SELECT ?subject " + 
				"WHERE " + 
				" { ?x dc:subject ?subject . "
				+ " ?x dc:identifier ?identifier . "
				+ " FILTER (?identifier = \""+identifier+"\") . "
				+ " } " ;
		Query query = QueryFactory.create(consulta) ;
	    QueryExecution qexec = QueryExecutionFactory.create(query, m) ;
	    try {
	          ResultSet results = qexec.execSelect() ;
	          for ( ; results.hasNext() ; )
	          {
	        	  QuerySolution soln = results.nextSolution();
	              tags.add(this.pegaLiteral(soln.getLiteral("subject")));
	              
	          }
	          
	    } catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
		} 

		return tags;
	}
	
	private List<String> buscarFormantFile(String prefixo, String identifier, Model m){
		List<String> retorno = new ArrayList<String>();
		
		String consulta = prefixo+" SELECT ?format " + 
				"WHERE " + 
				" { ?x dcterms:FileFormat ?format . "
				+ " ?x dc:identifier ?identifier . "
				+ " FILTER (?identifier = \""+identifier+"\") . "
				+ " } " ;
		Query query = QueryFactory.create(consulta) ;
	    QueryExecution qexec = QueryExecutionFactory.create(query, m) ;
	    try {
	          ResultSet results = qexec.execSelect() ;
	          for ( ; results.hasNext() ; )
	          {
	        	  QuerySolution soln = results.nextSolution();
	        	  retorno.add(this.pegaLiteral(soln.getLiteral("format")));
	              
	          }
	          
	    } catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
		} 

		return retorno;
	}
	
	private List<String> buscarVocabulario(String prefixo, String identifier, Model m){
		List<String> retorno = new ArrayList<String>();
		
		String consulta = prefixo+" SELECT ?vocab " + 
				"WHERE " + 
				" { ?x dcam:VocabularyEncodingScheme ?vocab . "
				+ " ?x dc:identifier ?identifier . "
				+ " FILTER (?identifier = \""+identifier+"\") . "
				+ " } " ;
		Query query = QueryFactory.create(consulta) ;
	    QueryExecution qexec = QueryExecutionFactory.create(query, m) ;
	    try {
	          ResultSet results = qexec.execSelect() ;
	          for ( ; results.hasNext() ; )
	          {
	        	  QuerySolution soln = results.nextSolution();
	        	  retorno.add(this.pegaResource(soln.getResource("vocab")));
	              
	          }
	          
	    } catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
		} 

		return retorno;
	}

	private List<String> buscarDadosConectados(String prefixo, String identifier, Model m){
		List<String> retorno = new ArrayList<String>();
		
		String consulta = prefixo+" SELECT ?relation " + 
				"WHERE " + 
				" { ?x dcterms:Relation ?relation . "
				+ " ?x dc:identifier ?identifier . "
				+ " FILTER (?identifier = \""+identifier+"\") . "
				+ " } " ;
		Query query = QueryFactory.create(consulta) ;
	    QueryExecution qexec = QueryExecutionFactory.create(query, m) ;
	    try {
	          ResultSet results = qexec.execSelect() ;
	          for ( ; results.hasNext() ; )
	          {
	        	  QuerySolution soln = results.nextSolution();
	        	  retorno.add(this.pegaResource(soln.getResource("relation")));
	              
	          }
	          
	    } catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
		} 

		return retorno;
	}

	
	private Catalogo preeencheCatalago(QuerySolution soln) {
		Catalogo catalogo = new Catalogo();
        catalogo.setIri(this.pegaResource(soln.getResource("x")));
        catalogo.setIdentifier(this.pegaLiteral(soln.getLiteral("identifier")));
        catalogo.setTitle(this.pegaLiteral(soln.getLiteral("title")));
        catalogo.setDescription(this.pegaLiteral(soln.getLiteral("description")));
        catalogo.setSource(this.pegaResource(soln.getResource("source")));
        catalogo.setOrganization(this.pegaLiteral(soln.getLiteral("organization")));
        catalogo.setFrequency(this.pegaLiteral(soln.getLiteral("frequency")));
        catalogo.setType(this.pegaResource(soln.getResource("type")));
        catalogo.setDate(this.pegaLiteral(soln.getLiteral("date")));
        catalogo.setCreated(this.pegaLiteral(soln.getLiteral("created")));
        catalogo.setLanguage(this.pegaLiteral(soln.getLiteral("language")));
        catalogo.setDataset(this.pegaResource(soln.getResource("dataset")));
        return catalogo;
	}
	
	private String pegaLiteral(Literal literal) {
		String res = "";
        if(literal != null) {
      	  res = literal.getString();
        }
        return res;
	}

	private String pegaResource(Resource resource) {
		String res = "";
		if(resource != null) {
			res = resource.getURI();
        }
        return res;
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
