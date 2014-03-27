package com.ssi.kernel.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.FeriadosInt;
import com.ssi.kernel.model.FeriadosLocais;
import com.ssi.kernel.model.FeriadosNacionais;
import com.ssi.kernel.model.Ufs;

@Stateless(mappedName = "SLSBFeriados")
public class SLSBFeriados implements FeriadosInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	@Override
	public List<FeriadosNacionais> recuperarFeriadosNacionaisPorPeriodo(
			Calendar dataInicial, Calendar dataFinal) {
		
		int diaMesInicial;
		int diaMesFinal;
		
		Calendar calendarInicio = Calendar.getInstance();
		calendarInicio.setTime(dataInicial.getTime());

		Calendar calendarFim = Calendar.getInstance();
		calendarFim.setTime(dataFinal.getTime());
		
		String jpaquery = "SELECT fn FROM FeriadosNacionais fn join fetch fn.feriadonacional f WHERE ";
		jpaquery += " :diaMesInicial <= (fn.dia + fn.mes)  AND (fn.dia + fn.mes) <= :diaMesFinal";
		
		TypedQuery<FeriadosNacionais> q = em.createQuery(jpaquery,FeriadosNacionais.class);
		
		if(calendarInicio.get(Calendar.MONTH)<=calendarFim.get(Calendar.MONTH)){
			diaMesInicial = calendarInicio.get(Calendar.DAY_OF_MONTH) + calendarInicio.get(Calendar.MONTH)+1;
			diaMesFinal = calendarFim.get(Calendar.DAY_OF_MONTH) + calendarFim.get(Calendar.MONTH)+1;
			
			q.setParameter("diaMesInicial", diaMesInicial);
			q.setParameter("diaMesFinal", diaMesFinal);
			
			return q.getResultList();
		}else{
			
			List<FeriadosNacionais> feriadosNacionais = new ArrayList<FeriadosNacionais>();
			
			diaMesInicial = calendarInicio.get(Calendar.DAY_OF_MONTH) + calendarInicio.get(Calendar.MONTH)+1;
			diaMesFinal = 43; //31 - �ltimo dia do ano + 12 �ltimo m�s do ano;
			
			q.setParameter("diaMesInicial", diaMesInicial);
			q.setParameter("diaMesFinal", diaMesFinal);
			
			feriadosNacionais.addAll(q.getResultList());
						
			diaMesInicial = 2; //1-Dia + 1-Mes
			diaMesFinal = calendarFim.get(Calendar.DAY_OF_MONTH) + calendarFim.get(Calendar.MONTH)+1;

			q.setParameter("diaMesInicial", diaMesInicial);
			q.setParameter("diaMesFinal", diaMesFinal);
			
			feriadosNacionais.addAll(q.getResultList());			
			
			return feriadosNacionais;
			
		}
											
	}

	@Override
	public List<FeriadosLocais> recuperarFeriadosLocaisPorPeriodo(
			Calendar dataInicial, Calendar dataFinal, List<Ufs> ufs) {
		
		Calendar calendarInicio = Calendar.getInstance();
		calendarInicio.setTime(dataInicial.getTime());

		Calendar calendarFim = Calendar.getInstance();
		calendarFim.setTime(dataFinal.getTime());
		
		String jpaquery = "SELECT fl FROM FeriadosLocais fl join fetch fl.feriadolocal f WHERE ";
		jpaquery += " :dataInicial <= fl.data  AND fl.data <= :dataFinal";
		
		if(ufs != null){
			for(Ufs uf:ufs){
				jpaquery += " AND fl.ufferiado.iduf = " + uf.getIduf();	
			}	
		}
		
		TypedQuery<FeriadosLocais> q = em.createQuery(jpaquery,FeriadosLocais.class);
				
		q.setParameter("dataInicial", dataInicial);
		q.setParameter("dataFinal", dataFinal);
		
		return q.getResultList();
	}

}
