package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.TiposLeiloes;

@Local
public interface TiposLeiloesInt {
	public List<TiposLeiloes> recuperar();
}
