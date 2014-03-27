package com.ssi.compras.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.CronogramaInt;
import com.ssi.kernel.compras.domain.Cronogramas;
import com.ssi.kernel.compras.domain.Demandas;

@Stateless(mappedName = "SLSBCronogramas")
public class SLSBCronogramas implements CronogramaInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBCronogramas(){}
	
	@Override
	public Cronogramas recuperarPorAtividade(Long idatividade, Demandas obj) {
		
		final String jpaquery = "SELECT crono FROM Cronogramas crono where crono.demandascronograma = :demanda and crono.atividade.idatividade = :idatividade";
		TypedQuery<Cronogramas> q = em.createQuery(jpaquery, Cronogramas.class);
		q.setParameter("demanda", obj);
		q.setParameter("idatividade", idatividade);
		
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	@Override
	public void atualizar(Cronogramas obj) {
		em.merge(obj);
	}

}
