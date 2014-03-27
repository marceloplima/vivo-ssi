package com.ssi.compras.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.LpsInt;
import com.ssi.kernel.compras.domain.Lps;

@Stateless(mappedName = "SLSBLps")
public class SLSBLps implements LpsInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;		
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Lps> recuperar() {
		final String jpaquery = "SELECT lp FROM Lps lp ORDER BY lp.cnmlp";
		TypedQuery<Lps> q = em.createQuery(jpaquery, Lps.class);
		return q.getResultList();	
	}

	@Override
	public Lps recuperarUnico(Lps lp) {
		final String jpaquery = "SELECT lp FROM Lps lp where lp:=lp";
		TypedQuery<Lps> q = em.createQuery(jpaquery, Lps.class);
		q.setParameter("lp", lp);
		return q.getResultList().get(0);
	}
	
}
