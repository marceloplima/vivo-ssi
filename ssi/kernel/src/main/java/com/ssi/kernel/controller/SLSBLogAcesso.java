package com.ssi.kernel.controller;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ssi.kernel.controller.interfaces.LogAcessoInt;
import com.ssi.kernel.model.LogAcesso;

@Stateless(mappedName="SLSBLogAcesso")
public class SLSBLogAcesso implements LogAcessoInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBLogAcesso(){}
	
	
	@Override
	public void incluir(LogAcesso obj) {
		em.merge(obj);
		em.flush();
	}
	
}
