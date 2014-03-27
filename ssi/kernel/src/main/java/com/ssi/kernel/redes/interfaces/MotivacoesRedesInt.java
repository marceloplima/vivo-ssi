package com.ssi.kernel.redes.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.redes.domain.MotivacoesRedes;

@Local
public interface MotivacoesRedesInt {

	public List<MotivacoesRedes> recuperar();
	
}
