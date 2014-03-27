package com.ssi.redes.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.interfaces.CopiadosInt;
import com.ssi.kernel.model.Pessoas;

@Stateless(mappedName = "SLSBCopiados")
public class SLSBCopiados implements CopiadosInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;

	@Override
	public List<Pessoas> recuperarCopiadosDemanda(DemandasRedes demanda) {
		
		String jpaquery = "SELECT pessoas FROM DemandasRedes demanda INNER JOIN demanda.copiados pessoas WHERE demanda = :demanda";
	    TypedQuery<Pessoas> q = em.createQuery(jpaquery,Pessoas.class);
	    q.setParameter("demanda", demanda);
	    
	    try{
	    	return q.getResultList();
		}
	    catch(IndexOutOfBoundsException e){
	    	return new ArrayList<Pessoas>();
	    }
	    
	}
}
