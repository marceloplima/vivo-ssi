package com.ssi.adm.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.TiposFornecedor;

@FacesConverter("tipofornecedorConv")
public class TiposFornecedorConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		
		TiposFornecedor obj = new TiposFornecedor();
		try{
			if(value != "" && !value.equals("") && !value.equals("null")){
				Long id = Long.parseLong(value);
				obj = (new TiposFornecedor()).getByid(id);
			
				return obj;
			}
		}
		catch(Exception e){
			obj = new TiposFornecedor();
			obj.setIdtipofornecedor(null);
			obj.setCnmtipofornecedor("");
		}
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		TiposFornecedor objint = (TiposFornecedor)obj;
		
		try{
			return objint.getIdtipofornecedor().toString();
		}
		catch(Exception e){
			return "";
		}
	}

}
