package com.ssi.compras.common.interfaces;


import javax.ejb.Local;

import com.ssi.kernel.compras.domain.TesteA;

@Local
public interface TesteAint {
	public TesteA inserir(TesteA obj);
	public TesteA recuperar();
}
