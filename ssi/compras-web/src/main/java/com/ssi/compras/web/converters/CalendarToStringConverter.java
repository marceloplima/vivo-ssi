package com.ssi.compras.web.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

@FacesConverter("calendarToStringConverter")
public class CalendarToStringConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		
		if("".equals(valor)){
			return null;
		}
		
		Calendar cal=Calendar.getInstance();
		
		 try {  
		 	DateFormat formatter ; 
		 	Date date ; 
		 	formatter = new SimpleDateFormat("dd/MM/yyyy");
		 	date = (Date)formatter.parse(valor); 		 	
		 	cal.setTime(date);
		  } 
		 catch (ParseException e){
			 System.out.println("ERRO NO CALENDARTOSTRINGCONVERTER getAsObject");
			 e.printStackTrace();
			 return null;
		 } 
		 			
		return cal;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {

		if(obj==null){
			return "";
		}
		
		Calendar cal = (Calendar)obj;
		String dia = StringUtils.leftPad(Integer.toString(cal.get(Calendar.DATE)), 2,'0');
		String mes = Integer.toString(cal.get(Calendar.MONTH)+1); //M�s no calendar: Janeiro = 0; Dezembro = 11. Por isso somat�rio com 1 
		String ano = StringUtils.leftPad(Integer.toString(cal.get(Calendar.YEAR)), 4,'0');		
		
		String data = dia + "/" + StringUtils.leftPad(mes,2,'0') + "/" + ano;
		
		
		return data;
	}

}
