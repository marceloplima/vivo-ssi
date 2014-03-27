package com.ssi.regulatorio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.redes.domain.AnexosRedes;
import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.TiposAnexosRedes;
import com.ssi.kernel.redes.interfaces.AnexosRedesInt;

@Stateless(mappedName = "SLSBAnexos")
public class SLSBAnexos implements AnexosRedesInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	@Override
	public void incluir(AnexosRedes anexo) {
		em.merge(anexo);
	}

	@Override
	public AnexosRedes getRowData(Object rowKey) {
		return em.find(AnexosRedes.class, rowKey);
	}

	@Override
	public Integer getRowCount(Map<String, Object> filtros) {
		String jpaquery = "SELECT count(anexo.idanexo) FROM AnexosRedes anexo where 1=1 ";
		
		DemandasRedes demanda = (DemandasRedes)filtros.get("demanda");
		
		if(demanda!= null && demanda.getIddemanda()!=null && demanda.getIddemanda()>=1){
	    	jpaquery+=" and anexo.demanda = :demanda ";
	    }
		
	    TypedQuery<Long> q = em.createQuery(jpaquery,Long.class);
	    
	    if(demanda!= null && demanda.getIddemanda()!=null && demanda.getIddemanda()>=1){
	    	q.setParameter("demanda", demanda);
	    }
	    		
	    Long lid = q.getResultList().get(0);
	    jpaquery = null;
	    
	    try{
	    	return lid.intValue();
	    }
	    catch(IndexOutOfBoundsException e){
	    	return 0;
	    }
	}

	@Override
	public List<AnexosRedes> retornarPaginado(int firstRow, int numberOfRows,
			Map<String, Object> filtros) {
		
		String jpaquery = "SELECT anexo FROM AnexosRedes anexo where 1=1 ";
	    
	    DemandasRedes demanda = (DemandasRedes)filtros.get("demanda");
	    
	    if(demanda!= null && demanda.getIddemanda()!=null && demanda.getIddemanda()>=1){
	    	jpaquery+=" and anexo.demanda = :demanda ";
	    }
	    
	    jpaquery+="ORDER BY anexo.idanexo DESC";
	    
	    TypedQuery<AnexosRedes> q = em.createQuery(jpaquery,AnexosRedes.class);
	    
	    if(demanda!= null && demanda.getIddemanda()!=null && demanda.getIddemanda()>=1){
	    	q.setParameter("demanda", demanda);
	    }
	        
	    if (firstRow >= 0 && numberOfRows > 0){
	    	q.setFirstResult(firstRow);
	    	q.setMaxResults(numberOfRows);
	    }
	    try{
	    	return q.getResultList();
		}
	    catch(NoResultException nex){
	    	return new ArrayList<AnexosRedes>();
	    }
	}

	@Override
	public AnexosRedes retornarUltimaMinutaDemanda(DemandasRedes demanda) {
		
		String jpaquery = "SELECT anexo FROM AnexosRedes anexo WHERE anexo.demanda = :demanda and anexo.tipoanexo.idtipoanexo = 1 ORDER BY anexo.idanexo DESC";
	    TypedQuery<AnexosRedes> q = em.createQuery(jpaquery,AnexosRedes.class);
	    q.setMaxResults(1);
	    q.setParameter("demanda", demanda);
		
	    try{
	    	return q.getResultList().get(0);
		}
	    catch(IndexOutOfBoundsException e){
	    	return new AnexosRedes();
	    }
	}

	@Override
	public void remover(AnexosRedes anexo) {
		em.remove(em.merge(anexo));
	}

	@Override
	public TiposAnexosRedes recuperarTipoAnexo(Long idtipoanexo) {
		String jpaquery = "SELECT tipoanexo FROM TiposAnexosRedes tipoanexo WHERE tipoanexo.idtipoanexo = :idtipoanexo";
	    TypedQuery<TiposAnexosRedes> q = em.createQuery(jpaquery,TiposAnexosRedes.class);
	    q.setParameter("idtipoanexo", idtipoanexo);
	    try{
	    	return q.getResultList().get(0);
		}
	    catch(IndexOutOfBoundsException e){
	    	return new TiposAnexosRedes();
	    }
	}
}
