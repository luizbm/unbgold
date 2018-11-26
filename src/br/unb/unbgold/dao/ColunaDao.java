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
		StartSession();
		Query<Coluna> query = session.createQuery("from Coluna");
		lista = query.getResultList();
		session.close();
		return lista;
	}

	public Coluna get(int id) throws Exception {
		StartSession();;
		return session.getReference(Coluna.class, id);
	}

	public void add(Coluna coluna) throws Exception {	
		StartSession();
		session.beginTransaction();
		session.save(coluna);
		session.getTransaction().commit();
		session.close();
	}

	public void alter(Coluna coluna) throws Exception {
		StartSession();
		session.beginTransaction();
		session.update(coluna);
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
	
	public List<Coluna> findByDataset(int id){
		StartSession();
		List<Coluna> retorno = new ArrayList<Coluna>();
		String queryString = "SELECT c "
			+ " FROM  Coluna c "
			+ " WHERE c.conjuntoDados.id_dataset = :id ";
		Query<Coluna> query = session.createQuery(queryString);
		query.setParameter("id", id);
		retorno = query.list();
		session.close();
		return retorno;
	}
}
