package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Leiloes;
import com.ssi.kernel.compras.domain.Propostas;

@Local
public interface LeiloesInt {
	public List<Leiloes> recuperar(Demandas demanda);
	public void registrar(Leiloes leilao);
	public List<Propostas> recuperarPropostasPart(Leiloes leilao);
	public List<Propostas> recuperarPropostasVenc(Leiloes leilao);
}
