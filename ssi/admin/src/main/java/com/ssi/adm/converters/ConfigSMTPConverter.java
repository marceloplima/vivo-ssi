package com.ssi.adm.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.ConfigSMTP;

@FacesConverter("smtpConv")
public class ConfigSMTPConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		try{
			if(value == "" || value.equals("")){
				value = "0";
			}
			Long id = Long.parseLong(value);
			ConfigSMTP modulo = (new ConfigSMTP()).getByid(id);
			
			return modulo;
		}
		catch(Exception e){
			throw new ConverterException(e);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		ConfigSMTP objint = (ConfigSMTP)obj;
		
		try{
			return objint.getIdsmtp().toString();
		}
		catch(Exception e){
			throw new ConverterException(e);
		}
	}

}
