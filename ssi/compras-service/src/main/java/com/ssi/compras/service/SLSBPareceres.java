package com.ssi.compras.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.PareceresInt;
import com.ssi.kernel.compras.domain.Pareceres;
import com.ssi.kernel.compras.domain.Status;

@Stateless(mappedName = "SLSBPareceres")
public class SLSBPareceres implements PareceresInt{

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	@Override
	public List<Pareceres> recuperarPareceresPorStatus(Status status) {
		String jpaquery = "SELECT DISTINCT parecer FROM Pareceres parecer join fetch parecer.status WHERE :status in elements(parecer.status) ORDER BY parecer.cnmparecer";
		TypedQuery<Pareceres> q = em.createQuery(jpaquery,Pareceres.class);
		q.setParameter("status", status);
		return q.getResultList();
	}

}
