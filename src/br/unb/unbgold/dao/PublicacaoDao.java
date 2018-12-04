package br.unb.unbgold.dao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Publicacao;
import br.unb.unbgold.util.Util;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import br.unb.unbgold.ckan.CkanManager;
import br.unb.unbgold.model.ConjuntoDados;

public class PublicacaoDao extends Dao {
	
	

	public List<Publicacao> getAll() throws Exception {
		StartSession();
		List<Publicacao> lista = new ArrayList<Publicacao>();
		Query<Publicacao> query = session.createQuery("from Publicacao p where p.id_publicacao NOT IN (3,5) ");
		lista = query.getResultList();
		session.close();
		return lista;
		
		
		
	}

	public Publicacao get(int id) throws Exception {
		StartSession();
		Publicacao p =  session.getReference(Publicacao.class, id);
		return p;
	}

	public void add(Publicacao publicacao) throws Exception {	
		StartSession();
		session.beginTransaction();
		session.save(publicacao);
		session.getTransaction().commit();
		session.close();
	}

	public void alter(Publicacao publicacao) throws Exception {
		StartSession();
		session.beginTransaction();
		session.update(publicacao);
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
	
	public List<Publicacao> findByDataset(ConjuntoDados dataset){
		 StartSession();
		 List<Publicacao> publicacaos = new ArrayList<Publicacao>();
			
		 Criteria crit = session.createCriteria(Publicacao.class);
		 crit.add(Restrictions.eq("dataset", dataset));
		 publicacaos = crit.list();
		 
		 return publicacaos;
	}
	
	public List<Publicacao> findLigacoes(Publicacao publicacao){
		StartSession();
		List<Publicacao> retorno = new ArrayList<Publicacao>();
		String queryString = "SELECT p2 "
				+ " FROM Coluna c2 INNER JOIN ConjuntoDados d2 ON c2.conjuntoDados.id_dataset = d2.id_dataset "
				+ "                INNER JOIN Publicacao p2    ON p2.dataset.id_dataset = d2.id_dataset "
				+ " WHERE c2.id_coluna IN( "
				+ "     SELECT c1.id_coluna_ligacao "
				+ "     FROM Coluna c1 "
				+ "          INNER JOIN ConjuntoDados d1 ON c1.conjuntoDados.id_dataset = d1.id_dataset "
				+ "          INNER JOIN Publicacao p1 ON d1.id_dataset = p1.dataset.id_dataset "
				+ "     WHERE c1.id_coluna_ligacao <> 1 AND p1.id_publicacao = :id"
				+ " ) AND p2.id_publicacao IN ("
				+ "      SELECT max(p3.id_publicacao) " + 
				"		 FROM Publicacao p3 " + 
				"	     WHERE p3.ativo = 1 " + 
				"		 GROUP BY p3.dataset.id_dataset)";
		Query<Publicacao> query = session.createQuery(queryString);
		query.setParameter("id", publicacao.getId_publicacao());
		retorno = query.list();
		session.close();
		return retorno;
	}
	
	public List<Publicacao> findLigados(Publicacao publicacao){
		StartSession();
		List<Publicacao> retorno = new ArrayList<Publicacao>();
		String queryString = "SELECT p2 "
				+ " FROM Coluna c2 INNER JOIN ConjuntoDados d2 ON c2.conjuntoDados.id_dataset = d2.id_dataset "
				+ "                INNER JOIN Publicacao p2    ON p2.dataset.id_dataset = d2.id_dataset "
				+ " WHERE c2.id_coluna_ligacao <> 1 AND c2.id_coluna_ligacao IN( "
				+ "     SELECT c1.id_coluna "
				+ "     FROM Coluna c1 "
				+ "          INNER JOIN ConjuntoDados d1 ON c1.conjuntoDados.id_dataset = d1.id_dataset "
				+ "          INNER JOIN Publicacao p1 ON d1.id_dataset = p1.dataset.id_dataset "
				+ "     WHERE  p1.id_publicacao = :id"
				+ " ) AND p2.id_publicacao IN ("
				+ "      SELECT max(p3.id_publicacao) " + 
				"		 FROM Publicacao p3 " + 
				"	     WHERE p3.ativo = 1 " + 
				"		 GROUP BY p3.dataset.id_dataset)";
		Query<Publicacao> query = session.createQuery(queryString);
		query.setParameter("id", publicacao.getId_publicacao());
		retorno = query.list();
		session.close();
		return retorno;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Publicacao> buscarPendentes() {
		StartSession();
		// TODO Auto-generated method stub
		List<Publicacao> retorno = new ArrayList<Publicacao>();
		String queryString = "SELECT p FROM Publicacao p WHERE p.ativo = 0 AND p.data_publicacao <=  :date ";
		
		Query<Publicacao> query = session.createQuery(queryString);
		Date hoje = new Date();
		query.setParameter("date", hoje);
		retorno = query.list();
		session.close();
		return retorno;
		}

	public Publicacao setarVersao(Publicacao pub) {
		Publicacao publicacao = null;
		StartSession();
		session.beginTransaction();
		publicacao = session.getReference(Publicacao.class, pub.getId_publicacao());
		List<Publicacao> totais = this.findByDataset(pub.getDataset());
		Date now = new Date();
		String com = totais.size()+"-"+now.toString().replace(" ", "").replace("-", "").replaceAll(":", "");
		if(publicacao != null) {
			publicacao.setVersao(totais.size());
			publicacao.setNm_arquivo(pub.getDataset().getTitulo().toLowerCase().replace(" ", "-")+"-"+totais.size()+com);
		}
		session.update(publicacao);
		pub.setVersao(publicacao.getVersao());
		session.getTransaction().commit();
		
		pub.setNm_arquivo(pub.getDataset().getTitulo().toLowerCase().replace(" ", "-")+"-"+totais.size()+com);
		session.close();
		return pub;
	}
	
	public void SendFile(File file, CkanDataset cd) {
		
	}

	public Publicacao setarParametrosParaPublicacao(Publicacao pub, ConjuntoDados cd) {
		StartSession();
		session.beginTransaction();
		List<Publicacao> totais = this.findByDataset(cd);
		pub.setVersao(totais.size());
		Date now = new Date();
		SimpleDateFormat dataformat = new SimpleDateFormat("yyy-MM-dd-HH-mm-ss");
		String nameFile = Util.getNomeConjuntoDados(cd.getTitulo(), pub.getVersao())+"-"+dataformat.format(now);
		String com = totais.size()+"-"+dataformat.format(now);
		pub.setNm_arquivo(nameFile);
		//pub.setData_publicacao(new Date());
		pub.setFonte(CkanManager.ENDERECO_PUBLICACAO);
		pub.setAtivo(true);
		session.flush();
		session.clear();
		session.update(pub);
		return pub;
		
	}

	public Publicacao concluirPublicacao(Publicacao pub, ConjuntoDados cd) {
	
		Publicacao nPublicacao = new Publicacao();
		nPublicacao.setAtivo(false);
		nPublicacao.setDataset(cd);
		nPublicacao.setNm_arquivo(Util.getNomeConjuntoDados(cd.getTitulo(), 0));
		Calendar c = Calendar.getInstance();
		c.setTime(pub.getData_publicacao());
		switch (cd.getFrequencia().getId_frequencia()) {
		case 1: c.add(Calendar.DATE, 1); break;
		case 2: c.add(Calendar.WEEK_OF_MONTH, 1); break;
		case 3: c.add(Calendar.WEEK_OF_MONTH, 2); break;
		case 4: c.add(Calendar.MONTH, 1); break;
		case 5: c.add(Calendar.MONTH, 2); break;
		case 6: c.add(Calendar.MONTH, 6); break;
		case 7: c.add(Calendar.MONTH, 6); break;
		case 8: c.add(Calendar.MONTH, 12); break;
		default:
			break;
		}
		
		pub.setData_publicacao(new Date());
		session.update(pub);
		nPublicacao.setData_publicacao(c.getTime());
		//nPublicacao.setData_publicacao(new Date());
		session.save(nPublicacao);
		session.getTransaction().commit();
		session.close();
		
		return nPublicacao;
	}

	public void rollback() {
		session.getTransaction().rollback();
	}

	public List<Publicacao> getPublicacaoAtivas() {
		StartSession();
		List<Publicacao> retorno = new ArrayList<Publicacao>();
		String queryString = " SELECT p1 "
				+ " FROM Publicacao p1 "
				+ " WHERE p1.id_publicacao IN ("
				+ "     SELECT max(p2.id_publicacao)  "
				+ "     FROM Publicacao p2 "
				+ "     WHERE p2.ativo = 1 "
				+ "     GROUP BY p2.dataset.id_dataset)";
		Query<Publicacao> query = session.createQuery(queryString);
		//query.setParameter("id", publicacao.getId_publicacao());
		retorno = query.list();
		//session.close();
		return retorno;
	}
	
	public Publicacao getPublicacaAtivaDataset(Integer id) {
		StartSession();
		Publicacao retorno = new Publicacao();
		String queryString = " SELECT p1 "
				+ " FROM Publicacao p1 "
				+ " WHERE p1.id_publicacao IN ("
				+ "     SELECT max(p2.id_publicacao)  "
				+ "     FROM Publicacao p2 "
				+ "     WHERE p2.ativo = 1 AND  p2.dataset.id_dataset = :id)";
		Query<Publicacao> query = session.createQuery(queryString);
		query.setParameter("id", id);
		retorno = query.uniqueResult();
		//session.close();
		return retorno;
	}
	
}
