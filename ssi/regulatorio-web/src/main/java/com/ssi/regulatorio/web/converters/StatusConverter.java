package com.ssi.regulatorio.web.converters;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.redes.domain.StatusRedes;

@FacesConverter("statusConv")
public class StatusConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		
		StatusRedes obj = new StatusRedes();
		obj.setIdstatus(null);
		
		if(valor.trim().equals("")){
			return obj;
		}
		else{
			try{
				Long id = Long.parseLong(valor);
				obj.setIdstatus(id);
				return obj;
			}
			catch(Exception ex){
				ex.printStackTrace();
				System.out.println("Erro no status converter");
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj == null) {
			return "";
		}
		StatusRedes objint = (StatusRedes)obj;
		
		try {
			return objint.getIdstatus().toString();
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
