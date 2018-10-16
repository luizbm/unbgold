package br.unb.unbgold.ctrl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

import br.unb.unbgold.dao.ColunaDao;
import br.unb.unbgold.dao.ObjetoDao;
import br.unb.unbgold.dao.OntologiaDao;
import br.unb.unbgold.dao.PublicacaoDao;
import br.unb.unbgold.dao.SujeitoDao;
import br.unb.unbgold.dao.TriplaDao;
import br.unb.unbgold.model.Coluna;
import br.unb.unbgold.model.Dataset;
import br.unb.unbgold.model.Objeto;
import br.unb.unbgold.model.Objeto_tipo;
import br.unb.unbgold.model.Ontologia;
import br.unb.unbgold.model.Publicacao;
import br.unb.unbgold.model.Sujeito;
import br.unb.unbgold.util.TriplaUtil;
import eu.trentorise.opendata.traceprov.internal.org.apache.commons.io.output.ByteArrayOutputStream;

@Path("/jena")
public class JenaCtrl {

	private PublicacaoDao publicacaoDao;
	private TriplaDao triplaDao;
	private ObjetoDao objetoDao;
	@PostConstruct
	private void init() {
		publicacaoDao = new PublicacaoDao();
		triplaDao = new TriplaDao();
		objetoDao = new ObjetoDao();
	}
	
	@GET
	@Path("/{id}")
	public String novaColuna(@PathParam("id") int id) {
		Publicacao publicacao = new Publicacao();
		publicacao.setId_publicacao(id);
		
		
		
		String msg = "Rodou";
		List<Resource> resorces = new ArrayList<Resource>();
		
		List<TriplaUtil> triplas = new ArrayList<TriplaUtil>();
		try {
			List<Ontologia> ontologias = new ArrayList<Ontologia>();
			
			
			publicacao = publicacaoDao.get(id);
			Dataset dataset = publicacao.getDataset();
			Objeto oType = new Objeto();
			oType.setDesc_objeto(dataset.getTermo().getIri_termo());
			Objeto_tipo ot = new Objeto_tipo();
			ot.setId_objeto_tipo(2);
			
			List<Sujeito> sujeitos = new SujeitoDao().getByPublicacaoId(id);
			//List<Sujeito> sujeitos = new SujeitoDao().getAll();
			Model m = ModelFactory.createDefaultModel();
			for (Sujeito sujeito : sujeitos) {
				List<Objeto> objetos = objetoDao.findByPublicacao(sujeito);
				Resource root = m.createResource(sujeito.getDesc_sujeito());
				
				oType.setObjeto_tipo(ot);
				TriplaUtil type = new TriplaUtil(root, m.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), oType, false, null);
						//new TriplaUtil(root, m.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), "", false, null)
				triplas.add(type);
				for (Objeto objeto: objetos) {
					if(!ontologias.contains(objeto.getTermo().getOntologia())){
						ontologias.add(objeto.getTermo().getOntologia());
					}	
					String iri_termo;
					if(objeto.getTermo().getNm_termo().equals("naoindexado")) {
						iri_termo = objeto.getTermo().getOntologia().getUrl_ontologia()+"#"+objeto.getColuna().getNm_campo();
					}else {
						iri_termo = objeto.getTermo().getOntologia().getUrl_ontologia()+"#"+objeto.getTermo().getNm_termo();
					}
					
					Property p = m.createProperty(iri_termo);
					if(objeto.getObjeto_tipo().getId_objeto_tipo() != 2) {
						triplas.add(new TriplaUtil(root, p, objeto, true, objeto.getColuna()));
					}else {
						triplas.add(new TriplaUtil(root, p, objeto, false, objeto.getColuna()));
					}
					//m.add(root, p, objeto.getDesc_objeto());
				}
				
				
			}
			for (Ontologia ontologia : ontologias) {
				 m.setNsPrefix( ontologia.getPrefixo_ontologia(), ontologia.getUrl_ontologia()+"#");
			}
			List<Coluna> colunas = new ArrayList<Coluna>();
			colunas = new ColunaDao().findByDataset(publicacao.getDataset());
			
			for (Coluna coluna : colunas) {
				if(coluna.getColuna_ligacao().getId_coluna() != 1) {
					System.out.println(coluna.getNm_campo());
					List<Objeto> objetosLigacao = new ObjetoDao().findByColuna(coluna.getColuna_ligacao());
					List<TriplaUtil> triplaLink = new ArrayList<TriplaUtil>();
					for (TriplaUtil tripla : triplas) {
						//System.out.println(tripla.getObjeto().getObjeto_tipo().getId_objeto_tipo());
						tripla.setLiteral(true);
						if(tripla.getObjeto().getObjeto_tipo().getId_objeto_tipo() == 2) {
							for (Objeto objeto: objetosLigacao) {
								//System.out.println(objeto.getDesc_objeto());
								if(tripla.getObjeto().getDesc_objeto().equals(objeto.getDesc_objeto())){
									Objeto ob = tripla.getObjeto();
									ob.setDesc_objeto(objeto.getSujeito().getDesc_sujeito());
									
									tripla.setObjeto(ob);
									tripla.setLiteral(false);
									break;
								}
							}
						}
						triplaLink.add(tripla);
					}
					triplas = triplaLink;
				}
					
			}
			for (TriplaUtil tripla : triplas) {
				if(tripla.getLiteral()) {
					
					m.add(tripla.getRoot(), tripla.getP(), tripla.getObjeto().getDesc_objeto());
				}else {
					//Model model = new 
					Resource o = m.createResource(tripla.getObjeto().getDesc_objeto());
					m.add(tripla.getRoot(), tripla.getP(), o);
					
				}
			}
			
			/* String nsA = "http://somewhere/else#";
			 String nsB = "http://nowhere/else#";
			 Resource root = m.createResource( nsA + "root" );
			 Property P = m.createProperty( nsA + "P" );
			 Property Q = m.createProperty( nsB + "Q" );
			 Resource x = m.createResource( nsA + "x" );
			 Resource y = m.createResource( nsA + "y" );
			 Resource z = m.createResource( nsA + "z" );
			 m.add( root, P, x ).add( root, P, y ).add( y, Q, z );
			 System.out.println( "# -- no special prefixes defined" );
			 //m.write( System.out );
			 System.out.println( "# -- nsA defined" );
			 m.setNsPrefix( "nsA", nsA );
			// m.write( System.out );
			 System.out.println( "# -- nsA and cat defined" );
			 m.setNsPrefix( "cat", nsB );*/
			String fileName = "file_rdf.rdf";
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");

			m.write( System.out );
			 ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			 
			 String str;
				m.write(out);
			msg	= out.toString("UTF-8");;
			System.out.println(msg);
			System.out.println(new String(msg.getBytes("ISO-8859-1")));
			writer.write(out.toString());
			writer.close();
			 /*
			Model model = ModelFactory.createDefaultModel();
			
			for (Tripla tripla : triplas) {
				
				Property p = model.createProperty(tripla.getPredicado().getIri_termo());
				Resource r = model.createResource(tripla.getSujeito()).addProperty(p , tripla.getObjeto());
			
			}
			
			// create the resource
			/*Resource johnSmith =
			        model.createResource(personURI)
			             .addProperty(VCARD.FN, fullName);
			
			*/
			//m.write(System.out);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return msg;
	}
	
	@GET
	@Path("/consulta")
	public String consulta() {
		String diretorio = "C:\\Users\\luiz\\eclipse-workspace\\unbgold\\rdfs";
		try {
			System.out.println("Aqui  -> " + new File("").getCanonicalPath());
			System.out.println("/  -> " + new File("/").getCanonicalPath());
			System.out.println(".. -> " + new File("..").getCanonicalPath());
			System.out.println(".  -> " + new File(".").getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File file = new File(diretorio);
		File afile[] = file.listFiles();
		int i = 0;
		for (int j = afile.length; i < j; i++) {
			File arquivos = afile[i];
			System.out.println(arquivos.getName());
		}
		
		
		String msg = "Teste Consulta";
		 // create an empty model
		Model model = ModelFactory.createDefaultModel();
		 String inputFileName = diretorio+"\\orgao.rdf";
		 // use the FileManager to find the input file
		 InputStream in = FileManager.get().open( inputFileName );
		 if (in == null) {
		    msg =  "File: " + inputFileName + " not found";
		}else {
			model.read(in, null);
			// write it to standard out
			model.write(System.out);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			model.write(out);
			//msg = out.toString();
		}

		// read the RDF/XML file
		

		
		return msg;
	}
	
}
