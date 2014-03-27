package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.compras.domain.Lps;
import com.ssi.compras.common.interfaces.LpsInt;

@ManagedBean
@ViewScoped
public class LpsMB implements Serializable {

	private static final long serialVersionUID = -2795136699614030909L;
	
	@EJB
	private LpsInt lpInt;
	
	private List<Lps> lps;
	
	public LpsMB() {
	}

	public List<Lps> getLps() {
		
		if(lps==null){
			lps = lpInt.recuperar();
		}			
		
		return lps;
	}

	public void setLps(List<Lps> lps) {
		this.lps = lps;
	}

	
}
