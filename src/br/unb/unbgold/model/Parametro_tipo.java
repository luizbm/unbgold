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
	private String desc_parametro_tipo;

	public int getId_parametro_tipo() {
		return id_parametro_tipo;
	}

	public void setId_parametro_tipo(int id_parametro_tipo) {
		this.id_parametro_tipo = id_parametro_tipo;
	}

	public String getDesc_parametro_tipo() {
		return desc_parametro_tipo;
	}

	public void setDesc_parametro_tipo(String desc_parametro_tipo) {
		this.desc_parametro_tipo = desc_parametro_tipo;
	}
	
	
}
