package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Parametro;
import br.unb.unbgold.model.Termo;
import br.unb.unbgold.util.KeyValue;

public class TermoDao extends Dao {

	public List<Termo> getAll() throws Exception {
		List<Termo> lista = new ArrayList<Termo>();
		StartSession();
		Query<Termo> query = session.createQuery("from Termo");
		lista = query.getResultList();
		session.close();
		return lista;
	}

	public Termo get(int id) throws Exception {
		StartSession();
		Termo t = session.getReference(Termo.class, id);
		session.close();
		return t;
	}

	public void add(Termo termo) throws Exception {	
		StartSession();
		session.beginTransaction();
		session.save(termo);
		session.getTransaction().commit();
		session.close();
	}

	public void alter(Termo termo) throws Exception {
		StartSession();
		session.beginTransaction();
		session.update(termo);
		session.getTransaction().commit();		
		session.close();
	}

	public void delete(int id) throws Exception {
		StartSession();
		session.beginTransaction();
		Ontologia ontologia = session.getReference(Ontologia.class, id);
		session.delete(ontologia);
		session.getTransaction().commit();
		session.close();
	}

	public List<Termo> getBuscarPorIdsOntologia(List<KeyValue> ids) {
		StartSession();
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
		session.close();
		return retorno;
	}

	public List<Termo> buscarPorConjuntoDados(int id) {
		List<Termo> lista = new ArrayList<Termo>();
		StartSession();
	    String queryString = "SELECT t "
	    		+ " FROM Termo t "
	    		+ " WHERE t.id_termo IN ("
	    		+ "	SELECT c.termo.id_termo "
	    		+ " FROM Coluna c "
	    		+ " WHERE c.conjuntoDados.id_dataset =:id "
	    		+ ")";
	    Query<Termo> query = session.createQuery(queryString);
		query.setParameter("id", id);
		lista = query.getResultList();
		session.close();
		return lista;
	}
}
