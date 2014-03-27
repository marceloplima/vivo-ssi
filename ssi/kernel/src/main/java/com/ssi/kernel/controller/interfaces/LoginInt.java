package com.ssi.kernel.controller.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.model.Areas;
import com.ssi.kernel.model.Funcionalidades;

@Local
public interface LoginInt {
	public boolean autenticar();
	public List<Areas> verificarAcessoAreas();
	public List<Funcionalidades> verificarAcessoSistema();
}
