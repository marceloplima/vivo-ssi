package com.ssi.adm.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.TiposUsuario;

@FacesConverter("tipousuarioConv")
public class TiposUsuarioConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		TiposUsuario obj = new TiposUsuario();
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
				Long id = Long.parseLong(value);
				obj = (new TiposUsuario()).getByid(id);
			
				return obj;
			}
		}
		catch(Exception e){
			obj = new TiposUsuario();
			obj.setIdtipousuario(null);
			obj.setCnmtipousuario("");
		}
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		TiposUsuario objint = (TiposUsuario)obj;
		
		try{
			return objint.getIdtipousuario().toString();
		}
		catch(Exception e){
			return "";
		}
	}

}
