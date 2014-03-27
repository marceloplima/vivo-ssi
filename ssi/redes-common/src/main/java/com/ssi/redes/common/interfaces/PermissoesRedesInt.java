package com.ssi.redes.common.interfaces;


import javax.ejb.Local;

import com.ssi.kernel.model.Pessoas;

@Local
public interface PermissoesRedesInt{
	public boolean verificarPermissaoEmissor(Pessoas pessoa);
}
