package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.RespostasQuestionamentos;

@Local
public interface RespostaQuestionamentoInt {
	
	public List<RespostasQuestionamentos> recuperarRespostasQuestionamento(Demandas demanda);
	public RespostasQuestionamentos incluir(RespostasQuestionamentos respostasQuestionamentos);

}
