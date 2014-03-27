package com.ssi.redes.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.Rotas;
import com.ssi.kernel.redes.interfaces.RotasInt;

@Stateless(mappedName = "SLSBRotas")
public class SLSBRotas implements RotasInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
		
	public SLSBRotas() {}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Rotas> recuperarRotasDemanda(DemandasRedes demanda) {
		
		if(demanda.getIddemanda()==null){
			return new ArrayList<Rotas>();
		}
		
		final String jpaquery = "SELECT rota FROM Rotas rota where rota.demanda = :demanda ORDER BY rota.idrota";
		TypedQuery<Rotas> q = em.createQuery(jpaquery, Rotas.class);
		q.setParameter("demanda", demanda);
		try{
			return q.getResultList();
		}
		catch(IndexOutOfBoundsException e){
			e.printStackTrace();
			return new ArrayList<Rotas>(); 
		}
		
	}

	@Override
	public Rotas persistir(Rotas rota) {
		em.merge(rota);
		em.flush();
		em.detach(rota);
		return rota;
	}
	
	@Override
	public void remover(Rotas rota){
		em.remove(em.merge(rota));
	}

}
