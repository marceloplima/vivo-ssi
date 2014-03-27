package com.ssi.compras.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.TiposLogInt;
import com.ssi.kernel.compras.domain.TiposLog;

@Stateless(mappedName = "SLSBTiposLog")
public class SLSBTiposLog implements TiposLogInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
	
	@Override
	public TiposLog recuperarUnico(Long idTipoLog) {
		final String jpaquery = "SELECT tl FROM TiposLog as tl where tl.idtipolog=:tipolog";
		TypedQuery<TiposLog> q = em.createQuery(jpaquery, TiposLog.class);
		q.setParameter("tipolog", idTipoLog);
		return q.getResultList().get(0);
	}

}
