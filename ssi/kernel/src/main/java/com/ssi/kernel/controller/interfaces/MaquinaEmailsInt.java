package com.ssi.kernel.controller.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssi.kernel.model.MaquinaEmails;

@Local
public interface MaquinaEmailsInt {
	public List<MaquinaEmails> recuperar();
	public MaquinaEmails recuperarUnico(MaquinaEmails grupo);
	public List<MaquinaEmails> recuperarFiltrado(Map<String, Object> filtros);
	public void incluir(MaquinaEmails obj);
	public void alterar(MaquinaEmails obj);
}
