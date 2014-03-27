package com.ssi.redes.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.redes.domain.TiposAnexosRedes;
import com.ssi.kernel.redes.interfaces.TiposAnexosRedesInt;

@Stateless(mappedName = "SLSBTiposAnexos")
public class SLSBTiposAnexos implements TiposAnexosRedesInt{

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	@Override
	public List<TiposAnexosRedes> recuperarTiposAnexos() {
		final String jpaquery = "SELECT tc FROM TiposAnexosRedes ta where ta.flagexibemenuanexo = 1";
		TypedQuery<TiposAnexosRedes> q = em.createQuery(jpaquery, TiposAnexosRedes.class);
		return q.getResultList();
	}
}
