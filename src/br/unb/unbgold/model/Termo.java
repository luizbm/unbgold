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
@Table(name="tb_termo")
public class Termo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_termo;
	
	@ManyToOne
	@JoinColumn(name="id_ontologia")
	private Ontologia ontologia;
	@Column
	private String nm_termo;
	@Column
	private String iri_termo;
	@Column
	private String comentario;
	
	public int getId_termo() {
		return id_termo;
	}
	public void setId_termo(int id_termo) {
		this.id_termo = id_termo;
	}
	public Ontologia getOntologia() {
		return ontologia;
	}
	public void setOntologia(Ontologia ontologia) {
		this.ontologia = ontologia;
	}
	public String getNm_termo() {
		return nm_termo;
	}
	public void setNm_termo(String nm_termo) {
		this.nm_termo = nm_termo;
	}
	public String getIri_termo() {
		return iri_termo;
	}
	public void setIri_termo(String iri_termo) {
		this.iri_termo = iri_termo;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}	
}