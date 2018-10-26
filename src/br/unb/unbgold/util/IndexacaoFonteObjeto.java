package br.unb.unbgold.util;

import org.apache.jena.rdf.model.Resource;

import br.unb.unbgold.model.Termo;

public class IndexacaoFonteObjeto {

	Termo termo;
	Boolean literal;
	String valor;
	Resource root;
	
	public IndexacaoFonteObjeto(Termo termo, Boolean literal, String valor, Resource root) {
		super();
		this.termo = termo;
		this.literal = literal;
		this.valor = valor;
		this.root = root;
	}
	public Termo getTermo() {
		return termo;
	}
	public void setTermo(Termo termo) {
		this.termo = termo;
	}
	public Boolean getLiteral() {
		return literal;
	}
	public void setLiteral(Boolean literal) {
		this.literal = literal;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public Resource getRoot() {
		return root;
	}
	public void setRoot(Resource root) {
		this.root = root;
	}
		
}
