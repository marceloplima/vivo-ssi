package com.ssi.compras.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.TiposLeiloesInt;
import com.ssi.kernel.compras.domain.TiposLeiloes;

@Stateless(mappedName = "SLSBTiposLeiloes")
public class SLSBTiposLeiloes implements TiposLeiloesInt{
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBTiposLeiloes(){}

	@Override
	public List<TiposLeiloes> recuperar() {
		final String jpaquery = "SELECT tipoleilao FROM TiposLeiloes tipoleilao";
		TypedQuery<TiposLeiloes> q = em.createQuery(jpaquery, TiposLeiloes.class);
		return q.getResultList();
	}
	
}
