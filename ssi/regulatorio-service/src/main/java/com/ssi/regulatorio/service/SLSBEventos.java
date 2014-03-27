package com.ssi.regulatorio.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.EventosRedes;
import com.ssi.kernel.redes.domain.EventosRedesConcluir;
import com.ssi.kernel.redes.interfaces.EventosRedesInt;

@Stateless(mappedName = "SLSBEventos")
public class SLSBEventos implements EventosRedesInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBEventos(){}

	@Override
	public void registrar(EventosRedes obj) {
		em.merge(obj);
	}
	
	@Override
	public List<EventosRedes> retornarPaginado(int firstRow, int numberOfRows, DemandasRedes demanda) {
		if(demanda.getIddemanda()==null){
			return new ArrayList<EventosRedes>(); 
		}
		String jpaquery = "SELECT evento FROM EventosRedes evento where evento.demanda = :demanda";
	    TypedQuery<EventosRedes> q = em.createQuery(jpaquery,EventosRedes.class);
	    q.setParameter("demanda", demanda);
	        
	    if (firstRow >= 0 && numberOfRows > 0){
	    	q.setFirstResult(firstRow);
	    	q.setMaxResults(numberOfRows);
	    }
	    try{
	    	return q.getResultList();
		}
	    catch(NoResultException nex){
	    	return new ArrayList<EventosRedes>();
	    }
	}
	
	@Override
	public EventosRedes getRowData(Object rowKey) {
		return em.find(EventosRedes.class, rowKey);
	}
	
	@Override
	public Integer getRowCount(DemandasRedes demanda) {
		if(demanda.getIddemanda()==null){
			return 0;
		}
		String jpaquery = "SELECT count(evento.idevento) FROM EventosRedes evento where evento.demanda = :demanda";
	    TypedQuery<Long> q = em.createQuery(jpaquery,Long.class);
	    q.setParameter("demanda", demanda);
	    		
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
	public List<EventosRedes> recuperarEventos() {
		final String jpaquery = "SELECT eventos FROM EventosRedes eventos";
		TypedQuery<EventosRedes> q = em.createQuery(jpaquery, EventosRedes.class);
		try{
			return q.getResultList();
		}
		catch(NoResultException ex){
			return null;
		}
	}
	
	@Override
	public EventosRedes recuperarUltimoEventoDemanda(Long iddemanda) {
		// TODO Auto-generated method stub
		final String jpaquery = "SELECT evento FROM EventosRedes evento where evento.demanda.iddemanda = :iddemanda ORDER BY evento.idevento DESC";
		TypedQuery<EventosRedes> q = em.createQuery(jpaquery, EventosRedes.class);
		q.setParameter("iddemanda", iddemanda);
		q.setMaxResults(1);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}
	
	@Override
	public EventosRedesConcluir recuperarEventosRedesConcluir(EventosRedes evento) {
		// TODO Auto-generated method stub
		final String jpaquery = "SELECT erc FROM EventosRedesConcluir erc where erc.eventorc = :evento";
		TypedQuery<EventosRedesConcluir> q = em.createQuery(jpaquery, EventosRedesConcluir.class);
		q.setParameter("evento", evento);
		try{
			return q.getResultList().get(0);
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}

}
