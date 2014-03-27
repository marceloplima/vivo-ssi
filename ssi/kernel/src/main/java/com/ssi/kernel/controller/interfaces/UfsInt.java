package com.ssi.kernel.controller.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.model.Ufs;

@Local
public interface UfsInt {

	public List<Ufs> recuperar();
	public Ufs recuperaUnico(Ufs uf);
	
}
