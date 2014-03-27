package com.ssi.redes.common.utils;


import java.util.Calendar;
import java.util.Date;

import javax.ejb.Local;

@Local
public interface DataUtilsInt {

	Date adicionaDias(Date data, int prazo);
	Date adicionaMeses(Date data, int prazo);
	boolean isFimDeSemana(Calendar dia);	
}
