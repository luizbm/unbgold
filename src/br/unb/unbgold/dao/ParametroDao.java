package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import br.unb.unbgold.model.ConjuntoDados;
import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Parametro;

public class ParametroDao extends Dao {

	public List<Parametro> getAll() throws Exception {
		List<Parametro> lista = new ArrayList<Parametro>();
		StartSession();
		session.beginTransaction();
		Query<Parametro> query = session.createQuery("from Parametro");
		lista = query.getResultList();
		session.close();
		return lista;
	}
	
	public List<Parametro> getPorConjuntoDeDados(int id) throws Exception {
		List<Parametro> lista = new ArrayList<Parametro>();
		StartSession();
		session.beginTransaction();
		Query<Parametro> query = session.createQuery("from Parametro WHERE dataset.id_dataset =:id ");
		query.setParameter("id", id);
		lista = query.getResultList();
		session.close();
		return lista;
	}

	public Parametro get(int id) throws Exception {
		StartSession();
		session.beginTransaction();
		Parametro p = session.getReference(Parametro.class, id);
		session.close();
		return p;
	}

	public void add(Parametro parametro) throws Exception {	
		StartSession();
		session.beginTransaction();
		session.save(parametro);
		session.getTransaction().commit();
		session.close();
	}

	public void alter(Parametro parametro) throws Exception {
		StartSession();
		session.beginTransaction();
		session.update(parametro);
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
	
	public List<Parametro> findByDataset(ConjuntoDados dataset){
		 List<Parametro> parametros = new ArrayList<Parametro>();
		 StartSession();
			session.beginTransaction();
			Criteria crit = session.createCriteria(Parametro.class);
			crit.add(Restrictions.eq("dataset", dataset));
			parametros = crit.list();
			session.close();
		 return parametros;
	}
}
