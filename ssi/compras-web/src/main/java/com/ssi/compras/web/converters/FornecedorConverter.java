package com.ssi.compras.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.model.Fornecedores;

@FacesConverter("fornecedorConverter")
public class FornecedorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		
		Fornecedores fornecedor = new Fornecedores();
		fornecedor.setIdfornecedor(null);
		fornecedor.setCnmfornecedor("");			    
		
		if (valor.trim().equals("")) {
			return fornecedor;
		} else {
			try {
				Long id = Long.parseLong(valor);
				fornecedor.setIdfornecedor(id);

				return fornecedor;

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Erro no fornecedor converter");
				return fornecedor;
			}
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj == null) {
			return "";
		}

		Fornecedores fornecedores = (Fornecedores) obj;

		try {
			return fornecedores.getIdfornecedor().toString();
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
