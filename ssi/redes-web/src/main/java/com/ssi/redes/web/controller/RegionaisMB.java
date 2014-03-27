package com.ssi.redes.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.RegionaisInt;
import com.ssi.kernel.model.Regionais;

@ManagedBean
@ViewScoped
public class RegionaisMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4692394757666852025L;

	@EJB
	private RegionaisInt regionalint;
	
	private List<Regionais> regionais;
		
	public RegionaisMB() {
	}
	
	public List<Regionais> getRegionais() {
		
		if(regionais==null){
			regionais = regionalint.recuperar();
		}
		
		return regionais;
	}

	public void setRegionais(List<Regionais> regionais) {
		this.regionais = regionais;
	}


}
