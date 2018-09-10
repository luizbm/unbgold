package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.unb.unbgold.model.Nota;

public class NotaDao extends Dao {

	public List<Nota> getAll() throws Exception {
		List<Nota> lista = new ArrayList<Nota>();
		session = sessionFactory.openSession();
		session.beginTransaction();
		Query<Nota> query = session.createQuery("from Nota");
		lista = query.getResultList();
		return lista;
	}

	public Nota getNota(int idNota) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		return session.getReference(Nota.class, idNota);
	}

	public void addNota(Nota nota) throws Exception {	
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(nota);
		session.getTransaction().commit();
	}

	public void alterNota(Nota nota) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(nota);
		session.getTransaction().commit();
		
	}

	public void delNota(int idNota) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		Nota nota = session.getReference(Nota.class, idNota);
		
		session.delete(nota);
		session.getTransaction().commit();
	}
}
