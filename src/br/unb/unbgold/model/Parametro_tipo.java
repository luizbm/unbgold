package br.unb.unbgold.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_parametro_tipo")
public class Parametro_tipo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_parametro_tipo;
	
	@Column
	private String ds_parametro_tipo;

	public int getId_parametro_tipo() {
		return id_parametro_tipo;
	}

	public void setId_parametro_tipo(int id_parametro_tipo) {
		this.id_parametro_tipo = id_parametro_tipo;
	}

	public String getDs_parametro_tipo() {
		return ds_parametro_tipo;
	}

	public void setDs_parametro_tipo(String ds_parametro_tipo) {
		this.ds_parametro_tipo = ds_parametro_tipo;
	}

	
}
