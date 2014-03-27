package com.ssi.compras.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.compras.domain.Lps;

@FacesConverter("lpConverter")
public class LpConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		
		Lps lps = new Lps();
		lps.setIdlps(null);
		
		//System.out.println("lp converter valor : " + valor);
		
		if (valor.trim().equals("")) {
			return lps;
		} else {
			try {
				Long id = Long.parseLong(valor);				
				lps.setIdlps(id);

				return lps;

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Erro no lp converter");
				return lps;
			}
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		//System.out.println("%%%%%%%%%%%%% EXECUTANDO O LP CONVERTER getAsString");
		if(obj==null){
			return "";
		}
		
		Lps lps = (Lps) obj;

		try {
			return lps.getIdlp().toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro no lp converter: getAsString");
			throw new ConverterException(e);
		}
	}

}
