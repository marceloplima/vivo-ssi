package com.ssi.redes.common.interfaces;


import java.util.List;

import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.model.Pessoas;

public interface CopiadosInt {
	
	List<Pessoas> recuperarCopiadosDemanda(DemandasRedes demanda);
	
}
