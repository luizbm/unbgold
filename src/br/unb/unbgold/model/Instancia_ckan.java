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
@Table(name="tb_instancia_ckan")
public class Instancia_ckan {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_instancia_ckan;
	
	@ManyToOne
	@JoinColumn(name="id_unidade_publicadora")
	private Unidade_publicadora unidade_publicadora;
	@Column
	private String desc_instancia_ckan;
	@Column
	private String key_api;

	@Column
	private String endereco_ckan;

	public int getId_instancia_ckan() {
		return id_instancia_ckan;
	}

	public void setId_instancia_ckan(int id_instancia_ckan) {
		this.id_instancia_ckan = id_instancia_ckan;
	}

	public Unidade_publicadora getUnidade_publicadora() {
		return unidade_publicadora;
	}

	public void setUnidade_publicadora(Unidade_publicadora unidade_publicadora) {
		this.unidade_publicadora = unidade_publicadora;
	}

	public String getDesc_instancia_ckan() {
		return desc_instancia_ckan;
	}

	public void setDesc_instancia_ckan(String desc_instancia_ckan) {
		this.desc_instancia_ckan = desc_instancia_ckan;
	}

	
	public String getKey_api() {
		return key_api;
	}

	public void setKey_api(String key_api) {
		this.key_api = key_api;
	}

	public String getEndereco_ckan() {
		return endereco_ckan;
	}

	public void setEndereco_ckan(String endereco_ckan) {
		this.endereco_ckan = endereco_ckan;
	}
	
}
