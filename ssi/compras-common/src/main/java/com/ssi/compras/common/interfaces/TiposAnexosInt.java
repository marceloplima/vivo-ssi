package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.TiposAnexos;

@Local
public interface TiposAnexosInt {
	
	public List<TiposAnexos> recuperarTiposAnexos();

}
