package com.ssi.redes.common.utils;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



@Stateless(mappedName = "DataUtils")
public class DataUtils implements DataUtilsInt {
		
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;

	public DataUtils() {}

	public Date adicionaDias(Date data, int prazo) {
		Calendar dt = Calendar.getInstance();
		dt.setTime(data);
		dt.add(Calendar.DATE, prazo);
		return dt.getTime();		
	}

	public Date adicionaMeses(Date data, int prazo) {
		Calendar dt = Calendar.getInstance();
		dt.setTime(data);
		dt.add(Calendar.MONTH, prazo);
		return dt.getTime();
	}

	public boolean isFimDeSemana(Calendar dia) {

		if (dia.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| dia.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return true;
		}

		return false;
	}
			

}
