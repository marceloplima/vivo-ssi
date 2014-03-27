package com.ssi.regulatorio.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.LogsRedes;
import com.ssi.kernel.redes.interfaces.LogsRedesInt;

@Stateless(mappedName = "SLSBLogs")
public class SLSBLogs implements LogsRedesInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
		
	public SLSBLogs() {}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<LogsRedes> recuperarLogsDemanda(DemandasRedes demanda) {
		
		if(demanda.getIddemanda()==null){
			return new ArrayList<LogsRedes>();
		}
		
		final String jpaquery = "SELECT lg FROM LogsRedes lg where lg.demanda = :demanda ORDER BY lg.datacadastro DESC";
		TypedQuery<LogsRedes> q = em.createQuery(jpaquery, LogsRedes.class);
		q.setParameter("demanda", demanda);
		return q.getResultList();			
		
	}

}
