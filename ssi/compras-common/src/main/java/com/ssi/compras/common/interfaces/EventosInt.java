package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.EventosAberturasRequisicoesCompras;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.EventosAcionaRequisicoesCompras;
import com.ssi.kernel.compras.domain.EventosAnaliseGerenteCompra;
import com.ssi.kernel.compras.domain.EventosAnaliseMinuta;
import com.ssi.kernel.compras.domain.EventosEnvioAoMercado;
import com.ssi.kernel.compras.domain.EventosGenericos;
import com.ssi.kernel.compras.domain.EventosIndicaNovoCompradores;
import com.ssi.kernel.compras.domain.EventosLeiloes;
import com.ssi.kernel.compras.domain.EventosSolicitacaoRevisaoAquisicao;
import com.ssi.kernel.compras.domain.EventosValidacoesTecnicas;

@Local
public interface EventosInt {
	public List<Eventos> recuperarEventos();
	public void registrar(Eventos obj);
	public List<Eventos> retornarPaginado(int firstRow, int numberOfRows, Demandas demanda);
	public Eventos getRowData(Object rowKey);
	public Integer getRowCount(Demandas demanda);
	public EventosAberturasRequisicoesCompras recuperarEventosAberturasRequisicoesCompras(Eventos evento);
	public EventosAnaliseGerenteCompra recuperarEventosAnaliseGerenteCompra(Eventos evento);
	public EventosAnaliseMinuta recuperarEventosAnaliseMinuta(Eventos evento);
	public EventosEnvioAoMercado recuperarEventosEnvioAoMercado(Eventos evento);
	public EventosAcionaRequisicoesCompras recuperarEventosAcionaRequisicoesCompras(Eventos evento);
	public EventosIndicaNovoCompradores recuperarEventosIndicaNovoCompradores(Eventos evento);
	public EventosLeiloes recuperarEventosLeiloes(Eventos evento);
	public EventosValidacoesTecnicas recuperarEventosValidacoesTecnicas(Eventos evento);
	public EventosGenericos recuperarEventosGenericos(Eventos evento);
	public Eventos recuperarUltimoEventoDemanda(Long iddemanda);
	public EventosAnaliseGerenteCompra recuperaUltimoEventosAnaliseGerenteComprasComCompradorIndicado(Demandas demanda);
	public EventosSolicitacaoRevisaoAquisicao recuperaUltimaSolicitacaoRevisaoAquisicao(Demandas demanda);
	
}
