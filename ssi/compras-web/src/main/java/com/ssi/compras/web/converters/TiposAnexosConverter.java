package com.ssi.compras.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.compras.domain.TiposAnexos;

@FacesConverter("tipoanexoConv")
public class TiposAnexosConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		TiposAnexos obj = new TiposAnexos();
		
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
				Long id = Long.parseLong(value);
				obj = (new TiposAnexos()).getByid(id);
			
				//System.out.println(">>"+obj.getIdpessoa()+">>"+obj.getCnmnome());
				
				return obj;
			}
		}
		catch(Exception e){
			//e.printStackTrace();
			obj = new TiposAnexos();
			obj.setIdtipoanexo(null);
			obj.setCnmtipoanexo("");
			//System.out.println(">>caiu na exception PessoasConverter getAsObject()<<");
		}
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		TiposAnexos objint = (TiposAnexos)obj;
		
		try{
			if(objint!=null){
				return objint.getIdtipoanexo().toString();
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
