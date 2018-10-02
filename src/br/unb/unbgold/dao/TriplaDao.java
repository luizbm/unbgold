package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import br.unb.unbgold.model.Coluna;
import br.unb.unbgold.model.Dataset;
import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Publicacao;
=======
import org.hibernate.Query;

import br.unb.unbgold.model.Ontologia;
>>>>>>> e28b66aab49cb9b2b9551a2965582e7827efeb7d
import br.unb.unbgold.model.Tripla;

public class TriplaDao extends Dao {

	public List<Tripla> getAll() throws Exception {
		List<Tripla> lista = new ArrayList<Tripla>();
		session = sessionFactory.openSession();
		session.beginTransaction();
		Query<Tripla> query = session.createQuery("from Tripla");
		lista = query.getResultList();
		return lista;
	}

	public Tripla get(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		return session.getReference(Tripla.class, id);
	}

	public void add(Tripla tripla) throws Exception {	
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(tripla);
		session.getTransaction().commit();
	}

	public void alter(Tripla tripla) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(tripla);
		session.getTransaction().commit();		
	}

	public void delete(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		Ontologia ontologia = session.getReference(Ontologia.class, id);
		session.delete(ontologia);
		session.getTransaction().commit();
	}
	
	public List<Tripla> findByPublicacao(Publicacao publicacao){
		 List<Tripla> triplas = new ArrayList<Tripla>();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria crit = session.createCriteria(Tripla.class);
			crit.add(Restrictions.eq("publicacao", publicacao));
			triplas = crit.list();
		 return triplas;
	}
	
	
}
