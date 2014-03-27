package com.ssi.compras.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.compras.common.interfaces.AnexosInt;
import com.ssi.kernel.compras.domain.Anexos;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.TiposAnexos;

@Stateless(mappedName = "SLSBAnexos")
public class SLSBAnexos implements AnexosInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	@Override
	public void incluir(Anexos anexo) {
		em.merge(anexo);
	}

	@Override
	public Anexos getRowData(Object rowKey) {
		return em.find(Anexos.class, rowKey);
	}

	@Override
	public Integer getRowCount(Map<String, Object> filtros) {
		
		Demandas demanda = (Demandas)filtros.get("demanda");
		
		if(demanda!= null && demanda.getIddemanda()!=null && demanda.getIddemanda()>=1){
			String jpaquery = "SELECT count(anexo.idanexo) FROM Anexos anexo where 1=1 ";
			
			
			
			//if(demanda!= null && demanda.getIddemanda()!=null && demanda.getIddemanda()>=1){
		    	jpaquery+=" and anexo.demanda = :demanda ";
		    //}
			
		    TypedQuery<Long> q = em.createQuery(jpaquery,Long.class);
		    
		    //if(demanda!= null && demanda.getIddemanda()!=null && demanda.getIddemanda()>=1){
		    	q.setParameter("demanda", demanda);
		    //}
		    		
		    Long lid = q.getResultList().get(0);
		    jpaquery = null;
		    
		    try{
		    	return lid.intValue();
		    }
		    catch(IndexOutOfBoundsException e){
		    	return 0;
		    }
		}
		
		return 0;
	}

	@Override
	public List<Anexos> retornarPaginado(int firstRow, int numberOfRows,
			Map<String, Object> filtros) {
		
		Demandas demanda = (Demandas)filtros.get("demanda");
		
		if(demanda!= null && demanda.getIddemanda()!=null && demanda.getIddemanda()>=1){
			String jpaquery = "SELECT anexo FROM Anexos anexo where 1=1 ";
		    
		    
		    
		    //if(demanda!= null && demanda.getIddemanda()!=null && demanda.getIddemanda()>=1){
		    	jpaquery+=" and anexo.demanda = :demanda ";
		    //}
		    
		    jpaquery+="ORDER BY anexo.idanexo DESC";
		    
		    TypedQuery<Anexos> q = em.createQuery(jpaquery,Anexos.class);
		    
		    //if(demanda!= null && demanda.getIddemanda()!=null && demanda.getIddemanda()>=1){
		    	q.setParameter("demanda", demanda);
		    //}
		        
		    if (firstRow >= 0 && numberOfRows > 0){
		    	q.setFirstResult(firstRow);
		    	q.setMaxResults(numberOfRows);
		    }
		    try{
		    	return q.getResultList();
			}
		    catch(NoResultException nex){
		    	return new ArrayList<Anexos>();
		    }
		}
		
		return new ArrayList<Anexos>();
		
	}

	@Override
	public Anexos retornarUltimaMinutaDemanda(Demandas demanda) {
		
		String jpaquery = "SELECT anexo FROM Anexos anexo WHERE anexo.demanda = :demanda and anexo.tipoanexo.idtipoanexo = 1 ORDER BY anexo.idanexo DESC";
	    TypedQuery<Anexos> q = em.createQuery(jpaquery,Anexos.class);
	    q.setMaxResults(1);
	    q.setParameter("demanda", demanda);
		
	    try{
	    	return q.getResultList().get(0);
		}
	    catch(IndexOutOfBoundsException e){
	    	return new Anexos();
	    }
	}

	@Override
	public void remover(Anexos anexo) {
		em.remove(em.merge(anexo));
	}

	@Override
	public TiposAnexos recuperarTipoAnexo(Long idtipoanexo) {
		String jpaquery = "SELECT tipoanexo FROM TiposAnexos tipoanexo WHERE tipoanexo.idtipoanexo = :idtipoanexo";
	    TypedQuery<TiposAnexos> q = em.createQuery(jpaquery,TiposAnexos.class);
	    q.setParameter("idtipoanexo", idtipoanexo);
	    try{
	    	return q.getResultList().get(0);
		}
	    catch(IndexOutOfBoundsException e){
	    	return new TiposAnexos();
	    }
	}
}
