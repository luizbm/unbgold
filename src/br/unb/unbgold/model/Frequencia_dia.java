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
@Table(name="tb_frequencia_dia")
public class Frequencia_dia {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_frequencia_dia;
	
	@Column
	private String ds_frequencia_dia;

	@ManyToOne
	@JoinColumn(name="id_frequencia")
	private Frequencia frequencia;

	public int getId_frequencia_dia() {
		return id_frequencia_dia;
	}

	public void setId_frequencia_dia(int id_frequencia_dia) {
		this.id_frequencia_dia = id_frequencia_dia;
	}

	public String getDs_frequencia_dia() {
		return ds_frequencia_dia;
	}

	public void setDs_frequencia_dia(String ds_frequencia_dia) {
		this.ds_frequencia_dia = ds_frequencia_dia;
	}

	public Frequencia getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(Frequencia frequencia) {
		this.frequencia = frequencia;
	}
	
}
