package com.ssi.kernel.redes.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.redes.domain.TiposAnexosRedes;

@Local
public interface TiposAnexosRedesInt {
	
	public List<TiposAnexosRedes> recuperarTiposAnexos();

}
