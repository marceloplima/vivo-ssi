package com.ssi.kernel.controller;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ssi.kernel.controller.interfaces.TelefonesInt;
import com.ssi.kernel.model.Telefones;

@Stateless(mappedName = "SLSBTelefones")
public class SLSBTelefones implements TelefonesInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean verificaExistente(Telefones telefone) throws IndexOutOfBoundsException {
		Telefones telret;
		try{
			String jpaquery = "SELECT telefone FROM Telefones telefone WHERE cnmtelefone = '"+telefone.getCnmtelefone().trim()+"'";
			
			if(telefone.getIdtelefone()!=null && telefone.getIdtelefone()>=1)
				jpaquery+=" AND idtelefone != "+telefone.getIdtelefone();
			
			//System.out.println(jpaquery);
			
			telret = em.createQuery(jpaquery, Telefones.class).getResultList().get(0);
		}
    	catch(IndexOutOfBoundsException e){
    		return false;
    	}
		
		if(telret!=null)
			return true;
		else
			return false;
	}
	
	@Override
	public Telefones incluir(Telefones telefone) {
		em.persist(telefone);
		em.flush();
		em.refresh(telefone);
		
		return telefone;
	}

	@Override
	public void alterar(Telefones telefone) {
		em.merge(telefone);
		em.flush();
		em.detach(telefone);
	}
	
	@Override
	public void remover(Telefones telefone){
	}

}
