package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.TiposCondicionamento;

@Local
public interface TipoCondicionamentoInt {

	public List<TiposCondicionamento> recuperar();

	public TiposCondicionamento recuperarUnico(TiposCondicionamento tiposCondicionamento);
	
}
