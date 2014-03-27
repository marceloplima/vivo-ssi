package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Agendamentos;
import com.ssi.kernel.compras.domain.FornecedoresAgendamentos;

@Local
public interface FornecedoresAgendamentosInt {

	List<FornecedoresAgendamentos> recuperarFornecedoresAgendamentos(Agendamentos agendamentos);
	void remover(FornecedoresAgendamentos fornecedorAgendamento);
	FornecedoresAgendamentos alterar(FornecedoresAgendamentos fornecedorAgendamento);
	FornecedoresAgendamentos incluir(FornecedoresAgendamentos fornecedorAgendamento);
	
}
