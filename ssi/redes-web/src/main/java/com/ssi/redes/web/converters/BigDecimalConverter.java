package com.ssi.redes.web.converters;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("bigDecimalConverter")
public class BigDecimalConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {

		if("".equals(valor)){
			return new BigDecimal("0");
		}
		
		if(valor.contains(",")){
			
			Locale currentLocale = new Locale("pt", "BR");
			DecimalFormatSymbols symbols = new DecimalFormatSymbols(currentLocale);
			symbols.setGroupingSeparator('.');
			symbols.setDecimalSeparator(',');
			String pattern = "#,##0.00;-#,##0.00";
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);
	
			BigDecimal valorBigDecimal = null;
			try {
				valorBigDecimal = (BigDecimal) decimalFormat.parse(valor);
			} catch (ParseException e) {
				System.out.println("Erro na convers�o de BigDecimal");
				e.printStackTrace();
			}
			
			//System.out.println("VALOR --> " + valor);
			
		    return valorBigDecimal;
		}
		else{
			return valor;
		}
	    
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object valor) {

		String retorno="";
		
		if(valor==null || valor.toString().equals("0.0000")){
			return retorno;
		}
		
		try{
			Double valordbl = new Double(0.00);
			String valorft = new String();
			Locale currentLocale = new Locale("pt", "BR");
			if(valor instanceof String){
				String valorstr = (String)valor;
				valordbl = new Double(valorstr);
				
				NumberFormat nf = NumberFormat.getCurrencyInstance(currentLocale);
				valorft = nf.format(valordbl);
			}
			else{
				BigDecimal valorstr = (BigDecimal)valor;
				valordbl = valorstr.doubleValue();
				
				NumberFormat nf = NumberFormat.getCurrencyInstance(currentLocale);
				DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
				decimalFormatSymbols.setCurrencySymbol("");
				((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
				valorft = nf.format(valordbl);
			}						
			
			return valorft;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return retorno;
		
	}

}
