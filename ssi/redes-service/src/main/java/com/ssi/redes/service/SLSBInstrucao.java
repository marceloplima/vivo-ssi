package com.ssi.redes.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ssi.kernel.redes.domain.Instrucao;
import com.ssi.kernel.redes.interfaces.InstrucaoRedesInt;

@Stateless(mappedName = "SLSBInstrucao")
public class SLSBInstrucao implements InstrucaoRedesInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBInstrucao() {}

	@Override
	public Instrucao recuperaUnico(Long idInstrucao) {
		return em.find(Instrucao.class, idInstrucao);
	}

}
