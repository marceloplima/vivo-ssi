package com.ssi.kernel.redes.interfaces;

import javax.ejb.Local;

import com.ssi.kernel.redes.domain.TiposLogRedes;

@Local
public interface TiposLogRedesInt {

	public TiposLogRedes recuperarUnico(Long idTipoLog);
	
}
