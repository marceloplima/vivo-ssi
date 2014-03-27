package com.ssi.regulatorio.web.converters;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.Areas;

@FacesConverter("areaConv")
public class AreasConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		Areas obj = new Areas();
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
							
				Long id = Long.parseLong(value);
				obj = (new Areas()).getByid(id);
			
				return obj;
			}
		}
		catch(Exception e){
			obj = new Areas();
			obj.setIdarea(null);
			obj.setCnmsiglaarea("");
			obj.setCnmdescarea("");
		}
		
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		Areas objint = (Areas)obj;
		
		try{
			return objint.getIdarea().toString();
		}
		catch(Exception e){
			return "";
		}
	}

}
