package com.ssi.kernel.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.DominiosInt;
import com.ssi.kernel.model.Dominios;

@Stateless(mappedName="SLSBDominios")
public class SLSBDominios implements DominiosInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBDominios(){}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Dominios> recuperar() {
		final String jpaquery = "SELECT dominio FROM Dominios dominio";
		TypedQuery<Dominios> q = em.createQuery(jpaquery, Dominios.class);
		return q.getResultList();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Dominios recuperarUnico(Long iddominio) {
		final String jpaquery = "SELECT dominio FROM Dominios dominio WHERE iddominio = "+iddominio;
		
		return em.createQuery(jpaquery, Dominios.class).getResultList().get(0);
	}

	@Override
	public boolean remover() {
		// TODO Auto-generated method stub
		return false;
	}	
	
}
