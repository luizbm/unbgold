package br.unb.unbgold.dao;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.tdb.TDBFactory;

import br.unb.unbgold.model.Coluna;
import br.unb.unbgold.model.ConjuntoDados;
import br.unb.unbgold.model.Objeto;
import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Publicacao;
import br.unb.unbgold.model.Termo;
import br.unb.unbgold.util.Catalogo;
import br.unb.unbgold.util.IndexacaoFonteObjeto;
import br.unb.unbgold.util.MetadadoUtil;
import br.unb.unbgold.util.TabelaHorizontal;
import br.unb.unbgold.util.TriplaUtil;
import br.unb.unbgold.util.Util;
import eu.trentorise.opendata.traceprov.internal.org.apache.commons.io.output.ByteArrayOutputStream;

public class TriplaDao  {

	public static String PATH_CATALOGO = "C:\\Users\\00415102162\\git\\unbgold\\WebContent\\rdf\\catalogo";
	public static String PATH_DADOS = "C:\\Users\\00415102162\\git\\unbgold\\WebContent\\rdf";
	public static String GRAPH_CATALOGO = "http://dados.unb.br/catalago";
	public static String GRAPH_DADOS = "http://dados.unb.br/dados/";
	
	public Boolean atualizarCatalogo() {

		Boolean retorno = true;
		
		// instanciando os DAOs
		PublicacaoDao publicacaoDao = new PublicacaoDao();
		ColunaDao colunaDao = new ColunaDao();
		OntologiaDao ontologiaDao = new OntologiaDao();
		
		List<IndexacaoFonteObjeto> triplas = new ArrayList<IndexacaoFonteObjeto>();
		List<Ontologia> ontologias = new ArrayList<Ontologia>();

		// Buscando os vocabulários do Catalogo
		try {
			ontologias = ontologiaDao.getOntologiaCatalogo();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		Dataset ds = TDBFactory.createDataset(PATH_CATALOGO) ;
		ds.begin(ReadWrite.WRITE);
		Model m = ds.getNamedModel(GRAPH_CATALOGO);
		m.removeAll();
		///Model m = ModelFactory.createDefaultModel();
		// String graphURI = "http://dados.unb.br/catalago";
		
		for (Ontologia ontologia : ontologias) {
			String prefixo = ontologia.getPrefixo_ontologia();
			String url = ontologia.getUrl_ontologia().trim()+"#";
			m.setNsPrefix(prefixo, url);
		}
		try {
			List<Termo> termos = new TermoDao().getAll();
			
			List<Publicacao> publicacoes = publicacaoDao.getPublicacaoAtivas();
			
			for(Publicacao publicacao : publicacoes) {
				//publicacao = publicacaoDao.get(id);	
				Resource tp = m.createResource("http://purl.org/dc/dcmitype#Dataset");
				Resource root =  m.createResource(publicacao.getFonte()+publicacao.getNm_arquivo(), tp);
				ConjuntoDados conjuntoDados = publicacao.getDataset();
				//msg = conjuntoDados.getTitulo();
				Integer codigo = publicacao.getId_publicacao();
				triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos, MetadadoUtil.id), true, codigo.toString(), root));
				triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.titulo), true, conjuntoDados.getTitulo(), root));
				triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.descricao), true, conjuntoDados.getDescricao(), root));
				triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.orgao), true, conjuntoDados.getOrgao().getNm_orgao(), root));
				triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.vcge), false, conjuntoDados.getVcge().getIri_termo(), root));
				triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.fonte), false, publicacao.getFonte(), root));
				triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.metodologia), true, conjuntoDados.getMetodologia(), root));
				List<IndexacaoFonteObjeto> formatos = new ArrayList<IndexacaoFonteObjeto>();
				if(conjuntoDados.getIndexar_semantica()) {
					formatos.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.formato), true, "rdf", root));
					triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.formato), true, "rdf", root));
				}
				if(conjuntoDados.getCsv()) {
					formatos.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.formato), true, "csv", root));
					triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.formato), true, "csv", root));
				}
				if(conjuntoDados.getJson()) {
					formatos.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.formato), true, "json", root));
					triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.formato), true, "json", root));
				}
				List<IndexacaoFonteObjeto> tags = new ArrayList<IndexacaoFonteObjeto>();
				String[] room = conjuntoDados.getTags().split(",");
	        	for(int i = 0; i < room.length;i++) {
	        		tags.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.tags), true, room[i].trim(), root));
	        		triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.tags), true, room[i].trim(), root));
	        	}
	        	triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.frequencia), true, conjuntoDados.getFrequencia().getDs_frequencia(), root));
	        	List<Coluna> colunas = colunaDao.findByDataset(conjuntoDados.getId_dataset());
	        	List<Ontologia> vocabularios = new ArrayList<Ontologia>();
	        	for(Coluna coluna : colunas) {
	        		if(!vocabularios.contains(coluna.getTermo().getOntologia())){
	        			vocabularios.add(coluna.getTermo().getOntologia());
					}	
	        	}
	        	List<IndexacaoFonteObjeto> ontoInd = new ArrayList<IndexacaoFonteObjeto>();
	        	List<Ontologia> ontos = ontologiaDao.getOntologiaDosConjuntos(conjuntoDados.getId_dataset());
	        	for (Ontologia ontologia : ontos) {
	        		ontoInd.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.vocabulario), false, ontologia.getUrl_ontologia(), root));
	        		triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.vocabulario), false, ontologia.getUrl_ontologia(), root));
				}
	        	triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.tipo), false, conjuntoDados.getTermo().getIri_termo(), root));
	        	triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.data), true, publicacao.getData_publicacao().toString(), root));
	        	triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.datadecricao), true, publicacao.getData_publicacao().toString(), root));
	        	triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.linguagem), true, "pt", root));
	        	triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.publicacao), false, publicacao.getFonte(), root));
				List<Publicacao> ligacoes = publicacaoDao.findLigacoes(publicacao);
				for (Publicacao lig : ligacoes) {
					triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.relacao), false, lig.getFonte()+lig.getNm_arquivo(), root));
				}
				List<Publicacao> ligados = publicacaoDao.findLigados(publicacao);
				for (Publicacao lig : ligados) {
					triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.relacao), false, lig.getFonte()+lig.getNm_arquivo(), root));
				}
			}
			for (IndexacaoFonteObjeto tripla : triplas) {
    			Ontologia ontologia = tripla.getTermo().getOntologia();
    			String iriTermo = ontologia.getUrl_ontologia().trim()+"#"+tripla.getTermo().getNm_termo().trim();
    			if(tripla.getLiteral()) {
    				//System.out.println(tripla.getRoot().getURI()+" "+ontologia.getUrl_ontologia()+"#"+tripla.getTermo().getNm_termo()+" - "+tripla.getValor());
    				m.add(tripla.getRoot(), m.createProperty(iriTermo.trim()), tripla.getValor());
    			}else {
    				//System.out.println(tripla.getRoot().getURI()+" "+ontologia.getUrl_ontologia()+"#"+tripla.getTermo().getNm_termo()+" - "+tripla.getValor());
    				Resource o = m.createResource(tripla.getValor().trim());
    				m.add(tripla.getRoot(), m.createProperty(iriTermo.trim()), o);
    			}
    			
    			
    		}	
			//m.write( System.out );
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			m.write(out);
		} catch (Exception e) {
			e.printStackTrace();
			retorno = false;
		}
		
		ds.commit();
		ds.end();
		return retorno;
	}
	
	private Termo findTermo(List<Termo> termos, int id) {
		Termo retorno = new Termo();
		for(Termo termo : termos) {
			if(termo.getId_termo() == id) {
				retorno = termo;
				break;
			}
		}
		return retorno;		
	}

	public List<Catalogo> buscaSemantica(String valor) {
		List<Catalogo> catalagos = new ArrayList<Catalogo>();
		
		Dataset ds = TDBFactory.createDataset(PATH_CATALOGO) ;
		ds.begin(ReadWrite.READ);
		Model m = ds.getNamedModel(GRAPH_CATALOGO);
		String consulta = "";
		IRIFactory iriFactory = IRIFactory.semanticWebImplementation();
		Graph graph = m.getGraph();
		List<Ontologia> ontologias = new ArrayList<Ontologia>();
		OntologiaDao ontologiaDao = new OntologiaDao();
		try {
			ontologias = ontologiaDao.getOntologiaCatalogo();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String prefixo = "";
		for (Ontologia ontologia : ontologias) {
			prefixo += " PREFIX "+ontologia.getPrefixo_ontologia()+": <"+ontologia.getUrl_ontologia()+"#> ";
		}
		consulta += prefixo+" SELECT "
				+ " ?x "
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
		
		Query query = QueryFactory.create(consulta) ;
	    QueryExecution qexec = QueryExecutionFactory.create(query, m) ;
	    
	    try {
	          ResultSet results = qexec.execSelect() ;
	          for ( ; results.hasNext() ; )
	          {
	        	  QuerySolution soln = results.nextSolution() ;
	        	  Catalogo catalogo = this.preeencheCatalago(soln);
	        	  catalogo.setSubject(this.buscarSubjects(prefixo, catalogo.getIdentifier(), m));
	        	  catalogo.setFileFormat(this.buscarFormantFile(prefixo, catalogo.getIdentifier(), m));
	        	  catalogo.setVocabularyEncodingScheme(this.buscarVocabulario(prefixo, catalogo.getIdentifier(), m));
	        	  catalogo.setRelation(this.buscarDadosConectados(prefixo, catalogo.getIdentifier(), m));
	        	  if(this.naoExiste(catalagos, catalogo)) {
	        		  catalagos.add(catalogo);
	        		  break;
	        	  }
	        	  
	          }
	          
	    } catch (Exception e) {
	    	e.printStackTrace();
		} 
	    
	    ds.close();
	    return catalagos;
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

	public Catalogo detalhaCatalogo(String valor) {
		Catalogo catalogo = new Catalogo();
		String prefixo = "";
	    List<Ontologia> ontologias = new ArrayList<Ontologia>();
		OntologiaDao ontologiaDao = new OntologiaDao();
		try {
			ontologias = ontologiaDao.getOntologiaCatalogo();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Ontologia ontologia : ontologias) {
			prefixo += " PREFIX "+ontologia.getPrefixo_ontologia()+": <"+ontologia.getUrl_ontologia()+"#> ";
		}
	    String consulta = prefixo+" SELECT "
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
				+ " ?dataset "
				+ " WHERE "
				+ " { ?x dc:description ?description . "
				+ "   ?x dc:Title ?title  . "
				+ "   ?x dc:identifier ?identifier ."
				+ "   ?x dcterms:source ?source . "
				+ "   ?x foaf:Organization ?organization . "
				+ "   ?x dcterms:Frequency ?frequency . "
				+ "   ?x dc:type ?type . "
				+ "   ?x dc:date ?date . "
				+ "   ?x dcterms:created ?created . "
				+ "   ?x dcterms:language ?language . "
				+ "   ?x dcmitype:Dataset ?dataset . "
				+ "    FILTER (?identifier = \""+valor+"\") . "
				+ " } " ;
	    Dataset ds = TDBFactory.createDataset(PATH_CATALOGO) ;
		ds.begin(ReadWrite.READ);
		Model m = ds.getNamedModel(GRAPH_CATALOGO);
	    Query query = QueryFactory.create(consulta) ;
	    QueryExecution qexec = QueryExecutionFactory.create(query, m) ;
	    try {
	          ResultSet results = qexec.execSelect() ;
	          catalogo = this.preeencheCatalago(results.nextSolution());
        	  catalogo.setSubject(this.buscarSubjects(prefixo, catalogo.getIdentifier(), m));
        	  catalogo.setFileFormat(this.buscarFormantFile(prefixo, catalogo.getIdentifier(), m));
        	  catalogo.setVocabularyEncodingScheme(this.buscarVocabulario(prefixo, catalogo.getIdentifier(), m));
        	  catalogo.setRelation(this.buscarDadosConectados(prefixo, catalogo.getIdentifier(), m));
	    } catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
		} 
	    return catalogo;
	}

	public String indexarConjuntoDados(List<Coluna> cols, List<TabelaHorizontal> ths, String[] complementos, ConjuntoDados cd, Publicacao pub) {
		String comp = Util.getNomeConjuntoDados(cd.getTitulo(), pub.getVersao());
		Dataset ds = TDBFactory.createDataset(PATH_DADOS+"\\"+comp) ;
		ds.begin(ReadWrite.WRITE);
		Model m = ds.getNamedModel(GRAPH_DADOS+comp);
		List<Ontologia> ontologias = new ArrayList<Ontologia>();
		List<TriplaUtil> triplas = new ArrayList<TriplaUtil>();
		 
        for(Coluna col : cols) {
        	if(col.getPublicar()) {
            	Termo termo = col.getTermo();
            	Ontologia ontologia = termo.getOntologia();
//            	
            	if(!ontologias.contains(ontologia)) {
            		ontologias.add(ontologia);
            	}
            		for(TabelaHorizontal th : ths) {
            			String[] dados = th.getValores();
            			for(int i=0;i<dados.length;i++) {
            				if(th.getColuna().equals(col.getNm_campo())) {
	            				//String iri = cd.getIri()+"/"+complementos[i];
	            				Resource root =  m.createResource(cd.getIri()+complementos[i]);
	            				Property property = m.createProperty(ontologia.getUrl_ontologia()+"#"+termo.getNm_termo());
	            				String obj = dados[i];
	                        	if(col.getId_coluna_ligacao() == 1) {
	                        		triplas.add(new TriplaUtil(root, property, obj, true, col));
	                        	}else {
	                        		triplas.add(new TriplaUtil(root, property, obj, false, col));
	                        	}
            				}
            			}
            		}
            		
        	}
        }
        
		for (Ontologia ontologia : ontologias) {
			String prefixo = ontologia.getPrefixo_ontologia();
			String url = ontologia.getUrl_ontologia().trim()+"#";
			m.setNsPrefix(prefixo, url);
		}

        for(TriplaUtil tu : triplas) {
        	//System.out.println(tu.getLiteral());
        	if(tu.getLiteral()) {
        		m.add(tu.getRoot(), tu.getP(), tu.getObjeto());
        	}else {
        		Resource resource = this.pegarRecurso(pub, tu.getColuna(), tu.getObjeto());
        		if(resource != null) {
        			m.add(tu.getRoot(), tu.getP(), resource);
        		}else {
            		m.add(tu.getRoot(), tu.getP(), tu.getObjeto());
        		}
        	}
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        m.write(out);
        ds.commit();
        ds.end();
		String auxRDF = "";
		try {
			auxRDF = out.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

		return auxRDF;
	}

	public Resource pegarRecurso(Publicacao publicacao, Coluna coluna, String valor) {
		

		String comp = publicacao.getNm_arquivo();
		Resource retorno = null;
		Dataset ds = TDBFactory.createDataset(PATH_DADOS+"\\"+comp) ;
		ds.begin(ReadWrite.WRITE);
		Model m = ds.getNamedModel(GRAPH_DADOS+comp);
		Termo termo = coluna.getTermo();
		String vocab = termo.getOntologia().getUrl_ontologia()+"#"+termo.getNm_termo();
		String consulta = " SELECT ?x " + 
				"WHERE " + 
				" { ?x <"+vocab+"> ?field . "
			//	+ " ?x dc:identifier ?field . "
				+ " FILTER (?field = \""+valor+"\") . "
				+ " } " ;
		Query query = QueryFactory.create(consulta) ;
	    QueryExecution qexec = QueryExecutionFactory.create(query, m) ;
	    try {
	          ResultSet results = qexec.execSelect() ;
	          for ( ; results.hasNext() ; )
	          {
	        	  QuerySolution soln = results.nextSolution();
	        	  retorno = soln.getResource("x");
	              
	          }
	          
	    } catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
		} 
	    ds.close();
		return retorno;
		
		
	}
	
}
