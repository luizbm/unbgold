package br.unb.unbgold.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_frequencia")
public class Frequencia {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_frequencia;
	
	@Column
	private String ds_frequencia;

	public int getId_frequencia() {
		return id_frequencia;
	}

	public void setId_frequencia(int id_frequencia) {
		this.id_frequencia = id_frequencia;
	}

	public String getDs_frequencia() {
		return ds_frequencia;
	}

	public void setDs_frequencia(String ds_frequencia) {
		this.ds_frequencia = ds_frequencia;
	}		
	
}
