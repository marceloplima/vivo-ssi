package com.ssi.adm.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.Pessoas;

@FacesConverter("pessoasConv")
public class PessoasConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		Pessoas obj = new Pessoas();
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
				Long id = Long.parseLong(value);
				obj = (new Pessoas()).getByid(id);
			
				return obj;
			}
		}
		catch(Exception e){
			obj = new Pessoas();
			obj.setIdpessoa(null);
			obj.setCnmnome("");
		}
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		Pessoas objint = (Pessoas)obj;
		
		try{
			return objint.getIdpessoa().toString();
		}
		catch(Exception e){
			return "";
		}
	}

}
