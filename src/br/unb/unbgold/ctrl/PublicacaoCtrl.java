package br.unb.unbgold.ctrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
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

import br.unb.unbgold.dao.ColunaDao;
import br.unb.unbgold.dao.ObjetoDao;
import br.unb.unbgold.dao.OntologiaDao;
import br.unb.unbgold.dao.PublicacaoDao;
import br.unb.unbgold.dao.SujeitoDao;
import br.unb.unbgold.dao.TermoDao;
import br.unb.unbgold.dao.TriplaDao;
import br.unb.unbgold.model.Coluna;
import br.unb.unbgold.model.ConjuntoDados;
import br.unb.unbgold.model.Objeto;
import br.unb.unbgold.model.Objeto_tipo;
import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Publicacao;
import br.unb.unbgold.model.Sujeito;
import br.unb.unbgold.model.Termo;
import br.unb.unbgold.util.IndexacaoFonte;
import br.unb.unbgold.util.IndexacaoFonteObjeto;
import br.unb.unbgold.util.MetadadoUtil;
import br.unb.unbgold.util.Util;
import eu.trentorise.opendata.traceprov.internal.org.apache.commons.io.output.ByteArrayOutputStream;

@Path("/publicacao")
public class PublicacaoCtrl {

	private PublicacaoDao publicacaoDao;
	private TriplaDao triplaDao;
	private SujeitoDao sujeitoDao;
	private ObjetoDao objetoDao;
	
	@PostConstruct
	private void init() {
		publicacaoDao = new PublicacaoDao();
		triplaDao = new TriplaDao();
		sujeitoDao = new SujeitoDao();
		objetoDao = new ObjetoDao();
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Publicacao> listaPublicacao(){
		List<Publicacao> lista = null;
		try {
			;
			lista = publicacaoDao.getAll();
			
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
	public Publicacao getPublicacao(@PathParam("id") int id) {
		Publicacao publicacao = null;
		try {
			publicacao = publicacaoDao.get(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return publicacao;
	}
	
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String novaPublicacao(Publicacao publicacao) {
		String msg = "";
		try {
			publicacaoDao.add(publicacao);
			msg = "Publicacao cadastrada com sucesso";
		} catch (Exception e) {
			msg = "Erro ao cadastrar a publicacao";
			e.printStackTrace();
		}
		
		return msg;
	}	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarPublicacao(Publicacao publicacao, @PathParam("id") int id) {
		String msg = "";
		
		try {
			publicacaoDao.alter(publicacao);
			msg = "Publicacao editada com sucesso";
		} catch (Exception e) {
			msg = "Erro ao editar a publicacao";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String excluirPublicacao(@PathParam("id") int id) {
		String msg = "";
		
		try {
			publicacaoDao.delete(id);
			msg = "Publicacao removida com sucesso";
		} catch (Exception e) {
			msg = "Erro ao remover a publicacao";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	@GET
	@Path("/gerar/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String gerarTriplas(@PathParam("id") int id) {
		String msg = "";
		List<Coluna> colunas;
		Publicacao publicacao = new Publicacao();
		ConjuntoDados dataset = new ConjuntoDados();
		Objeto_tipo objeto_tipo = new Objeto_tipo();
		objeto_tipo.setDesc_objeto_tipo("Literal");
		objeto_tipo.setId_objeto_tipo(1);
		try {
			publicacao = publicacaoDao.get(id);
			dataset = publicacao.getDataset();
			colunas = new ColunaDao().findByDataset(dataset);
			//System.out.println(colunas.size());
			//System.out.println(dataset.getFonte());
			BufferedReader buffer = this.pegaCSV(dataset.getFonte());
			String line = "";
	        String csvSplitBy = ";";
	        String strComplmento = "";
	        for(Coluna coluna : colunas) {
	        	if(coluna.getComplemento()) {
	        		strComplmento = coluna.getNm_campo();
	        	}
	        }
	        Boolean lercabeca = true;
            int colunaIri = 0;
            int colunaSelec = -1;
            String[] head = null;
            
	        while ((line = buffer.readLine().trim()) != null) {
	        	
	        	line = line.replace("﻿", "");
	        	 String[] room = line.split(csvSplitBy);
	        	 for(int i = 0; i < room.length;i++) {
	        		 room[i] = room[i].trim();
 	        	 }
	        	 
	        	 if(lercabeca) {
	                	head = room;
		                for(int i = 0; i < room.length;i++) {
		                	if(strComplmento.equals(head[i])) {
		                		colunaIri = i;
		                	}
		                	/*if(coluna.getNm_campo().equals(room[i])) {
		                		colunaSelec = i;
		                	}*/
		                }
		                lercabeca = false;
	                	
	             }else {
	            	//System.out.println("--------------------------------------------------");
	 	        	//System.out.println("IRI DO SUJEITO: "+dataset.getIri()+room[colunaIri]); 
	                Sujeito sujeito = new Sujeito();
	                sujeito.setPublicacao(publicacao);
	                sujeito.setDesc_sujeito(dataset.getIri()+Util.preUri(room[colunaIri]));
	                
	                sujeitoDao.add(sujeito);
	                for (Coluna coluna : colunas) {
	                	//if(coluna.getPublicar()) {
	                		for(int i = 0; i < head.length;i++) {
	        	        		if(coluna.getNm_campo().equals(head[i])) {
	        	        			
	        	        			if(coluna.getColuna_ligacao().getId_coluna() == 1) {
	        	        				objeto_tipo.setDesc_objeto_tipo("Literal");
	        	        				objeto_tipo.setId_objeto_tipo(1);
	        	        			}else {
	        	        				objeto_tipo.setDesc_objeto_tipo("Recurso");
	        	        				objeto_tipo.setId_objeto_tipo(2);
	        	        			}
	        	        			Objeto objeto = new Objeto();
	        	        			objeto.setSujeito(sujeito);
	        	        			objeto.setObjeto_tipo(objeto_tipo);
	        	        			objeto.setTermo(coluna.getTermo());
	        	        			objeto.setDesc_objeto(room[i].trim());
	        	        			objeto.setColuna(coluna);
	        	        			//System.out.println(objeto.getDesc_objeto());
	        	        			objetoDao.add(objeto);
	        	        		}
	        	        	}
	                	//}
					}
	             }
	        	
	        	
	        	
	        	
	        	/*System.out.println("--------------------------------------------------");
	        	for(int i = 0; i < room.length;i++) {
	        		System.out.println(room[i]);
	        	}*/
	        	
	        }    
	        
	        
	        /*for(Coluna coluna : colunas) {
	        	String sujeito = dataset.getIri();
	        	Boolean lercabeca = true;
                int colunaIri = -1;
                int colunaSelec = -1;
                String[] head = null;
				while ((line = buffer.readLine()) != null) {
	                String[] room = line.split(csvSplitBy);
	                if(lercabeca) {
	                	head = room;
		                for(int i = 0; i < room.length;i++) {
		                	if(strComplmento.equals(room[i])) {
		                		colunaIri = i;
		                	}
		                	if(coluna.getNm_campo().equals(room[i])) {
		                		colunaSelec = i;
		                	}
		                }
		                lercabeca = false;
	                	
	                }else {
	                	for(int i = 0; i < room.length;i++) {
	                		if(coluna.getNm_campo().equals(head[colunaSelec])) {
			                	Tripla tripla = new Tripla();
			                	sujeito += room[colunaIri];
			                	tripla.setSujeito(sujeito);
			                	tripla.setObjeto(room[i]);
			                	tripla.setPublicacao(publicacao);
			                	tripla.setPredicado(coluna.getTermo());
			                	triplaDao.add(tripla);
			                	System.out.println(tripla.getSujeito()+" "+tripla.getObjeto()+" "+tripla.getPredicado().getNm_termo());
	                			
	                		}
	                	}
	                }
	                
	                //System.out.println(line);
	                //System.out.println("room [codigo =" + room[0] + " , Sigla=" + room[1]+"; Denominacao="+room[2]+"; Site="+room[3]);
	            }        	
	        	
	        }*/

			/*File file = new File(publicacao.getFonte());
			Scanner scanner;
			scanner = new Scanner(file);
			 while(scanner.hasNext()){
			        String line = scanner.next();
			        String[] columns = line.split(";");
			        for (String string : columns) {
						System.out.println(string);
					}
			        System.out.println("------------------------------------");

			    }
			*/

		} catch (Exception e) {
			// TODO Auto-generated catch block
 			e.printStackTrace();
		}
		msg = dataset.getDs_dataset();
		return msg;
	}
	
	private BufferedReader pegaCSV(String string) throws IOException {
		
		URL url = new URL(string);
        URLConnection connection = url.openConnection();

        InputStreamReader input = new InputStreamReader(connection.getInputStream(), "UTF-8");
        BufferedReader buffer = null;
        
        buffer = new BufferedReader(input);
        
		
		return buffer;
		
	}
	
	@GET
	@Path("/indexar/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String indexarFonte(@PathParam("id") int id) {
		String msg = "RODOU";
		PublicacaoDao publicacaoDao = new PublicacaoDao();
		TermoDao termoDao = new TermoDao();
		ColunaDao colunaDao = new ColunaDao();
		IndexacaoFonte indexacaoFonte = new IndexacaoFonte();
		List<IndexacaoFonteObjeto> triplas = new ArrayList<IndexacaoFonteObjeto>();
		
		//Publicacao publicacao = new Publicacao();
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

		String directory = "rdfs/catalogo" ;
		Dataset ds = TDBFactory.createDataset(directory) ;
		 String graphURI = "http://dados.unb.br/catalago";
		Model m = ds.getNamedModel(graphURI);
		
		
		for (Ontologia ontologia : ontologias) {
			String prefixo = ontologia.getPrefixo_ontologia();
			String url = ontologia.getUrl_ontologia().trim()+"#";
			 m.setNsPrefix(prefixo, url);
		}
		try {
			List<Termo> termos = new TermoDao().getAll();
			
			List<Publicacao> publicacoes = publicacaoDao.getAll();
			Publicacao p1 = publicacaoDao.get(7);
			publicacoes = new ArrayList<Publicacao>();
			publicacoes.add(p1);
			
			for(Publicacao publicacao : publicacoes) {
				//publicacao = publicacaoDao.get(id);
				Resource root =  m.createResource(publicacao.getFonte());
				ConjuntoDados conjuntoDados = publicacao.getDataset();
				msg = conjuntoDados.getDs_dataset();
				Integer codigo = publicacao.getId_publicacao();
				indexacaoFonte.setCodigo(this.findTermo(termos,MetadadoUtil.id), true, codigo.toString());
				triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos, MetadadoUtil.id), true, codigo.toString(), root));
				indexacaoFonte.setTitle(this.findTermo(termos,MetadadoUtil.titulo), true, conjuntoDados.getDs_dataset());
				triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.titulo), true, conjuntoDados.getDs_dataset(), root));
				indexacaoFonte.setDescricao(this.findTermo(termos,MetadadoUtil.descricao), true, conjuntoDados.getDescricao());
				triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.descricao), true, conjuntoDados.getDescricao(), root));
				indexacaoFonte.setAutor(this.findTermo(termos,MetadadoUtil.orgao), true, conjuntoDados.getOrgao().getNm_orgao());
				triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.orgao), true, conjuntoDados.getOrgao().getNm_orgao(), root));
				indexacaoFonte.setVcge(this.findTermo(termos,MetadadoUtil.vcge), false, conjuntoDados.getVcge().getIri_termo());
				triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.vcge), false, conjuntoDados.getVcge().getIri_termo(), root));
				indexacaoFonte.setFonte(this.findTermo(termos,MetadadoUtil.fonte), false, publicacao.getFonte());
				triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.fonte), false, publicacao.getFonte(), root));
				
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
				indexacaoFonte.setFormatos(formatos);
				
				List<IndexacaoFonteObjeto> tags = new ArrayList<IndexacaoFonteObjeto>();
				String[] room = conjuntoDados.getTags().split(",");
	        	for(int i = 0; i < room.length;i++) {
	        		tags.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.tags), true, room[i].trim(), root));
	        		triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.tags), true, room[i].trim(), root));
	        	}
	        	
	        	indexacaoFonte.setTags(tags);
	        	indexacaoFonte.setFonte(this.findTermo(termos,MetadadoUtil.frequencia), true, conjuntoDados.getFrequencia());
	        	triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.frequencia), true, conjuntoDados.getFrequencia(), root));
	        	
	        	List<Coluna> colunas = colunaDao.findByDataset(conjuntoDados);
	        	List<Ontologia> vocabularios = new ArrayList<Ontologia>();
	        	for(Coluna coluna : colunas) {
	        		if(!vocabularios.contains(coluna.getTermo().getOntologia())){
	        			vocabularios.add(coluna.getTermo().getOntologia());
					}	
	        	}
	        	List<IndexacaoFonteObjeto> ontoInd = new ArrayList<IndexacaoFonteObjeto>();
	        	for (Ontologia ontologia : ontologias) {
	        		ontoInd.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.vocabulario), false, ontologia.getUrl_ontologia(), root));
	        		triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.vocabulario), false, ontologia.getUrl_ontologia(), root));
				}
	        	indexacaoFonte.setFonte(this.findTermo(termos,MetadadoUtil.tipo), false, conjuntoDados.getTermo().getIri_termo());
	        	triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.tipo), false, conjuntoDados.getTermo().getIri_termo(), root));
	        	indexacaoFonte.setFonte(this.findTermo(termos,MetadadoUtil.data), true, publicacao.getData_publicacao().toString());
	        	triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.data), true, publicacao.getData_publicacao().toString(), root));
	        	indexacaoFonte.setFonte(this.findTermo(termos,MetadadoUtil.datadecricao), true, publicacao.getData_publicacao().toString());
	        	triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.datadecricao), true, publicacao.getData_publicacao().toString(), root));
	        	indexacaoFonte.setFonte(this.findTermo(termos,MetadadoUtil.linguagem), true, "pt");
	        	triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.linguagem), true, "pt", root));
	        	indexacaoFonte.setFonte(this.findTermo(termos,MetadadoUtil.publicacao), false, publicacao.getFonte());
	        	triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.publicacao), false, publicacao.getFonte(), root));
				List<Publicacao> ligacoes = publicacaoDao.findLigacoes(publicacao);
				for (Publicacao lig : ligacoes) {
					triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.relacao), false, lig.getFonte(), root));
				}
				List<Publicacao> ligados = publicacaoDao.findLigados(publicacao);
				for (Publicacao lig : ligados) {
					triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.relacao), false, lig.getFonte(), root));
				}
			}
			for (IndexacaoFonteObjeto tripla : triplas) {
    			Ontologia ontologia = tripla.getTermo().getOntologia();
           	/*	if(!ontologias.contains(ontologia)){
    				ontologias.add(ontologia);
    			}
           	*/	
    			String iriTermo = ontologia.getUrl_ontologia().trim()+"#"+tripla.getTermo().getNm_termo().trim();
    			if(tripla.getLiteral()) {
    				System.out.println(tripla.getRoot().getURI()+" "+ontologia.getUrl_ontologia()+"#"+tripla.getTermo().getNm_termo()+" - "+tripla.getValor());
    				m.add(tripla.getRoot(), m.createProperty(iriTermo.trim()), tripla.getValor());
    			}else {
    				//Model model = new 
    				System.out.println(tripla.getRoot().getURI()+" "+ontologia.getUrl_ontologia()+"#"+tripla.getTermo().getNm_termo()+" - "+tripla.getValor());
    				Resource o = m.createResource(tripla.getValor().trim());
    				m.add(tripla.getRoot(), m.createProperty(iriTermo.trim()), o);
    				
    			}
    		}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ds.end();
		m.write( System.out );
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		m.write(out);
		try {
			msg	= out.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
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

	
}

