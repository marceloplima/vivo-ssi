package com.ssi.compras.service;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ssi.compras.common.interfaces.FeriadosServiceInt;
import com.ssi.kernel.controller.interfaces.FeriadosInt;
import com.ssi.kernel.model.Feriados;
import com.ssi.kernel.model.FeriadosLocais;
import com.ssi.kernel.model.FeriadosNacionais;
import com.ssi.kernel.model.Ufs;
import com.ssi.kernel.redes.interfaces.utils.DataUtilsInt;

@Stateless(mappedName = "FeriadosService")
public class FeriadosService implements FeriadosServiceInt {
	
	@EJB
	private FeriadosInt feriadosInt;
	
	@EJB
	private DataUtilsInt dataUtilsInt;
	
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;

	public FeriadosService() {}

	@Override
	public List<Feriados> retornaFeriadosNacionais(Calendar dataInicial,
			Calendar dataFinal) {
		
		List<Feriados> feriados = new ArrayList<Feriados>();

		for (FeriadosNacionais fn : feriadosInt.recuperarFeriadosNacionaisPorPeriodo(dataInicial,dataFinal)) {
			feriados.add(fn.getFeriadonacional());
		}
		
		return feriados;
	}

	@Override
	public List<Feriados> retornaFeriadosLocais(Calendar dataInicial,
			Calendar dataFinal, List<Ufs> ufs) {

		List<Feriados> feriados = new ArrayList<Feriados>();
		
		for (FeriadosLocais fl : feriadosInt.recuperarFeriadosLocaisPorPeriodo(dataInicial, dataFinal,ufs)) {
			feriados.add(fl.getFeriadolocal());
		}
		
		return feriados;
	}
	
	@Override
	public boolean isDataFeriado(List<Feriados> feriados, Calendar dia) {

		int resultadoComparacaoDia;
		int resultadoComparacaoMes;

		for (Feriados feriado : feriados) {
			if ("NACIONAL".equals(retornaTipoFeriado(feriado))) {
				resultadoComparacaoDia = Integer.compare(dia.get(Calendar.DAY_OF_MONTH), feriado.getFeriadosnacionais().getDia());
				resultadoComparacaoMes = Integer.compare(dia.get(Calendar.MONTH), feriado.getFeriadosnacionais().getMes());
				//resultadoComparacaoDia = Integer.compare(dia.get(Calendar.DAY_OF_MONTH), feriado.getFeriadosnacionais().getDia());
				//resultadoComparacaoMes = Integer.compare(dia.get(Calendar.MONTH), feriado.getFeriadosnacionais().getMes());

				if (resultadoComparacaoDia == 0 && resultadoComparacaoMes == 0) {
					return true;
				}
			} else {
				if (feriado.getFeriadosLocais().getData().equals(dia)) {
					return true;
				}
			}
		}

		return false;
	}

	private String retornaTipoFeriado(Feriados feriado) {

		if (feriado.getFeriadosLocais() == null) {
			return "NACIONAL";
		}

		return "LOCAL";

	}

	@Override
	public List<Feriados> retornaFeriadosNacionaisELocais(
			int qtdMesesAPartirDaDataCorrente, List<Ufs> ufs) {
		Calendar calendarInicio = Calendar.getInstance();
		Calendar calendarFim = Calendar.getInstance();
		calendarFim.setTime(dataUtilsInt.adicionaMeses(calendarFim.getTime(), qtdMesesAPartirDaDataCorrente));		

		List<Feriados> feriados = new ArrayList<Feriados>();
		
		feriados.addAll(retornaFeriadosLocais(calendarInicio, calendarFim, ufs));
		feriados.addAll(retornaFeriadosNacionais(calendarInicio, calendarFim));
		
		return feriados;
		
	}

			

}
