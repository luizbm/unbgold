package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.unb.unbgold.model.Grupo;

public class GrupoDao extends Dao {

	public List<Grupo> getAll() throws Exception {
		List<Grupo> lista = new ArrayList<Grupo>();
		StartSession();
		Query<Grupo> query = session.createQuery("from Grupo");
		lista = query.getResultList();
		session.close();
		return lista;
	}
	
}
