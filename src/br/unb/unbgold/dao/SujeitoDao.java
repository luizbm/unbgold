package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Publicacao;
import br.unb.unbgold.model.Sujeito;
import br.unb.unbgold.model.Coluna;
import br.unb.unbgold.model.Dataset;

public class SujeitoDao extends Dao {

	public List<Sujeito> getAll() throws Exception {
		List<Sujeito> lista = new ArrayList<Sujeito>();
		session = sessionFactory.openSession();
		Query<Sujeito> query = session.createQuery("from Sujeito");
		lista = query.getResultList();
		return lista;
	}

	
	public List<Sujeito> getByPublicacaoId(int id ) throws Exception {
		List<Sujeito> sujeitos = new ArrayList<Sujeito>();
		session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(Sujeito.class);
		Publicacao publicacao = new Publicacao();
		publicacao.setId_publicacao(id);
		crit.add(Restrictions.eq("publicacao", publicacao));
		sujeitos = crit.list();
		return sujeitos;
	}

	
	public Sujeito get(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		return session.getReference(Sujeito.class, id);
	}

	public void add(Sujeito sujeito) throws Exception {	
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(sujeito);
		session.getTransaction().commit();
	}

	public void alter(Sujeito sujeito) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(sujeito);
		session.getTransaction().commit();		
	}

	public void delete(int id) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		Ontologia ontologia = session.getReference(Ontologia.class, id);
		session.delete(ontologia);
		session.getTransaction().commit();
	}
	
	public List<Sujeito> findByPublicacao(Publicacao publicacao){
		 List<Sujeito> sujeitos = new ArrayList<Sujeito>();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria crit = session.createCriteria(Sujeito.class);
			crit.add(Restrictions.eq("publicacao", publicacao));
			sujeitos = crit.list();
		 return sujeitos;
	}

}
