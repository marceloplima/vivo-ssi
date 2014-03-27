package com.ssi.regulatorio.web.converters;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.Ufs;

@FacesConverter("ufConverter")
public class UfConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		//System.out.println("getAsObject");
		Ufs uf = new Ufs();
		uf.setIduf(null);;
		
		if (valor.trim().equals("")) {
			//System.out.println("UF ZERADO");
			return uf;
		} else {
		
			try {
				//System.out.println("TENTANDO UF");
				Long id = Long.parseLong(valor);				
				uf.setIduf(id);

				return uf;

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Erro no uf converter");
				return uf;
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
		
		Ufs uf = (Ufs) obj;

		try {
			//System.out.println("TENTANDO getAsString");
			return uf.getIduf().toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro no uf converter: getAsString");
			throw new ConverterException(e);
		}
	}

}
