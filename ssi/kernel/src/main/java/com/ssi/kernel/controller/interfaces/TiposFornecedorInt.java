package com.ssi.kernel.controller.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssi.kernel.model.TiposFornecedor;

@Local
public interface TiposFornecedorInt {
	public List<TiposFornecedor> recuperar();
	public TiposFornecedor recuperarUnico(TiposFornecedor tipofornecedor);
	public boolean verificaExistente(TiposFornecedor obj) throws IndexOutOfBoundsException;
	public List<TiposFornecedor> recuperarFiltrado(Map<String, Object> filtros);
	public void incluir(TiposFornecedor obj);
	public void alterar(TiposFornecedor obj);
}
