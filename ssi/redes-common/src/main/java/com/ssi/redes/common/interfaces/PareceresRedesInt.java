package com.ssi.redes.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.redes.domain.PareceresRedes;
import com.ssi.kernel.redes.domain.StatusRedes;

@Local
public interface PareceresRedesInt {
	
	List<PareceresRedes> recuperar();

	PareceresRedes recuperarUnico(Long id);
	
	List<PareceresRedes> recuperarPorStatus(StatusRedes status);

}
