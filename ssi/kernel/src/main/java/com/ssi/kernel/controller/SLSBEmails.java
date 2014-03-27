package com.ssi.kernel.controller;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ssi.kernel.controller.interfaces.EmailsInt;
import com.ssi.kernel.model.Emails;

@Stateless(mappedName = "SLSBEmails")
public class SLSBEmails implements EmailsInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean verificaExistente(Emails email) throws IndexOutOfBoundsException {
		Emails emailret;
		try{
			String jpaquery = "SELECT email FROM Emails email WHERE cnmemail = '"+email.getCnmemail().trim()+"'";
			
			//if(email.getIdemail()!=null && email.getIdemail()>=1)
				//jpaquery+=" AND idemail != "+email.getIdemail();
			
			//System.out.println(jpaquery);
			
			emailret = em.createQuery(jpaquery, Emails.class).getResultList().get(0);
		}
    	catch(IndexOutOfBoundsException e){
    		return false;
    	}
		
		if(emailret!=null)
			return true;
		else
			return false;
	}
	
	@Override
	public Emails incluir(Emails email) {
		em.persist(email);
		em.flush();
		em.refresh(email);
		
		return email;
	}

	@Override
	public void alterar(Emails email) {
		em.merge(email);
		em.flush();
		em.detach(email);
	}
	
	@Override
	public void remover(Emails email){
	}

}
