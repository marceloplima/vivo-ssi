package com.ssi.redes.common.interfaces;


import javax.ejb.Local;

import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.PapeisRedes;
import com.ssi.kernel.model.Pessoas;

@Local
public interface DemandasRedesServiceInt {

	DemandasRedes setaPessoaComQuemEsta(Pessoas pessoa,DemandasRedes demanda);
	DemandasRedes setaGrupoComQuemEsta(PapeisRedes papel,DemandasRedes demanda);
	
}
