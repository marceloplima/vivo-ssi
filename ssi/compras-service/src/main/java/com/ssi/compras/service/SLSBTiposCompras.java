package com.ssi.compras.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.TiposComprasInt;
import com.ssi.kernel.compras.domain.TiposCompras;

@Stateless(mappedName = "SLSBTiposCompras")
public class SLSBTiposCompras implements TiposComprasInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;		
	
	public SLSBTiposCompras() {}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<TiposCompras> recuperar() {
		final String jpaquery = "SELECT tc FROM TiposCompras tc";
		TypedQuery<TiposCompras> q = em.createQuery(jpaquery, TiposCompras.class);
		return q.getResultList();	
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TiposCompras recuperarUnico(TiposCompras obj) {
		final String jpaquery = "SELECT tc FROM TiposCompras tc where tc:=obj";
		TypedQuery<TiposCompras> q = em.createQuery(jpaquery, TiposCompras.class);
		q.setParameter("tc", obj);
		return q.getResultList().get(0);				
	}
}
