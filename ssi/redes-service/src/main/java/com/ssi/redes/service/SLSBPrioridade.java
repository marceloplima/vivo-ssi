package com.ssi.redes.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.redes.domain.PrioridadesRedes;
import com.ssi.kernel.redes.interfaces.PrioridadeRedesInt;

@Stateless(mappedName = "SLSBPrioridade")
public class SLSBPrioridade implements PrioridadeRedesInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;		
	
	public SLSBPrioridade() {}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<PrioridadesRedes> recuperar() {
		final String jpaquery = "SELECT p FROM PrioridadesRedes p";
		TypedQuery<PrioridadesRedes> q = em.createQuery(jpaquery, PrioridadesRedes.class);
		return q.getResultList();	
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PrioridadesRedes recuperarUnico(PrioridadesRedes prioridade) {
		final String jpaquery = "SELECT p FROM PrioridadesRedes p where p:=prioridade";
		TypedQuery<PrioridadesRedes> q = em.createQuery(jpaquery, PrioridadesRedes.class);
		q.setParameter("prioridade", prioridade);
		return q.getResultList().get(0);			
	}
}
