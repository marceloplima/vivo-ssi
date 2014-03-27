package com.ssi.kernel.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.model.Modulos;

@Stateless(mappedName = "SLSBModulos")
public class SLSBModulos implements ModulosInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Modulos> buscarModulos(Modulos modulo) {
		final String jpaquery = "SELECT modulo FROM Modulos modulo WHERE cnmmodulo like '%"+modulo.getCnmmodulo().trim()+"%'";
		TypedQuery<Modulos> q = em.createQuery(jpaquery, Modulos.class);
		return q.getResultList();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Modulos recuperarUnico(Long idmodulo) {
		final String jpaquery = "SELECT modulo FROM Modulos modulo WHERE idmodulo = "+idmodulo;
		return em.createQuery(jpaquery, Modulos.class).getResultList().get(0);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean verificaExistente(Modulos modulo) throws IndexOutOfBoundsException {
		Modulos mod;
		try{
			String jpaquery = "SELECT modulo FROM Modulos modulo WHERE cnmmodulo = '"+modulo.getCnmmodulo().trim()+"'";
			
			if(modulo.getIdmodulo()!=null && modulo.getIdmodulo()>=1)
				jpaquery+=" AND idmodulo != "+modulo.getIdmodulo();
			
			//System.out.println(jpaquery);
			
			mod = em.createQuery(jpaquery, Modulos.class).getResultList().get(0);
		}
    	catch(IndexOutOfBoundsException e){
    		return false;
    	}
		
		if(mod!=null)
			return true;
		else
			return false;
	}
	
	@Override
	public Modulos incluir(Modulos modulo) {
		em.persist(modulo);
		em.flush();
		em.refresh(modulo);
		
		return modulo;
	}

	@Override
	public void alterar(Modulos modulo) {
		em.merge(modulo);
		em.detach(modulo);
	}

	@Override
	public List<Modulos> recuperar() {
		final String jpaquery = "SELECT modulo FROM Modulos modulo";
		TypedQuery<Modulos> q = em.createQuery(jpaquery, Modulos.class);
		return q.getResultList();
	}
	
	@Override
	public void remover(Modulos modulo){
//		em.remove(em.contains(modulo) ? modulo : em.merge(modulo));
//		em.flush();
//		em.detach(modulo);
	}

}
