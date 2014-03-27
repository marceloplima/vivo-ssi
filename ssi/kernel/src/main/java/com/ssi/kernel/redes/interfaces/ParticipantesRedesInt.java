package com.ssi.kernel.redes.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ssi.kernel.redes.domain.ParticipantesRedes;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Pessoas;

@Local
public interface ParticipantesRedesInt {
	void definir(ParticipantesRedes obj);
	ParticipantesRedes recuperarPorPapel(Integer idpapel);
	List<Pessoas> recuperarEmissores();
	List<Pessoas> recuperarRequisitados();
	List<Pessoas> recuperarCopiados();
	List<GruposModulos> recuperarGrupoEmissores(boolean ativo);
	List<GruposModulos> recuperarGrupoRequisitados(boolean ativo);
	List<GruposModulos> recuperarGrupoCopiados(boolean ativo);
	List<String> recuperarAutocompleteRespTecnico(String autobusca);
	List<Pessoas> recuperarResponsaveisTecnicos();
	List<Pessoas> recuperarPessoasGruposCopiados(boolean ativo);
	List<Pessoas> recuperarFaturamento();	
}
