package com.ssi.admin.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.model.Modulos;


@ManagedBean
@RequestScoped
public class ModulosMB{

	@EJB 
	private ModulosInt modulosproxy;
	
	private Modulos modulo = new Modulos();
	private List<Modulos> modulos;
	
	public List<Modulos> recuperar(){
		modulos = modulosproxy.recuperar();
		return modulos;
	}

	public Modulos getModulo() {
		return modulo;
	}

	public void setModulo(Modulos modulo) {
		this.modulo = modulo;
	}

	public List<Modulos> getModulos() {
		return modulos;
	}

	public void setModulos(List<Modulos> modulos) {
		this.modulos = modulos;
	}

}
