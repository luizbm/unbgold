package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Coluna;
import br.unb.unbgold.model.ConjuntoDados;

public class ColunaDao extends Dao {

	public List<Coluna> getAll() throws Exception {
		List<Coluna> lista = new ArrayList<Coluna>();
		session = sessionFactory.openSession();
		session.beginTransaction();
		Query<Coluna> query = session.createQuery("from Coluna");
		lista = query.getResultList();
		return lista;
	}

	public Coluna get(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		return session.getReference(Coluna.class, id);
	}

	public void add(Coluna coluna) throws Exception {	
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(coluna);
		session.getTransaction().commit();
	}

	public void alter(Coluna coluna) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(coluna);
		session.getTransaction().commit();		
	}

	public void delete(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		Ontologia ontologia = session.getReference(Ontologia.class, id);
		session.delete(ontologia);
		session.getTransaction().commit();
	}
	
	public List<Coluna> findByDataset(ConjuntoDados dataset){
		 List<Coluna> colunas = new ArrayList<Coluna>();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria crit = session.createCriteria(Coluna.class);
			crit.add(Restrictions.eq("conjuntoDados", dataset));
			colunas = crit.list();
		 return colunas;
	}
}
