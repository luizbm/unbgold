package br.unb.unbgold.util;

import java.text.Normalizer;

public class Util {

	static public String preUri(String str) {
		
	    str =  Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase();
	    str = str.replace(" ", "_");
	    return str;
	    
	}
	
	static public String getNomeConjuntoDados(String nome, Integer versao) {
		String retorno;
		retorno = nome.toLowerCase().replace(" ", "-");
		retorno = retorno.replaceAll("[^\\p{ASCII}]", "").toLowerCase();
		retorno = "v"+versao+"-"+retorno;
		return retorno;
	}
}
