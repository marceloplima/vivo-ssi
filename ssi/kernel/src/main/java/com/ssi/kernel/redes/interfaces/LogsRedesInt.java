package com.ssi.kernel.redes.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.LogsRedes;

@Local
public interface LogsRedesInt {

	public List<LogsRedes> recuperarLogsDemanda(DemandasRedes demanda);
	
}
