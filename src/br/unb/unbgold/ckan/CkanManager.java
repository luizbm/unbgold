package br.unb.unbgold.ckan;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import br.unb.unbgold.model.ConjuntoDados;
import br.unb.unbgold.model.Grupo;
import br.unb.unbgold.model.Orgao;
import br.unb.unbgold.model.Publicacao;
import br.unb.unbgold.util.ManagerFiles;
import eu.trentorise.opendata.jackan.CheckedCkanClient;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.internal.org.apache.http.HttpEntity;
import eu.trentorise.opendata.jackan.internal.org.apache.http.HttpResponse;
import eu.trentorise.opendata.jackan.internal.org.apache.http.client.HttpClient;
import eu.trentorise.opendata.jackan.internal.org.apache.http.client.methods.HttpPost;
import eu.trentorise.opendata.jackan.internal.org.apache.http.entity.ContentType;
import eu.trentorise.opendata.jackan.internal.org.apache.http.entity.mime.MultipartEntityBuilder;
import eu.trentorise.opendata.jackan.internal.org.apache.http.entity.mime.content.ContentBody;
import eu.trentorise.opendata.jackan.internal.org.apache.http.entity.mime.content.FileBody;
import eu.trentorise.opendata.jackan.internal.org.apache.http.entity.mime.content.StringBody;
import eu.trentorise.opendata.jackan.internal.org.apache.http.impl.client.DefaultHttpClient;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanDatasetBase;
import eu.trentorise.opendata.jackan.model.CkanGroup;
import eu.trentorise.opendata.jackan.model.CkanOrganization;
import eu.trentorise.opendata.jackan.model.CkanResource;
import eu.trentorise.opendata.jackan.model.CkanResourceBase;
import eu.trentorise.opendata.jackan.model.CkanTag;

public class CkanManager {

	private String key_api;
	private String url_api;
	private CkanClient cli;
	
	private String fileupload = "C:\\Users\\00415102162\\git\\unbgold\\teste.csv";
	
	public String getKey_api() {
		return key_api;
	}
	public void setKey_api(String key_api) {
		this.key_api = key_api;
	}
	public String getUrl_api() {
		return url_api;
	}
	public void setUrl_api(String url_api) {
		this.url_api = url_api;
	}
	
	
	public CkanDatasetBase getDataset(String nome) {
		CkanClient cliente = this.getCliente();
		CkanDatasetBase retorno;
		retorno = cliente.getDataset(nome.toLowerCase().replace(" ", "-"));
		return retorno;
	}
	
	private CkanClient getCliente() {
		
		if(this.cli == null) {
			this.cli = new CheckedCkanClient(this.url_api, this.key_api);
		}
		return this.cli;
	}
	public CkanDatasetBase criarDataset(ConjuntoDados cd, Publicacao pub) {
		CkanClient cliente = getCliente();
		CkanDatasetBase retorno = new CkanDatasetBase();
		Orgao orgao = cd.getOrgao();
		retorno.setOwnerOrg(this.criarOrganizacao(orgao));
		retorno.setAuthor(orgao.getAutor());
		retorno.setAuthorEmail(orgao.getEmail());
		// garantidno que os grupos existem
		//List<CkanGroup> grupos = ;
		retorno.setGroups(this.criarGrupos(cd));
		retorno.setTags(this.criarTags(cd.getTags()));
		retorno.setLicenseId("cc-by");
		retorno.setName(cd.getTitulo().toLowerCase().replace(" ", "-"));
		retorno.setTitle(cd.getTitulo());
		//retorno.set
		try {
			cliente.getDataset(retorno.getName());
		} catch (Exception e) {
			retorno = cliente.createDataset(retorno);
		}
		return retorno;
		
	}
	
	
	
	private List<CkanTag> criarTags(String tags) {
		// TODO Auto-generated method stub
		
		List<CkanTag> retorno =  new ArrayList<CkanTag>();
		String[] marc = tags.split(",");
		for(int i=0;i<marc.length;i++) {
			CkanTag ct = new CkanTag(marc[i]);
			retorno.add(ct);
		}
		
		return retorno;
	}
	private List<CkanGroup> criarGrupos(ConjuntoDados cd) {
		List<CkanGroup> grupos = new ArrayList<CkanGroup>();
		CkanGroup gru = new CkanGroup(cd.getGrupo().getDs_grupo().toLowerCase());
		gru.setDisplayName(cd.getGrupo().getDs_grupo());
		grupos.add(gru);
		
		CkanClient cliente = getCliente();
		CkanGroup group = new CkanGroup();
		for(CkanGroup g : grupos) {
			try {
				group = cliente.getGroup(g.getName());
			}catch (Exception e) {
				group = cliente.createGroup(g);
				System.out.println(group.getDisplayName());
				// TODO: handle exception
			}
		}
		return grupos;
	}
	private String criarOrganizacao(Orgao orgao) {
		CkanClient cliente = getCliente();
		CkanOrganization o;
		try {
		o = cliente.getOrganization(orgao.getSigla_orgao().toLowerCase());
		}catch (Exception e) {
			o = new CkanOrganization();
			o.setDisplayName(orgao.getSigla_orgao()+" "+orgao.getNm_orgao());
			o.setName(orgao.getSigla_orgao().toLowerCase());
			o = cliente.createOrganization(o);
		}
		return o.getName();
	}
	public CkanResourceBase saveResource(ConjuntoDados cd, Publicacao pub, CkanDatasetBase dataset) {
		CkanResourceBase resource = new CkanResourceBase("upload", dataset.getId());
		//File file = null;
		
		CkanClient cliente = getCliente();
		resource.setSize("123456");
		//resource.setUpload(new File(cd.getFonte()), true);
		resource.setDescription(cd.getDescricao());
		//resource.setWebstoreUrl(cd.getFonte());
		Integer id = (int) new Random().nextLong();
		
		if(cd.getJson()) {
			//resource.setPackageId("aaaaaa");
			File file = ManagerFiles.pegaArquivo(pub.getNm_arquivo()+".json");
			if(file != null) {
				Long tamanho = file.length();
				resource.setSize(tamanho.toString());
				resource.setUrl(pub.getFonte()+".json");
				resource.setFormat("json");
				cliente.createResource(resource);
			}
		}
		if(cd.getCsv()) {
			File file = ManagerFiles.pegaArquivo(pub.getNm_arquivo()+".csv");
			if(file != null) {
				Long tamanho = file.length();
				resource.setSize(tamanho.toString());
				resource.setUrl(pub.getFonte()+".csv");
				resource.setFormat("csv");
				cliente.createResource(resource);
			}
		}

		if(cd.getRdf()) {
			File file = ManagerFiles.pegaArquivo(pub.getNm_arquivo()+".rdf");
			if(file != null) {
				Long tamanho = file.length();
				resource.setSize(tamanho.toString());
				resource.setUrl(pub.getFonte()+".rdf");
				resource.setFormat("rdf");
				cliente.createResource(resource);
			}
		}

		resource.setPackageId("aaaaaa");
		
		
		return resource;
	}
	
	public void MostraTodosInstancias(String ckan) {
		CkanClient cc = new CkanClient(ckan);
		List<String> dl = cc.getDatasetList();
		for(String ds: dl) {
			System.out.println(ds);
			CkanDataset dc =cc.getDataset(ds);
			List<CkanResource> crs = dc.getResources();
			for(CkanResource cr : crs){
				System.out.println(cr);
			}
		}
		System.out.println(cc.getDatasetList());
	}
	
	@SuppressWarnings("deprecation")
	public void testarUplad() {
		this.url_api = "http://164.41.101.40:8002";
		this.key_api = "619c3f13-3972-4446-98c3-08fa0a06b0eb";
		HttpClient httpClient = new DefaultHttpClient();
		Date now = new Date();
		File file = new File(this.fileupload);
		//FileBody bin = new FileBody(file, "csv");
		SimpleDateFormat dateFormatGmt = new  SimpleDateFormat("yyyyMMdd_HHmmss");
	    String date=dateFormatGmt.format(new Date());
	    try {
	    	   ContentBody cbFile = new FileBody(file, ContentType.TEXT_HTML);
	           HttpEntity reqEntity = MultipartEntityBuilder.create()
	            .addPart("file", cbFile )
	            .addPart("key", new StringBody(this.fileupload+date))
	            .addPart("package_id",new StringBody("test2"))
	            .addPart("url",new StringBody(this.url_api+"/files/"+date+"/test.txt"))         
	            .build();

	           HttpPost postRequest = new HttpPost(this.url_api+"/api/action/resource_create");
	           postRequest.setEntity(reqEntity);
	           postRequest.setHeader("X-CKAN-API-Key", this.key_api);

	           HttpResponse response = httpClient.execute(postRequest);
	           int statusCode = response.getStatusLine().getStatusCode();
	           BufferedReader br = new BufferedReader(
	                   new InputStreamReader((response.getEntity().getContent())));

	           String line;
	           while ((line = br.readLine()) != null) {
	             System.out.println("+"+line);
	           }
	           if(statusCode!=200){
	              System.out.println("statusCode =!=" +statusCode);
	           }
	    }catch (IOException ioe) {
	    System.out.println(ioe);
	    } finally {
	    	httpClient.getConnectionManager().shutdown();
	    }
	    
	}
}
