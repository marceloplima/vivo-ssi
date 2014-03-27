package com.ssi.compras.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.LeiloesInt;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Leiloes;
import com.ssi.kernel.compras.domain.Propostas;

@Stateless(mappedName = "SLSBLeiloes")
public class SLSBLeiloes implements LeiloesInt{

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBLeiloes(){}
	
	@Override
	public List<Leiloes> recuperar(Demandas demanda) {
		
		if(demanda==null || demanda.getIddemanda() == null || demanda.getIddemanda()<=0){
			return new ArrayList<Leiloes>();
		}
		
		// TODO Auto-generated method stub
		final String jpaquery = "SELECT leilao FROM Leiloes leilao where leilao.demandaleilao = :demanda ORDER BY leilao.idleilao DESC";
		TypedQuery<Leiloes> q = em.createQuery(jpaquery, Leiloes.class);
		q.setParameter("demanda", demanda);
		try{
			return q.getResultList();
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}
	
	@Override
	public void registrar(Leiloes leilao){
		em.merge(leilao);
		em.flush();
	}
	
	@Override
	public List<Propostas> recuperarPropostasPart(Leiloes leilao) {
		String jpaquery = "SELECT DISTINCT proposta FROM Propostas proposta join fetch proposta.leiloesproppart WHERE :leilao in elements(proposta.leiloesproppart)";
		TypedQuery<Propostas> q = em.createQuery(jpaquery,Propostas.class);
		q.setParameter("leilao", leilao);
		return q.getResultList();
	}

	@Override
	public List<Propostas> recuperarPropostasVenc(Leiloes leilao) {
		String jpaquery = "SELECT DISTINCT proposta FROM Propostas proposta join fetch proposta.leiloespropvenc WHERE :leilao in elements(proposta.leiloespropvenc)";
		TypedQuery<Propostas> q = em.createQuery(jpaquery,Propostas.class);
		q.setParameter("leilao", leilao);
		return q.getResultList();
	}
}
