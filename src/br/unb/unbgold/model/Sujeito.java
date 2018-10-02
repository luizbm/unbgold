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
@Table(name="tb_sujeito")
public class Sujeito {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_sujeito;
	
	@ManyToOne
	@JoinColumn(name="id_publicacao")
	private Publicacao publicacao;
	@Column
	private String desc_sujeito;
	public int getId_sujeito() {
		return id_sujeito;
	}
	public void setId_sujeito(int id_sujeito) {
		this.id_sujeito = id_sujeito;
	}
	public Publicacao getPublicacao() {
		return publicacao;
	}
	public void setPublicacao(Publicacao publicacao) {
		this.publicacao = publicacao;
	}
	public String getDesc_sujeito() {
		return desc_sujeito;
	}
	public void setDesc_sujeito(String desc_sujeito) {
		this.desc_sujeito = desc_sujeito;
	}
	
}
