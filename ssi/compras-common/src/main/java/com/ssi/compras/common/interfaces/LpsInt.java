package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Lps;

@Local
public interface LpsInt {

	public List<Lps> recuperar();

	public Lps recuperarUnico(Lps lp);
	
}
