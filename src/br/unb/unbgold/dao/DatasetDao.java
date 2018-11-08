package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.unb.unbgold.model.ConjuntoDados;

public class DatasetDao extends Dao {

	public List<ConjuntoDados> getAll() throws Exception {
		List<ConjuntoDados> lista = new ArrayList<ConjuntoDados>();
		session = sessionFactory.openSession();
		session.beginTransaction();
		Query<ConjuntoDados> query = session.createQuery("from ConjuntoDados");
		lista = query.getResultList();
		return lista;
	}

	public ConjuntoDados get(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		return session.getReference(ConjuntoDados.class, id);
	}

	public void add(ConjuntoDados dataset) throws Exception {	
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(dataset);
		session.getTransaction().commit();
	}

	public void alter(ConjuntoDados dataset) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(dataset);
		session.getTransaction().commit();		
	}

	public void delete(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		ConjuntoDados dataset = session.getReference(ConjuntoDados.class, id);
		session.delete(dataset);
		session.getTransaction().commit();
	}
}
