package com.ssi.compras.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssi.kernel.compras.domain.TiposCondicionamento;

@FacesConverter("tipoCondicionamentoConverter")
public class TipoCondicionamentoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		
		TiposCondicionamento tiposCondicionamento = new TiposCondicionamento();
		tiposCondicionamento.setIdtipocondicionamento(null);
		
		if (valor.trim().equals("")) {
			return tiposCondicionamento;
		} else {
			try {
				Long id = Long.parseLong(valor);				
				tiposCondicionamento.setIdtipocondicionamento(id);

				return tiposCondicionamento;

			} catch (Exception e) {
				System.out.println("Erro no converter Tipo de Condicionamento");
				e.printStackTrace();
				throw new ConverterException(e);
			}
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		
		TiposCondicionamento tiposCondicionamento = (TiposCondicionamento)obj;
		
		try{
			return tiposCondicionamento.getIdtipocondicionamento().toString();
		}
		catch(Exception e){
			throw new ConverterException(e);
		}
	}

}
