package br.unb.unbgold.ctrl;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.unb.unbgold.dao.ColunaDao;
import br.unb.unbgold.model.Coluna;

@Path("/user")
public class UserCtrl {

	private ColunaDao colunaDao;
	
	@PostConstruct
	private void init() {
		colunaDao = new ColunaDao();
	}
	
	
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String novaColuna(Coluna coluna) {
		String msg = "";
		try {
			colunaDao.add(coluna);
			msg = "Coluna cadastrada com sucesso";
		} catch (Exception e) {
			msg = "Erro ao cadastrar a coluna";
			e.printStackTrace();
		}
		
		return msg;
	}	
	
}
