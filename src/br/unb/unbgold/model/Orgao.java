package br.unb.unbgold.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_orgao")
public class Orgao {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_orgao;
	
	
	@Column
	private String nm_orgao;
	
	@Column
	private String sigla_orgao;

	public int getId_orgao() {
		return id_orgao;
	}

	public void setId_orgao(int id_orgao) {
		this.id_orgao = id_orgao;
	}

	public String getNm_orgao() {
		return nm_orgao;
	}

	public void setNm_orgao(String nm_orgao) {
		this.nm_orgao = nm_orgao;
	}

	public String getSigla_orgao() {
		return sigla_orgao;
	}

	public void setSigla_orgao(String sigla_orgao) {
		this.sigla_orgao = sigla_orgao;
	}
	
		
}
