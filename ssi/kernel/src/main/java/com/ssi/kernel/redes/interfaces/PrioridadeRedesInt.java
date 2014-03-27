package com.ssi.kernel.redes.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.redes.domain.PrioridadesRedes;

@Local
public interface PrioridadeRedesInt {
	
	public List<PrioridadesRedes> recuperar();

	public PrioridadesRedes recuperarUnico(PrioridadesRedes prioridade);

}
