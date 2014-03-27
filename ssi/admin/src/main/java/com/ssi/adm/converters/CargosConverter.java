package com.ssi.adm.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.Cargos;

@FacesConverter("cargoConv")
public class CargosConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		Cargos obj = new Cargos();
		
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
				Long id = Long.parseLong(value);
				obj = (new Cargos()).getByid(id);
			
				return obj;
			}
		}
		catch(Exception e){
			obj = new Cargos();
			obj.setIdcargo(null);
			obj.setCnmcargo("");
		}
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		Cargos objint = (Cargos)obj;
		
		try{
			return objint.getIdcargo().toString();
		}
		catch(Exception e){
			return "";
		}
	}

}
