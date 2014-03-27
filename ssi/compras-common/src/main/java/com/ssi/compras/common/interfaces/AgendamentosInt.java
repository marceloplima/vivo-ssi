package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Agendamentos;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Status;

@Local
public interface AgendamentosInt {

	List<Agendamentos> recuperarAgendamentos(Demandas demanda);
	List<Agendamentos> recuperarAgendamentosPorStatus(Demandas demanda,Status status);
	
}
