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
@Table(name="tb_parametro")
public class Parametro {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_parametro;
	
	@ManyToOne
	@JoinColumn(name="id_dataset")
	private ConjuntoDados dataset;


	@ManyToOne
	@JoinColumn(name="id_parametro_tipo")
	private Parametro_tipo parametro_tipo;
	
	@Column
	private String chave_parametro;
	
	@Column
	private String valor_parametro;

	public int getId_parametro() {
		return id_parametro;
	}

	public void setId_parametro(int id_parametro) {
		this.id_parametro = id_parametro;
	}

	public ConjuntoDados getDataset() {
		return dataset;
	}

	public void setDataset(ConjuntoDados dataset) {
		this.dataset = dataset;
	}

	public Parametro_tipo getParametro_tipo() {
		return parametro_tipo;
	}

	public void setParametro_tipo(Parametro_tipo parametro_tipo) {
		this.parametro_tipo = parametro_tipo;
	}

	public String getChave_parametro() {
		return chave_parametro;
	}

	public void setChave_parametro(String chave_parametro) {
		this.chave_parametro = chave_parametro;
	}

	public String getValor_parametro() {
		return valor_parametro;
	}

	public void setValor_parametro(String valor_parametro) {
		this.valor_parametro = valor_parametro;
	}
	
	
	

	
}
