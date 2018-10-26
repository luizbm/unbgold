package br.unb.unbgold.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

@Entity
@Table(name="tb_dataset")
public class ConjuntoDados {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_dataset;
	@Column
	private String ds_dataset;
	@Column
	private String fonte;
	@Column
	private String parametros;
	@Column 
	private String iri;

	@Column 
	private String frequencia;

	@ManyToOne
	@JoinColumn(name="id_termo")
	private Termo termo;
	
	@ManyToOne
	@JoinColumn(name="id_instancia_ckan")
	private Instancia_ckan instancia_ckan;
	
	
	@ManyToOne
	@JoinColumn(name="id_termo_vcge")
	private Termo vcge;
	
	@ManyToOne
	@JoinColumn(name="id_orgao")
	private Orgao orgao;
	
	
	
	@Column 
	private String descricao;

	@Column 
	private String metodologia;

	@Column 
	private String idioma;
	
	@Column
	private String tags;
	
	@Column
	private Boolean concluido;
	
	@Column
	private Boolean automatizada;
	
	@Column
	private Boolean indexar_semantica;
	
	@Column
	private Boolean csv;
	
	@Column
	private Boolean json;
	
	
	public int getId_dataset() {
		return id_dataset;
	}
	public void setId_dataset(int id_dataset) {
		this.id_dataset = id_dataset;
	}
	public String getDs_dataset() {
		return ds_dataset;
	}
	public void setDs_dataset(String ds_dataset) {
		this.ds_dataset = ds_dataset;
	}
	public String getFonte() {
		return fonte;
	}
	public void setFonte(String fonte) {
		this.fonte = fonte;
	}
	public String getParametros() {
		return parametros;
	}
	public void setParametros(String parametros) {
		this.parametros = parametros;
	}
	public String getIri() {
		return iri;
	}
	public void setIri(String iri) {
		this.iri = iri;
	}
	
	public Termo getTermo() {
		return termo;
	}
	public void setTermo(Termo termo) {
		this.termo = termo;
	}
	public Boolean getConcluido() {
		return concluido;
	}
	public void setConcluido(Boolean concluido) {
		this.concluido = concluido;
	}
	public Instancia_ckan getInstancia_ckan() {
		return instancia_ckan;
	}
	public void setInstancia_ckan(Instancia_ckan instancia_ckan) {
		this.instancia_ckan = instancia_ckan;
	}
	public Termo getVcge() {
		return vcge;
	}
	public void setVcge(Termo vcge) {
		this.vcge = vcge;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getMetodologia() {
		return metodologia;
	}
	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public Boolean getAutomatizada() {
		return automatizada;
	}
	public void setAutomatizada(Boolean automatizada) {
		this.automatizada = automatizada;
	}
	public Boolean getIndexar_semantica() {
		return indexar_semantica;
	}
	public void setIndexar_semantica(Boolean indexar_semantica) {
		this.indexar_semantica = indexar_semantica;
	}
	public Boolean getCsv() {
		return csv;
	}
	public void setCsv(Boolean csv) {
		this.csv = csv;
	}
	public Boolean getJson() {
		return json;
	}
	public void setJson(Boolean json) {
		this.json = json;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Orgao getOrgao() {
		return orgao;
	}
	public void setOrgao(Orgao orgao) {
		this.orgao = orgao;
	}
	public String getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}	
	
}