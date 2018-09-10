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
@Table(name="tb_tripla")
public class Tripla {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_tripla;
	
	@ManyToOne
	@JoinColumn(name="id_dataset")
	private Dataset dataset;
	@Column
	private String sujeito;
	
	@ManyToOne
	@JoinColumn(name="predicado")
	private Termo predicado;
	
	@Column
	private String objeto;

	public int getId_tripla() {
		return id_tripla;
	}

	public void setId_tripla(int id_tripla) {
		this.id_tripla = id_tripla;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	public String getSujeito() {
		return sujeito;
	}

	public void setSujeito(String sujeito) {
		this.sujeito = sujeito;
	}

	public Termo getPredicado() {
		return predicado;
	}

	public void setPredicado(Termo predicado) {
		this.predicado = predicado;
	}

	public String getObjeto() {
		return objeto;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
}