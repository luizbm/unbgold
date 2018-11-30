package br.unb.unbgold.ctrl;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
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
	private PrefixMapping map;
	ManagerFiles mf;
	private static String endCatalogo = "rdfs/catalogo" ;
	@PostConstruct
	private void init() {
		publicacaoDao = new PublicacaoDao();
		sujeitoDao = new SujeitoDao();
		objetoDao = new ObjetoDao();
		colunaDao = new ColunaDao();
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
	        	
	        	line = line.replace("ï»¿", "");
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

	public Boolean atualizarCatalogo(@PathParam("id") int id) {

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
		
		Dataset ds = TDBFactory.createDataset(endCatalogo) ;
		ds.begin(ReadWrite.WRITE);
		Model m = ds.getDefaultModel();
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
			
			List<Publicacao> publicacoes = publicacaoDao.getAll();
			
			for(Publicacao publicacao : publicacoes) {
				//publicacao = publicacaoDao.get(id);	
				Resource tp = m.createResource("http://purl.org/dc/dcmitype#Dataset");
				Resource root =  m.createResource(publicacao.getFonte(), tp);
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
					triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.relacao), false, lig.getFonte(), root));
				}
				List<Publicacao> ligados = publicacaoDao.findLigados(publicacao);
				for (Publicacao lig : ligados) {
					triplas.add(new IndexacaoFonteObjeto(this.findTermo(termos,MetadadoUtil.relacao), false, lig.getFonte(), root));
				}
			}
			for (IndexacaoFonteObjeto tripla : triplas) {
    			Ontologia ontologia = tripla.getTermo().getOntologia();
    			String iriTermo = ontologia.getUrl_ontologia().trim()+"#"+tripla.getTermo().getNm_termo().trim();
    			if(tripla.getLiteral()) {
    				System.out.println(tripla.getRoot().getURI()+" "+ontologia.getUrl_ontologia()+"#"+tripla.getTermo().getNm_termo()+" - "+tripla.getValor());
    				m.add(tripla.getRoot(), m.createProperty(iriTermo.trim()), tripla.getValor());
    			}else {
    				System.out.println(tripla.getRoot().getURI()+" "+ontologia.getUrl_ontologia()+"#"+tripla.getTermo().getNm_termo()+" - "+tripla.getValor());
    				Resource o = m.createResource(tripla.getValor().trim());
    				m.add(tripla.getRoot(), m.createProperty(iriTermo.trim()), o);
    			}
    			m.write( System.out );
    			ByteArrayOutputStream out = new ByteArrayOutputStream();
    			m.write(out);
    		}	
		} catch (Exception e) {
			e.printStackTrace();
			retorno = false;
		}
		
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

	@GET
	@Path("/publicar/")
	@Produces(MediaType.TEXT_PLAIN)
	public String publicar() {
		CkanManager cm = new CkanManager();
		//cm.testarUplad();
		cm.MostraTodosInstancias("http://164.41.101.40:8002");
		List<Publicacao> pendentes = publicacaoDao.buscarPendentes();
		
		
		for(Publicacao pub : pendentes) {
			pub = publicacaoDao.setarVersao(pub);
			System.out.println(pub.getData_publicacao());
			if(this.publicarArquivos(pub)) {
				System.out.println("------------ ARQUIVOS CRIADOS ----------------------------");
			}
			ConjuntoDados cd = pub.getDataset();
			Instancia_ckan ic = cd.getInstancia_ckan();
			cm.setKey_api(ic.getKey_api());
			cm.setUrl_api(ic.getEndereco_ckan());
			
			
			CkanDatasetBase dataset = new CkanDatasetBase();
			
			try {
				dataset = cm.getDataset(cd.getTitulo());
			}catch (Exception e) {
				System.out.println(e.getMessage());
				dataset = cm.criarDataset(cd, pub);
			}
			
			
			//System.out.println(dataset.getTitle());
			
			try {
				CkanResourceBase resource  = cm.saveResource(cd, pub, dataset);
			}catch (Exception e) {
				System.out.println(e.getMessage());
				
			}
			
			// Conectar ao CKAN
			// Pegar o Dataset ou Criar outro
			// Setar valores
			// Atualizar Dataset
			// Atualizar Publicação
			// Programar próxima publicação
			
			
		}
		return "Rodou";
	}
	
	@GET
	@Path("/criar/")
	public void criarAqruivos() {
		String end = "C:\\\\Users\\\\00415102162\\\\git\\\\unbgold\\\\WebContent\\\\dados\\\\tabuada.txt";
		Scanner ler = new Scanner(System.in);
	    int i, n;
	 
	    System.out.printf("Informe o número para a tabuada:\n");
	    n = ler.nextInt();
	 
//	    File file = new File(emd)
	    FileWriter arq;
		try {
			arq = new FileWriter(end);
		    PrintWriter gravarArq = new PrintWriter(arq);
		    gravarArq.printf("+--Resultado--+%n");
		    for (i=1; i<=10; i++) {
		      gravarArq.printf("| %2d X %d = %2d |%n", i, n, (i*n));
		    }
		    gravarArq.printf("+-------------+%n");
		 
		   
		    System.out.printf("\nTabuada do %d foi gravada com sucesso em \""+end+"\".\n", n);

		    
		    arq.close();
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	}
	
	
	
	public Boolean publicarArquivos(Publicacao pub) {
		Boolean retorno = true;
		String auxFile = "";
		JSONArray jArray = new JSONArray();
		
			//retorno += pub.getNm_arquivo();
			ConjuntoDados cd = pub.getDataset();
			List<String[]> arrayLinha = new ArrayList<String[]>();
			
			try {
				BufferedReader buffer = this.pegaCSV(cd);
				
				String line = "";
				String csvSplitBy = ";";
		        while ((line = buffer.readLine().trim()) != null) {
		        	 line = line.replace("ï»¿", "");
		        	 String[] room = line.split(csvSplitBy);
		        	 for(int i = 0; i < room.length;i++) {
		        		 room[i] = room[i].trim();
		        		 
	 	        	 }
		        	 arrayLinha.add(room);
		        }
		        
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//json.put(head[i], room[i]);
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
					auxFile += strLinha;
					if(cabeca == null) {
						cabeca = linha;
					}else {
						for(int i=0;i<linha.length;i++) {
							json.put(cabeca[i], linha[i]);
						}
						jArray.put(json);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				retorno = false;
				
			}
			try {
				
				
				  
		            int colunaIri = 0;
		            
			        
			        String strComplmento = "";
			       
			        List<TabelaHorizontal> tabela = new ArrayList<TabelaHorizontal>();
			        
			        
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
		            
		            List<TriplaUtil> triplas = new ArrayList<TriplaUtil>();
		            
		            List<Coluna> cols = colunaDao.findByDataset(cd.getId_dataset());
		            String complemento = "";
		            Integer idComplemento = 0;
		            TabelaHorizontal thComplemento;
		            String[] complementos = null;
		            for(Coluna col : cols) {
		            	if(col.getComplemento()) {
		            		complemento = col.getNm_campo();
		            	}
		            }
		            for(TabelaHorizontal th : ths) {
		            	System.out.println(th.getColuna()+" "+complemento);
		            	if(th.getColuna().equals(complemento)) {
		            		complementos = th.getValores();
		            		break;
		            	}
		            }
		            Model m = ModelFactory.createDefaultModel();
		            
		    		
		    		List<Ontologia> ontologias = new ArrayList<Ontologia>();
		            for(Coluna col : cols) {
		            	if(col.getPublicar()) {
			            	Termo termo = col.getTermo();
			            	Ontologia ontologia = termo.getOntologia();
//			            	
			            	if(!ontologias.contains(ontologia)) {
			            		ontologias.add(ontologia);
			            	}
			            	if(col.getId_coluna_ligacao() == 1) {
			            		for(TabelaHorizontal th : ths) {
			            			String[] dados = th.getValores();
			            			for(int i=0;i<dados.length;i++) {
			            				if(th.getColuna().equals(col.getNm_campo())) {
				            				//String iri = cd.getIri()+"/"+complementos[i];
				            				Resource root =  m.createResource(cd.getIri()+complementos[i]);
				            				Property property = m.createProperty(ontologia.getUrl_ontologia()+"#"+termo.getNm_termo());
				            				String obj = dados[i];
				            				//System.out.println(iri+" "+ter+" "+obj);
				            				Objeto objeto = new Objeto();
				            				triplas.add(new TriplaUtil(root, property, obj, true, col));
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
		            	if(tu.getLiteral()) {
		            		m.add(tu.getRoot(), tu.getP(), tu.getObjeto());
		            	}
		            }
		            ByteArrayOutputStream out = new ByteArrayOutputStream();
		            m.write(out);
					String auxRDF	= out.toString("UTF-8");;
					
					List<Publicacao> totais = publicacaoDao.findByDataset(pub.getDataset());
					Date now = new Date();
					
					String com = totais.size()+"-"+now.toString().replace(" ", "").replace("-", "").replaceAll(":", "");
					String nameRDF = pub.getNm_arquivo()+"-"+com+".rdf";
					String nameCSV = pub.getNm_arquivo()+"-"+com+".csv";
					String nameJson = pub.getNm_arquivo()+"-"+com+".json";
					System.out.println(nameRDF);
					System.out.println(nameCSV);
					System.out.println(nameJson);
					
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
	
}

