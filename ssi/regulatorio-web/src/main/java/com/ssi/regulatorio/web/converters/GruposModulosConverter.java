package com.ssi.regulatorio.web.converters;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.GruposModulos;

@FacesConverter("grupomoduloConv")
public class GruposModulosConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		GruposModulos obj = new GruposModulos();
		
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
				Long id = Long.parseLong(value);
				obj = (new GruposModulos()).getByid(id);
			
				return obj;
			}
		}
		catch(Exception e){
			obj = new GruposModulos();
			obj.setIdgrupomodulo(null);
			obj.setCnmgrupomodulo("");
		}
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		GruposModulos objint = (GruposModulos)obj;
		
		try{
			return objint.getIdgrupomodulo().toString();
		}
		catch(Exception e){
			return "";
		}
	}

}
