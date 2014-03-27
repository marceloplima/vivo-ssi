package com.ssi.admin.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PessoasTesteMB implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -184373638834906547L;
	private String cnmnome;
	private String nnrgaus;
	
	public void testarcad(){
		System.out.println(">>testarcad()<<");
		System.out.println(">"+getCnmnome());
		System.out.println(">"+getNnrgaus());
	}
	
	public void testarbusca(){
		System.out.println(">>testarbusca()<<");
		System.out.println(">"+getCnmnome());
		System.out.println(">"+getNnrgaus());
	}

	public String getCnmnome() {
		return cnmnome;
	}

	public void setCnmnome(String cnmnome) {
		this.cnmnome = cnmnome;
	}

	public String getNnrgaus() {
		return nnrgaus;
	}

	public void setNnrgaus(String nnrgaus) {
		this.nnrgaus = nnrgaus;
	}
	
}
