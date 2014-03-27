package com.ssi.redes.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.Centrais;

@FacesConverter("centralConverter")
public class CentraisConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		//System.out.println("getAsObject");
		Centrais obj = new Centrais();
		obj.setIdcentral(null);
		
		if (valor.trim().equals("")) {
			//System.out.println("ZERADO");
			return obj;
		} else {
		
			try {
				//System.out.println("TENTANDO");
				Long id = Long.parseLong(valor);				
				obj.setIdcentral(id);

				return obj;

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Erro no centralConverter");
				return obj;
			}
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		//System.out.println("ENTREI: getAsString");
		if(obj==null){
			//System.out.println("getAsString NULO");
			return "";
		}
		
		Centrais c = (Centrais) obj;

		try {
			//System.out.println("TENTANDO getAsString");
			return c.getIdcentral().toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro no centralConverter: getAsString");
			throw new ConverterException(e);
		}
	}

}
