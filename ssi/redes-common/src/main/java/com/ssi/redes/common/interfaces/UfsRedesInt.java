package com.ssi.redes.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.model.Ufs;

@Local
public interface UfsRedesInt {

	public List<Ufs> recuperar();
	public Ufs recuperaUnico(Ufs uf);
	
}
