package com.ssi.compras.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.StatusInt;
import com.ssi.kernel.compras.domain.Status;
import com.ssi.kernel.compras.domain.TiposDocumentos;

@Stateless(mappedName = "SLSBStatus")
public class SLSBStatus implements StatusInt {
	
	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
	
	public SLSBStatus() {}

	@Override
	public List<Status> recuperar(TiposDocumentos tipoDocumento) {
		final String jpaquery = "SELECT st FROM Status st where st.tipoDocumento = :tipoDocumento";
		TypedQuery<Status> q = em.createQuery(jpaquery, Status.class);
		q.setParameter("tipoDocumento", tipoDocumento);
		return q.getResultList();				
	}

	@Override
	public Status recuperarUnico(Status status) {
		final String jpaquery = "SELECT st FROM Status as st where st.idstatus=:status";
		TypedQuery<Status> q = em.createQuery(jpaquery, Status.class);
		q.setParameter("status", status.getIdstatus());
		return q.getResultList().get(0);
	}
	

}
