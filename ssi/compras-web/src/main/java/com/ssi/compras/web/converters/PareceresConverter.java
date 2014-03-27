package com.ssi.compras.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.compras.domain.Pareceres;

@FacesConverter("parecerConv")
public class PareceresConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		Pareceres obj = new Pareceres();
		
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
				Long id = Long.parseLong(value);
				obj = (new Pareceres()).getByid(id);
			
				//System.out.println(">>"+obj.getIdpessoa()+">>"+obj.getCnmnome());
				
				return obj;
			}
		}
		catch(Exception e){
			//e.printStackTrace();
			obj = new Pareceres();
			obj.setIdparecer(null);
			obj.setCnmparecer("");
			//System.out.println(">>caiu na exception PessoasConverter getAsObject()<<");
		}
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		Pareceres objint = (Pareceres)obj;
		
		try{
			if(objint!=null){
				return objint.getIdparecer().toString();
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
