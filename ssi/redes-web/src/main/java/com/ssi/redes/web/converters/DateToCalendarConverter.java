package com.ssi.redes.web.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("dateToCalendarConverter")
public class DateToCalendarConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String strData) {
		Calendar data = new GregorianCalendar();    
	    SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");  
	    try {  
	        data.setTime(sd.parse(strData));  
	    } catch (ParseException e) {
	    	System.out.println("Erro ao converter Data para Calendar");
	        e.printStackTrace();  
	    }  
	    return data;  
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object data) {
		
		String retorno="";
		
		if(data==null){
			return retorno;
		}
				
		try {
			 
			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");			 
									 
			retorno = formatoData.format(((Calendar)data).getTime());			 
			 
			} catch (Exception e) {
				
				e.printStackTrace();
				System.out.println("Erro ao converter Calendar para data");
				
			}
		
		return retorno;
        
	}

}
