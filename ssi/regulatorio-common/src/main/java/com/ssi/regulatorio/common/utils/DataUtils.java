package com.ssi.regulatorio.common.utils;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.ssi.kernel.utils.DataUtilsInt;

@Stateless(mappedName = "DataUtils")
public class DataUtils implements DataUtilsInt{
		
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;

	public DataUtils() {}

	@Override
	public Date adicionaDias(Date data, int prazo) {
		Calendar dt = Calendar.getInstance();
		dt.setTime(data);
		dt.add(Calendar.DATE, prazo);
		return dt.getTime();		
	}

	@Override
	public Date adicionaMeses(Date data, int prazo) {
		Calendar dt = Calendar.getInstance();
		dt.setTime(data);
		dt.add(Calendar.MONTH, prazo);
		return dt.getTime();
	}

	@Override
	public boolean isFimDeSemana(Calendar dia) {

		if (dia.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| dia.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return true;
		}

		return false;
	}
			

}
