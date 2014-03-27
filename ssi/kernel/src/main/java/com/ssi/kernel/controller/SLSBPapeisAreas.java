package com.ssi.kernel.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.PapeisAreasInt;
import com.ssi.kernel.model.PapeisAreas;

@Stateless(mappedName = "SLSBPapeisAreas")
public class SLSBPapeisAreas implements PapeisAreasInt {
	
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBPapeisAreas(){}

	@Override
	public List<PapeisAreas> recuperar() {
		final String jpaquery = "SELECT papelarea FROM PapeisAreas papelarea order by papelarea.cnmpapelarea";
		TypedQuery<PapeisAreas> q = em.createQuery(jpaquery, PapeisAreas.class);
		return q.getResultList();
	}

	@Override
	public PapeisAreas recuperarUnico(PapeisAreas papelarea) {
		final String jpaquery = "SELECT papelarea FROM PapeisAreas papelarea WHERE papelarea = :papelarea";
		TypedQuery<PapeisAreas> q = em.createQuery(jpaquery, PapeisAreas.class);
		q.setParameter("papelarea", papelarea);
		return q.getResultList().get(0);
	}

}
