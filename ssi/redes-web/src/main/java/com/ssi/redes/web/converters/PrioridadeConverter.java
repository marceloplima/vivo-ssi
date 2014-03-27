package com.ssi.redes.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.redes.domain.PrioridadesRedes;

@FacesConverter("prioridadeConverter")
public class PrioridadeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {

		PrioridadesRedes prioridades = new PrioridadesRedes();
		prioridades.setIdprioridade(null);

		if (valor.trim().equals("")) {
			return prioridades;
		} else {
			try {
				Long id = Long.parseLong(valor);
				prioridades.setIdprioridade(id);
				return prioridades;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Erro no prioridades converter");
				return prioridades;
			}
		}

	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {

		PrioridadesRedes prioridade = (PrioridadesRedes) obj;
		
		try {
			if(prioridade != null){
				return prioridade.getIdprioridade().toString();
			}
			
			return "";
				
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
