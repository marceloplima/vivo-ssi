package com.ssi.kernel.controller.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssi.kernel.model.ConfigSMTP;

@Local
public interface ConfigSMTPInt {
	public List<ConfigSMTP> recuperar();
	public ConfigSMTP recuperarUnico(Long idsmtp);
	public boolean verificaExistente(ConfigSMTP obj) throws IndexOutOfBoundsException;
	public List<ConfigSMTP> recuperarFiltrado(Map<String, Object> filtros);
	public void incluir(ConfigSMTP obj);
	public void alterar(ConfigSMTP obj);
}
