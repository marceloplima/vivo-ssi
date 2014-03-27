package com.ssi.compras.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.AgendamentosInt;
import com.ssi.kernel.compras.domain.Agendamentos;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Status;

@Stateless(mappedName = "SLSBAgendamentos")
public class SLSBAgendamentos implements AgendamentosInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
		
	public SLSBAgendamentos() {}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Agendamentos> recuperarAgendamentos(Demandas demanda) {
		
		if(demanda.getIddemanda()==null){
			return new ArrayList<Agendamentos>();
		}
		
		final String jpaquery = "SELECT agendamento FROM Agendamentos agendamento WHERE agendamento.demanda = :demanda ORDER BY agendamento.datacadastro DESC";
		TypedQuery<Agendamentos> q = em.createQuery(jpaquery, Agendamentos.class);
		q.setParameter("demanda", demanda);
		
		return q.getResultList();			
		
	}

	@Override
	public List<Agendamentos> recuperarAgendamentosPorStatus(Demandas demanda,Status status) {

		final String jpaquery = "SELECT agendamento FROM Agendamentos agendamento WHERE agendamento.demanda = :demanda AND agendamento.status = :status";
		TypedQuery<Agendamentos> q = em.createQuery(jpaquery, Agendamentos.class);
		q.setParameter("demanda", demanda);
		q.setParameter("status", status);
		
		return q.getResultList();		
		
	}
	
	

}
