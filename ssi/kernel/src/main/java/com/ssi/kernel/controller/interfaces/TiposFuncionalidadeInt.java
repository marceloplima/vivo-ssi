package com.ssi.kernel.controller.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssi.kernel.model.TiposFuncionalidade;

@Local
public interface TiposFuncionalidadeInt {

	public List<TiposFuncionalidade> recuperar();
	public TiposFuncionalidade recuperarUnico(Long idtipofuncionalidade);
	public boolean verificaExistente(TiposFuncionalidade obj) throws IndexOutOfBoundsException;
	public List<TiposFuncionalidade> recuperarFiltrado(Map<String, Object> filtros);
	public void incluir(TiposFuncionalidade obj);
	public void alterar(TiposFuncionalidade obj);
	public boolean remover();
	
}
