package com.ssi.redes.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.redes.domain.EmissoresRedes;

@FacesConverter("emissorConverter")
public class EmissorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		EmissoresRedes emissor = new EmissoresRedes();
		emissor.setIdemissor(null);;
		
		if (valor.trim().equals("")) {
			return emissor;
		} else {
			try {
				Long id = Long.parseLong(valor);
				emissor.setIdemissor(id);

				return emissor;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception em : emissorConverter");
				throw new ConverterException(e);
			}
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj == null) {
			return "";
		}

		EmissoresRedes emissor = (EmissoresRedes) obj;

		try {
			return emissor.getIdemissor().toString();
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
