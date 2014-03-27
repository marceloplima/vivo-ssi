package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.TiposAditivo;

@Local
public interface TipoAditivoInt {

	public List<TiposAditivo> recuperar();

	public TiposAditivo recuperarUnico(TiposAditivo tipoAditivo);
	
}
