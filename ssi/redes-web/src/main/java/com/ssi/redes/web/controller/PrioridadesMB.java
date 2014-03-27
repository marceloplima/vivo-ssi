package com.ssi.redes.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.redes.domain.PrioridadesRedes;
import com.ssi.kernel.redes.interfaces.PrioridadeRedesInt;

@ManagedBean
@ViewScoped
public class PrioridadesMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5638377338815545557L;

	@EJB
	private PrioridadeRedesInt prioridadeInt;
	
	private List<PrioridadesRedes> prioridades;
		
	public PrioridadesMB() {
	}
	
	public List<PrioridadesRedes> getPrioridades() {
		
		if(prioridades==null){
			prioridades = prioridadeInt.recuperar();
		}			
		
		return prioridades;
	}

	public void setPrioridades(List<PrioridadesRedes> prioridades) {
		this.prioridades = prioridades;
	}

}
