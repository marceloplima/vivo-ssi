package com.ssi.regulatorio.web.converters;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.redes.domain.PareceresRedes;

@FacesConverter("parecerRedesConverter")
public class ParecerConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {

		PareceresRedes parecer = new PareceresRedes();
		parecer.setIdparecer(null);

		if (valor.trim().equals("")) {
			return parecer;
		} else {
			try {
				Long id = Long.parseLong(valor);
				parecer.setIdparecer(id);
				return parecer;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Erro no parecer converter");
				return parecer;
			}
		}

	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {

		PareceresRedes parecer = (PareceresRedes) obj;
		
		try {
			if(parecer != null){
				return parecer.getIdparecer().toString();
			}
			
			return "";
				
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
