package com.ssi.redes.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.Atividades;

@FacesConverter("atividadeConv")
public class AtividadesConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		Atividades obj = new Atividades();
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
							
				Long id = Long.parseLong(value);
				
				obj.setIdatividade(id);
			
				return obj;
			}
		}
		catch(Exception e){
			obj = new Atividades();
			obj.setIdatividade(null);
			obj.setCnmatividade("");
		}
		
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		Atividades objint = (Atividades)obj;
		
		try{
			return objint.getIdatividade().toString();
		}
		catch(Exception e){
			return "";
		}
	}

}
