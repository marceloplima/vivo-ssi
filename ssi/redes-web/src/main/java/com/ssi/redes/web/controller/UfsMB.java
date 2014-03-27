package com.ssi.redes.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.UfsInt;
import com.ssi.kernel.model.Ufs;

@ManagedBean
@ViewScoped
public class UfsMB implements Serializable {

	private static final long serialVersionUID = 5613637750871746280L;
	
	@EJB
	private UfsInt ufsint;
	
	private List<Ufs> ufs;

	public List<Ufs> getUfs() {
		
		if(ufs==null){
			ufs = ufsint.recuperar();
		}
		return ufs;
	}

	public void setUfs(List<Ufs> ufs) {
		this.ufs = ufs;
	}
	
	
	
	

}
