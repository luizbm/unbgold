package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Termo;
import br.unb.unbgold.util.KeyValue;

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

	public List<Termo> getBuscarPorIdsOntologia(List<KeyValue> ids) {
		session = sessionFactory.openSession();
		List<Termo> retorno = new ArrayList<Termo>();
		if(ids.size() > 0) {
			String queryString = "SELECT t "
					+ " FROM  Termo t "
					+ " WHERE t.ontologia.id_ontologia IN(0 ";
			for(KeyValue kv : ids) {
				queryString += ", "+kv.getValue().toString();
			}
			queryString += ")";
			Query<Termo> query = session.createQuery(queryString);
			retorno = query.list();
			
		}
		
		return retorno;
	}
}
