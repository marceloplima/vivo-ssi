package com.ssi.redes.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.redes.domain.StatusRedes;
import com.ssi.kernel.redes.domain.TiposDocumentosRedes;

@Local
public interface StatusRedesInt {

	public List<StatusRedes> recuperar(TiposDocumentosRedes tipoDocumento);
	
	public StatusRedes recuperarUnico(StatusRedes status);
	
}
