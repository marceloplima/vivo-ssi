package com.ssi.redes.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.redes.domain.MotivacoesRedes;

@FacesConverter("motivacaoConv")
public class MotivacoesRedesConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		
		MotivacoesRedes obj = new MotivacoesRedes();
		obj.setIdmotivacao(null);
		
		if(valor.trim().equals("")){
			return obj;
		}
		else{
			try{
				Long id = Long.parseLong(valor);
				obj.setIdmotivacao(id);
				return obj;
			}
			catch(Exception ex){
				ex.printStackTrace();
				System.out.println("Erro no "+this.getClass().getName());
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj == null) {
			return "";
		}
		MotivacoesRedes objint = (MotivacoesRedes)obj;
		
		try {
			return objint.getIdmotivacao().toString();
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}
}
