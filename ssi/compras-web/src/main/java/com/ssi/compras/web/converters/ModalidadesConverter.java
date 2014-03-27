package com.ssi.compras.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.compras.domain.Modalidades;

@FacesConverter("modalidadeConv")
public class ModalidadesConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		Modalidades obj = new Modalidades();
		
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
				Long id = Long.parseLong(value);
				obj = (new Modalidades()).getByid(id);
			
				//System.out.println(">>"+obj.getIdpessoa()+">>"+obj.getCnmnome());
				
				return obj;
			}
		}
		catch(Exception e){
			//e.printStackTrace();
			obj = new Modalidades();
			obj.setIdmodalidade(null);
			obj.setCnmmodalidade("");
			//System.out.println(">>caiu na exception PessoasConverter getAsObject()<<");
		}
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		Modalidades objint = (Modalidades)obj;
		
		try{
			if(objint!=null){
				return objint.getIdmodalidade().toString();
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
