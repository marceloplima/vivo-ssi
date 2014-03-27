package com.ssi.compras.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.RespostaQuestionamentoInt;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.RespostasQuestionamentos;

@Stateless(mappedName = "SLSBRespostasQuestionamento")
public class SLSBRespostasQuestionamento implements RespostaQuestionamentoInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
		
	public SLSBRespostasQuestionamento() {}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<RespostasQuestionamentos> recuperarRespostasQuestionamento(Demandas demanda) {

		if(demanda.getIddemanda()==null){
			return new ArrayList<RespostasQuestionamentos>();
		}
	
		final String jpaquery = "SELECT resp FROM RespostasQuestionamentos resp WHERE resp.demanda = :demanda ORDER BY resp.datacadastro DESC";
		TypedQuery<RespostasQuestionamentos> q = em.createQuery(jpaquery, RespostasQuestionamentos.class);
		q.setParameter("demanda", demanda);
		
		return q.getResultList();				
		
	}

	@Override
	public RespostasQuestionamentos incluir(RespostasQuestionamentos respostasQuestionamentos) {
		
		em.persist(respostasQuestionamentos); 
		
		return respostasQuestionamentos;
	}
	

}
