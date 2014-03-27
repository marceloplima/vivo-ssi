package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.compras.common.interfaces.CapexOpexInt;
import com.ssi.kernel.compras.domain.CapexOpex;

@ManagedBean
@ViewScoped
public class CapexOpexMB implements Serializable {

	private static final long serialVersionUID = -2670414865275960308L;
	
	@EJB
	private CapexOpexInt capexpexint;
	
	private List<CapexOpex> capexOpex;

	public CapexOpexMB() {}
	
	public List<CapexOpex> getCapexOpex() {
		if(capexOpex==null){
			capexOpex = capexpexint.recuperar();
		}
		
		return capexOpex;
	}

	public void setCapexOpex(List<CapexOpex> capexOpex) {
		this.capexOpex = capexOpex;
	}
	
}
