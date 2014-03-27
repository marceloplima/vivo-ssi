package com.ssi.kernel.controller.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.model.Dominios;

@Local
public interface DominiosInt {
	public List<Dominios> recuperar();
	public Dominios recuperarUnico(Long iddominio);
	public boolean remover();
}
