package com.ssi.compras.common.interfaces;


import java.util.List;
import java.util.Map;

import com.ssi.kernel.compras.domain.CapexOpex;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.TiposAditivo;
import com.ssi.kernel.model.Pessoas;

public interface DemandaInt {
	
	Demandas incluir(Demandas demanda);
	Demandas alterar(Demandas demanda);
	Demandas recuperaUltimaDemandaComNumero();
	List<Demandas> recuperar();	
	public Demandas getRowData(Object rowKey);
	public Integer getRowCount(Map<String,Object> filtros);
	public List<Demandas> retornarPaginado(int firstRow, int numberOfRows, Map<String,Object> filtros);
	public Demandas recuperarDemandaEspecifica(Long iddemandaeditar);
	public List<CapexOpex> recuperarCapexOpex(Demandas demanda);
	public List<TiposAditivo> recuperaTiposAditivos(Demandas demanda);
	List<Demandas> recuperarDemandasCriadasPeloAutor(Pessoas pessoa);
	List<Demandas> recuperarDemandasEstaoComPessoaLogada(Pessoas pessoa);
	
}
