package com.ssi.kernel.controller.interfaces;

import javax.ejb.Local;

import com.ssi.kernel.model.Dominios;
import com.ssi.kernel.model.Usuarios;

@Local
public interface AutenticadorInt {

	public void setUsuario(Usuarios usuario);
	public void setDominio(Dominios dominio);
	public boolean autenticar();
}
