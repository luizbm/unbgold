package br.unb.unbgold.ctrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.Normalizer;
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

import br.unb.unbgold.dao.ColunaDao;
import br.unb.unbgold.dao.ObjetoDao;
import br.unb.unbgold.dao.PublicacaoDao;
import br.unb.unbgold.dao.SujeitoDao;
import br.unb.unbgold.dao.TriplaDao;
import br.unb.unbgold.model.Coluna;
import br.unb.unbgold.model.ConjuntoDados;
import br.unb.unbgold.model.Objeto;
import br.unb.unbgold.model.Objeto_tipo;
import br.unb.unbgold.model.Publicacao;
import br.unb.unbgold.model.Sujeito;
import br.unb.unbgold.model.Tripla;
import br.unb.unbgold.util.Util;

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
			System.out.println(colunas.size());
			System.out.println(dataset.getFonte());
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
	            	System.out.println("--------------------------------------------------");
	 	        	//System.out.println("IRI DO SUJEITO: "+dataset.getIri()+room[colunaIri]); 
	                Sujeito sujeito = new Sujeito();
	                sujeito.setPublicacao(publicacao);
	                sujeito.setDesc_sujeito(dataset.getIri()+Util.preUri(room[colunaIri]));
	                
	                sujeitoDao.add(sujeito);
	                for (Coluna coluna : colunas) {
	                	if(coluna.getPublicar()) {
	                		for(int i = 0; i < room.length;i++) {
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
	        	        			System.out.println(objeto.getDesc_objeto());
	        	        			objetoDao.add(objeto);
	        	        		}
	        	        	}
	                	}
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
	

	
}

