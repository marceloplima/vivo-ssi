package com.ssi.compras.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.TiposAnexosInt;
import com.ssi.kernel.compras.domain.TiposAnexos;

@Stateless(mappedName = "SLSBTiposAnexos")
public class SLSBTiposAnexos implements TiposAnexosInt{

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	@Override
	public List<TiposAnexos> recuperarTiposAnexos() {
		final String jpaquery = "SELECT tc FROM TiposAnexos ta where ta.flagexibemenuanexo = 1";
		TypedQuery<TiposAnexos> q = em.createQuery(jpaquery, TiposAnexos.class);
		return q.getResultList();
	}
}
