package br.unb.unbgold.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_grupo")
public class Grupo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_grupo;
	
	@Column
	private String ds_grupo;

	public int getId_grupo() {
		return id_grupo;
	}


	public void setId_grupo(int id_grupo) {
		this.id_grupo = id_grupo;
	}


	public String getDs_grupo() {
		return ds_grupo;
	}


	public void setDs_grupo(String ds_grupo) {
		this.ds_grupo = ds_grupo;
	}

	
}
