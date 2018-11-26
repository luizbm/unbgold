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
import br.unb.unbgold.model.Objeto;
import br.unb.unbgold.model.Coluna;
import br.unb.unbgold.model.ConjuntoDados;

public class ObjetoDao extends Dao {

	public List<Objeto> getAll() throws Exception {
		List<Objeto> lista = new ArrayList<Objeto>();
		StartSession();
		Query<Objeto> query = session.createQuery("from Objeto");
		lista = query.getResultList();
		return lista;
	}

	public Objeto get(int id) throws Exception {
		StartSession();
		return session.getReference(Objeto.class, id);
	}

	public void add(Objeto objeto) throws Exception {	
		StartSession();
		session.beginTransaction();
		session.save(objeto);
		session.getTransaction().commit();
	}

	public void alter(Objeto objeto) throws Exception {
		StartSession();
		session.beginTransaction();
		session.update(objeto);
		session.getTransaction().commit();		
	}

	public void delete(int id) throws Exception {
		StartSession();
		session.beginTransaction();
		Ontologia ontologia = session.getReference(Ontologia.class, id);
		session.delete(ontologia);
		session.getTransaction().commit();
		session.close();
	}
	
	public List<Objeto> findByPublicacao(Sujeito sujeito){
		List<Objeto> objetos = new ArrayList<Objeto>();
		StartSession();
		Criteria crit = session.createCriteria(Objeto.class);
		crit.add(Restrictions.eq("sujeito", sujeito));
		objetos = crit.list();
		session.close();
		return objetos;
		
	}

	public List<Objeto> findByColuna(Coluna coluna) {
		List<Objeto> objetos = new ArrayList<Objeto>();
		StartSession();
		Criteria crit = session.createCriteria(Objeto.class);
		crit.add(Restrictions.eq("coluna", coluna));
		objetos = crit.list();
		session.close();
		return objetos;
	}
}
