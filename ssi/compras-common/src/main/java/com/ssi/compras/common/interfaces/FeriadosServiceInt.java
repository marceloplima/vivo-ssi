package com.ssi.compras.common.interfaces;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.model.Feriados;
import com.ssi.kernel.model.Ufs;

@Local
public interface FeriadosServiceInt {

	List<Feriados> retornaFeriadosNacionaisELocais(int qtdMesesAPartirDaDataCorrente,List<Ufs> ufs);
	List<Feriados> retornaFeriadosNacionais(Calendar dataInicial, Calendar dataFinal);
	List<Feriados> retornaFeriadosLocais(Calendar dataInicial, Calendar dataFinal,List<Ufs> ufs);
	boolean isDataFeriado(List<Feriados> feriados, Calendar dia);
	
}
