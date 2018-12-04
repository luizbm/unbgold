package br.unb.unbgold.ctrl;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.tdb.TDBFactory;

import com.hp.hpl.jena.sparql.lib.org.json.JSONArray;
import com.hp.hpl.jena.sparql.lib.org.json.JSONException;
import com.hp.hpl.jena.sparql.lib.org.json.JSONObject;

import br.unb.unbgold.ckan.CkanManager;
import br.unb.unbgold.dao.ColunaDao;
import br.unb.unbgold.dao.ObjetoDao;
import br.unb.unbgold.dao.OntologiaDao;
import br.unb.unbgold.dao.PublicacaoDao;
import br.unb.unbgold.dao.SujeitoDao;
import br.unb.unbgold.dao.TermoDao;
import br.unb.unbgold.dao.TriplaDao;
import br.unb.unbgold.model.Coluna;
import br.unb.unbgold.model.ConjuntoDados;
import br.unb.unbgold.model.Instancia_ckan;
import br.unb.unbgold.model.Objeto;
import br.unb.unbgold.model.Objeto_tipo;
import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Publicacao;
import br.unb.unbgold.model.Sujeito;
import br.unb.unbgold.model.Termo;
import br.unb.unbgold.util.IndexacaoFonteObjeto;
import br.unb.unbgold.util.ManagerFiles;
import br.unb.unbgold.util.MetadadoUtil;
import br.unb.unbgold.util.TabelaHorizontal;
import br.unb.unbgold.util.TriplaUtil;
import br.unb.unbgold.util.Util;
import eu.trentorise.opendata.jackan.model.CkanDatasetBase;
import eu.trentorise.opendata.jackan.model.CkanResourceBase;
import eu.trentorise.opendata.traceprov.internal.org.apache.commons.io.output.ByteArrayOutputStream;

@Path("/publicacao")
public class PublicacaoCtrl {

	private PublicacaoDao publicacaoDao;
	private SujeitoDao sujeitoDao;
	private ObjetoDao objetoDao;
	private ColunaDao colunaDao;
	private TriplaDao triplaDao;
	private PrefixMapping map;
	ManagerFiles mf;
	private static String endCatalogo = "rdfs/catalogo" ;
	@PostConstruct
	private void init() {
		publicacaoDao = new PublicacaoDao();
		sujeitoDao = new SujeitoDao();
		objetoDao = new ObjetoDao();
		colunaDao = new ColunaDao();
		triplaDao = new TriplaDao();
		mf  = new ManagerFiles();
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
		String auxFile = "";
		JSONArray jArray = new JSONArray();
		try {
			publicacao = publicacaoDao.get(id);
			dataset = publicacao.getDataset();
			colunas = new ColunaDao().findByDataset(dataset.getId_dataset());
			//System.out.println(colunas.size());
			//System.out.println(dataset.getFonte());
			BufferedReader buffer = this.pegaCSV(dataset);
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
            String[] head = null;
            
	        while ((line = buffer.readLine().trim()) != null) {
	        	
	        	line = line.replace("﻿", "");
	        	 String[] room = line.split(csvSplitBy);
	        	 
	        	 for(int i = 0; i < room.length;i++) {
	        		 room[i] = room[i].trim();
	        		 
	        		 auxFile += room[i]+";"; 
 	        	 }
	        	 auxFile = auxFile.substring(0,(auxFile.length()-1))+ "\n";
	        	  
	        	 JSONObject json = new JSONObject();
	        	 if(lercabeca) {
	                	head = room;
		                for(int i = 0; i < room.length;i++) {
		                	head[i] = head[i].replace("?", ""); 
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
	                
	               // sujeitoDao.add(sujeito);
	                for (Coluna coluna : colunas) {
	                	//if(coluna.getPublicar()) {
	                		for(int i = 0; i < head.length;i++) {
	                			
	                			json.put(head[i], room[i]);
	        	        		if(coluna.getNm_campo().equals(head[i])) {
	        	        			
	        	        			if(coluna.getId_coluna() == 1) {
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
	        	        			//objetoDao.add(objeto);
	        	        		}
	        	        	}
	                	jArray.put(json);
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
		
        this.mf.gravarArquivo(auxFile, dataset.getTitulo(), dataset.getTitulo().toLowerCase().replace(" ", "-")+".csv");
        this.mf.gravarArquivo(jArray.toString(), dataset.getTitulo(), dataset.getTitulo().toLowerCase().replace(" ", "-")+".json");
		msg = dataset.getTitulo();
		return msg;
	}
	
	private BufferedReader pegaCSV(ConjuntoDados cd) throws IOException {
		
		
		
		URL url = new URL(this.montarURLComParametro(cd));
        URLConnection connection = url.openConnection();

        InputStreamReader input = new InputStreamReader(connection.getInputStream(), "UTF-8");
        BufferedReader buffer = null;
        
        buffer = new BufferedReader(input);
        
		
		return buffer;
		
	}
	
	private String montarURLComParametro(ConjuntoDados cd) {
		// TODO Auto-generated method stub
		return cd.getFonte();
	}


	



	public Boolean publicar(Publicacao pub) {
		Boolean retorno = true;
		
		//cm.testarUplad();
		
		if(this.publicarArquivos(pub)) {
			System.out.println("------------ ARQUIVOS CRIADOS ----------------------------");
		}
		ConjuntoDados cd = pub.getDataset();
		Instancia_ckan ic = cd.getInstancia_ckan();
		
		CkanManager cm = new CkanManager();
		cm.setKey_api(ic.getKey_api());
		cm.setUrl_api(ic.getEndereco_ckan());
			
		cm.MostraTodosInstancias("http://164.41.101.40:8002");	
		CkanDatasetBase dataset = new CkanDatasetBase();
			
		try {
			dataset = cm.getDataset(cd.getTitulo());
			if(dataset == null) {
				dataset = cm.criarDataset(cd, pub);	
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		cm.MostraTodosInstancias("http://164.41.101.40:8002");	
			try {
				CkanResourceBase resource  = cm.saveResource(cd, pub, dataset);
			}catch (Exception e) {
				System.out.println(e.getMessage());
				retorno = false;
				
			}
			
			
		return retorno;
	}
	
	
	
	
	public Boolean publicarArquivos(Publicacao pub) {
		Boolean retorno = true;
		String auxFile = "";
		JSONArray jArray = new JSONArray();
			ConjuntoDados cd = pub.getDataset();
			List<String[]> arrayLinha = new ArrayList<String[]>();
			try {
				BufferedReader buffer = this.pegaCSV(cd);
				String line = "";
				String csvSplitBy = ";";
		        while ((line = buffer.readLine().trim()) != null) {
		        	 line = line.replace("﻿", "");
		        	 String[] room = line.split(csvSplitBy);
		        	 for(int i = 0; i < room.length;i++) {
		        		 room[i] = room[i].trim();
	 	        	 }
		        	 arrayLinha.add(room);
		        }
		        
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();			}
			Boolean lercabeca = true;
			String[] cabeca = null;
			try {
				for(String[] linha : arrayLinha) {
					JSONObject json = new JSONObject();
					String strLinha = "";
					for(int i=0;i<linha.length;i++) {
						strLinha += linha[i]+";";
					}
					strLinha = strLinha.substring(0,(strLinha.length()-1));
					auxFile += strLinha+"\n";
					if(cabeca == null) {
						cabeca = linha;
					}else {
						for(int i=0;i<linha.length;i++) {
							System.out.println(cabeca[i]+"----------"+linha[i]);
							json.put(cabeca[i], linha[i]);
						}
						jArray.put(json);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				retorno = false;
			}
			try {
		            String[] head = arrayLinha.get(0);
		            List<TabelaHorizontal> ths = new ArrayList<TabelaHorizontal>(); 
		            for(int i=0;i<head.length;i++) {
		            	String aux = "";
		            	List<String> cols = new ArrayList<String>();
		            	Boolean cab = true;
		            	for(String[] th : arrayLinha) {
		            		if(cab) {
		            			cab = false;
		            		}else {
		            			for(int c=0;c<th.length;c++) {
		            				if(c==i) {
			            				aux += th[c].trim()+";";
		            				}
		            			}
		            			
		            		}
		            	}
		            	aux = aux.substring(0,(aux.length()-1));
		            	ths.add(new TabelaHorizontal(head[i], aux.split(";")));
		            }
		           
		            List<Coluna> cols = colunaDao.findByDataset(cd.getId_dataset());
		            String complemento = "";
		            String[] complementos = null;
		            for(Coluna col : cols) {
		            	if(col.getComplemento()) {
		            		complemento = col.getNm_campo();
		            	}
		            }
		            for(TabelaHorizontal th : ths) {
		            	System.out.println(th.getColuna()+" "+complemento);
		            	if(th.getColuna().toString().equals(complemento.toString())) {
		            		complementos = th.getValores();
		            		break;
		            	}
		            }
		            String auxRDF = "";
		            if(cd.getRdf())
						auxRDF = triplaDao.indexarConjuntoDados(cols, ths, complementos, cd, pub);
		            
		            					
					List<Publicacao> totais = publicacaoDao.findByDataset(pub.getDataset());
					
					String nameRDF = pub.getNm_arquivo()+".rdf";
					String nameCSV = pub.getNm_arquivo()+".csv";
					String nameJson = pub.getNm_arquivo()+".json";
					//System.out.println(nameRDF);
					//System.out.println(nameCSV);
					//System.out.println(nameJson);
					
		            this.mf.gravarArquivo(auxRDF, cd.getTitulo(), nameRDF);
		            this.mf.gravarArquivo(auxFile, cd.getTitulo(), nameCSV);
		            this.mf.gravarArquivo(jArray.toString(), cd.getTitulo(), nameJson);
		            
			} catch (Exception e) {
				retorno = false;
				// TODO: handle exception
				e.printStackTrace();
			}
			
		return retorno;
		
	
	}
	
	@GET
	@Path("/publicarPendentes")
	public String publicarPendentes() {
		String retorno = "Sem Pendentes";
		
		List<Publicacao> pendendes = publicacaoDao.buscarPendentes();
		
		for(Publicacao pub : pendendes) {
			
			ConjuntoDados cd = pub.getDataset();
			pub = publicacaoDao.setarParametrosParaPublicacao(pub, cd);
			if(this.publicar(pub)) {
				Publicacao nPublicacao = publicacaoDao.concluirPublicacao(pub, cd);
				SimpleDateFormat dataformat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
				retorno = "Atualizou "+pub.getNm_arquivo()+", Nova Publicacao em: "+dataformat.format(nPublicacao.getData_publicacao());
			}else {
				retorno = "Erro ao publicar pendende: "+cd.getTitulo();
				publicacaoDao.rollback();
			}
			break;
		}
		/*if(triplaDao.atualizarCatalogo()) {
			retorno += "\n Cat�logo Atualizado";
		}*/
			
		return retorno;
	}
	
	@GET
	@Path("/pegarRecurso")
	public String pegarRecurso() {
		String retorno = "";
		Publicacao publicacao = publicacaoDao.getPublicacaAtivaDataset(3);
		Coluna coluna = new Coluna();
		try {
			coluna = colunaDao.get(10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Resource resource = triplaDao.pegarRecurso(publicacao, coluna, "115");
		if(resource != null) {
			retorno = resource.getURI();	
		}
		 
		return retorno;		
	}
	
	
}

