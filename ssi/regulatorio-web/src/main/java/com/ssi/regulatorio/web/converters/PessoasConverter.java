package com.ssi.regulatorio.web.converters;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.Pessoas;

@FacesConverter("pessoaConv")
public class PessoasConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		Pessoas obj = new Pessoas();
		
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
				Long id = Long.parseLong(value);
				obj = (new Pessoas()).getByid(id);
			
				//System.out.println(">>"+obj.getIdpessoa()+">>"+obj.getCnmnome());
				
				return obj;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			obj = new Pessoas();
			obj.setIdpessoa(null);
			obj.setCnmnome("");
			System.out.println(">>caiu na exception PessoasConverter getAsObject()<<");
		}
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		Pessoas objint = (Pessoas)obj;
		
		try{
			if(objint!=null){
				return objint.getIdpessoa().toString();
			}else{
				return "";
			}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(">>caiu na exception PessoasConverter getAsString()<<");
			return "";
		}
	}

}
