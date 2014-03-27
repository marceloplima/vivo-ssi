package com.ssi.kernel.controller.interfaces;

import javax.ejb.Local;

import com.ssi.kernel.model.Telefones;

@Local
public interface TelefonesInt {
	public boolean verificaExistente(Telefones telefone) throws IndexOutOfBoundsException;
	public Telefones incluir(Telefones telefone);
	public void alterar(Telefones telefone);
	public void remover(Telefones telefone);
}
