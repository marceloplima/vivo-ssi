package com.ssi.redes.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.redes.domain.MotivacoesRedes;
import com.ssi.kernel.redes.interfaces.MotivacoesRedesInt;

@Stateless(mappedName = "SLSBMotivacoes")
public class SLSBMotivacoes implements MotivacoesRedesInt {
	
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
	
	public SLSBMotivacoes() {}

	@Override
	public List<MotivacoesRedes> recuperar() {
		final String jpaquery = "SELECT mt FROM MotivacoesRedes mt";
		TypedQuery<MotivacoesRedes> q = em.createQuery(jpaquery, MotivacoesRedes.class);
		return q.getResultList();				
	}
}
