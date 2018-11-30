package br.unb.unbgold.util;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import br.unb.unbgold.model.Coluna;
import br.unb.unbgold.model.Objeto;

public class TriplaUtil {

	private Resource root;
	private Property p;
	private String objeto;
	private Boolean literal;
	private Coluna coluna;
	private Boolean tipo;
	
	public TriplaUtil(Resource root2, Property property, String objeto, Boolean literal, Coluna coluna) {
		super();
		this.root = root2;
		this.p = property;
		this.objeto = objeto;
		this.literal = literal;
		this.coluna = coluna;
		this.tipo = false;
	}
	public Resource getRoot() {
		return root;
	}
	public void setRoot(Resource root) {
		this.root = root;
	}
	public Property getP() {
		return p;
	}
	public void setP(Property p) {
		this.p = p;
	}
	public String getObjeto() {
		return objeto;
	}
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
	public Boolean getLiteral() {
		return literal;
	}
	public void setLiteral(Boolean literal) {
		this.literal = literal;
	}
	public Coluna getColuna() {
		return coluna;
	}
	public void setColuna(Coluna coluna) {
		this.coluna = coluna;
	}
	public Boolean getTipo() {
		return tipo;
	}
	public void setTipo(Boolean tipo) {
		this.tipo = tipo;
	}
	
}
