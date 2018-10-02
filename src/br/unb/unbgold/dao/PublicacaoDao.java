package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Publicacao;
import br.unb.unbgold.model.Dataset;

public class PublicacaoDao extends Dao {

	public List<Publicacao> getAll() throws Exception {
		List<Publicacao> lista = new ArrayList<Publicacao>();
		session = sessionFactory.openSession();
		Query<Publicacao> query = session.createQuery("from Publicacao");
		lista = query.getResultList();
		return lista;
	}

	public Publicacao get(int id) throws Exception {
		session = sessionFactory.openSession();
		return session.getReference(Publicacao.class, id);
	}

	public void add(Publicacao publicacao) throws Exception {	
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(publicacao);
		session.getTransaction().commit();
	}

	public void alter(Publicacao publicacao) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(publicacao);
		session.getTransaction().commit();		
	}

	public void delete(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		Ontologia ontologia = session.getReference(Ontologia.class, id);
		session.delete(ontologia);
		session.getTransaction().commit();
	}
	
	public List<Publicacao> findByDataset(Dataset dataset){
		 List<Publicacao> publicacaos = new ArrayList<Publicacao>();
			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(Publicacao.class);
			crit.add(Restrictions.eq("dataset", dataset));
			publicacaos = crit.list();
		 return publicacaos;
	}
}
