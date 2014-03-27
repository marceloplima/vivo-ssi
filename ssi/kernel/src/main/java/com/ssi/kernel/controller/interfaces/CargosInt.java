package com.ssi.kernel.controller.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.model.Cargos;

@Local
public interface CargosInt {

	public List<Cargos> recuperar();
	public Cargos recuperarUnico(Cargos grupo);
	
}
