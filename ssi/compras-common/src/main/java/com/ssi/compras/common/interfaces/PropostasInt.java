package com.ssi.compras.common.interfaces;


import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Propostas;
import com.ssi.kernel.model.Fornecedores;

@Local
public interface PropostasInt {
	public void registrar(Propostas obj);
	public List<Propostas> retornarPaginado(int firstRow, int numberOfRows, Map<String,Object> filtros);
	public Propostas getRowData(Object rowKey);
	public Integer getRowCount(Map<String,Object> filtros);
	public Propostas verificaExistenteEncaminhada(Demandas demanda);
	public Propostas verificaExistenteDemanda(Demandas demanda);
	public List<Propostas> recuperarPropostas(Demandas demanda);
	public List<Propostas> verificarTodasReprovadas(Demandas demanda);
	public List<Propostas> verificarTodasPropostas(Demandas demanda);
	public List<Propostas> recuperarPropostasAprovadas(Demandas demanda);
	public List<Fornecedores> recuperarFornecedoresPropostas(Propostas prop);
}
