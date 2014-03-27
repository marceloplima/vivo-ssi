package com.ssi.kernel.controller.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.model.Centrais;
import com.ssi.kernel.model.Ufs;

@Local
public interface CentraisInt {

	public List<Centrais> recuperar();
	public List<Centrais> recuperarPorUf(Ufs uf);
	public Centrais recuperaUnico(Centrais obj);
	
}
