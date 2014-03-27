package com.ssi.redes.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.Regionais;

@FacesConverter("regionalConverter")
public class RegionaisConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {

		Regionais regional = new Regionais();
		regional.setIdregional(null);

		if (valor.trim().equals("")) {
			return regional;
		} else {
			try {
				Long id = Long.parseLong(valor);
				regional.setIdregional(id);
				return regional;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Erro no regionais converter");
				return regional;
			}
		}

	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {

		Regionais regional = (Regionais) obj;

		try {
			return regional.getIdregional().toString();
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
