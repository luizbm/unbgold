package br.unb.unbgold.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_dataset")
public class Dataset {
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
}