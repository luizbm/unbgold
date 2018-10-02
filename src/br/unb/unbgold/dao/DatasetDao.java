package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.unb.unbgold.model.Dataset;

public class DatasetDao extends Dao {

	public List<Dataset> getAll() throws Exception {
		List<Dataset> lista = new ArrayList<Dataset>();
		session = sessionFactory.openSession();
		session.beginTransaction();
		Query<Dataset> query = session.createQuery("from Dataset");
		lista = query.getResultList();
		return lista;
	}

	public Dataset get(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		return session.getReference(Dataset.class, id);
	}

	public void add(Dataset dataset) throws Exception {	
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(dataset);
		session.getTransaction().commit();
	}

	public void alter(Dataset dataset) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(dataset);
		session.getTransaction().commit();		
	}

	public void delete(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		Dataset dataset = session.getReference(Dataset.class, id);
		session.delete(dataset);
		session.getTransaction().commit();
	}
}
