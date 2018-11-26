package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.unb.unbgold.model.ConjuntoDados;
import br.unb.unbgold.model.Termo;
import br.unb.unbgold.util.KeyValue;

public class DatasetDao extends Dao {

	public List<ConjuntoDados> getAll() throws Exception {
		List<ConjuntoDados> lista = new ArrayList<ConjuntoDados>();
		StartSession();
		Query<ConjuntoDados> query = session.createQuery("from ConjuntoDados");
		lista = query.getResultList();
		session.close();
		return lista;
	}

	public ConjuntoDados get(int id) throws Exception {
		StartSession();
		return session.getReference(ConjuntoDados.class, id);
	}

	public void add(ConjuntoDados dataset) throws Exception {	
		StartSession();
		session.beginTransaction();
		session.save(dataset);
		session.getTransaction().commit();
	}

	public void alter(ConjuntoDados dataset) throws Exception {
		StartSession();
		session.beginTransaction();
		session.update(dataset);
		session.getTransaction().commit();		
	}

	public void delete(int id) throws Exception {
		StartSession();
		session.beginTransaction();
		ConjuntoDados dataset = session.getReference(ConjuntoDados.class, id);
		session.delete(dataset);
		session.getTransaction().commit();
	}

	public List<ConjuntoDados> buscaPorTipo(int id) {
		StartSession();
		List<ConjuntoDados> retorno = new ArrayList<ConjuntoDados>();
		String queryString = "SELECT c "
			+ " FROM  ConjuntoDados c "
			+ " WHERE c.termo.id_termo = :id ";
		Query<ConjuntoDados> query = session.createQuery(queryString);
		query.setParameter("id", id);
		retorno = query.list();
		session.close();
		return retorno;
		
	}
}
