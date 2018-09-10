package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.unb.unbgold.model.Nota;
import br.unb.unbgold.model.Ontologia;

public class OntologiaDao extends Dao {

	public List<Ontologia> getAll() throws Exception {
		List<Ontologia> lista = new ArrayList<Ontologia>();
		session = sessionFactory.openSession();
		session.beginTransaction();
		Query<Ontologia> query = session.createQuery("from Ontologia");
		lista = query.getResultList();
		return lista;
	}

	public Ontologia get(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		return session.getReference(Ontologia.class, id);
	}

	public void add(Ontologia ontologia) throws Exception {	
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(ontologia);
		session.getTransaction().commit();
	}

	public void alter(Ontologia ontologia) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(ontologia);
		session.getTransaction().commit();
		
	}

	public void delete(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		Nota nota = session.getReference(Nota.class, id);
		
		session.delete(nota);
		session.getTransaction().commit();
	}
}
