package br.unb.unbgold.util;

public class TabelaHorizontal {

	String coluna;
	String[] valores;
	
	public TabelaHorizontal(String coluna, String[] valores) {
		super();
		this.coluna = coluna;
		this.valores = valores;
	}
	public String getColuna() {
		return coluna;
	}
	public void setColuna(String coluna) {
		this.coluna = coluna;
	}
	public String[] getValores() {
		return valores;
	}
	public void setValores(String[] valores) {
		this.valores = valores;
	}
	
	
}
