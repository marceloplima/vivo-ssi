package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.compras.domain.Prioridades;
import com.ssi.compras.common.interfaces.PrioridadeInt;

@ManagedBean
@ViewScoped
public class PrioridadesMB implements Serializable {

	private static final long serialVersionUID = -4479633020080699927L;
	
	@EJB
	private PrioridadeInt prioridadeInt;
	
	private List<Prioridades> prioridades;
		
	public PrioridadesMB() {
	}
	
	public List<Prioridades> getPrioridades() {
		
		if(prioridades==null){
			prioridades = prioridadeInt.recuperar();
		}			
		
		return prioridades;
	}

	public void setPrioridades(List<Prioridades> prioridades) {
		this.prioridades = prioridades;
	}


}
