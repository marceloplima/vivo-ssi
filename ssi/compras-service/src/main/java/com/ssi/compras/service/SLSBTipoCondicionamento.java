package com.ssi.compras.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.TipoCondicionamentoInt;
import com.ssi.kernel.compras.domain.TiposCondicionamento;


@Stateless(mappedName = "SLSBTipoCondicionamento")
public class SLSBTipoCondicionamento implements TipoCondicionamentoInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
	
	public SLSBTipoCondicionamento() {
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<TiposCondicionamento> recuperar() {
		final String jpaquery = "SELECT tc FROM TiposCondicionamento tc";
		TypedQuery<TiposCondicionamento> q = em.createQuery(jpaquery, TiposCondicionamento.class);
		return q.getResultList();
	}

	@Override
	public TiposCondicionamento recuperarUnico(
			TiposCondicionamento tiposCondicionamento) {
		final String jpaquery = "SELECT tc FROM TiposCondicionamento tc where tc:=tiposcondicionamento";
		TypedQuery<TiposCondicionamento> q = em.createQuery(jpaquery, TiposCondicionamento.class);
		q.setParameter("tiposcondicionamento", tiposCondicionamento);
		return q.getResultList().get(0);			
	}

}
