package com.ssi.compras.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.FornecedoresAgendamentosInt;
import com.ssi.kernel.compras.domain.Agendamentos;
import com.ssi.kernel.compras.domain.FornecedoresAgendamentos;

@Stateless(mappedName = "SLSBFornecedoresAgendamentos")
public class SLSBFornecedoresAgendamentos implements FornecedoresAgendamentosInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
		
	public SLSBFornecedoresAgendamentos() {}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<FornecedoresAgendamentos> recuperarFornecedoresAgendamentos(Agendamentos agendamento) {
		
		if(agendamento.getIdagendamento()==null){
			return new ArrayList<FornecedoresAgendamentos>();
		}
		
		final String jpaquery = "SELECT fornage FROM FornecedoresAgendamentos fornage INNER JOIN FETCH fornage.fornecedores WHERE fornage.agendamentos = :agendamento";
		TypedQuery<FornecedoresAgendamentos> q = em.createQuery(jpaquery, FornecedoresAgendamentos.class);
		q.setParameter("agendamento", agendamento);
		
		return q.getResultList();
	}

	@Override
	public void remover(FornecedoresAgendamentos fornecedorAgendamento) {		
		final String jpaquery = "DELETE FROM FornecedoresAgendamentos fornage WHERE fornage.idfornecedoragendamento = " + fornecedorAgendamento.getIdfornecedoragendamento();
		em.createQuery(jpaquery).executeUpdate();
	}

	@Override
	public FornecedoresAgendamentos alterar(FornecedoresAgendamentos fornecedorAgendamento) {
		
		em.merge(fornecedorAgendamento);
		em.flush();
		return fornecedorAgendamento;
	}

	@Override
	public FornecedoresAgendamentos incluir(FornecedoresAgendamentos fornecedorAgendamento) {
		em.persist(fornecedorAgendamento);
		em.flush();
		return fornecedorAgendamento;		
	}

}
