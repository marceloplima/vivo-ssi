package com.ssi.redes.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.redes.domain.PareceresRedes;
import com.ssi.kernel.redes.domain.StatusRedes;
import com.ssi.kernel.redes.interfaces.PareceresRedesInt;

@Stateless(mappedName = "SLSBPareceres")
public class SLSBPareceres implements PareceresRedesInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	@Override
	public List<PareceresRedes> recuperar() {
		
		final String jpaquery = "SELECT pa FROM PareceresRedes as pa ORDER BY pa.cnmparecer DESC";
		TypedQuery<PareceresRedes> q = em.createQuery(jpaquery, PareceresRedes.class);
				
	    return q.getResultList();
				
	}

	@Override
	public PareceresRedes recuperarUnico(Long id) {

		return em.find(PareceresRedes.class, id);
		
	}

	@Override
	public List<PareceresRedes> recuperarPorStatus(StatusRedes status) {

		final String jpaquery = "SELECT pa FROM PareceresRedes as pa JOIN FETCH pa.status status WHERE status.idstatus = :idstatus ORDER BY pa.cnmparecer DESC";
		TypedQuery<PareceresRedes> q = em.createQuery(jpaquery, PareceresRedes.class);
		
		q.setParameter("idstatus", status.getIdstatus());
				
	    return q.getResultList();		
		
	}

}
