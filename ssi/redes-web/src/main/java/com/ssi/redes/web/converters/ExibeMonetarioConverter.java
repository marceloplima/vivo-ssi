package com.ssi.redes.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("exibeMonetarioConverter")
public class ExibeMonetarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {

		return 0;
	    
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object valor) {

		String retorno="";
		
		if(valor==null){
			return retorno;
		}
		else{
			String strvalor = valor.toString().replace(".", ",");
			return strvalor.substring(0,strvalor.length()-2);
		}
		
	}

}
