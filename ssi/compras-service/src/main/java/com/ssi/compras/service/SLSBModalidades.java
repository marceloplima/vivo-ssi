package com.ssi.compras.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.ModalidadesInt;
import com.ssi.kernel.compras.domain.Modalidades;

@Stateless(mappedName = "SLSBStatus")
public class SLSBModalidades implements ModalidadesInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBModalidades(){}
	
	@Override
	public List<Modalidades> recuperar() {
		final String jpaquery = "SELECT modalidade FROM Modalidades modalidade";
		TypedQuery<Modalidades> q = em.createQuery(jpaquery, Modalidades.class);
		return q.getResultList();
	}

}
