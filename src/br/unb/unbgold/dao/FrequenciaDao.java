package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.unb.unbgold.model.Coluna;
import br.unb.unbgold.model.Frequencia;
import br.unb.unbgold.model.Frequencia_dia;

public class FrequenciaDao extends Dao {

	public List<Frequencia> getAll() throws Exception {
		List<Frequencia> lista = new ArrayList<Frequencia>();
		StartSession();
		Query<Frequencia> query = session.createQuery("from Frequencia");
		lista = query.getResultList();
		session.close();
		return lista;
	}
	
	public List<Frequencia_dia> getDiasDaFrequencia(int id) throws Exception {
		List<Frequencia_dia> lista = new ArrayList<Frequencia_dia>();
		StartSession();
		Query<Frequencia_dia> query = session.createQuery("from Frequencia_dia WHERE frequencia.id_frequencia = :id ");
		query.setParameter("id", id);
		lista = query.getResultList();
		session.close();
		return lista;
	}

}
