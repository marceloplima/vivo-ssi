package com.ssi.kernel.controller.interfaces;

import javax.ejb.Local;

import com.ssi.kernel.model.LogAcesso;

@Local
public interface LogAcessoInt {
	public void incluir(LogAcesso obj);
}
