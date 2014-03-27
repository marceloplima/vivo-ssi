package com.ssi.kernel.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.UfsInt;
import com.ssi.kernel.model.Ufs;

@Stateless(mappedName = "SLSBUf")
public class SLSBUf implements UfsInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBUf() {}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Ufs> recuperar() {
		final String jpaquery = "SELECT uf FROM Ufs uf ORDER BY uf.cnmuf";
		TypedQuery<Ufs> q = em.createQuery(jpaquery, Ufs.class);
		return q.getResultList();	
	}

	@Override
	public Ufs recuperaUnico(Ufs uf) {
		return em.find(Ufs.class, uf.getIduf());
	}

}
