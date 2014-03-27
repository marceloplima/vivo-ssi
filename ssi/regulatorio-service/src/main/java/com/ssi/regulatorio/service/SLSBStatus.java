package com.ssi.regulatorio.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.redes.domain.StatusRedes;
import com.ssi.kernel.redes.domain.TiposDocumentosRedes;
import com.ssi.kernel.redes.interfaces.StatusRedesInt;

@Stateless(mappedName = "SLSBStatus")
public class SLSBStatus implements StatusRedesInt {
	
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
	
	public SLSBStatus() {}

	@Override
	public List<StatusRedes> recuperar(TiposDocumentosRedes tipoDocumento) {
		final String jpaquery = "SELECT st FROM StatusRedes st where st.tipoDocumento = :tipoDocumento";
		TypedQuery<StatusRedes> q = em.createQuery(jpaquery, StatusRedes.class);
		q.setParameter("tipoDocumento", tipoDocumento);
		return q.getResultList();				
	}

	@Override
	public StatusRedes recuperarUnico(StatusRedes status) {
		final String jpaquery = "SELECT st FROM StatusRedes as st where st.idstatus=:status";
		TypedQuery<StatusRedes> q = em.createQuery(jpaquery, StatusRedes.class);
		q.setParameter("status", status.getIdstatus());
		return q.getResultList().get(0);
	}
	

}
