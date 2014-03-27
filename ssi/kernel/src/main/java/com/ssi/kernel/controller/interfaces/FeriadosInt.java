package com.ssi.kernel.controller.interfaces;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.model.FeriadosLocais;
import com.ssi.kernel.model.FeriadosNacionais;
import com.ssi.kernel.model.Ufs;

@Local
public interface FeriadosInt {

	public List<FeriadosNacionais> recuperarFeriadosNacionaisPorPeriodo(Calendar dataInicial,Calendar dataFinal);
	public List<FeriadosLocais> recuperarFeriadosLocaisPorPeriodo(Calendar dataInicial,Calendar dataFinal, List<Ufs> ufs);
	
}
