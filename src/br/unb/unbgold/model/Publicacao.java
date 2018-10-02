package br.unb.unbgold.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tb_publicacao")
public class Publicacao {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_publicacao;
	@ManyToOne
	@JoinColumn(name="id_dataset")
	private Dataset dataset;
	@Column
	private Date data_publicacao;
	@Column
	private Boolean ativo;
	
	public int getId_publicacao() {
		return id_publicacao;
	}
	public void setId_publicacao(int id_publicacao) {
		this.id_publicacao = id_publicacao;
	}
	public Dataset getDataset() {
		return dataset;
	}
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
	public Date getData_publicacao() {
		return data_publicacao;
	}
	public void setData_publicacao(Date data_publicacao) {
		this.data_publicacao = data_publicacao;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
		
	

	
}
