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
@Table(name="tb_objeto")
public class Objeto {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_objeto;

	@ManyToOne
	@JoinColumn(name="id_sujeito")
	private Sujeito sujeito;
	
	@ManyToOne
	@JoinColumn(name="id_termo")
	private Termo termo;
	
	@ManyToOne
	@JoinColumn(name="id_objeto_tipo")
	private Objeto_tipo objeto_tipo;

	@ManyToOne
	@JoinColumn(name="id_coluna")
	private Coluna coluna;
	
	@Column
	private String desc_objeto;

	public int getId_objeto() {
		return id_objeto;
	}

	public void setId_objeto(int id_objeto) {
		this.id_objeto = id_objeto;
	}

	public Sujeito getSujeito() {
		return sujeito;
	}

	public void setSujeito(Sujeito sujeito) {
		this.sujeito = sujeito;
	}

	public Termo getTermo() {
		return termo;
	}

	public void setTermo(Termo termo) {
		this.termo = termo;
	}

	public Objeto_tipo getObjeto_tipo() {
		return objeto_tipo;
	}

	public void setObjeto_tipo(Objeto_tipo objeto_tipo) {
		this.objeto_tipo = objeto_tipo;
	}

	public String getDesc_objeto() {
		return desc_objeto;
	}

	public void setDesc_objeto(String desc_objeto) {
		this.desc_objeto = desc_objeto;
	}

	public Coluna getColuna() {
		return coluna;
	}

	public void setColuna(Coluna coluna) {
		this.coluna = coluna;
	}

}
