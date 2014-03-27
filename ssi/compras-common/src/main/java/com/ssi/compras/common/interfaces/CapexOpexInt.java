package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.CapexOpex;

@Local
public interface CapexOpexInt {
	
	public List<CapexOpex> recuperar();
	public CapexOpex recuperarPorId(Long id);

}
