package com.ssi.kernel.redes.interfaces;

import javax.ejb.Local;

import com.ssi.kernel.redes.domain.Instrucao;

@Local
public interface InstrucaoRedesInt {

	public Instrucao recuperaUnico(Long idInstrucao);
	
}
