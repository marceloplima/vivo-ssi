package com.ssi.kernel.controller;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.MaquinaEmailsInt;
import com.ssi.kernel.model.MaquinaEmails;

@Stateless(mappedName="SLSBMaquinaEmails")
public class SLSBMaquinaEmails implements MaquinaEmailsInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	public SLSBMaquinaEmails(){}
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<MaquinaEmails> recuperar() {
		final String jpaquery = "SELECT obj FROM MaquinaEmails obj";
		TypedQuery<MaquinaEmails> q = em.createQuery(jpaquery, MaquinaEmails.class);
		return q.getResultList();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<MaquinaEmails> recuperarFiltrado(Map<String, Object> filtros) {
		
		String jpaquery = "SELECT obj FROM MaquinaEmails obj WHERE 1=1 ";
		
		MaquinaEmails obj = (MaquinaEmails) filtros.get("obj");
		
		if(obj!=null){
			if(!obj.getCnmmodulo().isEmpty()){
				jpaquery +="and obj.cnmmodulo like '%"+obj.getCnmmodulo()+"%'";
			}
		}
		
		TypedQuery<MaquinaEmails> q = em.createQuery(jpaquery, MaquinaEmails.class);
		
		return q.getResultList();
	}
	
	@Override
	public void incluir(MaquinaEmails obj) {
		em.merge(obj);
		em.flush();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MaquinaEmails recuperarUnico(MaquinaEmails obj) {
		return em.find(MaquinaEmails.class, obj);
	}

	@Override
	public void alterar(MaquinaEmails obj) {
		em.merge(obj);
		em.detach(obj);
	}
	
}
