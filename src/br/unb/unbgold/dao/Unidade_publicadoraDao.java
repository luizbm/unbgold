package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.unb.unbgold.model.Unidade_publicadora;

public class Unidade_publicadoraDao extends Dao {

	public List<Unidade_publicadora> getAll() throws Exception {
		List<Unidade_publicadora> lista = new ArrayList<Unidade_publicadora>();
		session = sessionFactory.openSession();
		session.beginTransaction();
		Query<Unidade_publicadora> query = session.createQuery("from Unidade_publicadora");
		lista = query.getResultList();
		return lista;
	}

	public Unidade_publicadora get(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		return session.getReference(Unidade_publicadora.class, id);
	}

	public void add(Unidade_publicadora unidade_publicadora) throws Exception {	
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(unidade_publicadora);
		session.getTransaction().commit();
	}

	public void alter(Unidade_publicadora unidade_publicadora) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(unidade_publicadora);
		session.getTransaction().commit();		
	}

	public void delete(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		Unidade_publicadora unidade_publicadora = session.getReference(Unidade_publicadora.class, id);
		session.delete(unidade_publicadora);
		session.getTransaction().commit();
	}
}
