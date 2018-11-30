package br.unb.unbgold.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ManagerFiles {

	static String PATH = "C:\\Users\\00415102162\\git\\unbgold\\WebContent\\dados\\";
	
	public String gravarArquivo(String conteudo, String cd, String name) {
		
		FileWriter arq;
		
		try {
			arq = new FileWriter(PATH+name);
			PrintWriter gravarArq = new PrintWriter(arq);
 		    gravarArq.print(conteudo);
			arq.close();
			    
		} catch (IOException e) {
			e.printStackTrace();
		}
	   
		
		return "rodou";
		
	}
	public static File pegaArquivo(String nomeArquivo ) {
		File file = new File(PATH+nomeArquivo);
		if(file.exists()) {
			return file;			
		}else {
			return null;
		}
	}
}
