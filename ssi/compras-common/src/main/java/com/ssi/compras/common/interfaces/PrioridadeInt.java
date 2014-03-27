package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Prioridades;

@Local
public interface PrioridadeInt {
	
	public List<Prioridades> recuperar();

	public Prioridades recuperarUnico(Prioridades prioridade);

}
