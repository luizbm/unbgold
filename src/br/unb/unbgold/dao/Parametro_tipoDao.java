package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Parametro;
import br.unb.unbgold.model.Parametro_tipo;
import br.unb.unbgold.model.ConjuntoDados;

public class Parametro_tipoDao extends Dao {

	public List<Parametro_tipo> getAll() throws Exception {
		List<Parametro_tipo> lista = new ArrayList<Parametro_tipo>();
		session = sessionFactory.openSession();
		session.beginTransaction();
		Query<Parametro_tipo> query = session.createQuery("from Parametro_tipo");
		lista = query.getResultList();
		return lista;
	}

	public Parametro_tipo get(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		return session.getReference(Parametro_tipo.class, id);
	}

	public void add(Parametro_tipo parametro_tipo) throws Exception {	
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(parametro_tipo);
		session.getTransaction().commit();
	}

	public void alter(Parametro_tipo parametro_tipo) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(parametro_tipo);
		session.getTransaction().commit();		
	}

	public void delete(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		Parametro_tipo parametro_tipo = session.getReference(Parametro_tipo.class, id);
		session.delete(parametro_tipo);
		session.getTransaction().commit();
	}

}
