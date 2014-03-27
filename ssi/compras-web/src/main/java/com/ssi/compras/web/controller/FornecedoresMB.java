package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.FornecedoresInt;
import com.ssi.kernel.model.Fornecedores;

@ManagedBean
@ViewScoped
public class FornecedoresMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private FornecedoresInt fornecedoresInt;
	
	private List<Fornecedores> fornecedores;
	
	public FornecedoresMB() {
	}
	
	public List<String> autocompletarForn(String autobusca){
		return fornecedoresInt.recuperarAutocomplete(autobusca.trim());
	}

	public List<Fornecedores> getFornecedores() {
		
		if(fornecedores==null){
			fornecedores = fornecedoresInt.recuperar();
		}
		
		return fornecedores;
	}
	
	

	public void setFornecedores(List<Fornecedores> fornecedores) {
		this.fornecedores = fornecedores;
	}
	
}
