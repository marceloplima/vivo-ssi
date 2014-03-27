package com.ssi.admin.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.CargosInt;
import com.ssi.kernel.model.Cargos;

@ManagedBean
@ViewScoped
public class CargosMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5217254605904081828L;
	
	@EJB 
	private CargosInt cargoint;
	
	private List<Cargos> cargos;
	
	public List<Cargos> recuperar(){
		cargos = cargoint.recuperar();
		return cargos;
	}

	public List<Cargos> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargos> cargos) {
		this.cargos = cargos;
	}

}
