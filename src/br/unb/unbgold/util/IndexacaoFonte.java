package br.unb.unbgold.util;

import java.util.List;

import br.unb.unbgold.model.Termo;

public class IndexacaoFonte {
	
	private IndexacaoFonteObjeto codigo;
	private IndexacaoFonteObjeto title;
	private IndexacaoFonteObjeto descricao;
	private IndexacaoFonteObjeto autor;
	private IndexacaoFonteObjeto vcge;
	private IndexacaoFonteObjeto fonte;
	private List<IndexacaoFonteObjeto> formatos;
	private IndexacaoFonteObjeto frequencia;
	private List<IndexacaoFonteObjeto> relacoes;
	private List<IndexacaoFonteObjeto> vocabularios;
	private List<IndexacaoFonteObjeto> tags;
	private IndexacaoFonteObjeto tipo;
	private IndexacaoFonteObjeto data;
	private IndexacaoFonteObjeto idioma;
	private IndexacaoFonteObjeto publicacao;
	public IndexacaoFonteObjeto getCodigo() {
		return codigo;
	}
	public void setCodigo(Termo termo, Boolean literal, String valor) {
		this.codigo = new IndexacaoFonteObjeto(termo, literal, valor, null);
	}
	public IndexacaoFonteObjeto getTitle() {
		return title;
	}
	public void setTitle(Termo termo, Boolean literal, String valor) {
		this.title = new IndexacaoFonteObjeto(termo, literal, valor, null);
	}
	public IndexacaoFonteObjeto getDescricao() {
		return descricao;
	}
	public void setDescricao(Termo termo, Boolean literal, String valor) {
		this.descricao = new IndexacaoFonteObjeto(termo, literal, valor, null);
	}
	public IndexacaoFonteObjeto getAutor() {
		return autor;
	}
	public void setAutor(Termo termo, Boolean literal, String valor) {
		this.autor = new IndexacaoFonteObjeto(termo, literal, valor, null);
	}
	public IndexacaoFonteObjeto getVcge() {
		return vcge;
	}
	public void setVcge(Termo termo, Boolean literal, String valor) {
		this.vcge = new IndexacaoFonteObjeto(termo, literal, valor, null);
	}
	public IndexacaoFonteObjeto getFonte() {
		return fonte;
	}
	public void setFonte(Termo termo, Boolean literal, String valor) {
		this.fonte = new IndexacaoFonteObjeto(termo, literal, valor, null);
	}
	public List<IndexacaoFonteObjeto> getFormatos() {
		return formatos;
	}
	public void setFormatos(List<IndexacaoFonteObjeto> formatos) {
		this.formatos = formatos;
	}
	public IndexacaoFonteObjeto getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(Termo termo, Boolean literal, String valor) {
		this.frequencia = new IndexacaoFonteObjeto(termo, literal, valor, null);
	}
	public List<IndexacaoFonteObjeto> getRelacoes() {
		return relacoes;
	}
	public void setRelacoes(List<IndexacaoFonteObjeto> relacoes) {
		this.relacoes = relacoes;
	}
	public List<IndexacaoFonteObjeto> getVocabularios() {
		return vocabularios;
	}
	public void setVocabularios(List<IndexacaoFonteObjeto> vocabularios) {
		this.vocabularios = vocabularios;
	}
	public List<IndexacaoFonteObjeto> getTags() {
		return tags;
	}
	public void setTags(List<IndexacaoFonteObjeto> tags) {
		this.tags = tags;
	}
	public IndexacaoFonteObjeto getTipo() {
		return tipo;
	}
	public void setTipo(Termo termo, Boolean literal, String valor) {
		this.tipo = new IndexacaoFonteObjeto(termo, literal, valor, null);
	}
	public IndexacaoFonteObjeto getData() {
		return data;
	}
	public void setData(Termo termo, Boolean literal, String valor) {
		this.data = new IndexacaoFonteObjeto(termo, literal, valor, null);
	}
	public IndexacaoFonteObjeto getIdioma() {
		return idioma;
	}
	public void setIdioma(Termo termo, Boolean literal, String valor) {
		this.idioma = new IndexacaoFonteObjeto(termo, literal, valor, null);
	}
	public IndexacaoFonteObjeto getPublicacao() {
		return publicacao;
	}
	public void setPublicacao(Termo termo, Boolean literal, String valor) {
		this.publicacao = new IndexacaoFonteObjeto(termo, literal, valor, null);
	}
	
}
