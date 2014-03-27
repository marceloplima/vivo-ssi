package com.ssi.kernel.controller.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Orcamentos;

@Local
public interface OrcamentosInt {

	public List<Orcamentos> recuperarOrcamentosDaDemanda(Demandas demanda,String ativoInativo);
	
}
