package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.unb.unbgold.model.Nota;

public class Dao {

	SessionFactory sessionFactory = null;
	Session session = null;
	
	public Dao() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}
	
	public List<Nota> getAllNota(){
		List<Nota> lista = new ArrayList<Nota>();
		
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query<Nota> query = session.createQuery("from Nota");
		
		lista = query.getResultList();
		return lista;
	}
	
	public <T> T getById(final Class<T> type, final Long id){
	      return (T) sessionFactory.getCurrentSession().get(type, id);
    }
	
	

}
