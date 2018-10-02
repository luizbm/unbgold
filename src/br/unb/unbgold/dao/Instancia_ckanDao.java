package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Unidade_publicadora;
import br.unb.unbgold.model.Instancia_ckan;
import br.unb.unbgold.model.Dataset;

public class Instancia_ckanDao extends Dao {

	public List<Instancia_ckan> getAll() throws Exception {
		List<Instancia_ckan> lista = new ArrayList<Instancia_ckan>();
		session = sessionFactory.openSession();
		session.beginTransaction();
		Query<Instancia_ckan> query = session.createQuery("from Instancia_ckan");
		lista = query.getResultList();
		return lista;
	}

	public Instancia_ckan get(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		return session.getReference(Instancia_ckan.class, id);
	}

	public void add(Instancia_ckan instancia_ckan) throws Exception {	
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(instancia_ckan);
		session.getTransaction().commit();
	}

	public void alter(Instancia_ckan instancia_ckan) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(instancia_ckan);
		session.getTransaction().commit();		
	}

	public void delete(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		Ontologia ontologia = session.getReference(Ontologia.class, id);
		session.delete(ontologia);
		session.getTransaction().commit();
	}
	
	public List<Instancia_ckan> findByUnidadePublicadora(Unidade_publicadora unidade_publicadora){
		 List<Instancia_ckan> instancia_ckans = new ArrayList<Instancia_ckan>();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria crit = session.createCriteria(Instancia_ckan.class);
			crit.add(Restrictions.eq("unidade_publicadora", unidade_publicadora));
			instancia_ckans = crit.list();
		 return instancia_ckans;
	}
}
