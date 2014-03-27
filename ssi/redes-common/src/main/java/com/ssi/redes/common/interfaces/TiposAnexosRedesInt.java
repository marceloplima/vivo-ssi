package com.ssi.redes.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.redes.domain.TiposAnexosRedes;

@Local
public interface TiposAnexosRedesInt {
	
	public List<TiposAnexosRedes> recuperarTiposAnexos();

}
