package com.ssi.compras.common.interfaces;


import java.util.List;
import java.util.Map;

import com.ssi.kernel.compras.domain.Anexos;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.TiposAnexos;

public interface AnexosInt {

	public void incluir(Anexos anexo);
	public Anexos getRowData(Object rowKey);
	public Integer getRowCount(Map<String,Object> filtros);
	public List<Anexos> retornarPaginado(int firstRow, int numberOfRows, Map<String,Object> filtros);
	public Anexos retornarUltimaMinutaDemanda(Demandas demanda);
	public void remover(Anexos anexo);
	public TiposAnexos recuperarTipoAnexo(Long idtipoanexo);
	
}
