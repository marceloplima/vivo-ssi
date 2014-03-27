package com.ssi.adm.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.Funcionalidades;

@FacesConverter("funcConv")
public class FuncionalidadesConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		Funcionalidades func = new Funcionalidades();
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
							
				Long id = Long.parseLong(value);
				func = (new Funcionalidades()).getByid(id);
			
				return func;
			}
		}
		catch(Exception e){
			func = new Funcionalidades();
			func.setIdfuncionalidade(null);
			func.setCnmfuncionalidade("");
		}
		
		return func;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		Funcionalidades objint = (Funcionalidades)obj;
		
		try{
			return objint.getIdfuncionalidade().toString();
		}
		catch(Exception e){
			return "";
		}
	}

}
