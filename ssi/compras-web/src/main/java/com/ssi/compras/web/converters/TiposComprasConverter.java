package com.ssi.compras.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.compras.domain.TiposCompras;

@FacesConverter("tipocompraConv")
public class TiposComprasConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		TiposCompras obj = new TiposCompras();
		
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
				Long id = Long.parseLong(value);
				obj = (new TiposCompras()).getByid(id);
			
				//System.out.println(">>"+obj.getIdpessoa()+">>"+obj.getCnmnome());
				
				return obj;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			obj = new TiposCompras();
			obj.setIdtipocompra(null);
			obj.setCnmtipocompra("");
			System.out.println(">>caiu na exception getAsObject()<<");
		}
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		TiposCompras objint = (TiposCompras)obj;
		
		try{
			if(objint!=null){
				return objint.getIdtipocompra().toString();
			}else{
				return "";
			}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(">>caiu na exception getAsString()<<");
			return "";
		}
	}

}
