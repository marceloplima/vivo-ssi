package com.ssi.kernel.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.CentraisInt;
import com.ssi.kernel.model.Centrais;
import com.ssi.kernel.model.Ufs;

@Stateless(mappedName = "SLSBCentrais")
public class SLSBCentrais implements CentraisInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBCentrais() {}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Centrais> recuperar() {
		final String jpaquery = "SELECT c FROM Centrais c ORDER BY c.cnmcentral";
		TypedQuery<Centrais> q = em.createQuery(jpaquery, Centrais.class);
		return q.getResultList();	
	}

	@Override
	public Centrais recuperaUnico(Centrais central) {
		return em.find(Centrais.class, central.getIdcentral());
	}

	@Override
	public List<Centrais> recuperarPorUf(Ufs uf) {
		final String jpaquery = "SELECT c FROM Centrais c WHERE c.uf = :uf ORDER BY c.cnmcentral";
		TypedQuery<Centrais> q = em.createQuery(jpaquery, Centrais.class);
		q.setParameter("uf", uf);
		return q.getResultList();
	}

}
