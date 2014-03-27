package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Status;
import com.ssi.kernel.compras.domain.TiposDocumentos;

@Local
public interface StatusInt {

	public List<Status> recuperar(TiposDocumentos tipoDocumento);
	
	public Status recuperarUnico(Status status);
	
}
