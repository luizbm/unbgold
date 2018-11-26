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
import br.unb.unbgold.model.ConjuntoDados;

public class SujeitoDao extends Dao {

	public List<Sujeito> getAll() throws Exception {
		List<Sujeito> lista = new ArrayList<Sujeito>();
		StartSession();
		Query<Sujeito> query = session.createQuery("from Sujeito");
		lista = query.getResultList();
		session.close();
		return lista;
	}

	
	public List<Sujeito> getByPublicacaoId(int id ) throws Exception {
		List<Sujeito> sujeitos = new ArrayList<Sujeito>();
		StartSession();
		Criteria crit = session.createCriteria(Sujeito.class);
		Publicacao publicacao = new Publicacao();
		publicacao.setId_publicacao(id);
		crit.add(Restrictions.eq("publicacao", publicacao));
		sujeitos = crit.list();
		session.close();
		return sujeitos;
	}

	
	public Sujeito get(int id) throws Exception {
		StartSession();
		Sujeito s = session.getReference(Sujeito.class, id);
		return s;
	}

	public void add(Sujeito sujeito) throws Exception {	
		StartSession();
		session.beginTransaction();
		session.save(sujeito);
		session.getTransaction().commit();
		session.close();
	}

	public void alter(Sujeito sujeito) throws Exception {
		StartSession();
		session.beginTransaction();
		session.update(sujeito);
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
	
	public List<Sujeito> findByPublicacao(Publicacao publicacao){
		 List<Sujeito> sujeitos = new ArrayList<Sujeito>();
		 StartSession();
			session.beginTransaction();
			Criteria crit = session.createCriteria(Sujeito.class);
			crit.add(Restrictions.eq("publicacao", publicacao));
			sujeitos = crit.list();
			session.close();
		 return sujeitos;
	}

}
