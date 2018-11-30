package br.unb.unbgold.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.unb.unbgold.model.Nota;
import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Termo;

public class OntologiaDao extends Dao {

	public List<Ontologia> getAll() throws Exception {
		List<Ontologia> lista = new ArrayList<Ontologia>();
		StartSession();
		Query<Ontologia> query = session.createQuery("from Ontologia WHERE id_ontologia <> 3");
		lista = query.getResultList();
		session.close();
		return lista;
	}

	
	public List<Ontologia> getOntologiaCatalogo() throws Exception {
		List<Ontologia> lista = new ArrayList<Ontologia>();
		StartSession();
		Query<Ontologia> query = session.createQuery("from Ontologia WHERE id_ontologia IN (2, 8, 9, 10, 11, 5)");
		lista = query.getResultList();
		session.close();
		return lista;
	}
	
	public Ontologia get(int id) throws Exception {
		StartSession();
		Ontologia o = session.getReference(Ontologia.class, id);
		session.close();
		return o;
	}

	public void add(Ontologia ontologia) throws Exception {	
		StartSession();
		session.beginTransaction();
		session.save(ontologia);
		session.getTransaction().commit();
		session.close();
	}

	public void alter(Ontologia ontologia) throws Exception {
		StartSession();
		session.beginTransaction();
		session.update(ontologia);
		session.getTransaction().commit();
		session.close();
		
	}

	public void delete(int id) throws Exception {
		StartSession();
		session.beginTransaction();
		Nota nota = session.getReference(Nota.class, id);
		
		session.delete(nota);
		session.getTransaction().commit();
		session.close();
	}
	
	public List<Ontologia> getOntologiaDosConjuntos(int id) throws Exception {
		String queryString = "SELECT o FROM Ontologia o WHERE o.id_ontologia IN ( "
				+ " SELECT t.ontologia.id_ontologia "
				+ " FROM Termo t INNER JOIN Coluna c ON c.termo.id_termo = t.id_termo "
				+ " WHERE c.conjuntoDados.id_dataset =:id "
				+ ")";
		List<Ontologia> lista = new ArrayList<Ontologia>();
		StartSession();
		session.beginTransaction();
	    Query<Ontologia> query = session.createQuery(queryString);
		query.setParameter("id", id);
		lista = query.getResultList();
		session.close();
		return lista;
	}
	
	
}
