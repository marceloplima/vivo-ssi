package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.model.Modulos;

@ManagedBean
@ViewScoped
public class BuscarModulosMB implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5637833019934725523L;

	@EJB
	private ModulosInt modulosint;
	
	private List<Modulos> modulos;
	
	private Modulos modulo = new Modulos();
	
	// Filtros
	private String cnmmodulo;
	
	@PostConstruct
    public void init() {
		modulos = new ArrayList<Modulos>();
    }
	
	public void buscar(){
		// Cria um objeto com o filtro informado e passa para o ejb
		Modulos modulo = new Modulos();
		modulo.setCnmmodulo(getCnmmodulo());
		modulos = modulosint.buscarModulos(modulo);
		modulo = null;
	}
  
	public void delete(Modulos modulo) {
    	modulos.remove(modulo);
    	modulosint.remover(modulo);
    }
	
	public List<Modulos> getModulos() {
		return modulos;
	}
	public void setModulos(List<Modulos> modulos) {
		this.modulos = modulos;
	}

	public Modulos getModulo() {
		return modulo;
	}

	public void setModulo(Modulos modulo) {
		this.modulo = modulo;
	}

	public String getCnmmodulo() {
		return cnmmodulo;
	}

	public void setCnmmodulo(String cnmmodulo) {
		this.cnmmodulo = cnmmodulo;
	}

}
