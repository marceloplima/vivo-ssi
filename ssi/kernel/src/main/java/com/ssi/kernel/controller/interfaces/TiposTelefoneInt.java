package com.ssi.kernel.controller.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssi.kernel.model.TiposTelefone;

@Local
public interface TiposTelefoneInt {
	public List<TiposTelefone> recuperar();
	public TiposTelefone recuperarUnico(Long idtipotelefone);
	public boolean verificaExistente(TiposTelefone obj) throws IndexOutOfBoundsException;
	public List<TiposTelefone> recuperarFiltrado(Map<String, Object> filtros);
	public void incluir(TiposTelefone obj);
	public void alterar(TiposTelefone obj);
}
