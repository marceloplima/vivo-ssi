package com.ssi.kernel.controller.interfaces;

import javax.ejb.Local;

import com.ssi.kernel.model.Emails;

@Local
public interface EmailsInt {

	public boolean verificaExistente(Emails email) throws IndexOutOfBoundsException;
	public Emails incluir(Emails email);
	public void alterar(Emails email);
	public void remover(Emails email);
	
}
