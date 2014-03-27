package com.ssi.kernel.redes.interfaces;

import java.util.List;
import java.util.Map;

import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Regionais;

public interface DemandasRedesInt {
	
	DemandasRedes recuperaUltimaDemandaComNumero();
	List<DemandasRedes> recuperar();	
	public DemandasRedes getRowData(Object rowKey);
	public Integer getRowCount(Map<String,Object> filtros);
	public List<DemandasRedes> retornarPaginado(int firstRow, int numberOfRows, Map<String,Object> filtros);
	public DemandasRedes recuperarDemandaEspecifica(Long iddemandaeditar);
	List<DemandasRedes> recuperarDemandasCriadasPeloAutor(Pessoas pessoa);
	List<DemandasRedes> recuperarDemandasEstaoComPessoaLogada(Pessoas pessoa);
	DemandasRedes persistir(DemandasRedes demanda);
	DemandasRedes alterar(DemandasRedes demanda);
	void incluir(DemandasRedes demanda);
	List<Regionais> recuperarRegionais(DemandasRedes demandas);
	DemandasRedes recuperaRegistroDaAreaDeTransferencia(String universalId);
	List<DemandasRedes> recuperaDemandaPeloUniversalId(String universalId);
	DemandasRedes recuperaDemandaPeloNumeroSSINotes(String numeroSSI);
	
}
