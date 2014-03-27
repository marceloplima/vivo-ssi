package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Pareceres;
import com.ssi.kernel.compras.domain.Status;

@Local
public interface PareceresInt {

	public List<Pareceres> recuperarPareceresPorStatus(Status status);
	
}
