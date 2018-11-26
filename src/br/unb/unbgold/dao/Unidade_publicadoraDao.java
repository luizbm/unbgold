package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.unb.unbgold.model.Unidade_publicadora;

@SuppressWarnings("deprecation")
public class Unidade_publicadoraDao extends Dao {

	@SuppressWarnings("unchecked")
	public List<Unidade_publicadora> getAll() throws Exception {
		List<Unidade_publicadora> lista = new ArrayList<Unidade_publicadora>();
		StartSession();
		
		Query<Unidade_publicadora> query = session.createQuery("from Unidade_publicadora");
		lista = query.getResultList();
		session.close();
		return lista;
	}

	public Unidade_publicadora get(int id) throws Exception {
		StartSession();
		Unidade_publicadora up =  session.getReference(Unidade_publicadora.class, id);
		session.close();
		return up;
	}

	public void add(Unidade_publicadora unidade_publicadora) throws Exception {	
		StartSession();
		session.beginTransaction();
		session.save(unidade_publicadora);
		session.getTransaction().commit();
		session.close();
	}

	public void alter(Unidade_publicadora unidade_publicadora) throws Exception {
		StartSession();
		session.beginTransaction();
		session.update(unidade_publicadora);
		session.getTransaction().commit();		
		session.close();
	}

	public void delete(int id) throws Exception {
		StartSession();
		session.beginTransaction();
		Unidade_publicadora unidade_publicadora = session.getReference(Unidade_publicadora.class, id);
		session.delete(unidade_publicadora);
		session.getTransaction().commit();
		session.close();
	}
}
