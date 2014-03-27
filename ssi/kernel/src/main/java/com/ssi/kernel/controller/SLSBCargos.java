package com.ssi.kernel.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.CargosInt;
import com.ssi.kernel.model.Cargos;

@Stateless(mappedName="SLSBCargos")
public class SLSBCargos implements CargosInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBCargos(){}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Cargos> recuperar() {
		final String jpaquery = "SELECT obj FROM Cargos obj";
		TypedQuery<Cargos> q = em.createQuery(jpaquery, Cargos.class);
		return q.getResultList();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Cargos recuperarUnico(Cargos obj) {
		return em.find(Cargos.class, obj);
	}
	
}
