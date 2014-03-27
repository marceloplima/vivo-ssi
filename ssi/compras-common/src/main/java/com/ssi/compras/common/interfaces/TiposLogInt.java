package com.ssi.compras.common.interfaces;


import javax.ejb.Local;

import com.ssi.kernel.compras.domain.TiposLog;

@Local
public interface TiposLogInt {

	public TiposLog recuperarUnico(Long idTipoLog);
	
}
