package br.unb.unbgold.ctrl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import br.unb.unbgold.dao.ColunaDao;
import br.unb.unbgold.dao.DatasetDao;
import br.unb.unbgold.dao.TriplaDao;
import br.unb.unbgold.model.Coluna;
import br.unb.unbgold.model.Dataset;
import br.unb.unbgold.model.Tripla;

@Path("/dataset")
public class DatasetCtrl {

	private DatasetDao datasetDao;
	private TriplaDao triplaDao;
	
	@PostConstruct
	private void init() {
		datasetDao = new DatasetDao();
		triplaDao = new TriplaDao();
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Dataset> listaDataset(){
		List<Dataset> lista = null;
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
	@Path("/{id}")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Dataset getDataset(@PathParam("id") int id) {
		Dataset dataset = null;
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
	public String novaDataset(Dataset dataset) {
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
	public String editarDataset(Dataset dataset, @PathParam("id") int id) {
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
	
	@GET
	@Path("/gerar/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String gerarTriplas(@PathParam("id") int id) {
		String msg = "";
		List<Coluna> colunas;
		Dataset dataset = new Dataset();
		
		
		try {
			dataset = datasetDao.get(id);
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
	        for(Coluna coluna : colunas) {
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
			                	tripla.setDataset(dataset);
			                	tripla.setPredicado(coluna.getTermo());
			                	triplaDao.add(tripla);
			                	System.out.println(tripla.getSujeito()+" "+tripla.getObjeto()+" "+tripla.getPredicado().getNm_termo());
	                			
	                		}
	                	}
	                }
	                
	                //System.out.println(line);
	                //System.out.println("room [codigo =" + room[0] + " , Sigla=" + room[1]+"; Denominacao="+room[2]+"; Site="+room[3]);
	            }        	
	        	
	        }

			/*File file = new File(dataset.getFonte());
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

        InputStreamReader input = new InputStreamReader(connection.getInputStream());
        BufferedReader buffer = null;
        
        buffer = new BufferedReader(input);
        
		
		return buffer;
		
	}
}
