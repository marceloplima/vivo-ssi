package com.ssi.compras.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.LogsInt;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Logs;

@Stateless(mappedName = "SLSBLogs")
public class SLSBLogs implements LogsInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
		
	public SLSBLogs() {}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Logs> recuperarLogsDemanda(Demandas demanda) {
		
		if(demanda.getIddemanda()==null){
			return new ArrayList<Logs>();
		}
		
		final String jpaquery = "SELECT lg FROM Logs lg where lg.demanda = :demanda ORDER BY lg.datacadastro DESC";
		TypedQuery<Logs> q = em.createQuery(jpaquery, Logs.class);
		q.setParameter("demanda", demanda);
		return q.getResultList();			
		
	}

}
