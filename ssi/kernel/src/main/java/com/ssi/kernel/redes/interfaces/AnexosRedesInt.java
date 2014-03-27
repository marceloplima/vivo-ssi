package com.ssi.kernel.redes.interfaces;

import java.util.List;
import java.util.Map;

import com.ssi.kernel.redes.domain.AnexosRedes;
import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.TiposAnexosRedes;

public interface AnexosRedesInt {

	public void incluir(AnexosRedes anexo);
	public AnexosRedes getRowData(Object rowKey);
	public Integer getRowCount(Map<String,Object> filtros);
	public List<AnexosRedes> retornarPaginado(int firstRow, int numberOfRows, Map<String,Object> filtros);
	public AnexosRedes retornarUltimaMinutaDemanda(DemandasRedes demanda);
	public void remover(AnexosRedes anexo);
	public TiposAnexosRedes recuperarTipoAnexo(Long idtipoanexo);
	
}
