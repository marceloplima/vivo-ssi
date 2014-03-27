package com.ssi.redes.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.redes.domain.EmissoresRedes;
import com.ssi.kernel.redes.interfaces.PermissoesRedesInt;
import com.ssi.kernel.model.Pessoas;


@Stateless(mappedName = "SLSBPermissoes")
public class SLSBPermissoes implements PermissoesRedesInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;	
	
	public SLSBPermissoes() {
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean verificarPermissaoEmissor(Pessoas pessoa) {
		
		final String jpaquery = "SELECT e from EmissoresRedes e join fetch e.grupoModulo gm join fetch gm.gruposmodulospessoas WHERE :pessoa in elements(gm.gruposmodulospessoas) ";
		TypedQuery<EmissoresRedes> q = em.createQuery(jpaquery, EmissoresRedes.class);
		q.setParameter("pessoa", pessoa);
		try{
			q.getResultList().get(0);
			return true;
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
	}
}
