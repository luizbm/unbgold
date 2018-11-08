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
@Table(name="tb_coluna")
public class Coluna {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_coluna;
	
	@ManyToOne
	@JoinColumn(name="id_dataset")
	private ConjuntoDados conjuntoDados;
	@Column
	private String nm_campo;
	@Column
	private Boolean publicar;
	
	@ManyToOne
	@JoinColumn(name="id_termo")
	private Termo termo;

	@Column
	private String objeto_literal;
	
	@Column
	private Boolean complemento;
	
	@Column
	private int id_coluna_ligacao;
	
	public int getId_coluna() {
		return id_coluna;
	}

	public void setId_coluna(int id_coluna) {
		this.id_coluna = id_coluna;
	}

	public ConjuntoDados getConjuntoDados() {
		return conjuntoDados;
	}

	public void setConjuntoDados(ConjuntoDados conjuntoDados) {
		this.conjuntoDados = conjuntoDados;
	}

	public String getNm_campo() {
		return nm_campo;
	}

	public void setNm_campo(String nm_campo) {
		this.nm_campo = nm_campo;
	}

	public Boolean getPublicar() {
		return publicar;
	}

	public void setPublicar(Boolean publicar) {
		this.publicar = publicar;
	}


	public Termo getTermo() {
		return termo;
	}

	public void setTermo(Termo termo) {
		this.termo = termo;
	}

	public String getObjeto_literal() {
		return objeto_literal;
	}

	public void setObjeto_literal(String objeto_literal) {
		this.objeto_literal = objeto_literal;
	}

	public Boolean getComplemento() {
		return complemento;
	}

	public void setComplemento(Boolean complemento) {
		this.complemento = complemento;
	}

	public int getId_coluna_ligacao() {
		return id_coluna_ligacao;
	}

	public void setId_coluna_ligacao(int id_coluna_ligacao) {
		this.id_coluna_ligacao = id_coluna_ligacao;
	}

	

		
}
