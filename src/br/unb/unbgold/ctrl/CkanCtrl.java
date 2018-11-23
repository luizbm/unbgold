package br.unb.unbgold.ctrl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.unb.unbgold.dao.Instancia_ckanDao;
import br.unb.unbgold.model.Instancia_ckan;
import br.unb.unbgold.util.KeyValue;
import eu.trentorise.opendata.jackan.CheckedCkanClient;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanDatasetBase;
import eu.trentorise.opendata.jackan.model.CkanResource;
import eu.trentorise.opendata.jackan.model.CkanTag;
import eu.trentorise.opendata.jackan.model.CkanTagBase;

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
	
	
	@GET
	@Path("/acessar")
	@Produces(MediaType.TEXT_HTML)
	public String acessar() {
		String key_api = "619c3f13-3972-4446-98c3-08fa0a06b0eb";
		String url_api = "http://164.41.101.40:8002";
		/*
		CkanClient cc = new CkanClient("http://164.41.101.40:8002","619c3f13-3972-4446-98c3-08fa0a06b0eb");
        System.out.println(cc.getDatasetList());
        List<String> ds = cc.getDatasetList(10, 0);

        for (String s : ds) {
            System.out.println();
            System.out.println("DATASET: " + s);
            CkanDataset d = cc.getDataset(s);
            System.out.println("  RESOURCES:");
            for (CkanResource r : d.getResources()) {
                System.out.println("    " + r.getName());
                System.out.println("    FORMAT: " + r.getFormat());
                System.out.println("       URL: " + r.getUrl());
            }
        }
        CkanDatasetBase dataset = new CkanDatasetBase();
        dataset.setName("my-cool-dataset-" + new Random().nextLong());
        // notice Jackan will only send field 'name' as it is non-null
        CkanClient myClient = new CheckedCkanClient("http://164.41.101.40:8002", key_api);
        
        CkanTagBase t1 = new CkanTagBase();
        t1.setId("12");
        t1.setName("Testando");
        t1.setVocabularyId("VocabTste");
        CkanTag tag = myClient.createTag(t1);
        System.out.println(tag.getDisplayName());
        CkanDataset createdDataset = myClient.createDataset(dataset);
        
        checkNotEmpty(createdDataset.getId(), "Invalid dataset id!");
        assertEquals(dataset.getName(), createdDataset.getName());
        System.out.println("Dataset is available online at " + CkanClient.makeDatasetUrl(myClient.getCatalogUrl(), dataset.getName()));
        
		*/
		 // here we use CheckedCkanClient for extra safety
        CkanClient myClient = new CheckedCkanClient(url_api, key_api);

		// we create a dataset with one tag 'cool'
        CkanDatasetBase dataset = new CkanDatasetBase("my-dataset-" + new Random().nextLong());
        List<CkanTag> tags_1 = new ArrayList();
        tags_1.add(new CkanTag("cool"));
        dataset.setTags(tags_1);
        CkanDataset createdDataset = myClient.createDataset(dataset);

        // now we assign a new array with one tag ["amazing"] 
        List<CkanTag> tags_2 = new ArrayList();
        tags_2.add(new CkanTag("amazing"));
        createdDataset.setTags(tags_2);

        // let's patch-update, jackan will take care of merging tags to prevent erasure of 'cool'
        CkanDataset updatedDataset = myClient.patchUpdateDataset(createdDataset);

        assert 2 == updatedDataset.getTags().size(); //  'amazing' has been added to ['cool']
        System.out.println("Merged tags = "
                + updatedDataset.getTags().get(0).getName()
                + ", " + updatedDataset.getTags().get(1).getName());

        System.out.println("Updated dataset is available online at " + CkanClient.makeDatasetUrl(myClient.getCatalogUrl(), dataset.getName()));
        
        
        
        
        
        String retorno = "Rodou";
		return retorno;
	}

	private void checkNotEmpty(String id, String string) {
		// TODO Auto-generated method stub
		
	}
	
}
