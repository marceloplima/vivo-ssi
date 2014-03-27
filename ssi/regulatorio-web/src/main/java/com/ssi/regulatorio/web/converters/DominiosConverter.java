package com.ssi.regulatorio.web.converters;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.Dominios;

@FacesConverter("dominioConv")
public class DominiosConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		try{
			if(value == "" || value.equals("")){
				value = "0";
			}
			int id_dominio = Integer.parseInt(value);
			Dominios dominio = (new Dominios()).getByid(id_dominio);
			
			return dominio;
		}
		catch(Exception e){
			throw new ConverterException(e);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		Dominios dom = (Dominios)obj;
		
		try{
			return dom.getIddominio().toString();
		}
		catch(Exception e){
			throw new ConverterException(e);
		}
	}

}
