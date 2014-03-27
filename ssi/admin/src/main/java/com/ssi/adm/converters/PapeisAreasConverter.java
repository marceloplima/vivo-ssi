package com.ssi.adm.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.PapeisAreas;

@FacesConverter("papelareaConv")
public class PapeisAreasConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		PapeisAreas obj = new PapeisAreas();
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
							
				Long id = Long.parseLong(value);
				obj = (new PapeisAreas()).getByid(id);
			
				return obj;
			}
		}
		catch(Exception e){
			obj = new PapeisAreas();
			obj.setIdpapelarea(null);
			obj.setCnmpapelarea("");
		}
		
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		PapeisAreas objint = (PapeisAreas)obj;
		
		try{
			return objint.getIdpapelarea().toString();
		}
		catch(Exception e){
			return "";
		}
	}

}
