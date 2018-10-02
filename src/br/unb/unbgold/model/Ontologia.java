package br.unb.unbgold.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_ontologia")
public class Ontologia {

	@Id
	@Column(name="id_ontologia")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_ontologia;
	
	@Column(name="nm_ontologia")
	private String nm_ontologia;
	
	@Column(name="prefixo_ontologia")
	private String prefixo_ontologia;
	
	@Column(name="url_ontologia")
	private String url_ontologia;
	
	@Column(name="comentario")
	private String comentario;
	
	@Column(name="versao")
	private String versao;

	public int getId_ontologia() {
		return id_ontologia;
	}

	public void setId_ontologia(int id_ontologia) {
		this.id_ontologia = id_ontologia;
	}

	public String getNm_ontologia() {
		return nm_ontologia;
	}

	public void setNm_ontologia(String nm_ontologia) {
		this.nm_ontologia = nm_ontologia;
	}

	public String getPrefixo_ontologia() {
		return prefixo_ontologia;
	}

	public void setPrefixo_ontologia(String prefixo_ontologia) {
		this.prefixo_ontologia = prefixo_ontologia;
	}

	public String getUrl_ontologia() {
		return url_ontologia;
	}

	public void setUrl_ontologia(String url_ontologia) {
		this.url_ontologia = url_ontologia;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

}
