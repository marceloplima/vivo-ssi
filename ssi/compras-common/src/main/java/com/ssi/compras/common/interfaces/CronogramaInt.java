package com.ssi.compras.common.interfaces;



import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Cronogramas;
import com.ssi.kernel.compras.domain.Demandas;

@Local
public interface CronogramaInt {
	
	public Cronogramas recuperarPorAtividade(Long idatividade, Demandas demanda);
	public void atualizar(Cronogramas obj);

}
