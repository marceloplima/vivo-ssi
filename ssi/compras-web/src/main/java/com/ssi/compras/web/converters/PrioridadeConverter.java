package com.ssi.compras.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.compras.domain.Prioridades;

@FacesConverter("prioridadeConverter")
public class PrioridadeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {

		Prioridades prioridades = new Prioridades();
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

		Prioridades prioridades = (Prioridades) obj;

		try {
			return prioridades.getIdprioridade().toString();
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
