package com.ssi.regulatorio.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.redes.interfaces.ParticipantesRedesInt;

@ManagedBean
@ViewScoped
public class ParticipantesMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -80406186434346219L;

	@EJB
	private ParticipantesRedesInt participanteInt;	
	
	private List<Pessoas> responsaveisTecnicos;
	
	public ParticipantesMB() {}
	
	@PostConstruct
    public void init() {	
    }

	public List<String> autocompletarRespTecnico(String autobusca){
		return participanteInt.recuperarAutocompleteRespTecnico(autobusca.trim());	
	}
	
	public List<Pessoas> getResponsaveisTecnicos() {
		
		if(responsaveisTecnicos==null){			
			responsaveisTecnicos = participanteInt.recuperarResponsaveisTecnicos();			
		}
		
		return responsaveisTecnicos;
	}


	public void setResponsaveisTecnicos(List<Pessoas> responsaveisTecnicos) {
		this.responsaveisTecnicos = responsaveisTecnicos;
	}
	
}
