package com.ssi.compras.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.compras.domain.Propostas;

@FacesConverter("propostaConv")
public class PropostasConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		
		Propostas obj = new Propostas();
		obj.setIdproposta(null);
				    
		
		if (valor.trim().equals("")) {
			return obj;
		} else {
			try {
				Long id = Long.parseLong(valor);
				obj.setIdproposta(id);

				return obj;

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Erro na propostas converter");
				return obj;
			}
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj == null) {
			return "";
		}

		Propostas objint = (Propostas) obj;

		try {
			return objint.getIdproposta().toString();
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}
}
