package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.kernel.model.Pessoas;

@ManagedBean
@ViewScoped
public class ParticipantesMB implements Serializable {

	private static final long serialVersionUID = -4540028558010909986L;
		
	@EJB
	private ParticipanteInt participanteInt;	
	
	private List<Pessoas> responsaveisTecnicos;
	private List<Pessoas> analistasDeContrato;
	
	public ParticipantesMB() {}
	
	@PostConstruct
    public void init() {	
		analistasDeContrato = new ArrayList<Pessoas>();		
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

	public List<Pessoas> getAnalistasDeContrato() {
		return analistasDeContrato;
	}

	public void setAnalistasDeContrato(List<Pessoas> analistasDeContrato) {
		this.analistasDeContrato = analistasDeContrato;
	}

	public void setResponsaveisTecnicos(List<Pessoas> responsaveisTecnicos) {
		this.responsaveisTecnicos = responsaveisTecnicos;
	}
	
}
