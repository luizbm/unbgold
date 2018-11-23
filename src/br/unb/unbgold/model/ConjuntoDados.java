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
	private String titulo;
	@Column
	private String fonte;
	@Column
	private String parametros;
	@Column 
	private String iri;



	@ManyToOne
	@JoinColumn(name="id_frequencia")
	private Frequencia frequencia;
	
	@ManyToOne
	@JoinColumn(name="id_frequencia_dia")
	private Frequencia_dia frequencia_dia;
	
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
	
	@ManyToOne
	@JoinColumn(name="id_grupo")
	private Grupo grupo;
	
	
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
	
	@Column
	private Boolean rdf;
	
	@Column
	private String hora_publicacao;
	
	
	@Column
	private String documentacao;
	
	@Column
	private String cobertura_geografica;

	@Column
	private String cobertura_temporal;

	@Column
	private String granularidade_geografica;

	@Column
	private String 	granularidade_temporal;

	public int getId_dataset() {
		return id_dataset;
	}
	public void setId_dataset(int id_dataset) {
		this.id_dataset = id_dataset;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
	
	public Boolean getRdf() {
		return rdf;
	}
	public void setRdf(Boolean rdf) {
		this.rdf = rdf;
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
	public Frequencia getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(Frequencia frequencia) {
		this.frequencia = frequencia;
	}
	public Frequencia_dia getFrequencia_dia() {
		return frequencia_dia;
	}
	public void setFrequencia_dia(Frequencia_dia frequencia_dia) {
		this.frequencia_dia = frequencia_dia;
	}
	public String getHora_publicacao() {
		return hora_publicacao;
	}
	public void setHora_publicacao(String hora_publicacao) {
		this.hora_publicacao = hora_publicacao;
	}
	public String getDocumentacao() {
		return documentacao;
	}
	public void setDocumentacao(String documentacao) {
		this.documentacao = documentacao;
	}
	public String getCobertura_geografica() {
		return cobertura_geografica;
	}
	public void setCobertura_geografica(String cobertura_geografica) {
		this.cobertura_geografica = cobertura_geografica;
	}
	public String getCobertura_temporal() {
		return cobertura_temporal;
	}
	public void setCobertura_temporal(String cobertura_temporal) {
		this.cobertura_temporal = cobertura_temporal;
	}
	public String getGranularidade_geografica() {
		return granularidade_geografica;
	}
	public void setGranularidade_geografica(String granularidade_geografica) {
		this.granularidade_geografica = granularidade_geografica;
	}
	public String getGranularidade_temporal() {
		return granularidade_temporal;
	}
	public void setGranularidade_temporal(String granularidade_temporal) {
		this.granularidade_temporal = granularidade_temporal;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	
}