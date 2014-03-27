package com.ssi.admin.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ValidadorCamposInvalidos {

	/**
	 * Validador gen�rico 
	 * @param campos (Nome do campo, valor)
	 * @return msgerro com os alertas a serem exibidos na tela
	 */
	public List<String> validarCamposVazios(Map<String,String> campos){
		
		List<String> erros = new ArrayList<String>();
		
		Iterator<Entry<String, String>> itc = campos.entrySet().iterator();
		
		while(itc.hasNext()){
			Entry<String, String> pares = itc.next();
			
			if(pares.getValue().isEmpty() || pares.getValue().equals("0") || pares.getValue().equals("null") || pares==null){
				erros.add(pares.getKey()+" precisa ser preenchido");
			}
		}
		
		return erros;
	}
	
}
