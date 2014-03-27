package com.ssi.kernel.controller.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssi.kernel.model.TiposUsuario;

@Local
public interface TiposUsuarioInt {
	public List<TiposUsuario> recuperar();
	public TiposUsuario recuperarUnico(TiposUsuario tipousuario);
	public boolean verificaExistente(TiposUsuario obj) throws IndexOutOfBoundsException;
	public List<TiposUsuario> recuperarFiltrado(Map<String, Object> filtros);
	public void incluir(TiposUsuario obj);
	public void alterar(TiposUsuario obj);
}
