package com.ssi.regulatorio.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.redes.domain.TiposLogRedes;
import com.ssi.kernel.redes.interfaces.TiposLogRedesInt;

@Stateless(mappedName = "SLSBTiposLog")
public class SLSBTiposLog implements TiposLogRedesInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
	
	@Override
	public TiposLogRedes recuperarUnico(Long idTipoLog) {
		final String jpaquery = "SELECT tl FROM TiposLogRedes as tl where tl.idtipolog=:tipolog";
		TypedQuery<TiposLogRedes> q = em.createQuery(jpaquery, TiposLogRedes.class);
		q.setParameter("tipolog", idTipoLog);
		return q.getResultList().get(0);
	}

}
