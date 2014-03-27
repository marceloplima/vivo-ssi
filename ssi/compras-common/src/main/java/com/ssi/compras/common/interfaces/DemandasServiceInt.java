package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Lps;
import com.ssi.kernel.compras.domain.Papeis;
import com.ssi.kernel.model.Atividades;
import com.ssi.kernel.model.Pessoas;

@Local
public interface DemandasServiceInt {

	Demandas setaPessoaComQuemEsta(Pessoas pessoa,Demandas demanda);
	Demandas setaGrupoComQuemEsta(Papeis papel,Demandas demanda,List<Lps> lps);
	Demandas setarAtividadeCronograma(Demandas demanda,Atividades atividade,Pessoas pessoaConclusao);
	
}
