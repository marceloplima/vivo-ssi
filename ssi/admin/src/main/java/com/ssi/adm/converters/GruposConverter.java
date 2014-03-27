package com.ssi.adm.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.Grupos;

@FacesConverter("grupoConv")
public class GruposConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		Grupos obj = new Grupos();
		
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
				Long id = Long.parseLong(value);
				obj = (new Grupos()).getByid(id);
			
				return obj;
			}
		}
		catch(Exception e){
			obj = new Grupos();
			obj.setIdgrupo(null);
			obj.setCnmgrupo("");
		}
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		Grupos objint = (Grupos)obj;
		
		try{
			return objint.getIdgrupo().toString();
		}
		catch(Exception e){
			return "";
		}
	}

}
