package com.ssi.compras.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.CapexOpexInt;
import com.ssi.kernel.compras.domain.CapexOpex;


@Stateless(mappedName = "SLSBCapexOpex")
public class SLSBCapexOpex implements CapexOpexInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
	
	public SLSBCapexOpex() {
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<CapexOpex> recuperar() {
	
		final String jpaquery = "SELECT c FROM CapexOpex c";
		TypedQuery<CapexOpex> q = em.createQuery(jpaquery, CapexOpex.class);
		return q.getResultList();					
		
	}

	@Override
	public CapexOpex recuperarPorId(Long id) {
		
		return em.find(CapexOpex.class, id);
		
	}

}
