package com.ssi.adm.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("ativosConv")
public class AtivosConverter implements Converter {
     public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
         /*
          * Transforma o "true/false" que vem do bean em "sim/n�o" para ficar melhor exibido na view
          * 
          */
          String coluna = value;
          if (value!= null && !value.equals("")){
        	  if(value.equals("SIM")){
        		  coluna = "true";
        	  }
        	  else{
        		  coluna = "false";
        	  }
          }
          return coluna;
     }
 
     public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
         
    	 /*
          * Transforma o "true/false" que vem do bean em "sim/n�o" para ficar melhor exibido na view
          * 
          */
         Boolean ativo = (Boolean) value;
         String coluna = "";
         if (ativo){
        	 coluna = "SIM";
         }
         else{
        	 coluna = "N�O";
         }
          return coluna;
     }
}