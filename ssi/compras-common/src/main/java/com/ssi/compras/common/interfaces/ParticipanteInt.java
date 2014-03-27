package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.compras.domain.Lps;
import com.ssi.kernel.compras.domain.Participantes;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Pessoas;

@Local
public interface ParticipanteInt {
			
	List<Pessoas> recuperarResponsaveisTecnicos();
	void definir(Participantes obj);
	Participantes recuperarPorPapel(Integer idpapel);	
	List<Pessoas> recuperarGerentesPorLp(List<Lps> lps);
	List<Pessoas> recuperarGerentesContratos();
	List<Pessoas> recupearAnalistasContratos();
	List<Pessoas> recuperarGerentesAquisicoes();
	List<Pessoas> recuperarAnalistasAquisicaoes();
	List<Pessoas> recuperarAnalistasOrcamentos();
	List<Pessoas> recuperarGerentesOrcamentos();
	List<Pessoas> recuperarDiretores();
	List<GruposModulos> recuperarGruposGerenteAquisicao(boolean ativo);
	List<GruposModulos> recuperarGruposGerentesPorLp(boolean ativo,List<Lps> lps);
	List<Pessoas> recuperarCompradoresPorLp(List<Lps> lps);
	List<String> recuperarAutocompleteRespTecnico(String autobusca);
	
}
