package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.FornecedoresInt;
import com.ssi.kernel.model.Fornecedores;


@ManagedBean
@ViewScoped
public class TestandoMB implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4562314348500634066L;

	@EJB
	private FornecedoresInt fornecedoresInt;
	
	private List<Fornecedores> fornecedores;
	private Fornecedores fornecedor;
	
	
	public TestandoMB() {}	
		
	public void incluir(){
		//System.out.println("EXECUTEI !");
		//System.out.println("Titulo da compra: " +  demandas.getCnmtitulocompra());
		//System.out.println("Solicitante --> " + demandas);
	}

	@PostConstruct
    public void init() {
		
		fornecedor  = new Fornecedores();
		
		
    }

	public void testar(){
		System.out.println(">> teste <<");
	}
	
	public List<Fornecedores> recuperar(){
		System.out.println(">>recuperar()");
		fornecedores = fornecedoresInt.recuperar();
		return fornecedores;
	}

	public List<Fornecedores> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedores> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public Fornecedores getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedores fornecedor) {
		this.fornecedor = fornecedor;
	}
			
}
