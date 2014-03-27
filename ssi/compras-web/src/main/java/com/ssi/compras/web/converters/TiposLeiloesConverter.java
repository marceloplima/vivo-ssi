package com.ssi.compras.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.compras.domain.TiposLeiloes;

@FacesConverter("tipoleilaoConv")
public class TiposLeiloesConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		TiposLeiloes obj = new TiposLeiloes();
		
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
				Long id = Long.parseLong(value);
				obj = (new TiposLeiloes()).getByid(id);
			
				//System.out.println(">>"+obj.getIdpessoa()+">>"+obj.getCnmnome());
				
				return obj;
			}
		}
		catch(Exception e){
			//e.printStackTrace();
			obj = new TiposLeiloes();
			obj.setIdtipoleilao(null);
			obj.setCnmtipoleilao("");
			//System.out.println(">>caiu na exception PessoasConverter getAsObject()<<");
		}
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		TiposLeiloes objint = (TiposLeiloes)obj;
		
		try{
			if(objint!=null){
				return objint.getIdtipoleilao().toString();
			}else{
				return "";
			}
		}
		catch(Exception e){
			//e.printStackTrace();
			//System.out.println(">>caiu na exception PessoasConverter getAsString()<<");
			return "";
		}
	}

}
