package com.ssi.compras.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.TipoAditivoInt;
import com.ssi.kernel.compras.domain.TiposAditivo;

@Stateless(mappedName = "SLSBTipoAditivo")
public class SLSBTipoAditivo implements TipoAditivoInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
	
	public SLSBTipoAditivo() {
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<TiposAditivo> recuperar() {
		final String jpaquery = "SELECT ta FROM TiposAditivo ta";
		TypedQuery<TiposAditivo> q = em.createQuery(jpaquery, TiposAditivo.class);
		return q.getResultList();	
	}

	@Override
	public TiposAditivo recuperarUnico(TiposAditivo tipoAditivo) {
		final String jpaquery = "SELECT ta FROM TiposAditivo ta where ta:=tipoaditivo";
		TypedQuery<TiposAditivo> q = em.createQuery(jpaquery, TiposAditivo.class);
		q.setParameter("tipoaditivo", tipoAditivo);
		return q.getResultList().get(0);
	}

}
