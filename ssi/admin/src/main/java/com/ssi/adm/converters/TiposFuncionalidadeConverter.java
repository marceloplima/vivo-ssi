package com.ssi.adm.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.TiposFuncionalidade;

@FacesConverter("tfuncConv")
public class TiposFuncionalidadeConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		try{
			if(value == "" || value.equals("")){
				value = "0";
			}
			Long id = Long.parseLong(value);
			TiposFuncionalidade tfunc = (new TiposFuncionalidade()).getByid(id);
			
			return tfunc;
		}
		catch(Exception e){
			throw new ConverterException(e);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		TiposFuncionalidade objint = (TiposFuncionalidade)obj;
		
		try{
			return objint.getIdtipofuncionalidade().toString();
		}
		catch(Exception e){
			throw new ConverterException(e);
		}
	}

}
