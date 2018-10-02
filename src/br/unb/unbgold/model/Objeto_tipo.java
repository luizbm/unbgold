package br.unb.unbgold.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_objeto_tipo")
public class Objeto_tipo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_objeto_tipo;
	
	@Column
	private String desc_objeto_tipo;

	public int getId_objeto_tipo() {
		return id_objeto_tipo;
	}

	public void setId_objeto_tipo(int id_objeto_tipo) {
		this.id_objeto_tipo = id_objeto_tipo;
	}

	public String getDesc_objeto_tipo() {
		return desc_objeto_tipo;
	}

	public void setDesc_objeto_tipo(String desc_objeto_tipo) {
		this.desc_objeto_tipo = desc_objeto_tipo;
	}
	
	
}
