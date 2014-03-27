package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.TiposCompras;

@Local
public interface TiposComprasInt {
	
	public List<TiposCompras> recuperar();

	public TiposCompras recuperarUnico(TiposCompras obj);

}
