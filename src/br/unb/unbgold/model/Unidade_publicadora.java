package br.unb.unbgold.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_unidade_publicadora")
public class Unidade_publicadora {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_unidade_publicadora;
	
	@Column
	private String nome_unidade_publicadora;
	
	@Column
	private String sigla;

	public int getId_unidade_publicadora() {
		return id_unidade_publicadora;
	}

	public void setId_unidade_publicadora(int id_unidade_publicadora) {
		this.id_unidade_publicadora = id_unidade_publicadora;
	}

	public String getNome_unidade_publicadora() {
		return nome_unidade_publicadora;
	}

	public void setNome_unidade_publicadora(String nome_unidade_publicadora) {
		this.nome_unidade_publicadora = nome_unidade_publicadora;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	
	
}
