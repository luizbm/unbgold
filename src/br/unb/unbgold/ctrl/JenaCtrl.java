package br.unb.unbgold.ctrl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb.TDBFactory;

import br.unb.unbgold.dao.ColunaDao;
import br.unb.unbgold.dao.ObjetoDao;
import br.unb.unbgold.dao.PublicacaoDao;
import br.unb.unbgold.dao.SujeitoDao;
import br.unb.unbgold.model.Coluna;
import br.unb.unbgold.model.ConjuntoDados;
import br.unb.unbgold.model.Objeto;
import br.unb.unbgold.model.Objeto_tipo;
import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Publicacao;
import br.unb.unbgold.model.Sujeito;
import br.unb.unbgold.util.TriplaUtil;
import eu.trentorise.opendata.traceprov.internal.org.apache.commons.io.output.ByteArrayOutputStream;

@Path("/jena")
public class JenaCtrl {

	private PublicacaoDao publicacaoDao;
	private ObjetoDao objetoDao;
	@PostConstruct
	private void init() {
		publicacaoDao = new PublicacaoDao();
		objetoDao = new ObjetoDao();
	}
	
	@GET
	@Path("/{id}")
	public String novaColuna(@PathParam("id") int id) {
		Publicacao publicacao = new Publicacao();
		publicacao.setId_publicacao(id);
		
		

		
		
		
    	String msg = "Rodou";
		List<TriplaUtil> triplas = new ArrayList<TriplaUtil>();
		List<TriplaUtil> triplasLiteral = new ArrayList<TriplaUtil>();
		List<TriplaUtil> triplasRecurso = new ArrayList<TriplaUtil>();
		try {
			List<Ontologia> ontologias = new ArrayList<Ontologia>();
			Model m = ModelFactory.createDefaultModel();
		    
			
					
			publicacao = publicacaoDao.get(id);
			ConjuntoDados dataset = publicacao.getDataset();

			List<Sujeito> sujeitos = new SujeitoDao().getByPublicacaoId(id);
			//List<Sujeito> sujeitos = new SujeitoDao().getAll();
			//Model m = tdb.getDefaultModel();
			for (Sujeito sujeito : sujeitos) {
				List<Objeto> objetos = objetoDao.findByPublicacao(sujeito);

				Resource root =  m.createResource(sujeito.getDesc_sujeito());
				Objeto oType = new Objeto();
				oType.setDesc_objeto(dataset.getTermo().getIri_termo());
				Objeto_tipo ot = new Objeto_tipo();
				ot.setId_objeto_tipo(2);
				oType.setObjeto_tipo(ot);
				Coluna tColuna = new Coluna();
				tColuna.setPublicar(true);
				TriplaUtil type = new TriplaUtil(root, m.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), oType, false, tColuna);
						//new TriplaUtil(root, m.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), "", false, null)
				triplas.add(type);
				
				for (Objeto objeto: objetos) {
					
					if(!ontologias.contains(objeto.getTermo().getOntologia())){
						ontologias.add(objeto.getTermo().getOntologia());
					}	
					String iri_termo;
					if(objeto.getTermo().getNm_termo().equals("naoindexado")) {
						iri_termo = objeto.getTermo().getOntologia().getUrl_ontologia()+"#"+objeto.getColuna().getNm_campo();
					}else {
						iri_termo = objeto.getTermo().getOntologia().getUrl_ontologia()+"#"+objeto.getTermo().getNm_termo();
					}
					
					Property p = m.createProperty(iri_termo);
					if(objeto.getObjeto_tipo().getId_objeto_tipo() != 2) {
						triplasLiteral.add(new TriplaUtil(root, p, objeto, true, objeto.getColuna()));
					}else {
						triplasRecurso.add(new TriplaUtil(root, p, objeto, false, objeto.getColuna()));
						System.out.println(objeto.getDesc_objeto());
					}
					//m.add(root, p, objeto.getDesc_objeto());
				}
				oType.setObjeto_tipo(ot);
				Coluna ct = new Coluna();
				ct.setPublicar(true);
				ct.setNm_campo("Tipo_Termo");
				TriplaUtil tipo = new TriplaUtil(root, m.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), oType, false, ct);
						//new TriplaUtil(root, m.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), "", false, null)
				type.setTipo(true);
				triplas.add(type);
				
			}
			for (Ontologia ontologia : ontologias) {
				String prefixo = ontologia.getPrefixo_ontologia();
				String url = ontologia.getUrl_ontologia()+"#";
				 m.setNsPrefix(prefixo, url);
			}
			List<Coluna> colunas = new ArrayList<Coluna>();
			colunas = new ColunaDao().findByDataset(publicacao.getDataset().getId_dataset());
			
			for (Coluna coluna : colunas) {
				if(coluna.getId_coluna_ligacao() != 1) {
					System.out.println(coluna.getNm_campo());
					List<Objeto> objetosLigacao = new ObjetoDao().findByColuna(new ColunaDao().get(coluna.getId_coluna_ligacao()));
					List<TriplaUtil> triplaLink = new ArrayList<TriplaUtil>();
					for (TriplaUtil tripla : triplasRecurso) {
						
		//				System.out.println(tripla.getObjeto().getObjeto_tipo().getId_objeto_tipo());
						//tripla.setLiteral(true);
						Boolean naoAchou = true;
						if(tripla.getColuna().getNm_campo().equals(coluna.getNm_campo())) {
							for (Objeto objeto: objetosLigacao) {
		//					System.out.println(tripla.getObjeto().getDesc_objeto()+" - "+objeto.getDesc_objeto());
								//System.out.println(objeto.getDesc_objeto());
								if(tripla.getObjeto().getDesc_objeto().equals(objeto.getDesc_objeto())){
									Objeto ob = tripla.getObjeto();
									ob.setDesc_objeto(objeto.getSujeito().getDesc_sujeito());
									
									tripla.setObjeto(ob);
									tripla.setLiteral(false);
									naoAchou = false;
									break;
								}
							}
							if(naoAchou) {
								tripla.setLiteral(true);
							}
							triplaLink.add(tripla);
						}
						
					}
					triplas.addAll(triplaLink);
			
				}
					
			}
			triplas.addAll(triplasLiteral);
			for (TriplaUtil tripla : triplas) {
				if(tripla.getColuna().getPublicar()) {
					if(tripla.getLiteral()) {
						
						m.add(tripla.getRoot(), tripla.getP(), tripla.getObjeto().getDesc_objeto());
					}else {
						//Model model = new 
						Resource o = m.createResource(tripla.getObjeto().getDesc_objeto());
						m.add(tripla.getRoot(), tripla.getP(), o);
						
					}
				}
			}
			
			/* String nsA = "http://somewhere/else#";
			 String nsB = "http://nowhere/else#";
			 Resource root = m.createResource( nsA + "root" );
			 Property P = m.createProperty( nsA + "P" );
			 Property Q = m.createProperty( nsB + "Q" );
			 Resource x = m.createResource( nsA + "x" );
			 Resource y = m.createResource( nsA + "y" );
			 Resource z = m.createResource( nsA + "z" );
			 m.add( root, P, x ).add( root, P, y ).add( y, Q, z );
			 System.out.println( "# -- no special prefixes defined" );
			 //m.write( System.out );
			 System.out.println( "# -- nsA defined" );
			 m.setNsPrefix( "nsA", nsA );
			// m.write( System.out );
			 System.out.println( "# -- nsA and cat defined" );
			 m.setNsPrefix( "cat", nsB );*/
			String fileName = "file_rdf.rdf";
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");

			m.write( System.out );
			 ByteArrayOutputStream out = new ByteArrayOutputStream();
			

			    
			 String str;

				m.write(out);
			msg	= out.toString("UTF-8");;
			System.out.println(msg);
			//System.out.println(new String(msg.getBytes("ISO-8859-1")));
			//writer.write(out.toString());
			//writer.close();
			 /*
			Model model = ModelFactory.createDefaultModel();
			
			for (Tripla tripla : triplas) {
				
				Property p = model.createProperty(tripla.getPredicado().getIri_termo());
				Resource r = model.createResource(tripla.getSujeito()).addProperty(p , tripla.getObjeto());
			
			}
			
			// create the resource
			/*Resource johnSmith =
			        model.createResource(personURI)
			             .addProperty(VCARD.FN, fullName);
			
			*/
			//m.write(System.out);
			String directory = "rdfs/"+publicacao.getId_publicacao() ;
			Dataset ds = TDBFactory.createDataset(directory) ;
			ds.setDefaultModel(m);
			ds.end();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return msg;
	}
	
	@GET
	@Path("/consulta")
	public String consulta() {
		String msg = "";
		String directory = "rdfs/1";
		Dataset ds = TDBFactory.createDataset(directory) ;
		Model m = ds.getDefaultModel();
		String sparqlQueryString = " "+
			
				" PREFIX uvoc:      <http://dadosabertos.unb.br/ontologia#> " + 
				" PREFIX aiiso:      <http://purl.org/vocab/aiiso/schema#> " + 
				" PREFIX foaf:      <http://xmlns.com/foaf/0.1/#> " + 
				" " + 
				"SELECT ?x ?name ?sigla ?department " + 
				"WHERE " + 
				" { ?x foaf:name ?name . " + 
				"   ?x uvoc:sigla ?sigla . " +
				"   ?x aiiso:Department ?department . " + 
				" } " ;
		Query query = QueryFactory.create(sparqlQueryString) ;
	    QueryExecution qexec = QueryExecutionFactory.create(query, ds) ;
	    try {
	          ResultSet results = qexec.execSelect() ;
	          for ( ; results.hasNext() ; )
	          {
	              QuerySolution soln = results.nextSolution() ;
	             
	              Resource resource = soln.getResource("x");
	              String uri = "";
	              if(resource != null) {
	            	  uri = resource.getURI();
	              }
	              Literal literal = soln.getLiteral("name");
	              String name = ""; 
	              if(literal != null) {
	            	  name = literal.getString() ;
	              }
	              String sigla = "";
	              literal = soln.getLiteral("sigla");
	              if(literal != null) {
	            	  sigla = literal.getString();
	              }
	              
	              String departamento = "";
	              resource = soln.getResource("department");
	              if(resource != null) {
	            	  departamento = resource.getURI();
	              }
	              
	              
	             // String departament = soln.getLiteral("departament").getString() ;
	              
	              String linha = "IRI: "+uri+" | name: "+name+" | sigla: "+sigla+" | departament:  "+ departamento 
	              ;
	              
	              msg = msg + linha + " \n";
	              System.out.println(linha
	              ) ;
	          }
	    } catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
		} 

	        // Close the dataset.
	    ds.close();
	        
	    
		
		return msg;
	}

	@Path("/tdb")
	public String buscaTdb() {
		String retorno = "Teste";
		String directory = "MyDatabases/Dataset1" ;
	    Dataset dataset = TDBFactory.createDataset(directory) ;
	    dataset.begin(ReadWrite.WRITE);
	    
		return retorno;
		
		
	}


	@GET
	@Path("/query")
	public String query() {
		String retorno = "TEste";
		
		String banda = "iron maiden";
		
		String prefixos = 
				 "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns/>  "
				+ "PREFIX aiiso: <http://purl.org/vocab/aiiso/schema/>  "
				+ "PREFIX  foaf: <http://xmlns.com/foaf/0.1/>  "
				//+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				//+ "PREFIX dbpprop: <http://dbpedia.org/property/> "
				;
		
		String queryBanda = prefixos + 
				" SELECT  ?faculty "
				+ "FROM <http://localhost/rdfs/faculdades.rdf> " 
				+"WHERE { "
				+ "?faculty foaf:name "
				+ "} ";
		System.out.println(queryBanda);
		System.out.println(queryBanda);
		try {
			QueryExecution queryExecution = 
					QueryExecutionFactory.sparqlService(
							"https://dbpedia.org/sparql",
							queryBanda
							);
			ResultSet results = queryExecution.execSelect();
			ResultSetFormatter.out(System.out, results);
			
			queryExecution.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			retorno = e.getMessage();
			
		}
		
		return retorno;
	
	}

}


