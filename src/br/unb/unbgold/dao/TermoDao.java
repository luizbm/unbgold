package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Termo;

public class TermoDao extends Dao {

	public List<Termo> getAll() throws Exception {
		List<Termo> lista = new ArrayList<Termo>();
		session = sessionFactory.openSession();
		session.beginTransaction();
		Query<Termo> query = session.createQuery("from Termo");
		lista = query.getResultList();
		return lista;
	}

	public Termo get(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		return session.getReference(Termo.class, id);
	}

	public void add(Termo termo) throws Exception {	
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(termo);
		session.getTransaction().commit();
	}

	public void alter(Termo termo) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(termo);
		session.getTransaction().commit();		
	}

	public void delete(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		Ontologia ontologia = session.getReference(Ontologia.class, id);
		session.delete(ontologia);
		session.getTransaction().commit();
	}
}
