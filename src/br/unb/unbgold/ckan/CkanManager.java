package br.unb.unbgold.ckan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.unb.unbgold.model.ConjuntoDados;
import br.unb.unbgold.model.Grupo;
import br.unb.unbgold.model.Orgao;
import br.unb.unbgold.model.Publicacao;
import eu.trentorise.opendata.jackan.CheckedCkanClient;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.model.CkanDatasetBase;
import eu.trentorise.opendata.jackan.model.CkanGroup;
import eu.trentorise.opendata.jackan.model.CkanOrganization;
import eu.trentorise.opendata.jackan.model.CkanResourceBase;
import eu.trentorise.opendata.jackan.model.CkanTag;

public class CkanManager {

	private String key_api;
	private String url_api;
	private CkanClient cli;
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
		retorno = cliente.getDataset(nome);
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
		resource.setUrl(pub.getFonte());
		resource.setFormat("CSV");
		resource.setSize("123456");
		
		//resource.setUpload(new File(cd.getFonte()), true);
		resource.setDescription(cd.getDescricao()+ new Random().nextLong());
		resource.setWebstoreUrl(cd.getFonte());
		cliente.createResource(resource);
		
		return resource;
	}
}
