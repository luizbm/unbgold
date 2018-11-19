package br.unb.unbgold.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_parametro_temporal")
public class Parametro_temporal {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_parametro_temporal;
	
	@Column
	private String ds_parametro_temporal;

	public int getId_parametro_temporal() {
		return id_parametro_temporal;
	}

	public void setId_parametro_temporal(int id_parametro_temporal) {
		this.id_parametro_temporal = id_parametro_temporal;
	}

	public String getDs_parametro_temporal() {
		return ds_parametro_temporal;
	}

	public void setDs_parametro_temporal(String ds_parametro_temporal) {
		this.ds_parametro_temporal = ds_parametro_temporal;
	}
	
}
