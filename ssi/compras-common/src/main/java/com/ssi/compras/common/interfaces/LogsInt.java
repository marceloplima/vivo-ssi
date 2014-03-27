package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Logs;

@Local
public interface LogsInt {

	public List<Logs> recuperarLogsDemanda(Demandas demanda);
	
}
