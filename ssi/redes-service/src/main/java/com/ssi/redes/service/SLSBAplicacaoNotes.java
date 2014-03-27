package com.ssi.redes.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ssi.kernel.redes.domain.AplicacaoNotes;
import com.ssi.kernel.redes.interfaces.AplicacaoNotesInt;

@Stateless(mappedName = "SLSBAplicacaoNotes")
public class SLSBAplicacaoNotes implements AplicacaoNotesInt {

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;

	@Override
	public AplicacaoNotes recuperarUnico(Long id) {		
		return em.find(AplicacaoNotes.class, id);
	}
	
}
