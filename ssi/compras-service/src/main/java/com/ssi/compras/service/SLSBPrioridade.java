package com.ssi.compras.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.PrioridadeInt;
import com.ssi.kernel.compras.domain.Prioridades;

@Stateless(mappedName = "SLSBPrioridade")
public class SLSBPrioridade implements PrioridadeInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;		
	
	public SLSBPrioridade() {}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Prioridades> recuperar() {
		final String jpaquery = "SELECT p FROM Prioridades p";
		TypedQuery<Prioridades> q = em.createQuery(jpaquery, Prioridades.class);
		return q.getResultList();	
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Prioridades recuperarUnico(Prioridades prioridade) {
		final String jpaquery = "SELECT p FROM Prioridades p where p:=prioridade";
		TypedQuery<Prioridades> q = em.createQuery(jpaquery, Prioridades.class);
		q.setParameter("prioridade", prioridade);
		return q.getResultList().get(0);			
	}
}
