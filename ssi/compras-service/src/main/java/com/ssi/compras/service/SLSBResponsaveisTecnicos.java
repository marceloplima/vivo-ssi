package com.ssi.compras.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.ResponsaveisTecnicosInt;
import com.ssi.kernel.compras.domain.ResponsaveisTecnicos;


@Stateless(mappedName="SLSBResponsaveisTecnicos")
public class SLSBResponsaveisTecnicos implements ResponsaveisTecnicosInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBResponsaveisTecnicos(){}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ResponsaveisTecnicos> recuperar() {
		final String jpaquery = "SELECT rt FROM ResponsaveisTecnicos rt";
		TypedQuery<ResponsaveisTecnicos> q = em.createQuery(jpaquery, ResponsaveisTecnicos.class);
		return q.getResultList();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ResponsaveisTecnicos recuperarUnico() {
		final String jpaquery = "SELECT rt FROM ResponsaveisTecnicos rt where rt.participante.papel.idpapel=2";
		TypedQuery<ResponsaveisTecnicos> q = em.createQuery(jpaquery, ResponsaveisTecnicos.class);
		return q.getResultList().get(0);
	}
	
}
