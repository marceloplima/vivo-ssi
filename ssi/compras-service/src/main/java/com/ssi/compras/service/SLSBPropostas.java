package com.ssi.compras.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.PropostasInt;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Propostas;
import com.ssi.kernel.model.Fornecedores;

@Stateless(mappedName = "SLSBPropostas")
public class SLSBPropostas implements PropostasInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBPropostas(){}
	
	@Override
	public List<Propostas> recuperarPropostas(Demandas demanda) {
		
		if(demanda.getIddemanda()==null){
			return null;
		}
		
		String jpaquery = "SELECT proposta FROM Propostas proposta where proposta.demanda = :demanda";
	    
	    TypedQuery<Propostas> q = em.createQuery(jpaquery,Propostas.class);
	    q.setParameter("demanda", demanda);
	    		
	    jpaquery = null;
	    
	    try{
	    	List<Propostas> p = q.getResultList();
	    	return p;
	    }
	    catch(NoResultException e){
	    	return null;
	    }
	}

	@Override
	public void registrar(Propostas obj) {
		em.merge(obj);
		em.flush();
	}

	@Override
	public List<Propostas> retornarPaginado(int firstRow, int numberOfRows,
			Map<String, Object> filtros) {
		
		Demandas demanda = (Demandas)filtros.get("demanda");
		
		if(demanda!= null && demanda.getIddemanda()!=null && demanda.getIddemanda()>=1){
			String jpaquery = "SELECT proposta FROM Propostas proposta WHERE 1=1";
			jpaquery+=" and proposta.demanda = :demanda ";
			
			jpaquery+=" order by proposta.idproposta DESC ";
			
		    TypedQuery<Propostas> q = em.createQuery(jpaquery,Propostas.class);
		    
		    q.setParameter("demanda", demanda);
		        
		    if (firstRow >= 0 && numberOfRows > 0){
		    	q.setFirstResult(firstRow);
		    	q.setMaxResults(numberOfRows);
		    }
		    try{
		    	return q.getResultList();
			}
		    catch(NoResultException nex){
		    	return new ArrayList<Propostas>();
		    }
		}
		
		return new ArrayList<Propostas>();
	}

	@Override
	public Propostas getRowData(Object rowKey) {
		return em.find(Propostas.class, rowKey);
	}

	@Override
	public Integer getRowCount(Map<String, Object> filtros) {
		
		Demandas demanda = (Demandas)filtros.get("demanda");
		
		String jpaquery = "SELECT count(proposta.idproposta) FROM Propostas proposta where 1=1";
		if(demanda!= null && demanda.getIddemanda()!=null && demanda.getIddemanda()>=1){
	    	jpaquery+=" and proposta.demanda = :demanda ";
	    }
	    TypedQuery<Long> q = em.createQuery(jpaquery,Long.class);
	    if(demanda!= null && demanda.getIddemanda()!=null && demanda.getIddemanda()>=1){
	    	q.setParameter("demanda", demanda);
	    }
	    try{	
		    Long lid = q.getResultList().get(0);
		    jpaquery = null;
	   
	    	return lid.intValue();
	    }
	    catch(IndexOutOfBoundsException e){
	    	return 0;
	    }
	}

	@Override
	public Propostas verificaExistenteEncaminhada(Demandas demanda) {
		
		if(demanda.getIddemanda()==null){
			return null;
		}
		
		String jpaquery = "SELECT proposta FROM Propostas proposta where proposta.demanda = :demanda and proposta.status.idstatus = 8";
	    
	    TypedQuery<Propostas> q = em.createQuery(jpaquery,Propostas.class);
	    q.setParameter("demanda", demanda);
	    jpaquery = null;
	    try{
	    	Propostas p = q.getResultList().get(0);
	    	return p;
	    }
	    catch(IndexOutOfBoundsException e){
	    	return null;
	    }
	}
	
	@Override
	public Propostas verificaExistenteDemanda(Demandas demanda) {
		
		if(demanda.getIddemanda()==null){
			return null;
		}
		
		String jpaquery = "SELECT proposta FROM Propostas proposta where proposta.demanda = :demanda";
	    
	    TypedQuery<Propostas> q = em.createQuery(jpaquery,Propostas.class);
	    q.setParameter("demanda", demanda);
	    		
	    jpaquery = null;
	    
	    try{
	    	Propostas p = q.getResultList().get(0);
	    	return p;
	    }
	    catch(IndexOutOfBoundsException e){
	    	return null;
	    }
	}
	
	@Override
	public List<Propostas> verificarTodasReprovadas(Demandas demanda) {
		
		if(demanda.getIddemanda()==null){
			return null;
		}
		
		String jpaquery = "SELECT proposta FROM Propostas proposta where proposta.status.idstatus = 34 and proposta.demanda = :demanda";
	    
	    TypedQuery<Propostas> q = em.createQuery(jpaquery,Propostas.class);
	    q.setParameter("demanda", demanda);
	    		
	    jpaquery = null;
	    
	    try{
	    	List<Propostas> p = q.getResultList();
	    	return p;
	    }
	    catch(NoResultException nex){
	    	return new ArrayList<Propostas>();
	    }
	}
	
	@Override
	public List<Propostas> verificarTodasPropostas(Demandas demanda) {
		
		if(demanda.getIddemanda()==null){
			return null;
		}
		
		String jpaquery = "SELECT proposta FROM Propostas proposta where proposta.demanda = :demanda";
	    
	    TypedQuery<Propostas> q = em.createQuery(jpaquery,Propostas.class);
	    q.setParameter("demanda", demanda);
	    		
	    jpaquery = null;
	    
	    try{
	    	List<Propostas> p = q.getResultList();
	    	return p;
	    }
	    catch(NoResultException nex){
	    	return new ArrayList<Propostas>();
	    }
	}

	@Override
	public List<Propostas> recuperarPropostasAprovadas(Demandas demanda) {
		
		if(demanda.getIddemanda()==null){
			return new ArrayList<Propostas>();
		}
		
		String jpaquery = "SELECT proposta FROM Propostas proposta where proposta.demanda = :demanda and proposta.status.idstatus=33";
	    
	    TypedQuery<Propostas> q = em.createQuery(jpaquery,Propostas.class);
	    q.setParameter("demanda", demanda);
	    		
	    jpaquery = null;
	    
	    try{
	    	List<Propostas> p = q.getResultList();
	    	return p;
	    }
	    catch(NoResultException e){
	    	return null;
	    }
	}

	@Override
	public List<Fornecedores> recuperarFornecedoresPropostas(Propostas prop) {
		String jpaquery = "SELECT DISTINCT fornecedor FROM Fornecedores fornecedor join fetch fornecedor.propostas WHERE :prop in elements(fornecedor.propostas)";
		TypedQuery<Fornecedores> q = em.createQuery(jpaquery,Fornecedores.class);
		q.setParameter("prop", prop);
		return q.getResultList();
	}

}
