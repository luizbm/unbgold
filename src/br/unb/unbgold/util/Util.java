package br.unb.unbgold.util;

import java.text.Normalizer;

public class Util {

	static public String preUri(String str) {
		
	    str =  Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase();
	    str = str.replace(" ", "_");
	    return str;
	    
	}
}
