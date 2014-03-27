package com.ssi.kernel.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.RegionaisInt;
import com.ssi.kernel.model.Regionais;

@Stateless(mappedName = "SLSBRegionais")
public class SLSBRegionais implements RegionaisInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;		
	
	public SLSBRegionais() {}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Regionais> recuperar() {
		final String jpaquery = "SELECT r FROM Regionais r";
		TypedQuery<Regionais> q = em.createQuery(jpaquery, Regionais.class);
		return q.getResultList();	
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Regionais recuperarUnico(Regionais regional) {
		final String jpaquery = "SELECT r FROM Regionais r where r:=regional";
		TypedQuery<Regionais> q = em.createQuery(jpaquery, Regionais.class);
		q.setParameter("regional", regional);
		return q.getResultList().get(0);			
	}
}
