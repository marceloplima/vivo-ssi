package com.ssi.adm.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.TiposTelefone;

@FacesConverter("tipotelefoneConv")
public class TiposTelefoneConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		TiposTelefone obj = new TiposTelefone();
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
				Long id = Long.parseLong(value);
				obj = (new TiposTelefone()).getByid(id);
			
				return obj;
			}
		}
		catch(Exception e){
			obj = new TiposTelefone();
			obj.setIdtipotelefone(null);
			obj.setCnmtipotelefone("");
		}
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		TiposTelefone objint = (TiposTelefone)obj;
		
		try{
			return objint.getIdtipotelefone().toString();
		}
		catch(Exception e){
			return "";
		}
	}
}
