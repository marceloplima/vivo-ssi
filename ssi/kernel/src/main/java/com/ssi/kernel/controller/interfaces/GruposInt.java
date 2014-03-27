package com.ssi.kernel.controller.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssi.kernel.model.Funcionalidades;
import com.ssi.kernel.model.Grupos;

@Local
public interface GruposInt {

	public List<Grupos> recuperar();
	public Grupos recuperarUnico(Grupos grupo);
	public boolean verificaExistente(Grupos obj) throws IndexOutOfBoundsException;
	public List<Funcionalidades> retornarGruposFuncionalidades(Grupos obj);
	public List<Grupos> recuperarFiltrado(Map<String, Object> filtros);
	public void incluir(Grupos obj);
	public void alterar(Grupos obj);
	
}
